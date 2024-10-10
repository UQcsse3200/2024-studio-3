//package com.csse3200.game.components.cutscenes;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
//import com.csse3200.game.entities.Entity;
//import com.csse3200.game.events.EventHandler;
//import com.csse3200.game.rendering.RenderService;
//import com.csse3200.game.services.ServiceLocator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class BackstoryCutsceneDisplayTest {
//
//    private BackstoryCutsceneDisplay backstoryCutsceneDisplay;
//    private CutsceneTextDisplay mockTextDisplay;
//    private Stage mockStage;
//    private Entity mockEntity;
//    private EventHandler mockEventHandler;
//    private Skin mockSkin;
//    private RenderService mockRenderService;
//    @BeforeEach
//    void setUp() {
//        // Create mock objects
//        mockStage = mock(Stage.class);
//        mockEntity = mock(Entity.class);
//        mockEventHandler = mock(EventHandler.class);
//        mockRenderService = mock(RenderService.class);
//        mockSkin = mock(Skin.class);
//        mockTextDisplay = mock(CutsceneTextDisplay.class);
//
//        when(mockEntity.getEvents()).thenReturn(mockEventHandler);
//
//        // Clear any previously registered services
//        ServiceLocator.clear();
//        ServiceLocator.registerRenderService(mockRenderService);
//
//        // Create the BackstoryCutsceneDisplay and set the entity and stage
//        backstoryCutsceneDisplay = new BackstoryCutsceneDisplay(mockSkin);
//        backstoryCutsceneDisplay.setEntity(mockEntity);
//        backstoryCutsceneDisplay.setStage(mockStage);
//    }
//
//    @Test
//    void testCreateInitializesUIComponents() {
//
//        // Verify that table is initialized
//        assertNotNull(backstoryCutsceneDisplay.getTable(), "Table should be initialised");
//    }
//
//    @Test
//    void testDisposeClearsUIComponents() {
//        // Arrange: Set up the table and text display with some UI elements
//        Table mockTable = mock(Table.class);
//        backstoryCutsceneDisplay.setTable(mockTable);
//        CutsceneTextDisplay mockTextDisplay = mock(CutsceneTextDisplay.class);
//        backstoryCutsceneDisplay.setTextDisplay(mockTextDisplay);
//
//        assertNotNull(backstoryCutsceneDisplay.getTextDisplay());
//
//        // Act: Call dispose to clear the UI
//        backstoryCutsceneDisplay.dispose();
//
//        // Verify that the table and text display are cleared
//        verify(mockTable, times(1)).clear();
//        verify(mockTextDisplay.getTable(), times(1)).clear();
//    }
//
//    @Test
//    void testSetStage() {
//        // Act: Set a new stage
//        Stage newMockStage = mock(Stage.class);
//        backstoryCutsceneDisplay.setStage(newMockStage);
//
//        // Assert: The stage should be updated
//        assertEquals(newMockStage, backstoryCutsceneDisplay.getStage());
//    }
//
//    @Test
//    void testSetStageErrorOnNull() {
//        // Act: Set a null stage
//        backstoryCutsceneDisplay.setStage(null);
//
//        // Assert: Ensure that the stage is null
//        assertNull(backstoryCutsceneDisplay.getStage(), "Stage should be null after setting to null");
//    }
//
////    @Test
////    void testSetupUIAddsComponentsToStage() {
////        // Act: Call create, which internally calls setupUI
////        backstoryCutsceneDisplay.create();
////
////        // Verify that a new button and text display are added to the stage
////        verify(mockStage, times(1)).addActor(any(TextButton.class));
////        verify(mockStage, times(1)).addActor(any(Table.class)); // For text display
////    }
////
////    @Test
////    void testNextSceneButtonClickTriggersEvent() {
////        // Arrange: Call create to set up the UI
////        backstoryCutsceneDisplay.create();
////
////        // Retrieve the TextButton (this requires some mock setup to simulate a button click)
////        Table table = backstoryCutsceneDisplay.getTable();
////        TextButton nextSceneBtn = (TextButton) table.getChildren().get(0);  // Assuming it's the first actor
////
////        // Act: Simulate a button click
////        nextSceneBtn.fire(new ChangeListener.ChangeEvent());
////
////        // Verify that the event "nextCutscene" was triggered
////        verify(mockEventHandler, times(1)).trigger("nextCutscene");
////    }
//}
//
