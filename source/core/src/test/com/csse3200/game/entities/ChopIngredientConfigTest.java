package com.csse3200.game.entities.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ChopIngredientConfigTest {

    private ChopIngredientConfig chopIngredientConfig;

    @BeforeEach
    public void setUp() {
        // Initialize a new ChopIngredientConfig instance and set up ingredientChopTimes
        chopIngredientConfig = new ChopIngredientConfig();
        chopIngredientConfig.ingredientChopTimes = new HashMap<>();
        chopIngredientConfig.ingredientChopTimes.put("Carrot", 5);
        chopIngredientConfig.ingredientChopTimes.put("Potato", 3);
    }

    @Test
    public void testGetCookTimeForValidIngredient() {
        // Verify that the getCookTime method returns the correct chop time for a valid ingredient
        int chopTime = chopIngredientConfig.getCookTime("Carrot");
        assertEquals(5, chopTime, "The chop time for Carrot should be 5");

        chopTime = chopIngredientConfig.getCookTime("Potato");
        assertEquals(3, chopTime, "The chop time for Potato should be 3");
    }

    @Test
    public void testGetCookTimeForInvalidIngredient() {
        // Verify that the getCookTime method throws an exception when the ingredient is not in the map
        Exception exception = assertThrows(NullPointerException.class, () -> {
            chopIngredientConfig.getCookTime("Onion");
        });

        String expectedMessage = "null"; // This indicates that the ingredient is not in the map
        assertTrue(exception.getMessage().contains(expectedMessage), "Expected NullPointerException for missing ingredient");
    }
}
