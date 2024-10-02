package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.services.ServiceLocator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StationBinComponent.java
 *
 * StationBinComponent throughs away an item
 *
 */
public class StationBinComponent extends Component {

    // itemHandler allows acess for serving component to see the inventory of
    // the station.
    private static final Logger logger = LoggerFactory.getLogger(StationBinComponent.class);

    /**
     * On creation a listener for Submit Meal will be added to the station.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("Station Interaction", this::handleInteraction);
    }

    /**
     * Handles any interaction with station, using current state of player and station
     * inventory to determine intended interaction
     * @param playerInventoryComponent reference to player inventory component
     * @param inventoryDisplay reference to individual inventory display
     * @param type the type of interaction attempt
     */
    public void handleInteraction(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay, String type) {
        if (!type.equals("default")) {
            return; // Do nothing if not the default interaction
        }

        if (playerInventoryComponent.isFull()) {
            ItemComponent item = playerInventoryComponent.getItemFirst();
            playerInventoryComponent.removeAt(0);
            inventoryDisplay.update();
            disposeItem(item);
        }
    }
    /**
     * Function which calls to identify the current ticket info, as well as the item in the inventory
     * Calls another function which grades the submission
     * @param item reference to the item being submitted by the user
     */
    public void disposeItem(ItemComponent item) {
        // Remove item
        ServiceLocator.getEntityService().unregister(item.getEntity());

        logger.info("Disposing of item");
    }
}
