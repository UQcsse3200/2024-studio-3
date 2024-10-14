package com.csse3200.game.services;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.files.GameState;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.events.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.csse3200.game.files.FileLoader.Location;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class SaveLoadServiceTest {

    private SaveLoadService saveLoadService;
    private Entity mockPlayer;
    private CombatStatsComponent mockCombatStats;

    @BeforeEach
    public void setUp() {
        // Initialize SaveLoadService
        saveLoadService = new SaveLoadService();

        // Mock player, combat stats, and moral decision
        mockPlayer = mock(Entity.class);
        mockCombatStats = mock(CombatStatsComponent.class);
        when(mockPlayer.getComponent(CombatStatsComponent.class)).thenReturn(mockCombatStats);

        // Mock and register services with ServiceLocator
        PlayerService mockPlayerService = mock(PlayerService.class);
        when(mockPlayerService.getPlayer()).thenReturn(mockPlayer);
        ServiceLocator.registerPlayerService(mockPlayerService);

        DayNightService mockDayNightService = mock(DayNightService.class);
        when(mockDayNightService.getDay()).thenReturn(5); // Mock day
        ServiceLocator.registerDayNightService(mockDayNightService);
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

    @Test
    public void testSave() {
        // Mock the gold retrieval from the CombatStatsComponent
        when(mockCombatStats.getGold()).thenReturn(100);

        // Set a save file to avoid the conditional branch
        saveLoadService.setSaveFile("testSave.json");

        // Call save() and verify that the correct interactions happen
        saveLoadService.save();

        verify(mockCombatStats, times(1)).getGold();
        verify(ServiceLocator.getPlayerService(), times(1)).getPlayer();
        verify(ServiceLocator.getDayNightService(), times(1)).getDay();
    }

    @Test
    public void testSaveWithoutSaveFile() {
        // Mock the gold retrieval from the CombatStatsComponent
        when(mockCombatStats.getGold()).thenReturn(100);

        // Call save() without explicitly setting the save file, it should auto-generate one
        saveLoadService.save();

        // Verify that the saveFile is auto-generated based on the date and time
        String expectedPattern = "\\d{2}-\\d{2}-\\d{4}_\\d{2}-\\d{2}-\\d{2}\\.json";
        assertTrue(saveLoadService.getSaveFile().matches(expectedPattern), "The save file name should match the date-time pattern");

        verify(mockCombatStats, times(1)).getGold();
    }

    @Test
    public void testLoad() {
        // Mock GameState
        GameState mockState = mock(GameState.class);
        when(mockState.getMoney()).thenReturn(200);
        when(mockState.getDay()).thenReturn(3);

        // Mock FileLoader readClass
        when(FileLoader.readClass(GameState.class, "saves" + File.separator + "testLoad.json", Location.LOCAL))
            .thenReturn(mockState);

        saveLoadService.setSaveFile("testLoad.json");
        saveLoadService.load();

        // Verify that load interacts correctly with GameState
        verify(mockCombatStats, times(1)).setGold(200);
        verify(ServiceLocator.getDayNightService(), times(1)).setDay(3);
    }

    @Test
    public void testLoadNonExistentFile() {
        // Mock FileLoader readClass to return null when the file doesn't exist
        when(FileLoader.readClass(GameState.class, "saves" + File.separator + "nonExistent.json", Location.LOCAL))
            .thenReturn(null);

        saveLoadService.setSaveFile("nonExistent.json");
        saveLoadService.load();

        // Nothing should be updated since the file doesn't exist
        verify(mockCombatStats, never()).setGold(anyInt());
        verify(ServiceLocator.getDayNightService(), never()).setDay(anyInt());
    }
}
