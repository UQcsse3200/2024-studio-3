//package com.csse3200.game.components.tutorial;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//import com.badlogic.gdx.utils.viewport.Viewport;
//import com.csse3200.game.GdxGame;
//import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
//import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
//import com.csse3200.game.input.InputService;
//import com.csse3200.game.services.ServiceLocator;
//import com.csse3200.game.ui.UIComponent;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TutorialScreenDisplayTest {
//
//    @Mock
//    private GdxGame game;
//    @Mock
//    private Stage stage;
//    @Mock
//    private Skin skin;
//    @Mock
//    private Label tutorialLabel;
//    @Mock
//    private MainGameOrderTicketDisplay orderTicketDisplay;
//    @Mock
//    private MainGameOrderBtnDisplay orderBtnDisplay;
//    @Mock
//    private Image tutorialBox;
//    @Mock
//    private TutorialScreenDisplay tutorialScreenDisplay;
//
//
//    @BeforeEach
//    void setUp() {
//
//        ServiceLocator.registerInputService(mock(InputService.class));
//
//
//        tutorialScreenDisplay = new TutorialScreenDisplay(game);
//
//
//        Viewport mockViewport = mock(Viewport.class);
//        when(stage.getViewport()).thenReturn(mockViewport);
//        when(mockViewport.getWorldWidth()).thenReturn(800f);  // assume world width is 800
//        when(mockViewport.getWorldHeight()).thenReturn(600f); // assume world height is 600
//
//
//        when(skin.getDrawable(anyString())).thenReturn(mock(TextureRegionDrawable.class));
//
//
//        tutorialBox = mock(Image.class);
//        doNothing().when(tutorialBox).setSize(anyFloat(), anyFloat());
//        doNothing().when(tutorialBox).setPosition(anyFloat(), anyFloat());
//
//
//        doNothing().when(tutorialLabel).setText(anyString());
//        doNothing().when(tutorialLabel).setPosition(anyFloat(), anyFloat());
//
//
//        doNothing().when(stage).addActor(any());
//
//        tutorialScreenDisplay.setStage(stage);
//
//
//        tutorialScreenDisplay.create();
//    }
//
//    @Test
//    void testCreateOrderPressed() {
//        // Test the case when createOrderPressed event is triggered
//        tutorialScreenDisplay.onCreateOrderPressed();
//
//        verify(tutorialLabel).setText("Now use [ and ] keys to switch dockets.");
//    }
//
//    @Test
//    void testAdvanceTutorialStep_MovementTutorial() {
//        // Simulate moving to movement tutorial (step 1)
//        tutorialScreenDisplay.advanceTutorialStep();
//
//        // Verify that the movement image is added to the stage
//        verify(stage, atLeastOnce()).addActor(any(Image.class));
//    }
//
//    @Test
//    void testAdvanceTutorialStep_ItemPickupTutorial() {
//        // Simulate moving to item pickup tutorial (step 2)
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 1
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 2
//
//        // Verify that the pickup image is added to the stage
//        verify(stage, atLeastOnce()).addActor(any(Image.class));
//    }
//
//    @Test
//    void testUpdate_MovementTutorial() {
//        // Simulate W key press during movement tutorial (step 1)
//        tutorialScreenDisplay.advanceTutorialStep(); // Start step 1
//
//        when(Gdx.input.isKeyJustPressed(Input.Keys.W)).thenReturn(true);
//
//        tutorialScreenDisplay.update();
//
//        // Verify that the tutorial step advanced
//        verify(stage, atLeastOnce()).addActor(any(Image.class));
//    }
//
//    @Test
//    void testUpdate_ItemPickupTutorial() {
//        // Simulate E key press during item pickup tutorial (step 2)
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 1
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 2
//
//        when(Gdx.input.isKeyJustPressed(Input.Keys.E)).thenReturn(true);
//
//        tutorialScreenDisplay.update();
//
//        // Verify that the tutorial step advanced to 3
//        verify(stage, atLeastOnce()).addActor(any(Image.class));
//    }
//
//    @Test
//    void testShowOrderingTutorial() {
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 1
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 2
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 3
//
//        when(Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET)).thenReturn(true);
//        tutorialScreenDisplay.update();
//
//        // Verify that the tutorial label text is updated
//        verify(tutorialLabel).setText("Now use [ and ] keys to switch dockets.");
//    }
//
//    @Test
//    void testCompleteTutorial() {
//        // Simulate completing the tutorial (step 4)
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 1
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 2
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 3
//        tutorialScreenDisplay.advanceTutorialStep(); // Step 4
//
//        // Simulate pressing ENTER to start the game
//        when(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)).thenReturn(true);
//        tutorialScreenDisplay.update();
//
//        // Verify that the game transition occurred
//        verify(game).setScreen(GdxGame.ScreenType.MAIN_GAME);
//    }
//
//    @Test
//    void testDispose() {
//        tutorialScreenDisplay.dispose();
//
//        // Verify that the label and images are removed from the stage
//        verify(tutorialLabel).remove();
//        verify(orderBtnDisplay).dispose();
//    }
//}
