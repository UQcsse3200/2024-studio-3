package com.csse3200.game.components.interaction;

import com.csse3200.game.components.CustomerSensorComponent;
import com.csse3200.game.components.npc.CustomerComponent;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.configs.BaseCustomerConfig;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
class CustomerSensorComponentTest {

    private CustomerSensorComponent customerSensorComponent;

    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        customerSensorComponent = new CustomerSensorComponent(5f);
        assertNotNull(customerSensorComponent, "CustomerSensorComponent should have been initialized");
    }

    @Test
    void checkSensorInit() {
        assertNotNull(this.customerSensorComponent, "The customerSensorComponent should not be null");

        // Set up entities
        Entity player = createEntity(0, 0);

        Fixture playerFixture = player.getComponent(InteractionComponent.class).getFixture();

        InteractionComponent sensorInteractionComponent = this.customerSensorComponent.getInteractionComponent();
        assertNotNull(sensorInteractionComponent, "The interaction component for sensor should not be null");

        Fixture sensorFixture = sensorInteractionComponent.getFixture();
        assertNotNull(sensorFixture, "The fixture for interaction component of sensor should not be null");

        assertSame(sensorFixture, playerFixture, "Sensor fixture should match player fixture");
    }

    @Test
    void shouldDetectClosestCustomer() {
        Entity player = createEntity(0, 0);
        Entity customer = createCustomer(1, 1);

        Fixture playerFixture = player.getComponent(InteractionComponent.class).getFixture();
        Fixture customerFixture = customer.getComponent(InteractionComponent.class).getFixture();

        customerSensorComponent.onCollisionStart(playerFixture, customerFixture);

        Fixture closestFixture = customerSensorComponent.getClosestCustomer();
        assertNotNull(closestFixture, "Closest fixture should not be null");
        assertEquals(customerFixture, closestFixture, "The closest fixture should be the customer's fixture");
    }

    @Test
    void shouldNotDetectFarAwayCustomer() {
        Entity player = createEntity(0, 0);
        Entity customer = createCustomer(10, 10);

        Fixture playerFixture = player.getComponent(InteractionComponent.class).getFixture();
        Fixture customerFixture = customer.getComponent(InteractionComponent.class).getFixture();

        customerSensorComponent.onCollisionStart(playerFixture, customerFixture);
        assertFalse(customerSensorComponent.isWithinDistance(customerFixture, 5), "Customer should be too far away");

        Fixture closestFixture = customerSensorComponent.getClosestCustomer();
        assertNull(closestFixture, "Closest fixture should be null because the customer is too far");
    }

    @Test
    void shouldNotDetectNonCustomerEntity() {
        Entity player = createEntity(0, 0);
        Entity nonCustomer = createNonCustomer(1, 1);

        Fixture playerFixture = player.getComponent(InteractionComponent.class).getFixture();
        Fixture nonCustomerFixture = nonCustomer.getComponent(InteractionComponent.class).getFixture();

        customerSensorComponent.onCollisionStart(playerFixture, nonCustomerFixture);

        Fixture closestFixture = customerSensorComponent.getClosestCustomer();
        assertNull(closestFixture, "Closest fixture should be null because the entity is not a customer");
    }

    @Test
    void shouldRemoveCustomerAfterCollisionEnded() {
        Entity player = createEntity(0, 0);
        Entity customer = createCustomer(1, 1);

        Fixture playerFixture = player.getComponent(InteractionComponent.class).getFixture();
        Fixture customerFixture = customer.getComponent(InteractionComponent.class).getFixture();

        customerSensorComponent.onCollisionStart(playerFixture, customerFixture);

        Fixture closestFixture = customerSensorComponent.getClosestCustomer();
        assertNotNull(closestFixture, "Closest fixture should not be null");
        assertEquals(customerFixture, closestFixture, "The closest fixture should be the customer's fixture");

        // Move the player out of collision range
        player.setPosition(20, 20);
        customerSensorComponent.onCollisionEnd(playerFixture, customerFixture);

        closestFixture = customerSensorComponent.getClosestCustomer();
        assertNull(closestFixture, "Closest fixture should now be null after collision ends");
    }

    private Entity createEntity(float x, float y) {
        Entity entity = new Entity();
        entity.setPosition(x, y);
        entity.addComponent(new PhysicsComponent());
        entity.addComponent(customerSensorComponent);
        InteractionComponent component = new InteractionComponent(PhysicsLayer.PLAYER);
        entity.addComponent(component);
        entity.create();
        return entity;
    }

    private Entity createCustomer(float x, float y) {
        Entity customer = new Entity();
        customer.setPosition(x, y);
        BaseCustomerConfig baseConfig = new BaseCustomerConfig();
        customer.addComponent(new CustomerComponent(baseConfig)); // Add CustomerComponent to identify as customer
        InteractionComponent interactionComponent = new InteractionComponent(PhysicsLayer.NPC);
        customer.addComponent(new PhysicsComponent());
        customer.addComponent(interactionComponent);
        customer.create();
        return customer;
    }

    private Entity createNonCustomer(float x, float y) {
        Entity nonCustomer = new Entity();
        nonCustomer.setPosition(x, y);
        InteractionComponent interactionComponent = new InteractionComponent(PhysicsLayer.PLAYER); // Non-customer layer
        nonCustomer.addComponent(new PhysicsComponent());
        nonCustomer.addComponent(interactionComponent);
        nonCustomer.create();
        return nonCustomer;
    }
}
