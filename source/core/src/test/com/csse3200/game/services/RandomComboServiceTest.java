package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.services.RandomComboService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RandomComboServiceTest {
    private RandomComboService randomComboService;
    private EventHandler mockEventHandler;

    @BeforeEach
    public void setUp() {
        ServiceLocator.clear();
        mockEventHandler = Mockito.mock(EventHandler.class);
        randomComboService = new RandomComboService(mockEventHandler);
    }

    @Test
    public void testDefaultConstructor() {
        RandomComboService defaultService = new RandomComboService();
        assertNotNull(defaultService.getEvents());
    }

    @Test
    public void testGetSelectedUpgrade() {
        String selectedUpgrade = randomComboService.getSelectedUpgrade();
        assertTrue(
                selectedUpgrade.equals("Extortion") ||
                        selectedUpgrade.equals("Loan") ||
                        selectedUpgrade.equals("Speed") ||
                        selectedUpgrade.equals("Dance party"));
    }

    @Test
    public void testActivateUpgrade() {
        randomComboService = new RandomComboService(mockEventHandler) {
            @Override
            public String getSelectedUpgrade() {
                return "Loan";
            }
        };

        randomComboService.activateUpgrade();
        verify(mockEventHandler, times(1)).trigger("Loan");
    }

    @Test
    public void testGetEvents() {
        assertEquals(mockEventHandler, randomComboService.getEvents());
    }

    @Test
    public void testActivateUpgradeForExtortion() {
        randomComboService = new RandomComboService(mockEventHandler) {
            @Override
            public String getSelectedUpgrade() {
                return "Extortion";
            }
        };
        randomComboService.activateUpgrade();
        verify(mockEventHandler, times(1)).trigger("Extortion");
    }

    @Test
    public void testActivateUpgradeForLoan() {
        randomComboService = new RandomComboService(mockEventHandler) {
            @Override
            public String getSelectedUpgrade() {
                return "Loan";
            }
        };
        randomComboService.activateUpgrade();
        verify(mockEventHandler, times(1)).trigger("Loan");
    }

    @Test
    public void testActivateUpgradeForSpeed() {
        randomComboService = new RandomComboService(mockEventHandler) {
            @Override
            public String getSelectedUpgrade() {
                return "Speed";
            }
        };
        randomComboService.activateUpgrade();
        verify(mockEventHandler, times(1)).trigger("Speed");
    }

    @Test
    public void testActivateUpgradeForDanceParty() {
        randomComboService = new RandomComboService(mockEventHandler) {
            @Override
            public String getSelectedUpgrade() {
                return "Dance party";
            }
        };
        randomComboService.activateUpgrade();
        verify(mockEventHandler, times(1)).trigger("Dance party");
    }

    @Test
    public void testInvalidRandomChoice() {
        randomComboService = new RandomComboService(mockEventHandler) {
            @Override
            public String getSelectedUpgrade() {
                randomChoice = 5;  // Invalid index (out of bounds)
                return super.getSelectedUpgrade();
            }
        };
        assertEquals("", randomComboService.getSelectedUpgrade());
    }
}



