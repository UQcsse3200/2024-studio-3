package com.csse3200.game.components.station;

import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;

import com.csse3200.game.components.Component;

/**
 * Class to represent the inventory of a station and provide some internal logic
 * for accepting and storing items intended to have some action performed on them.
 */
public class StationInventoryComponent extends Component {

    /**
     * <p>  acceptable items store a set of items that the instance 
     *      is allowed to accept.
     * 
     * <p>  item stores the current item within the component
     * 
     * <p>  type stores a string of the type of station that the component
     *      is
     */
    private HashSet<String> acceptableItems = new HashSet<>();
    private Optional<String> item = Optional.empty();
    private String type;

    // Initialises a station inventory component object
    // No javadoc needed as this is intended to be debrecated but will
    // be used for initial testing
    public StationInventoryComponent(String type, ArrayList<String> acceptableItems) {
        this.type = type;
        // Add all the items in the list to the HastSet
        for (String acceptedItem : acceptableItems) {
            this.acceptableItems.add(acceptedItem);
        }
    }

    /**
     * Creates a station inventory component for the specified type.
     * Intented to also initialise all of the accepted items based on some
     * predefined configuration.
     * @param type the type of station the entity is
     */
    public StationInventoryComponent(String type) {
        this.type = type;
    }

    /**
     * When called checks if there is an entity within the station and
     * if so, updates the state of it to reflect changes made due to the
     * station.
     */
    @Override
    public void update() {
        if(this.isItemPresent()) {
            //item.get().update();
        }
    }

    /**
     * Returns the string form of the type of station the instance is.
     * @return type of station
     */
    public String getType() {
        return this.type;
    }

    /**
     * Checks if an item is allowed to be accepted within the given station.
     * @param item inteneded to be passed in
     * @return true if the item can be accepted, false otherwise
     */
    public boolean isItemAccepted(String item) {
        return acceptableItems.contains(item);
    }

    /**
     * Adds the gived item to the list of items accepted by the station.
     * @param item intended to be allowed to be passed in
     */
    public void addAcceptedItem(String item) {
        acceptableItems.add(item);
    }

    /**
     * Removes a given item from the list of accepted items. If the item is
     * not in the list then nothing is done.
     * @param item that is intedended to be removed
     */
    public void removeAcceptedItem(String item) {
        acceptableItems.remove(item);
    }

    /**
     * Checks whether the station is currently holding an item.
     * @return true is there is an item, otherwise false
     */
    public boolean isItemPresent() {
        return item.isPresent();
    }



    /**
     * Set the current item within the station to the item provided. Checks if the 
     * item can be accepted and also if there is currently an item or not.
     * @param newItem to be put into the station.
     * @return true if the item has been accepted, false otherwise.
     */
    public boolean setCurrentItem(String newItem) {
        // Check if the stations accepts newItem return if it doesnt
        if (!this.isItemAccepted(newItem)) {
            return false;
        }

        // check if no item is present
        if (!this.isItemPresent()) {
            // No item present set item
            this.item = Optional.of(newItem);
            return true;
        } else {
            // Failed to set item since one already preseant
            return false;
        }    
    }

    /**
     * Force sets the current item within the station to the provided newItem if the
     * item is able to be accepted by the station.
     * @param newItem the item to be put in the station
     * @return the item previously within the station if present, null if no item
     * previously there, or if the item wasn't accepted
     */
    public String forceSetCurrentItem(String newItem) {
        // Check if the stations accepts newItem return if it doesnt
        if (!this.isItemAccepted(newItem)) {
            return null;
        }
    
        // Get the old item if present
        String oldItem = this.getCurrentItem();

        this.item = Optional.of(newItem);

        return oldItem;
    }

    /**
     * Forcibly sets the stations current item to be the new item provided.
     * This does not care if the item is within the stations accepted list
     * of items or not.
     * @param newItem to be forced into the station
     * @return the oldItem within the station if present, null if no item
     * was previously inside.
     */
    public String forceforceSetCurrentItem(String newItem) {
        String oldItem = this.getCurrentItem();

        item = Optional.of(newItem);

        return oldItem;
    }

    /**
     * Gets the current item within the station. If there is one.
     * @return the item within the station, null otherwise
     */
    public String getCurrentItem() {
        if (item.isPresent()) {
            return item.get();
        }
        return null;
    }

    /**
     * Removes the item currently within the station and returns it to the
     * caller. 
     * @return oldItem within the station, null if nothing was present.
     */
    public String removeCurrentItem() {
        // Get the old item if present
        String oldItem = this.getCurrentItem();
        item = Optional.empty();
        return oldItem;
    }
}