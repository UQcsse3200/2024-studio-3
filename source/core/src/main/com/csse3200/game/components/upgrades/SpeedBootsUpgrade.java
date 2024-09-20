package com.csse3200.game.components.upgrades;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;

public class SpeedBootsUpgrade {

    private CombatStatsComponent combatStatsComponent;

    public SpeedBootsUpgrade() {
        super();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
    }

    public void speedCost() {
        combatStatsComponent.addGold(-50);
    }
}
