package com.csse3200.game.components.items;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.entities.factories.PlateFactory;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlateComponentTest {

    private PlateComponent plateComponent;
    private Entity mockEntity;
    private Entity mockPlayer;

    @BeforeEach
    void setUp() {

        mockEntity = mock(Entity.class);
        mockPlayer = mock(Entity.class);

        plateComponent = new PlateComponent(3);
        plateComponent.setEntity(mockEntity);

    }

    @Test
    void shouldInstantiatePlateComponentWithGivenValuesAndDefaulValues() {
        assertEquals(3, plateComponent.getQuantity());
        assertTrue(plateComponent.isStacked());
        assertEquals(-1, plateComponent.getId());
        assertArrayEquals(new int[]{1, 2, 3}, plateComponent.getPlateArray());
    }

    /* Issues with Render/Physics Component
    @Test
    void shouldDecreaseQuantityAfterPickup() {
        plateComponent.setQuantity(1);
        plateComponent.pickup(mockPlayer);
        assertEquals(0, plateComponent.getQuantity());
        assertArrayEquals(new int[]{}, plateComponent.getPlateArray());
    }*/

    @Test
    void shouldAddMealToPlateAndPlateNotNullAndServable() {
        plateComponent.setQuantity(2);
        plateComponent.addMealToPlate("Steak");
        assertEquals("Steak", plateComponent.getItemOnPlate());
        assertFalse(plateComponent.isAvailable());
        assertNotNull(plateComponent.getItemOnPlate());
        assertTrue(plateComponent.isServable());
    }

    @Test
    void shouldUnavailableAfterDispose() {
        plateComponent.dispose();
        assertFalse(plateComponent.isAvailable());
    }

    @Test
    void shouldQuantity3AfterInstantiate() {
        assertEquals(3, plateComponent.getQuantity());
    }

    @Test
    void shouldSetQuantityTo5() {
        plateComponent.setQuantity(5);
        assertEquals(5, plateComponent.getQuantity());
    }

    @Test
    void shouldIsWashedAfterInstantiate() {
        assertTrue(plateComponent.isWashed());
    }

    @Test
    void shouldAvailableAfterInstantiate() {
        assertTrue(plateComponent.isAvailable());
    }

    @Test
    void shouldNotServableAfterInstantiate() {
        assertFalse(plateComponent.isServable());
    }

    @Test
    void shouldSetIdToGivenValue(){
        plateComponent.setId(1);
        assertEquals(1, plateComponent.getId());
    }
}