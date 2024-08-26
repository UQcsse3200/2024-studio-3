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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DocketDisplayTest {

    private DocketDisplay docketDisplay;
    private ResourceService mockResourceService;

    @BeforeEach
    public void setUp() {
        // Create a mock ResourceService
        mockResourceService = Mockito.mock(ResourceService.class);

        // Configure ServiceLocator to return the mock ResourceService
        Mockito.mockStatic(ServiceLocator.class);
        when(ServiceLocator.getResourceService()).thenReturn(mockResourceService);

        // Set up the mock for the asset retrieval
        when(mockResourceService.getAsset("images/ordersystem/docket_background.png", Texture.class))
                .thenReturn(new Texture("path/to/your/dummy/image.png"));

        // Initialize DocketDisplay
        docketDisplay = new DocketDisplay();
        docketDisplay.create(); // Initialize the component
    }

    @Test
    public void testTableInitialization() {
        Table table = docketDisplay.getTable(); // Assuming you have a getter for the table
        assertNotNull(table, "Table should be initialized");

        // Check if an Image is added to the table
        Image docketImage = (Image) table.getChildren().get(0);
        assertNotNull(docketImage, "Docket image should be added to the table");
        assertNotNull(docketImage.getDrawable(), "Docket image should have a drawable");
    }

    @Test
    public void testDispose() {
        // Ensure that the table is not empty before dispose
        Table table = docketDisplay.getTable(); // Assuming you have a getter for the table
        assertFalse(table.getChildren().isEmpty(), "Table should have children before dispose");

        // Call dispose
        docketDisplay.dispose();

        // Check if the table is cleared
        assertTrue(table.getChildren().isEmpty(), "Table should be cleared after dispose");
    }
}
