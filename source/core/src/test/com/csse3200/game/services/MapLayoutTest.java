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
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.areas.map.Map;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.events.listeners.EventListener1;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.rendering.Renderable;
import com.csse3200.game.screens.MainGameScreen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class MapLayoutTest {
    private AutoCloseable mocks;

    @Mock Viewport viewport;
    @Spy MapLayout mapLayoutSpy;
    @Mock TextureAtlas atlas;

    @Mock TerrainFactory factory;
    int benchesCreated = 0;

    @BeforeEach
    void setUp() {
//      SARAH'S BIT ____
//        resourceService = new ResourceService();
//
//        TerrainFactory factory = mock(TerrainFactory.class);
//
//        ServiceLocator.registerRenderService(new RenderService());
//        ServiceLocator.registerPhysicsService(new PhysicsService());
//        ServiceLocator.registerEntityService(new EntityService());
//        ServiceLocator.registerEntityService(new EntityService());
//        ServiceLocator.registerResourceService(resourceService);

        mocks = MockitoAnnotations.openMocks(this);

        //RenderService renderService = mock(RenderService.class);
        //EntityService entityService = mock(EntityService.class);
        //ResourceService resourceService = mock(ResourceService.class);
        //PhysicsService physicsService = mock(PhysicsService.class);
        //Stage stage = mock(Stage.class);

        //ServiceLocator.registerResourceService(resourceService);
        //ServiceLocator.registerEntityService(entityService);
        //ServiceLocator.registerRenderService(renderService);
        //ServiceLocator.registerPhysicsService(physicsService);

        factory = mock(TerrainFactory.class);

        mapLayoutSpy = spy(new MapLayout());
        ServiceLocator.registerMapLayout(mapLayoutSpy);

    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
        ServiceLocator.clear();
    }

    @Test
    void shouldInitializeEventHandlerProperly() {

        //event handler has been initalised
        assertNotNull(mapLayoutSpy.getEvents());

        //Event handler handles and triggers a given event
        EventListener0 mockListener = mock(EventListener0.class);
        mapLayoutSpy.getEvents().addListener("mockEvent",mockListener);
        mapLayoutSpy.getEvents().trigger("mockEvent");
        verify(mockListener).handle();
    }

    @Test
    void testLoad() {

        when(mapLayoutSpy.readBench(anyString(), anyInt(), anyInt(), anyInt()))
                .thenReturn(new ArrayList<Bench>());
        when(mapLayoutSpy.readStation(anyString(), anyInt(), anyInt()))
                .thenReturn(mock(Entity.class));

        Map map = mapLayoutSpy.load(1);

        verify(mapLayoutSpy).readBench("X", 0, 8, 4);
        verify(mapLayoutSpy).readBench("Y", 0, 7, 4);
        verify(mapLayoutSpy).readStation("S", 0, 5);
        verify(mapLayoutSpy).readStation("N", 6, 8);
    }
}