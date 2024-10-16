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
import static org.mockito.Mockito.*;

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
        assertEquals(EventHandler.class, levelServiceSpy.getEvents().getClass());
    }

    @Test
    void shouldReturnDefaultLevelValueOfOneFromGetCurrLevel() {
        assertEquals(GdxGame.LevelType.LEVEL_1, levelServiceSpy.getCurrLevel());
    }

    @Test
    void shouldIncrementLevelByOneIfTriggerIsTrue() {
        levelServiceSpy.togglePlayerFinishedLevel();
        assertEquals(GdxGame.LevelType.LEVEL_2, levelServiceSpy.getCurrLevel());
    }

    @Test
    void shouldIncrementLevelByOne() {
        levelServiceSpy.incrementLevel();
        assertEquals(GdxGame.LevelType.LEVEL_2, levelServiceSpy.getCurrLevel());
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
        assertEquals(GdxGame.LevelType.LEVEL_5, levelServiceSpy.getCurrLevel());
    }

    @Test
    void shouldRespondToDifferentIntegersInLevelControl() {
        EventListener1 mockEventListener = mock(EventListener1.class);
        EventHandler eventHandler = new EventHandler();
        doReturn(eventHandler).when(levelServiceSpy).getEvents();
        levelServiceSpy.getEvents().addListener("startSpawning", mockEventListener);

        levelServiceSpy.levelControl(GdxGame.LevelType.LEVEL_1);
        verify(levelServiceSpy).levelControl(GdxGame.LevelType.LEVEL_1);

        levelServiceSpy.levelControl(GdxGame.LevelType.LEVEL_2);
        verify(levelServiceSpy).levelControl(GdxGame.LevelType.LEVEL_2);

        levelServiceSpy.levelControl(GdxGame.LevelType.LEVEL_3);
        verify(levelServiceSpy).levelControl(GdxGame.LevelType.LEVEL_3);

        levelServiceSpy.levelControl(GdxGame.LevelType.LEVEL_4);
        verify(levelServiceSpy).levelControl(GdxGame.LevelType.LEVEL_4);

        levelServiceSpy.levelControl(GdxGame.LevelType.LEVEL_5);
        verify(levelServiceSpy).levelControl(GdxGame.LevelType.LEVEL_5);

        levelServiceSpy.levelControl(GdxGame.LevelType.DONE);
        verify(levelServiceSpy).levelControl(GdxGame.LevelType.DONE);
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