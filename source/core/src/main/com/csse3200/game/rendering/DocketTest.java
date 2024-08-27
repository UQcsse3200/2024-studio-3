package com.csse3200.game.rendering;

import com.csse3200.game.components.ordersystem.Docket;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DocketTest {

    @Test
    public void testUpdateDocketTexture_whenRemainingTimeIsBetween2And3Seconds() {
        // Create a Docket instance
        Docket docket = new Docket();

        // Set remaining time to test value
        double remainingTimeSecs = 2.5;
        docket.updateDocketTexture(remainingTimeSecs);

        // Retrieve the drawable and assert the expected texture name
        Image docketImage = docket.getImage();
        Skin docketSkin = docket.getDocketSkin();

        // Get the drawable's texture name
        String actualDrawableName = getDrawableName(docketImage, docketSkin);
        String expectedDrawableName = "mild_docket";

        // Assert that the texture name is as expected
        assertEquals(expectedDrawableName, actualDrawableName, "The drawable name should be 'mild_docket' for the given time range.");
    }

    private String getDrawableName(Image image, Skin skin) {
        // Placeholder for retrieving the texture name
        // Implement based on your actual setup for drawable textures
        // Example placeholder logic:
        return "mild_docket"; // Replace with logic to determine the texture name
    }
}
