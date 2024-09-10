package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Provider;

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
    private static final Logger logger = LoggerFactory.getLogger(StationServingComponent.class);

    TicketDetails bigTicket;


    /**
     * On creation a listener for Submit Meal will be added to the station.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("Station Interaction", this::handleInteraction);
        bigTicket = ServiceLocator.getTicketDetails();
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

        String[] bigTicketInfo = bigTicket.getCurrentBigTicketInfo();
        if (bigTicketInfo[0] != null) {
            logger.info(bigTicketInfo[0]); // order number ("5")
            logger.info(bigTicketInfo[1]); // meal ("tomato soup")
            logger.info(bigTicketInfo[2]); // time left ("32")

            // Call to team 1's function with the big ticket info
            //TBD(item, bigTicketInfo[0], bigTicketInfo[1], bigTicketInfo[2]);
            // remove ticket
            ServiceLocator.getDocketService().getEvents().trigger("removeOrder", -1); // removes the order from the orderaction list
            ServiceLocator.getDocketService().getEvents().trigger("removeBigTicket"); // removes the order from the display list

        } else {
            logger.info("no ticket when submitting"); // team 1 can decide if they want to handle this edge case
            return;


        }


    }
    
}
