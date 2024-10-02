package com.csse3200.game.components.ordersystem;


import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import com.csse3200.game.files.FileLoader;

/**
 * Unit tests for the MainGameOrderTicketDisplay class.
 */
@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class OrderManagerTest {

    private EventHandler eventHandler;
    private Entity customer;
    private CustomerComponent customerComponent;
    private Recipe recipe;

    @Before
    void setup() {
        // Mock FileLoader to return a valid recipes map when OrderManager tries to load recipes
        Map<String, Recipe> mockRecipes = new HashMap<>();
        Recipe bananaSplitRecipe = mock(Recipe.class);
        when(bananaSplitRecipe.getName()).thenReturn("bananaSplit");
        mockRecipes.put("bananaSplit", bananaSplitRecipe);

        // Use Mockito to mock the FileLoader static method
        mockStatic(FileLoader.class);
        when(FileLoader.readClass(eq(Map.class), eq("configs/recipe.json"))).thenReturn(mockRecipes);

        // Load recipes from the mocked FileLoader
        OrderManager.loadRecipes();
    }

    @Test
    void getRecipeTest() {
        setup();
        // Now, the "bananaSplit" recipe should be available due to the mocked FileLoader
        Recipe recipe = OrderManager.getRecipe("bananaSplit");

        assertNotNull(recipe, "Recipe should not be null");
        assertEquals("bananaSplit", recipe.getName(), "Recipe name should be bananaSplit");
    }
}
