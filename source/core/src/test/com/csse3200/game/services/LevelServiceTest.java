package com.csse3200.game.services;

import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class LevelServiceTest {
    @Spy
    LevelService levelServiceSpy;

    /*@BeforeAll
    void initSetUp() {
        ServiceLocator.registerLevelService(levelServiceSpy);
    }*/

    @Test
    void shouldInitialiseProperlyWhenCreated() {
        LevelService levelService = new LevelService();
        assertNotNull(levelService.getCurrLevel());
        assertNotNull(levelService.getEvents());
        EventListener0 mockListener = mock(EventListener0.class);
        /*levelServiceSpy.getEvents().trigger("startLevel", 1);
        levelServiceSpy.getEvents().trigger("createCustomer");
        verify(levelServiceSpy).getEvents().trigger("startLevel", 1);
        verify(levelServiceSpy).getEvents().trigger("createCustomer");*/
        levelServiceSpy.getEvents().addListener("mockEvent",mockListener);
        levelServiceSpy.getEvents().trigger("mockEvent");
        verify(mockListener).handle();
    }

    @Test
    void shouldReturnEventHandler() {
        levelServiceSpy.getEvents();
        verify(levelServiceSpy).getEvents();
    }

}