package com.csse3200.game.services;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.map.BenchGenerator;
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
        GameTime gameTime = mock(GameTime.class);
        when(gameTime.getTime()).thenReturn(0L);
        ServiceLocator.registerTimeSource(gameTime);

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

    /**
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
    */

    @Test
    void testLoad() {
        when(mapLayoutSpy.parseLine(any(String[].class), anyInt(), anyInt())).thenReturn(new ArrayList<Bench>());

        Map map = mapLayoutSpy.load(GdxGame.LevelType.LEVEL_1);

        verify(mapLayoutSpy).parseLine("X09 Y02 Y97".split(""), 4, 0);
        verify(mapLayoutSpy).parseLine("X09 Y02 Y97".split(""), 4, 4);
        verify(mapLayoutSpy).parseLine("X09 Y02 Y97".split(""), 4, 8);
    }

    /**
     @Test
     void testReadBench() {
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
    /**
    @Test
    void validateStations() {
        for (String station : validStations) {
            assertTrue(mapLayoutSpy.validateStation(station));
        }
        assertFalse(mapLayoutSpy.validateStation("L"));
    }
    */

    @Test
    void readSingleBench() {
        try(MockedConstruction<Bench> ignored =
                    Mockito.mockConstruction(Bench.class)) {
            ArrayList<Bench> bench = mapLayoutSpy.readBench("X", 0, 1, 4);
            ArrayList<Bench> expectedBench = BenchGenerator.singleBench(4,0);
            //checks coordinates -> can't compare benches directly as hash codes conflict
            assertEquals(bench.get(0).getX(), expectedBench.get(0).getX());
            assertEquals(bench.get(0).getY(), expectedBench.get(0).getY());

        }
    }

    @Test
    void readBenchRow() {
        try(MockedConstruction<Bench> ignored =
                    Mockito.mockConstruction(Bench.class)) {
            ArrayList<Bench> bench = mapLayoutSpy.readBench("X", 0, 3, 4);
            ArrayList<Bench> expectedBench = BenchGenerator.createBenchRow(4, 6,0);
            //checks coordinates -> can't compare benches directly as hash codes conflict

            //coordinates of starting bench
            assertEquals(bench.get(0).getX(), expectedBench.get(0).getX());
            assertEquals(bench.get(0).getY(), expectedBench.get(0).getY());

            //coordinates of ending bench -> assumes bench row loads properly (tested in
            // benchGenerator)
            assertEquals(bench.get(2).getX(), expectedBench.get(2).getX());
            assertEquals(bench.get(2).getY(), expectedBench.get(2).getY());

        }
    }

//    FOR PARAMETRISED TESTING _____
//
//    void testReadBench(String type, int startCol, int size, int row) {
//
//        try(MockedConstruction<BenchGenerator> benchGenerator =
//                    Mockito.mockConstruction(BenchGenerator.class)) {
//
//            ArrayList<Bench> bench = mapLayoutSpy.readBench(type, startCol, size, row);
//
//            // assert bench read is right
//            if (size == 1) {
//                switch (type) {
//                    case "X":
//                        verify(benchGenerator).singleBench(startCol + 4, row - 4);
//                        break;
//                    case "Q":
//                        assertEquals(bench, benchGenerator.singleShadowBench(startCol + 4, row - 4));
//                        break;
//                    case "P":
//                        assertEquals(bench, benchGenerator.singleBlocker(startCol + 4, row - 4));
//                        break;
//                    default:
//                        assertTrue(true);
//                }
//            } else {
//                switch (type) {
//                    case "X":
//                        assertEquals(bench, benchGenerator.createBenchRow(startCol + 4, startCol + size +4, row - 4));
//                        break;
//                    case "Y":
//                        benchGenerator.createBenchColumn(startCol + 4, row - 4, row + size - 4);
//                        break;
//                    default:
//                        assertTrue(true);
//                }
//            }
//
//
//        }
//
//
//
//    }

}