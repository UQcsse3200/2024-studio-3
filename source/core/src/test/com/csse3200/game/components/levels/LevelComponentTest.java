package com.csse3200.game.components.levels;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.LevelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

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
}