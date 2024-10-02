package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

/**
 * Specific cutscene class handling the backstory of the game.
 */
public class BackstoryCutscene extends Cutscene {
    /**
     * Constructor for BackstoryCutscene.
     */
    public BackstoryCutscene() {
        super();
    }

    /**
     * Sets up the scenes for the backstory cutscene, including background images,
     * animations, and the corresponding text for each scene.
     */
    @Override
    protected void setupScenes() {
        // Add cutscene text
        cutsceneText.add("In the year 2045, the world changed...");
        cutsceneText.add("Humans and animals were forced to coexist in a new order.");
        cutsceneText.add("Our hero, a chef, is one of the last remaining humans...");

        // Add scenes with background images, text, and duration
        scenes.add(new Scene(
                "images/Cutscenes/Brooklyn_Bistro_Background.png",
                null,  // No animations for this scene
                new Array<>(new String[]{"In the year 2045, the world changed..."}),
                5.0f
        ));
        scenes.add(new Scene(
                "images/Cutscenes/Kitchen_Background.png",
                null,  // No animations for this scene
                new Array<>(new String[]{"Humans and animals were forced to coexist in a new order."}),
                4.0f
        ));
        scenes.add(new Scene(
                "images/Cutscenes/Food_Critic_Background.png",
                null,  // No animations for this scene
                new Array<>(new String[]{"Humans and animals were forced to coexist in a new order."}),
                4.0f
        ));
        scenes.add(new Scene(
                "images/Cutscenes/Food_Critic_Background.png",
                null,  // No animations for this scene
                new Array<>(new String[]{"Humans and animals were forced to coexist in a new order."}),
                4.0f
        ));
        scenes.add(new Scene(
                "images/Cutscenes/Kitchen_Background.png",
                null,  // No animations for this scene
                new Array<>(new String[]{"Humans and animals were forced to coexist in a new order."}),
                4.0f
        ));
        scenes.add(new Scene(
                "images/Cutscenes/Farm_Background.png",
                null,  // No animations for this scene
                new Array<>(new String[]{"Our hero, a chef, is one of the last remaining humans..."}),
                3.0f
        ));
    }

    /**
     * Loads the assets needed for the backstory cutscene, including textures for backgrounds.
     */
//    @Override
//    protected void loadAssets() {
//        // Load the background images for the cutscene
//        textures = new String[] {
//                "images/Cutscenes/scene1_background.png",
//                "images/Cutscenes/scene2_background.png",
//                "images/Cutscenes/scene3_background.png"
//        };
//
//        // Get the resource service to load assets
//        ResourceService resourceService = ServiceLocator.getResourceService();
//        resourceService.loadTextures(textures);
//        resourceService.loadAll();  // Ensure all assets are loaded
//    }
    @Override
    protected void loadAssets() {
        // Load assets for the backstory cutscene
        for (Scene scene : scenes) {
            ServiceLocator.getResourceService().loadTextures(new String[]{scene.getBackgroundImagePath()});
        }
    }

    /**
     * Handles specific entity creation logic for the backstory cutscene.
     * Currently, there is no specific logic for creating entities.
     */
    @Override
    public void createEntities() {
        // Any specific entity creation logic for the backstory cutscene
    }

    /**
     * Returns the cutscene text that will be displayed during the cutscene.
     *
     * @return Array<String> of cutscene text.
     */
    public Array<String> getCutsceneText() {
        return cutsceneText;
    }
}
