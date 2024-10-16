package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

public class MoralDay4Cutscene extends Cutscene {
    public MoralDay4Cutscene() {
        super();
    }

    @Override
    protected void setupScenes() {
        createScene();
        createScene2();
    }

    private void createScene() {
        Array<String> sceneText = new Array<>();
        sceneText.add("Mafia Boss > You've impressed me, human. But stress gets to all of us.");

        sceneText.add("Racoon Dealer > Try some of my stuff. Best in the city.");

        sceneText.add("Mafia Boss > It'll help you work faster tomorrow. What do you have to lose?");

        sceneText.add("Mafia Boss > What do you say?");



        String mafiaImage = "images/Cutscenes/Character Artwork/rhino_sprite.png";
        Vector2 mafiaPosition = new Vector2(3, -1);
        float mafiaScale = 4.0f;

        String racoonImage = "images/Cutscenes/Character Artwork/racoon.png";
        Vector2 racoonPosition = new Vector2(-3, -1);
        float racoonScale = 5.5f;


        String iconImage = "images/Cutscenes/moral_icons/drug_ico.png";
        Vector2 iconPosition = new Vector2(-9, -2);
        float iconScale = 6.0f;

        // Add scenes with background images, animations, text, and duration
        Scene scene = new Scene("images/Cutscenes/Day2_Scene.png");
        scene.setImages(
                new String[]{mafiaImage, racoonImage, iconImage},
                new Vector2[] {mafiaPosition, racoonPosition, iconPosition},
                new float[] {mafiaScale, racoonScale, iconScale}
        );

        scene.setSceneText(sceneText);
        scene.setDuration(3.0f);

        scenes.add(scene);
    }

    private void createScene2() {
        Array<String> sceneText = new Array<>();

        sceneText.add("Press 'y' for yes and 'n' for no");


        String mafiaImage = "images/Cutscenes/Character Artwork/rhino_sprite.png";
        Vector2 mafiaPosition = new Vector2(3, -1);
        float mafiaScale = 4.0f;

        String racoonImage = "images/Cutscenes/Character Artwork/racoon.png";
        Vector2 racoonPosition = new Vector2(-3, -1);
        float racoonScale = 5.5f;


        String iconImage = "images/Cutscenes/moral_icons/drug_ico.png";
        Vector2 iconPosition = new Vector2(-9, -2);
        float iconScale = 6.0f;


        String yesImage = "images/Cutscenes/moral_icons/yes_ico.png";
        Vector2 yesPosition = new Vector2(-10, -4);
        float yesScale = 11.0f;

        String noImage = "images/Cutscenes/moral_icons/no_ico.png";
        Vector2 noPosition = new Vector2(-1, -4);
        float noScale = 11.0f;

        // Add scenes with background images, animations, text, and duration
        Scene scene = new Scene("images/Cutscenes/Day2_Scene.png");
        scene.setImages(
                new String[]{mafiaImage, racoonImage, iconImage, yesImage, noImage},
                new Vector2[] {mafiaPosition, racoonPosition, iconPosition, yesPosition, noPosition},
                new float[] {mafiaScale, racoonScale, iconScale, yesScale, noScale}
        );

        scene.setSceneText(sceneText);
        scene.setDuration(3.0f);

        scenes.add(scene);
    }

    @Override
    protected void loadAssets() {
        // Load the background images for the cutscene
        textures = new String[] {
                "images/Cutscenes/Day2_Scene.png",
        };

        animations = new String[] {};

        images = new String[] {
                "images/Cutscenes/Character Artwork/rhino_sprite.png",
                "images/Cutscenes/Character Artwork/racoon.png",
                "images/Cutscenes/moral_icons/drug_ico.png",
                "images/Cutscenes/moral_icons/yes_ico.png",
                "images/Cutscenes/moral_icons/no_ico.png"
        };

        // Get the resource service to load assets
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadTextures(images);
        resourceService.loadTextureAtlases(animations);
        resourceService.loadAll();
    }

    @Override
    public void createEntities() {
        // Any specific entity creation logic for the intro cutscene
    }
}