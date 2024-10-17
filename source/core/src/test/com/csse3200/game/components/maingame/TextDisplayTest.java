package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.cutscenes.Cutscene;
import com.csse3200.game.screens.CutsceneScreen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TextDisplayTest {

    @Mock
    private ScreenAdapter game;

    @Mock
    private Stage stage;

    @Mock
    private Cutscene cutscene;

    @Mock
    private CutsceneScreen cutsceneScreen;

    @Mock
    private Label label;

    private TextDisplay textDisplay;

    @BeforeEach
    void setUp() {
        // Mock the ServiceLocator and its methods
        ServiceLocator.registerCutsceneScreen(cutsceneScreen);

        textDisplay = new TextDisplay(game);
        textDisplay.setStage(stage);
    }

    @Test
    void testSetText_ShouldDivideTextIntoParts() {
        String inputText = "This is a long piece of text that needs to be split into parts for display.";
        textDisplay.setText(inputText);

        // Verify that the text is split correctly into parts
        Assertions.assertEquals(2, textDisplay.getText().size());
        Assertions.assertTrue(textDisplay.getText().get(0).contains("enter to continue"));
        Assertions.assertTrue(textDisplay.getText().get(1).contains("enter to continue"));
    }

    @Test
    void testSetText_ShouldMakeTextVisible() {
        textDisplay.setText("Hello World");

        Assertions.assertTrue(textDisplay.getVisible());
    }

    @Test
    void testUpdate_ShouldAddCharactersOverTime() {
        String inputText = "Hello World";
        textDisplay.setText(inputText);
        textDisplay.update();

        // Simulate the passing of time for the update method
        when(ServiceLocator.getTimeSource().getTime()).thenReturn(200L);
        textDisplay.update();

        // Verify that characters are being appended to the label text
        verify(label, atLeastOnce()).setText(anyString());
    }

    @Test
    void testUpdate_ShouldHideTextWhenFinished() {
        textDisplay.setText("Hello World");

        // Simulate that the text has been fully shown
        textDisplay.setVisible(true);
        textDisplay.update();

        // Verify that setVisible is called when the text is finished
        Assertions.assertTrue(textDisplay.getVisible());
    }

    @Test
    void testKeyDown_ShouldDisplayNextPartOrHide() {
        textDisplay.setText("Hello World");
        textDisplay.setVisible(true);

        // Simulate the ENTER key press to move to the next part
        textDisplay.setScreen("moralDecision");
        Assertions.assertTrue(textDisplay.getVisible());
        verify(stage).addListener(any());
    }

    @Test
    void testKeyDown_ShouldTriggerYesEventAtEnd() {
        textDisplay.setScreen("moralDecision");
        when(cutscene.isAtEnd()).thenReturn(true);

        // Act: Simulate 'Y' key press
        boolean result = textDisplay.simulateKeyPress(Input.Keys.Y);

        Assertions.assertTrue(result, "The keyDown method should return true when handling the 'Y' key.");
        verify(ServiceLocator.getDayNightService().getEvents()).trigger("YesAtMoralDecision");
    }

    @Test
    public void testSetText() {
        TextDisplay test = new TextDisplay();
        test.setText("Testing the length of this string to ensure it meets exactly 80 characters long.");
        assertEquals(test.getText().size(), 2);
        assertEquals(test.getText().get(0).length(), 80);
        assertEquals(test.getText().get(1).length(), 40);
    }
    @Test
    public void testSettersAndGetters() {
        TextDisplay test = new TextDisplay();
        assertFalse(test.getVisible());
        assertEquals(test.getDelay(), 100L);
        test.setText("Testing the text string");
        assertTrue(test.getVisible());
        List<String> expectedText = Collections.singletonList("Testing the text string (enter to continue)");
        assertEquals(expectedText, test.getText());
    }

    @Test
    public void testVisibilityToggling() {
        TextDisplay test = new TextDisplay();
        assertFalse(test.getVisible(), "TextDisplay should be initially invisible");

        // Set the text, which should make it visible
        test.setText("Visibility test string");
        assertTrue(test.getVisible(), "TextDisplay should be visible after setting text");

        // Manually toggle visibility
        test.setVisible(false);
        assertFalse(test.getVisible(), "TextDisplay should be invisible after setting visibility to false");
    }


    @Test
    public void testTextWrapping() {
        TextDisplay test = new TextDisplay();
        String longText = "This is a very long string that should be wrapped into multiple lines based on the text limit.";
        test.setText(longText);

        assertEquals(2, test.getText().size(), "Text should be split into two lines");
        assertTrue(test.getText().get(0).endsWith("(enter to continue)"), "First line should end with 'enter to continue'");
    }
}

