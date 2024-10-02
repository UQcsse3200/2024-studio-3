package com.csse3200.game.components.maingame;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.DayNightService;
import com.csse3200.game.services.ServiceLocator;

public class CheckWinLoseComponent extends Component {

    private CombatStatsComponent combatStatsComponent;
    private int baseWinAmount;
    private int baseLossThreshold;

    public CheckWinLoseComponent(int baseWinAmount, int baseLossThreshold) {
        this.baseWinAmount = baseWinAmount;
        this.baseLossThreshold = baseLossThreshold;

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

        // Get the current day from DayNightService
        int currentDay = ServiceLocator.getDayNightService().getDay();

        // Adjust win and loss thresholds based on the current day
        int adjustedWinAmount = getAdjustedWinAmount(currentDay);
        int adjustedLossThreshold = getAdjustedLossThreshold(currentDay);

        if (hasLost(adjustedLossThreshold)) {
            return "LOSE";
        } else if (currentDay == DayNightService.MAX_DAYS && hasWon(adjustedWinAmount)) {
            return "WIN";
        } else {
            return "GAME_IN_PROGRESS";
        }
    }

    /**
     * Calculate the adjusted win amount based on the current day.
     */
    private int getAdjustedWinAmount(int currentDay) {
        // Increase the win amount as the days progress
        return baseWinAmount + (currentDay * 10);  // Example: increase by 10 per day
    }

    /**
     * Calculate the adjusted loss threshold based on the current day.
     */
    private int getAdjustedLossThreshold(int currentDay) {
        // Increase the loss threshold as the days progress
        return baseLossThreshold + (currentDay * 5);  // Example: increase by 5 per day
    }

    /**
     * Returns true if the player has lost (gold less than lossThreshold).
     */
    public boolean hasLost(int adjustedLossThreshold) {
        return combatStatsComponent != null && combatStatsComponent.getGold() < adjustedLossThreshold;
    }

    /**
     * Returns true if the player has won (gold >= winAmount).
     */
    public boolean hasWon(int adjustedWinAmount) {
        return combatStatsComponent != null && combatStatsComponent.getGold() >= adjustedWinAmount;
    }
}
