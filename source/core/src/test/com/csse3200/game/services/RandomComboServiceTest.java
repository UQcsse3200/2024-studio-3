package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RandomComboServiceTest {
    private RandomComboService randomComboService;
    private EventHandler mockEventHandler;

    @BeforeEach
    public void setUp() {
        ServiceLocator.clear();
        mockEventHandler = Mockito.mock(EventHandler.class);
        randomComboService = new RandomComboService(mockEventHandler);
    }

    @Test
    void testDefaultConstructor() {
        RandomComboService defaultService = new RandomComboService();
        assertNotNull(defaultService.getEvents());
    }

    @Test
    void testGetSelectedUpgrade() {
        String selectedUpgrade = randomComboService.getSelectedUpgrade();
        assertTrue(
                selectedUpgrade.equals("Extortion") ||
                        selectedUpgrade.equals("Loan") ||
                        selectedUpgrade.equals("Speed") ||
                        selectedUpgrade.equals("Dance party"));
    }

    @Test
    void testActivateUpgrade() {
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
    void testGetEvents() {
        assertEquals(mockEventHandler, randomComboService.getEvents());
    }

    @Test
    void testActivateUpgradeForExtortion() {
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
    void testActivateUpgradeForLoan() {
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
    void testActivateUpgradeForSpeed() {
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
    void testActivateUpgradeForDanceParty() {
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
    void testInvalidRandomChoice() {
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



