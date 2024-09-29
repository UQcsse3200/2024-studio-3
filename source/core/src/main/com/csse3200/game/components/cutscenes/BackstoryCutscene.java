package com.csse3200.game.components.cutscenes;

import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

/**
 * The BackstoryCutscene class represents a specific cutscene that provides the player's backstory.
 */
public class BackstoryCutscene extends Cutscene {

    /**
     * Constructor for the BackstoryCutscene class.
     */
    public BackstoryCutscene() {
        super();
    }

    /**
     * Sets up the scenes for the background images, character sprites and text display
     * for each scene.
     */
    @Override
    protected void setupScenes() {
        // text to be displayed during the cutscene
        cutsceneText.add("In the year 2045, the world changed...");
        cutsceneText.add("Humans and animals were forced to coexist in a new order.");
        cutsceneText.add("Our hero, a chef, is one of the last remaining humans...");

        scenes.add(new Scene(
                "images/Cutscenes/Brooklyn_Bistro_Background.png",
                new String[]{"images/player/Cook_Model32.png"},
                cutsceneText, 4.0f));

        scenes.add(new Scene(
                "images/Cutscenes/Kitchen_Background.png",
                new String[]{"images/player/Cook_Model32.png"},
                cutsceneText, 5.0f));

        scenes.add(new Scene(
                "images/Cutscenes/Farm_Background.png",
                new String[]{"images/player/Cook_Model32.png"},
                cutsceneText, 3.0f));
    }

    /**
     * Loads the assets needed for the backstory cutscene, including textures for backgrounds
     * and animations.
     */
    @Override
    protected void loadAssets() {
        // Load the background images for the cutscene
        textures = new String[] {
                "images/Cutscenes/Brooklyn_Bistro_Background.png",
                "images/Cutscenes/Kitchen_Background.png",
                "images/Cutscenes/Farm_Background.png"
        };

        // Load the animation images for the cutscene (e.g., the hero character)
        animations = new String[] {"images/player/Cook_Model32.png"};

        // Get the resource service to load assets
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(animations);
        resourceService.loadAll();  // Ensure all assets are loaded
    }

    /**
     * Handles specific entity creation logic for the backstory cutscene.
     * Currently, there is no specific logic for creating entities.
     */
    @Override
    public void createEntities() {
        // none
    }
}

