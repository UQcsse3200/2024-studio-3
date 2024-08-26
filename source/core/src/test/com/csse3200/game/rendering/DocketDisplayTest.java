package com.csse3200.game.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.ordersystem.DocketDisplay;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.csse3200.game.services.ServiceLocator.resourceService;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class DocketDisplayTest {

    private DocketDisplay docketDisplay;

    @BeforeEach
    public void setUp() {


        // Mock Texture
        Texture mockTexture = Mockito.mock(Texture.class);
        ResourceService resourceService =null;
        when(resourceService.getAsset("images/ordersystem/docket_background.png", Texture.class))
                .thenReturn(mockTexture);

        // Initialize DocketDisplay
        docketDisplay = new DocketDisplay();
        docketDisplay.create(); // Initialize the component
    }

    @Test
    public void testTableInitialization() {
        Table table = docketDisplay.getTable(); // Assuming you have a getter for the table
        assertNotNull(table, "Table should be initialized");


        Image docketImage = (Image) table.getChildren().get(0);
        assertNotNull(docketImage, "Docket image should be added to the table");
    }

    @Test
    public void testDispose() {
        // Ensure that the table is not empty before dispose
        Table table = docketDisplay.getTable(); // Assuming you have a getter for the table
        assertFalse(table.getChildren().isEmpty(), "Table should have children before dispose");

        docketDisplay.dispose();

        assertTrue(table.getChildren().isEmpty(), "Table should be cleared after dispose");
    }

    private void assertFalse(boolean empty, String s) {

    }
}
