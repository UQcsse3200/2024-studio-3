package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

public class Day2Cutscene extends Cutscene{
    private final String reporterImage = "images/Cutscenes/Character Artwork/reporter_sprite.png";
    private final String playerImage = "images/Cutscenes/Character Artwork/player_sprite_back_turned.png";
    private final String day2Background = "images/Cutscenes/Day2_Scene.png";
    /**
     * Constructor for the IntroCutscene class.
     */
    public Day2Cutscene() {
        super();
    }

    /**
     * Sets up the scenes for the intro cutscene, including background images,
     * animation images, and the corresponding text for each scene.
     */
    @Override
    protected void setupScenes() {
        createScene1();
        createScene2();
    }

    private void createScene2() {
        Array<String> sceneText = new Array<>();
        sceneText.add("You > A... reverse Zoo? Im sorry, what?");
        sceneText.add("Reporter > Thats right! Animals from all over are dying to see what this circus act of a" +
                "restaurant is all about.");
        sceneText.add("Reporter > You will be seeing some familiar faces, and some exotic ones too.");
        sceneText.add("Reporter > They are all very curious");
        sceneText.add("Reporter > Dont be surprised if you see a few more animals than your first day");
        // Add scenes with background images, animations, text, and duration

        Vector2 reporterPosition = new Vector2(-5, 1);
        float reporterScale = 4.0f;

        Vector2 playerPosition = new Vector2(4, -5);
        float playerScale = 4.0f;

        String exclamationMark = "images/Cutscenes/ExclamationMark.png";
        Vector2 exclamationPosition = new Vector2(5.5f, 2);
        float exclamationScale = 0.5f;

        Scene scene = new Scene(day2Background);
        scene.setImages(
                new String[]{reporterImage, playerImage, exclamationMark},
                new Vector2[] {reporterPosition, playerPosition, exclamationPosition},
                new float[] {reporterScale, playerScale, exclamationScale}
        );

        scene.setSceneText(sceneText);
        scene.setDuration(3.0f);

        scenes.add(scene);
    }

    private void createScene1() {
        Array<String> sceneText = new Array<>();
        sceneText.add("Reporter > Good morning! Surprise, surprise! I hope you're ready. ");
        sceneText.add("Reporter > The whole animal kingdom has heard about your reverse zoo of a restaurant.");
        sceneText.add("Reporter > A human running a restaurant? Its the story of the century.");

        Vector2 reporterPosition = new Vector2(-5, 1);
        float reporterScale = 4.0f;

        Vector2 playerPosition = new Vector2(4, -5);
        float playerScale = 4.0f;

        // Add scenes with background images, animations, text, and duration
        Scene scene = new Scene(day2Background);
        scene.setImages(
                new String[]{reporterImage, playerImage},
                new Vector2[] {reporterPosition, playerPosition},
                new float[] {reporterScale, playerScale}
        );

        scene.setSceneText(sceneText);
        scene.setDuration(3.0f);

        scenes.add(scene);
    }

    /**
     * Loads the assets needed for the intro cutscene, including textures for backgrounds
     * and animations.
     */
    @Override
    protected void loadAssets() {
        // Load the background images for the cutscene
        textures = new String[] {
                "images/Cutscenes/Day2_Scene.png",
        };

        animations = new String[] {};

        images = new String[] {
                reporterImage,
                "images/Cutscenes/Character Artwork/player_sprite_back_turned.png",
                "images/Cutscenes/ExclamationMark.png"};

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