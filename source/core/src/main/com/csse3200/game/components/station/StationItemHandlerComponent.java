package com.csse3200.game.components.station;

import java.util.HashSet;
import java.util.ArrayList;

import com.csse3200.game.components.Component;

public class StationItemHandlerComponent extends Component {

    private final String type;
    private StationInventoryComponent inventoryComponent;
    private HashSet<String> acceptableItems = new HashSet<>();

    public StationItemHandlerComponent(String type, ArrayList<String> acceptableItems) {
        this.type = type;
        for (String acceptedItem : acceptableItems) {
            this.acceptableItems.add(acceptedItem);
        }
    }

    /**
     *  Called on creation of the station to allow outside interaction within the station.
     *  Adds the listener for set current item and for remove current item.
     */
    @Override
    public void create() {
        inventoryComponent = entity.getComponent(StationInventoryComponent.class);
    }

    public String getType() {
        return this.type;
    }

    /**
     * Checks if the item can be accepted
     * @param item to check if can be accepted
     */
    public boolean isItemAccepted(String item) {
        return this.acceptableItems.contains(item);
    }

    /**
        Adds the item to the station
        @param item that is being given to the station
     */
    public void giveItem(String item) {
        if (this.isItemAccepted(item)) {
            inventoryComponent.setCurrentItem(item);
        }
    }

    /**
        Takes the item from the station, and returns the old item
        @return oldItem - returns the item being taken from station
     */
    public String takeItem() {
        String oldItem = inventoryComponent.removeCurrentItem();
        return oldItem;
    }

    /**
        Swaps the old item present in the station with the new item
        @return oldItem - returns the item being swapped away from the station
    */
    public String swapItem(String newItem) {
        if (!this.isItemAccepted(newItem)) {
            return null;
        }

        String oldItem = inventoryComponent.removeCurrentItem();
        inventoryComponent.setCurrentItem(newItem);
        return oldItem;
    }

    // should also maybe have a createStove, createOven, createBench, creatECuttingBoard?
    // cos cbs using addAcceptedItem all the time. but idk if that should be here or stationfactory.java

    // also not sure the point of removeAcceptedItem really, i dont see where that would apply in the game
    // (station is on fire & cant use?) but i guess its good to have
}
