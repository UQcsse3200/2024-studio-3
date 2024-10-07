package com.csse3200.game.services;

import com.csse3200.game.GdxGame;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.events.listeners.EventListener1;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.atMost;

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
        assertEquals(levelServiceSpy.getCurrLevel(), GdxGame.LevelType.LEVEL_1);
    }

    @Test
    void shouldIncrementLevelByOneIfTriggerIsTrue() {
        levelServiceSpy.togglePlayerFinishedLevel();
        assertEquals(levelServiceSpy.getCurrLevel(), GdxGame.LevelType.LEVEL_2);
    }

    @Test
    void shouldIncrementLevelByOne() {
        levelServiceSpy.incrementLevel();
        assertEquals(levelServiceSpy.getCurrLevel(), GdxGame.LevelType.LEVEL_2);
    }

    @Test
    void shouldIncrementLevelMultipleTimesByTrigger() {
        GdxGame.LevelType level = GdxGame.LevelType.DONE;
        for (int i = 0; i < 4; i++) {
            levelServiceSpy.togglePlayerFinishedLevel();
            level = levelServiceSpy.getCurrLevel();
        }
        assertEquals(GdxGame.LevelType.LEVEL_5, level);
    }

    @Test
    void shouldManuallySetLevel() {
        levelServiceSpy.setCurrLevel(GdxGame.LevelType.LEVEL_5);
        assertEquals(levelServiceSpy.getCurrLevel(), GdxGame.LevelType.LEVEL_5);
    }

    @Test
    void shouldRespondToDifferentIntegersInLevelControl() {
        EventListener1 mockEventListener = mock(EventListener1.class);
        EventHandler eventHandler = new EventHandler();
        doReturn(eventHandler).when(levelServiceSpy).getEvents();
        levelServiceSpy.getEvents().addListener("startSpawning", mockEventListener);
        for (int i = 0; i < 11; i++) {
            levelServiceSpy.levelControl(i);
            verify(levelServiceSpy).levelControl(i);
            //when(levelServiceSpy.getEvents()).thenReturn(eventHandler);
            //doReturn(eventHandler).when(levelServiceSpy).getEvents();
            verify(mockEventListener, atMost(11)).handle(anyInt());
        }
    }

    @Test
    void shouldCorrectlyReturnAndSetGold() {
        int gold = levelServiceSpy.getCurrGold();
        //Test default value
        assertEquals(50, gold);
        //Check that it updates and retrieves correctly
        levelServiceSpy.setCurrGold(100);
        verify(levelServiceSpy).setCurrGold(100);
        gold = levelServiceSpy.getCurrGold();
        assertEquals(100, gold);
    }
}