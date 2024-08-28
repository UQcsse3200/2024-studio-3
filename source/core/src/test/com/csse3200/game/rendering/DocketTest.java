package com.csse3200.game.rendering;

import static org.junit.jupiter.api.Assertions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.csse3200.game.components.ordersystem.Docket;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
@ExtendWith(GameExtension.class)

class DocketTest {

    private Docket docket;
    private Docket docket2;

    @BeforeEach
    void setUp() {
        Skin mockSkin = new Skin();
        Image mockImage = new Image();
        docket = new Docket();
        docket2 = new Docket();
    }

    @Test
    void testDefaultStartTime() {
        long startTime = docket2.getStartTime();
        assertTrue(startTime > 0, "Start time should be initialized and greater than 0.");
    }

    @Test
    void testSetAndGetCellHash() {
        int hashValue = 12345;
        docket.setCellHash(hashValue);
        assertEquals(hashValue, docket.getCellHash(), "Cell hash should be set and returned correctly.");
    }

    @Test
    void testGetTextureNameArray() {
        String[] textureNameArray = docket.getTextureNameArray();
        assertNotNull(textureNameArray, "Texture name array should not be null.");
        assertEquals(4, textureNameArray.length, "Texture name array should have a length of 4.");
    }
}