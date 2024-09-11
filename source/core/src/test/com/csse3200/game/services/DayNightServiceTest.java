package com.csse3200.game.services;

import com.csse3200.game.components.maingame.EndDayDisplay;
import com.csse3200.game.components.ordersystem.Docket;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.security.Provider;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class DayNightServiceTest {
    private GameTime mockTimesource;
    private EventHandler mockEventHandler;
    private DayNightService dayNightService;
    private DocketService mockDocketService;
    private EndDayDisplay endDayDisplay;
    private MainGameScreen mockGameScreen;

    @BeforeEach
    public void setUp() {
        mockTimesource = mock(GameTime.class);
        ServiceLocator.registerTimeSource(mockTimesource);

        dayNightService = new DayNightService();
        ServiceLocator.registerDayNightService(dayNightService);

        mockDocketService = new DocketService();
        ServiceLocator.registerDocketService(mockDocketService);

        mockEventHandler = mock(EventHandler.class);
//        endDayDisplay = new EndDayDisplay(mock(MainGameScreen.class));
//        endDayDisplay.create();

//        dayNightService.setEndDayDisplay(endDayDisplay);
//        dayNightService.setEndDayEventHandler(mockEventHandler);
    }



    @Test
    public void testFiveMinuteTransition() {
        when(mockTimesource.getTime()).thenReturn(5 * 60 * 1000L);
        dayNightService.update();
//        verify(mockEventHandler).trigger("endOfDay");
//        verify(endDayDisplay).toggleVisibility();
        Assertions.assertEquals(0f, mockTimesource.getTimeScale(), 0.0001);
        Assertions.assertTrue(dayNightService.getEndOfDayTriggered());
    }

    // Having issues with event Handler / Service Locator Design Pattern. Unable to test
    // toggling of display's
    /*
   @Test
    public void testEndOfDayVisibilityToggle() {
        // Simulate end of day event trigger
//        dayNightService.getEvents().trigger("endOfDay");
//        verify(endDayDisplay).toggleVisibility();

    }
/*
    @Test
    public void testMoralDisplayVisibilityToggle() {
        when(mockTimesource.getTime()).thenReturn(5 * 60 * 1000L);
        dayNightService.update();
        Assertions.assertTrue(dayNightService.getEndOfDayTriggered());

        dayNightService.getEvents().trigger("TOMORAL");
        verify(endDayDisplay).toggleVisibility();
    }*/
}

