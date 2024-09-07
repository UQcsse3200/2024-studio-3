package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;

/**
 * StationServingComponent.java
 * </p>
 * StationServingComponent gives the station the ability to submit a meal to be 
 * able to be served to a customer. This will specifically be used by the 
 * serving bench 'station'.
 * </p>
 * {@link #submitMeal(ItemComponent)}: Function which controls the submission of a meal by the
 * class.
 * </p>
 * This component is currently incomplete and will need to be finished.
 */
public class StationServingComponent extends Component {

    // itemHandler allows acess for serving component to see the inventory of
    // the station.
    private StationItemHandlerComponent itemHandler;

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
     */
    public void handleInteraction(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        if (playerInventoryComponent.isFull()) {
            ItemComponent item = playerInventoryComponent.getItemFirst();
            playerInventoryComponent.removeAt(0);
            inventoryDisplay.update();
            submitMeal(item);
        }
    }
    /**
     * Function which allows the StationServingComponent to be able to access 
     * the inventory and serve a meal to one of the customers. 
     */
    public void submitMeal(ItemComponent item) {
        //TODO:
        //call getCurrentBigTicketInfo() to get values of bigticket, returning a string[]
        //String[] bigTicketInfo =
        // call made to other teams function

        //TBD(item, bigTicketInfo[0], bigTicketInfo[1], bigTicketInfo[2]);
        //AKA item being submitted, order number of ticket, meal of ticket, time left of ticket.
        return;
    }
    
}
