package com.csse3200.game.components.cutscenes;

import com.csse3200.game.components.cutscenes.scenes.AnimatedScene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

import java.util.List;

/**
 * The BadEndCutscene class plays the bad end cutscene when triggered at the end of the game
 */
public class BadEndCutscene extends Cutscene {

    /**
     * Constructor for the BadEndCutscene class.
     */
    public BadEndCutscene() {
        super();
        this.isAnimatedScenes = true;
    }

    /**
     * Sets up the scenes for the bad end cutscene, in particular,
     * the animation images for each scene.
     */
    @Override
    protected void setupScenes() {
        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd.atlas",
                "bad_end", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd2.atlas",
                "bad_end2", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd3.atlas",
                "bad_end3", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd4.atlas",
                "bad_end4", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd5.atlas",
                "bad_end5", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd6.atlas",
                "bad_end6", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd7.atlas",
                "bad_end7", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd8.atlas",
                "bad_end8", 20));

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
