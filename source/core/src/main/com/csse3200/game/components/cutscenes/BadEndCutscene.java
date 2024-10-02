package com.csse3200.game.components.cutscenes;

import com.csse3200.game.components.cutscenes.scenes.AnimatedScene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

/**
 * The BadEndCutscene class plays the bad end cutscene when triggered at the end of the game
 */
public class BadEndCutscene extends Cutscene {

    public BadEndCutscene() {
        super();
        this.IsAnimatedScenes = true;
    }

    @Override
    protected void setupScenes() {
        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd.atlas",
                "bad_end", 20));

        //animatedScenes.add(new AnimatedScene(
        //        "images/stations/Servery_Animation/servery.atlas",
        //        "servery_idle", 20)); // servery_idle

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd.atlas",
                "bad_end2", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd.atlas",
                "bad_end3", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd.atlas",
                "bad_end4", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd.atlas",
                "bad_end5", 20));

        animatedScenes.add(new AnimatedScene(
                "images/Cutscenes/cutscene_badEnd.atlas",
                "bad_end6", 20));
    }

    @Override
    protected void loadAssets() {
        textures = new String[] {
                "images/Cutscenes/Beastly_Bistro_Background.png",
        };

        animations = new String[] {
                "images/Cutscenes/cutscene_badEnd.atlas",
                "images/stations/Servery_Animation/servery.atlas"
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
}
