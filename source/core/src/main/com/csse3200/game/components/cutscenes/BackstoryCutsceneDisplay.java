////
////package com.csse3200.game.components.cutscenes;
////
////import com.badlogic.gdx.scenes.scene2d.Actor;
////import com.badlogic.gdx.scenes.scene2d.ui.Table;
////import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
////import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
////import com.badlogic.gdx.utils.Array;
////import com.csse3200.game.GdxGame;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////
////public class BackstoryCutsceneDisplay extends CutsceneScreenDisplay {
////    private static final Logger logger = LoggerFactory.getLogger(BackstoryCutsceneDisplay.class);
////
////    private int cutsceneStep = 0;
////    private CutsceneTextDisplay textDisplay;
////    private Array<String> cutsceneText;
////    private Table table; // Moved to class level for consistent usage
////
////    public BackstoryCutsceneDisplay(GdxGame game) {
////        super(game);
////        this.cutsceneText = new Array<>(); // Initialize cutscene text
////    }
////
////    @Override
////    public void create() {
////        super.create();
////        setupUI();  // Setup UI components
////        loadCutsceneText();  // Preload the cutscene text
////
////        // Ensure that the first cutscene text is displayed
////        if (cutsceneText.size > 0) {
////            advanceCutsceneStep();  // Ensure the first text shows after creating the UI
////        } else {
////            logger.error("No cutscene text found.");
////        }
////    }
////
////    /**
////     * Sets up the UI components including text display and next scene button.
////     */
////    private void setupUI() {
////        // Initialize table
////        if (table == null) {
////            table = new Table();
////            table.setFillParent(true);  // Set it to fill the screen
////        }
////
////        // Initialize the text display and set it to be invisible initially
////        textDisplay = new CutsceneTextDisplay();
////        textDisplay.setVisible(false);  // Initially hidden
////
////        // Add the text display to the stage
////        stage.addActor(textDisplay.getTable());  // Assuming `getTable()` returns the table UI component for the text
////
////        // Initialize the next scene button
////        TextButton nextSceneBtn = new TextButton("Next Scene", skin);
////        nextSceneBtn.addListener(new ChangeListener() {
////            @Override
////            public void changed(ChangeEvent event, Actor actor) {
////                advanceCutsceneStep();
////            }
////        });
////
////        // Add the next scene button to the table
////        table.add(nextSceneBtn).padTop(10f).padRight(10f);
////        stage.addActor(table); // Add the table to the stage that is set externally
////    }
////
////    /**
////     * Loads the cutscene text from the current cutscene object.
////     */
////    private void loadCutsceneText() {
////        BackstoryCutscene cutscene = (BackstoryCutscene) game.getCurrentCutscene();
////        if (cutscene != null) {
////            cutsceneText.addAll(cutscene.getCutsceneText());  // Load all cutscene text
////            logger.debug("Loaded cutscene text: " + cutsceneText);
////        } else {
////            logger.error("Failed to load cutscene text. Current cutscene is null.");
////        }
////    }
////
////    /**
////     * Advances to the next cutscene step and updates the displayed text.
////     */
////    public void advanceCutsceneStep() {
////        if (cutsceneStep < cutsceneText.size) {
////            String nextText = cutsceneText.get(cutsceneStep);
////            textDisplay.setText(nextText);
////            textDisplay.setVisible(true);  // Ensure the text display is visible
////            cutsceneStep++;  // Move to the next step
////        } else {
////            logger.info("Cutscene completed. Hiding text display.");
////            textDisplay.setText("The cutscene ends.");
////            textDisplay.setVisible(false);  // Hide the text display when cutscene ends
////        }
////    }
////
////    @Override
////    public void dispose() {
////        super.dispose();
////        if (textDisplay != null) {
////            textDisplay.dispose();
////        }
////        if (table != null) {
////            table.clear();
////        }
////    }
////}
////
//package com.csse3200.game.components.cutscenes;
//
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
//import com.badlogic.gdx.utils.Array;
//import com.csse3200.game.GdxGame;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class BackstoryCutsceneDisplay extends CutsceneScreenDisplay {
//    private static final Logger logger = LoggerFactory.getLogger(BackstoryCutsceneDisplay.class);
//
//    private int cutsceneStep = 0;
//    private Array<String> cutsceneText;
//    private CutsceneTextDisplay textDisplay;
//    private Table table;
//    private GdxGame game;
//
//    public BackstoryCutsceneDisplay(GdxGame game) {
//        super(game.getSkin()); // Assuming CutsceneScreenDisplay expects a Skin
//        this.game = game; // Store game instance
//        this.cutsceneText = new Array<>(); // Initialize cutscene text
//    }
//
//    @Override
//    public void create() {
//        super.create();
//        setupUI();  // Setup UI components
//        loadCutsceneText();  // Preload the cutscene text
//
//        // Ensure that the first cutscene text is displayed
//        if (cutsceneText.size > 0) {
//            advanceCutsceneStep();  // Ensure the first text shows after creating the UI
//        } else {
//            logger.error("No cutscene text found.");
//        }
//    }
//
//    /**
//     * Sets up the UI components including text display and next scene button.
//     */
//    private void setupUI() {
//        // Initialize table
//        if (table == null) {
//            table = new Table();
//            table.setFillParent(true);  // Set it to fill the screen
//        }
//
//        // Initialize the text display and set it to be invisible initially
//        textDisplay = new CutsceneTextDisplay(getSkin());
//        textDisplay.setVisible(false);  // Initially hidden
//
//        // Add the text display to the stage
//        stage.addActor(textDisplay.getTable());
//
//        // Initialize the next scene button
//        TextButton nextSceneBtn = new TextButton("Next Scene", getSkin());
//        nextSceneBtn.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                advanceCutsceneStep();
//            }
//        });
//
//        // Add the next scene button to the table
//        table.add(nextSceneBtn).padTop(10f).padRight(10f);
//        stage.addActor(table); // Add the table to the stage
//    }
//
//    /**
//     * Loads the cutscene text from the current cutscene object.
//     */
//    private void loadCutsceneText() {
//        BackstoryCutscene cutscene = (BackstoryCutscene) game.getCurrentCutscene();
//        if (cutscene != null) {
//            cutsceneText.addAll(cutscene.getCutsceneText());  // Load all cutscene text
//            logger.debug("Loaded cutscene text: " + cutsceneText);
//        } else {
//            logger.error("Failed to load cutscene text. Current cutscene is null.");
//        }
//    }
//
//    /**
//     * Advances to the next cutscene step and updates the displayed text.
//     */
//    public void advanceCutsceneStep() {
//        if (cutsceneStep < cutsceneText.size) {
//            String nextText = cutsceneText.get(cutsceneStep);
//            textDisplay.setText(nextText);
//            textDisplay.setVisible(true);  // Ensure the text display is visible
//            cutsceneStep++;  // Move to the next step
//        } else {
//            logger.info("Cutscene completed. Hiding text display.");
//            textDisplay.setText("The cutscene ends.");
//            textDisplay.setVisible(false);  // Hide the text display when cutscene ends
//        }
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//        if (textDisplay != null) {
//            textDisplay.dispose();
//        }
//        if (table != null) {
//            table.clear();
//        }
//    }
//}
