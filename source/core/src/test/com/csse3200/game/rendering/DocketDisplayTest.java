package com.csse3200.game.rendering;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.ordersystem.DocketDisplay;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DocketDisplayTest {

    private DocketDisplay docketDisplay;

    @BeforeEach
    void setUp() {

        ServiceLocator.clear();
        ServiceLocator.registerResourceService(Mockito.mock(ResourceService.class));
        ServiceLocator.registerRenderService(Mockito.mock(RenderService.class));
        ServiceLocator.getRenderService().getStage(Mockito.mock(Stage.class));

        docketDisplay = new DocketDisplay();
        docketDisplay.create();
    }

    @Test
    void testAddActorsInitializesTableCorrectly() {

        Table table = docketDisplay.getTable();


        assertNotNull(table, "Table should be initialized.");
        assertEquals(1, table.getChildren().size, "Table should contain one child actor.");
        assertTrue(table.getChildren().first() instanceof Image, "The first child of the table should be an Image.");
    }

    @Test
    void testDisposeClearsTable() {

        docketDisplay.dispose();
        Table table = docketDisplay.getTable();
        assertEquals(0, table.getChildren().size, "Table should be cleared after dispose.");
    }
}
