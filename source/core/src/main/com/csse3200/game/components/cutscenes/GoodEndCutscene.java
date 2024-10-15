package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

/**
 * The IntroCutscene class represents a specific cutscene that plays at the start of the game.
 * It defines the scenes, assets, and entities used during the intro cutscene.
 */
public class GoodEndCutscene extends Cutscene {


    /**
     * Constructor for the GoodEndCutscene class.
     */
    public GoodEndCutscene() {
        super();
    }

    /**
     * Sets up the scenes for the intro cutscene, including background images,
     * animation images, and the corresponding text for each scene.
     */
    @Override
    protected void setupScenes() {
        // Add text to be displayed during the cutscene
        Array<String> scene1Text = new Array<>();
        scene1Text.add("\"Get out here\"");

        Array<String> scene2Text = new Array<>();
        scene2Text.add("\"I gotta admit... I'm kinda impressed\"");

        Array<String> scene3Text = new Array<>();
        scene3Text.add("\"Maybe you humans aren't so bad \n" +
                "after all...\"");

        Array<String> scene4Text = new Array<>();
        scene4Text.add("\"I'll let you take care of this place \n" +
                "in peace. Keep it tidy ya?\"");

        Array<String> scene5Text = new Array<>();
        scene5Text.add("\"I'll see you around...\"");

        // Add scenes with background images, animations, text, and duration

        Scene scene1 = new Scene("images/Cutscenes/good_end_0.png");
        scene1.setSceneText(scene1Text);
        scene1.setDuration(3.0f);

        scenes.add(scene1);

        Scene scene2 = new Scene("images/Cutscenes/good_end_1.png");
        scene2.setSceneText(scene2Text);
        scene2.setDuration(3.0f);

        scenes.add(scene2);

        Scene scene3 = new Scene("images/Cutscenes/good_end_2.png");
        scene3.setSceneText(scene3Text);
        scene3.setDuration(3.0f);

        scenes.add(scene3);

        Scene scene4 = new Scene("images/Cutscenes/good_end_2.png");
        scene4.setSceneText(scene4Text);
        scene4.setDuration(3.0f);

        scenes.add(scene4);

        Scene scene5 = new Scene("images/Cutscenes/good_end_3.png");
        scene5.setSceneText(scene5Text);
        scene5.setDuration(3.0f);

        scenes.add(scene5);
    }

    /**
     * Loads the assets needed for the intro cutscene, including textures for backgrounds
     * and animations.
     */
    @Override
    protected void loadAssets() {
        // Load the background images for the cutscene
        // Load the background images for the cutscene
        textures = new String[] {
                "images/Cutscenes/Beastly_Bistro_Background.png",
                "images/Cutscenes/Graveyard_Scene.png",
                "images/Cutscenes/good_end_0.png",
                "images/Cutscenes/good_end_1.png",
                "images/Cutscenes/good_end_2.png",
                "images/Cutscenes/good_end_3.png"
        };

        // Load the animation images for the cutscene
        animations = new String[] {"images/player/Cook_Model32.png"};

        images = new String[] {"images/meals/acai_bowl.png"};

        // Get the resource service to load assets
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadTextures(images);
        resourceService.loadTextureAtlases(animations);
        resourceService.loadAll();
    }

    /**
     * Handles specific entity creation logic for the intro cutscene.
     * Currently, there is no specific logic for creating entities.
     */
    @Override
    public void createEntities() {
        // Any specific entity creation logic for the intro cutscene
    }
}
