package com.csse3200.game.components.items;

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

    // The formate strings used to get the full image name
    private static final String ingredientFormat = "images/ingredients/%s_%s.png";
    private static final String mealFormat = "images/meals/%s.png";
    
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
        String item = meal.getItemName();

        // string for the meal name image
        String imageName = switch(item) {
            case "Fruit Salad" -> "fruit_salad";
            case "Acai bowl" -> "acai_bowl";
            case "Salad" -> "salad";
            case "Steak Meal" -> "steak_meal";
            case "Banana Split" -> "banana_split";
            default -> null;
        };

        // if name is null return null
        if (imageName == null) {
            return null;
        }

        return String.format(mealFormat, imageName);
    }

}
