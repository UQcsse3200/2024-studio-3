package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

public class StationItemHandlerComponent extends Component {

    StationInventoryComponent inventoryComponent;


    /**
     *  Called on creation of the station to allow outside interaction within the station.
     *  Adds the listener for set current item and for remove current item.
     */
    @Override
    public void create() {
        inventoryComponent = entity.getComponent(StationInventoryComponent.class);
        entity.getEvents().addListener("give station item", this::giveItem);
        entity.getEvents().addListener("remove station item", this::takeItem);
        entity.getEvents().addListener("swap station item", this::swapItem); // not sure if we're doing this or nah
    }

    /**
        Adds the item to the station
        @param item that is being given to the station
     */
    public void giveItem(String item) {
        inventoryComponent.setCurrentItem(item); // should i use either of the force functions instead idk
    }

    /**
        Takes the item from the station, and returns the old item
        @param item - the item that is being taken from the station
        @return oldItem - returns the item being taken from station
     */
    public String takeItem(String item) {
        String oldItem = inventoryComponent.removeCurrentItem();
        return oldItem;
    }

    /**
        Swaps the old item present in the station with the new item
        @return oldItem - returns the item being swapped away from the station
    */
    public String swapItem(String newItem) {
        String oldItem = inventoryComponent.removeCurrentItem();
        giveItem(newItem);
        return oldItem;
    }

    // should also maybe have a createStove, createOven, createBench, creatECuttingBoard?
    // cos cbs using addAcceptedItem all the time. but idk if that should be here or stationfactory.java

    // also not sure the point of removeAcceptedItem really, i dont see where that would apply in the game
    // (station is on fire & cant use?) but i guess its good to have
}
