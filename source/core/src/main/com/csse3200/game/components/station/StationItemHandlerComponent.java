package com.csse3200.game.components.station;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Optional;

import com.csse3200.game.components.Component;

public class StationItemHandlerComponent extends Component {
    /**
     * String type - storing type of station
     * StationInventoryComponent inventorycomponent - instance of inventory for this station
     * TBD acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     */
    private final String type;
    private StationInventoryComponent inventoryComponent;
    private ArrayList<String> acceptableItems = new ArrayList<>();

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
    public StationItemHandlerComponent(String type, ArrayList<String> acceptableItems) {
        this.type = type;
        this.acceptableItems = acceptableItems;
    }

    /**
     *  Called on creation of the station to allow outside interaction within the station.
     *  Adds the listener for set current item and for remove current item.
     */
    @Override
    public void create() {
        acceptableItems.add("meat");
        acceptableItems.add("vegetable");
        acceptableItems.add("cheese");
        inventoryComponent = entity.getComponent(StationInventoryComponent.class);
        entity.getEvents().addListener("item exists", this::hasItem);
        entity.getEvents().addListener("give station item", this::giveItem);
        entity.getEvents().addListener("take item", this::takeItem);
        entity.getEvents().addListener("get type", this::getType);
    }

    /**
     * Gets the type of station
     * @return station type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Checks if the item can be accepted
     * @param item to check if can be accepted
     */
    public boolean isItemAccepted(String item) {
        // This needs to be available outside of handler, again how does interaction want
        // to receive it
        return this.acceptableItems.contains(item);

    }

    /**
     * Check if the station is storing an item
     * @return true if the station has an item
     */
    public boolean hasItem() {
        // This needs to send a fail trigger to player, plays full animation
        // Doesn't receive item etc
        return this.inventoryComponent.isItemPresent();
    }

    /**
        Adds the item to the station
        @param item that is being given to the station
     */
    public void giveItem(String item) {
        if (this.hasItem()) {
            // This needs to send a fail trigger to player, plays full animation
            // Doesn't receive item etc
            return;
        }
        if (!this.isItemAccepted(item)) {
            // This needs to send a different fail trigger to player, plays full animation
            // Doesn't receive item etc
            return;
        }
        inventoryComponent.setCurrentItem(item);

        // Hi, from Team 2, as mentioned in the studio
        // We made the trigger for start cooking/chopping depending on the station
        // Note: We didn't request a member variable for stationState since not all
        //      stations would have a state of "HOT". It doesn't make sense in the context
        //      of a cutting board.
        String stationState = "HOT";
        switch (type) {
            case "COOK_TOP" -> {
                entity.getEvents().trigger("cookIngredient", "NORMAL", 1);
            }
            case "OVEN" -> {
                entity.getEvents().trigger("cookIngredient", stationState, 5);
            }
            case "CUTTING_BOARD" ->
                entity.getEvents().trigger("chopIngredient");
        }


    }

    /**
        Takes the item from the station, and returns the old item
     */
    public void takeItem() {
        // Hi, from Team 2, as mentioned in the studio
        // We made the trigger for stop cooking/chopping here
        if (type.equals("COOK_TOP") || type.equals("OVEN")) {
            entity.getEvents().trigger("stopCookingIngredient");
        } else {
            entity.getEvents().trigger("stopChoppingIngredient");
        }

        Optional<String> oldItem = inventoryComponent.removeCurrentItem();
        // trigger here on player inventory component to send returned item
        // when done


    }
}
