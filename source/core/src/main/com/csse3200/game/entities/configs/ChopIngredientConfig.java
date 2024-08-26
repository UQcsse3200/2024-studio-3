package com.csse3200.game.entities.configs;

import java.util.Map;

/**
 * Defines the properties stored in ChopIngredient config files to be loaded by the Ingredient Factory.
 */
public class ChopIngredientConfig {
    public Map<String, Integer> ingredientChopTimes;

    public int getCookTime(String ingredientName) {
        return ingredientChopTimes.get(ingredientName);
    }
}
