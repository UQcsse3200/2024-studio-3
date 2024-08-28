package com.csse3200.game.components.items;

import com.csse3200.game.events.EventHandler;
import com.csse3200.game.entities.Entity;
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
    private EventHandler mockEvents;

    @BeforeEach
    public void setUp() {
        mockEntity = mock(Entity.class);
        mockEvents = mock(EventHandler.class);
        mockIngredient = mock(IngredientComponent.class);
        mockTimesource = mock(GameTime.class);
        ServiceLocator.registerTimeSource(mockTimesource);

        cookIngredientComponent = new CookIngredientComponent();

        when(mockEntity.getComponent(IngredientComponent.class)).thenReturn(mockIngredient);
        when(mockEntity.getEvents()).thenReturn(mockEvents);

        cookIngredientComponent.setEntity(mockEntity);
        cookIngredientComponent.create();
    }

    @Test
    public void testCookingStarts() {
        when(mockTimesource.getTime()).thenReturn(1000L); // Simulate game time

        cookIngredientComponent.cookIngredient("NORMAL", 1);

        verify(mockTimesource).getTime();
        assertTrue(cookIngredientComponent.getIsCooking());
    }

    @Test
    public void testIngredientBecomesCooked() {
        when(mockTimesource.getTime()).thenReturn(1000L, 2000L); // Simulate passage of time
        cookIngredientComponent.cookIngredient("NORMAL", 1);
        cookIngredientComponent.update(); // This should trigger cooking
        verify(mockIngredient).cookItem();
    }

    @Test
    public void testStopCooking() {
        cookIngredientComponent.cookIngredient("NORMAL", 1);
        cookIngredientComponent.stopCookingIngredient();
        assertFalse(cookIngredientComponent.getIsCooking());
    }

    @Test
    public void testIngredientBecomesBurnt() {
        when(mockTimesource.getTime()).thenReturn(1000L, 16000L); // Simulate item being cooked and then overcooked
        cookIngredientComponent.cookIngredient("NORMAL", 1);
        cookIngredientComponent.update(); // This should trigger burning the item
        verify(mockIngredient).burnItem();
        assertFalse(cookIngredientComponent.getIsCooking());
    }
}


