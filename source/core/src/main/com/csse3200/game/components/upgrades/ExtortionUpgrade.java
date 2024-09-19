package com.csse3200.game.components.upgrades;

import com.csse3200.game.components.ordersystem.OrderManager;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.services.GameTime;

public class ExtortionUpgrade {

    private boolean isActive;
    private long upgradeDuration;
    private final GameTime gameTime;
    private long actviateTime;
    //TODO will need to add whatever currency dependencies as well as morality and order stuff

    public ExtortionUpgrade(long upgradeDuration, GameTime gameTime) {
        this.upgradeDuration = upgradeDuration;
        this.isActive = false;
        this.gameTime = gameTime;
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
    }

    /**
     * Deactivates the extortion upgrade
     */
    public void deactivate() {
        this.isActive = false;
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
