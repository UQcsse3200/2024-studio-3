package com.csse3200.game.components.ordersystem;

import com.csse3200.game.files.FileLoader;
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
            recipes = loadRecipes();
        } catch (Exception e) {
            logger.error("Failed to load recipes", e);
        }
    }

    static Map<String, Recipe> loadRecipes() {
        Map<String, Recipe> loadedRecipes = FileLoader.readClass(Map.class, "configs/recipe.json");
        if (loadedRecipes == null) {
            logger.error("Loaded recipes map is null. Initializing with empty map.");
            loadedRecipes = new HashMap<>();
        }
        return loadedRecipes;
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

        ServiceLocator.getEntityService().getEvents().trigger("createOrder",preference);
        ServiceLocator.getEntityService().getEvents().trigger("createAcaiDocket");
        ServiceLocator.getEntityService().getEvents().trigger("createBananaDocket");
        ServiceLocator.getEntityService().getEvents().trigger("createSaladDocket");
        ServiceLocator.getEntityService().getEvents().trigger("createSteakDocket");
        ServiceLocator.getEntityService().getEvents().trigger("createFruitSaladDocket");

        Recipe recipe = getRecipe(preference);
        if (recipe != null) {
            logger.info("Displaying order for preference: " + preference);
            logger.info("Ingredients: " + recipe.getIngredients());
            logger.info("Making Time: " + recipe.getMakingTime());
            // Add more UI logic here, if needed
        } else {
            logger.error("No recipe found for preference: " + preference);
            // Handle case where no recipe is found
            // e.g., show a default message or placeholder
        }
    }
}
