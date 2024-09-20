package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;

public class SpeedBootsUpgrade extends Component {

    private CombatStatsComponent combatStatsComponent;

    public SpeedBootsUpgrade() {
        super();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
    }
    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            speedCost();
        }
    }

    public void speedCost() {
        combatStatsComponent.addGold(-20);
    }
}
