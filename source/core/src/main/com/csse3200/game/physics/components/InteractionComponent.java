package com.csse3200.game.physics.components;


/**
 * A physics component that detects a collision with an interactable object
 */
public class InteractionComponent extends ColliderComponent {
    @Override
    public void create() {
        setSensor(true);
        super.create();
    }
}
