package com.csse3200.game.components.player;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.Component;
import com.csse3200.game.rendering.AnimationRenderComponent;

public class PlayerAnimationController extends Component {
    AnimationRenderComponent animator;
    
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("walkLeft", this::animateLeft);
        entity.getEvents().addListener("walkRight", this::animateRight);
        entity.getEvents().addListener("walkUp", this::animateUp);
        entity.getEvents().addListener("walkDown", this::animateDown);
        entity.getEvents().addListener("walkUpLeft", this::animateUpLeft);
        entity.getEvents().addListener("walkUpRight", this::animateUpRight);
        entity.getEvents().addListener("walkDownLeft", this::animateDownLeft);
        entity.getEvents().addListener("walkDownRight", this::animateDownRight);
        entity.getEvents().addListener("walkStopAnimation", this::animateStop);
        
    }
    
    void animateLeft() {animator.startAnimation("Character_Left");}
    
    void animateRight() {animator.startAnimation("Character_Right");}
    
    void animateUp() {animator.startAnimation("Character_Up");}
    
    void animateDown() {animator.startAnimation("Character_Down");}
    
    void animateUpLeft() {animator.startAnimation("Character_UpLeft");}
    
    void animateUpRight() {animator.startAnimation("Character_UpRight");}
    
    void animateDownLeft() {animator.startAnimation("Character_DownLeft");}
    
    void animateDownRight() {animator.startAnimation("Character_DownRight");}
    
    void animateStop(Vector2 lastdirection)
    {
        if(lastdirection.x < -0.1) {
            animator.startAnimation("Character_StandLeft");
        } else if(lastdirection.x > 0.1 ){
            animator.startAnimation("Character_StandRight");
        } else if (lastdirection.y < -0.1) {
            animator.startAnimation("Character_StandDown");
        } else if(lastdirection.y > 0.1 ){
            animator.startAnimation("Character_StandUp");
        }
        
    }
    
    void animateStandLeft() { animator.startAnimation("Character_StandLeft");}
    
    void animateStandRight() { animator.startAnimation("Character_StandRight");}
    
    void animateStandUp() { animator.startAnimation("Character_StandUp");}
    
    
    
    
}
