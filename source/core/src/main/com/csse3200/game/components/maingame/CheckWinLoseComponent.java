package com.csse3200.game.components.maingame;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;

public class CheckWinLoseComponent extends Component {

    private CombatStatsComponent combatStatsComponent;
    private int winAmount;
    private int loseThreshold;

    public CheckWinLoseComponent(int winAmount, int loseThreshold) {
        this.winAmount = winAmount;
        this.loseThreshold = loseThreshold;

        // Listen for player creation and retrieve the CombatStatsComponent
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
    }

    /**
     * Public method to check the game state and return "WIN", "LOSE", or "GAME_IN_PROGRESS".
     */
    public String checkGameState() {
        if (combatStatsComponent == null) {
            return "GAME_IN_PROGRESS";  // Ensuring combatStatsComponent is initialised
        }

        if (hasLost()) {
            return "LOSE";
        } else if (hasWon()) {
            return "WIN";
        } else {
            return "GAME_IN_PROGRESS";
        }
    }

    /**
     * Returns true if the player has lost (gold less than loseThreshold).
     */
    public boolean hasLost() {
        return combatStatsComponent != null && combatStatsComponent.getGold() < loseThreshold;
    }

    /**
     * Returns true if the player has won (gold >= winAmount).
     */
    public boolean hasWon() {
        return combatStatsComponent != null && combatStatsComponent.getGold() >= winAmount;
    }

    public void decreaseLoseThreshold(){loseThreshold = loseThreshold - 10;}
}
