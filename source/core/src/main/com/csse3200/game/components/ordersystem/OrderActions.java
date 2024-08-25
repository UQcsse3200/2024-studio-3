package com.csse3200.game.components.ordersystem;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events related to the Order System and does something when
 * one of the events is triggered
 */
public class OrderActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(OrderActions.class);
    private GdxGame game;

    public OrderActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("addOrder", this::onAddOrder);
        entity.getEvents().addListener("removeOrder", this::onRemoveOrder);
        ServiceLocator.getDocketService().getEvents().addListener(
                "reorderDockets", MainGameOrderTicketDisplay::reorderDockets);
        entity.getEvents().addListener("moveOrder", this::onMoveOrder);
        entity.getEvents().addListener("changeColour", this::onChangeColour);
    }

    /**
     * Adds order to the line
     */
    private void onAddOrder() {
        logger.info("Add order");
        // do something
    }

    /**
     * Removes order from the line
     */
    private void onRemoveOrder() {
        logger.info("Remove order");
        // do something
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
