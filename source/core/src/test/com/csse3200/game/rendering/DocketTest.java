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

        docket = new Docket(5000);
        docket2 = new Docket(5000);
    }

    @Test
    void testGetCurrentTextureName() {
        docket.getDocketImage().setDrawable(docket.getSkin().getDrawable(docket.getTextureNameArray()[0]));
        assertEquals(docket.getTextureNameArray()[0], docket.getCurrentTextureName());
        docket.getDocketImage().setDrawable(null);
        assertEquals("none", docket.getCurrentTextureName());
    }

    @Test
    void testUpdateDocketTextures() {
        docket.setPaused(true);
        updateAndAssertDocketTexture(0.5, 0);

        docket.setPaused(false);
        double remainingTimeSecs = 1.5;
        setRecipeTimeAndUpdate(remainingTimeSecs, 0.6);
        updateAndAssertDocketTexture(remainingTimeSecs, 0);

        setRecipeTimeAndUpdate(remainingTimeSecs, 0.3);
        updateAndAssertDocketTexture(remainingTimeSecs, 1);

        remainingTimeSecs = 1;
        docket.setTotalRecipeTime(10);
        updateAndAssertDocketTexture(remainingTimeSecs, 2);

        remainingTimeSecs = -1;
        updateAndAssertDocketTexture(remainingTimeSecs, 3);
    }

    private void setRecipeTimeAndUpdate(double remainingTimeSecs, double factor) {
        docket.setTotalRecipeTime((long) (remainingTimeSecs / factor));
        docket.updateDocketTexture(remainingTimeSecs);
    }

    private void updateAndAssertDocketTexture(double remainingTimeSecs, int textureIndex) {
        docket.updateDocketTexture(remainingTimeSecs);
        assertEquals(
                docket.getSkin().getDrawable(docket.getTextureNameArray()[textureIndex]),
                docket.getDocketImage().getDrawable()
        );
    }


    @Test
    void testRecipeTimeSetAndGet() {
        docket.setTotalRecipeTime((long) 1.5);
        assertEquals((long) 1.5, docket.getTotalRecipeTime());

        docket.setTotalRecipeTime((long) 3.8);
        assertEquals((long) 3.8, docket.getTotalRecipeTime());
    }

    @Test
    void testPauseSetAndGet() {
        docket.setPaused(true);
        assertTrue(docket.getPaused());
        docket.setPaused(false);
        assertFalse(docket.getPaused());
    }

    @Test
    void testSkinSetAndGet() {
        Skin mockSkin = new Skin();
        docket.setSkin(mockSkin);
        assertEquals(mockSkin, docket.getSkin());
    }

    @Test
    void testImageSetAndGet() {
        Image mockImage = new Image();
        docket.setDocketImage(mockImage);
        assertEquals(mockImage, docket.getDocketImage());
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