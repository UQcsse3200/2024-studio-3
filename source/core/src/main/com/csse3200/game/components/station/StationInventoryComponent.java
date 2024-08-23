package com.csse3200.game.components.station;

import java.util.Optional;
import java.util.ArrayList;

import com.csse3200.game.components.Component;


/**
 * Class to represent the inventory of a station and provide some internal logic
 * for accepting and storing items intended to have some action performed on them.
 */
public class StationInventoryComponent extends Component {

    /**
     * final integer storing max amount of items, 2
     * item stores the current item within the component, defaults to only one item at a time
     */
    private static final int SIZE = 2;
    private ArrayList<Optional<String>> item;


    /**
     * TODO
     * Processing for timer, recipe mapping etc
     * Consider integration of abstract class
     * 
     * Creates a station inventory component for the specified type.
     * Intented to also initialise all of the accepted items based on some
     * predefined configuration.
     */
    public StationInventoryComponent() {
        this.item = new ArrayList<>(SIZE);
        this.item.add(Optional.empty());
        this.item.add(Optional.empty());
    }

    /**
     * When called checks if there is an entity within the station and
     * if so, updates the state of it to reflect changes made due to the
     * station.
     * @param type the type of station
     */
    public void processInventory(String type) {
        if(this.isItemPresent()) {
            // Processing to be implemented goes here, access this.item and
            // type, map with dictionary hashmap etc, cooking times to come later
        }
    }

    /**
     * Checks whether the station is currently holding an item.
     * @return true is there is an item, otherwise false
     */
    public boolean isItemPresent() {
        return item.getFirst().isPresent();
    }


    /**
     * Set the current item within the station to the item provided. Checks if the 
     * item can be accepted only, call isItemPresent() in handler.
     * @param newItem to be put into the station.
     * @return true if the item has been accepted, false otherwise.
     */
    public boolean setCurrentItem(String newItem) {
        // No item present, set item
        this.item.set(0, (Optional.of(newItem)));
        return true;

    }

    /**
     * Gets the current item within the station. If there is one.
     * @return the item within the station, Optional.empty() otherwise
     */
    public Optional<String> getCurrentItem() {
        if (this.isItemPresent()) {
            return item.getFirst();
        }
        return Optional.empty();
    }

    /**
     * Removes the item currently within the station and returns it to the
     * caller. 
     * @return oldItem within the station, Optional.empty() if nothing was present.
     */
    public Optional<String> removeCurrentItem() {
        // Get the old item if present
        if (this.isItemPresent()) {
            Optional<String> tmp = item.getFirst();
            item.remove(tmp);
            return tmp;
        }
        return Optional.empty();
    }
}