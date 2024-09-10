package com.csse3200.game.components.maingame;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;

public class CheckWinLoseComponent extends Component {

    private CombatStatsComponent combatStatsComponent;
    private float winAmount;
    private float loseThreshold;

    public CheckWinLoseComponent(float winAmount, float loseThreshold) {
        this.winAmount = winAmount;
        this.loseThreshold = loseThreshold;

        // Listen for player creation and retrieve the CombatStatsComponent
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
    }

    public String checkGameState() {
        if (combatStatsComponent == null) {
            return "GAME_IN_PROGRESS";  // Ensuring combatStatsComponent is initialized
        }

        if (checkLose()) {
            triggerLoseState();
            return "LOSE";
        } else if (checkWin()) {
            triggerWinState();
            return "WIN";
        } else {
            return "GAME_IN_PROGRESS";
        }
    }

    private boolean checkLose() {
        if (combatStatsComponent.getGold() < loseThreshold) {
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        if (combatStatsComponent.getGold() >= winAmount) {
            return true;
        }
        return false;
    }

    private void triggerLoseState() {
        // Need to add logic for losing (trigger cutscenes, end game)
    }

    private void triggerWinState() {
        // Need to add logic for winning (trigger cutscenes, end game)
    }
}
