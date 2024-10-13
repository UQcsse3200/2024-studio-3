package com.csse3200.game.components.cutscenes;


import com.csse3200.game.components.cutscenes.scenes.AnimatedScene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

/**
 * The IntroCutscene class represents a specific cutscene that plays at the start of the game.
 * It defines the scenes, assets, and entities used during the intro cutscene.
 */
public class GoodEndCutscene extends Cutscene {


    public GoodEndCutscene() {
        super();
        this.IsAnimatedScenes = true;
    }

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

}
