package com.csse3200.game.services;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.map.BenchGenerator;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.station.*;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.areas.map.Map;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.entities.factories.StationFactory;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.extensions.GameExtension;
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
    @Spy MapLayout mapLayoutSpy;
    @Mock MapLayout mapLayoutMock;
    @Mock TerrainFactory factory;
    public String[] validStations = new String[14];


    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        GameTime gameTime = mock(GameTime.class);
        ServiceLocator.registerTimeSource(gameTime);

//      SARAH'S BIT ____
        ResourceService resourceService = mock(ResourceService.class);
        EntityService entityService = mock(EntityService.class);
        RenderService renderService = mock(RenderService.class);

        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerPhysicsComponent(new PhysicsComponent());



        validStations = new String[]{"b", "s", "u", "t", "c", "a", "E", "O", "B", "C", "G", "N", "S", "F"};

        factory = mock(TerrainFactory.class);

        mapLayoutMock = mock(MapLayout.class);

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

        //event handler has been initialised
        assertNotNull(mapLayoutSpy.getEvents());

        //Event handler handles and triggers a given event
        EventListener0 mockListener = mock(EventListener0.class);
        mapLayoutSpy.getEvents().addListener("mockEvent",mockListener);
        mapLayoutSpy.getEvents().trigger("mockEvent");
        verify(mockListener).handle();
    }


    @Test
    void testLoad() {
        when(mapLayoutSpy.parseLine(any(String[].class), anyInt(), anyInt())).thenReturn(new ArrayList<>());

        mapLayoutSpy.load(GdxGame.LevelType.LEVEL_1);

        assertEquals("Level 1", mapLayoutSpy.getMapName());
        assertEquals(7, mapLayoutSpy.getMapWidth());
        assertEquals(4, mapLayoutSpy.getMapHeight());

        verify(mapLayoutSpy).parseLine("X09 Y02 Y97".split(""), 4, 0);
        verify(mapLayoutSpy).parseLine("X09 Y02 Y97".split(""), 4, 4);
        verify(mapLayoutSpy).parseLine("X09 Y02 Y97".split(""), 4, 8);
    }

    @Test
    void validateStations() {
        for (String station : validStations) {
            assertTrue(mapLayoutSpy.validateStation(station));
        }
        assertFalse(mapLayoutSpy.validateStation("L"));
    }

    @Test
    void readSingleBench() {
        try(MockedConstruction<Bench> ignored =
                    Mockito.mockConstruction(Bench.class)) {
            ArrayList<Bench> bench = mapLayoutSpy.parseLine("X41".split(""), 0, 0);
            ArrayList<Bench> expectedBench = BenchGenerator.singleBench(4,0);
            //checks coordinates -> can't compare benches directly as hash codes conflict
            assertEquals(bench.getFirst().getX(), expectedBench.getFirst().getX());
            assertEquals(bench.getFirst().getY(), expectedBench.getFirst().getY());
        }
    }

    @Test
    void readBenchRow() {
        try(MockedConstruction<Bench> ignored =
                    Mockito.mockConstruction(Bench.class))
        {
            ArrayList<Bench> bench = mapLayoutSpy.parseLine("X43".split(""), 0, 0);
            ArrayList<Bench> expectedBench = BenchGenerator.createBenchRow(4, 6, 0);
            //checks coordinates -> can't compare benches directly as hash codes conflict

            //coordinates of starting bench
            assertEquals(bench.getFirst().getX(), expectedBench.getFirst().getX());
            assertEquals(bench.getFirst().getY(), expectedBench.getFirst().getY());

            //coordinates of ending bench -> assumes bench row loads properly (tested in
            // benchGenerator)
            assertEquals(bench.get(2).getX(), expectedBench.get(2).getX());
            assertEquals(bench.get(2).getY(), expectedBench.get(2).getY());
        }
    }

    @Test
    void readBenchColumn() {
        try(MockedConstruction<Bench> ignored =
                    Mockito.mockConstruction(Bench.class))
        {
            ArrayList<Bench> bench = mapLayoutSpy.parseLine("Y43".split(""), 0, 0);
            ArrayList<Bench> expectedBench = BenchGenerator.createBenchColumn(8, -4,
                    -1);
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

    @Test
    void bananaTest() {
        stationTest("b", 4, 4);
    }

    @Test
    void cuttingBoardTest() {
        stationTest("G", 6, 2);
    }

    @Test
    void stoveTest() {
        stationTest("E", 2, 2);
    }

    @Test
    void strawberryTestPara() {
        stationTest("s", 7, 3);
    }

    @Test
    void beefFridgeTestPara() {
        stationTest("B", 1, 1);
    }


    void stationTest(String type, int x, int y) {
        try(MockedConstruction<StationFactory> ignored =
                    Mockito.mockConstruction(StationFactory.class)) {

            Texture texture = mock(Texture.class);
            when(ServiceLocator.getResourceService().getAsset(anyString(), eq(Texture.class))).thenReturn(texture);

            Entity readStation = mapLayoutSpy.readStation(type, x, y);
            switch (type) {
                case "b":
                    assertEquals("bananaTree", readStation.getComponent(IngredientStationHandlerComponent.class).getType());
                    break;
                case "s":
                    assertEquals("strawberriesStation", readStation.getComponent(IngredientStationHandlerComponent.class).getType());
                    break;
                case "u":
                    assertEquals("lettuceStation", readStation.getComponent(IngredientStationHandlerComponent.class).getType());
                    break;
                case "t":
                    assertEquals("tomatoStation", readStation.getComponent(IngredientStationHandlerComponent.class).getType());
                    break;
                case "c":
                    assertEquals("cucumberStation", readStation.getComponent(IngredientStationHandlerComponent.class).getType());
                    break;
                case "a":
                    assertEquals("acaiStation", readStation.getComponent(IngredientStationHandlerComponent.class).getType());
                    break;
                case "E":
                    assertEquals("stove", readStation.getComponent(StationItemHandlerComponent.class).getType());
                    break;
                case "O":
                    assertEquals("oven", readStation.getComponent(StationItemHandlerComponent.class).getType());
                    break;
                case "B":
                    assertEquals("beefStation", readStation.getComponent(IngredientStationHandlerComponent.class).getType());
                    assertEquals("beef", readStation.getComponent(IngredientStationHandlerComponent.class).getIngredientName());
                    break;
                case "C":
                    assertEquals("beefStation", readStation.getComponent(IngredientStationHandlerComponent.class).getType());
                    assertEquals("chocolate", readStation.getComponent(IngredientStationHandlerComponent.class).getIngredientName());
                    break;
                case "G":
                    assertEquals("cutting board", readStation.getComponent(StationItemHandlerComponent.class).getType());
                    break;
                case "N":
                    boolean isABin = false;
                    for (Component component : readStation.getCreatedComponents()) {
                        if (component instanceof StationBinComponent) {
                            isABin = true;
                            break;
                        }
                    }
                    assertTrue(isABin);
                    break;
                case "S":
                    boolean isSubmissionWindow = false;
                    for (Component component : readStation.getCreatedComponents()) {
                        if (component instanceof StationServingComponent) {
                            isSubmissionWindow = true;
                            break;
                        }
                    }
                    assertTrue(isSubmissionWindow);
                    break;
                case "F":
                    boolean isFireExtinguisher = false;
                    for (Component component : readStation.getCreatedComponents()) {
                        if (component instanceof FireExtinguisherHandlerComponent) {
                            isFireExtinguisher = true;
                            break;
                        }
                    }
                    assertTrue(isFireExtinguisher);
                    break;
            }

            assertEquals(readStation.getPosition(), new Vector2(x + 4, y - 4));
        }
    }

}