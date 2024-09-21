package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.player.KeyboardPlayerInputComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;

public class SpeedBootsUpgrade extends Component {

    private static final long BOOST_DURATION = 60000;
    private static final float NORMAL_SPEED = 1f;
    private static final float BOOSTED_SPEED = 2f;
    private CombatStatsComponent combatStatsComponent;
    private KeyboardPlayerInputComponent keyboardPlayerInputComponent;
    private final GameTime gameTime;
    private long boostStartTime = -1;

    public SpeedBootsUpgrade() {
        super();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
            this.keyboardPlayerInputComponent = player.getComponent(KeyboardPlayerInputComponent.class);
        });
        gameTime = ServiceLocator.getTimeSource();
    }

    @Override
    public void update() {
        if (keyboardPlayerInputComponent != null) {
            long current = gameTime.getTime();
            if (Gdx.input.isKeyJustPressed(Input.Keys.B) && boostStartTime == -1) {
                activate();
            }
            if (boostStartTime != -1 && current - boostStartTime > BOOST_DURATION) {
                deactivate();
            }
        }
    }

    public void activate() {
        keyboardPlayerInputComponent.setWalkSpeed(BOOSTED_SPEED);
        boostStartTime = gameTime.getTime();
        speedCost();
    }

    public void deactivate() {
        keyboardPlayerInputComponent.setWalkSpeed(NORMAL_SPEED);
        boostStartTime = -1;
    }

    public void speedCost() {
        combatStatsComponent.addGold(-20);
    }
}
