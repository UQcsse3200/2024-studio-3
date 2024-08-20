package com.csse3200.game.components.items;
import com.csse3200.game.components.Component;

import java.util.UUID;

public class ItemComponent extends Component {
    private final String itemid;
    private final String itemName;
    private final int weight;
    private final ItemType itemType;
    private final int cookTime;
    private int itemState;

    public ItemComponent(String itemName, ItemType itemType, int weight, int cookTime) {
        this.itemid = UUID.randomUUID().toString();
        this.itemName = itemName;
        this.weight = weight;
        this.itemType = itemType;
        this.cookTime = cookTime;
        this.itemState = 0;
    }

    public String getItemid() {
        return this.itemid;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int weight() {
        return this.weight;
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    public void nextState() {
        this.itemState += 1;
    }
}
