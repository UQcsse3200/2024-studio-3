/* package com.csse3200.game.services;

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
import com.csse3200.game.screens.MainGameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
        mapLayoutSpy = new MapLayout();

        mapLayoutSpy.create();

        ServiceLocator.registerMapLayout(mapLayoutSpy);
        // Verify the event handler is initialized


//       ServiceLocator.getResourceService().loadTextures(forestTextures);
        assertNotNull(mapLayoutSpy.getEvents());

        // Create a mock listener and add it to the event handler
        EventListener1<String> mockListener = mock(EventListener1.class);

        // Trigger the event and verify listener is called
        mapLayoutSpy.getEvents().trigger("load", "level1");
        verify(mockListener).handle("level1");
    }

    @Test
    void testLoadWithBenchRow() throws Exception {
        // Mock the file reader
        String mockMapData = "X123\n";  // A sample line from the map file
        BufferedReader mockReader = new BufferedReader(new StringReader(mockMapData));
        MapLayout mapLayout = Mockito.spy(new MapLayout());

        // Mock static methods
        MockedStatic<BenchGenerator> mockedBenchGenerator = mockStatic(BenchGenerator.class);
        mockedBenchGenerator.when(() -> BenchGenerator.createBenchRow(anyInt(), anyInt(), anyInt()))
                .thenReturn(new ArrayList<Bench>());

        // Mock logger if needed or track console output

        // Call the load method
        mapLayout.load("level1");

        // Verify the file reading
     //   verify(mapLayout, times(1)).spawnEntity(any(Bench.class));

        // Clean up mocks
        mockedBenchGenerator.close();
    }

}

*/