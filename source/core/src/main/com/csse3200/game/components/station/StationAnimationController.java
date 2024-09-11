package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.rendering.AnimationRenderComponent;

public class StationAnimationController extends Component{
    AnimationRenderComponent animator;
    

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("Active", this::animateActive);
        entity.getEvents().addListener("Inactive", this::animateInactive);

        
    }


    
    void animateActive() {animator.startAnimation("Oven");}
    void animateInactive() {animator.startAnimation("OvenDefault");}
}
