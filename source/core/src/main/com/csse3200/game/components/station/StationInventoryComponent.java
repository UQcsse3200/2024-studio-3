package com.csse3200.game.components.station;

import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;

import com.csse3200.game.components.Component;

/**
 * Class to represent the inventory of a station.
 * Used for storing 'items' and keeping control over a stations inventory
 * Doesn't have knowledge of station type at the moment
 */
public class StationInventoryComponent extends Component {

    private HashSet<String> acceptableItems = new HashSet<>();
    private Optional<String> item = Optional.empty();

    @Override
    public void create() {
        entity.getEvents().addListener("set station item", this::setCurrentItem);
        entity.getEvents().addListener("remove station item", this::removeCurrentItem);
    }

    /* Used to update the item while it is inside the station inventory
    @Override
    public void update() {
        if(this.isItemPresent()) {
            //item.get().update();
        }
    }*/

    // Initialises a station inventory component object
    public StationInventoryComponent(ArrayList<String> acceptableItems) {
        // Add all the items in the list to the HastSet
        for (String acceptedItem : acceptableItems) {
            this.acceptableItems.add(acceptedItem);
        }
    }

    // Another initialiser for an inventory component
    public StationInventoryComponent(HashSet<String> acceptableItems) {
        this.acceptableItems = acceptableItems;
    }

    // Another initialiser for an inventory component
    public StationInventoryComponent() {
    }

    // Check if the station accepts the item
    public boolean isItemAccepted(String item) {
        return acceptableItems.contains(item);
    }

    // Add an item to the acceptableItems list
    public void addAcceptedItem(String item) {
        acceptableItems.add(item);
    }

    // Remove an item from accpetable items list
    public void removeAcceptedItem(String item) {
        acceptableItems.remove(item);
    }

    // Function to get if an item is present
    public boolean isItemPresent() {
        return item.isPresent();
    }

    // Get the current item if available
    public String getCurrentItem() {
        // Check if the item is present and return it if it is
        if (item.isPresent()) {
            return item.get();
        } 

        // Otherwise return null
        return null;
    }

    // Set the item if no item is present
    public boolean setCurrentItem(String newItem) {
        // Check if the stations accepts newItem return if it doesnt
        if (!this.isItemAccepted(newItem)) {
            return false;
        }

        // check if no item is present
        if (!this.isItemPresent()) {
            // No item present set item
            item = Optional.of(newItem);
            return true;
        } else {
            // Failed to set item since one already preseant
            return false;
        }    
    }

    // Forces the item to be new item and returns the old item
    // For this the item must still be allowed to be accepted
    // and will return null if item not accepted  
    public String forceSetCurrentItem(String newItem) {
        // Check if the stations accepts newItem return if it doesnt
        if (!this.isItemAccepted(newItem)) {
            return null;
        }

        // Get the old item if present
        String oldItem = this.getCurrentItem();

        item = Optional.of(newItem);

        return oldItem;
    }

    // Completely forces the item to be newItem
    // Regardless of whether station accepts said item or not
    public String forceforceSetCurrentItem(String newItem) {
        String oldItem = this.getCurrentItem();

        item = Optional.of(newItem);

        return oldItem;
    }

    // Remove the item if it is present and return it
    public String removeCurrentItem() {
        // Get the old item if present
        String oldItem = this.getCurrentItem();

        item = Optional.empty();

        return oldItem;
    }

}