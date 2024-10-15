package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Input.Keys;
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

    /**
     * Constructs an OrderActions instance.
     */
    public OrderActions() {
        super(5);
    }

    /**
     * Initialises the OrderActions component by registering input listeners and
     * event listeners for order-related actions.
     */
    @Override
    public void create() {
        ServiceLocator.getInputService().register(this);
        ServiceLocator.getDocketService().getEvents().addListener("removeOrder", this::onRemoveOrder);
        ServiceLocator.getDocketService().getEvents().addListener("reorderDockets", MainGameOrderTicketDisplay::reorderDockets);
    }

    /**
     * Handles key press events. Shifts dockets left or right based on the pressed key.
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

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Handles the event to remove an order from the line.
     *
     * @param index the index of the order to be removed
     */
    void onRemoveOrder(int index) {
        logger.info("Remove order");
        ServiceLocator.getDocketService().getEvents().trigger("reorderDockets", index);
    }

    /**
     * Get the logger instance for testing purposes.
     *
     * @return the logger instance.
     */
    public Logger getLogger() {
        return logger;
    }
}
