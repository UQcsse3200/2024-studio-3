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
import com.csse3200.game.components.CombatStatsComponent;

public class DancePartyUpgrade extends Component implements Upgrade {
    private static final long UPGRADE_DURATION = 60000;
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

    @Override
    public void activate() {
        if (!isActive && startTime == -1 && combatStatsComponent.getGold() >= 20) {
            activeTimeRemaining = UPGRADE_DURATION;
            dancePartyCost();
            isActive = true;
        }
    }

    @Override
    public void deactivate() {
        startTime = -1;
        isActive = false;
    }

    public void update() {
        if (isActive && gameTime.getDeltaTime() - startTime >= UPGRADE_DURATION) {
            deactivate();
        }
    }

    public void dancePartyCost() {
        combatStatsComponent.addGold(-20);
    }
}
