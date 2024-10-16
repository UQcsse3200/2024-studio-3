package com.csse3200.game.components;

import com.csse3200.game.components.maingame.CheckWinLoseComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.DayNightService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.events.EventHandler;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class CheckWinLoseComponentTest {

    private CheckWinLoseComponent checkWinLoseComponent;
    private CombatStatsComponent combatStatsComponent;
    private PlayerService playerService;
    private DayNightService dayNightService;
    private Entity playerEntity;

    @BeforeEach
    public void setUp() {
        playerService = mock(PlayerService.class);
        ServiceLocator.registerPlayerService(playerService);

        EventHandler playerEvents = new EventHandler();
        when(playerService.getEvents()).thenReturn(playerEvents);

        playerEntity = mock(Entity.class);
        combatStatsComponent = mock(CombatStatsComponent.class);
        when(playerEntity.getComponent(CombatStatsComponent.class)).thenReturn(combatStatsComponent);
        when(playerService.getPlayer()).thenReturn(playerEntity);

        dayNightService = mock(DayNightService.class);
        ServiceLocator.registerDayNightService(dayNightService);

        checkWinLoseComponent = new CheckWinLoseComponent(55, 55);
    }

    @AfterEach
    public void tearDown() {
        ServiceLocator.clear();
    }

    @Test
    public void testCheckGameStateReturnsWinOnlyOnFinalDay() {
        when(dayNightService.getDay()).thenReturn(DayNightService.MAX_DAYS);

        int adjustedWinAmount = 55 + (DayNightService.MAX_DAYS * 10);
        int playerGold = adjustedWinAmount + 5;
        when(combatStatsComponent.getGold()).thenReturn(playerGold);

        playerService.getEvents().trigger("playerCreated", playerEntity);
        String gameState = checkWinLoseComponent.checkGameState();

        // Testing that the player 'wins'
        Assertions.assertEquals("WIN", gameState);
    }

    @Test
    public void testCheckGameStateReturnsGameInProgressWhenGoldBetweenThresholds() {
        when(dayNightService.getDay()).thenReturn(2);

        int adjustedLossThreshold = 55 + (2 * 5);
        int adjustedWinAmount = 55 + (2 * 10);
        int playerGold = adjustedLossThreshold + 5;
        when(combatStatsComponent.getGold()).thenReturn(playerGold);

        playerService.getEvents().trigger("playerCreated", playerEntity);
        String gameState = checkWinLoseComponent.checkGameState();

        // Testing that the player's game is still 'in progress'
        Assertions.assertEquals("GAME_IN_PROGRESS", gameState);
    }

    @Test
    public void testCheckGameStateReturnsLoseWhenGoldBelowThreshold() {
        when(dayNightService.getDay()).thenReturn(1);

        int adjustedLossThreshold = 55 + (1 * 5);
        int playerGold = adjustedLossThreshold - 1;
        when(combatStatsComponent.getGold()).thenReturn(playerGold);

        playerService.getEvents().trigger("playerCreated", playerEntity);
        String gameState = checkWinLoseComponent.checkGameState();

        // Testing that the player 'loses'
        Assertions.assertEquals("LOSE", gameState);
    }

    @Test
    public void testCheckGameStateReturnsGameInProgressWhenGoldAboveWinAmountBeforeFinalDay() {
        when(dayNightService.getDay()).thenReturn(DayNightService.MAX_DAYS - 1);

        int adjustedWinAmount = 55 + ((DayNightService.MAX_DAYS - 1) * 10);
        int playerGold = adjustedWinAmount + 10;
        when(combatStatsComponent.getGold()).thenReturn(playerGold);

        playerService.getEvents().trigger("playerCreated", playerEntity);
        String gameState = checkWinLoseComponent.checkGameState();

        // Testing that the player's game is still 'in progress'
        Assertions.assertEquals("GAME_IN_PROGRESS", gameState);
    }

    @Test
    public void testHasWonReturnsTrueWhenGoldAboveWinAmount() {
        when(combatStatsComponent.getGold()).thenReturn(65);
        playerService.getEvents().trigger("playerCreated", playerEntity);
        int adjustedWinAmount = 60;

        // Testing hasWon() returns true when the player has more gold than the win amount
        Assertions.assertTrue(checkWinLoseComponent.hasWon(adjustedWinAmount));
    }

    @Test
    public void testHasLostReturnsTrueWhenGoldBelowThreshold() {
        when(combatStatsComponent.getGold()).thenReturn(55);
        playerService.getEvents().trigger("playerCreated", playerEntity);
        int adjustedLossThreshold = 60;

        // Testing hasLost() returns true when the player has less gold than the loss threshold
        Assertions.assertTrue(checkWinLoseComponent.hasLost(adjustedLossThreshold));
    }

    @Test
    public void testCheckGameStateWhenGoldEqualsLossThreshold() {
        when(dayNightService.getDay()).thenReturn(3);
        int adjustedLossThreshold = 55 + (3 * 5);
        when(combatStatsComponent.getGold()).thenReturn(adjustedLossThreshold);

        playerService.getEvents().trigger("playerCreated", playerEntity);
        String gameState = checkWinLoseComponent.checkGameState();

        // Testing that the player's game is still 'in progress'
        Assertions.assertEquals("GAME_IN_PROGRESS", gameState);
    }

    @Test
    public void testAdjustedWinAndLossThresholdCalculations() {
        for (int day = 1; day <= 5; day++) {
            int expectedWinAmount = 55 + (day * 10);
            int expectedLossThreshold = 55 + (day * 5);

            int actualWinAmount = checkWinLoseComponent.getAdjustedWinAmount(day);
            int actualLossThreshold = checkWinLoseComponent.getAdjustedLossThreshold(day);

            Assertions.assertEquals(expectedWinAmount, actualWinAmount);
            Assertions.assertEquals(expectedLossThreshold, actualLossThreshold);
        }
    }

    @Test
    public void testCheckGameStateWhenGoldEqualsWinAmountOnFinalDay() {
        when(dayNightService.getDay()).thenReturn(DayNightService.MAX_DAYS);

        int adjustedWinAmount = 55 + (DayNightService.MAX_DAYS * 10);
        when(combatStatsComponent.getGold()).thenReturn(adjustedWinAmount);

        playerService.getEvents().trigger("playerCreated", playerEntity);
        String gameState = checkWinLoseComponent.checkGameState();

        // Testing that the player 'wins'
        Assertions.assertEquals("WIN", gameState);
    }

    @Test
    public void testCheckGameStateReturnsGameInProgressWhenCombatStatsComponentIsNull() {
        String gameState = checkWinLoseComponent.checkGameState();

        // Testing that the player's game is still 'in progress'
        Assertions.assertEquals("GAME_IN_PROGRESS", gameState);
    }

    @Test
    public void testCheckGameStateWithNegativeGold() {
        when(dayNightService.getDay()).thenReturn(2);
        when(combatStatsComponent.getGold()).thenReturn(-10);

        playerService.getEvents().trigger("playerCreated", playerEntity);
        String gameState = checkWinLoseComponent.checkGameState();

        // Testing that the player 'loses'
        Assertions.assertEquals("LOSE", gameState);
    }
}
