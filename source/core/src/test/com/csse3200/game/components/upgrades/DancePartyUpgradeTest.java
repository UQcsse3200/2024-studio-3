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
    @Mock KeyboardPlayerInputComponent keyboardPlayerInputComponent;
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

        assertTrue(isActive.get());
        assertTrue(dancePartyUpgrade.isActive());
        assertTrue(dancePartyUpgrade.layout.isVisible());
        assertEquals(dancePartyUpgrade.getActiveTimeRemaining(), dancePartyUpgrade.getUpgradeDuration());
        verify(combatStatsComponent).addGold(-20);
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

    @AfterEach
    void tearDown() {
        ServiceLocator.clear();
    }
}
