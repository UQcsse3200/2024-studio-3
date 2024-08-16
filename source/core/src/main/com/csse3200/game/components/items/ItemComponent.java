package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;

public class ItemComponent extends Component {
    private final String itemid;
    private final String itemName;
    private final int weight;
    private boolean isInteractable;

    public ItemComponent(String itemid, String itemName, int weight) {
        this.itemid = itemid;
        this.itemName = itemName;
        this.weight = weight;
    }
}
