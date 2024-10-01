package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.components.ordersystem.OrderManager;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtortionUpgrade extends Component implements Upgrade {
    //private static final Logger logger = LoggerFactory.getLogger(ExtortionUpgrade.class);
    private boolean isActive;
    private final long upgradeDuration = 30000;
    private final GameTime gameTime;
    private long actviateTime;
    private CombatStatsComponent combatStatsComponent;
    //TODO will need to add whatever currency dependencies as well as morality and order stuff

    public ExtortionUpgrade() {
        this.isActive = false;
        this.gameTime = ServiceLocator.getTimeSource();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) ->
        {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
        ServiceLocator.getRandomComboService().getEvents().addListener("Extortion", this::activate); 
    }

    /**
     * Activates the extortion upgrade
     */
    public void activate() {
        if (combatStatsComponent.getGold() >= 40){
            this.actviateTime = gameTime.getTime();
            this.isActive = true;
            combatStatsComponent.addGold(-40);
            ServiceLocator.getRandomComboService().getEvents().trigger("ExtortionActive", true);

        } else {
            ServiceLocator.getRandomComboService().getEvents().trigger("notenoughmoney");
        }
    }

    /**
     * Deactivates the extortion upgrade
     */
    public void deactivate() {
        this.isActive = false;
        ServiceLocator.getRandomComboService().getEvents().trigger("ExtortionActive", false);
    }

    public boolean isActive() {
        return isActive;
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
