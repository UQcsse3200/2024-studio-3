package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.player.PlayerActions;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.LevelService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class TutorialScreenDisplayTest {

    private TutorialScreenDisplay tutorialScreenDisplay;
    private GdxGame mockGame;
    private Stage mockStage;
    private LevelService mockLevelService;

    @BeforeEach
    public void setUp() {
        mockGame = mock(GdxGame.class);
        mockStage = mock(Stage.class);
        tutorialScreenDisplay = new TutorialScreenDisplay(mockGame);
        tutorialScreenDisplay.setStage(mockStage);

        // Mock services and entities
        ServiceLocator.registerRenderService(mock(RenderService.class));  // Ensure RenderService is registered
        ServiceLocator.registerPlayerService(mock(PlayerService.class));

        mockLevelService = mock(LevelService.class);
        ServiceLocator.registerLevelService(mockLevelService);

        Gdx.input = mock(Input.class);
    }

    @Test
    public void testAdvanceTutorialStep_ValidSteps() {
        tutorialScreenDisplay.advanceTutorialStep();
        assertEquals(1, tutorialScreenDisplay.getTutorialStep());

        tutorialScreenDisplay.advanceTutorialStep();
        assertEquals(2, tutorialScreenDisplay.getTutorialStep());

        tutorialScreenDisplay.advanceTutorialStep();
        assertEquals(3, tutorialScreenDisplay.getTutorialStep());

        tutorialScreenDisplay.advanceTutorialStep();
        assertEquals(4, tutorialScreenDisplay.getTutorialStep());
    }

    @Test
    public void testAdvanceTutorialStep_InvalidStep() {
        tutorialScreenDisplay.setTutorialStep(5);
        tutorialScreenDisplay.advanceTutorialStep();
        assertEquals(5, tutorialScreenDisplay.getTutorialStep());
    }

//    @Test
//    public void testOnCreateOrderPressed() {
//        tutorialScreenDisplay.onCreateOrderPressed();
//        assertTrue(tutorialScreenDisplay.isCreateOrderPressed());
//    }
//

    @Test
    public void testCompleteTutorial() {
        tutorialScreenDisplay.setTutorialStep(4);
        when(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)).thenReturn(true);
        tutorialScreenDisplay.update();
        verify(mockGame).setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    @Test
    public void testDispose() {
        tutorialScreenDisplay.dispose();
        assertFalse(tutorialScreenDisplay.isCreateOrderPressed());
    }
}