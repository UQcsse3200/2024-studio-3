package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.csse3200.game.GdxGame;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.screens.TutorialScreen;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfirmationpopupTest {

    @Mock
    private GdxGame game;

    @Mock
    private Stage stage;

    @Mock
    private RenderService renderService;

    @Mock
    private Files files;

    @Mock
    private FileHandle mockFileHandle;

    @BeforeEach
    public void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Mock the RenderService and stage
        when(renderService.getStage()).thenReturn(stage);
        ServiceLocator.registerRenderService(renderService);

        // Mock Gdx.files to return a mock FileHandle for a nonexistent file
        Gdx.files = files;
        when(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json")).thenReturn(mockFileHandle);
        when(mockFileHandle.exists()).thenReturn(false);  // Simulate the file not existing
    }

    @Test
    public void testYesButtonStartsTutorialScreen() {
        // Instantiate the Skin using the mocked file handle
        Skin skin = mock(Skin.class);  // Mock the Skin as we're not testing its behavior

        // Create the Confirmationpopup object with a mocked skin
        new Confirmationpopup("Game Tuto Confirm", skin, stage, game).result(true);

        // Capture the argument passed to setScreen
        ArgumentCaptor<TutorialScreen> screenCaptor = ArgumentCaptor.forClass(TutorialScreen.class);

        // Verify that setScreen is called with a new instance of TutorialScreen
        verify(game).setScreen(screenCaptor.capture());

        // Ensure that the screen being set is an instance of TutorialScreen
        assertTrue(screenCaptor.getValue() instanceof TutorialScreen);
    }

    @Test
    public void testNoButtonStartsMainGameScreen() {
        // Instantiate the Skin using the mocked file handle
        Skin skin = mock(Skin.class);  // Mock the Skin

        // Create the Confirmationpopup object with a mocked skin
        new Confirmationpopup("Game Tuto Confirm", skin, stage, game).result(false);

        // Verify that setScreen is called with GdxGame.ScreenType.MAIN_GAME
        verify(game).setScreen(GdxGame.ScreenType.MAIN_GAME);
    }
}