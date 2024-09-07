package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.ItemFactory;

public class StationCollectionComponent extends Component {

    StationItemHandlerComponent itemHandler;

    @Override
    public void create() {
        itemHandler = entity.getComponent(StationItemHandlerComponent.class);
    }

    public boolean canCollectItem(String itemType) {
        return this.itemHandler.isItemAccepted(itemType);
    }

    public Entity collectItem(String itemType) {
        if (!this.canCollectItem(itemType)) {
            return null;
        }

        return ItemFactory.createBaseItem(itemType);
    }
    
}
