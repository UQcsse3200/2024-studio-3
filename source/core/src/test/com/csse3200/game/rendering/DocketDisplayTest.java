package com.csse3200.game.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.ordersystem.DocketDisplay;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class DocketDisplayTest {

    @Mock
    private ResourceService resourceService;

    private DocketDisplay docketDisplay;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);



        // Initialize the DocketDisplay instance
        docketDisplay = new DocketDisplay();
        docketDisplay.create(); // Call create to initialize
    }

    @Test
    public void testCreate() {
        // Verify if table is initialized
        Table table = docketDisplay.getTable(); // You need to provide a getter for the table in DocketDisplay
        assertNotNull(table, "Table should be initialized");

        // Verify if Image is added to the table
        Image docketImage = (Image) table.getChildren().get(0);
        assertNotNull(docketImage, "Docket image should be added to the table");
        assertNotNull(docketImage.getDrawable(), "Docket image should have a drawable");

        // Optionally verify the resource loading
        when(resourceService.getAsset("images/ordersystem/docket_background.png", Texture.class))
                .thenReturn(new Texture("path/to/your/dummy/image.png"));
    }

    @Test
    public void testDispose() {
        // Call dispose method
        docketDisplay.dispose();

        // Verify if the table is cleared
        Table table = docketDisplay.getTable(); // You need to provide a getter for the table in DocketDisplay
        assertTrue(table.getChildren().isEmpty(), "Table should be cleared");
    }
}
