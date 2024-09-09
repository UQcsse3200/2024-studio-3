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
    private Image tutorialBox;
    private Skin skin;
    private int tutorialStep = 0;
    private MainGameOrderTicketDisplay orderTicketDisplay;
    private MainGameOrderBtnDisplay orderBtnDisplay;
    private Image movementImage;
    private Image pickupImage;
    private boolean createOrderPressed = false;
    private boolean docketsShifted = false;
    Table table = new Table();

    public TutorialScreenDisplay(GdxGame game) {
        this.game = game;
        this.orderTicketDisplay = new MainGameOrderTicketDisplay();
        this.orderBtnDisplay = new MainGameOrderBtnDisplay();
    }

    @Override
    public void create() {
        super.create();
        setupUI();
        advanceTutorialStep();

        entity.getEvents().addListener("createOrder", this::onCreateOrderPressed);
        ServiceLocator.getInputService().getEvents().addListener("createOrder", this::onCreateOrderPressed);


//
    }

    /**
     * Sets up the tutorial UI components.
     */
    private void setupUI() {
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));

        skin.add("tutorial_box", new Texture(Gdx.files.internal("images/tutorial/tutorial_box.png")));


        tutorialBox = new Image(skin.getDrawable("tutorial_box"));
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

        if (movementImage != null) {
            movementImage.remove();
            movementImage = null;
        }
        if (pickupImage != null) {
            pickupImage.remove();
            pickupImage = null;
        }

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
        // Load and display the movement image
        if (movementImage == null) {
            skin.add("movement_image", new Texture(Gdx.files.internal("images/tutorial/MOMENT TUT.jpg")));
            movementImage = new Image(skin.getDrawable("movement_image"));
            movementImage.setSize(1100, 800);
            float imageX = stage.getViewport().getWorldWidth() * 0f; // Center image
            float imageY = stage.getViewport().getWorldHeight() * 0.15f;
            movementImage.setPosition(imageX, imageY);
            stage.addActor(movementImage);
        }
    }

    /**
     * Displays the item pickup tutorial. The player needs to press E to pick up an item.
     */
    private void showItemPickupTutorial() {

        if (pickupImage == null) {
            skin.add("pickup_image", new Texture(Gdx.files.internal("images/tutorial/PICKUP TUT.jpg")));
            pickupImage = new Image(skin.getDrawable("pickup_image"));
            pickupImage.setSize(1100, 800);  // Adjust size as necessary
            float imageX = stage.getViewport().getWorldWidth() * 0f;  // Adjust position as necessary
            float imageY = stage.getViewport().getWorldHeight() * 0.15f;
            pickupImage.setPosition(imageX, imageY);
            stage.addActor(pickupImage);
        }
    }

    /**
     * Displays the docket switching tutorial. The player needs to use [ and ] to switch dockets.
     */
    private void showOrderingTutorial() {
        tutorialLabel.setText("To begin, press the 'Create Order' button."); // Wait for button press

        // Wait for the button press (handled by onCreateOrderPressed)
        if (createOrderPressed) {
            tutorialLabel.setText("Now use [ and ] keys to switch dockets.");

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

        switch (tutorialStep) {
            case 1:

                if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.A) ||
                        Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                    movementImage.remove();
                    movementImage = null;
                    advanceTutorialStep();
                }
                break;

            case 2:

                if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {

                    pickupImage.remove();
                    pickupImage = null;
                    advanceTutorialStep();
                }
                break;

            case 3:

                if (createOrderPressed) {
                    tutorialLabel.setText("Now use [ and ] keys to switch dockets.");

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

    private void onCreateOrderPressed() {
        createOrderPressed = true;
        tutorialLabel.setText("Now use [ and ] keys to switch dockets.");
    }

    @Override
    public void dispose() {
        super.dispose();
        tutorialLabel.remove();
        if (movementImage != null) movementImage.remove();
        if (pickupImage != null) pickupImage.remove();
        orderBtnDisplay.dispose(); // Dispose the order button
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






