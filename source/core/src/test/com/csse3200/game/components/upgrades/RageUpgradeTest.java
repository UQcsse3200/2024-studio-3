package com.csse3200.game.components.upgrades;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.csse3200.game.entities.EntityService;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class RageUpgradeTest {
    @Mock RenderService renderService;
    @Spy OrthographicCamera camera;
    @Mock Stage stage;
    @Mock Viewport viewport;
    @Mock ResourceService resourceService;
    @Mock GameTime gameTime;
    @Mock Texture textureMock;
    @Mock Table mockLayout;
    @Mock ProgressBar mockRageMeter;
    RandomComboService randomComboService;
    EntityService entityService;
    private RageUpgrade rageUpgrade;

    @BeforeEach
    void setUp() {
        randomComboService = new RandomComboService();
        entityService = new EntityService();

        ServiceLocator.registerRandomComboService(randomComboService);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerTimeSource(gameTime);

        lenient().when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(textureMock);
        lenient().when(renderService.getStage()).thenReturn(stage);
        lenient().when(renderService.getStage().getViewport()).thenReturn(viewport);
        lenient().when(renderService.getStage().getViewport().getCamera()).thenReturn(camera);

        rageUpgrade = new RageUpgrade();
        rageUpgrade.setStage(stage);
        rageUpgrade.layout = mockLayout;
        rageUpgrade.rageMeter = mockRageMeter;
        rageUpgrade.create();
    }

    @Test
    void testRageOverlayPopsUP() {
        rageUpgrade.activateRageMode();
        assertTrue(rageUpgrade.isOverlayVisible(), "The red overlay should be visible when rage mode is active.");
        assertTrue(rageUpgrade.layout.isVisible(), "The layout should be visible when rage mode is active.");
    }

    @AfterEach
    void tearDown() {
        ServiceLocator.clear();
    }
}

