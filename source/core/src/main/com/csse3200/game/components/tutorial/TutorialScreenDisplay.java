package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.components.player.PlayerActions;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;

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
    boolean docketsShifted = false;
    private Table table;
    private TutorialTextDisplay textDisplay;
    private boolean wPressedLastFrame = false;
    private boolean aPressedLastFrame = false;
    private boolean sPressedLastFrame = false;
    private boolean dPressedLastFrame = false;
    private  int  i = 0;
    public TutorialScreenDisplay(GdxGame game) {

        this.game = game;
//        this.orderTicketDisplay = new MainGameOrderTicketDisplay(ServiceLocator.getRenderService(), ServiceLocator.getPlayerService());
//        this.orderBtnDisplay = new MainGameOrderBtnDisplay();

    }

    @Override
    public void create() {
        super.create();

        playerActions = entity.getComponent(PlayerActions.class);

        if (table == null) {
            table = new Table();  // Ensure table is initialised
        }

        setupUI();

        // Initialise the textDisplay before using it
        textDisplay = new TutorialTextDisplay();
        textDisplay.setVisible(false);  // Initially hidden
        stage.addActor(textDisplay.getTable());  // Add it to the stage

        advanceTutorialStep();  // Ensure textDisplay is initialized before calling this method

        // Add event listeners for create order
//        entity.getEvents().addListener("createOrder", this::onCreateOrderPressed);
//        ServiceLocator.getInputService().getEvents().addListener("createOrder", this::onCreateOrderPressed);
        ServiceLocator.getInputService().getEvents().addListener("walked", this::onPlayerMoved);
        ServiceLocator.getInputService().getEvents().addListener("interact", this::onInteraction);// start the tutorial from the first step

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
        tutorialStep++;
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
                completeTutorial();
                break;
            default:
                logger.error("Unexpected tutorial step: " + tutorialStep);
        }
    }

    /**
     * Displays the movement tutorial. The player needs to use W/A/S/D to move.
     */
    private void showMovementTutorial() {
        textDisplay.setVisible(true);
        createTextBox("Use W/A/S/D to move around.");
        ServiceLocator.getInputService().getEvents().addListener("playerMoved", this::onPlayerMoved);
    }

    /**
     * Called when the player moves. Proceeds to the next tutorial step.
     * @return true if the tutorial step should advance, false otherwise.
     */
    private boolean onPlayerMoved() {
        if (i == 0) {
            advanceTutorialStep();
            i++;
            return true; // Indicate that the tutorial should advance
        }
        return false; // No advancement
    }

    /**
     * Called when the player interacts. Proceeds to the next tutorial step.
     * @return true if the tutorial step should advance, false otherwise.
     */
    private boolean onInteraction() {
        if (i == 1) {
            advanceTutorialStep(); // Advance tutorial step
            i++;
            return true; // Indicate that the tutorial should advance
        }
        return false; // No advancement
    }

    /**
     * Displays the item pickup tutorial. The player needs to press E to pick up an item.
     */
    private void showItemPickupTutorial() {
        textDisplay.setVisible(true);
        createTextBox("Press E to pick up items.");
        ServiceLocator.getInputService().getEvents().addListener("interact", this::onInteraction);
    }

    /**
     * Displays the ordering tutorial. The player needs to use [ and ] to switch dockets.
     */

    public void showOrderingTutorial() {
        textDisplay.setVisible(true);

        // Combine both instructions into one
        createTextBox("Press [ and ] keys to switch dockets.");

        // Check if both the order button is pressed and the dockets are shifted
        if ((Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET))) {

            docketsShifted = true;
            logger.debug("Dockets shifted and create order pressed");

            // Advance the tutorial as both conditions are now met
            advanceTutorialStep();
        }
    }

    /**
     * Completes the tutorial and informs the player that they can continue.
     */
    private void completeTutorial() {
        createTextBox("Tutorial Complete! Press ENTER to continue.");
        textDisplay.setVisible(true);
    }

    @Override
    public void update() {
        switch (tutorialStep) {
            case 1:
                // Check if the player moved
                if (onPlayerMoved()) {
                    advanceTutorialStep();
                }
                break;
            case 2:
                // Check if the player interacted
                if (onInteraction()) {
                    advanceTutorialStep();
                }
                break;
            case 3:
//                if (createOrderPressed) {
//                    textDisplay.setText("Now use [ and ] keys to switch dockets.");

                // Check if the user presses [ or ]
                if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET)) {
                    docketsShifted = true;
                    logger.debug("Dockets shifted");
                }

                if (docketsShifted) {
                    logger.debug("Advancing tutorial after dockets shifted");
                    advanceTutorialStep();
                }
                //}
                break;
            case 4:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    startGame();
                }
                break;
        }
    }

    public void onCreateOrderPressed() {
        createOrderPressed = true;
        logger.debug("Create order button pressed, createOrderPressed set to true");
        textDisplay.setText("Now use [ and ] keys to switch dockets.");
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
}
