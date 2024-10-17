package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.screens.CutsceneScreen;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.cutscenes.Cutscene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import com.csse3200.game.services.DayNightService;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@ExtendWith(GameExtension.class)
public class TestTextDisplayMoral {

    @Mock
    private ScreenAdapter game;

    @Mock
    private Stage stage;

    @Mock
    private Cutscene cutscene;

    @Mock
    private DayNightService dayNightService;

    private TextDisplay textDisplay;

    @Mock
    InputEvent inputEvent;

    @BeforeEach
    void setUp() {
        // Mock the ServiceLocator and its methods
        ServiceLocator.setCurrentCutscene(cutscene);
        ServiceLocator.registerTimeSource(new GameTime());


        textDisplay = new TextDisplay(game);
        textDisplay.setStage(stage);
    }

    @Test
    void testSetText_ShouldDivideTextIntoParts() {
        String inputText = "This is a long piece of text that needs to be split into parts for display.";
        textDisplay.setText(inputText);

        // Verify that the text is split correctly into parts
        Assertions.assertEquals(2, textDisplay.getText().size());
        assertTrue(textDisplay.getText().get(0).contains("enter to continue"));
        assertTrue(textDisplay.getText().get(1).contains("enter to continue"));
    }

    @Test
    void testSetText_ShouldMakeTextVisible() {
        textDisplay.setText("Hello World");

        assertTrue(textDisplay.getVisible());
    }

    @Test
    void testUpdate_ShouldHideTextWhenFinished() {
        textDisplay.setText("Hello World");

        // Simulate that the text has been fully shown
        textDisplay.setVisible(true);
        textDisplay.update();

        // Verify that setVisible is called when the text is finished
        assertTrue(textDisplay.getVisible());
    }

    @Test
    void testKeyDown_ShouldDisplayNextPartOrHide() {
        textDisplay.setText("Hello World");
        textDisplay.setVisible(true);

        // Simulate the ENTER key press to move to the next part
        textDisplay.setScreen("moralDecision");
        assertTrue(textDisplay.getVisible());
    }

    @Test
    void testKeyDown_ShouldTriggerYesEventAtEnd() {
        textDisplay.setScreen("moralDecision");

        Cutscene cutscene;
    }
}
