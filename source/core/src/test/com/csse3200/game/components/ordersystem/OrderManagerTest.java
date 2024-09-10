/*package com.csse3200.game.components.ordersystem;

import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderManagerTest {

    @Mock
    private Entity customer;

    @Mock
    private CustomerComponent customerComponent;

    @InjectMocks
    private static OrderManager orderManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Reset recipes to ensure tests do not interfere with each other
        OrderManager.recipes = new HashMap<>();
    }

    @Test
    void testLoadRecipes() {
        // Mock FileLoader to return a sample map
        Map<String, Recipe> sampleRecipes = new HashMap<>();
        sampleRecipes.put("TestRecipe", new Recipe("TestRecipe", "TestIngredients", 10));

        try {
            when(FileLoader.readClass(Map.class, "configs/recipe.json")).thenReturn(sampleRecipes);
            Map<String, Recipe> recipes = OrderManager.loadRecipes();
            assertEquals(sampleRecipes, recipes, "Loaded recipes should match the sample recipes");
        } catch (Exception e) {
            fail("Exception should not be thrown during recipe loading");
        }
    }

    @Test
    void testGetRecipe() {
        Recipe sampleRecipe = new Recipe("TestRecipe", "TestIngredients", 10);
        OrderManager.recipes.put("TestRecipe", sampleRecipe);

        Recipe recipe = OrderManager.getRecipe("TestRecipe");
        assertNotNull(recipe, "Recipe should not be null");
        assertEquals(sampleRecipe, recipe, "Retrieved recipe should match the expected recipe");
    }

    @Test
    void testDisplayOrder_ValidCustomer() {
        Recipe sampleRecipe = new Recipe("TestRecipe", "TestIngredients", 10);
        OrderManager.recipes.put("TestRecipe", sampleRecipe);

        when(customer.getComponent(CustomerComponent.class)).thenReturn(customerComponent);
        when(customerComponent.getPreference()).thenReturn("TestRecipe");

        OrderManager.displayOrder(customer);

        verify(customerComponent, times(1)).getPreference();
        // Optionally verify logs or UI logic if applicable
    }

    @Test
    void testDisplayOrder_NullCustomer() {
        OrderManager.displayOrder(null);
        // Verify log message or behavior if necessary
    }

    @Test
    void testDisplayOrder_NoCustomerComponent() {
        when(customer.getComponent(CustomerComponent.class)).thenReturn(null);
        OrderManager.displayOrder(customer);
        // Verify log message or behavior if necessary
    }

    @Test
    void testDisplayOrder_NoPreference() {
        when(customer.getComponent(CustomerComponent.class)).thenReturn(customerComponent);
        when(customerComponent.getPreference()).thenReturn(null);
        OrderManager.displayOrder(customer);
        // Verify log message or behavior if necessary
    }

    @Test
    void testDisplayOrder_InvalidRecipe() {
        when(customer.getComponent(CustomerComponent.class)).thenReturn(customerComponent);
        when(customerComponent.getPreference()).thenReturn("InvalidRecipe");

        OrderManager.displayOrder(customer);

        // Verify log message or behavior if necessary
    }
}
*/