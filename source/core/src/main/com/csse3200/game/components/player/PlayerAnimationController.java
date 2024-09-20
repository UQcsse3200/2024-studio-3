package com.csse3200.game.components.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.Component;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.services.ServiceLocator;

public class PlayerAnimationController extends Component {
    AnimationRenderComponent animator;

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
    
    void animateStandLeft() {
        animator.startAnimation("Character_StandLeft");
    }
    
    void animateStandRight() { animator.startAnimation("Character_StandRight");}
    
    void animateStandUp() { animator.startAnimation("Character_StandUp");}

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
        //Removes all animations
        animator.removeAnimation("Character_StandDown");
        animator.removeAnimation("Character_StandUp");
        animator.removeAnimation("Character_StandLeft");
        animator.removeAnimation("Character_StandRight");

        animator.removeAnimation("Character_DownLeft");
        animator.removeAnimation("Character_UpRight");
        animator.removeAnimation("Character_Up");
        animator.removeAnimation("Character_Left");
        animator.removeAnimation("Character_Right");
        animator.removeAnimation("Character_Down");
        animator.removeAnimation("Character_DownRight");
        animator.removeAnimation("Character_UpLeft");


        //Updates atlas
        animator.updateAtlas(ServiceLocator.getResourceService().getAsset(
                "images/player/" + atlasPath, TextureAtlas.class));


        //Adds new animations
        animator.addAnimation("Character_StandDown", 0.2f);
        animator.addAnimation("Character_StandUp", 0.2f);
        animator.addAnimation("Character_StandLeft", 0.2f);
        animator.addAnimation("Character_StandRight", 0.2f);

        animator.addAnimation("Character_DownLeft", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("Character_UpRight", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("Character_Up", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("Character_Left", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("Character_DownRight", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("Character_Down", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("Character_UpLeft", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("Character_Right", 0.2f, Animation.PlayMode.LOOP);
    }
    
}
