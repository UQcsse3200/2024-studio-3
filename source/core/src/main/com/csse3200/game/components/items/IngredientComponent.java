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
     * Constructs an IngredientComponent with the specified attributes.
     * Initialises the required variables, determining specific actions that can be used on an ingredient.
     * @param itemName - a string which is the name of the ingredient
     * @param itemType - an ItemType which is the type of the item
     * @param weight - an integer which is the weight of the ingredient
     * @param cookTime - an integer which is the time taken to cook the item
     * @param chopTime - an integer which is the time taken to chop the item
     * @param itemState - a string which is the current state of the item
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
     * Returns the cook time value
     * @return an integer which is the required cook time
     */
    public int getCookTime() {
        return this.cookTime;
    }

    /**
     * Returns the chop time value
     * @return an integer which is the required chop time
     */
    public int getChopTime() {
        return this.chopTime;
    }

    /**
     * Returns the Item state
     * @return a string which is the current state of the item
     */
    public String getItemState() {
        return this.itemState;
    }

    /**
     * Returns whether the ingredient can be cooked
     * @return a boolean that indicates whether an ingredient can be cooked
     */
    public boolean getIsCookable() {
        return isCookable;
    }

    /**
     * Returns whether the ingredient can be chopped
     * @return a boolean that indicates whether an ingredient can be chopped
     */
    public boolean getIsChoppable() {
        return isChoppable;
    }

    /**
     * Set the item state to cooked
     */
    public void cookItem() {
        if (isCookable) {
            this.itemState = "cooked";
        } else {
            throw new UnsupportedOperationException("This item is not cookable.");
        }
    }

    /**
     * Set the item state to burned
     */
    public void burnItem() {
        if (isCookable) {
            this.itemState = "burnt";
        } else {
            throw new UnsupportedOperationException("This item is not able to be burnt.");
        }
    }

    /**
     * Set the item state to chopped
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
     * Set the item state to raw
     */
    public void rawItem() {
        this.itemState = "raw";
    }

}
