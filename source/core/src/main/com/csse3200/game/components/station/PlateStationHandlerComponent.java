package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.components.items.PlateComponent;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlateStationHandlerComponent extends Component {
    /**
     * String type - storing type of station
     * String ingredient - storing the item type dispensed by this station
     * StationInventoryComponent inventory component - instance of inventory for this station
     */
    protected final String type = "dishwasher";
    protected InventoryComponent inventoryComponent;
    private static final Logger logger = LoggerFactory.getLogger(PlateStationHandlerComponent.class);

    /**
     * General constructor
     */
    public PlateStationHandlerComponent() {}

    /**
     *  Called on creation of the station to allow outside interaction within the station.
     *  Adds the listener for set current item and for remove current item.
     */
    @Override
    public void create() {
        // initialise the inventory component
        entity.getEvents().addListener("Station Interaction", this::handleInteraction);
        this.inventoryComponent = entity.getComponent(InventoryComponent.class);

        // create and add a plate into the inventory
        Entity plate = ItemFactory.createPlate();
        this.inventoryComponent.addItemAt(plate.getComponent(PlateComponent.class), 0);
    }

    /**
     * Gets the type of station
     * 
     * @return - station type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Handles any interaction with station, using current state of player and station
     * inventory to determine intended interaction.
     * 
     * @param playerInventoryComponent - reference to player inventory component
     * @param inventoryDisplay - reference to individual inventory display
     * @param type - the type of interaction attempt
     */
    public void handleInteraction(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay, String type) {
        if (!type.equals("default")) { // Return if not default interaction
            return;
        }
        
        if (!playerInventoryComponent.isFull()) {
            stationGiveItem(playerInventoryComponent, inventoryDisplay);
            logger.debug("INTERACTED WITH DISHWASHER");
        }
    }

    /**
     * Takes the item from the station, and returns the old item
     *
     * @param playerInventoryComponent - reference to player inventory
     * @param inventoryDisplay - reference to the inventory display for the station
     */
    public void stationGiveItem(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        ItemComponent item = this.inventoryComponent.getItemFirst();
        playerInventoryComponent.addItemAt(item,0);
        inventoryDisplay.update();
        this.inventoryComponent.removeAt(0);

        // create and add a plate into the inventory
        Entity plate = ItemFactory.createPlate();
        this.inventoryComponent.addItemAt(plate.getComponent(PlateComponent.class), 0);
    }

}
