package com.csse3200.game.components;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlameComponentTest {

    private FlameComponent flameComponent;
    private Entity entity;

    @BeforeEach
    public void setUp() {
        // Create a dummy TextureAtlas
        TextureAtlas dummyAtlas = new TextureAtlas();

        // Create an entity and add an AnimationRenderComponent to it
        entity = new Entity();
        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(dummyAtlas);
        entity.addComponent(animationRenderComponent);

        // Initialize the FlameComponent and attach it to the entity
        flameComponent = new FlameComponent();
        entity.addComponent(flameComponent);

        // Simulate the component's initialization by calling create()
        flameComponent.create();
    }

    @Test
    public void testAnimatorIsAssigned() {
        // Verify that the animator is assigned after calling create()
        assertNotNull(flameComponent.animator, "Animator should be assigned after calling create()");
    }

    @Test
    public void testAnimatorIsAnimationRenderComponent() {
        // Ensure the animator is an instance of AnimationRenderComponent
        assertTrue(flameComponent.animator instanceof AnimationRenderComponent,
                "Animator should be an instance of AnimationRenderComponent");
    }
}
