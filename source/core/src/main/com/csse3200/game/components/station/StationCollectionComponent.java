package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.ItemFactory;

public class StationCollectionComponent extends Component {

    private StationItemHandlerComponent itemHandler;

    @Override
    public void create() {
        itemHandler = entity.getComponent(StationItemHandlerComponent.class);
    }

    public boolean canCollectItem(ItemComponent itemType) {
        return this.itemHandler.isItemAccepted(itemType);
    }

    public Entity collectItem(ItemComponent itemType) {
        if (!this.canCollectItem(itemType)) {
            return null;
        }

        return ItemFactory.createBaseItem(itemType.getItemName());
    }
    
}
