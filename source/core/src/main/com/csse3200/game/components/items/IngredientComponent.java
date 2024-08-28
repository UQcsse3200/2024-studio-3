package com.csse3200.game.components.items;

public class IngredientComponent extends ItemComponent{
        private final int cookTime;
        private String itemState;

    public IngredientComponent(String itemName, ItemType itemType, int weight, int cookTime, String itemState) {
        super(itemName, itemType, weight);
        this.cookTime = cookTime;
        this.itemState = itemState;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public String getItemState() {
        return this.itemState;
    }

    public void cookItem() {
        this.itemState = "cooked";
    }

    public void burnItem() {
        this.itemState = "burnt";
    }

    public void chopItem() {
        this.itemState = "chopped";
    }

}
