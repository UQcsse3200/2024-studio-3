package com.csse3200.game.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.ordersystem.Docket;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
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
    private Skin mockSkin;

    @Mock
    private Docket mockDocket;

    @InjectMocks
    private MainGameOrderTicketDisplay display;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(ServiceLocator.getRenderService().getStage()).thenReturn(mockStage);
        display.create();
    }

    @Test
    void testAddActors() {
        display.addActors();

        // Verify that the table was added to the stage
        verify(mockStage).addActor(any(Table.class));

        // Check that there is one table added
        assertEquals(1, display.tableArrayList.size());

        Table table = display.tableArrayList.get(0);
        assertNotNull(table);

        // Check the label text
        Label countdownLabel = display.countdownLabelArrayList.get(0);
        assertNotNull(countdownLabel);
        assertEquals("Timer: 5000", countdownLabel.getText().toString());
    }

    @Test
    void testUpdate() {
        display.addActors();

        // Fast forward the time
        long futureTime = TimeUtils.millis() + 6000; // 6 seconds ahead
        display.startTimeArrayList.set(0, futureTime);

        display.update();

        // Ensure the label text is updated to show that time has elapsed
        Label countdownLabel = display.countdownLabelArrayList.get(0);
        assertEquals("Timer: 0", countdownLabel.getText().toString());

        // Verify the lists are emptied
        assertTrue(display.tableArrayList.isEmpty());
        assertTrue(display.backgroundArrayList.isEmpty());
        assertTrue(display.startTimeArrayList.isEmpty());
        assertTrue(display.countdownLabelArrayList.isEmpty());
    }

    @Test
    void testStageDispose() {
        display.addActors();
        Table table = display.tableArrayList.get(0);

        // Replace the actual Docket with a mock
        display.backgroundArrayList.set(0, mockDocket);

        Docket background = display.backgroundArrayList.get(0);

        // Capture the state before disposal
        boolean hasChildrenBeforeDispose = !table.getChildren().isEmpty();

        // Perform disposal
        display.stageDispose(background, table, 0);

        // Verify that the table was cleared and removed from the stage
        assertTrue(table.getChildren().isEmpty());
        assertFalse(hasChildrenBeforeDispose, "Table should be cleared of children.");

        // Verify that dispose was called on the mock Docket
        verify(mockDocket).dispose();

        // Verify event triggering if applicable
        // Uncomment and modify if you have an event service mock
        // verify(mockEventService).trigger("removeOrder", 0);
    }

    @Test
    void testGetZIndex() {
        assertEquals(2f, display.getZIndex());
    }

    @Test
    void testSetStage() {
        display.setStage(mockStage);
        // No assertion needed if the method doesn't modify the state
    }

    @Test
    void testDispose() {
        // Call dispose method
        display.dispose();

        // Verify any specific clean-up operations if necessary
        // e.g., verify if resources are properly cleaned up
        // assertTrue(display.isDisposed()); // Example if such a method exists
    }
}
