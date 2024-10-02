package com.csse3200.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.map.BenchGenerator;
import com.csse3200.game.areas.map.Map;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.events.listeners.EventListener1;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.screens.MainGameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class MapLayoutTest {
    @Mock
    Stage stage;
    @Mock
    ResourceService resourceService;
    @Mock
    Viewport viewport;
    @Spy
    private MapLayout mapLayoutSpy;
    @Mock
    private TextureAtlas atlas;
    private static final Logger logger = LoggerFactory.getLogger(MapLayoutTest.class);

    @BeforeEach
    void setUp() {

        resourceService = new ResourceService();

        TerrainFactory factory = mock(TerrainFactory.class);

        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerResourceService(resourceService);

    }


    @Test
    void shouldInitializeEventHandlerProperly() {


        // Initialize MapLayout
        MapLayout mapLayout = new MapLayout();

        // Load the map level
        int level = 1; // You can modify this if you have different levels
        Map map = mapLayout.load(level);

        // Log the results
        logger.info("Map loaded with benches: " + map.getBenches().size() + " and stations: " + map.getStations().size());

        // Print the details of benches and stations for verification (if needed)
        for (Bench bench : map.getBenches()) {
            logger.info("Bench position: " + bench.getPosition());
        }

        for (Entity station : map.getStations()) {
            logger.info("Station position: " + station.getPosition());

        }
    }
}

