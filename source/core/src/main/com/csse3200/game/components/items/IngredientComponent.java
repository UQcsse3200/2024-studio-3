package com.csse3200.game.components.items;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class IngredientComponent extends ItemComponent{
        private final int cookTime;
        private final int chopTime;
        private String itemState;
        private final boolean isCookable;
        private final boolean isChoppable;

    public IngredientComponent(String itemName, ItemType itemType, int weight, int cookTime,
                               int chopTime, String itemState) {
        super(itemName, itemType, weight);
        this.cookTime = cookTime;
        this.chopTime = chopTime;
        this.itemState = itemState;

        if (cookTime > 0) {
            this.isCookable = TRUE;
        } else {
            this.isCookable = FALSE;
        }

        if (chopTime > 0) {
            this.isChoppable = TRUE;
        } else {
            this.isChoppable = FALSE;
        }
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public int getChopTime() {
        return this.chopTime;
    }

    public String getItemState() {
        return this.itemState;
    }

    public boolean isCookable() {
        return isCookable;
    }

    public boolean isChoppable() {
        return isChoppable;
    }

    public void cookItem() {
        if (isCookable) {
            this.itemState = "cooked";
        } else {
            throw new UnsupportedOperationException("This item is not cookable.");
        }
    }

    public void burnItem() {
        if (isCookable) {
            this.itemState = "burnt";
        } else {
            throw new UnsupportedOperationException("This item is not able to be burnt.");
        }
    }

    public void chopItem() {
        if (isChoppable) {
            this.itemState = "chopped";
        } else {
            throw new UnsupportedOperationException("This item is not choppable.");
        }
    }

    public void setItemState (String itemState) {
        this.itemState = itemState;
    }

    public void rawItem() {
        this.itemState = "raw";
    }

}
