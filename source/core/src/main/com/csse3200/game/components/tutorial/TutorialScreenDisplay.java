package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


/**
 * Displays tutorial-related UI components and manages tutorial flow.
 */
public class TutorialScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(TutorialScreenDisplay.class);
    private final GdxGame game;
    private Label tutorialLabel;
    private int tutorialStep = 0;  // tracks the current tutorial step
    private final MainGameOrderTicketDisplay orderTicketDisplay;
    private boolean createOrderPressed = false;
    private boolean docketsShifted = false;
    private MainGameOrderBtnDisplay orderBtnDisplay;
    Table table = new Table();

    public TutorialScreenDisplay(GdxGame game) {
        this.game = game;
        this.orderTicketDisplay = new MainGameOrderTicketDisplay();
    }

    @Override
    public void create() {
        super.create();
        setupUI();
        advanceTutorialStep();

        ServiceLocator.getInputService().getEvents().addListener("createOrder", this::onCreateOrderPressed);
    }

    /**
     * Sets up the tutorial UI components.
     */
    private void setupUI() {
        // Create a skin for loading textures
        Skin skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));

        // Manually load the white_box texture and add it to the skin
        skin.add("tutorial_box", new Texture(Gdx.files.internal("images/tutorial/tutorial_box.png")));

        // Create a white box background
        // White box for background
        Image tutorialBox = new Image(skin.getDrawable("tutorial_box"));
        tutorialBox.setSize(300, 150);
        float boxX = stage.getViewport().getWorldWidth() * 0.1f;
        float boxY = stage.getViewport().getWorldHeight() * 0.75f;
        tutorialBox.setPosition(boxX, boxY);

        tutorialLabel = new Label("", skin);
        tutorialLabel.setFontScale(1.2f);  // scale font size
        tutorialLabel.setPosition(boxX + 20, boxY + 80);

        stage.addActor(tutorialBox);
        stage.addActor(tutorialLabel);
        stage.addActor(table);
    }

    /**
     * Proceeds to the next tutorial step using a switch-case.
     */
    private void advanceTutorialStep() {
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
        tutorialLabel.setText("Use W/A/S/D to move around.");
    }

    /**
     * Displays the item pickup tutorial. The player needs to press E to pick up an item.
     */
    private void showItemPickupTutorial() {
        tutorialLabel.setText("Press E to pick up the item.");
    }

    /**
     * Displays the docket switching tutorial. The player needs to use [ and ] to switch dockets and create an order.
     */
    private void showOrderingTutorial() {
        tutorialLabel.setText("To begin, press the 'Create Order' button."); // Main instruction


        if (!createOrderPressed) {
            // Button press will be handled in onCreateOrderPressed() event listener
            return;
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET)) {
            docketsShifted = true;
        }

        if (createOrderPressed && docketsShifted) {
            advanceTutorialStep();
        }
    }

    /**
     * Completes the tutorial and informs the player that they can continue.
     */
    private void completeTutorial() {
        tutorialLabel.setText("Tutorial Complete! Press ENTER to continue.");
    }

    /**
     * Starts the main game after the tutorial is complete.
     */
    private void startGame() {
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);  // Transition to the main game
    }

    @Override
    public void update() {
        // check for key inputs

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

                if (!createOrderPressed) {
                    if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {  // need to change to work with on screen button
                        createOrderPressed = true;
                    }
                } else {
                    if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET)) {
                        docketsShifted = true;
                    }
                }
                if (createOrderPressed && docketsShifted) {
                    advanceTutorialStep();
                }
                break;
            case 4:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    startGame();
                }
                break;
        }
    }

    private void onCreateOrderPressed() {
        createOrderPressed = true;
        tutorialLabel.setText("Now use [ and ] keys to switch dockets.");

    }

    @Override
    public void dispose() {
        super.dispose();
        tutorialLabel.remove();
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

