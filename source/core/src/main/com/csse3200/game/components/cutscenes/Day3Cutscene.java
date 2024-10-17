package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

public class Day3Cutscene extends Cutscene {

    private final String backgroundImage = "images/Cutscenes/Day3_Scene.png";

    String playerImage = "images/Cutscenes/Character Artwork/player_sprite_back_turned.png";

    public Day3Cutscene() {
        super();
    }

    @Override
    protected void setupScenes() {
        createScene1();
        createScene2();
    }

    private void createScene1() {
        // Scene 1: Mafia Boss speaking
        Array<String> sceneText = new Array<>();
        sceneText.add("Mafia Boss > Heard the health inspector gave you a hard time. Typical.");
        sceneText.add("Mafia Boss > If you mess up today, its five coins out of your pocket.");
        sceneText.add("Mafia Boss > Make those orders perfect or you'll be paying for it.");



        String mafiaBossImage = "images/Cutscenes/Character Artwork/rhino_sprite.png";
        Vector2 mafiaBossPosition = new Vector2(-8, -7f);
        float mafiaBossScale = 5.0f;

        Vector2 playerPosition = new Vector2(4, -7f);
        float playerScale = 5.0f;

        Scene scene = new Scene(backgroundImage);

        scene.setImages(
                new String[]{mafiaBossImage, playerImage},
                new Vector2[]{mafiaBossPosition, playerPosition},
                new float[]{mafiaBossScale, playerScale}
        );

        scene.setSceneText(sceneText);
        scene.setDuration(3.0f);

        scenes.add(scene);
    }


    private void createScene2() {
        // Scene 2: Health Inspector speaking
        Array<String> sceneText = new Array<>();
        sceneText.add("Health Inspector > Five meals, five perfect orders.");
        sceneText.add("Health Inspector > Any mistakes and you’ll pay for them.");
        sceneText.add("Health Inspector > You better get it right.");

        String healthInspectorImage = "images/Cutscenes/Character Artwork/panda_sprite.png";
        Vector2 healthInspectorPosition = new Vector2(-8, -7);
        float healthInspectorScale = 5.0f;

        Vector2 playerPosition = new Vector2(4, -7f);
        float playerScale = 5.0f;

        Scene scene = new Scene(backgroundImage);
        scene.setImages(
                new String[]{healthInspectorImage, playerImage},
                new Vector2[]{healthInspectorPosition, playerPosition},
                new float[]{healthInspectorScale, playerScale}
        );

        scene.setSceneText(sceneText);
        scene.setDuration(3.0f);

        scenes.add(scene);
    }

    @Override
    protected void loadAssets() {
        // Load the background and character images for the cutscene
        textures = new String[] {
                backgroundImage,
        };

        animations = new String[] {};

        images = new String[] {
                "images/Cutscenes/Character Artwork/rhino_sprite.png",
                "images/Cutscenes/Character Artwork/panda_sprite.png",
                playerImage
        };

        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadTextures(images);
        resourceService.loadTextureAtlases(animations);
        resourceService.loadAll();
    }

    @Override
    public void createEntities() {
        // Entity creation logic specific to Day 3
        // For now, it remains simple and tied to assets
    }
}
