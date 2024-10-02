package com.csse3200.game.services;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.files.GameState;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.entities.EntityService;
import org.junit.jupiter.api.BeforeEach;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.csse3200.game.events.EventHandler;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class SaveLoadServiceTest {

    private SaveLoadService saveLoadService;
    private Entity testPlayer;
    private CombatStatsComponent testCombatStats;
    private GameState testGameState;

    @BeforeEach
    public void setUp() {
        saveLoadService = new SaveLoadService();
    }

    @Test
    public void testSetSaveFile() {
        saveLoadService.setSaveFile("newSaveFile.json");
        assertEquals("newSaveFile.json", saveLoadService.getSaveFile());
    }

    @Test
    public void testEventHandlerExists() {
        EventHandler eventHandler = saveLoadService.getEvents();
        assertNotNull(eventHandler);
    }
}