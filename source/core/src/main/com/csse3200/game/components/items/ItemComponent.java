package com.csse3200.game.components.items;
import com.csse3200.game.components.Component;

import java.util.UUID;

public class ItemComponent extends Component {
    private final String itemid;
    private final String itemName;
    private final int weight;
    private final String itemType;

    public ItemComponent(String itemName, String itemType, int weight) {
        this.itemid = UUID.randomUUID().toString();
        this.itemName = itemName;
        this.weight = weight;
        this.itemType = itemType;
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

    public String getItemType() {
        return this.itemType;
    }


}
