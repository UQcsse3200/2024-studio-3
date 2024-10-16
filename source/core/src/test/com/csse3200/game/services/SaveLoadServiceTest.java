package com.csse3200.game.services;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.moral.Decision;
import com.csse3200.game.components.moral.MoralDecision;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.files.GameState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SaveLoadServiceTest {

    private SaveLoadService saveLoadService;

    @BeforeEach
    public void setUp() {
        saveLoadService = new SaveLoadService();

        // Mocking the ServiceLocator services
        PlayerService mockPlayerService = mock(PlayerService.class);
        Entity mockPlayer = mock(Entity.class);
        CombatStatsComponent mockCombatStats = mock(CombatStatsComponent.class);
        when(mockCombatStats.getGold()).thenReturn(100);
        when(mockPlayer.getComponent(CombatStatsComponent.class)).thenReturn(mockCombatStats);
        when(mockPlayerService.getPlayer()).thenReturn(mockPlayer);

        DayNightService mockDayNightService = mock(DayNightService.class);
        when(mockDayNightService.getDay()).thenReturn(5);

        EntityService mockEntityService = mock(EntityService.class);
        Entity mockMoralSystem = mock(Entity.class);
        MoralDecision mockMoralDecision = mock(MoralDecision.class);
        List<Decision> mockDecisions = new ArrayList<>();
        when(mockMoralDecision.getListOfDecisions()).thenReturn(mockDecisions);
        when(mockMoralSystem.getComponent(MoralDecision.class)).thenReturn(mockMoralDecision);
        when(mockEntityService.getMoralSystem()).thenReturn(mockMoralSystem);
        EventHandler mockEventHandler = mock(EventHandler.class);
        when(mockEntityService.getEvents()).thenReturn(mockEventHandler);

        // Setting up the ServiceLocator with the mocks
        ServiceLocator.registerPlayerService(mockPlayerService);
        ServiceLocator.registerDayNightService(mockDayNightService);
        ServiceLocator.registerEntityService(mockEntityService);

        // Mocking FileLoader static methods
        // Requires Mockito-inline dependency for mocking static methods
    }

    @AfterEach
    void tearDown() {
        ServiceLocator.clear();
    }

    @Test
    void testConstructor() {
        assertNotNull(saveLoadService.getEvents(), "EventHandler should be initialized");
    }

    @Test
    void testSave() {
        // Mocking static methods of FileLoader
        try (MockedStatic<FileLoader> mockedFileLoader = Mockito.mockStatic(FileLoader.class)) {

            // Ensure saveFile is empty to test conditional branch
            saveLoadService.setSaveFile("");

            saveLoadService.save();

            // Verify that getPlayer was called
            verify(ServiceLocator.getPlayerService(), times(1)).getPlayer();

            // Verify that gold was retrieved
            verify(ServiceLocator.getPlayerService().getPlayer().getComponent(CombatStatsComponent.class), times(1)).getGold();

            // Verify that day was retrieved
            verify(ServiceLocator.getDayNightService(), times(1)).getDay();

            // Verify that decisions were retrieved
            verify(ServiceLocator.getEntityService().getMoralSystem().getComponent(MoralDecision.class), times(1)).getListOfDecisions();

            // Verify that writeClass was called
            mockedFileLoader.verify(() -> FileLoader.writeClass(any(GameState.class), anyString(), eq(FileLoader.Location.LOCAL)), times(1));
        }
    }

    @Test
    void testSaveWithExistingSaveFile() {
        try (MockedStatic<FileLoader> mockedFileLoader = Mockito.mockStatic(FileLoader.class)) {

            // Set saveFile to an existing filename
            String existingFilename = "existing_save.json";
            saveLoadService.setSaveFile(existingFilename);

            saveLoadService.save();

            // Verify that getPlayer was called
            verify(ServiceLocator.getPlayerService(), times(1)).getPlayer();

            // Verify that saveFile was not changed
            assertEquals(existingFilename, saveLoadService.getSaveFile());

            // Construct the expected file path using File.separator
            String expectedFilePath = "saves" + File.separator + existingFilename;

            // Verify that writeClass was called with the correct filename
            mockedFileLoader.verify(() -> FileLoader.writeClass(any(GameState.class), eq(expectedFilePath), eq(FileLoader.Location.LOCAL)), times(1));
        }
    }


    @Test
    void testLoadWithNullGameState() {
        // Mocking static methods of FileLoader
        try (MockedStatic<FileLoader> mockedFileLoader = Mockito.mockStatic(FileLoader.class)) {

            // Mock FileLoader.readClass() to return null
            mockedFileLoader.when(() -> FileLoader.readClass(eq(GameState.class), anyString(), eq(FileLoader.Location.LOCAL))).thenReturn(null);

            // Call the load method
            saveLoadService.load();

            // Verify that readClass was called
            mockedFileLoader.verify(() -> FileLoader.readClass(eq(GameState.class), anyString(), eq(FileLoader.Location.LOCAL)), times(1));

            // Verify that no interactions occur with player or services
            verifyNoMoreInteractions(ServiceLocator.getPlayerService().getPlayer().getComponent(CombatStatsComponent.class));
            verifyNoMoreInteractions(ServiceLocator.getDayNightService());
            verifyNoMoreInteractions(ServiceLocator.getEntityService().getMoralSystem().getComponent(MoralDecision.class));
        }
    }

    @Test
    void testGetAndSetSaveFile() {
        String filename = "test_save.json";
        saveLoadService.setSaveFile(filename);
        assertEquals(filename, saveLoadService.getSaveFile(), "Save file name should be set correctly");
    }

    @Test
    void testGetEvents() {
        assertNotNull(saveLoadService.getEvents(), "EventHandler should not be null");
    }
}