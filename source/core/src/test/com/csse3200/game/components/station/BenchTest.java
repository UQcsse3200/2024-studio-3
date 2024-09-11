package com.csse3200.game.components.station;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.physics.PhysicsService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

public class BenchTest {

    @BeforeEach
    public void setUp() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerRenderService(new RenderService());
        ResourceService mockResourceService = mock(ResourceService.class);
        Texture mockTexture = mock(Texture.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(mockTexture);
        ServiceLocator.registerResourceService(mockResourceService);

    }

    @Test
    public void testCreateBench() {
        Entity oven = Bench.createBench("left_border");
        assertNotNull(oven);
        assertNotNull(oven.getComponent(TextureRenderComponent.class));
        assertNotNull(oven.getComponent(PhysicsComponent.class));
        assertNotNull(oven.getComponent(ColliderComponent.class));
        assertNotNull(oven.getComponent(StationItemHandlerComponent.class));

        assertEquals(BodyDef.BodyType.StaticBody, oven.getComponent(PhysicsComponent.class).getBody().getType());
    }
    @Test
    public void testCreateBench2() {
        Entity oven = Bench.createBench("middle");
        assertNotNull(oven);
        assertNotNull(oven.getComponent(TextureRenderComponent.class));
        assertNotNull(oven.getComponent(PhysicsComponent.class));
        assertNotNull(oven.getComponent(ColliderComponent.class));
        assertNotNull(oven.getComponent(StationItemHandlerComponent.class));

        assertEquals(BodyDef.BodyType.StaticBody, oven.getComponent(PhysicsComponent.class).getBody().getType());
    }
    @Test
    public void testCreateBench3() {
        Entity oven = Bench.createBench("single");
        assertNotNull(oven);
        assertNotNull(oven.getComponent(TextureRenderComponent.class));
        assertNotNull(oven.getComponent(PhysicsComponent.class));
        assertNotNull(oven.getComponent(ColliderComponent.class));
        assertNotNull(oven.getComponent(StationItemHandlerComponent.class));

        assertEquals(BodyDef.BodyType.StaticBody, oven.getComponent(PhysicsComponent.class).getBody().getType());
    }
}