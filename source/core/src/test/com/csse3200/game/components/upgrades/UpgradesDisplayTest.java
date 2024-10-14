package com.csse3200.game.components.upgrades;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class UpgradesDisplayTest {
    @Mock RenderService renderService;
    @Spy OrthographicCamera camera;
    @Mock Stage stage;
    @Mock Viewport viewport;
    @Mock ResourceService resourceService;
    @Mock Texture textureMock;
    @Mock Table upgradesTable;
    @Mock ProgressBar mockRageMeter;
    @Mock EventHandler eventHandler;
    @Mock MainGameScreen mainGameScreen;
    RandomComboService randomComboService;
    private UpgradesDisplay upgradesDisplay;

    @BeforeEach
    void setUp() {
        ServiceLocator.clear();

        randomComboService = new RandomComboService();

        ServiceLocator.registerRandomComboService(randomComboService);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerGameScreen(mainGameScreen);

        lenient().when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(textureMock);
        lenient().when(renderService.getStage()).thenReturn(stage);
        lenient().when(renderService.getStage().getViewport()).thenReturn(viewport);
        lenient().when(renderService.getStage().getViewport().getCamera()).thenReturn(camera);

        upgradesDisplay = new UpgradesDisplay(mainGameScreen);
        upgradesDisplay.setUpgradesTable(upgradesTable);
        upgradesDisplay.setStage(stage);
        upgradesDisplay.create();
    }

    @Test
    void testNotNull() {
        assertNotNull(upgradesDisplay);
    }

    @Test
    void testToggleVisibilityOn() {
        upgradesDisplay.toggleVisibility();
        assertTrue(upgradesDisplay.isVisible());
        assertTrue(upgradesDisplay.getUpgradesMenuImage().isVisible());
        assertTrue(upgradesDisplay.getUpgradesTable().isVisible());
        verify(mainGameScreen).pause();
    }

    @Test
    void testToggleVisibilityOff() {
        upgradesDisplay.toggleVisibility();
        upgradesDisplay.toggleVisibility();
        assertFalse(upgradesDisplay.isVisible());
        assertFalse(upgradesDisplay.getUpgradesMenuImage().isVisible());
        assertFalse(upgradesDisplay.getUpgradesTable().isVisible());
        verify(mainGameScreen).resume();
    }

    @Test
    void testDispose() {

    }

    @AfterEach
    void tearDown() {
        ServiceLocator.clear();
    }
}
