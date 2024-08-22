package com.csse3200.game.components.items;

import java.util.List;

public class MealComponent extends ItemComponent {
    private List<IngredientComponent> ingredients;
    private int quality;
    private int price;

    public MealComponent(String itemName, ItemType itemType, int weight, List<IngredientComponent> ingredients,
                         int quality, int price) {
        super(itemName, itemType, weight);
        this.ingredients = ingredients;
        this.quality = quality;
        this.price = price;
    }
}
