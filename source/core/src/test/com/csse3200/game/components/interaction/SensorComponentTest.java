package com.csse3200.game.components.interaction;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.csse3200.game.components.SensorComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.services.InteractableService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(GameExtension.class)
public class SensorComponentTest {

    private SensorComponent sensorComponent;  // Declare sensorComponent here
    private Entity mockPlayer;
    private PhysicsComponent mockPhysics;
    private Body mockBody;

    @BeforeEach
    void beforeEach() {
        ServiceLocator.clear();

        // Create the mock player with set position of (0, 0)
        sensorComponent = new SensorComponent();
        assertNotNull(sensorComponent, "sensor component should have been initialised");

        ServiceLocator.registerPhysicsService(new PhysicsService());

        mockBody = mock(Body.class);
        mockPhysics = mock(PhysicsComponent.class);
        when(mockPhysics.getBody()).thenReturn(mockBody);
        when(mockBody.getPosition()).thenReturn(new Vector2(0, 0));
        mockPlayer = new Entity();
        mockPlayer.addComponent(mockPhysics);
        mockPlayer.addComponent(sensorComponent);

        // Create an interactable service to be able to register entities
        ServiceLocator.registerInteractableService(new InteractableService());

        // Create the player entity
        mockPlayer.create();
    }

    private Entity createEntityAt(float x, float y) {
        Body mockBodyOther = mock(Body.class);
        PhysicsComponent mockPhysicsOther = mock(PhysicsComponent.class);
        when(mockPhysicsOther.getBody()).thenReturn(mockBodyOther);
        when(mockBodyOther.getPosition()).thenReturn(new Vector2(x, y));
        
        Entity mockEntity = new Entity();
        mockEntity.addComponent(mockPhysicsOther);

        return mockEntity;
    }

    @Test
    void TestSensorInit() {
        Vector2 playerPosition = mockPlayer.getComponent(PhysicsComponent.class).getBody().getPosition();
        assertTrue(playerPosition.isZero());
    }

    @Test
    void TestSensorNoInteractables() {
        Entity closestEntity = mockPlayer.getComponent(SensorComponent.class).getClosestInteractable();
        assertNull(closestEntity);
    }

    @Test
    void TestSensorOneInteractableOutOfRange() {
        Entity mockEntity = createEntityAt(1, 1);
        ServiceLocator.getInteractableService().registerEntity(mockEntity);

        Entity closestEntity = sensorComponent.getClosestInteractable();
        assertNull(closestEntity);
    }

    @Test
    void TestSensorOneInteractableInRange() {
        Entity mockEntity = createEntityAt(0, 1);
        ServiceLocator.getInteractableService().registerEntity(mockEntity);

        Entity closestEntity = sensorComponent.getClosestInteractable();
        assertNotNull(closestEntity);
        assertEquals(mockEntity, closestEntity);
    }

    @Test
    void TestSensorTwoInteractableBothOutOfRange() {
        Entity mockEntity1 = createEntityAt(2, 1);
        ServiceLocator.getInteractableService().registerEntity(mockEntity1);

        Entity mockEntity2 = createEntityAt(1, 1);
        ServiceLocator.getInteractableService().registerEntity(mockEntity2);

        Entity closestEntity = sensorComponent.getClosestInteractable();
        assertNull(closestEntity);
    }

    @Test
    void TestSensorTwoInteractableBothInRangeBigToSmall() {
        Entity mockEntity1 = createEntityAt(0f, 1f);
        ServiceLocator.getInteractableService().registerEntity(mockEntity1);

        Entity mockEntity2 = createEntityAt(0.5f, 0f);
        ServiceLocator.getInteractableService().registerEntity(mockEntity2);

        Entity closestEntity = sensorComponent.getClosestInteractable();
        assertNotNull(closestEntity);
        assertEquals(mockEntity2, closestEntity);
    }

    @Test
    void TestSensorTwoInteractableBothInRangeSmallToBig() {
        Entity mockEntity1 = createEntityAt(0.5f, 0f);
        ServiceLocator.getInteractableService().registerEntity(mockEntity1);

        Entity mockEntity2 = createEntityAt(0.5f, 1f);
        ServiceLocator.getInteractableService().registerEntity(mockEntity2);

        Entity closestEntity = sensorComponent.getClosestInteractable();
        assertNotNull(closestEntity);
        assertEquals(mockEntity1, closestEntity);
    }

    @Test
    void TestSensorTwoInteractableOneInRange() {
        Entity mockEntity1 = createEntityAt(1, 1);
        ServiceLocator.getInteractableService().registerEntity(mockEntity1);

        Entity mockEntity2 = createEntityAt(0.5f, 0f);
        ServiceLocator.getInteractableService().registerEntity(mockEntity2);

        Entity closestEntity = sensorComponent.getClosestInteractable();
        assertNotNull(closestEntity);
        assertEquals(mockEntity2, closestEntity);
    }
}
