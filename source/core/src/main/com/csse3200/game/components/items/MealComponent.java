package com.csse3200.game.components.items;

import java.util.List;

public class MealComponent extends ItemComponent {
    private List<IngredientComponent> ingredients;
    private int quality;
    private int price;
    private String mealType;

    public MealComponent(String itemName, ItemType itemType, int weight, List<IngredientComponent> ingredients,
                         int price) {
        super(itemName, itemType, weight);
        this.ingredients = ingredients;
        this.quality = calculateQuality();
        this.price = price;
        this.mealType = determineMealType();

    }

    public List<IngredientComponent> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientComponent> ingredients) {
        this.ingredients = ingredients;
        this.mealType = determineMealType();
    }

    public void addIngredient(IngredientComponent ingredient) {
        this.ingredients.add(ingredient);
        this.mealType = determineMealType();
    }

    private int calculateQuality() {
        int qualityScore = 100;
        int penalty;
        int penaltyCount = 0;

        for (IngredientComponent ingredient : ingredients) {
            String itemState = ingredient.getItemState();

            if (itemState.equals("burnt") || itemState.equals("raw")) {
                penaltyCount += 1;
            }
        }

        penalty = (penaltyCount / ingredients.size()) * 100;

        return qualityScore - penalty;
    }

    private String determineMealType() {
        return "SomeMealType";
    }

    public String getMealType() {
        return mealType;
    }

    public int getQuality() {
        return quality;
    }
}
