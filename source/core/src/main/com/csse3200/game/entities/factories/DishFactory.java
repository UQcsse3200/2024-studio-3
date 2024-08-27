
package com.csse3200.game.entities.factories;


import com.csse3200.game.entities.configs.CookingConfig;
import com.csse3200.game.entities.configs.SingleStationRecipeConfig;
import com.csse3200.game.entities.configs.MultiStationRecipeConfig;


import com.csse3200.game.files.FileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Factory to create a cooking entity with predefined components.
 *
 * <p>Predefined recipe properties are loaded from a config stored as a json file and should have
 * the properties stores in 'CookingConfig'.
 */
public class DishFactory {
    private static final CookingConfig configs =
            FileLoader.readClass(CookingConfig.class, "configs/recipe.json");

    /**
     * Store the recipe and associated ingredients to the dictionary
     * @return dictionary that only need one station when making the meal
     */
    private static Map<String, SingleStationRecipeConfig> getSingleStationRecipes() {
        Map<String, SingleStationRecipeConfig> singleStationRecipes = new HashMap<>();
        singleStationRecipes.put("acaiBowl", configs.acaiBowl);
        singleStationRecipes.put("salad", configs.salad);
        singleStationRecipes.put("fruitSalad", configs.fruitSalad);
        singleStationRecipes.put("bananaSplit", configs.bananaSplit);
        return singleStationRecipes;
    }

    /**
     * Store the recipe and associated ingredients to the dictionary
     * @return dictionary of recipes that need more than one station when making the meal
     */
    private static Map<String, MultiStationRecipeConfig> getMultiStationRecipes() {
        Map<String, MultiStationRecipeConfig> multiStationRecipes = new HashMap<>();
        multiStationRecipes.put("steakMeal", configs.steakMeal);
        return multiStationRecipes;
    }

    /**
    Get the recipe for associated ingredients

     @param ingredient needed to make the dish (specify in the recipe.json)
     @return list of recipes that contain associated ingredients
     */
    public static List<String> getRecipe (List<String> ingredient) {
        List<String> recipes = new ArrayList<>();

        if (configs == null) {
            return recipes;
        }

        for (Map.Entry<String, SingleStationRecipeConfig> entry : getSingleStationRecipes().entrySet()) {
            String recipe = entry.getKey();
            SingleStationRecipeConfig recipeConfig = entry.getValue();

            if (new HashSet<>(recipeConfig.ingredient).containsAll(ingredient)) {
                recipes.add(recipe);
            }
        }

        for (Map.Entry<String, MultiStationRecipeConfig> entry : getMultiStationRecipes().entrySet()) {
            String recipe = entry.getKey();
            MultiStationRecipeConfig recipeConfig = entry.getValue();

            if (new HashSet<>(recipeConfig.ingredient).containsAll(ingredient)) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }
}