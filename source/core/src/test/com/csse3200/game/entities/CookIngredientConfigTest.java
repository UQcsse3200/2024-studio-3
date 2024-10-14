package com.csse3200.game.entities.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CookIngredientConfigTest {

    private CookIngredientConfig cookIngredientConfig;

    @BeforeEach
    public void setUp() {
        // Initialize a new CookIngredientConfig instance and set up ingredientCookTimes
        cookIngredientConfig = new CookIngredientConfig();
        cookIngredientConfig.ingredientCookTimes = new HashMap<>();
        cookIngredientConfig.ingredientCookTimes.put("Steak", 10);
        cookIngredientConfig.ingredientCookTimes.put("Chicken", 7);
    }

    @Test
    public void testGetCookTimeForValidIngredient() {
        // Verify that getCookTime returns the correct cook time for a valid ingredient
        int cookTime = cookIngredientConfig.getCookTime("Steak");
        assertEquals(10, cookTime, "The cook time for Steak should be 10");

        cookTime = cookIngredientConfig.getCookTime("Chicken");
        assertEquals(7, cookTime, "The cook time for Chicken should be 7");
    }

    @Test
    public void testGetCookTimeForInvalidIngredient() {
        // Verify that getCookTime throws a NullPointerException for a missing ingredient
        Exception exception = assertThrows(NullPointerException.class, () -> {
            cookIngredientConfig.getCookTime("Fish");
        });

        String expectedMessage = "null"; // Expecting a NullPointerException for missing ingredient
        assertTrue(exception.getMessage().contains(expectedMessage), "Expected NullPointerException for missing ingredient");
    }
}
