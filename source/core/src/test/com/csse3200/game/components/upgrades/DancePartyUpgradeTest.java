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
public class DancePartyUpgradeTest {
    @Mock RenderService renderService;
    @Spy OrthographicCamera camera;
    @Mock Stage stage;
    @Mock Viewport viewport;
    @Mock ResourceService resourceService;
    @Mock GameTime gameTime;
    @Mock Texture textureMock;
    @Mock Table mockLayout;
    @Mock ProgressBar meter;
    @Mock Label mockText;
    @Mock CombatStatsComponent combatStatsComponent;
    private EventHandler randomComboEventHandler;
    private EventHandler docketEventHandler;
    RandomComboService randomComboService;
    DocketService docketService;
    private DancePartyUpgrade dancePartyUpgrade;

    @BeforeEach
    void setUp() {
        ServiceLocator.clear();

        randomComboEventHandler = new EventHandler();
        docketEventHandler = new EventHandler();
        randomComboService = new RandomComboService(randomComboEventHandler);
        docketService = spy(new DocketService(randomComboEventHandler));

        ServiceLocator.registerRandomComboService(randomComboService);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerTimeSource(gameTime);
        ServiceLocator.registerDocketService(docketService);

        lenient().when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(textureMock);
        lenient().when(renderService.getStage()).thenReturn(stage);
        lenient().when(renderService.getStage().getViewport()).thenReturn(viewport);
        lenient().when(renderService.getStage().getViewport().getCamera()).thenReturn(camera);

        dancePartyUpgrade = new DancePartyUpgrade(combatStatsComponent);
        dancePartyUpgrade.setStage(stage);
        dancePartyUpgrade.layout = mockLayout;
        dancePartyUpgrade.meter = meter;
        dancePartyUpgrade.text = mockText;
        dancePartyUpgrade.create();
    }

    @Test
    void testDancePartyActivates() {
        assertNotNull(dancePartyUpgrade);
        when(combatStatsComponent.getGold()).thenReturn(100);
        dancePartyUpgrade.activate();

        AtomicBoolean isActive = new AtomicBoolean(false);
        docketEventHandler.addListener("Dancing", () -> {
            isActive.set(true);
        });
        docketEventHandler.trigger("Dancing");

        assertEquals(1f, dancePartyUpgrade.meter.getValue());
        assertTrue(isActive.get());
        assertTrue(dancePartyUpgrade.isActive());
        assertTrue(dancePartyUpgrade.layout.isVisible());
        assertEquals(dancePartyUpgrade.getActiveTimeRemaining(), dancePartyUpgrade.getUpgradeDuration());
    }

    @Test
    void testDancePartyDeactivates() {
        dancePartyUpgrade.activate();
        lenient().when(meter.hasParent()).thenReturn(true);
        dancePartyUpgrade.deactivate();

        AtomicBoolean isActive = new AtomicBoolean(false);
        docketEventHandler.addListener("UnDancing", () -> {
            isActive.set(true);
        });
        docketEventHandler.trigger("UnDancing");

        assertTrue(isActive.get());
        verify(dancePartyUpgrade.meter).remove();
        verify(dancePartyUpgrade.text).remove();

        assertFalse(dancePartyUpgrade.isActive());
        assertFalse(dancePartyUpgrade.layout.isVisible());
        assertEquals(0f, dancePartyUpgrade.meter.getValue());
    }

    @Test
    void testLoseGoldOnPurchase() {
        when(combatStatsComponent.getGold()).thenReturn(100);
        dancePartyUpgrade.activate();
        verify(combatStatsComponent).addGold(-20);
    }

    @Test
    void testInsufficientGold() {
        when(combatStatsComponent.getGold()).thenReturn(10);
        AtomicBoolean notEnoughMoney = new AtomicBoolean(false);
        randomComboEventHandler.addListener("notenoughmoney", () -> {
            notEnoughMoney.set(true);
        });

        dancePartyUpgrade.activate();

        assertTrue(notEnoughMoney.get());
        assertFalse(dancePartyUpgrade.isActive());
        assertFalse(dancePartyUpgrade.layout.isVisible());
    }

    @Test
    void testDancePartyFor30Seconds() {
        DancePartyUpgrade spyDancePartyUpgrade = spy(dancePartyUpgrade);
        when(combatStatsComponent.getGold()).thenReturn(100);
        spyDancePartyUpgrade.activate();

        when(gameTime.getDeltaTime()).thenReturn(1f);
        for (int i = 0; i < 15; i++) {
            spyDancePartyUpgrade.update();
        }

        assertEquals(spyDancePartyUpgrade.getActiveTimeRemaining() /
                (float) spyDancePartyUpgrade.getUpgradeDuration(), spyDancePartyUpgrade.meter.getValue());

        for (int i = 0; i < 15; i++) {
            spyDancePartyUpgrade.update();
        }

        assertEquals(0, spyDancePartyUpgrade.getActiveTimeRemaining());
        verify(spyDancePartyUpgrade).deactivate();
        assertFalse(spyDancePartyUpgrade.getPlaySound());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 20, 25, 30})
    void testMeterValueAtDifferentLevelsOfDepletion(int totalDepletedTime) {
        DancePartyUpgrade spyDancePartyUpgrade = spy(dancePartyUpgrade);
        when(combatStatsComponent.getGold()).thenReturn(100);
        spyDancePartyUpgrade.activate();

        when(gameTime.getDeltaTime()).thenReturn(1f);
        for (int i = 0; i < totalDepletedTime; i++) {
            spyDancePartyUpgrade.update();
        }

        assertEquals(spyDancePartyUpgrade.getActiveTimeRemaining() /
                (float) spyDancePartyUpgrade.getUpgradeDuration(), spyDancePartyUpgrade.meter.getValue(), 0.01);
    }

    @Test
    void testDispose() {
        dancePartyUpgrade.dispose();
        verify(resourceService).unloadAssets(SpeedBootsUpgrade.whiteBgTexture);
        verify(resourceService).unloadAssets(SpeedBootsUpgrade.greenTexture);
    }

    @AfterEach
    void tearDown() {
        ServiceLocator.clear();
    }
}
