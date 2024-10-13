package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

public class Day4Cutscene extends Cutscene {

    public Day4Cutscene() {
        super();
    }

    @Override
    protected void setupScenes() {
        createScene1();
    }

    private void createScene1() {
        // Scene 1: Mafia Boss warning about the riot
        Array<String> sceneText = new Array<>();
        sceneText.add("Mafia Boss > Heh, seems like the animals are done laughing at you.");
        sceneText.add("Mafia Boss > They’re worried, and I don’t blame them. A riot’s brewing outside.");
        sceneText.add("Mafia Boss > Keep calm and serve the customers, or you’ll have more than just a fire to put out today.");

        String mafiaBossImage = "images/Cutscenes/Character Artwork/rhino_sprite.png";
        Vector2 mafiaBossPosition = new Vector2(-8, -7f);
        float mafiaBossScale = 5.0f;

        String playerImage = "images/Cutscenes/Character Artwork/player_sprite_back_turned.png";
        Vector2 playerPosition = new Vector2(4, -7f);
        float playerScale = 5.0f;

        Scene scene = new Scene("images/Cutscenes/Day3_Scene.png");
        scene.setImages(
                new String[]{mafiaBossImage, playerImage},
                new Vector2[]{mafiaBossPosition, playerPosition},
                new float[]{mafiaBossScale, playerScale}
        );

        scene.setSceneText(sceneText);
        scene.setDuration(3.0f);

        scenes.add(scene);
    }

    @Override
    protected void loadAssets() {
        // Load the background and character images for the cutscene
        textures = new String[] {
                "images/Cutscenes/Day3_Scene.png",
        };

        animations = new String[] {};

        images = new String[] {
                "images/Cutscenes/Character Artwork/rhino_sprite.png",
                "images/Cutscenes/Character Artwork/player_sprite_back_turned.png"
        };

        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadTextures(images);
        resourceService.loadTextureAtlases(animations);
        resourceService.loadAll();
    }

    @Override
    public void createEntities() {
        // Entity creation logic specific to Day 4
        // For now, it remains simple and tied to assets
    }
}
