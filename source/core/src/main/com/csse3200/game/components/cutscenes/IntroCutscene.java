package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

/**
 * The IntroCutscene class represents a specific cutscene that plays at the start of the game.
 * It defines the scenes, assets, and entities used during the intro cutscene.
 */
public class IntroCutscene extends Cutscene {
    private final String acaiBowl = "images/meals/acai_bowl.png";
    /**
     * Constructor for the IntroCutscene class.
     */
    public IntroCutscene() {
        super();
    }

    /**
     * Sets up the scenes for the intro cutscene, including background images,
     * animation images, and the corresponding text for each scene.
     */
    @Override
    protected void setupScenes() {
        cutsceneText.add("Hello This is an Example Text");
        cutsceneText.add("Wow, we can move forward, I wonder what else this can do :)");
        cutsceneText.add("This is a quick check to see if truncation is working correctly or whether it does not " +
                "show what it is meant to show or not.");

        // Add scenes with background images, animations, text, and duration

        Scene scene1 = new Scene("images/Cutscenes/Beastly_Bistro_Background.png");
        scene1.setImages(
                new String[]{acaiBowl},
                new Vector2[] {new Vector2(4, 2)},
                new float[] {1.0f}
        );

        scene1.setSceneText(cutsceneText);
        scene1.setDuration(3.0f);

        scenes.add(scene1);

        Scene scene2 = new Scene("images/Cutscenes/Graveyard_Scene.png");
        scene2.setImages(
                new String[]{acaiBowl},
                new Vector2[] {new Vector2(2, 2)},
                new float[] {4.0f}
        );
        Array<String> scene2Text = new Array<>();
        scene2Text.add("This is the second scene");
        scene2Text.add("We made it");
        scene2Text.add("LETS GO!!!!");
        scene2.setSceneText(scene2Text);
        scene2.setDuration(3.0f);

        scenes.add(scene2);
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

        images = new String[] {acaiBowl};

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
