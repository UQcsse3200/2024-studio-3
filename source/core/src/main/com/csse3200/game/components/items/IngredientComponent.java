package com.csse3200.game.components.items;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class IngredientComponent extends ItemComponent{
        private final int cookTime;
        private final int chopTime;
        private String itemState;
        private final boolean isCookable;
        private final boolean isChoppable;

    /**
     * Called to create ingredient items
     * @param itemName - a string which is the name of the item
     * @param itemType - an itemType which is the type of the item
     * @param weight - an integer which is the weight of the item
     * @param cookTime - an integer which is the time for the item to cook
     * @param chopTime - an integer which is the time to chop the item
     * @param itemState - an ItemState which is the current state of the item
     */
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

    /**
     * Gets the cook time of the item
     * @return an integer which is the cook time
     */
    public int getCookTime() {
        return this.cookTime;
    }

    /**
     * Gets the chop time of the item
     * @return an integer which is the time to chop the item
     */
    public int getChopTime() {
        return this.chopTime;
    }

    /**
     * Gets the current state of the item
     * @return an ItemState which is the current state of the item
     */
    public String getItemState() {
        return this.itemState;
    }

    /**
     * Gets if the item can be cooked
     * @return a boolean of whether the item can be cooked
     */
    public boolean getIsCookable() {
        return isCookable;
    }

    /**
     * Gets if the item can be chopped
     * @return a boolean of whether the item can be chopped
     */
    public boolean getIsChoppable() {
        return isChoppable;
    }

    /**
     * Updates the item state to 'cooked'
     */
    public void cookItem() {
        if (isCookable) {
            this.itemState = "cooked";
        } else {
            throw new UnsupportedOperationException("This item is not cookable.");
        }
    }

    /**
     * Updates the item state to 'burnt'
     */
    public void burnItem() {
        if (isCookable) {
            this.itemState = "burnt";
        } else {
            throw new UnsupportedOperationException("This item is not able to be burnt.");
        }
    }

    /**
     * Updates the item state to 'chopped'
     */
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

    /**
     * Sets the current item state to 'raw'
     */
    public void rawItem() {
        this.itemState = "raw";
    }

}
