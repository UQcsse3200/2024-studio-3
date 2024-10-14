package com.csse3200.game.components;


import com.csse3200.game.rendering.AnimationRenderComponent;


public class FlameComponent extends Component {
    AnimationRenderComponent animator;
    @Override
    public void create() {
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        animator.startAnimation("flame");
    }
}
