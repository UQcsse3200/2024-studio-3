package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csse3200.game.GdxGame;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Image tutorialBox;  // White box for background
    private Skin skin;
    private  int  i = 0;
    private int tutorialStep = 0;  // tracks the current tutorial step
    private MainGameOrderTicketDisplay orderTicketDisplay;
    private Image movementImage;// New image for movement tutorial
    private Image pickupImage;

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
        ServiceLocator.getInputService().getEvents().addListener("walked", this::onPlayerMoved);
        ServiceLocator.getInputService().getEvents().addListener("interact", this::onInteraction);// start the tutorial from the first step
    }


    /**
     * Sets up the tutorial UI components.
     */
    private void setupUI() {
        // Create a skin for loading textures
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));

        // Manually load the white_box texture and add it to the skin
        skin.add("tutorial_box", new Texture(Gdx.files.internal("images/tutorial/tutorial_box.png")));

        //Create a white box background
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
     * Shifts the order tickets to the left using MainGameOrderTicketDisplay.
     */
    private void shiftDocketsLeft() {
        orderTicketDisplay.shiftDocketsLeft();
    }

    /**
     * Shifts the order tickets to the right using MainGameOrderTicketDisplay.
     */
    private void shiftDocketsRight() {
        orderTicketDisplay.shiftDocketsRight();
    }

    /**
     * Proceeds to the next tutorial step using a switch-case.
     */
    private void advanceTutorialStep() {
        // Remove the movement image if it exists
        if (movementImage != null) {
            movementImage.remove();
            movementImage = null;
        }

        // Remove the pickup image if it exists
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
        tutorialLabel.setText("Use W/A/S/D to move around.");
        // implement all other movement tutorial code here
// Load and display the movement image
        if (movementImage == null) {
            skin.add("movement_image", new Texture(Gdx.files.internal("images/tutorial/MOMENT TUT.jpg")));
            movementImage = new Image(skin.getDrawable("movement_image"));
            movementImage.setSize(1100, 800);
            float imageX = stage.getViewport().getWorldWidth() * 0f; // Center image
            float imageY = stage.getViewport().getWorldHeight() * 0.15f ;
            movementImage.setPosition(imageX, imageY);
            stage.addActor(movementImage);
        }

        ServiceLocator.getInputService().getEvents().addListener("playerMoved", this::onPlayerMoved);
    }

    /**
     * Called when the player moves. Proceeds to the next tutorial step.
     */
    private void onPlayerMoved() {

        if(i == 0)
        {advanceTutorialStep();
        i++;}
    }
    private void onInteraction() {

        if(i == 1)
        {advanceTutorialStep();//hacky way to implement tutorial
        i++;}

    }

    private void onDocketSwitched() {
        // Implement the behavior when a docket is shifted left or right
        logger.info("Docket has been switched");
        advanceTutorialStep();
    }

    /**
     * Displays the docket switching tutorial. The player needs to use [ and ] to switch dockets.
     */
    private void showOrderingTutorial() {
        tutorialLabel.setText("Use the [ and ] keys to switch dockets.");
        // all other ordering code to be implemented here once other team is finished with customer ordering
        ServiceLocator.getTutorialService().getEvents().addListener("shiftDocketsRight", this::onDocketSwitched);
        ServiceLocator.getTutorialService().getEvents().addListener("shiftDocketsLeft", this::onDocketSwitched);
    }

    /**
     * Called when the player switches dockets. Proceeds to the next tutorial step.
     */
    private void onOrderingComplete() {
        advanceTutorialStep();
    }

    /**
     * Displays the item pickup tutorial. The player needs to press SPACE to pick up an item.
     */
    private void showItemPickupTutorial() {
        tutorialLabel.setText("Press E to pick up the item."); // NEEDS TO BE MODIFIED ONCE WE TALK TO OTHER TEAM
        // all other item pickup tutorial code to be implemend here
// Load and display the pickup image
        if (pickupImage == null) {
            skin.add("pickup_image", new Texture(Gdx.files.internal("images/tutorial/PICKUP TUT.jpg")));
            pickupImage = new Image(skin.getDrawable("pickup_image"));
            pickupImage.setSize(1100, 800);  // Adjust size as necessary
            float imageX = stage.getViewport().getWorldWidth() * 0f;  // Adjust position as necessary
            float imageY = stage.getViewport().getWorldHeight() * 0.15f;
            pickupImage.setPosition(imageX, imageY);
            stage.addActor(pickupImage);
        }

        ServiceLocator.getTutorialService().getEvents().addListener("itemPickedUp", this::onItemPickedUp);
    }


    /**
     * Called when the player picks up an item. Proceeds to the next tutorial step.
     */
    private void onItemPickedUp() {
        advanceTutorialStep();
    }

    /**
     * Completes the tutorial and informs the player that they can continue.
     */
    private void completeTutorial() {
        tutorialLabel.setText("Tutorial Complete! Press ENTER to continue.");
        ServiceLocator.getInputService().getEvents().addListener("startGame", this::startGame);
    }

    /**
     * Starts the main game after the tutorial is complete.
     */
    private void startGame() {
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);  // Transition to the main game
    }

    @Override
    public void update() {
        // might be useful later for dynamically updating order tickets
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
