package com.csse3200.game.components.items;
import com.csse3200.game.components.Component;

public class ItemComponent extends Component {
    private final String itemid;
    private final String itemName;
    private final int weight;
    private final String itemType;

    public ItemComponent(String itemid, String itemName, String itemType, int weight) {
        this.itemid = itemid;
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
