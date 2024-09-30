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
public class CookIngredientComponentTest {

    private CookIngredientComponent cookIngredientComponent;
    private IngredientComponent mockIngredient;
    private GameTime mockTimesource;
    private Entity mockEntity;

    /**
     * Sets up the test environment by initializing the necessary mock objects and
     * registering the time source, executed before each test.
     */
    @BeforeEach
    public void setUp() {
        // Clear service locator
        ServiceLocator.clear();

        ServiceLocator.registerEntityService(new EntityService());

        mockEntity = new Entity();
        // creating mockEntity via
        // mock(EventHandler.class) was causing some issue that I couldn't
        // look into yet

        mockIngredient = mock(IngredientComponent.class);
        mockTimesource = mock(GameTime.class);
        ServiceLocator.registerTimeSource(mockTimesource);

        cookIngredientComponent = new CookIngredientComponent();

//        when(mockEntity.getComponent(IngredientComponent.class)).thenReturn(mockIngredient);
//        when(mockEntity.getEvents()).thenReturn(mockEvents);
        mockEntity.addComponent(mockIngredient).addComponent(cookIngredientComponent);

//        cookIngredientComponent.setEntity(mockEntity);
//        cookIngredientComponent.create();
    }

    /**
     * Tests that cooking starts when the `cookIngredient` method is called with the "NORMAL"
     * station state and an oven multiplier of 1. The test verifies that the cooking process
     * is initiated by checking that `getIsCooking()` returns true.
     */
    @Test
    public void testCookingStarts() {
        when(mockTimesource.getTime()).thenReturn(1000L); // Simulate game time
        mockEntity.create();

        mockEntity.getEvents().trigger("cookIngredient");

        verify(mockTimesource).getTime();
        verify(mockIngredient).getCookTime();
        assertTrue(cookIngredientComponent.getIsCooking());
    }

    /**
     * Tests that the ingredient becomes cooked after the appropriate cooking time has passed.
     * The test simulates the passage of time and verifies that the `cookItem` method on the
     * ingredient component is called.
     */
    @Test
    public void testIngredientBecomesCooked() {
        when(mockTimesource.getTime()).thenReturn(1000L, 10000L); // Simulate passage of time
        when(mockIngredient.getCookTime()).thenReturn(1); // Setting some cook time: 1 seconds
        mockEntity.create();

        mockEntity.getEvents().trigger("cookIngredient");
        // After cookIngredientComponent.cookIngredient() gets called
        // cookEndTime = 1000L + 1 * 1000L * 1 = 2000

        cookIngredientComponent.update(); // This should trigger cooking
        // 10000L return from timesource > cookEndTime

        // ingredient is cooked
        verify(mockIngredient).cookItem();
    }

    /**
     * Tests that the cooking process stops when the `stopCookingIngredient` method is called.
     * The test verifies that `getIsCooking()` returns false after stopping the cooking process.
     */
    @Test
    public void testStopCooking() {
        mockEntity.create();
        mockEntity.getEvents().trigger("cookIngredient");
        assertTrue(cookIngredientComponent.getIsCooking());
        mockEntity.getEvents().trigger("stopCookingIngredient");
        assertFalse(cookIngredientComponent.getIsCooking());
    }

    /**
     * Tests that the ingredient becomes burnt if sufficient time has passed after cooking.
     * The test simulates the passage of time and verifies that the `burnItem` method on the
     * ingredient component is called and that the cooking process is stopped.
     */
    @Test
    public void testIngredientBecomesBurnt() {
        when(mockTimesource.getTime()).thenReturn(1000L, 20000L); // Simulate item being cooked and then overcooked
        when(mockIngredient.getCookTime()).thenReturn(1);
        mockEntity.create();

        mockEntity.getEvents().trigger("cookIngredient");
        // cookEndTime = 1000L + 1 * 1000L * 1 = 2000L = 2 seconds

        cookIngredientComponent.update();
        // This should trigger burning the item since
        // current_time = 20000L, i.e., 20 seconds
        // current_time - cookEndTime = 18 seconds
        // More than 15 seconds have passed since ingredient was cooked
        // So item is burnt

        verify(mockIngredient).burnItem();
        assertFalse(cookIngredientComponent.getIsCooking());
    }

    @Test
    public void testRageMode() {
        when(mockTimesource.getTime()).thenReturn(1000L, 6000L);
        when(mockIngredient.getCookTime()).thenReturn(10);
        mockEntity.create();

        ServiceLocator.getEntityService().getEvents().trigger("rageModeOn");

        mockEntity.getEvents().trigger("cookIngredient");
        
        cookIngredientComponent.update(); // This should trigger chopping

        // ingredient is chopped
        verify(mockIngredient).cookItem();

    }
}


