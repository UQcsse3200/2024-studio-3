//package com.csse3200.game.components.cutscenes;
//
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
//import com.badlogic.gdx.utils.Array;
//import com.csse3200.game.GdxGame;
//
//
///**
// * Display class specifically for the backstory cutscene, handling text updates.
// */
//public class BackstoryCutsceneDisplay extends CutsceneScreenDisplay {
//    private int cutsceneStep = 0;  // Track the current text step in the cutscene
//    private Array<String> cutsceneText;
//    private CutsceneTextDisplay textDisplay;
//    private Table table;
//    public BackstoryCutsceneDisplay(GdxGame game) {
//        super(game);
//    }
//
//    @Override
//    public void create() {
//        super.create();
//
//        // Initialize text display for the cutscene
//        textDisplay = new CutsceneTextDisplay();
//        textDisplay.setVisible(false);  // Hide until needed
//        stage.addActor(textDisplay.getTable());
//
//        // Modify setupUI to add custom functionality for BackstoryCutscene
//        setupUI();
//
//        // Pre-load the cutscene text
//        loadCutsceneText();
//    }
//
//    /**
//     * Set up the UI, particularly the "Next Scene" button with its new functionality.
//     */
//    private void setupUI() {
//        TextButton nextSceneBtn = new TextButton("Next Scene", skin);
//        nextSceneBtn.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                advanceCutsceneStep();
//            }
//        });
//        table.add(nextSceneBtn).padTop(10f).padRight(10f);
//    }
//
//    /**
//     * Load and initialize the text for the cutscene.
//     */
//    private void loadCutsceneText() {
//        // Example text sequence, should be dynamically loaded as needed
//        cutsceneText.addAll("In the year 2045, the world changed...",
//                "Humans and animals were forced to coexist in a new order.",
//                "Our hero, a chef, is one of the last remaining humans...");
//        advanceCutsceneStep();  // Start with the first text
//    }
//
//    /**
//     * Advances to the next text in the cutscene, or ends the cutscene if all texts are shown.
//     */
//    public void advanceCutsceneStep() {
//        if (cutsceneStep < cutsceneText.size) {
//            String nextText = cutsceneText.get(cutsceneStep);
//            textDisplay.setText(nextText);
//            cutsceneStep++;  // Move to the next step
//            textDisplay.setVisible(true);
//        } else {
//            textDisplay.setText("The cutscene ends.");  // Indicate end of the cutscene
//            textDisplay.setVisible(false);
//        }
//    }
//}
package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.cutscenes.scenes.Scene;

public class BackstoryCutsceneDisplay extends CutsceneScreenDisplay {
    private GdxGame game;
    private Image backgroundImage;
    private Stage stage;
    private int cutsceneStep = 0;
    private CutsceneTextDisplay textDisplay;

    public BackstoryCutsceneDisplay(GdxGame game) {
        super(game);
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        create();
    }

    @Override
    public void create() {
        super.create();
        backgroundImage = new Image();
        stage.addActor(backgroundImage);
        setupUI();
    }

    private void setupUI() {
        TextButton nextSceneBtn = new TextButton("Next Scene", skin);
        nextSceneBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                advanceCutsceneStep();
            }
        });
        stage.addActor(nextSceneBtn);
    }

    public void advanceCutsceneStep() {
        cutsceneStep++;
        showScene(cutsceneStep);
    }

//    public void showScene(int index) {
//        BackstoryCutscene cutscene = (BackstoryCutscene) game.getCurrentCutscene();
//        Scene currentScene = cutscene.getCurrentScene();
//
//        if (currentScene != null) {
//            backgroundImage.setDrawable(new TextureRegionDrawable(new Texture(currentScene.getBackgroundImagePath())));
//            textDisplay.setText(currentScene.getCutsceneText()[0]); // Assuming each scene has only one string for simplicity
//        } else {
//            textDisplay.setText("The cutscene ends.");
//            textDisplay.setVisible(false);
//        }
//    }
public void showScene(int index) {
    BackstoryCutscene cutscene = (BackstoryCutscene) game.getCurrentCutscene();
    Scene currentScene = cutscene.getCurrentScene();

    if (currentScene != null) {
        // Correctly access the first element of the Array using .get(0) instead of [0]
        String sceneText = currentScene.getCutsceneText().get(0);

        backgroundImage.setDrawable(new TextureRegionDrawable(new Texture(currentScene.getBackgroundImagePath())));
        textDisplay.setText(sceneText); // Use the variable holding the retrieved text
    } else {
        textDisplay.setText("The cutscene ends.");
        textDisplay.setVisible(false);
    }
}


    @Override
    public void dispose() {
        super.dispose();
        if (stage != null) {
            stage.dispose();
        }
    }
}
