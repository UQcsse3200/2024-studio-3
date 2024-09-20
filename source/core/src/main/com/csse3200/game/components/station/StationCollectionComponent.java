package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.ItemFactory;

/**
 * StationCollectionComponent.java
 *
 * StationCollectionComponentAllows for items to be collected from a station. It
 * works by being able to create an item when the player interacted with a
 * collection station. This includes the fridge and collection basket.
 */
public class StationCollectionComponent extends Component {

    /**
     * Attempts to collect the specified item from the station.
     * @require itemType is a valid type of item from the station
     * @param itemType the type of item to be collected
     * @return the item if sucessful, null otherwise.
     */
    public Entity collectItem(String itemType) {
        Entity newItem = ItemFactory.createBaseItem(itemType);

        if (newItem == null) {
            return null;
        }
        
        return newItem;
    }
    
}
