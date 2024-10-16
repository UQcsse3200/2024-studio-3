package com.csse3200.game.services;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.maingame.CheckWinLoseComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
class DayNightServiceTest {

    private GameTime gameTime;
    private EventHandler enddayEventHandler;
    private EventHandler docketServiceEventHandler;
    private EntityService entityService;
    private PlayerService playerService;
    private Entity playerEntity;
    private CheckWinLoseComponent checkWinLoseComponent;
    private CombatStatsComponent combatStatsComponent;
    private DayNightService dayNightService;

    @BeforeEach
    public void setUp() {
        gameTime = mock(GameTime.class);
        when(gameTime.getTime()).thenReturn(0L);
        ServiceLocator.registerTimeSource(gameTime);

        entityService = mock(EntityService.class);
        when(entityService.getEvents()).thenReturn(new EventHandler());
        ServiceLocator.registerEntityService(entityService);

        playerService = mock(PlayerService.class);
        playerEntity = mock(Entity.class);
        checkWinLoseComponent = mock(CheckWinLoseComponent.class);
        combatStatsComponent = mock(CombatStatsComponent.class);
        when(playerEntity.getComponent(CheckWinLoseComponent.class)).thenReturn(checkWinLoseComponent);
        when(playerEntity.getComponent(CombatStatsComponent.class)).thenReturn(combatStatsComponent);
        when(playerService.getPlayer()).thenReturn(playerEntity);
        ServiceLocator.registerPlayerService(playerService);

        enddayEventHandler = new EventHandler();
        docketServiceEventHandler = new EventHandler();

        dayNightService = new DayNightService(enddayEventHandler, docketServiceEventHandler);
        ServiceLocator.registerDayNightService(dayNightService);
    }

    @AfterEach
    void tearDown() {
        ServiceLocator.clear();
    }

    @Test
    void testUpdateTriggersEndOfDay() {
        when(gameTime.getTime()).thenReturn(0L);

        AtomicBoolean disposeTriggered = new AtomicBoolean(false);
        AtomicBoolean endOfDayTriggered = new AtomicBoolean(false);
        docketServiceEventHandler.addListener("Dispose", () -> disposeTriggered.set(true));
        enddayEventHandler.addListener("endOfDay", () -> endOfDayTriggered.set(true));

        long currentTime = 0L;
        long timeStep = 1000L; // 1 second

        while (dayNightService.getTimeRemaining() > 0) {
            when(gameTime.getTime()).thenReturn(currentTime);
            dayNightService.update();

            currentTime += timeStep;
            enddayEventHandler.trigger("callpastsecond");
        }

        when(gameTime.getTime()).thenReturn(currentTime);
        dayNightService.update();

        // Testing the events were triggered
        Assertions.assertTrue(disposeTriggered.get(), "Dispose not triggered");
        Assertions.assertTrue(endOfDayTriggered.get(), "endOfDay not triggered");
        Assertions.assertTrue(dayNightService.getEndOfDayTriggered(), "End of day not triggered in service");
    }

    @Test
    void testDecisionDone() {
        AtomicBoolean isNewDay = new AtomicBoolean(false);
        enddayEventHandler.addListener("newday", () -> isNewDay.set(true));
        when(checkWinLoseComponent.checkGameState()).thenReturn("GAME_IN_PROGRESS");

        enddayEventHandler.trigger("decisionDone");
        Assertions.assertTrue(isNewDay.get(), "newday event was not triggered");

        // Testing that the game time resumed and day incremented
        verify(gameTime).setTimeScale(1);
        Assertions.assertEquals(2, dayNightService.getDay(), "Day was not incremented correctly");
    }

    @Test
    void testApplyEndOfDayBonus() {
        dayNightService.incrementHighQualityMealCount();
        dayNightService.incrementHighQualityMealCount();
        when(checkWinLoseComponent.checkGameState()).thenReturn("GAME_IN_PROGRESS");
        enddayEventHandler.trigger("decisionDone");

        // Testing that 2 x bonus gold were added
        verify(combatStatsComponent).addGold(2);
    }

