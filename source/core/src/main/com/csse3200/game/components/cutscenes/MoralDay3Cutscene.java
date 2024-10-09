package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

public class MoralDay3Cutscene extends Cutscene {
    public MoralDay3Cutscene() {
        super();
    }

    @Override
    protected void setupScenes() {
        createScene();
    }

    private void createScene() {
        Array<String> sceneText = new Array<>();
        sceneText.add("Mafia Boss > This is day 2 11.");
        sceneText.add("Mafia Boss > This is day 2 22.");
        sceneText.add("Mafia Boss > This is day 2 .");

        String mafiaImage = "images/Cutscenes/Character Artwork/rhino_sprite.png";
        Vector2 mafiaPosition = new Vector2(3, -1);
        float mafiaScale = 4.0f;

        String iconImage = "images/Cutscenes/moral_icons/gambling_ico.png";
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
