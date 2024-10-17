package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.csse3200.game.components.maingame.TextDisplay;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CutsceneScreenDisplayTest {

    private CutsceneScreenDisplay cutsceneScreenDisplay;
    private Stage mockStage;
    private Entity mockEntity;
    private EventHandler mockEventHandler;
    private RenderService mockRenderService;

    @BeforeEach
    void setUp() {
        // Create mock objects
        mockStage = mock(Stage.class);
        mockEntity = mock(Entity.class);
        mockEventHandler = mock(EventHandler.class);
        mockRenderService = mock(RenderService.class);
        Skin mockSkin = mock(Skin.class);

        when(mockEntity.getEvents()).thenReturn(mockEventHandler);

        // Clear any previously registered services
        ServiceLocator.clear();
        ServiceLocator.registerRenderService(mockRenderService);


        // Create the CutsceneScreenDisplay and set the entity and stage
        cutsceneScreenDisplay = new CutsceneScreenDisplay(mockSkin) {
            @Override
            public void setupTextDisplay() {
                //Do nothing
            }
        };
        Table table = mock(Table.class);
        when(table.bottom()).thenReturn(table);
        when(table.right()).thenReturn(table);
        cutsceneScreenDisplay.setTable(table);
        cutsceneScreenDisplay.setEntity(mockEntity);
        cutsceneScreenDisplay.setStage(mockStage);
    }

    @Test
    void testCreateInitializesUIComponents() {
        // Verify that table is initialised
        assertNotNull(cutsceneScreenDisplay.getTable(), "Table should be initialized");
    }

    @Test
    void testSetStage() {
        // Act: Set a new stage
        Stage newMockStage = mock(Stage.class);
        cutsceneScreenDisplay.setStage(newMockStage);

        // Assert: The stage should be updated
        assertEquals(newMockStage, cutsceneScreenDisplay.getStage());
    }

    @Test
    void testSetStageErrorOnNull() {
        // Act: Set a null stage
        cutsceneScreenDisplay.setStage(null);

        // Verify that the logger catches the error (you can mock the logger or test with a real logger)
        assertNull(cutsceneScreenDisplay.getStage(), "Stage should not be set to null");
    }
}

