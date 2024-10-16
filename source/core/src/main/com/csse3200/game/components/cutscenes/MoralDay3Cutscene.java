package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

public class MoralDay3Cutscene extends Cutscene {
    private static final String MAFIA_IMAGE_PATH = "images/Cutscenes/Character Artwork/rhino_sprite.png";
    private static final String ICON_IMAGE_PATH = "images/Cutscenes/moral_icons/kill_ico.png";
    private static final String DAY_2_SCENE_PATH = "images/Cutscenes/Day2_Scene.png";
    public MoralDay3Cutscene() {
        super();
    }

    @Override
    protected void setupScenes() {
        createScene();
        createScene2();
    }

    private void createScene() {
        Array<String> sceneText = new Array<>();
        sceneText.add("Mafia Boss > I don't like inspectors poking around my business.");
        sceneText.add("Mafia Boss > We'll get rid of him tomorrow.");
        sceneText.add("Mafia Boss > Poison his food, and the problem's solved.");
        sceneText.add("Mafia Boss > Take care of this, and I'll lower your rent.");
        sceneText.add("Mafia Boss > You in?");


        Vector2 mafiaPosition = new Vector2(3, -1);
        float mafiaScale = 4.0f;

        Vector2 iconPosition = new Vector2(-8, -2);
        float iconScale = 7.0f;

        // Add scenes with background images, animations, text, and duration
        Scene scene = new Scene(DAY_2_SCENE_PATH);
        scene.setImages(
                new String[]{MAFIA_IMAGE_PATH, ICON_IMAGE_PATH},
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

        Vector2 mafiaPosition = new Vector2(3, -1);
        float mafiaScale = 4.0f;

        Vector2 iconPosition = new Vector2(-8, -2);
        float iconScale = 7.0f;

        String yesImage = "images/Cutscenes/moral_icons/yes_ico.png";
        Vector2 yesPosition = new Vector2(-10, -4);
        float yesScale = 11.0f;

        String noImage = "images/Cutscenes/moral_icons/no_ico.png";
        Vector2 noPosition = new Vector2(-1, -4);
        float noScale = 11.0f;

        // Add scenes with background images, animations, text, and duration
        Scene scene = new Scene(DAY_2_SCENE_PATH);
        scene.setImages(
                new String[]{MAFIA_IMAGE_PATH, ICON_IMAGE_PATH, yesImage, noImage},
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
                DAY_2_SCENE_PATH,
        };

        animations = new String[] {};

        images = new String[] {
                MAFIA_IMAGE_PATH,
                ICON_IMAGE_PATH,
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
