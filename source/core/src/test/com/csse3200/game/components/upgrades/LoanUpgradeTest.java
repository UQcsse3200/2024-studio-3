package com.csse3200.game.components.upgrades;

import static org.mockito.Mockito.*;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.events.listeners.EventListener1;
import org.mockito.ArgumentCaptor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.lang.reflect.Field;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.services.RandomComboService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class LoanUpgradeTest {

    private LoanUpgrade loanUpgrade;
    private CombatStatsComponent combatStatsComponent;
    private EventHandler playerServiceEvents;
    private EventHandler randomComboServiceEvents;
    private MockedStatic<ServiceLocator> mockedServiceLocator;

    @BeforeEach
    public void setUp() {
        combatStatsComponent = mock(CombatStatsComponent.class);
        playerServiceEvents = mock(EventHandler.class);
        randomComboServiceEvents = mock(EventHandler.class);
        Entity player = mock(Entity.class);
        RandomComboService mockRandomComboService = mock(RandomComboService.class);

        mockedServiceLocator = mockStatic(ServiceLocator.class);
        PlayerService mockPlayerService = mock(PlayerService.class);
        mockedServiceLocator.when(ServiceLocator::getPlayerService).thenReturn(mockPlayerService);
        mockedServiceLocator.when(ServiceLocator::getRandomComboService).thenReturn(mockRandomComboService);

        when(mockPlayerService.getEvents()).thenReturn(playerServiceEvents);
        when(mockRandomComboService.getEvents()).thenReturn(randomComboServiceEvents);
        when(player.getComponent(CombatStatsComponent.class)).thenReturn(combatStatsComponent);
        ArgumentCaptor<EventListener1<Entity>> eventListenerCaptor = ArgumentCaptor.forClass(EventListener1.class);
        loanUpgrade = new LoanUpgrade();
        verify(playerServiceEvents).addListener(eq("playerCreated"), eventListenerCaptor.capture());
        EventListener1<Entity> capturedListener = eventListenerCaptor.getValue();
        capturedListener.handle(player);
    }


    @AfterEach
    public void tearDown() {
        mockedServiceLocator.close();
    }

    @Test
    public void activate_shouldAddGold_WhenGoldIsSufficient() {
        loanUpgrade.activate();
        verify(combatStatsComponent).addGold(100);
    }

}
