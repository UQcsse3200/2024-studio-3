package com.csse3200.game.components.cutscenes;


import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

/**
 * The IntroCutscene class represents a specific cutscene that plays at the start of the game.
 * It defines the scenes, assets, and entities used during the intro cutscene.
 */
public class LoseCutscene extends Cutscene {


    /**
     * Constructor for the GoodEndCutscene class.
     */
    public LoseCutscene() {
        super();
    }

    /**
     * Sets up the scenes for the intro cutscene, including background images,
     * animation images, and the corresponding text for each scene.
     */
    @Override
    protected void setupScenes() {
        // Add text to be displayed during the cutscene
        cutsceneText.add("\"Its quite a shame...\"");
        cutsceneText.add("\"That I didn't kill him earlier.\"");


        // Add scenes with background images, animations, text, and duration

        Scene scene1 = new Scene("images/Cutscenes/Graveyard_Scene.png");

        scene1.setSceneText(cutsceneText);
        scene1.setDuration(3.0f);

        scenes.add(scene1);


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
                "images/Cutscenes/Graveyard_Scene.png"
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


