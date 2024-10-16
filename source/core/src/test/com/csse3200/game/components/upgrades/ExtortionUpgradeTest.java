package com.csse3200.game.components.upgrades;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.player.KeyboardPlayerInputComponent;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicBoolean;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class ExtortionUpgradeTest {
    @Mock RenderService renderService;
    @Spy OrthographicCamera camera;
    @Mock Stage stage;
    @Mock Viewport viewport;
    @Mock ResourceService resourceService;
    @Mock GameTime gameTime;
    @Mock Texture textureMock;
    @Mock Table mockLayout;
    @Mock ProgressBar mockSpeedMeter;
    @Mock Label mockText;
    @Mock CombatStatsComponent combatStatsComponent;
    @Mock KeyboardPlayerInputComponent keyboardPlayerInputComponent;
    private EventHandler eventHandler;
    RandomComboService randomComboService;
    private ExtortionUpgrade extortionUpgrade;

    @BeforeEach
    void setUp() {
        ServiceLocator.clear();

        eventHandler = new EventHandler();
        randomComboService = new RandomComboService(eventHandler);

        ServiceLocator.registerRandomComboService(randomComboService);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerTimeSource(gameTime);

        lenient().when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(textureMock);
        lenient().when(renderService.getStage()).thenReturn(stage);
        lenient().when(renderService.getStage().getViewport()).thenReturn(viewport);
        lenient().when(renderService.getStage().getViewport().getCamera()).thenReturn(camera);

        extortionUpgrade = new ExtortionUpgrade(combatStatsComponent);
        extortionUpgrade.setStage(stage);
        extortionUpgrade.layout = mockLayout;
        extortionUpgrade.meter = mockSpeedMeter;
        extortionUpgrade.text = mockText;
        extortionUpgrade.create();
    }

    @Test
    void testNotNull() {
        assertNotNull(extortionUpgrade);
    }

    @Test
    void testDispose() {
        extortionUpgrade.dispose();
        verify(resourceService).unloadAssets(SpeedBootsUpgrade.whiteBgTexture);
        verify(resourceService).unloadAssets(SpeedBootsUpgrade.greenTexture);
    }

    @AfterEach
    void tearDown() {
        ServiceLocator.clear();
    }
}
