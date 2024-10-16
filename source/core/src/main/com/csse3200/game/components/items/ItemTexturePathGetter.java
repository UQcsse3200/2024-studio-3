package com.csse3200.game.components.items;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csse3200.game.entities.Entity;

/**
 * ItemTexturePathGetter is a static class whose only purpose is to return the 
 * correct texture path of an item based on its type and depending on its state
 * will return the correct picture.
 * 
 * Note that this class could potentially return a string refers to an image path
 * that might not exist, or the image might not be loaded. In these instances
 * if a call is made to get the asset, a GdxRuntimeException will be thrown
 * resulting in a game crash. So please keep this in mind
 */
public class ItemTexturePathGetter {

    // A logger for the class
    private static final Logger logger = LoggerFactory.getLogger(ItemTexturePathGetter.class);

    // The formate strings used to get the full image name
    private static final String ingredientFormat = "images/ingredients/%s_%s.png";
    private static final String mealFormat = "images/meals/%s.png";
    private static final String plateFormat = "images/platecomponent/cleanplate.png";
    
    /**
     * Static function to return the texture path based on the entity and
     * potentially the state of the entity.
     * @param entity the food entity to get the texture for
     * @return the path to the texture if entity is an item, null otherwise
     */
    public static String getTexturePath(Entity entity) {
        // Determine what type of item this is
        if (entity.getComponent(IngredientComponent.class) != null) {
            return getTexturePathIngredient(entity.getComponent(IngredientComponent.class));
        } else if (entity.getComponent(MealComponent.class) != null) {
            return getTexturePathMeal(entity.getComponent(MealComponent.class));
        } else if (entity.getComponent(PlateComponent.class) != null) {
            return getTexturePathPlate();
        } else {
            return null;
        }
    }

    /**
     * Static function to return the texture path based on the entity if it is
     * an ingredient
     * @param entity the ingredient to get the texture for
     * @return the path to the texture if entity is an item, null otherwise
     */
    private static String getTexturePathIngredient(IngredientComponent ingredient) {
        // Get the item and it's state
        String item = ingredient.getItemName().toLowerCase();
        String state = ingredient.getItemState().toLowerCase();
        
        //logger.info("The texture of the item is:");
        //logger.info(String.format(ingredientFormat, state, item));

        // Return a format string
        return String.format(ingredientFormat, state, item);
    }

    /**
     * Static function to return the texture path based on the entity if it is
     * a meal
     * @param entity the ingredient to get the texture for
     * @return the path to the texture if entity is an item, null otherwise
     */
    private static String getTexturePathMeal(MealComponent meal) {
        // Get the item
        String item = meal.getItemName().toLowerCase();

        // string for the meal name image
        String imageName = switch(item) {
            case "fruit salad" -> "fruit_salad";
            case "acai bowl" -> "acai_bowl";
            case "salad" -> "salad";
            case "steak meal" -> "steak_meal";
            case "banana split" -> "banana_split";
            default -> null;
        };

        // if name is null return null
        if (imageName == null) {
            logger.info("Meal texture not found");
            return null;
        }

        //logger.info("The texture of the item is:");
        //logger.info(String.format(mealFormat, imageName));

        return String.format(mealFormat, imageName);
    }

    /**
     * Static function to return the plate texture path. 
     * 
     * @return the path to the clean plate image.
     */ 
    private static String getTexturePathPlate() {

        //logger.info("The texture of the item is:");
        //logger.info(plateFormat);

        return plateFormat;
    }

}
