package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.csse3200.game.components.tutorial.TutorialTextDisplay;

/**
 * Displays tutorial-related UI components and manages tutorial flow using textDisplay.
 */
public class TutorialScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(TutorialScreenDisplay.class);
    private final GdxGame game;
    private Skin skin;
    private int tutorialStep = 0;
    private MainGameOrderTicketDisplay orderTicketDisplay;
    private MainGameOrderBtnDisplay orderBtnDisplay;
    private boolean createOrderPressed = false;
    private boolean docketsShifted = false;
    private Table table;
    private TutorialTextDisplay textDisplay;

    public TutorialScreenDisplay(GdxGame game) {
        this.game = game;
        this.orderTicketDisplay = new MainGameOrderTicketDisplay();
        this.orderBtnDisplay = new MainGameOrderBtnDisplay();
    }

    @Override
    public void create() {
        super.create();

        if (table == null) {
            table = new Table();  // Ensure table is initialized
        }


        setupUI();

        // Initialize the textDisplay before using it, but don't manually call create()
        textDisplay = new TutorialTextDisplay();
        textDisplay.setVisible(false);  // Initially hidden
        stage.addActor(textDisplay.getTable());  // Add it to the stage

        advanceTutorialStep();  // Ensure textDisplay is initialized before calling this method

        entity.getEvents().addListener("createOrder", this::onCreateOrderPressed);
        ServiceLocator.getInputService().getEvents().addListener("createOrder", this::onCreateOrderPressed);

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
     * This now only uses textDisplay to show the instructions.
     */
    private void showMovementTutorial() {
        // Set tutorial text using textDisplay
        textDisplay.setVisible(true);
        createTextBox("Use W/A/S/D to move around.");

    }

    /**
     * Displays the item pickup tutorial. The player needs to press E to pick up an item.
     * This now only uses textDisplay to show the instructions.
     */
    private void showItemPickupTutorial() {
        // Set tutorial text using textDisplay
        textDisplay.setVisible(true);
        createTextBox("Press E to pick up items.");

    }

    /**
     * Displays the ordering tutorial. The player needs to use [ and ] to switch dockets.
     */
    public void showOrderingTutorial() {
        textDisplay.setVisible(true);
        createTextBox("To begin, press the 'Create Order' button.");

        // Check if the order has been created
        if (createOrderPressed) {
            createTextBox("Now use [ and ] keys to switch dockets.");

            // Check if the user presses [ or ]
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET)) {
                docketsShifted = true;
            }

            if (createOrderPressed && docketsShifted) {
                advanceTutorialStep();
            }
        }
    }

    /**
     * Completes the tutorial and informs the player that they can continue.
     * This now only uses textDisplay to show the completion message.
     */
    private void completeTutorial() {
        createTextBox("Tutorial Complete! Press ENTER to continue.");
        textDisplay.setVisible(true);
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
                if (createOrderPressed) {
                    textDisplay.setText("Now use [ and ] keys to switch dockets.");

                    // Check if the user presses [ or ]
                    if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET)) {
                        docketsShifted = true;
                    }

                    if (createOrderPressed && docketsShifted) {
                        advanceTutorialStep();
                    }
                }
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

    // set a loop where count starts from 0, incrementing by 1 each time and the loop
    // is terminated once youve pressed the key i = 0 number of text boxes (4)


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
