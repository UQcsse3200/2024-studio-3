package com.csse3200.game.components.ordersystem;

import com.csse3200.game.entities.configs.MultiStationRecipeConfig;
import com.csse3200.game.entities.configs.SingleStationRecipeConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeTest {

    private Recipe recipe;
    private SingleStationRecipeConfig singleStationRecipeConfig;
    private MultiStationRecipeConfig multiStationRecipeConfig;

    @BeforeEach
    void setUp() {
        singleStationRecipeConfig = mock(SingleStationRecipeConfig.class);
        multiStationRecipeConfig = mock(MultiStationRecipeConfig.class);

        createRecipe("banana");
    }

    void createRecipe(String name) {
        recipe = new Recipe(name) {
            @Override
            protected void loadRecipeDetails() {
                // Do Nothing
            }
        };
    }

    @Test
    void testGetName() {
        createRecipe("steak");
        assertEquals("steak", recipe.getName());
    }

    @Test
    void testGetBurnedTime() {
        recipe.setMultiStationRecipe(multiStationRecipeConfig);
        assertEquals(multiStationRecipeConfig.getBurnedTime(), recipe.getBurnedTime());
        recipe.setMultiStationRecipe(null);
        assertNull(recipe.getBurnedTime());
    }

    @Test
    void testIsValid() {
        // MultiStation Recipe is null and SingleStation Recipe is null
        recipe.setMultiStationRecipe(null);
        recipe.setSingleStationRecipe(null);
        assertFalse(recipe.isValid());


        recipe.setMultiStationRecipe(null);
        recipe.setSingleStationRecipe(singleStationRecipeConfig);
        assertTrue(recipe.isValid());


        recipe.setMultiStationRecipe(multiStationRecipeConfig);
        recipe.setSingleStationRecipe(null);
        assertTrue(recipe.isValid());

        recipe.setMultiStationRecipe(multiStationRecipeConfig);
        recipe.setSingleStationRecipe(singleStationRecipeConfig);
        assertTrue(recipe.isValid());
    }

    @Test
    void testGetIngredients() {
        // MultiStation Recipe is null and SingleStation Recipe is null
        recipe.setMultiStationRecipe(null);
        recipe.setSingleStationRecipe(null);
        assertEquals(Collections.emptyList(), recipe.getIngredients());


        recipe.setSingleStationRecipe(singleStationRecipeConfig);
        assertEquals(singleStationRecipeConfig.getIngredient(), recipe.getIngredients());


        recipe.setMultiStationRecipe(multiStationRecipeConfig);
        assertEquals(multiStationRecipeConfig.getIngredient(), recipe.getIngredients());
    }

    @Test
    void testGetMakingTime() {
        // MultiStation Recipe is null and SingleStation Recipe is null
        recipe.setMultiStationRecipe(null);
        recipe.setSingleStationRecipe(null);
        assertEquals(0, recipe.getMakingTime());


        recipe.setSingleStationRecipe(singleStationRecipeConfig);
        assertEquals(singleStationRecipeConfig.getMakingTime(), recipe.getMakingTime());


        recipe.setMultiStationRecipe(multiStationRecipeConfig);
        assertEquals(multiStationRecipeConfig.getMakingTime(), recipe.getMakingTime());

    }

    @Test
    void testGetStationTypeMultiStationNull() {
        recipe.setMultiStationRecipe(null);
        assertEquals("CUTTING_BOARD", recipe.getStationType());

    }

    @Test
    void testGetStationTypeFryingPanExists() {
        when(multiStationRecipeConfig.getFryingPan()).thenReturn(new ArrayList<>());
        recipe.setMultiStationRecipe(multiStationRecipeConfig);
        assertEquals("FRYING_PAN", recipe.getStationType());
    }

    @Test
    void testGetStationTypeOven() {
        when(multiStationRecipeConfig.getFryingPan()).thenReturn(null);
        when(multiStationRecipeConfig.getOven()).thenReturn(new ArrayList<>());
        recipe.setMultiStationRecipe(multiStationRecipeConfig);
        assertEquals("OVEN", recipe.getStationType());
    }

    @Test
    void testGetAndSetSingleStationRecipe() {
        recipe.setSingleStationRecipe(singleStationRecipeConfig);
        assertEquals(singleStationRecipeConfig, recipe.getSingleStationRecipe());
    }

    @Test
    void testGetAndSetMultiStationRecipe() {
        recipe.setMultiStationRecipe(multiStationRecipeConfig);
        assertEquals(multiStationRecipeConfig, recipe.getMultiStationRecipe());
    }
}
