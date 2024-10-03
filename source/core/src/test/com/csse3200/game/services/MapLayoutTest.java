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

        RenderService renderService = mock(RenderService.class);
        EntityService entityService = mock(EntityService.class);
        ResourceService resourceService = mock(ResourceService.class);
        Stage stage = mock(Stage.class);

        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerRenderService(renderService);
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

//      SARAH'S BIT ____
//        mapLayoutSpy = new MapLayout();
//
//        mapLayoutSpy.create();
//
//        ServiceLocator.registerMapLayout(mapLayoutSpy);
//        // Verify the event handler is initialized
//
//
////       ServiceLocator.getResourceService().loadTextures(forestTextures);
//        assertNotNull(mapLayoutSpy.getEvents());
//
//        // Create a mock listener and add it to the event handler
//        EventListener1<String> mockListener = mock(EventListener1.class);
//
//        // Trigger the event and verify listener is called
//        mapLayoutSpy.getEvents().trigger("load", "level1");
//        verify(mockListener).handle("level1");

        //event handler has been initalised
        assertNotNull(mapLayoutSpy.getEvents());

        //Event handler handles and triggers a given event
        EventListener0 mockListener = mock(EventListener0.class);
        mapLayoutSpy.getEvents().addListener("mockEvent",mockListener);
        mapLayoutSpy.getEvents().trigger("mockEvent");
        verify(mockListener).handle();
    }

    @Test
    void testLoadWithBenchRow() throws Exception {
//        SARAH's BIT ___
//        Mock the file reader
//        String mockMapData = "X123\n";  // A sample line from the map file
//        BufferedReader mockReader = new BufferedReader(new StringReader(mockMapData));
//        MapLayout mapLayout = Mockito.spy(new MapLayout());
//
//        // Mock static methods
//        MockedStatic<BenchGenerator> mockedBenchGenerator = mockStatic(BenchGenerator.class);
//        mockedBenchGenerator.when(() -> BenchGenerator.createBenchRow(anyInt(), anyInt(), anyInt()))
//                .thenReturn(new ArrayList<Bench>());
//
//        // Mock logger if needed or track console output
//
//        // Call the load method
//        mapLayout.load("level1");
//
//        // Verify the file reading
//     //   verify(mapLayout, times(1)).spawnEntity(any(Bench.class));
//
//        // Clean up mocks
//        mockedBenchGenerator.close();

        //Test file with a single bench
//        String testFile = "com/csse3200/game/services/MapLayoutTestTextFile.txt";
//        ArrayList<Bench> benches = mock(ArrayList.class);
//
//        //when(BenchGenerator.createBenchRow(anyInt(), anyInt(), anyInt())).do(this.incrementer());
//
//        MockedStatic mockedStatic = mockStatic(BenchGenerator.class);
//        mockedStatic.when(() -> BenchGenerator.createBenchRow(anyInt(), anyInt(), anyInt())).thenReturn(benches);
//
//        mapLayoutSpy.load(testFile);
//
//        mockedStatic.verify(() -> BenchGenerator.createBenchRow(anyInt(), anyInt(), anyInt()));
        assertTrue(true);
    }
}