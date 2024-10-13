package com.csse3200.game.areas.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.csse3200.game.entities.benches.Bench;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsEngine;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.ArrayList;

import static com.csse3200.game.entities.benches.Bench.createBench;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test class for the Bench entity.
 */
/**
@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class BenchGeneratorTest {
    @Mock
    ResourceService resourceService;
    @Mock
    PhysicsService physicsService;
    @Mock
    PhysicsEngine physicsEngine;
    @Mock
    PolygonShape polygonShape;
    @Mock
    InventoryComponent inventoryComponent;
    @Mock
    BodyDef bodyDef;
    @Mock Texture texture;
    @Mock Bench bench;
    @Mock PhysicsComponent physicsComponent;
    @Spy BenchGenerator benchSpy;
    private Bench benc3h;
    private Bench benchWithDefault;


    @BeforeEach
    void setUp() {
        // Initialize Box2D
        Box2D.init();

        // Mock services and components
        resourceService = mock(ResourceService.class);
        physicsService = mock(PhysicsService.class);
        physicsEngine = mock(PhysicsEngine.class);
        physicsComponent = mock(PhysicsComponent.class);
        inventoryComponent = mock(InventoryComponent.class);
        texture = mock(Texture.class);
        polygonShape = mock(PolygonShape.class);
        bodyDef = mock(BodyDef.class);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        ServiceLocator.registerRenderService(mock(RenderService.class));

        // Mock texture retrieval
        when(resourceService.getAsset(anyString(), any())).thenReturn(texture);

        // Register services
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerPhysicsService(physicsService);
        ServiceLocator.registerPhysicsEngine(physicsEngine);

        // Mock PhysicsComponent and its body
        physicsComponent = mock(PhysicsComponent.class);
        Body body = mock(Body.class);
        when(physicsComponent.getBody().getType()).thenReturn(BodyDef.BodyType.StaticBody);
        when(physicsComponent.getBody()).thenReturn(body);
        when(physicsComponent.setBodyType(any())).thenReturn(physicsComponent);

        ServiceLocator.registerPhysicsComponent(physicsComponent);



        // Initialize benches before each test
        bench = mock(Bench.class);
        when(bench.getComponent(TextureRenderComponent.class)).thenReturn(mock(TextureRenderComponent.class));
        when(bench.getComponent(PhysicsComponent.class)).thenReturn(physicsComponent);
        when(bench.getComponent(ColliderComponent.class)).thenReturn(mock(ColliderComponent.class));
        when(bench.getComponent(StationItemHandlerComponent.class)).thenReturn(mock(StationItemHandlerComponent.class));
        when(bench.getComponent(InventoryComponent.class)).thenReturn(inventoryComponent);
        when(bench.getComponent(PhysicsComponent.class).getBody().getType()).thenReturn(BodyDef.BodyType.StaticBody);
        benchWithDefault = new Bench(1,3);
        benchSpy = spy(new BenchGenerator());

    }



    @Test
    void testCreateBenchColumn() {
        ArrayList<Bench> benches =  benchSpy.createBenchColumn(5, 1, 4);

        assertEquals(4, benches.size(), "Column should have 4 benches");
        // assertEquals("bottom_shadow", benches.get(0).getType());
        //assertEquals("vertical", benches.get(1).getType());
        //assertEquals("vertical", benches.get(2).getType());
        //assertEquals("top", benches.get(3).getType());
    }

}
*/