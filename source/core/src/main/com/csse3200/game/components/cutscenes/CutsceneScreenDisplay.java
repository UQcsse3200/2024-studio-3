package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.csse3200.game.GdxGame;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.components.maingame.TextDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The CutsceneScreenDisplay class manages the user interface for displaying cutscenes,
 * including text display, buttons to advance or exit the cutscene, and handling user input.
 */
public class CutsceneScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(CutsceneScreenDisplay.class);
    private Table table;
    private TextDisplay textDisplay;

    public CutsceneScreenDisplay(Skin skin) {
        super(skin);
        this.skin = skin;
    }

    public CutsceneScreenDisplay() {
        super();
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
    }

    /**
     * Sets up the cutscene UI components.
     */
    private void setupUI() {
        // Initialize the text display and add it to the stage, hidden initially
        setupTextDisplay();

        if (skin == null) {
            skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        }

        // Positioning the table at the bottom-right of the screen
        table.bottom().right();
        table.setFillParent(true);

        // Create "Skip" button with its functionality
        TextButton skipBtn = new TextButton("Skip Backstory", skin);
        skipBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.info("Backstory skipped.");
                ServiceLocator.getLevelService().setCurrLevel(GdxGame.LevelType.LEVEL_1);
                entity.getEvents().trigger("cutsceneEnded");  // Trigger skip
            }
        });
        table.add(skipBtn).padBottom(10f).padRight(30f);

        // Create "Next Scene" button with its functionality
        TextButton nextSceneBtn = new TextButton("Next Scene", skin);
        nextSceneBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.info("Next Scene button clicked");
                entity.getEvents().trigger("nextCutscene");  // Trigger next cutscene
            }
        });
        table.add(nextSceneBtn).padBottom(10f).padRight(10f);

        // Create "Exit" button to transition back to the main menu
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ChangeListener() {
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
        topRightTable.add(exitButton).padTop(20f).padRight(20f);
        stage.addActor(topRightTable);
        stage.addActor(table);
    }

    /**
     * Sets up the text display for the screen
     */
    public void setupTextDisplay() {
        textDisplay = new TextDisplay();
        stage.addActor(textDisplay.getTable());
    }

    /**
     * Disposes of the cutscene screen display, clearing any UI elements.
     */
    @Override
    public void dispose() {
        super.dispose();

        if (table != null) {
            table.clear();
        }
        if (textDisplay != null && textDisplay.getTable() != null) {
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
     * Gets the stage component for the cutscene display.
     * @return The Stage component for the cutscene.
     */
    public Stage getStage() { return stage; }

    /**
     * Sets the stage for the cutscene display.
     * @param stage The stage to be set.
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the table for the cutscene UI.
     * @param table The table to be set.
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * Gets the table for the cutscene UI.
     * @return The Table component
     */
    public Table getTable() {
        return table;
    }

    /**
     * Gets the cutscene text display component for the cutscene.
     * @return A CutsceneTextDisplay component for the cutscene.
     */
    public TextDisplay getTextDisplay() { return textDisplay; }

    /**
     * Sets the Cutscene Text Display Component for the cutscene.
     * @param textDisplay the CutsceneTextDisplay to be set.
     */
    public void setTextDisplay(TextDisplay textDisplay) { this.textDisplay = textDisplay; }

}