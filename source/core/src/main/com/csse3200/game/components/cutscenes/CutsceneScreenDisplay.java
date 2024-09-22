package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.GdxGame;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CutsceneScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(CutsceneScreenDisplay.class);
    private final GdxGame game;
    private Table table;
    private CutsceneTextDisplay textDisplay;

    // The current step in the cutscene
    private int cutsceneStep = 0;
    // Store all text steps for the current cutscene
    private Array<String> cutsceneText;

    public CutsceneScreenDisplay(GdxGame game) {
        this.game = game;
        this.cutsceneText = new Array<>();
    }

    @Override
    public void create() {
        super.create();

        if (table == null) {
            table = new Table();  // Ensure table is initialised
        }

        setupUI();

        // Initialise the textDisplay before using it
        textDisplay = new CutsceneTextDisplay();
        textDisplay.setVisible(false);  // Initially hidden
        stage.addActor(textDisplay.getTable());  // Add it to the stage

        advanceCutsceneStep();  // Ensure textDisplay is initialized before calling this method

        stage.addActor(table);
    }

    private void setupUI() {
        // Only using textDisplay for now, so no need for tutorialBox
    }

    /**
     * Set the text for the current cutscene.
     * This method allows setting different text sequences for different cutscenes.
     *
     * @param cutsceneText Array of strings where each string represents a step in the cutscene
     */
    public void setCutsceneText(Array<String> cutsceneText) {
        this.cutsceneText.clear();
        this.cutsceneText.addAll(cutsceneText);
        cutsceneStep = 0;  // Reset to the first step
    }

    /**
     * Proceeds to the next step in the cutscene, displaying the next piece of text.
     * Automatically ends the cutscene when all steps are complete.
     */
    public void advanceCutsceneStep() {
        if (cutsceneStep < cutsceneText.size) {
            String text = cutsceneText.get(cutsceneStep);
            textDisplay.setText(text);  // Display the current step text
            cutsceneStep++;  // Move to the next step
        } else {
            textDisplay.setText("The cutscene ends.");
            startGame();  // End the cutscene and start the game
        }
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            advanceCutsceneStep();
        }
    }

    private void createTextBox(String text) {
        Array<Entity> entities = ServiceLocator.getEntityService().getEntities();

        for (int i = 0; i < entities.size; i++) {
            Entity entity = entities.get(i);
            entity.getEvents().trigger("SetText", text);
        }
    }

    private void startGame() {
        if (table != null) {
            table.clear();  // Safely clear the table
        }
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);  // Transition to the main game
    }

    @Override
    public void dispose() {
        super.dispose();

        if (table != null) {
            table.clear();
        }
        if (textDisplay != null) {
            textDisplay.setVisible(false);
            textDisplay.getTable().clear();
        }
    }

    public void draw(SpriteBatch batch) {
        // handled by stage
    }
    @Override
    public void setStage(Stage stage) {
        if (stage == null) {
            logger.error("Attempted to set a null stage.");
            return;
        }
        this.stage = stage;
    }
}
