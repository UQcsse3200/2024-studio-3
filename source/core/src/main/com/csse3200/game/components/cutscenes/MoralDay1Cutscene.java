package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.components.maingame.TextDisplay;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

public class MoralDay1Cutscene extends Cutscene {
    public MoralDay1Cutscene() {
        super();
    }

    @Override
    protected void setupScenes() {
        createScene();
        createScene2();
    }

    private void createScene() {
        Array<String> sceneText = new Array<>();
        sceneText.add("Mafia Boss > Not bad, human. You made enough to pay rent.");
        sceneText.add("Mafia Boss > Maybe there's some hope for you.");
        sceneText.add("Mafia Boss > Here's an offer.");
        sceneText.add("Mafia Boss > You wash some of my dirty money through your restaurant");
        sceneText.add("Mafia Boss > and tomorrow you won’t need to stress about the rent too much.");
        sceneText.add("Mafia Boss > What do you say?");


        String mafiaImage = "images/Cutscenes/Character Artwork/rhino_sprite.png";
        Vector2 mafiaPosition = new Vector2(3, -1);
        float mafiaScale = 4.0f;

        String iconImage = "images/Cutscenes/moral_icons/laundering_ico.png";
        Vector2 iconPosition = new Vector2(-8, -2);
        float iconScale = 7.0f;


        // Add scenes with background images, animations, text, and duration
        Scene scene = new Scene("images/Cutscenes/Day2_Scene.png");
        scene.setImages(
                new String[]{mafiaImage, iconImage},
                new Vector2[] {mafiaPosition, iconPosition},
                new float[] {mafiaScale, iconScale}
        );

        scene.setSceneText(sceneText);
        scene.setDuration(3.0f);

        scenes.add(scene);
    }

    private void createScene2() {
        Array<String> sceneText = new Array<>();
        sceneText.add("Press 'y' for yes and 'no' for no");

        String mafiaImage = "images/Cutscenes/Character Artwork/rhino_sprite.png";
        Vector2 mafiaPosition = new Vector2(3, -1);
        float mafiaScale = 4.0f;

        String iconImage = "images/Cutscenes/moral_icons/laundering_ico.png";
        Vector2 iconPosition = new Vector2(-8, -2);
        float iconScale = 7.0f;

        String yesImage = "images/Cutscenes/moral_icons/yes_ico.png";
        Vector2 yesPosition = new Vector2(-10, -4);
        float yesScale = 11.0f;

        String noImage = "images/Cutscenes/moral_icons/no_ico.png";
        Vector2 noPosition = new Vector2(-1, -4);
        float noScale = 11.0f;

        // Add scenes with background images, animations, text, and duration
        Scene scene = new Scene("images/Cutscenes/Day2_Scene.png");
        scene.setImages(
                new String[]{mafiaImage, iconImage, yesImage, noImage},
                new Vector2[] {mafiaPosition, iconPosition, yesPosition, noPosition},
                new float[] {mafiaScale, iconScale, yesScale, noScale}
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
                "images/Cutscenes/moral_icons/laundering_ico.png",
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
