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
        verify(mockDocketServiceEventHandler).trigger("Dispose");
        verify(mockEndDayEventHandler).trigger("endOfDay");
    }


    /**
     * *********This tests whether the day cycle loop continues for the new day, BUT:********
     *
     * This test is commented out since a new day would not get triggered as per
     * team 6 request. It was a working test prior to this design change. It would work when team 6
     * extends functionality for multiple days.
     */
/*    @Test
    public void testDecisionAndAnimationDone() {
        GameTime gameTime = mock(GameTime.class);
        ServiceLocator.registerTimeSource(gameTime);
        EventHandler enddayEventHandler = new EventHandler();
        EventHandler mockDocketServiceEventHandler = mock(EventHandler.class);
        DayNightService dayNightService = new DayNightService(enddayEventHandler, mockDocketServiceEventHandler);
        ServiceLocator.registerDayNightService(dayNightService);

        AtomicBoolean isNewDay = new AtomicBoolean(false);
        enddayEventHandler.addListener("newday", () -> {
            isNewDay.set(true);
        });

        // These don't get triggered as per Team 6 request. Not originally intended.
        enddayEventHandler.trigger("decisionDone");
        enddayEventHandler.trigger("animationDone");
        Assertions.assertTrue(isNewDay.get());
    }*/
}