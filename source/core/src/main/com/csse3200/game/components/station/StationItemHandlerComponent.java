package com.csse3200.game.components.station;

import java.util.ArrayList;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;

public class StationItemHandlerComponent extends Component {
    /**
     * String type - storing type of station
     * StationInventoryComponent inventorycomponent - instance of inventory for this station
     * TBD acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     */
    private final String type;
    private InventoryComponent inventoryComponent;
    private final ArrayList<ItemComponent> acceptableItems;

    // General TODO:
    // Add trigger calls to external for failed interactions
    // Introduce an actual structure for acceptable items, json parsing etc
    // Processing in Inventory component, animation, timing and mapping
    // Create subclass for each station where needed, eg classic bench will need
    //      to call add second component method that we dont want all stations to be able to access

    /**
     * General constructor
     * @param type - storing type of station
     * @param acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     */
    public StationItemHandlerComponent(String type, ArrayList<ItemComponent> acceptableItems) {
        this.type = type;
        this.acceptableItems = acceptableItems;

    }

    /**
     *  Called on creation of the station to allow outside interaction within the station.
     *  Adds the listener for set current item and for remove current item.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("Station Interaction", this::handleInteraction);
        inventoryComponent = entity.getComponent(InventoryComponent.class);
    }

    /**
     * Gets the type of station
     * @return station type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Handles any interaction with station, using current state of player and station
     * inventory to determine intended interaction
     * @param playerInventoryComponent reference to player inventory component
     */
    public void handleInteraction(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        // Pre calcs
        boolean full = playerInventoryComponent.isFull() & this.inventoryComponent.isFull();
        boolean empty = playerInventoryComponent.isEmpty() & this.inventoryComponent.isEmpty();
        if (full | empty) {
            // Throw an invalid interaction, red cross on station etc
            entity.getEvents().trigger("showTooltip", "Station is Full!");
        } else if (playerInventoryComponent.isFull()) {
            // Player trying to give item to station
            ItemComponent item = playerInventoryComponent.getItemFirst();
            // Check item is accepted
            if (!isItemAccepted(item)) {
                // Throw a failed accept interaction as item not valid in current station
                entity.getEvents().trigger("showTooltip", "We don't accept that trash here...");
            } else {
                this.stationReceiveItem(item);
                // 0 by default, same position as getItemFirst()
                playerInventoryComponent.removeAt(0);
                inventoryDisplay.update();
            }
        } else if (inventoryComponent.isFull()) {
            // Player wants item from station
            this.stationGiveItem(playerInventoryComponent, inventoryDisplay);
        }
    }

    /**
     * Checks if the item can be accepted, True if it can be
     * @param item to check if can be accepted
     */
    public boolean isItemAccepted(ItemComponent item) {
        // This needs initialising by parsing given list and passed during factory
        // Placeholder to accept everything
        return true;
    }

    /**
        Adds the item to the station
        @param item that is being given to the station
     */
    public void stationReceiveItem(ItemComponent item) {
        this.inventoryComponent.addItem(item);
//        entity.getEvents().trigger("showTooltip", "You gave something to the station!");
        // Need timers and animation start here, for Animations and Timer task ticket


        // These are all placeholders and don't currently go anywhere

        // Hi, from Team 2, as mentioned in the studio
        // We made the trigger for start cooking/chopping depending on the station
        // Note: We didn't request a member variable for stationState since not all
        //      stations would have a state of "HOT". It doesn't make sense in the context
        //      of a cutting board.
        //String stationState = "HOT";
        //switch (type) {
        //    case "COOK_TOP" -> {
        //        entity.getEvents().trigger("cookIngredient", "NORMAL", 1);
        //    }
        //    case "OVEN" -> {
        //        entity.getEvents().trigger("cookIngredient", stationState, 5);
        //    }
        //    case "CUTTING_BOARD" ->
        //        entity.getEvents().trigger("chopIngredient");
        //}
    }

    /**
        Takes the item from the station, and returns the old item
        @param playerInventoryComponent reference to player inventory
     */
    public void stationGiveItem(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        // These are placeholders and don't currently go anywhere
        //if (type.equals("COOK_TOP") || type.equals("OVEN")) {
        //    entity.getEvents().trigger("stopCookingIngredient");
        //} else {
        //    entity.getEvents().trigger("stopChoppingIngredient");
        //}
//        entity.getEvents().trigger("showTooltip", "You took something from the station!");
        ItemComponent item = inventoryComponent.getItemFirst();
        playerInventoryComponent.addItem(item);
        inventoryDisplay.update();
        // Remove single item in station
        this.inventoryComponent.removeAt(0);
        entity.getEvents().trigger("interactionEnd");
    }
}
