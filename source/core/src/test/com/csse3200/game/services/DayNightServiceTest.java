package com.csse3200.game.services;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.maingame.CheckWinLoseComponent;
import com.csse3200.game.components.moral.MoralDecision;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class DayNightServiceTest {

    private GameTime gameTime;
    private EventHandler enddayEventHandler;
    private EventHandler docketServiceEventHandler;
    private EntityService entityService;
    private DayNightService dayNightService;


    @BeforeEach
    public void setUp() {
        gameTime = mock(GameTime.class);
        when(gameTime.getTime()).thenReturn(0L);
        ServiceLocator.registerTimeSource(gameTime);
        GdxGame gdxGame = mock(GdxGame.class);
        ServiceLocator.registerGame(gdxGame);

        MoralDecision moralDecision = mock(MoralDecision.class);
        Entity moralSystem = mock(Entity.class);
        moralSystem.addComponent(moralDecision);

        entityService = mock(EntityService.class);
        when(entityService.getEvents()).thenReturn(new EventHandler());
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.getEntityService().registerMoralSystem(moralSystem);

        PlayerService playerService = mock(PlayerService.class);
        Entity playerEntity = mock(Entity.class);
        CheckWinLoseComponent checkWinLoseComponent = mock(CheckWinLoseComponent.class);
        CombatStatsComponent combatStatsComponent = mock(CombatStatsComponent.class);
        when(playerEntity.getComponent(CheckWinLoseComponent.class)).thenReturn(checkWinLoseComponent);
        when(playerEntity.getComponent(CombatStatsComponent.class)).thenReturn(combatStatsComponent);
        when(playerService.getPlayer()).thenReturn(playerEntity);
        ServiceLocator.registerPlayerService(playerService);

        enddayEventHandler = new EventHandler();
        docketServiceEventHandler = new EventHandler();

        dayNightService = new DayNightService(enddayEventHandler, docketServiceEventHandler);
        ServiceLocator.registerDayNightService(dayNightService);

        // Reset static day variable before each test
        dayNightService.setDay(1);
    }

    @AfterEach
    public void tearDown() {
        ServiceLocator.clear();
    }

    @Test
    public void testUpdateTriggersEndOfDay() {
        when(gameTime.getTime()).thenReturn(0L);

        AtomicBoolean disposeTriggered = new AtomicBoolean(false);
        AtomicBoolean endOfDayTriggered = new AtomicBoolean(false);
        docketServiceEventHandler.addListener("Dispose", () -> disposeTriggered.set(true));
        enddayEventHandler.addListener("endOfDay", () -> endOfDayTriggered.set(true));

        long currentTime = 0L;
        long timeStep = 1000L; // 1 second

        while (dayNightService.timeRemaining > 0) {
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

//    @Test
//    public void testDecisionDone() {
//        dayNightService.setDay(1); // Ensure the day starts at 1
//        AtomicBoolean isNewDay = new AtomicBoolean(false);
//        enddayEventHandler.addListener("newday", () -> isNewDay.set(true));
//        when(checkWinLoseComponent.checkGameState()).thenReturn("GAME_IN_PROGRESS");
//
//        enddayEventHandler.trigger("Yes");
//        Assertions.assertTrue(isNewDay.get(), "newday event was not triggered");
//
//        // Testing that the game time resumed and day incremented
//        verify(gameTime).setTimeScale(1);
//        Assertions.assertEquals(2, dayNightService.getDay(), "Day was not incremented correctly");
//    }

//    @Test
//    public void testApplyEndOfDayBonus() {
//        dayNightService.incrementHighQualityMealCount();
//        dayNightService.incrementHighQualityMealCount();
//        when(checkWinLoseComponent.checkGameState()).thenReturn("GAME_IN_PROGRESS");
//        enddayEventHandler.trigger("TOMORAL");
//
//
//        // Testing that 2 x bonus gold were added
//        verify(combatStatsComponent).addGold(2);
//    }

//    @Test
//    public void testResetHighQualityMealCount() {
//        dayNightService.incrementHighQualityMealCount();
//        dayNightService.incrementHighQualityMealCount();
//        when(checkWinLoseComponent.checkGameState()).thenReturn("GAME_IN_PROGRESS");
//        enddayEventHandler.trigger("YesAtMoralDecision");
//
//        // Testing that when new day starts, highQualityMeals resets
//        Assertions.assertEquals(0, dayNightService.getHighQualityMeals(), "High-quality meal count didnt reset");
//    }
//
//    @Test
//    public void testGameEndsAfterMaxDays() {
//        dayNightService.setDay(6);
//        AtomicBoolean endGameTriggered = new AtomicBoolean(false);
//        dayNightService.getEvents().addListener("endGame", () -> endGameTriggered.set(true));
//        enddayEventHandler.trigger("TOMORAL");
//
//        // Testing that "endGame" event was triggered
//        Assertions.assertTrue(endGameTriggered.get(), "endGame event was not triggered");
//    }
//
//    @Test
//    public void testStartNewDayWithLoseCondition() {
//        when(checkWinLoseComponent.checkGameState()).thenReturn("LOSE");
//        AtomicBoolean endGameTriggered = new AtomicBoolean(false);
//        dayNightService.getEvents().addListener("endGame", () -> endGameTriggered.set(true));
//        enddayEventHandler.trigger("TOMORAL");
//
//        // Testing that "endGame" event was triggered
//        Assertions.assertTrue(endGameTriggered.get(), "endGame event was not triggered");
//    }

    @Test
    public void testGetAndSetDay() {
        Assertions.assertEquals(1, dayNightService.getDay(), "Initial day incorrect");
        dayNightService.setDay(3);

        // Testing that day can be retrieved and is updated correctly
        Assertions.assertEquals(3, dayNightService.getDay(), "Day not set correctly");
    }

    @Test
    public void testIncrementHighQualityMealCount() {
        Assertions.assertEquals(0, dayNightService.getHighQualityMeals(), "Initial highQualityMeals count incorrect");

        // Testing that highQualityMeals is incremented once
        entityService.getEvents().trigger("mealHighQuality");
        Assertions.assertEquals(1, dayNightService.getHighQualityMeals(), "High-quality meal count didnt increment");

        // Testing that highQualityMeals is incremented a second time
        entityService.getEvents().trigger("mealHighQuality");
        Assertions.assertEquals(2, dayNightService.getHighQualityMeals(), "High-quality meal count didnt increment");
    }

//    @Test
//    public void testDaysIterateAndGameEndsAfterMaxDays() {
//        dayNightService.setDay(1); // Reset day before iterating through the days
//        AtomicBoolean endGameTriggered = new AtomicBoolean(false);
//        dayNightService.getEvents().addListener("endGame", () -> endGameTriggered.set(true));
//        when(checkWinLoseComponent.checkGameState()).thenReturn("GAME_IN_PROGRESS");
//
//        long currentTime = 0L;
//        long timeStep = 1000L; // 1 second
//
//        for (int expectedDay = 1; expectedDay <= DayNightService.MAX_DAYS; expectedDay++) {
//            while (dayNightService.timeRemaining > 0) {
//                when(gameTime.getTime()).thenReturn(currentTime);
//                dayNightService.update();
//                currentTime += timeStep;
//                enddayEventHandler.trigger("callpastsecond");
//            }
//
//            // Final update to trigger end-of-day events
//            when(gameTime.getTime()).thenReturn(currentTime);
//            dayNightService.update();
//            enddayEventHandler.trigger("TOMORAL");
//
//            // Verify that the day has incremented correctly and trigger is reset
//            if (expectedDay < DayNightService.MAX_DAYS) {
//                // For days before the last day, day should increment
//                Assertions.assertEquals(expectedDay + 1, dayNightService.getDay(), "Day didn't increment correctly after day " + expectedDay);
//            } else {
//                // On the last day, day should remain at MAX_DAYS
//                Assertions.assertEquals(DayNightService.MAX_DAYS, dayNightService.getDay(), "Day didn't increment correctly after day " + expectedDay);
//            }
//        }
//
//        // Verify that "endGame" event was triggered after max days
//        Assertions.assertTrue(endGameTriggered.get(), "endGame event was not triggered after max days");
//    }

}

