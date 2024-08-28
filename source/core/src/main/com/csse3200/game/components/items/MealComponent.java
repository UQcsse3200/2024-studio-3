package com.csse3200.game.components.items;

import java.util.List;

public class MealComponent extends ItemComponent {
    private List<IngredientComponent> ingredients;
    private int quality;
    private int price;

    public MealComponent(String mealName, ItemType mealType, int weight, List<IngredientComponent> ingredients,
                         int price) {
        super(mealName, mealType, weight);
        this.ingredients = ingredients;
        this.quality = calculateQuality();
        this.price = price;

    }

    public List<IngredientComponent> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientComponent> ingredients) {
        this.ingredients = ingredients;
        this.quality = calculateQuality();
    }

    public void addIngredient(IngredientComponent ingredient) {
        this.ingredients.add(ingredient);
        this.quality = calculateQuality();
    }

    public void deleteIngredient(IngredientComponent ingredient) {
        this.ingredients.remove(ingredient);
        this.quality = calculateQuality();
    }

    private int calculateQuality() {

        if (ingredients.isEmpty()) {
            return 0;
        }

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

    public int getQuality() {
        return quality;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
