package com.csse3200.game.entities.factories;

import com.csse3200.game.entities.configs.CookingConfig;
import com.csse3200.game.entities.configs.SingleStationRecipeConfig;
import com.csse3200.game.entities.configs.MultiStationRecipeConfig;
import com.csse3200.game.files.FileLoader;

import java.util.*;

/**
 * Factory to create a cooking entity with predefined components.
 * Predefined recipe properties are loaded from a config stored as a json file and should have
 * the properties stores in 'CookingConfig'.
 */
public class DishFactory {
    // read in recipe configs
    private static final CookingConfig configs =
            FileLoader.readClass(CookingConfig.class, "configs/recipe.json");
    private static final Map<String, SingleStationRecipeConfig> singleStationRecipes =
            new HashMap<>();    // all single-station recipes
    private static final Map<String, MultiStationRecipeConfig> multiStationRecipes =
            new HashMap<>();      // all multi-station recipes
    private static final Map<String, SingleStationRecipeConfig> recipes = new HashMap<>();// all recipes

    /**
     * Constructor for the DishFactory class, no parameters required.
     */
    public DishFactory() {
        // populate single-station recipes
        DishFactory.singleStationRecipes.put("salad", configs.salad);
        DishFactory.singleStationRecipes.put("fruitSalad", configs.fruitSalad);
        DishFactory.singleStationRecipes.put("bananaSplit", configs.bananaSplit);

        // populate multi-station recipes
        DishFactory.multiStationRecipes.put("steakMeal", configs.steakMeal);
        DishFactory.multiStationRecipes.put("acaiBowl", configs.acaiBowl);

        // generate all recipes
        this.generateAllRecipes();
    }

    /**
     * Store the recipe and associated ingredients to the dictionary
     * 
     * @return - dictionary that only need one station when making the meal
     */
    private static Map<String, SingleStationRecipeConfig> getSingleStationRecipes() {
        return singleStationRecipes;
    }

    /**
     * Store the recipe and associated ingredients to the dictionary
     * 
     * @return - dictionary of recipes that need more than one station when making the meal
     */
    private static Map<String, MultiStationRecipeConfig> getMultiStationRecipes() {
        return multiStationRecipes;
    }

