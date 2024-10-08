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

class BenchTest {

    @BeforeEach
    public void setUp() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerRenderService(new RenderService());
        ResourceService mockResourceService = mock(ResourceService.class);
        Texture mockTexture = mock(Texture.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(mockTexture);
        ServiceLocator.registerResourceService(mockResourceService);

    }

    /**
     * Helper function to help test the bench creation.
     * @param benchType the type of bench being created
     */
    void validateBenchCreation(String benchType) {
        Entity bench = Bench.createBench(benchType);
        assertNotNull(bench);
        assertNotNull(bench.getComponent(TextureRenderComponent.class));
        assertNotNull(bench.getComponent(PhysicsComponent.class));
        assertNotNull(bench.getComponent(ColliderComponent.class));
        assertNotNull(bench.getComponent(StationItemHandlerComponent.class));
        assertEquals(BodyDef.BodyType.StaticBody, bench.getComponent(PhysicsComponent.class).getBody().getType());
    }

    /**
     * Test the creation of a bench with the type "left_border".
     */
    @Test
    void testCreateBenchLeftBorder() {
        validateBenchCreation("left_border");
    }

    /**
     * Test the creation of a bench with the type "middle".
     */
    @Test
    void testCreateBenchMiddle() {
        validateBenchCreation("middle");
    }

    /**
     * Test the creation of a bench with the type "single".
     */
    @Test
    void testCreateBenchSingle() {
        validateBenchCreation("single");
    }
}