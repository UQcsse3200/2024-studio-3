//package com.csse3200.game.components.cutscenes;
//
//import com.badlogic.gdx.Gdx;
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
//    private RenderService mockRenderService;
//
//    @BeforeEach
//    void setUp() {
//        // Create mock objects
//        mockStage = mock(Stage.class);
//        mockEntity = mock(Entity.class);
//        mockEventHandler = mock(EventHandler.class);
//        mockRenderService = mock(RenderService.class);
//        mockTextDisplay = mock(CutsceneTextDisplay.class);
//        Skin mockSkin = mock(Skin.class);
//
//        when(mockEntity.getEvents()).thenReturn(mockEventHandler);
//
//        // Clear any previously registered services
//        ServiceLocator.clear();
//        ServiceLocator.registerRenderService(mockRenderService);
//
//        // Create the BackstoryCutsceneDisplay and set the entity and stage
//        backstoryCutsceneDisplay = new BackstoryCutsceneDisplay(mockSkin) {
//            @Override
//            public void setupTextDisplay() {
//                //Do nothing
//            }
//        };
//        Table table = mock(Table.class);
//        when(table.top()).thenReturn(table);  // Mocking position
//        backstoryCutsceneDisplay.setTable(table);
//        backstoryCutsceneDisplay.setEntity(mockEntity);
//        backstoryCutsceneDisplay.setStage(mockStage);
//    }
//
//    @Test
//    void testCreateInitializesUIComponents() {
//        // Verify that table is initialized
//        assertNotNull(backstoryCutsceneDisplay.getTable(), "Table should be initialized");
//    }
//
//    @Test
//    void testDisposeClearsUIComponents() {
//        // Arrange: Set up the table with some UI elements
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
//        // Verify that the stage should not be set to null
//        assertNull(backstoryCutsceneDisplay.getStage(), "Stage should not be set to null");
//    }
//
//    @Test
//    void testSetupUI() {
//        // Call setupUI() to initialize components
//        backstoryCutsceneDisplay.create();
//
//        // Verify that table is set up correctly
//        assertNotNull(backstoryCutsceneDisplay.getTable(), "Table should be initialized");
//        verify(mockStage, times(1)).addActor(any(Table.class));
//    }
//
//    @Test
//    void testTextDisplaySetup() {
//        // Act: Set up the text display
//        backstoryCutsceneDisplay.setupTextDisplay();
//
//        // Assert: Text display should be added to the stage
//        assertNotNull(backstoryCutsceneDisplay.getTextDisplay(), "Text display should be initialized");
//        verify(mockStage, times(1)).addActor(backstoryCutsceneDisplay.getTextDisplay().getTable());
//    }
//
//    @Test
//    void testNextSceneButtonTriggersNextCutsceneEvent() {
//        // Mock the button and simulate click event
//        TextButton nextSceneButton = mock(TextButton.class);
//        Table table = backstoryCutsceneDisplay.getTable();
//        table.add(nextSceneButton);
//
//        // Simulate the Next Scene button click
//        backstoryCutsceneDisplay.create();
//        nextSceneButton.fire(new ChangeListener.ChangeEvent());
//
//        // Verify that the "nextCutscene" event was triggered
//        verify(mockEventHandler, times(1)).trigger("nextCutscene");
//    }
//
//    @Test
//    void testDisposeRemovesTextDisplay() {
//        // Mock a CutsceneTextDisplay object
//        CutsceneTextDisplay mockTextDisplay = mock(CutsceneTextDisplay.class);
//        backstoryCutsceneDisplay.setTextDisplay(mockTextDisplay);
//
//        // Dispose the display
//        backstoryCutsceneDisplay.dispose();
//
//        // Verify that the text display table is cleared
//        verify(mockTextDisplay.getTable(), times(1)).clear();
//    }
//}
//
