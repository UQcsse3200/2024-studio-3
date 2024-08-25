
package com.csse3200.game.entities.factories;


import com.csse3200.game.entities.configs.CookingConfig;
import com.csse3200.game.entities.configs.SingleStationRecipeConfig;
import com.csse3200.game.entities.configs.MultiStationRecipeConfig;


import com.csse3200.game.files.FileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory to create a cooking entity with predefined components.
 *
 * <p>Predefined recipe properties are loaded from a config stored as a json file and should have
 * the properties stores in 'CookingConfig'.
 */
public class DishFactory {
    private static final CookingConfig configs =
            FileLoader.readClass(CookingConfig.class, "configs/recipe.json");

    private static Map<String, SingleStationRecipeConfig> getSingleStationRecipes() {
        Map<String, SingleStationRecipeConfig> singleStationRecipes = new HashMap<>();
        singleStationRecipes.put("acaiBowl", configs.acaiBowl);
        singleStationRecipes.put("salad", configs.salad);
        singleStationRecipes.put("fruitSalad", configs.fruitSalad);
        singleStationRecipes.put("bananaSplit", configs.bananaSplit);
        return singleStationRecipes;
    }

    private static Map<String, MultiStationRecipeConfig> getMultiStationRecipes() {
        Map<String, MultiStationRecipeConfig> multiStationRecipes = new HashMap<>();
        multiStationRecipes.put("steakMeal", configs.steakMeal);
        return multiStationRecipes;
    }

    public static List<String> getRecipe (String ingredient) {
        List<String> recipes = new ArrayList<>();

        if (configs == null) {
            return recipes;
        }

        for (Map.Entry<String, SingleStationRecipeConfig> entry : getSingleStationRecipes().entrySet()) {
            String recipe = entry.getKey();
            SingleStationRecipeConfig recipeConfig = entry.getValue();

            if (recipeConfig.ingredient.contains(ingredient)) {
                recipes.add(recipe);
            }
        }

        for (Map.Entry<String, MultiStationRecipeConfig> entry : getMultiStationRecipes().entrySet()) {
            String recipe = entry.getKey();
            MultiStationRecipeConfig recipeConfig = entry.getValue();

            if (recipeConfig.ingredient.contains(ingredient)) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }
}