package com.csse3200.game.components.items;

import com.csse3200.game.components.items.ItemComponent;

import java.util.UUID;

public class IngredientComponent extends ItemComponent{
        private final int cookTime;
        private ItemState itemState;
        private enum ItemState {
            RAW,
            COOKED,
            BURNED
        }

    public IngredientComponent(String itemName, ItemType itemType, int weight, int cookTime) {
        super(itemName, itemType, weight);
        this.cookTime = cookTime;
        this.itemState = ItemState.RAW;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public String getItemState() {
        return this.itemState.name();
    }

    public void cookItem() {
        this.itemState = ItemState.COOKED;
    }

    public void burnItem() {
        this.itemState = ItemState.BURNED;
    }

}