    @Test
    void testResetHighQualityMealCount() {
        dayNightService.incrementHighQualityMealCount();
        dayNightService.incrementHighQualityMealCount();
        when(checkWinLoseComponent.checkGameState()).thenReturn("GAME_IN_PROGRESS");
        enddayEventHandler.trigger("decisionDone");

        // Testing that when new day starts, highQualityMeals resets
        Assertions.assertEquals(0, dayNightService.getHighQualityMeals(), "High-quality meal count didnt reset");
    }

    @Test
    void testGameEndsAfterMaxDays() {
        dayNightService.setDay(6);
        AtomicBoolean endGameTriggered = new AtomicBoolean(false);
        dayNightService.getEvents().addListener("endGame", () -> endGameTriggered.set(true));
        enddayEventHandler.trigger("decisionDone");

        // Testing that "endGame" event was triggered
        Assertions.assertTrue(endGameTriggered.get(), "endGame event was not triggered");
    }

    @Test
    void testStartNewDayWithLoseCondition() {
        when(checkWinLoseComponent.checkGameState()).thenReturn("LOSE");
        AtomicBoolean endGameTriggered = new AtomicBoolean(false);
        dayNightService.getEvents().addListener("endGame", () -> endGameTriggered.set(true));
        enddayEventHandler.trigger("decisionDone");

        // Testing that "endGame" event was triggered
        Assertions.assertTrue(endGameTriggered.get(), "endGame event was not triggered");
    }

    @Test
    void testGetAndSetDay() {
        Assertions.assertEquals(1, dayNightService.getDay(), "Initial day incorrect");
        dayNightService.setDay(3);

        // Testing that day can be retrieved and is updated correctly
        Assertions.assertEquals(3, dayNightService.getDay(), "Day not set correctly");
    }

    @Test
    void testIncrementHighQualityMealCount() {
        Assertions.assertEquals(0, dayNightService.getHighQualityMeals(), "Initial highQualityMeals count incorrect");

        // Testing that highQualityMeals is incremented once
        entityService.getEvents().trigger("mealHighQuality");
        Assertions.assertEquals(1, dayNightService.getHighQualityMeals(), "High-quality meal count didnt increment");

        // Testing that highQualityMeals is incremented a second time
        entityService.getEvents().trigger("mealHighQuality");
        Assertions.assertEquals(2, dayNightService.getHighQualityMeals(), "High-quality meal count didnt increment");
    }

    @Test
    void testDaysIterateAndGameEndsAfterMaxDays() {
        AtomicBoolean endGameTriggered = new AtomicBoolean(false);
        dayNightService.getEvents().addListener("endGame", () -> endGameTriggered.set(true));
        when(checkWinLoseComponent.checkGameState()).thenReturn("GAME_IN_PROGRESS");

        long currentTime = 0L;
        long timeStep = 1000L; // 1 second

        for (int expectedDay = 1; expectedDay <= 5; expectedDay++) {
            while (dayNightService.getTimeRemaining() > 0) {

                when(gameTime.getTime()).thenReturn(currentTime);
                dayNightService.update();
                currentTime += timeStep;
                enddayEventHandler.trigger("callpastsecond");
            }

            // Final update to trigger end-of-day events
            when(gameTime.getTime()).thenReturn(currentTime);
            dayNightService.update();
            enddayEventHandler.trigger("decisionDone");

            // Verify that the day has incremented correctly and trigger is reset
            Assertions.assertEquals(expectedDay + 1, dayNightService.getDay(), "Day didnt increment correctly after day " + expectedDay);
            Assertions.assertFalse(dayNightService.getEndOfDayTriggered(), "endOfDayTriggered was not reset for day " + (expectedDay + 1));
        }

        while (dayNightService.getTimeRemaining() > 0) {
            when(gameTime.getTime()).thenReturn(currentTime);
            dayNightService.update();

            currentTime += timeStep;
            enddayEventHandler.trigger("callpastsecond");
        }

        when(gameTime.getTime()).thenReturn(currentTime);
        dayNightService.update();
        enddayEventHandler.trigger("decisionDone");

        // Verify that "endGame" event was triggered after day 5 is done
        Assertions.assertTrue(endGameTriggered.get(), "endGame event was not triggered after max days");
    }
}

