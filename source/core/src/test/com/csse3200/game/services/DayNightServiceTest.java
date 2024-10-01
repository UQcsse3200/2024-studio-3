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

    @Test
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

        // Using "animationDone" as per Team 6 request. Not originally intended.
        enddayEventHandler.trigger("decisionDone");
        enddayEventHandler.trigger("animationDone");
        Assertions.assertTrue(isNewDay.get());
    }

}