    /**
     * Get the list of possible recipes for associated ingredients
     * 
     * @param ingredients - needed to make the dish (specify in the recipe.json)
     * @return - list of recipes that contain associated ingredients
     */
    public static List<String> getPossibleRecipes(List<String> ingredients) {
        List<String> recipes = new ArrayList<>();

        if (configs == null) {
            return recipes;
        }

        for (Map.Entry<String, SingleStationRecipeConfig> entry : getSingleStationRecipes().entrySet()) {
            String recipe = entry.getKey();
            SingleStationRecipeConfig recipeConfig = entry.getValue();

            if (!recipeConfig.ingredient.isEmpty() &&
                    new HashSet<>(recipeConfig.ingredient).containsAll(ingredients)) {
                recipes.add(recipe);
            }
        }

        for (Map.Entry<String, MultiStationRecipeConfig> entry : getMultiStationRecipes().entrySet()) {
            String recipe = entry.getKey();
            MultiStationRecipeConfig recipeConfig = entry.getValue();

            if (!recipeConfig.ingredient.isEmpty() &&
                    new HashSet<>(recipeConfig.ingredient).containsAll(ingredients)) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    /**
     * Gets a recipe if and only if it matches exactly the list of ingredients
     * 
     * @param ingredients - needed to make the dish (specify in the recipe.json
     * @return - name of recipe that contain associated ingredients
     */
    public static Optional<String> getDefinitiveRecipe(List<String> ingredients) {
        if (!ingredients.isEmpty()) {
            for (Map.Entry<String, SingleStationRecipeConfig> entry : getSingleStationRecipes().entrySet()) {
                String recipe = entry.getKey();
                SingleStationRecipeConfig recipeConfig = entry.getValue();

                if (new HashSet<>(recipeConfig.ingredient).containsAll(ingredients)
                        && recipeConfig.ingredient.size() == ingredients.size()) {
                    return Optional.of(recipe);
                }
            }

            for (Map.Entry<String, MultiStationRecipeConfig> entry : getMultiStationRecipes().entrySet()) {
                String recipe = entry.getKey();
                MultiStationRecipeConfig recipeConfig = entry.getValue();

                if (new HashSet<>(recipeConfig.ingredient).containsAll(ingredients)
                        && recipeConfig.ingredient.size() == ingredients.size()) {
                    return Optional.of(recipe);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Returns the single station recipe config for the given recipe name.
     * 
     * @param recipeName - the recipe config searched for. 
     * @return - the SingleStationRecipeConfig for the given recipe name.
     */
    public static SingleStationRecipeConfig getSingleStationRecipe(String recipeName) {
        return getSingleStationRecipes().get(recipeName);
    }

    /**
     * Returns the multi station recipe config for the given recipe name.
     * 
     * @param recipeName - the recipe config searched for.
     * @return - the MultiStationRecipeConfig for the given recipe name.
     */
    public static MultiStationRecipeConfig getMultiStationRecipe(String recipeName) {
        return getMultiStationRecipes().get(recipeName);
    }

    /**
     * Checks if the provided recipe name is a valid recipe as defined in the recipe.json.
     * 
     * @param recipeName - the recipe searched for.
     * @return - true if the recipe is valid, false otherwise.
     */
    public static boolean recipeExists(String recipeName) {
        return getSingleStationRecipes().containsKey(recipeName) ||
          getMultiStationRecipes().containsKey(recipeName);
    }

    /**
     * Populates all possible recipes from the dish factory configs.
     */
    private void generateAllRecipes() {
        recipes.putAll(getSingleStationRecipes());

        recipes.putAll(getMultiStationRecipes());
    }

    /**
     * Get all the recipes from the dish factory
     * @return the recipes from the dish factory
     */
    public Map<String, SingleStationRecipeConfig> getAllRecipes() {
        return recipes;
    }

    /**
     * get the recipe from a list of ingredients
     * @param ingredients: The list of ingredients in the dish
     * @return The recipe made from the ingredients
     */
    public Optional<String> getRealRecipe(List<String> ingredients) {
        for (Map.Entry<String, SingleStationRecipeConfig> entry : getAllRecipes().entrySet()) {
            List<String> recipeIngredients = entry.getValue().getIngredient();
            if (new HashSet<>(recipeIngredients).equals(new HashSet<>(ingredients))) {
                return Optional.of(entry.getKey());
            }
        }

        return Optional.empty();
    }

}

//    /**
//     * Get the station type for a given recipe
//     * @param recipeName the name of the recipe
//     * @return list of station types required for the recipe
//     */
//    public static List<String> getStationTypes(String recipeName) {
//        List<String> stationTypes = new ArrayList<>();
//        SingleStationRecipeConfig singleStationRecipe = getSingleStationRecipes().get(recipeName);
//        MultiStationRecipeConfig multiStationRecipe = getMultiStationRecipes().get(recipeName);
//
//        if (singleStationRecipe != null) {
//            stationTypes.addAll(singleStationRecipe.getStationTypes());
//        } else if (multiStationRecipe != null) {
//            stationTypes.addAll(multiStationRecipe.getStationTypes());
//        }
//        return stationTypes;
//    }
//    /**
//     * Get the making time for a given recipe
//     * @param recipeName the name of the recipe
//     * @return making time for the recipe
//     */
//    public static int getMakingTime(String recipeName) {
//        SingleStationRecipeConfig singleStationRecipe = getSingleStationRecipes().get(recipeName);
//        MultiStationRecipeConfig multiStationRecipe = getMultiStationRecipes().get(recipeName);
//
//        if (singleStationRecipe != null) {
//            return singleStationRecipe.makingTime;
//        } else if (multiStationRecipe != null) {
//            return multiStationRecipe.makingTime;
//        }
//        return 0;
//    }
//
//    /**
//     * Get the burned time for a given recipe, if applicable
//     * @param recipeName the name of the recipe
//     * @return burned time for the recipe, or -1 if not applicable
//     */
//    public static int getBurnedTime(String recipeName) {
//        MultiStationRecipeConfig multiStationRecipe = getMultiStationRecipes().get(recipeName);
//
//        if (multiStationRecipe != null && multiStationRecipe.burnedTime != null) {
//            return multiStationRecipe.burnedTime;
//        }
//        return -1; // Return -1 if there's no burned time
//    }
//

    /**
     * Get the recipe for associated ingredients
     *
     * @param ingredient - needed to make the dish (specify in the recipe.json)
     * @return - list of recipes that contain associated ingredients

    public static List<String> getRecipe(List<String> ingredient) {
        List<String> recipes = new ArrayList<>();

        if (this.configs == null) {
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
        */