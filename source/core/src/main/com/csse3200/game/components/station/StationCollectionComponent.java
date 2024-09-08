package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.ItemFactory;

/**
 * StationCollectionComponent.java
 * </p>
 * StationCollectionComponentAllows for items to be collected from a station. It
 * works by being able to create an item when the player interacted with a
 * collection station. This includes the fridge and collection basket.
 */
public class StationCollectionComponent extends Component {

    // Item handler here to check that the item asked for is valid
    private StationItemHandlerComponent itemHandler;

    /**
     *  On creation get the item handler component
     * */
    @Override
    public void create() {
        itemHandler = entity.getComponent(StationItemHandlerComponent.class);
    }

    /**
     * Checks to see if a specific item can be collected from the station
     * @param itemType the type of item intended to be collected
     * @return true if the item can be collected, false otherwise
     */
    public boolean canCollectItem(String itemType) {
        return this.itemHandler.isItemAccepted(itemType);
    }

    /**
     * Attempts to collect the specified item from the station.
     * @require itemType is a valid type of item from the station
     * @param itemType the type of item to be collected
     * @return the item if sucessful, null otherwise.
     */
    public Entity collectItem(String itemType) {
        if (!this.canCollectItem(itemType)) {
            return null;
        }

        return ItemFactory.createBaseItem(itemType);
    }
    
}
