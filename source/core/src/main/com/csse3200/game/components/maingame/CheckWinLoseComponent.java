package com.csse3200.game.components.maingame;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.DayNightService;
import com.csse3200.game.services.ServiceLocator;

/**
 * The CheckWinLoseComponent is responsible for determining if a player has won or lost the game.
 * It evaluates the player's current gold and compares it against win and loss thresholds that adjust
 * dynamically based on the current day of the game. This component is attached to the player entity
 * and listens for changes in the player's stats (e.g., gold) and day progression.
 */
public class CheckWinLoseComponent extends Component {

    private CombatStatsComponent combatStatsComponent;
    private int baseWinAmount;
    private int baseLossThreshold;

    /**
     * Constructs a new CheckWinLoseComponent with specified win and loss thresholds.
     * The component listens for the player creation event to retrieve the player's combat stats.
     *
     * @param baseWinAmount The base amount of gold required to win on Day 1
     * @param baseLossThreshold The base amount of gold below which the player loses on Day 1
     */
    public CheckWinLoseComponent(int baseWinAmount, int baseLossThreshold) {
        this.baseWinAmount = baseWinAmount;
        this.baseLossThreshold = baseLossThreshold;

        // Listen for player creation and retrieve the CombatStatsComponent
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) ->   this.combatStatsComponent = player.getComponent(CombatStatsComponent.class));
    }

    /**
     * Checks the current game state and returns one of the following values:
     * - "WIN" if the player has enough gold to win after the final day
     * - "LOSE" if the player's gold is below the loss threshold
     * - "GAME_IN_PROGRESS" if the game is ongoing and no win/loss conditions have been met
     *
     * @return a string representing the current game state ("WIN", "LOSE", or "GAME_IN_PROGRESS")
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
     * Calculates the adjusted win amount based on the current day.
     * The win amount increases as the game progresses, making it harder to win on later days.
     *
     * @param currentDay The current day of the game
     * @return the adjusted win amount for the current day
     */
    public int getAdjustedWinAmount(int currentDay) {
        return baseWinAmount + (currentDay * 10);
    }

    /**
     * Calculates the adjusted loss threshold based on the current day.
     * The loss threshold increases as the game progresses, giving the player more room to lose on later days.
     *
     * @param currentDay The current day of the game
     * @return the adjusted loss threshold for the current day
     */
    public int getAdjustedLossThreshold(int currentDay) {
        return baseLossThreshold + (currentDay * 5);
    }

    /**
     * Determines if the player has lost the game.
     * The player loses if their gold falls below the adjusted loss threshold for the current day.
     *
     * @param adjustedLossThreshold The loss threshold for the current day
     * @return true if the player has less gold than the loss threshold, false otherwise
     */
    public boolean hasLost(int adjustedLossThreshold) {
        return combatStatsComponent != null && combatStatsComponent.getGold() < adjustedLossThreshold;
    }

    /**
     * Determines if the player has won the game.
     * The player wins if their gold equals or exceeds the adjusted win amount for the current day.
     *
     * @param adjustedWinAmount The win threshold for the current day
     * @return true if the player has equal or more gold than the win threshold, false otherwise
     */
    public boolean hasWon(int adjustedWinAmount) {
        return combatStatsComponent != null && combatStatsComponent.getGold() >= adjustedWinAmount;
    }

    public void decreaseLoseThreshold() {
        this.baseLossThreshold -= 10;
        this.baseWinAmount -= 10;
    }
}
