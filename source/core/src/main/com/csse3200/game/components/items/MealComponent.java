package com.csse3200.game.components.items;

import java.util.List;

public class MealComponent extends ItemComponent {
    private List<IngredientComponent> ingredients;
    private int quality;
    private int price;

    public MealComponent(String itemName, ItemType itemType, int weight, List<IngredientComponent> ingredients,
                         int price) {
        super(itemName, itemType, weight);
        this.ingredients = ingredients;
        this.quality = calculateQuality();
        this.price = price;
    }

    public List<IngredientComponent> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientComponent> ingredients) {
        this.ingredients = ingredients;
    }

    private int calculateQuality() {
        int qualityScore = 100;
        int penalty = 0;

        for (IngredientComponent ingredient : ingredients) {
            String itemState = ingredient.getItemState();

            if (itemState.equals("burnt") || itemState.equals("raw")) {
                penalty += (1 - (1 / ingredients.size()) / 2) * 100;
            }
        }

        return qualityScore - penalty;
    }

    public int getQuality() {
        return quality;
    }
}
