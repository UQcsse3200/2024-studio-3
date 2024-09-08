package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.csse3200.game.GdxGame;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to key presses to shift dockets left and right and handles
 * order-related actions in the game.
 */
public class OrderActions extends InputComponent {
    private static final Logger logger = LoggerFactory.getLogger(OrderActions.class);
    private GdxGame game;
    private static final int SHIFT_LEFT_KEY = Input.Keys.LEFT_BRACKET; // Key for shifting left
    private static final int SHIFT_RIGHT_KEY = Input.Keys.RIGHT_BRACKET; // Key for shifting right

    // key values of big ticket
    private String currentOrderNumber;
    private String currentMeal;
    private String currentTimeLeft;

    /**
     * Constructs an OrderActions instance with a reference to the main game object. - Tia
     *
     * @param game the main game instance
     */
    public OrderActions(GdxGame game) {
        super(5);
        this.game = game;
    }

    /**
     * Initialises the OrderActions component by registering input listeners and
     * event listeners for order-related actions.
     */
    @Override
    public void create() {

        ServiceLocator.getInputService().register(this);
        entity.getEvents().addListener("addOrder", this::onAddOrder);

        ServiceLocator.getDocketService().getEvents().addListener("removeOrder", this::onRemoveOrder);
        ServiceLocator.getDocketService().getEvents().addListener(
                "reorderDockets", MainGameOrderTicketDisplay::reorderDockets);
        entity.getEvents().addListener("changeColour", this::onChangeColour);

        ServiceLocator.getDocketService().getEvents().addListener("updateBigTicket", this::onUpdateBigTicket); // update big ticket values
        //ServiceLocator.getDocketService().getEvents().addListener("getTicketDetails", this::getCurrentBigTicketInfo); // dont like this but w/e for now
    }

    /**
     * Handles key press events. Shifts dockets left or right based on the pressed key. - Tia
     *
     * @param keycode the code of the pressed key
     * @return true if the key event was handled, false otherwise
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.LEFT_BRACKET) {
            logger.info("Shift dockets left");
            ServiceLocator.getDocketService().getEvents().trigger("shiftDocketsLeft");
            return true;
        } else if (keycode == Keys.RIGHT_BRACKET) {
            logger.info("Shift dockets right");
            ServiceLocator.getDocketService().getEvents().trigger("shiftDocketsRight");
            return true;
        }

//        logger.info("pls work");
        return false;
    }

     /**
     * Handles key release events. Currently does nothing. - Tia
     *
     * @param keycode the code of the released key
     * @return false, as no action is taken
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    /**
     * Handles key typed events. Currently does nothing. - Tia
     *
     * @param character the typed character
     * @return false, as no action is taken
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Handles the event to add an order to the line. Tia
     */
    private void onAddOrder() {
        logger.info("Add order");
    }

    /**
     * Handles the event to remove an order from the line.
     *
     * @param index the index of the order to be removed
     */
    private void onRemoveOrder(int index) {
        logger.info("Remove order");
        if (index == - 1) { // remove big ticket details
            clearBigTicketInfo();
        }
        ServiceLocator.getDocketService().getEvents().trigger("reorderDockets", index);
    }

    /**
     * Changes order colour based on recipe timer
     */
    private void onChangeColour() {
        logger.info("Move order");
        // do something
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
        //logger.info("Big ticket updated: Order {}, Meal: {}, Time Left: {}", orderNumber, meal, timeLeft);
        // logs the correct details
    }

    /**
     * Clears big ticket information
     */
    private void clearBigTicketInfo() {
        this.currentOrderNumber = null;
        this.currentMeal = null;
        this.currentTimeLeft = null;
        logger.info("Big ticket information cleared");
    }

    /**
     * Returns current big ticket information
     * @return String Array representation of big ticket details [orderNum, meal, time]
     */
    public String[] getCurrentBigTicketInfo() {
        logger.info("Big ticket updated: Order {}, Meal: {}, Time Left: {}", currentOrderNumber, currentMeal, currentTimeLeft);
        // logs null.
        return new String[]{currentOrderNumber, currentMeal, currentTimeLeft};
    }
}
