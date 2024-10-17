package com.csse3200.game.components.cutscenes;

import com.csse3200.game.components.cutscenes.scenes.AnimatedScene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

import java.util.List;

/**
 * The GoodEndCutscene class plays the good end cutscene when triggered at the end of the game.
 */
public class GoodEndCutscene extends Cutscene {

    /**
     * Constructor for the GoodEndCutscene class.
     */
    public GoodEndCutscene() {
        super();
        this.isAnimatedScenes = true;
    }

    /**
     * Sets up the scenes for the good end cutscene, in particular,
     * the animation images for each scene.
     */
    @Override
    protected void setupScenes() {
        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_goodEnd1.atlas",
                "good_end1", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_goodEnd2.atlas",
                "good_end2", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_goodEnd3.atlas",
                "good_end3", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_goodEnd4.atlas",
                "good_end4", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_goodEnd5.atlas",
                "good_end5", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_goodEnd6.atlas",
                "good_end6", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_goodEnd7.atlas",
                "good_end7", 20));


    }

    /**
     * Loads the assets needed for the cutscene, including textures for backgrounds
     * and animations.
     */
    @Override
    protected void loadAssets() {
        textures = new String[] {
                "images/Cutscenes/Beastly_Bistro_Background.png",
        };

        animations = new String[] {

        };
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(animations);
        resourceService.loadAll();
    }

    @Override
    public void createEntities() {
        // redundant doesn't do anything
    }

    /**
     * Get the list of animated scenes
     * @return List of animated scenes
     */
    public List<AnimatedScene> getAnimatedScenes() {
        return animatedScenes;
    }

}
