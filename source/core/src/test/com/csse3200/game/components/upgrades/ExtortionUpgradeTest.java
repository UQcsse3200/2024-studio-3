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
    void testExtortionUpgradeActivates() {
        assertNotNull(extortionUpgrade);
        when(combatStatsComponent.getGold()).thenReturn(100);
        extortionUpgrade.activate();

        AtomicBoolean isActive = new AtomicBoolean(false);
        eventHandler.addListener("extortion active", () -> {
            isActive.set(true);
        });
        eventHandler.trigger("extortion active");

        assertTrue(isActive.get());
        assertTrue(extortionUpgrade.isActive());
        assertTrue(extortionUpgrade.isVisible());
        assertTrue(extortionUpgrade.layout.isVisible());
        assertEquals(extortionUpgrade.getUpgradeDuration(), extortionUpgrade.getActivateTimeRemaining());
        assertEquals(1f, extortionUpgrade.meter.getValue());
    }

    @Test
    void testExtortionUpgradeDeactivates() {
        extortionUpgrade.activate();
        lenient().when(mockSpeedMeter.hasParent()).thenReturn(true);
        extortionUpgrade.deactivate();

        AtomicBoolean isActive = new AtomicBoolean(false);
        eventHandler.addListener("UnDancing", () -> {
            isActive.set(true);
        });
        eventHandler.trigger("UnDancing");

        assertTrue(isActive.get());
        verify(extortionUpgrade.meter).remove();
        verify(extortionUpgrade.text).remove();

        assertFalse(extortionUpgrade.isActive());
        assertFalse(extortionUpgrade.isVisible());
        assertFalse(extortionUpgrade.layout.isVisible());
        assertEquals(0f, extortionUpgrade.meter.getValue());
    }

    @Test
    void testLoseGoldOnPurchase() {
        when(combatStatsComponent.getGold()).thenReturn(100);
        extortionUpgrade.activate();
        verify(combatStatsComponent).addGold(-40);
    }

    @Test
    void testInsufficientGold() {
        when(combatStatsComponent.getGold()).thenReturn(10);
        AtomicBoolean notEnoughMoney = new AtomicBoolean(false);
        eventHandler.addListener("notenoughmoney", () -> {
            notEnoughMoney.set(true);
        });

        extortionUpgrade.activate();

        assertTrue(notEnoughMoney.get());
        assertFalse(extortionUpgrade.isActive());
        assertFalse(extortionUpgrade.isVisible());
        assertFalse(extortionUpgrade.layout.isVisible());
    }

    @Test
    void testExtortionUpgradeFor30Seconds() {
        ExtortionUpgrade spyExtortionUpgrade = spy(extortionUpgrade);
        when(combatStatsComponent.getGold()).thenReturn(100);
        spyExtortionUpgrade.activate();

        when(gameTime.getDeltaTime()).thenReturn(1f);
        for (int i = 0; i < 15; i++) {
            spyExtortionUpgrade.update();
        }

        assertEquals(spyExtortionUpgrade.getActivateTimeRemaining() /
                (float) spyExtortionUpgrade.getUpgradeDuration(), spyExtortionUpgrade.meter.getValue());

        for (int i = 0; i < 15; i++) {
            spyExtortionUpgrade.update();
        }

        verify(spyExtortionUpgrade).deactivate();
        assertFalse(spyExtortionUpgrade.getPlaySound());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 20, 25, 30})
    void testMeterValueAtDifferentLevelsOfDepletion(int totalDepletedTime) {
        ExtortionUpgrade spyExtortionUpgrade = spy(extortionUpgrade);
        when(combatStatsComponent.getGold()).thenReturn(100);
        spyExtortionUpgrade.activate();

        when(gameTime.getDeltaTime()).thenReturn(1f);
        for (int i = 0; i < totalDepletedTime; i++) {
            spyExtortionUpgrade.update();
        }

        assertEquals(spyExtortionUpgrade.getActivateTimeRemaining() /
                (float) spyExtortionUpgrade.getUpgradeDuration(), spyExtortionUpgrade.meter.getValue(), 0.01);
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
