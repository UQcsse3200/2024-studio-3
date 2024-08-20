package com.csse3200.game.components.items;
import com.csse3200.game.components.Component;

import java.util.UUID;

public class ItemComponent extends Component {
    private final String itemId;
    private final String itemName;
    private final int weight;
    private final ItemType itemType;
    private final int cookTime;
    private ItemState itemState;
    private enum ItemState{
        RAW,
        COOKED,
        BURNED
    }

    public ItemComponent(String itemName, ItemType itemType, int weight, int cookTime) {
        this.itemId = UUID.randomUUID().toString();
        this.itemName = itemName;
        this.weight = weight;
        this.itemType = itemType;
        this.cookTime = cookTime;
        this.itemState = ItemState.RAW;
    }

    public String getItemId() {
        return this.itemId;
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
