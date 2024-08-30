package com.csse3200.game.rendering;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.ordersystem.Docket;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainGameOrderTicketDisplayTest {

    @Mock
    private Stage mockStage;

    @Mock
    private Docket mockDocket;

    @InjectMocks
    private MainGameOrderTicketDisplay display;

    MainGameOrderTicketDisplayTest() {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(ServiceLocator.getRenderService().getStage()).thenReturn(mockStage);
        display.create();
    }

    @Test
    void testAddActors() {
        display.addActors();

        verify(mockStage).addActor(any(Table.class));

        assertEquals(1, MainGameOrderTicketDisplay.tableArrayList.size());

        Table table = MainGameOrderTicketDisplay.tableArrayList.getFirst();
        assertNotNull(table);

        Label countdownLabel = MainGameOrderTicketDisplay.countdownLabelArrayList.getFirst();
        assertNotNull(countdownLabel);
        assertEquals("Timer: 5000", countdownLabel.getText().toString());
    }

    @Test
    void testUpdate() {
        display.addActors();

        // Fast forward the time
        long futureTime = TimeUtils.millis() + 6000; // 6 seconds ahead
        MainGameOrderTicketDisplay.startTimeArrayList.set(0, futureTime);

        display.update();

        Label countdownLabel = MainGameOrderTicketDisplay.countdownLabelArrayList.getFirst();
        assertEquals("Timer: 0", countdownLabel.getText().toString());

        assertTrue(MainGameOrderTicketDisplay.tableArrayList.isEmpty());
        assertTrue(MainGameOrderTicketDisplay.backgroundArrayList.isEmpty());
        assertTrue(MainGameOrderTicketDisplay.startTimeArrayList.isEmpty());
        assertTrue(MainGameOrderTicketDisplay.countdownLabelArrayList.isEmpty());
    }

    @Test
    void testStageDispose() {
        display.addActors();
        Table table = MainGameOrderTicketDisplay.tableArrayList.getFirst();

        MainGameOrderTicketDisplay.backgroundArrayList.set(0, mockDocket);

        Docket background = MainGameOrderTicketDisplay.backgroundArrayList.getFirst();

        boolean hasChildrenBeforeDispose = !table.getChildren().isEmpty();

        display.stageDispose(background, table, 0);

        assertTrue(table.getChildren().isEmpty());
        assertFalse(hasChildrenBeforeDispose, "Table should be cleared of children.");

        verify(mockDocket).dispose();

    }

    @Test
    void testGetZIndex() {
        assertEquals(2f, display.getZIndex());
    }

    @Test
    void testSetStage() {
        display.setStage(mockStage);

    }

    @Test
    void testDispose() {

        display.dispose();

    }
}
