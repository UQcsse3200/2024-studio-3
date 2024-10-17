package com.csse3200.game.components.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.Component;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.services.ServiceLocator;

public class PlayerAnimationController extends Component {
    AnimationRenderComponent animator;
    private static final String CHARACTER_LEFT = "Character_Left";
    private static final String CHARACTER_RIGHT = "Character_Right";
    private static final String CHARACTER_UP = "Character_Up";
    private static final String CHARACTER_DOWN = "Character_Down";
    private static final String CHARACTER_UP_LEFT = "Character_UpLeft";
    private static final String CHARACTER_UP_RIGHT = "Character_UpRight";
    private static final String CHARACTER_DOWN_LEFT = "Character_DownLeft";
    private static final String CHARACTER_DOWN_RIGHT = "Character_DownRight";
    private static final String CHARACTER_STAND_LEFT = "Character_StandLeft";
    private static final String CHARACTER_STAND_RIGHT = "Character_StandRight";
    private static final String CHARACTER_STAND_DOWN = "Character_StandDown";
    private static final String CHARACTER_STAND_UP = "Character_StandUp";


    /**
     * Creates the player animation controller
     * Adds listeners for walk cycle animations
     * Adds listeners for ingredient animations
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        //Add walk cycle animation listeners
        entity.getEvents().addListener("walkLeft", this::animateLeft);
        entity.getEvents().addListener("walkRight", this::animateRight);
        entity.getEvents().addListener("walkUp", this::animateUp);
        entity.getEvents().addListener("walkDown", this::animateDown);
        entity.getEvents().addListener("walkUpLeft", this::animateUpLeft);
        entity.getEvents().addListener("walkUpRight", this::animateUpRight);
        entity.getEvents().addListener("walkDownLeft", this::animateDownLeft);
        entity.getEvents().addListener("walkDownRight", this::animateDownRight);
        entity.getEvents().addListener("walkStopAnimation", this::animateStop);


        //Add animation update listeners (for ingredients)
        entity.getEvents().addListener("updateAnimationEmptyInventory",
                this::updateAnimationEmptyInventory);
        entity.getEvents().addListener("updateAnimationRawAcai", this::updateAnimationRawAcai);
        entity.getEvents().addListener("updateAnimationChoppedAcai",
                this::updateAnimationChoppedAcai);
        entity.getEvents().addListener("updateAnimationRawBeef", this::updateAnimationRawBeef);
        entity.getEvents().addListener("updateAnimationCookedBeef", this::updateAnimationCookedBeef);
        entity.getEvents().addListener("updateAnimationBurntBeef", this::updateAnimationBurntBeef);
        entity.getEvents().addListener("updateAnimationRawBanana", this::updateAnimationRawBanana);
        entity.getEvents().addListener("updateAnimationChoppedBanana",
                this::updateAnimationChoppedBanana);
        entity.getEvents().addListener("updateAnimationRawLettuce", this::updateAnimationRawLettuce);
        entity.getEvents().addListener("updateAnimationChoppedLettuce",
                this::updateAnimationChoppedLettuce);
        entity.getEvents().addListener("updateAnimationRawCucumber", this::updateAnimationRawCucumber);
        entity.getEvents().addListener("updateAnimationChoppedLettuce", this::updateAnimationChoppedLettuce);
        entity.getEvents().addListener("updateAnimationRawCucumber", this::updateAnimationRawCucumber);
        entity.getEvents().addListener("updateAnimationChoppedCucumber",
                this::updateAnimationChoppedCucumber);
        entity.getEvents().addListener("updateAnimationRawTomato", this::updateAnimationRawTomato);
        entity.getEvents().addListener("updateAnimationChoppedTomato",
                this::updateAnimationChoppedTomato);
        entity.getEvents().addListener("updateAnimationRawStrawberry", this::updateAnimationRawStrawberry);
        entity.getEvents().addListener("updateAnimationChoppedStrawberry",
                this::updateAnimationChoppedStrawberry);
        entity.getEvents().addListener("updateAnimationRawChocolate", this::updateAnimationRawChocolate);
        entity.getEvents().addListener("updateAnimationChoppedChocolate",
                this::updateAnimationChoppedChocolate);
        entity.getEvents().addListener("updateAnimationRawFish",
                this::updateAnimationRawFish);
        entity.getEvents().addListener("updateAnimationCookedFish", this::updateAnimationCookedFish);

        //Add animation update listeners (for meals)
        entity.getEvents().addListener("updateAnimationAcaiBowl", this::updateAnimationAcaiBowl);
        entity.getEvents().addListener("updateAnimationBananaSplit", this::updateAnimationBananaSplit);
        entity.getEvents().addListener("updateAnimationFruitSalad", this::updateAnimationFruitSalad);
        entity.getEvents().addListener("updateAnimationSalad", this::updateAnimationSalad);
        entity.getEvents().addListener("updateAnimationSteak", this::updateAnimationSteak);

        entity.getEvents().addListener("updateAnimationPlate", this::updateAnimationPlate);
        entity.getEvents().addListener("updateAnimationDirtyPlate",
                this::updateAnimationDirtyPlate);
        entity.getEvents().addListener("updateAnimationFireExtinguisher",
                this::updateAnimationFireExtinguisher);
    }
    
    void animateLeft() {animator.startAnimation(CHARACTER_LEFT);}
    
    void animateRight() {animator.startAnimation(CHARACTER_RIGHT);}
    
    void animateUp() {animator.startAnimation(CHARACTER_UP);}
    
    void animateDown() {animator.startAnimation(CHARACTER_DOWN);}
    
    void animateUpLeft() {animator.startAnimation(CHARACTER_UP_LEFT);}
    
    void animateUpRight() {animator.startAnimation(CHARACTER_UP_RIGHT);}
    
    void animateDownLeft() {animator.startAnimation(CHARACTER_DOWN_LEFT);}
    
    void animateDownRight() {animator.startAnimation(CHARACTER_DOWN_RIGHT);}
    
    void animateStop(Vector2 lastDirection)
    {
        if (lastDirection.x < -0.1) {
            animator.startAnimation(CHARACTER_STAND_LEFT);
        } else if (lastDirection.x > 0.1 ){
            animator.startAnimation(CHARACTER_STAND_RIGHT);
        } else if (lastDirection.y < -0.1) {
            animator.startAnimation(CHARACTER_STAND_DOWN);
        } else if (lastDirection.y > 0.1 ){
            animator.startAnimation(CHARACTER_STAND_UP);
        }
    }
    
    void animateStandLeft() {
        animator.startAnimation(CHARACTER_STAND_LEFT);
    }
    
    void animateStandRight() { animator.startAnimation(CHARACTER_STAND_RIGHT);}
    
    void animateStandUp() { animator.startAnimation(CHARACTER_STAND_UP);}

    //Update animation to hold ingredients
    void updateAnimationEmptyInventory(){updateAnimation("player.atlas");}

    void updateAnimationRawAcai(){updateAnimation("rawAcai.atlas");}
    void updateAnimationChoppedAcai(){updateAnimation("choppedAcai.atlas");}
    void updateAnimationRawBeef(){updateAnimation("rawBeef.atlas");}
    void updateAnimationCookedBeef(){updateAnimation("cookedBeef.atlas");}
    void updateAnimationBurntBeef(){updateAnimation("burntBeef.atlas");}
    void updateAnimationRawBanana(){updateAnimation("rawBanana.atlas");}
    void updateAnimationChoppedBanana(){updateAnimation("choppedBanana.atlas");}
    void updateAnimationRawLettuce(){updateAnimation("rawLettuce.atlas");}
    void updateAnimationChoppedLettuce(){updateAnimation("choppedLettuce.atlas");}
    void updateAnimationRawCucumber(){updateAnimation("rawCucumber.atlas");}
    void updateAnimationChoppedCucumber(){updateAnimation("choppedCucumber.atlas");}
    void updateAnimationRawTomato(){updateAnimation("rawTomato.atlas");}
    void updateAnimationChoppedTomato(){updateAnimation("choppedTomato.atlas");}
    void updateAnimationRawStrawberry(){updateAnimation("rawStrawberry.atlas");}
    void updateAnimationChoppedStrawberry(){updateAnimation("choppedStrawberry.atlas");}
    void updateAnimationRawChocolate(){updateAnimation("rawChocolate.atlas");}
    void updateAnimationChoppedChocolate(){updateAnimation("choppedChocolate.atlas");}
    void updateAnimationRawFish(){updateAnimation("rawFish.atlas");}
    void updateAnimationCookedFish(){updateAnimation("cookedFish.atlas");}

    //Update animation to hold meals
    void updateAnimationAcaiBowl(){updateAnimation("acaiBowl.atlas");}
    void updateAnimationBananaSplit(){updateAnimation("bananaSplit.atlas");}
    void updateAnimationFruitSalad(){updateAnimation("fruitSalad.atlas");}
    void updateAnimationSalad(){updateAnimation("salad.atlas");}
    void updateAnimationSteak(){updateAnimation("steak.atlas");}

    void updateAnimationPlate(){updateAnimation("playerPlate.atlas");}
    void updateAnimationDirtyPlate(){updateAnimation("playerDirtyPlate.atlas");}
    void updateAnimationFireExtinguisher(){updateAnimation("playerFireExtinguisher.atlas");}

    /**
     * Updates player animation to use given atlas, removes and reloads walk cycle
     * @param atlasPath new atlas to update player animation with
     */
    public void updateAnimation(String atlasPath) {
        // Get the current animation going and stop it
        String currentAnimation = animator.getCurrentAnimation();
        animator.stopAnimation(); // Will do nothing if no animation

        //Removes all animations
        animator.removeAnimation(CHARACTER_STAND_DOWN);
        animator.removeAnimation(CHARACTER_STAND_UP);
        animator.removeAnimation(CHARACTER_STAND_LEFT);
        animator.removeAnimation(CHARACTER_STAND_RIGHT);

        animator.removeAnimation(CHARACTER_DOWN_LEFT);
        animator.removeAnimation(CHARACTER_UP_RIGHT);
        animator.removeAnimation(CHARACTER_UP);
        animator.removeAnimation(CHARACTER_LEFT);
        animator.removeAnimation(CHARACTER_RIGHT);
        animator.removeAnimation(CHARACTER_DOWN);
        animator.removeAnimation(CHARACTER_DOWN_RIGHT);
        animator.removeAnimation(CHARACTER_UP_LEFT);

        //Updates atlas
        animator.updateAtlas(ServiceLocator.getResourceService().getAsset(
                "images/player/" + atlasPath, TextureAtlas.class));

        //Adds new animations
        animator.addAnimation(CHARACTER_STAND_DOWN, 0.2f);
        animator.addAnimation(CHARACTER_STAND_UP, 0.2f);
        animator.addAnimation(CHARACTER_STAND_LEFT, 0.2f);
        animator.addAnimation(CHARACTER_STAND_RIGHT, 0.2f);

        animator.addAnimation(CHARACTER_DOWN_LEFT, 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation(CHARACTER_UP_RIGHT, 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation(CHARACTER_UP, 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation(CHARACTER_LEFT, 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation(CHARACTER_DOWN_RIGHT, 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation(CHARACTER_DOWN, 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation(CHARACTER_UP_LEFT, 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation(CHARACTER_RIGHT, 0.2f, Animation.PlayMode.LOOP);

        // Now restart the animation that was going
        animator.startAnimation(currentAnimation); // Will do nothing if no animation
    }
    
}
