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
        orderActions = ServiceLocator.getOrderActions(); // ?
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

        String[] bigTicketInfo = orderActions.getCurrentBigTicketInfo();
        if (bigTicketInfo[0] != null) {
            logger.info(bigTicketInfo[0]);
            logger.info(bigTicketInfo[1]);
            logger.info(bigTicketInfo[2]);
            // Call to other team's function with the big ticket info
            //TBD(item, bigTicketInfo[0], bigTicketInfo[1], bigTicketInfo[2]);
            // After successful submission, trigger removal of the big ticket
            //ServiceLocator.getDocketService().getEvents().trigger("removeOrder",
            //        MainGameOrderTicketDisplay.getTableArrayList().size() - 1);
            ServiceLocator.getDocketService().getEvents().trigger("removeOrder", -1);

            // NOTE: the current 'recipe name' is all the ingredients individually required (cucumber, tomato, lettuce)
        } else {
            logger.info("hello");
            // Handle case where there's no current big ticket
            // Maybe show an error message or ignore the submission
            return;
        }
    }
    
}
