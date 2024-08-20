package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

public class StationItemHandlerComponent extends Component {

    StationInventoryComponent inventoryComponent;

    @Override
    public void create() {
        inventoryComponent = entity.getComponent(StationInventoryComponent.class);
        entity.getEvents().addListener("give item", this::giveItem);

    }

    public void giveItem(String item) {
        inventoryComponent.setCurrentItem(item);
    }
}
