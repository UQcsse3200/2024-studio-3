package com.csse3200.game.components.upgrades;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;


/**
 * Manages the Extortion Upgrade component, allowing players to receive extra
 * gold per customer
 */
public class ExtortionUpgrade extends Component implements Upgrade {
    private boolean isActive;
    private final GameTime gameTime;
    private long actviateTime;
    private CombatStatsComponent combatStatsComponent;

    public ExtortionUpgrade() {
        this.isActive = false;
        this.gameTime = ServiceLocator.getTimeSource();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) ->
                this.combatStatsComponent = player.getComponent(CombatStatsComponent.class));
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
    @Override
    public void update() {
        long upgradeDuration = 30000;
        if (isActive && (gameTime.getTime() - actviateTime >= upgradeDuration)) {
            deactivate();
        }
    }

}

