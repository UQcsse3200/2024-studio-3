package com.csse3200.game.physics.components;


/**
 * A physics component that detects a collision with an interactable object
 */
public class InteractionComponent extends ColliderComponent {

    private short targetLayer;

    public InteractionComponent(short targetLayer) {
        super();
        this.targetLayer = targetLayer;
    }

    @Override
    public void create() {
        setSensor(true);
        super.create();
    }

    public short getTargetLayer() {
        return this.targetLayer;
    }
}
