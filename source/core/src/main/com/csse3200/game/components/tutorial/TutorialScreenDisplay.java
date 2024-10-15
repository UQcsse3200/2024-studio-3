package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.components.player.PlayerActions;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.csse3200.game.components.maingame.TextDisplay;

/**
 * Displays tutorial-related UI components and manages tutorial flow using textDisplay.
 */
public class TutorialScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(TutorialScreenDisplay.class);
    private final GdxGame game;
    private Skin skin;
    private PlayerActions playerActions;
    private int tutorialStep = 0;
    private MainGameOrderTicketDisplay orderTicketDisplay;
    private MainGameOrderBtnDisplay orderBtnDisplay;
    private boolean createOrderPressed = false;
    private boolean docketsShifted = false;
    private Table table;
    private TextDisplay textDisplay;
    private boolean wPressedLastFrame = false;
    private boolean aPressedLastFrame = false;
    private boolean sPressedLastFrame = false;
    private boolean dPressedLastFrame = false;
    private static final int MAX_TUTORIAL_STEP = 8;  // Increased for combining step
    int i = 0;

    public TutorialScreenDisplay(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        super.create();

        if (entity != null) {
            playerActions = entity.getComponent(PlayerActions.class);
        } else {
            logger.error("Entity null");
        }

        ServiceLocator.getLevelService().setCurrLevel(GdxGame.LevelType.LEVEL_0);

        if (table == null) {
            table = new Table();  // Ensure table is initialised
        }

        setupUI();

        // Initialise the textDisplay before using it
        textDisplay = new TextDisplay();
        textDisplay.setVisible(false);  // Initially hidden
        stage.addActor(textDisplay.getTable());  // Add it to the stage

        advanceTutorialStep();  // Ensure textDisplay is initialized before calling this method

        stage.addActor(table);
    }

    /**
     * Sets up the UI components (textDisplay only).
     */
    private void setupUI() {
        // Only using textDisplay for now, so no need for tutorialBox
    }

    /**
     * Proceeds to the next tutorial step using a switch-case.
     */
    public void advanceTutorialStep() {
        if (tutorialStep < MAX_TUTORIAL_STEP) {
            tutorialStep++;

            // Clear the previous text before showing the next step's instructions
            clearTextBox();

            switch (tutorialStep) {
                case 1:
                    showMovementTutorial();
                    break;
                case 2:
                    showItemPickupTutorial();
                    break;
                case 3:
                    showOrderingTutorial();
                    break;
                case 4:
                    showRageModeTutorial();
                    break;
                case 5:
                    showChoppingTutorial();
                    break;
                case 6:
                    completeTutorial();
                    break;
                case 7:
                    showRotationTutorial();  // New rotation step
                    break;
                case 8:
                    showCombiningTutorial();  // New combining step
                    break;
                default:
                    logger.error("Unexpected tutorial step: " + tutorialStep);
            }
        } else {
            logger.error("Tutorial step exceeded maximum value.");
        }
    }

    /**
     * Clears the tutorial text box.
     */
    private void clearTextBox() {
        if (textDisplay != null) {
            textDisplay.setText("");  // Clear the text
        } else {
            logger.error("textDisplay is null during clearTextBox.");
        }
    }

    /**
     * Displays the movement tutorial. The player needs to use W/A/S/D to move.
     */
    private void showMovementTutorial() {
        if (textDisplay != null) {
            textDisplay.setVisible(true);
            createTextBox("Use W/A/S/D to move around.");
        } else {
            logger.error("textDisplay is null during showMovementTutorial.");
        }
    }

    /**
     * Displays the item pickup tutorial. The player needs to press E to pick up an item.
     */
    private void showItemPickupTutorial() {
        if (textDisplay != null) {
            textDisplay.setVisible(true);
            createTextBox("Press E to pick up items.");
        } else {
            logger.error("textDisplay is null during showItemPickupTutorial.");
        }
    }

    /**
     * Displays the ordering tutorial. The player needs to use [ and ] to switch dockets.
     */
    public void showOrderingTutorial() {
        if (textDisplay != null) {
            textDisplay.setVisible(true);
            createTextBox("Use [ and ] keys to switch dockets.");

            if ((Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET))) {
                docketsShifted = true;
                logger.debug("Dockets shifted!");

                advanceTutorialStep();
            }
        } else {
            logger.error("textDisplay is null during showOrderingTutorial.");
        }
    }

    /**
     * Displays the Rage Mode tutorial. The player needs to press R to activate Rage Mode.
     */
    public void showRageModeTutorial() {
        if (textDisplay != null) {
            textDisplay.setVisible(true);
            createTextBox("Press R to activate rage mode.");
        } else {
            logger.error("textDisplay is null during showRageModeTutorial.");
        }
    }

    /**
     * Displays the chopping tutorial. The player needs to press Q to chop an item.
     */
    public void showChoppingTutorial() {
        if (textDisplay != null) {
            textDisplay.setVisible(true);
            createTextBox("Pick up an item and press Q to chop it.");
        } else {
            logger.error("textDisplay is null during showChoppingTutorial.");
        }
    }

    /**
     * Displays the rotation tutorial. The player needs to pick up 2 or more items and press K to rotate.
     */
    public void showRotationTutorial() {
        if (textDisplay != null) {
            textDisplay.setVisible(true);
            createTextBox("Pick up 2 or more items and then press K to rotate them.");
        } else {
            logger.error("textDisplay is null during showRotationTutorial.");
        }
    }

    /**
     * Displays the combining tutorial. The player needs to pick up 2 or more items and press J to combine them into a meal.
     */
    public void showCombiningTutorial() {
        if (textDisplay != null) {
            textDisplay.setVisible(true);
            createTextBox("Pick up 2 or more items and press J to combine them into a meal.");
        } else {
            logger.error("textDisplay is null during showCombiningTutorial.");
        }
    }

    /**
     * Completes the tutorial and informs the player that they can continue.
     */
    private void completeTutorial() {
        if (textDisplay != null) {
            textDisplay.setVisible(true);
            createTextBox("Tutorial Complete! Press `Space` to continue.");
        } else {
            logger.error("textDisplay is null during completeTutorial.");
        }
    }

    @Override
    public void update() {
        switch (tutorialStep) {
            case 1:
                if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.A) ||
                        Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                    advanceTutorialStep();
                }
                break;
            case 2:
                if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                    advanceTutorialStep();
                }
                break;
            case 3:
                textDisplay.setText("Now use [ and ] keys to switch dockets.");

                if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET)) {
                    docketsShifted = true;
                    logger.debug("Dockets shifted");
                }

                if (docketsShifted) {
                    advanceTutorialStep();
                }
                break;
            case 4:
                if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {  // Triggered by R key press
                    advanceTutorialStep();
                }
                break;
            case 5:
                if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {  // Triggered by Q key press for chopping
                    advanceTutorialStep();
                }
                break;
            case 6:
                if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {  // Triggered by K key press for rotation
                    advanceTutorialStep();
                }
                break;
            case 7:
                if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {  // Triggered by J key press for combining
                    advanceTutorialStep();
                }
                break;
            case 8:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    startGame();
                }
                break;
        }
    }

    /**
     * Creates tutorial text box. Calls set text.
     * @param text being displayed into textbox.
     */
    private void createTextBox(String text) {
        Array<Entity> entities = ServiceLocator.getEntityService().getEntities();

        for (int i = 0; i < entities.size; i++) {
            Entity entity = entities.get(i);
            entity.getEvents().trigger("SetText", text);
        }
    }

    /**
     * Starts the main game after the tutorial is complete.
     */
    private void startGame() {
        if (table != null) {
            table.clear();  // Safely clear the table
        }
        ServiceLocator.getLevelService().setCurrLevel(GdxGame.LevelType.LEVEL_1);
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);  // Transition to the main game
    }

    @Override
    public void dispose() {
        super.dispose();

        if (table != null) {
            table.clear();
        }

        if (orderBtnDisplay != null) {
            orderBtnDisplay.dispose();
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

    public int getTutorialStep() {
        return tutorialStep;
    }

    public void setTutorialStep(int i) {
        this.tutorialStep = i;
    }

    public boolean isCreateOrderPressed() {
        return createOrderPressed;
    }
}
