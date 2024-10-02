package com.csse3200.game.components.cutscenes;

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
        cutsceneText.add("First bit of text");
        cutsceneText.add("Second bit of text");
        cutsceneText.add("Third bit of text");

        // Add scenes with background images, animations, text, and duration
        scenes.add(new Scene(
                "images/Cutscenes/good_end_0.png",
                new String[]{"images/player/Cook_Model32.png"},
                cutsceneText, 3.0f));

        scenes.add(new Scene(
                "images/Cutscenes/good_end_1.png",
                new String[]{"images/player/Cook_Model32.png"},
                cutsceneText, 4.0f));

        scenes.add(new Scene(
                "images/Cutscenes/good_end_2.png",
                new String[]{"images/player/Cook_Model32.png"},
                cutsceneText, 2.0f));

        scenes.add(new Scene(
                "images/Cutscenes/good_end_3.png",
                new String[]{"images/player/Cook_Model32.png"},
                cutsceneText, 4.0f));

    }

    /**
     * Loads the assets needed for the intro cutscene, including textures for backgrounds
     * and animations.
     */
    @Override
    protected void loadAssets() {
        // Load the background images for the cutscene
        textures = new String[] {
                "images/Cutscenes/Beastly_Bistro_Background.png",
                "images/Cutscenes/Graveyard_Scene.png"
        };

        // Load the animation images for the cutscene
        animations = new String[] {"images/player/Cook_Model32.png"};

        // Get the resource service to load assets
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(animations);
        resourceService.loadAll();  // Ensure all assets are loaded
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
