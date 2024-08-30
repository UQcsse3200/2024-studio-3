package com.csse3200.game.rendering;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.extensions.GameExtension;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class MainGameOrderDisplayTest {

    private MainGameOrderTicketDisplay display;
    private Stage mockStage;
    private Skin mockSkin;
    private Table mockTable;

    @Before
    public void setUp() {
        // Mock dependencies
        mockStage = Mockito.mock(Stage.class);
        mockSkin = Mockito.mock(Skin.class);
        mockTable = Mockito.mock(Table.class);

        display = new MainGameOrderTicketDisplay("idk lol");
        //display.setStage(mockStage);
        //display.setSkin(mockSkin);
    }

    @Test
    public void testCreateInitializesComponents() {
        display.create();

        assertNotNull("Table should be initialized", display.getTableArrayList());
        assertNotNull("Countdown label should be initialized", display.getCountdownLabelArrayList());
        assertEquals("Start time should be set", TimeUtils.millis(), display.getStartTimeArrayList().get(0), 100);
    }

    @Test
    public void testAddActorsAddsUIComponents() {
        display.create(); // This will call addActors()

        verify(mockStage).addActor(any(Table.class));
    }

    @Test
    public void testUpdateCountdownDecreasesCorrectly() {
        display.create();
        display.getStartTimeArrayList().set(0, TimeUtils.millis() - 5000); // Simulate 5 seconds have passed

        display.update();

        assertEquals(
                "Timer should display correct countdown", "Timer: " +
                        (10000 / 1000 - 5), display.getCountdownLabelArrayList().get(0).getText().toString());
    }

    @Test
    public void testDisposeClearsComponents() {
        display.create();
        display.dispose();

        verify(mockTable).clear();

        verify(mockStage).dispose();
    }
}
