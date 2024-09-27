package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.GdxGame;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The CutsceneScreenDisplay class manages the user interface for displaying cutscenes,
 * including text display, buttons to advance or exit the cutscene, and handling user input.
 */
public class CutsceneScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(CutsceneScreenDisplay.class);
    private final GdxGame game;
    private Table table;
    private CutsceneTextDisplay textDisplay;

    // The current step in the cutscene sequence
    private int cutsceneStep = 0;
    // Stores the text for each step of the cutscene
    private Array<String> cutsceneText;

    /**
     * Constructor for CutsceneScreenDisplay.
     * @param game The main game instance.
     */
    public CutsceneScreenDisplay(GdxGame game) {
        this.game = game;
        this.cutsceneText = new Array<>();
    }

    /**
     * Initializes the cutscene UI, sets up buttons and listeners, and starts the cutscene text display.
     */
    @Override
    public void create() {
        super.create();

        if (table == null) {
            table = new Table();  // Initialize table if it's not already
        }

        setupUI();

        // Initialize the text display and add it to the stage, hidden initially
        textDisplay = new CutsceneTextDisplay();
        textDisplay.setVisible(false);
        stage.addActor(textDisplay.getTable());

        // Positioning the table at the bottom-right of the screen
        table.bottom().right();
        table.setFillParent(true);

        // Create "Next Scene" button with its functionality
        TextButton nextSceneBtn = new TextButton("Next Scene", skin);
        nextSceneBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.info("Next Scene button clicked");
                entity.getEvents().trigger("nextCutscene");  // Trigger next cutscene
            }
        });
        table.add(nextSceneBtn).padTop(10f).padRight(10f);

        // Create "Exit" button to transition back to the main menu
        TextButton ExituButton = new TextButton("Exit", skin);
        ExituButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Main Menu button clicked");
                entity.getEvents().trigger("exitCutscene");  // Trigger cutscene exit
            }
        });

        // Positioning the table for the "Exit" button at the top-right
        Table topRightTable = new Table();
        topRightTable.setFillParent(true);
        topRightTable.top().right();
        topRightTable.add(ExituButton).padTop(20f).padRight(20f);
        stage.addActor(topRightTable);
        stage.addActor(table);

        // Adding sample text for the cutscene
        cutsceneText.add("Hello guys");

        // Initialize the first text in the cutscene
        createTextBox("Start text");

        // Move to the first step in the cutscene
        advanceCutsceneStep();
    }

    /**
     * Sets up the cutscene UI components.
     */
    private void setupUI() {
        // Currently only the text display is used, more UI elements can be added if needed
    }

    /**
     * Sets the text steps for the cutscene.
     * @param cutsceneText An array of strings representing each step of the cutscene.
     */
    public void setCutsceneText(Array<String> cutsceneText) {
        this.cutsceneText.clear();
        this.cutsceneText.addAll(cutsceneText);
        cutsceneStep = 0;  // Reset to the first step
    }

    /**
     * Gets the current cutscene text.
     * @return An array of strings representing each step of the cutscene.
     */
    public Array<String> getCutsceneText() {
        return cutsceneText;
    }

    /**
     * Gets the current step of the cutscene.
     * @return The current step in the cutscene sequence.
     */
    public int getCutsceneStep() {
        return cutsceneStep;
    }

    /**
     * Advances to the next step of the cutscene, displaying the corresponding text.
     * If all steps are completed, it ends the cutscene.
     */
    public void advanceCutsceneStep() {
        logger.info("Cutscene Step: {}, cutsceneText.size {}", cutsceneStep, cutsceneText.size);
        if (cutsceneStep < cutsceneText.size) {
            // Display the text for the current step
            String text = cutsceneText.get(cutsceneStep);
            textDisplay.setText(text);
            cutsceneStep++;  // Move to the next step
        } else {
            // End the cutscene if all steps are complete
            textDisplay.setText("The cutscene ends.");
        }
    }

    /**
     * Updates the cutscene, allowing the player to advance to the next step by pressing the space bar.
     */
    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            advanceCutsceneStep();
        }
    }

    /**
     * Creates a text box entity to display the specified text.
     * @param text The text to display in the text box.
     */
    private void createTextBox(String text) {
        Array<Entity> entities = ServiceLocator.getEntityService().getEntities();

        // Trigger the "SetText" event on all entities
        for (int i = 0; i < entities.size; i++) {
            Entity entity = entities.get(i);
            entity.getEvents().trigger("SetText", text);
        }
    }

    /**
     * Starts the main game after the cutscene ends.
     */
    private void startGame() {
        if (table != null) {
            table.clear();  // Clear the table before starting the game
        }

        logger.info("We are starting the main game");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);  // Transition to the main game
    }

    /**
     * Disposes of the cutscene screen display, clearing any UI elements.
     */
    @Override
    public void dispose() {
        super.dispose();

        if (table != null) {
            table.clear();  // Clear the table safely
        }
        if (textDisplay != null) {
            textDisplay.setVisible(false);
            textDisplay.getTable().clear();  // Clear the text display table
        }
    }

    /**
     * Draw method for rendering the cutscene screen. Handled by the stage.
     * @param batch The sprite batch used for rendering.
     */
    public void draw(SpriteBatch batch) {
        // Drawing is handled by the stage, so no implementation needed here
    }

    /**
     * Sets the stage for the cutscene display.
     * @param stage The stage to be set.
     */
    @Override
    public void setStage(Stage stage) {
        if (stage == null) {
            logger.error("Attempted to set a null stage.");
            return;
        }
        this.stage = stage;
    }

    /**
     * Sets the text display for the cutscene.
     * @param textDisplay The text display to be set.
     */
    public void setTextDisplay(CutsceneTextDisplay textDisplay) {
        this.textDisplay = textDisplay;
    }

    /**
     * Sets the table for the cutscene UI.
     * @param table The table to be set.
     */
    public void setTable(Table table) {
        this.table = table;
    }
}
