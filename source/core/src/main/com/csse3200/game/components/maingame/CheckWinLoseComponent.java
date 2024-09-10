package com.csse3200.game.components.maingame;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;

public class CheckWinLoseComponent extends Component {

    private CombatStatsComponent combatStatsComponent;
    private float winAmount;
    private float loseThreshold;
    private float mealQuality;
    private int storeRating;

    public CheckWinLoseComponent(float winAmount, float loseThreshold, float mealQuality) {
        this.winAmount = winAmount;
        this.loseThreshold = loseThreshold;
        this.mealQuality = mealQuality;
        this.storeRating = calculateStoreRating();

        // Listen for player creation and retrieve the CombatStatsComponent
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
    }

    public void updateGameState(float mealQuality) {
        this.mealQuality = mealQuality;
        this.storeRating = calculateStoreRating();
    }

    public int calculateStoreRating() {
        if (mealQuality >= 0 && mealQuality < 20) {
            return 1;
        } else if (mealQuality >= 20 && mealQuality < 40) {
            return 2;
        } else if (mealQuality >= 40 && mealQuality < 60) {
            return 3;
        } else if (mealQuality >= 60 && mealQuality < 80) {
            return 4;
        } else {
            return 5;
        }
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
        if (storeRating <= 2) {
            return true;
        }
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
