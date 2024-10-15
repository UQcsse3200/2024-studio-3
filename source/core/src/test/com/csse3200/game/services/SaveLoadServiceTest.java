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
import com.csse3200.game.components.moral.MoralDecision;
import com.csse3200.game.components.moral.Decision;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(GameExtension.class)
public class SaveLoadServiceTest {

    private SaveLoadService saveLoadService;

    @Mock
    private PlayerService playerService;

    @Mock
    private DayNightService dayNightService;

    @Mock
    private EntityService entityService;

    @Mock
    private Entity player;

    @Mock
    private CombatStatsComponent combatStats;

    @Mock
    private MoralDecision moralDecision;

    private GameState loadGame;

    @BeforeEach
    public void setUp() {
        // This line initializes the mocks
        MockitoAnnotations.openMocks(this);

        ServiceLocator.registerPlayerService(playerService);
        ServiceLocator.registerDayNightService(dayNightService);
        ServiceLocator.registerEntityService(entityService);

        saveLoadService = new SaveLoadService();

        when(playerService.getPlayer()).thenReturn(player);
        when(player.getComponent(CombatStatsComponent.class)).thenReturn(combatStats);
        when(entityService.getMoralSystem()).thenReturn(player);
        when(player.getComponent(MoralDecision.class)).thenReturn(moralDecision);

        loadGame = FileLoader.readClass(GameState.class, "saves/testLoad.json");
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
        // Mocking values for saving
        when(combatStats.getGold()).thenReturn(100);
        when(dayNightService.getDay()).thenReturn(5);

        // Create mock Decision objects
        List<Decision> decisions = new ArrayList<>();
        Decision mockDecision = mock(Decision.class);
        decisions.add(mockDecision);

        when(moralDecision.getListOfDecisions()).thenReturn(decisions);

        saveLoadService.setSaveFile("testSave.json");
        saveLoadService.save();

        // Verify that the file was saved using the FileLoader
        verify(combatStats, times(1)).getGold();
        verify(dayNightService, times(1)).getDay();
        verify(moralDecision, times(1)).getListOfDecisions();
    }

    @Test
    public void testLoad() {
        saveLoadService.setSaveFile("testLoad.json");
        saveLoadService.load();

        verify(combatStats, times(1)).setGold(loadGame.getMoney());
        verify(dayNightService, times(1)).setDay(loadGame.getDay());
        //verify(moralDecision, times(1)).getListOfDecisions();
    }
}