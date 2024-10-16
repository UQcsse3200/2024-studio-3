package com.csse3200.game.components.items;

import com.csse3200.game.services.ServiceLocator;

import java.util.List;

public class MealComponent extends ItemComponent {
    private List<IngredientComponent> ingredients;
    private int quality;
    private int price;

    /**
     * Constructs a MealComponent with the specified attributes.
     * Initialises the list of ingredients, calculates the initial quality, and sets the price.
     *
     * @param mealName - a string which is the name of the meal
     * @param mealType - an ItemType which represents the type of the meal
     * @param weight - an integer representing the weight of the meal
     * @param ingredients - a list of IngredientComponent objects that make up the meal
     * @param price - an integer representing the price of the meal
     */
    public MealComponent(String mealName, ItemType mealType, int weight, List<IngredientComponent> ingredients, int price) {
        super(mealName, mealType, weight);
        this.ingredients = ingredients;
        this.quality = calculateQuality();
        this.price = price;
    }

    /**
     * Gets the list of ingredients that make up the meal.
     *
     * @return a List of IngredientComponent objects representing the ingredients in the meal
     */
    public List<IngredientComponent> getIngredients() {
        return ingredients;
    }

    /**
     * Sets the list of ingredients for the meal and recalculates the meal's new quality.
     *
     * @param ingredients - a List of IngredientComponent objects to set as the meal's ingredients
     */
    public void setIngredients(List<IngredientComponent> ingredients) {
        this.ingredients = ingredients;
        this.quality = calculateQuality();
    }

    /**
     * Adds an ingredient to the meal and recalculates the meal's new quality.
     *
     * @param ingredient - an IngredientComponent object to add to the meal
     */
    public void addIngredient(IngredientComponent ingredient) {
        this.ingredients.add(ingredient);
        this.quality = calculateQuality();
    }

    /**
     * Deletes an ingredient from the meal and recalculates the meal's new quality.
     *
     * @param ingredient - an IngredientComponent object to remove from the meal
     */
    public void deleteIngredient(IngredientComponent ingredient) {
        this.ingredients.remove(ingredient);
        this.quality = calculateQuality();
    }

    /**
     * Calculates the quality of the meal based on the state of its ingredients.
     * The quality score starts at 100 and penalties are applied based on the number of ingredients
     * that are either "burnt" or "raw" (except for ingredients of type LETTUCE, which are expected to be
     * raw and therefore draw no penalty).
     *
     * @return an integer representing the calculated quality of the meal
     */
    private int calculateQuality() {

        // If there are no ingredients in the list, the quality is set to 0
        if (ingredients.isEmpty()) {
            return 0;
        }

        // Quality score initialised to the maximum value
        int qualityScore = 100;
        int penalty;
        int penaltyCount = 0;

        // Iterating through each ingredient to determine number of penalties
        for (IngredientComponent ingredient : ingredients) {
            String itemState = ingredient.getItemState();

            // Increases number of penalties if the ingredient is "burnt" or "raw" (excluding LETTUCE, which is
            // allowed to be "raw")
            if (itemState.equals("burnt")
                    || (ingredient.getItemType() != ItemType.LETTUCE && itemState.equals("raw"))) {
                penaltyCount += 1;
            }
        }

        // Calculates the penalty score as a percentage of the total number of ingredients
        penalty = (int) Math.round(((double) penaltyCount / ingredients.size()) * 100);
        int finalQuality = qualityScore - penalty;

        // Trigger event for 100% quality meal
        if (finalQuality == 100 && ServiceLocator.getEntityService() != null) {
            ServiceLocator.getEntityService().getEvents().trigger("mealHighQuality");
        }

        return finalQuality;
    }

    /**
     * Gets the quality of the meal.
     *
     * @return an integer representing the quality of the meal
     */
    public int getQuality() {
        return quality;
    }

    /**
     * Sets the price of the meal.
     *
     * @param price - an integer representing the new price of the meal
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets the price of the meal.
     *
     * @return an integer representing the price of the meal
     */
    public int getPrice() {
        return price;
    }
}
