package com.csse3200.game.entities.benches;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.PlayerFactory;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BenchTest {

    private Entity bench;
    private Entity player;

    @BeforeEach
    void setUp() {
        bench = Bench.createBench("bench", 2.0f);
        player = PlayerFactory.createPlayer();
    }

    @Test
    void shouldCreateBenchWithComponents() {
        // Ensure the bench entity has the required components
        assertNotNull(bench.getComponent(TextureRenderComponent.class));
        assertNotNull(bench.getComponent(PhysicsComponent.class));
        assertNotNull(bench.getComponent(ColliderComponent.class));
    }

    @Test
    void shouldSetAndGetPosition() {
        bench.setPosition(10f, 20f);
        assertEquals(10f, bench.getPosition().x);
        assertEquals(20f, bench.getPosition().y);

        bench.setPosition(0f, 0f);
        assertEquals(0f, bench.getPosition().x);
        assertEquals(0f, bench.getPosition().y);
    }

    @Test
    void shouldSetAndGetScale() {
        bench.setScale(1.5f, 2.0f);
        assertEquals(1.5f, bench.getScale().x);
        assertEquals(2.0f, bench.getScale().y);
    }

    @Test
    //test that the player can't be on the same position as the bench
    void shouldNotOverlap() {
        bench.setPosition(10f, 20f);
        player.setPosition(10f, 20f);

        boolean isOverlapping = checkPositionOverlap(bench, player);

        assertFalse(isOverlapping);
    }

    private boolean checkPositionOverlap(Entity entity1, Entity entity2) {
        // Get the positions of the two entities
        Vector2 position1 = entity1.getPosition();
        Vector2 position2 = entity2.getPosition();

        // Check if their positions are the same (within a small tolerance for floating-point precision)
        return position1.epsilonEquals(position2, 0.1f);
    }

}