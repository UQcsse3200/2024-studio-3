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
        //        "servery_idle", 20));
    }

    @Override
    protected void loadAssets() {
        // redundant doesn't do anything

        animations = new String[] {
                "images/Cutscenes/cutscene_badEnd.atlas",
                "images/stations/Servery_Animation/servery.atlas"
        };
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextureAtlases(animations);
        resourceService.loadAll();
    }

    @Override
    public void createEntities() {
        // redundant doesn't do anything
    }
}
