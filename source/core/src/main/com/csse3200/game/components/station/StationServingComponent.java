package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Provider;

/**
 * StationServingComponent.java
 *
 * StationServingComponent gives the station the ability to submit a meal to be 
 * able to be served to a customer. This will specifically be used by the 
 * serving bench 'station'.
 *
 * {@link #submitMeal(ItemComponent)}: Function which controls the submission of a meal by the
 * class.
 *
 * This component is currently incomplete and will need to be finished.
 */
public class StationServingComponent extends Component {

    // itemHandler allows acess for serving component to see the inventory of
    // the station.
    protected StationItemHandlerComponent itemHandler;
    private static final Logger logger = LoggerFactory.getLogger(StationServingComponent.class);
    private OrderActions orderActions;

    /**
     * On creation a listener for Submit Meal will be added to the station.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("Station Interaction", this::handleInteraction);
        //orderActions = entity.getComponent(OrderActions.class);
        orderActions = ServiceLocator.getOrderActions(); // ? doesn't seem to work...
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
     * Function which calls to identify the current ticket info, as well as the item in the inventory
     * Calls another function which grades the submission
     * @param item reference to the item being submitted by the user
     */
    public void submitMeal(ItemComponent item) {
        /*
        String[] bigTicketInfo = orderActions.getCurrentBigTicketInfo();
        // TODO bigTicketInfo[0] is ALWAYS null, even when there is a ticket and it shouldn't. orderActions needs to be instantiated better, not sure how though
        if (bigTicketInfo[0] != null) {
            logger.info(bigTicketInfo[0]); // order number ("5")
            logger.info(bigTicketInfo[1]); // meal ("tomato")
            logger.info(bigTicketInfo[2]); // time left ("32")
            // Call to other team's function with the big ticket info
            //TBD(item, bigTicketInfo[0], bigTicketInfo[1], bigTicketInfo[2]);
            // remove ticket
            ServiceLocator.getDocketService().getEvents().trigger("removeOrder", -1); // removes the order from the orderaction list
            ServiceLocator.getDocketService().getEvents().trigger("removeBigTicket"); // removes the order from the display list

        } else { // only enters this condition, when it shouldn't.  TODO
            /*
            TODO
             DELETE THIS, it should only be seen in the IF clause (bigTicketInfo[0] != null), just here to show that it works.

            ServiceLocator.getDocketService().getEvents().trigger("removeOrder", -1);
            ServiceLocator.getDocketService().getEvents().trigger("removeBigTicket");
            return;
        }*/
    }
    
}