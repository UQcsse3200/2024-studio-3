package com.csse3200.game.components.ordersystem;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class OrderManager {
    private static final Logger logger = LoggerFactory.getLogger(OrderManager.class);
    static Map<String, Recipe> recipes = new HashMap<>();

    static {
        try {
            loadRecipes();
        } catch (Exception e) {
            logger.error("Failed to load recipes", e);
        }
    }

    private OrderManager() {
        super();
    }

    public static void loadRecipes() {
        // List of recipe names to load
        String[] recipeNames = {"acaiBowl", "salad", "fruitSalad", "steakMeal", "bananaSplit"};

        for (String recipeName : recipeNames) {
            Recipe recipe = new Recipe(recipeName);
            if (recipe.isValid()) {
                recipes.put(recipeName, recipe);
            } else {
                logger.error("Failed to load recipe: {}", recipeName);
            }
        }

        logger.info("Recipes loaded: {}", recipes.keySet());
    }

    public static Recipe getRecipe(String recipeName) {
        return recipes.get(recipeName);
    }

    public static void displayOrder(Entity customer) {
        if (customer == null) {
            logger.error("Customer entity is null. Cannot display order.");
            return;
        }

        CustomerComponent customerComponent = customer.getComponent(CustomerComponent.class);
        if (customerComponent == null) {
            logger.error("CustomerComponent is null. Cannot display order.");
            return;
        }

        String preference = customerComponent.getPreference();
        if (preference == null || preference.isEmpty()) {
            logger.error("Customer preference is null or empty. Cannot display order.");
            return;
        }

        Recipe recipe = getRecipe(preference);
        if (recipe != null) {
            logger.info("Displaying order for preference: {}", preference);
            logger.info("Ingredients: {}", recipe.getIngredients());
            logger.info("Making Time: {}", recipe.getMakingTime());
        }
        switch (customerComponent.getPreference()){
            case "acaiBowl" -> ServiceLocator.getEntityService().getEvents().trigger("createAcaiDocket");
            case "salad" -> ServiceLocator.getEntityService().getEvents().trigger("createSaladDocket");
            case "fruitSalad" -> ServiceLocator.getEntityService().getEvents().trigger("createFruitSaladDocket");
            case "steakMeal" -> ServiceLocator.getEntityService().getEvents().trigger("createSteakDocket");
            case "bananaSplit" -> ServiceLocator.getEntityService().getEvents().trigger("createBananaDocket");
            default -> logger.error("No recipe found for preference: {}", preference);
        }
    }
}
