package com.csse3200.game.components.station;

import java.util.ArrayList;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;

public class StationItemHandlerComponent extends Component {
    /**
     * String type - storing type of station
     * InventoryComponent inventorycomponent - instance of inventory for this station
     * TBD acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     */
    private final String type;
    private InventoryComponent inventoryComponent;
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
    public StationItemHandlerComponent(String type) {
        this.type = type;
        this.getAcceptedItem();
    }

    /**
     *  Called on creation of the station to allow outside interaction within the station.
     *  Adds the listener for set current item and for remove current item.
     */
    @Override
    public void create() {
        inventoryComponent = entity.getComponent(InventoryComponent.class);
    }

    /**
     * Get the acceptable items and put it in the station
     */
    private void getAcceptedItem() {
        // get the acceptable items type by reading the json file i will create
        this.acceptableItems.add("beef");
        this.acceptableItems.add("cheese");
        this.acceptableItems.add("apple");
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
    public boolean isItemAccepted(Entity item) {
        String itemName = item
                .getComponent(ItemComponent.class)
                .getItemName();

        return this.acceptableItems.contains(itemName);
    }

    /**
     * Checks if the item can be accepted
     * @param item to check if can be accepted
     */
    public boolean isItemAccepted(String item) {
        return this.acceptableItems.contains(item);
    }

    /**
     * Check if the station is storing an item
     * @return true if the station has an item
     */
    public boolean hasItem() {
        return !this.inventoryComponent.isEmpty();
    }

    /**
        Adds the item to the station
        @param item that is being given to the station
     */
    public void giveItem(Entity item) {

        // Check item is being able to be given
        if (!this.isItemAccepted(item)) {
            return; // return if the item isn't accepted
        }

        // Check the inventory has space to give items
        if (this.inventoryComponent.isFull()) {
            return; // return if full inventory
        }

        // Add  the item to the inventory component
        inventoryComponent.addItem(item.getComponent(ItemComponent.class));
    }

    /**
        Takes the item from the station, and returns the old item
     */
    public Entity takeItem() {
        if (inventoryComponent.isEmpty()) {
            return null;
        }

        return inventoryComponent.removeAt(0).getEntity();
    }

    public Entity peekItem() {
        if (inventoryComponent.isEmpty()) {
            return null;
        }

        return inventoryComponent.getItemAt(0).getEntity();
    }



    /**
        Takes the item from the station, and returns the old item
     */
    /*public void takeItem() {
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
        entity.getEvents().trigger("interactionEnd");
    }*/
}
