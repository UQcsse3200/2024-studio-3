package com.csse3200.game.entities.factories;

import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ServiceLocator;

/**
 * Creates new UI
 */
public class UIFactory {
    /** createDocketUI
     *
     * Creates a new entity with a new MainGameOrderTicketDisplay component added
     *
     * @return the entity with a MainGameOrderTicketDisplay component
     */
    public static Entity createDocketUI() {
        return new Entity().addComponent(new MainGameOrderTicketDisplay(ServiceLocator.getRenderService(), ServiceLocator.getPlayerService()));
//        return new Entity().addComponent(new MainGameOrderTicketDisplay());
    }
}
