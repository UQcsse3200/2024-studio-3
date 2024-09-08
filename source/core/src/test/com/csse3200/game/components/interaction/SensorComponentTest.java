package com.csse3200.game.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.entities.Entity;
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
class SensorComponentTest {

    private SensorComponent sensorComponent;  // Declare sensorComponent here
    private final short interactableLayer = PhysicsLayer.INTERACTABLE;

    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        sensorComponent = new SensorComponent(interactableLayer, 1f);
        assertNotNull(sensorComponent, "sensor component should have been initialised");
    }

    @Test
    void checkSensorInit() {
        assertNotNull(this.sensorComponent, "The sensorComponent should not be null");
        // Set up entities
        Entity entity = createEntity(0, 0);
        Entity target = createTarget(0, 0);

        Fixture entityFixture = entity.getComponent(InteractionComponent.class).getFixture();
        Fixture targetFixture = target.getComponent(InteractionComponent.class).getFixture();
        //Ensure the sensor component is set up correctly
        InteractionComponent sensorInteractionComponent = this.sensorComponent.getInteractionComponent();
        assertNotNull(sensorInteractionComponent, "The interaction component for sensor should not be null");
        
        Fixture sensorFixture = sensorInteractionComponent.getFixture();
        assertNotNull(sensorFixture, "The fixture for interaction component of sensor should not be null");

        //Ensure the entity interaction component matchest the sensor interaction component
        assertSame(sensorFixture, entityFixture, "Sensore fixture should match entity fixture");
    }

    @Test
    void checkEntityInit() {
        Entity entity = createEntity(0, 0);
        InteractionComponent entityInteractionComponent = entity.getComponent(InteractionComponent.class);
        assertEquals(PhysicsLayer.INTERACTABLE, entityInteractionComponent.getLayer());
        Fixture entityFixture = entityInteractionComponent.getFixture();
        assertNotNull(entityFixture, "The fixture for InteractionComponent should not be null");
        assertEquals(interactableLayer, entityFixture.getFilterData().categoryBits);
        assertTrue(PhysicsLayer.contains(interactableLayer, entityFixture.getFilterData().categoryBits),
                "entity fixture should be in the physics layer");
    }

    @Test
    void checkTargetInit() {
        Entity target = createTarget(0, 0);
        InteractionComponent targetInteractionComponent = target.getComponent(InteractionComponent.class);
        assertEquals(PhysicsLayer.INTERACTABLE, targetInteractionComponent.getLayer());

        Fixture targetFixture = targetInteractionComponent.getFixture();
        assertNotNull(targetFixture, "The fixture for InteractionComponent should not be null");
        assertEquals(targetFixture.getFilterData().categoryBits, interactableLayer);
        assertTrue(PhysicsLayer.contains(interactableLayer, targetFixture.getFilterData().categoryBits),
                "entity fixture should be in the physics layer");
    }

    @Test
    void shouldAddFixtureToList() {
        Entity entity = createEntity(0, 0);
        Entity target = createTarget(0, 0);

        Fixture entityFixture = entity.getComponent(InteractionComponent.class).getFixture();
        Fixture targetFixture = target.getComponent(InteractionComponent.class).getFixture();

        assertEquals(targetFixture.getFilterData().categoryBits, entityFixture.getFilterData().categoryBits,
                "Target fixture layer should match entity fixture layer");

        assertNotEquals(sensorComponent.getInteractionComponent().getFixture(), targetFixture);
        assertTrue(PhysicsLayer.contains(interactableLayer, targetFixture.getFilterData().categoryBits),
                "target fixture should be in the physics layer");
        assertTrue(sensorComponent.isWithinDistance(targetFixture, 1));

        sensorComponent.onCollisionStart(entityFixture, targetFixture);
        assertEquals(1, sensorComponent.getNumFixtures(),
                "There should be one fixture added to the list");
    }

     @Test
     void shouldDetectClosestFixture() {
         Entity entity = createEntity(0, 0);
         Entity target = createTarget(0, 0);

         InteractionComponent targetInteractionComponent = target.getComponent(InteractionComponent.class);
         Fixture targetFixture = targetInteractionComponent.getFixture();

         InteractionComponent entityInteractionComponent = entity.getComponent(InteractionComponent.class);
         Fixture entityFixture = entityInteractionComponent.getFixture();

         sensorComponent.onCollisionStart(entityFixture, targetFixture);

         Fixture closestFixture = sensorComponent.getClosestFixture();
         assertNotNull(closestFixture, "Closest fixture should not be null");
         assertEquals(targetFixture, closestFixture, "The closest fixture should be the target's fixture");
     }

     @Test
     void shouldNotDetectFarFeature() {
         Entity entity = createEntity(0, 0);
         Entity target = createTarget(10, 10);

         Fixture entityFixture = entity.getComponent(InteractionComponent.class).getFixture();
         Fixture targetFixture = target.getComponent(InteractionComponent.class).getFixture();

         assertEquals(targetFixture.getFilterData().categoryBits, entityFixture.getFilterData().categoryBits,
                 "Target fixture layer should match entity fixture layer");

         assertNotEquals(sensorComponent.getInteractionComponent().getFixture(), targetFixture);
         assertTrue(PhysicsLayer.contains(interactableLayer, targetFixture.getFilterData().categoryBits),
                 "target fixture should be in the physics layer");
         assertFalse(sensorComponent.isWithinDistance(targetFixture, 1));

         sensorComponent.onCollisionStart(entityFixture, targetFixture);
         assertEquals(0, sensorComponent.getNumFixtures(),
                 "There should be no fixture added to the list");
         Fixture closestFixture = sensorComponent.getClosestFixture();
         assertNull(closestFixture, "Closest fixture should be null");
     }

     @Test
     void shouldNotDetectBadTarget() {
         Entity entity = createEntity(0, 0);
         Entity badTarget = createBadTarget(0, 0);

         Fixture entityFixture = entity.getComponent(InteractionComponent.class).getFixture();
         Fixture badTargetFixture = badTarget.getComponent(InteractionComponent.class).getFixture();

         sensorComponent.onCollisionStart(entityFixture, badTargetFixture);

         // Should not detect a target with the wrong layer
         assertEquals(0, sensorComponent.getNumFixtures(),
                 "There should be no fixture added to the list");
         Fixture closestFixture = sensorComponent.getClosestFixture();
         assertNull(closestFixture, "Closest fixture should be null");
     }

     @Test
     void shouldRemoveTargetAfterCollisionEnded() {
         Entity entity = createEntity(0, 0);
         Entity target = createTarget(0, 0);

         Fixture entityFixture = entity.getComponent(InteractionComponent.class).getFixture();
         Fixture targetFixture = target.getComponent(InteractionComponent.class).getFixture();

         sensorComponent.onCollisionStart(entityFixture, targetFixture);

         Fixture closestFixture = sensorComponent.getClosestFixture();
         assertNotNull(closestFixture, "Closest fixture should not be null");
         assertEquals(targetFixture, closestFixture, "The closest fixture should be the target's fixture");

         // Move the entity out of collision box
         entity.setPosition(20, 20);
         sensorComponent.onCollisionEnd(entityFixture, targetFixture);

         closestFixture = sensorComponent.getClosestFixture();
         assertNull(closestFixture, "Closest fixture should now be null");
     }

    private Entity createEntity(float x, float y) {
        Entity entity = new Entity();
        entity.setPosition(x, y);
        entity.addComponent(new PhysicsComponent()); // Initialize and add PhysicsComponent
        entity.addComponent(sensorComponent); // Add SensorComponent
        InteractionComponent component = new InteractionComponent(PhysicsLayer.INTERACTABLE);
        entity.addComponent(component);
        entity.create(); // Ensure components are created
        return entity;
    }

    private Entity createTarget(float x, float y) {
        Entity target = new Entity();
        target.setPosition(x, y);
        InteractionComponent interactionComponent = new InteractionComponent(PhysicsLayer.INTERACTABLE);
        target.addComponent(new PhysicsComponent());
        target.addComponent(interactionComponent);
        target.create();
        return target;
    }

    private Entity createBadTarget(float x, float y) {
        Entity target = new Entity();
        target.setPosition(x, y);
        InteractionComponent interactionComponent = new InteractionComponent(PhysicsLayer.NPC);
        target.addComponent(new PhysicsComponent());
        target.addComponent(interactionComponent);
        target.create();
        return target;
    }
}
