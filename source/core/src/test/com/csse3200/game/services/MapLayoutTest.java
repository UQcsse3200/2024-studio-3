package com.csse3200.game.services;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class MapLayoutTest {
    private AutoCloseable mocks;
    @Mock Map map;
    @Mock Viewport viewport;
    @Spy MapLayout mapLayoutSpy;
    @Mock TextureAtlas atlas;
    @Mock MapLayout mapLayoutmock;
    @Mock TerrainFactory factory;
    int benchesCreated = 0;
    public String[] validStations = new String[14];

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

        validStations = new String[]{"b", "s", "u", "t", "c", "a", "E", "O", "B", "C", "G", "N", "S", "F"};
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerRenderService(renderService);

        ServiceLocator.registerPhysicsEngine(mock(PhysicsEngine.class));
        ServiceLocator.registerPhysicsComponent(physicsComponent);
        ServiceLocator.registerPhysicsService(physicsService);
        ServiceLocator.registerResourceService(resourceService);

        factory = mock(TerrainFactory.class);

        mapLayoutmock = mock(MapLayout.class);

        mapLayoutSpy = spy(new MapLayout());
        ServiceLocator.registerMapLayout(mapLayoutSpy);
        map = mock(Map.class);
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

        verify(mapLayoutSpy).readBench("X", 0, 9, 4);
        verify(mapLayoutSpy).readBench("Y", 0, 2, 4);
        verify(mapLayoutSpy).readStation("N", 2, 5);
        verify(mapLayoutSpy).readStation("S", 0, 7);
    }
    /**
     @Test
     void testReadBench() {
     //when(mapLayoutmock.readBench(anyString(), anyInt(), anyInt(), anyInt()))
     //      .thenReturn(new ArrayList<Bench>());
     //when(mapLayoutmock.readStation(anyString(), anyInt(), anyInt()))
     //    .thenReturn(mock(Entity.class));

     try(MockedConstruction<BenchGenerator> benchGenerator =
     Mockito.mockConstruction(BenchGenerator.class)){

     when(mapLayoutmock.readBench(anyString(), anyInt(), anyInt(), anyInt()))
     .thenReturn(new ArrayList<Bench>());
     when(mapLayoutmock.readStation(anyString(), anyInt(), anyInt()))
     .thenReturn(mock(Entity.class));
     when(mapLayoutmock.load(GdxGame.LevelType.LEVEL_1)).thenReturn(mock(Map.class));
     map = mapLayoutmock.load(GdxGame.LevelType.LEVEL_1);
     System.out.println(map.getBenches());
     assertEquals(0, map.getNumBenches());
     }

     }


     @Test
     void testReadStation() {
     //when(mapLayoutmock.readBench(anyString(), anyInt(), anyInt(), anyInt()))
     //      .thenReturn(new ArrayList<Bench>());
     //when(mapLayoutmock.readStation(anyString(), anyInt(), anyInt()))
     //    .thenReturn(mock(Entity.class));

     try(MockedConstruction<BenchGenerator> benchGenerator =
     Mockito.mockConstruction(BenchGenerator.class)){

     when(mapLayoutmock.readBench(anyString(), anyInt(), anyInt(), anyInt()))
     .thenReturn(new ArrayList<Bench>());
     when(mapLayoutmock.readStation(anyString(), anyInt(), anyInt()))
     .thenReturn(mock(Entity.class));
     when(mapLayoutmock.load(GdxGame.LevelType.LEVEL_1)).thenReturn(mock(Map.class));
     map = mapLayoutmock.load(GdxGame.LevelType.LEVEL_1);
     System.out.println(map.getBenches());
     assertEquals(0, map.getNumBenches());
     }

     }
     */
    @Test
    void validateStations() {
        for (String station : validStations) {
            assertTrue(mapLayoutSpy.validateStation(station));
        }
        assertFalse(mapLayoutSpy.validateStation("L"));
    }

}