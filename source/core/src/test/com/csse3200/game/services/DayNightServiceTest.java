package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class DayNightServiceTest {

    @Test
    public void testUpdate() {
        GameTime gameTime = mock(GameTime.class);
        ServiceLocator.registerTimeSource(gameTime);
        when(gameTime.getTime()).thenReturn(0L);

        EventHandler mockEndDayEventHandler = mock(EventHandler.class);
        EventHandler mockDocketServiceEventHandler = mock(EventHandler.class);

        DayNightService dayNightService = new DayNightService(mockEndDayEventHandler, mockDocketServiceEventHandler);

        ServiceLocator.registerDayNightService(dayNightService);

        when(gameTime.getTime()).thenReturn(5 * 60 * 1000L);
        dayNightService.update();
        //verify(mockDocketServiceEventHandler).trigger("Dispose");
        //verify(mockEndDayEventHandler).trigger("endOfDay");
    }

    // Need to have a mock player for this to work !!
//    @Test
//    public void testDecisionAndAnimationDone() {
//        GameTime gameTime = mock(GameTime.class);
//        ServiceLocator.registerTimeSource(gameTime);
//
//        // Create the event handlers
//        EventHandler enddayEventHandler = new EventHandler();
//        EventHandler mockDocketServiceEventHandler = mock(EventHandler.class);
//
//        // Initialize DayNightService with a valid day range
//        DayNightService dayNightService = new DayNightService(enddayEventHandler, mockDocketServiceEventHandler);
//        ServiceLocator.registerDayNightService(dayNightService);
//
//        // Set day to a valid value within the range (e.g., day 1-4)
//        dayNightService.setDay(1);
//
//        // Flag to check if a new day has started
//        AtomicBoolean isNewDay = new AtomicBoolean(false);
//
//        // Add a listener for the "newday" event
//        enddayEventHandler.addListener("newday", () -> {
//            isNewDay.set(true);
//        });
//
//        // Using "animationDone" as per Team 6 request. Not originally intended.
//        // Trigger "decisionDone" and "animationDone" events
//        enddayEventHandler.trigger("decisionDone");
//        enddayEventHandler.trigger("animationDone");
//
//        // Assert that the "newday" event was triggered
//        Assertions.assertTrue(isNewDay.get(), "New day was not triggered");
//
//        // Also assert that the day counter has incremented correctly
//        Assertions.assertEquals(2, dayNightService.getDay(), "Day did not increment correctly after decisionDone and animationDone.");
//
//        //Assertions.assertTrue(isNewDay.get());
//    }

}