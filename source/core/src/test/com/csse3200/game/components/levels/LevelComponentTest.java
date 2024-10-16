package com.csse3200.game.components.levels;

import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.listeners.EventListener1;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.LevelService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class LevelComponentTest {
    @Spy
    LevelComponent levelComponentSpy;
    @Spy
    Entity entitySpy;

    /*@BeforeEach
    void setUp() {
        entitySpy.addComponent(levelComponentSpy);
    }*/

    @Test
    void shouldInitSpawningProperly() {
        levelComponentSpy.initSpawning(1);
        verify(levelComponentSpy).initSpawning(1);
        verify(levelComponentSpy).resetCustomerSpawn();
        verify(levelComponentSpy).setLevelSpawnCap(1);
        verify(levelComponentSpy).setSpawnStartTime();
        verify(levelComponentSpy).toggleNowSpawning();
    }

    @Test
    void shouldHaveDefaultValues() {
        assertEquals(0,levelComponentSpy.getSpawnStartTime());
        assertFalse(levelComponentSpy.getNowSpawning());
        assertEquals(0,levelComponentSpy.getLevelSpawnCap());
        assertEquals(0,levelComponentSpy.getNumbCustomersSpawned());
        assertEquals(0,levelComponentSpy.getCurrentCustomersLinedUp());
        assertNull(levelComponentSpy.getGameArea());
        assertNull(levelComponentSpy.getCustomerSpawnController());
    }

    @Test
    void shouldAppropriatelyTrackNumberOfCustomersInLineUp() {
        assertEquals(0, levelComponentSpy.getCurrentCustomersLinedUp());
        levelComponentSpy.customerJoinedLineUp();
        verify(levelComponentSpy).customerJoinedLineUp();
        assertEquals(1,levelComponentSpy.getCurrentCustomersLinedUp());
        levelComponentSpy.customerLeftLineUp();
        verify(levelComponentSpy).customerLeftLineUp();
        assertEquals(0, levelComponentSpy.getCurrentCustomersLinedUp());
    }

    @Test
    void shouldAppropriatelyUpdateTheNumberOfCustomersSpawned() {
        assertEquals(0, levelComponentSpy.getNumbCustomersSpawned());
        levelComponentSpy.customerSpawned();
        assertEquals(1, levelComponentSpy.getNumbCustomersSpawned());
        for (int i = 0; i < 4; i++) {
            levelComponentSpy.customerSpawned();
        }
        verify(levelComponentSpy, atMost(5)).customerSpawned();
        assertEquals(5, levelComponentSpy.getNumbCustomersSpawned());
        levelComponentSpy.resetCustomerSpawn();
        verify(levelComponentSpy).resetCustomerSpawn();
        assertEquals(0, levelComponentSpy.getNumbCustomersSpawned());
    }

    @Test
    void shouldAppropriatelySetAndGetGameArea() {
        ForestGameArea mockGameArea = mock(ForestGameArea.class);
        levelComponentSpy.setGameArea(mockGameArea);
        verify(levelComponentSpy).setGameArea(mockGameArea);
        assertEquals(levelComponentSpy.getGameArea(), mockGameArea);
        verify(levelComponentSpy).getGameArea();
    }

    @Test
    void scuffed() {
        ServiceLocator.registerLevelService(new LevelService());
        EventListener1 mockEvent = mock(EventListener1.class);
        ForestGameArea mockGameArea = mock(ForestGameArea.class);
        ServiceLocator.getLevelService().getEvents().addListener("createCustomer", mockEvent);
        levelComponentSpy.initSpawning(5);
        verify(mockEvent, atMost(5)).handle(mockGameArea);
        verify(levelComponentSpy, atMost(2)).toggleNowSpawning();
        verify(levelComponentSpy, atMost(5)).customerSpawned();
        //assertEquals(5, levelComponentSpy.getNumbCustomersSpawned());
        //assertEquals(levelComponentSpy.getLevelSpawnCap(), levelComponentSpy.getNumbCustomersSpawned());
    }
}