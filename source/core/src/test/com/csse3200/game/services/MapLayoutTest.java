package com.csse3200.game.services;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.areas.map.Map;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsEngine;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.physics.components.PhysicsComponent;
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
        ResourceService resourceService = mock(ResourceService.class);
//
        TerrainFactory factory = mock(TerrainFactory.class);
//
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerResourceService(resourceService);

        mocks = MockitoAnnotations.openMocks(this);

        RenderService renderService = mock(RenderService.class);
        EntityService entityService = mock(EntityService.class);

        PhysicsService physicsService = mock(PhysicsService.class);
        Stage stage = mock(Stage.class);

        PhysicsComponent physicsComponent = mock(PhysicsComponent.class);
        Body body = mock(Body.class);


        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerRenderService(renderService);

        ServiceLocator.registerPhysicsEngine(mock(PhysicsEngine.class));
        ServiceLocator.registerPhysicsComponent(physicsComponent);
        ServiceLocator.registerPhysicsService(physicsService);
        ServiceLocator.registerResourceService(resourceService);

        factory = mock(TerrainFactory.class);

        mapLayoutSpy = spy(new MapLayout());
        ServiceLocator.registerMapLayout(mapLayoutSpy);
        ServiceLocator.registerInteractableService(new InteractableService());

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

//        when(mapLayoutSpy.readBench(any(String[].class), anyInt(), anyInt()))
//                .thenReturn(new ArrayList<Bench>());
//        when(mapLayoutSpy.readStation(anyString(), anyInt(), anyInt()))
//                .thenReturn(mock(Entity.class));
//
//        Map map = mapLayoutSpy.load(GdxGame.LevelType.LEVEL_1);

        //verify(mapLayoutSpy).readBench("X", 0, 8, 4);
      //  verify(mapLayoutSpy).readBench("Y", 0, 7, 4);
        //verify(mapLayoutSpy).readStation("S", 0, 7);
        //verify(mapLayoutSpy).readStation("N", 6, 8);
    }
    /**

    void testReadBench() {

        MapLayout mapLayout = new MapLayout();
        ArrayList<Bench> benches = mapLayout.readBench("X", 0, 8, 4);
        assertEquals(4, benches.size());
        assertEquals(0, benches.get(0).getX());
        assertEquals(8, benches.get(0).getY());
        assertEquals(1, benches.get(1).getX());
        assertEquals(8, benches.get(1).getY());
        assertEquals(2, benches.get(2).getX());
        assertEquals(8, benches.get(2).getY());
        assertEquals(3, benches.get(3).getX());
        assertEquals(8, benches.get(3).getY());
    }
    **/
}