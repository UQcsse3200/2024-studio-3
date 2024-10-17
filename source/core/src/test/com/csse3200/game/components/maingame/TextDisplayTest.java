package com.csse3200.game.components.maingame;


import com.csse3200.game.extensions.GameExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Collections;

@ExtendWith(GameExtension.class)
public class TextDisplayTest {
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
        assertEquals(test.getVisible(), false);
        assertEquals(test.getDelay(), 100L);
        test.setText("Testing the text string");
        assertEquals(test.getVisible(), true);
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