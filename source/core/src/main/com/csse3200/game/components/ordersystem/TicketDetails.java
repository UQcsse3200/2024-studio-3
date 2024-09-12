package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Input.Keys;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csse3200.game.components.Component; // ?

import java.security.Provider;

/**
 * This class listens to key presses to shift dockets left and right and handles
 * order-related actions in the game.
 */
public class TicketDetails extends Component {
    private static final Logger logger = LoggerFactory.getLogger(TicketDetails.class);

    // key values of big ticket
    private String currentOrderNumber;
    private String currentMeal;
    private String currentTimeLeft;

    /**
     * Constructs an OrderActions instance with a reference to the main game object. - Tia
     *
     */
    public TicketDetails() {
        this.currentMeal = null;
        this.currentTimeLeft = null;
        this.currentOrderNumber = null;
    }

    /**
     * Updates big ticket information
     * @param orderNumber
     * @param meal
     * @param timeLeft
     */
    public void onUpdateBigTicket(String orderNumber, String meal, String timeLeft) {
        this.currentOrderNumber = orderNumber;
        this.currentMeal = meal;
        this.currentTimeLeft = timeLeft;
    }

    /**
     * Clears big ticket information
     */
    private void clearBigTicketInfo() {
        this.currentOrderNumber = null;
        this.currentMeal = null;
        this.currentTimeLeft = null;
        //logger.info("Big ticket information cleared");
    }

    /**
     * Returns current big ticket information
     * @return String Array representation of big ticket details [orderNum, meal, time]
     */
    public String[] getCurrentBigTicketInfo() {
        return new String[]{currentOrderNumber, currentMeal, currentTimeLeft};
    }
}
