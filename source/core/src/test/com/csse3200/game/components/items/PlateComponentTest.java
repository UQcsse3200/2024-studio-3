package com.csse3200.game.components.items;

import com.csse3200.game.entities.Entity;
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
    void shouldAddMealToPlateAndPlateNotNullAndServable() {
        plateComponent.addMealToPlate("Steak");

        assertEquals("Steak", plateComponent.getItemOnPlate());
        assertFalse(plateComponent.isAvailable());
        assertNotNull(plateComponent.getItemOnPlate());
        assertTrue(plateComponent.isServable());
    }

    @Test
    void shouldQuantityDecreaseAfterPickup() {
        plateComponent.setQuantity(1);
        plateComponent.pickup(mockPlayer);
        assertEquals(0, plateComponent.getQuantity());
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
}