package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Input.Keys;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to key presses to shift dockets left and right.
 */
public class OrderActions extends InputComponent {
    private static final Logger logger = LoggerFactory.getLogger(OrderActions.class);
    private MainGameOrderTicketDisplay orderTicketDisplay;

    public OrderActions(MainGameOrderTicketDisplay orderTicketDisplay) {
        this.orderTicketDisplay = orderTicketDisplay;
    }

    @Override
    public boolean keyDown(int keycode) {
        MainGameOrderTicketDisplay orderTicketDisplay = ServiceLocator.getDocketService().getOrderTicketDisplay();

        if (keycode == Keys.LEFT_BRACKET) {
            logger.info("Shift dockets left");
            orderTicketDisplay.shiftDocketsLeft();
            return true;
        } else if (keycode == Keys.RIGHT_BRACKET) {
            logger.info("Shift dockets right");
            orderTicketDisplay.shiftDocketsRight();
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
}
