package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.components.ordersystem.OrderManager;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtortionUpgrade {
    private static final Logger logger = LoggerFactory.getLogger(ExtortionUpgrade.class);
    private boolean isActive;
    private long upgradeDuration;
    private final GameTime gameTime;
    private long actviateTime;
    private CombatStatsComponent combatStatsComponent;
    //TODO will need to add whatever currency dependencies as well as morality and order stuff

    public ExtortionUpgrade(long upgradeDuration, GameTime gameTime) {
        this.upgradeDuration = upgradeDuration;
        this.isActive = false;
        this.gameTime = gameTime;
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) ->
        {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Activates the extortion upgrade
     */
    public void activate() {
        this.actviateTime = gameTime.getTime();
        this.isActive = true;

        //Placeholder for halving reputation; subtract gold instead
        if (combatStatsComponent.getGold() >= 40) combatStatsComponent.addGold(-40);
        //Upon activation, double gold from orders
        getTickets().goldMultiplier = 2;
    }

    /**
     * @return instance of MainGameorderTicketDisplay if it exists
     */
    public MainGameOrderTicketDisplay getTickets() {
        //TODO it would be nice to move this to the constructor, however there is no event triggered
        for (Entity entity : ServiceLocator.getEntityService().getEntities()) {
            MainGameOrderTicketDisplay component = entity.getComponent(MainGameOrderTicketDisplay.class);

            if (component != null) {
                return component;
            }
        }
        return null;
    }

    /**
     * Deactivates the extortion upgrade
     */
    public void deactivate() {
        this.isActive = false;
        getTickets().goldMultiplier = 1;
    }

    /**
     * checks to see if the duration of ugprade has ended and consequently deactivates
     */
    public void update() {
        if (isActive && (gameTime.getTime() - actviateTime >= upgradeDuration)) {
            deactivate();
        }
    }

}
