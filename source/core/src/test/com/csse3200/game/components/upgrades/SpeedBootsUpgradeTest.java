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
import org.mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicBoolean;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class SpeedBootsUpgradeTest {
    @Mock RenderService renderService;
    @Spy OrthographicCamera camera;
    @Mock Stage stage;
    @Mock Viewport viewport;
    @Mock ResourceService resourceService;
    @Mock GameTime gameTime;
    @Mock Texture textureMock;
    @Mock Table mockLayout;
    @Mock ProgressBar mockSpeedMeter;
    @Mock CombatStatsComponent combatStatsComponent;
    @Mock KeyboardPlayerInputComponent keyboardPlayerInputComponent;
    private EventHandler eventHandler;
    RandomComboService randomComboService;
    private SpeedBootsUpgrade speedBootsUpgrade;

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


        speedBootsUpgrade = new SpeedBootsUpgrade(combatStatsComponent, keyboardPlayerInputComponent);
        speedBootsUpgrade.setStage(stage);
        speedBootsUpgrade.layout = mockLayout;
        speedBootsUpgrade.speedMeter = mockSpeedMeter;
        speedBootsUpgrade.create();
    }

    @Test
    void testSpeedBootsActivates() {
        assertNotNull(speedBootsUpgrade);
        when(combatStatsComponent.getGold()).thenReturn(100);
        speedBootsUpgrade.activate();

        AtomicBoolean isActive = new AtomicBoolean(false);
        eventHandler.addListener("Speed", () -> {
            isActive.set(true);
        });
        eventHandler.trigger("Speed");

        assertTrue(isActive.get());
        assertTrue(speedBootsUpgrade.isActivate());
        assertTrue(speedBootsUpgrade.isVisible());
        assertTrue(speedBootsUpgrade.layout.isVisible());
        verify(keyboardPlayerInputComponent).setWalkSpeed(speedBootsUpgrade.getBoostedSpeed());
        assertEquals(speedBootsUpgrade.getBoostDuration(), speedBootsUpgrade.getActiveTimeRemaining());
    }

    @Test
    void testLoseGoldOnPurchase() {
        when(combatStatsComponent.getGold()).thenReturn(100);
        speedBootsUpgrade.activate();
        verify(combatStatsComponent).addGold(-20);
    }

    @Test
    void testInsufficientGold() {
        when(combatStatsComponent.getGold()).thenReturn(10);
        speedBootsUpgrade.activate();

        assertFalse(speedBootsUpgrade.isActivate());
        assertFalse(speedBootsUpgrade.isVisible());
        assertFalse(speedBootsUpgrade.layout.isVisible());
    }

    @AfterEach
    void tearDown() {
        ServiceLocator.clear();
    }
}
