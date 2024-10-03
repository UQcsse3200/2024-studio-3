package com.csse3200.game.components.upgrades;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.components.ordersystem.OrderManager;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.services.GameTime;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.csse3200.game.components.CombatStatsComponent;

/**
 * The DancePartyUpgrade class represents an upgrade that, when activated,
 * triggers a "Dancing" which causes current dockets within the game to pause
 * Their time, within the MainGameOrderTicketDisplay
 */

public class DancePartyUpgrade extends Component implements Upgrade {
    private static final long UPGRADE_DURATION = 3000;
    private OrderManager orderManager;
    private final GameTime gameTime;
    private boolean isActive;
    private long startTime = -1;
    private float activeTimeRemaining;
    private CombatStatsComponent combatStatsComponent;

    public DancePartyUpgrade() {
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
        this.orderManager = orderManager;
        gameTime = ServiceLocator.getTimeSource();
        this.isActive = false;
    }

     /**
     * Activates the Dance Party Upgrade if conditions are met:
     * by triggering Dancing in get Docketservice, pausing time
     */
    @Override
    public void activate() {
        if (!isActive && startTime == -1 && combatStatsComponent.getGold() >= 20) {
            activeTimeRemaining = UPGRADE_DURATION;
            dancePartyCost();
            isActive = true;
            ServiceLocator.getDocketService().getEvents().trigger("Dancing"); 
        }
    }

    /**
     * Deactivates the Dance Party Upgrade 
     * by triggering UnDancing in get Docketservice, unpausing time
     */
    @Override
    public void deactivate() {
        startTime = -1;
        isActive = false;
        ServiceLocator.getDocketService().getEvents().trigger("UnDancing"); 
    }


    public void dancePartyCost() {
        combatStatsComponent.addGold(-20);
    }

    @Override
    public void update() {
        // Check if the 'L' key is pressed in each frame
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            if(isActive){
                deactivate();
            }
            else{
                activate();
            }
        }
    }
}
