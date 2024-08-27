package com.csse3200.game.rendering;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.csse3200.game.components.ordersystem.Docket;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DocketTest {

    private Docket docket;

    @Before
    public void setUp() {
        docket = new Docket();
    }

    @Test
    public void testUpdateDocketTexture() {

        assertEquals("fresh_docket", getTextureName(docket.getImage()));

        docket.updateDocketTexture(2.5);
        assertEquals("mild_docket", getTextureName(docket.getImage()));

        docket.updateDocketTexture(1.5);
        assertEquals("old_docket", getTextureName(docket.getImage()));

        docket.updateDocketTexture(0.5);
        assertEquals("expired_docket", getTextureName(docket.getImage()));
    }

    private String getTextureName(Image image) {
        if (image.getDrawable() == null) {
            return "none";
        }
        TextureRegion region = (TextureRegion) image.getDrawable();
        return region.toString();
    }
}

