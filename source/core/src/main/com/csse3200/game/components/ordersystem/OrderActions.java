package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.csse3200.game.GdxGame;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;



/**
 * This class listens to key presses to shift dockets left and right and handles
 * order-related actions in the game. - Tia
 */

public class OrderActions extends InputComponent {
    private static final Logger logger = LoggerFactory.getLogger(OrderActions.class);
    private GdxGame game;
    private static final int SHIFT_LEFT_KEY = Input.Keys.LEFT_BRACKET; // Key for shifting left
    private static final int SHIFT_RIGHT_KEY = Input.Keys.RIGHT_BRACKET; // Key for shifting right


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
     * event listeners for order-related actions. - Tia/ Michael?
     */
    @Override
    public void create() {

        ServiceLocator.getInputService().register(this);
        entity.getEvents().addListener("addOrder", this::onAddOrder);

        ServiceLocator.getDocketService().getEvents().addListener("removeOrder", this::onRemoveOrder);
        ServiceLocator.getDocketService().getEvents().addListener(
                "reorderDockets", MainGameOrderTicketDisplay::reorderDockets);
        entity.getEvents().addListener("moveOrder", this::onMoveOrder);
        entity.getEvents().addListener("changeColour", this::onChangeColour);

        
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

    logger.info("pls work");
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
        ServiceLocator.getDocketService().getEvents().trigger("reorderDockets", index);
    }

    /**
     * Moves order
     */
    private void onMoveOrder() {
        logger.info("Move order");
        // do something
    }

    /**
     * Changes order colour based on recipe timer
     */
    private void onChangeColour() {
        logger.info("Move order");
        // do something
    }

}
