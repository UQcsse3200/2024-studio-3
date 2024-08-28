package com.csse3200.game.entities.configs;

import java.util.Map;

/**
 * Defines the properties stored in CookIngredient config files to be loaded by the Ingredient Factory.
 */
public class CookIngredientConfig {
    public Map<String, Integer> ingredientCookTimes;

    public int getCookTime(String ingredientName) {
        return ingredientCookTimes.get(ingredientName);
    }
}
