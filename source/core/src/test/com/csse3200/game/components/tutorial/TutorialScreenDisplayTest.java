//package com.csse3200.game.components.tutorial;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.csse3200.game.GdxGame;
//import com.csse3200.game.components.player.PlayerActions;
//import com.csse3200.game.entities.Entity;
//import com.csse3200.game.extensions.GameExtension;
//import com.csse3200.game.rendering.RenderService;
//import com.csse3200.game.services.PlayerService;
//import com.csse3200.game.services.ServiceLocator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(GameExtension.class)
//public class TutorialScreenDisplayTest {
//
//    private TutorialScreenDisplay tutorialScreenDisplay;
//    private GdxGame mockGame;
//    private Stage mockStage;
//
//    @BeforeEach
//    public void setUp() {
//        mockGame = mock(GdxGame.class);
//        mockStage = mock(Stage.class);
//        tutorialScreenDisplay = new TutorialScreenDisplay(mockGame);
//        tutorialScreenDisplay.setStage(mockStage);
//
//        // Mock services and entities
//        ServiceLocator.registerRenderService(mock(RenderService.class));  // Ensure RenderService is registered
//        ServiceLocator.registerPlayerService(mock(PlayerService.class));
//        Gdx.input = mock(Input.class);
//    }
//
//    @Test
//    public void testAdvanceTutorialStep_ValidSteps() {
//        tutorialScreenDisplay.advanceTutorialStep();
//        assertEquals(1, tutorialScreenDisplay.getTutorialStep());
//
//        tutorialScreenDisplay.advanceTutorialStep();
//        assertEquals(2, tutorialScreenDisplay.getTutorialStep());
//
//        tutorialScreenDisplay.advanceTutorialStep();
//        assertEquals(3, tutorialScreenDisplay.getTutorialStep());
//
//        tutorialScreenDisplay.advanceTutorialStep();
//        assertEquals(4, tutorialScreenDisplay.getTutorialStep());
//    }
//
//    @Test
//    public void testAdvanceTutorialStep_InvalidStep() {
//        tutorialScreenDisplay.setTutorialStep(5);
//        tutorialScreenDisplay.advanceTutorialStep();
//        assertEquals(5, tutorialScreenDisplay.getTutorialStep());
//    }
//
//
//    @Test
//    public void testCompleteTutorial() {
//        tutorialScreenDisplay.setTutorialStep(4);
//        when(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)).thenReturn(true);
//        tutorialScreenDisplay.update();
//        verify(mockGame).setScreen(GdxGame.ScreenType.MAIN_GAME);
//    }
//
//}
//
//
//
////package com.csse3200.game.components.tutorial;
////
////import static org.mockito.Mockito.*;
////import static org.junit.jupiter.api.Assertions.*;
////
////import com.csse3200.game.components.tutorial.TutorialScreenDisplay;
////import com.csse3200.game.components.tutorial.TutorialTextDisplay;
////import com.csse3200.game.GdxGame;
////import com.csse3200.game.entities.Entity;
////import com.csse3200.game.entities.EntityService;
////import com.csse3200.game.extensions.GameExtension;
////import com.csse3200.game.services.ServiceLocator;
////import com.badlogic.gdx.utils.Array;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.mockito.junit.jupiter.MockitoExtension;
////
////import static org.mockito.Mockito.mockStatic;
////import static org.junit.jupiter.api.Assertions.*;
////import static org.mockito.Mockito.*;
////
////@ExtendWith(GameExtension.class)
////@ExtendWith(MockitoExtension.class)
////public class TutorialScreenDisplayTest {
////
////    private TutorialScreenDisplay tutorialScreenDisplay;
////    private TutorialTextDisplay spyTextDisplay;
////    private GdxGame mockGame;
////    private EntityService mockEntityService;
////
////    @BeforeEach
////    void setUp() {
////        // Mock the Game
////        mockGame = mock(GdxGame.class);
////
////        // Mock the TutorialTextDisplay using a spy to retain some functionality
////        spyTextDisplay = spy(new TutorialTextDisplay());
////
////        // Mock the EntityService
////        mockEntityService = mock(EntityService.class);
////
////        // Set up the EntityService to return a list of entities
////        Array<Entity> mockEntities = new Array<>();
////        mockEntities.add(new Entity()); // Add a mock entity or create one as needed
////        when(mockEntityService.getEntities()).thenReturn(mockEntities);
////
////        // Mock ServiceLocator to return the mocked EntityService
////        mockStatic(ServiceLocator.class);
////        when(ServiceLocator.getEntityService()).thenReturn(mockEntityService);
////
////        // Initialize TutorialScreenDisplay with the mocked dependencies
////        tutorialScreenDisplay = new TutorialScreenDisplay(mockGame);
////        tutorialScreenDisplay.textDisplay = spyTextDisplay; // Inject spyTextDisplay
////    }
////
////    @Test
////    void testAdvanceTutorialStep() {
////        // Verify initial tutorial step is 0
////        assertEquals(0, tutorialScreenDisplay.tutorialStep);
////
////        // Advance to the first step
////        tutorialScreenDisplay.advanceTutorialStep();
////        assertEquals(1, tutorialScreenDisplay.tutorialStep);
////        verify(spyTextDisplay).setVisible(true); // Ensure text display is shown for movement tutorial
////
////        // Advance to the second step
////        tutorialScreenDisplay.advanceTutorialStep();
////        assertEquals(2, tutorialScreenDisplay.tutorialStep);
////        verify(spyTextDisplay, times(2)).setVisible(true); // Still visible
////        verify(spyTextDisplay).setText("Press E to pick up items."); // Ensure this method is called
////
////        // Debugging step: Check if the right methods are called
////        System.out.println("Expected to call setText for item pickup tutorial.");
////
////        // Advance to the third step
////        tutorialScreenDisplay.advanceTutorialStep();
////        assertEquals(3, tutorialScreenDisplay.tutorialStep);
////        verify(spyTextDisplay).setText("Press 'Create Order' and then use [ and ] keys to switch dockets.");
////
////        // Advance to the fourth step
////        tutorialScreenDisplay.advanceTutorialStep();
////        assertEquals(4, tutorialScreenDisplay.tutorialStep);
////        verify(spyTextDisplay).setText("Tutorial Complete! Press ENTER to continue.");
////    }
////}