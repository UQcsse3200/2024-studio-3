package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.Texture;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(GameExtension.class)
public class TextDisplayTest {
    @Test
    public void testSetText() {
        TextDisplay test = new TextDisplay();
        test.setText("Testing the length of this string to ensure it meets exactly 80 characters long.");
        assertEquals(test.getText().size(), 2);
        assertEquals(test.getText().get(0).length(), 60);
        assertEquals(test.getText().get(1).length(), 20);
    }

}