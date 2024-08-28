package com.csse3200.game.components.items;
import com.csse3200.game.components.Component;

import java.util.UUID;

public class ItemComponent extends Component {
    private final String itemId;
    private final String itemName;
    private final int weight;
    private final ItemType itemType;

    /**
     * Called when the item is created and initates necessary variables
     * @param itemName - a string which is the name of the item
     * @param itemType - an ItemType which is the type of the item
     * @param weight - an integer which is the weight of the item
     */
    public ItemComponent(String itemName, ItemType itemType, int weight) {
        this.itemId = UUID.randomUUID().toString();
        this.itemName = itemName;
        this.weight = weight;
        this.itemType = itemType;
    }

    /**
     * Gets the id of the item
     * @return a string which is the randomly generated id of the item
     */
    public String getItemId() {
        return this.itemId;
    }

    /**
     * Gets the name of the item
     * @return a string which is the name of the item
     */
    public String getItemName() {
        return this.itemName;
    }
    /**
     * Gets the weight of the item
     * @return a string which is the weight of the item
     */
    public int getWeight() {
        return this.weight;
    }
    /**
     * Gets the type of the item
     * @return an itemType which is the type the items is
     */
    public ItemType getItemType() {
        return this.itemType;
    }

}
