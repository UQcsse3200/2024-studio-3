package com.csse3200.game.components.items;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class ChopIngredientComponentTest {

    private ChopIngredientComponent chopIngredientComponent;
    private IngredientComponent mockIngredient;
    private GameTime mockTimesource;
    private Entity mockEntity;

    /**
     * Sets up the test environment by initializing the necessary mock objects and
     * registering the time source, executed before each test.
     */
    @BeforeEach
    public void setUp() {
        // Clear service locator before tests
        ServiceLocator.clear();

        ServiceLocator.registerEntityService(new EntityService());

        mockEntity = new Entity();
        mockIngredient = mock(IngredientComponent.class);
        mockTimesource = mock(GameTime.class);
        ServiceLocator.registerTimeSource(mockTimesource);

        chopIngredientComponent = new ChopIngredientComponent();

        mockEntity.addComponent(mockIngredient).addComponent(chopIngredientComponent);
    }

    /**
     * Tests that cooking starts when the `chopIngredient` method is called with the "NORMAL"
     * station state and an oven multiplier of 1. The test verifies that the cooking process
     * is initiated by checking that `getIsChopping()` returns true.
     */
    @Test
    public void testChoppingStarts() {
        when(mockTimesource.getTime()).thenReturn(1000L); // Simulate game time
        mockEntity.create();

        mockEntity.getEvents().trigger("chopIngredient");

        verify(mockTimesource).getTime();
        verify(mockIngredient).getChopTime();
        assertTrue(chopIngredientComponent.getIsChopping());
    }

    /**
     * Tests that the ingredient becomes cooked after the appropriate cooking time has passed.
     * The test simulates the passage of time and verifies that the `chopItem();` method on the
     * ingredient component is called.
     */
    @Test
    public void testIngredientBecomesChopped() {
        when(mockTimesource.getTime()).thenReturn(1000L, 10000L); // Simulate passage of time
        when(mockIngredient.getChopTime()).thenReturn(1);
        mockEntity.create();

        mockEntity.getEvents().trigger("chopIngredient");

        chopIngredientComponent.update(); // This should trigger chopping

        // ingredient is chopped
        verify(mockIngredient).chopItem();
    }

    /**
     * Tests that the cooking process stops when the `stopCookingIngredient` method is called.
     * The test verifies that `getIsChopping()` returns false after stopping the cooking process.
     */
    @Test
    public void testStopChopping() {
        when(mockTimesource.getTime()).thenReturn(1000L, 1000L);
        when(mockIngredient.getChopTime()).thenReturn(10); // chopping process wouldn't finsish yet
        mockEntity.create();

        mockEntity.getEvents().trigger("chopIngredient");
        assertTrue(chopIngredientComponent.getIsChopping());
        mockEntity.getEvents().trigger("stopChoppingIngredient");
        assertFalse(chopIngredientComponent.getIsChopping());
    }

    @Test
    public void testRageMode() {
        when(mockTimesource.getTime()).thenReturn(1000L, 6000L);
        when(mockIngredient.getChopTime()).thenReturn(10);
        mockEntity.create();

        ServiceLocator.getEntityService().getEvents().trigger("rageModeOn");

        mockEntity.getEvents().trigger("chopIngredient");
        
        chopIngredientComponent.update(); // This should trigger chopping

        // ingredient is chopped
        verify(mockIngredient).chopItem();
    }

}