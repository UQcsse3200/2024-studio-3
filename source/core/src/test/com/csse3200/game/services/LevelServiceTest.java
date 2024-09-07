package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;
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
        assertEquals(levelServiceSpy.getEvents().getClass(), EventHandler.class);
    }

    @Test
    void shouldReturnDefaultLevelValueOfOneFromGetCurrLevel() {
        assertEquals(levelServiceSpy.getCurrLevel(),1);
    }

    @Test
    void shouldIncrementLevelByOne() {
        levelServiceSpy.incrementLevel();
        assertEquals(levelServiceSpy.getCurrLevel(), 2);
    }

    @Test
    void shouldManuallySetLevel() {
        levelServiceSpy.setCurrLevel(5);
        assertEquals(levelServiceSpy.getCurrLevel(),5);
    }

    @Test
    void shouldRespondToDifferentIntegersInLevelControl() {
        for (int i = 0; i < 11; i++) {
            levelServiceSpy.levelControl(i);
            verify(levelServiceSpy).levelControl(i);
        }
    }
}