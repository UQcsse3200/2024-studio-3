package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The CutsceneScreenDisplay class manages the user interface for displaying cutscenes,
 * including text display, buttons to advance or exit the cutscene, and handling user input.
 */
public class CutsceneScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(CutsceneScreenDisplay.class);
    private Table table;
    private CutsceneTextDisplay textDisplay;

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
    }

    /**
     * Sets up the cutscene UI components.
     */
    private void setupUI() {
        // Initialize the text display and add it to the stage, hidden initially
        textDisplay = new CutsceneTextDisplay();
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
        TextButton ExitButton = new TextButton("Exit", skin);
        ExitButton.addListener(new ChangeListener() {
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
        topRightTable.add(ExitButton).padTop(20f).padRight(20f);
        stage.addActor(topRightTable);
        stage.addActor(table);
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
     * Sets the table for the cutscene UI.
     * @param table The table to be set.
     */
    public void setTable(Table table) {
        this.table = table;
    }
}
