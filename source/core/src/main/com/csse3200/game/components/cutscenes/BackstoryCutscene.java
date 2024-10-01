//package com.csse3200.game.components.cutscenes;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.csse3200.game.GdxGame;
//import com.csse3200.game.services.ServiceLocator;
//import com.csse3200.game.components.cutscenes.scenes.Scene;
//
//public class BackstoryCutscene extends Cutscene {
//    private BackstoryCutsceneDisplay cutsceneDisplay;
//
//    public BackstoryCutscene() {
//        super();
//        this.cutsceneDisplay = new BackstoryCutsceneDisplay(ServiceLocator.getGdxGame());
//    }
//
//    @Override
//    protected void setupScenes() {
//        scenes.add(new Scene("images/Cutscenes/scene1_background.png", new String[]{"Text for scene 1"}, 5.0f));
//        scenes.add(new Scene("images/Cutscenes/scene2_background.png", new String[]{"Text for scene 2"}, 4.0f));
//        scenes.add(new Scene("images/Cutscenes/scene3_background.png", new String[]{"Text for scene 3"}, 3.0f));
//        // Add more scenes as needed.
//    }
//
//    @Override
//    protected void loadAssets() {
//        for (Scene scene : scenes) {
//            ServiceLocator.getResourceService().loadTexture(scene.getBackgroundImagePath());
//            // Load other assets like sounds or animations as needed.
//        }
//    }
//
//    @Override
//    public void start() {
//        super.start();
//        cutsceneDisplay.create();
//        cutsceneDisplay.showScene(currentSceneIndex);
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//        cutsceneDisplay.dispose();
//    }
//
//    @Override
//    public void createEntities() {
//        // none
//    }
//
//    public Scene getCurrentScene() {
//        return scenes.get(currentSceneIndex);
//    }
//}

package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.GdxGame;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.cutscenes.scenes.Scene;

/**
 * Specific cutscene class handling the backstory of the game.
 */
public class BackstoryCutscene extends Cutscene {
    private BackstoryCutsceneDisplay cutsceneDisplay;

    public BackstoryCutscene() {
        super();  // Calls the constructor of the superclass Cutscene
        this.cutsceneDisplay = new BackstoryCutsceneDisplay(ServiceLocator.getGdxGame());
    }

    @Override
    protected void setupScenes() {
        // Initialize scenes with their respective background images, animation paths (if any), text, and duration
        scenes.add(new Scene(
                "images/Cutscenes/scene1_background.png",
                null, // No animations for this scene
                new Array<>(new String[]{"Text for scene 1"}),
                5.0f
        ));
        scenes.add(new Scene(
                "images/Cutscenes/scene2_background.png",
                null, // No animations for this scene
                new Array<>(new String[]{"Text for scene 2"}),
                4.0f
        ));
        scenes.add(new Scene(
                "images/Cutscenes/scene3_background.png",
                null, // No animations for this scene
                new Array<>(new String[]{"Text for scene 3"}),
                3.0f
        ));
        // Add more scenes as needed
    }

    @Override
    protected void loadAssets() {
        // Load background images and other assets for each scene
        for (Scene scene : scenes) {
            ServiceLocator.getResourceService().loadTextures(new String[]{scene.getBackgroundImagePath()});
            // Optionally load other types of assets such as sounds or animations
        }
    }

    @Override
    public void start() {
        super.start();  // Calls the start method of the superclass which handles the setup
        cutsceneDisplay.create();
        cutsceneDisplay.showScene(currentSceneIndex);
    }

    @Override
    public void dispose() {
        super.dispose();
        cutsceneDisplay.dispose();
    }

    @Override
    public void createEntities() {
        // Create entities for each scene as needed (e.g., background, characters, interactive elements)
        for (Scene scene : scenes) {
            createEntitiesForScene(scene);
        }
    }

    public Scene getCurrentScene() {
        return scenes.get(currentSceneIndex);
    }
}

