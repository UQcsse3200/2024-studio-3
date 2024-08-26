package com.csse3200.game.rendering;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.csse3200.game.components.ordersystem.Docket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocketTest {

    private Docket docket;

    @BeforeEach
    void setUp() {
        docket = new Docket();
    }

    @Test
    void testDocketInitialization() {

        Image docketImage = docket.getImage();
        Skin docketSkin = docket.getDocketSkin();
        String[] textureNameArray = docket.getTextureNameArray();

        assertNotNull(docketImage, "Docket image should not be null.");
        assertNotNull(docketSkin, "Docket skin should not be null.");
        assertArrayEquals(new String[]{"fresh_docket", "mild_docket", "old_docket", "expired_docket"}, textureNameArray, "Texture names should match the expected array.");
    }

    @Test
    void testUpdateDocketTexture() {
        docket.updateDocketTexture(2.5);
        assertEquals(docket.getImage().getDrawable(), docket.getDocketSkin().getDrawable("mild_docket"), "Docket should update to 'mild_docket' texture.");

        docket.updateDocketTexture(1.5);
        assertEquals(docket.getImage().getDrawable(), docket.getDocketSkin().getDrawable("old_docket"), "Docket should update to 'old_docket' texture.");

        docket.updateDocketTexture(0.5);
        assertEquals(docket.getImage().getDrawable(), docket.getDocketSkin().getDrawable("expired_docket"), "Docket should update to 'expired_docket' texture.");
    }
}
