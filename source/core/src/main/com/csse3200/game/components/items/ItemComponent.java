package com.csse3200.game.components.items;
import com.csse3200.game.components.Component;

import java.util.UUID;

public class ItemComponent extends Component {
    private final String itemId;
    private final String itemName;
    private final int weight;
    private final ItemType itemType;

    public ItemComponent(String itemName, ItemType itemType, int weight) {
        this.itemId = UUID.randomUUID().toString();
        this.itemName = itemName;
        this.weight = weight;
        this.itemType = itemType;
    }

    public String getItemId() {
        return this.itemId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getWeight() {
        return this.weight;
    }

    public ItemType getItemType() {
        return this.itemType;
    }

}
