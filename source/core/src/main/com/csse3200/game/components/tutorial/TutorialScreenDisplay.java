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
//    private void setupUI() {
//        // Create a skin for loading textures
//        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
//
//        // Clear previous content from the table if needed
//       table.clear();
//
//        // Load the background texture for the table and add it to the skin
//        skin.add("table_bg", new Texture(Gdx.files.internal("images/tutorial/img.png")));
//        skin.add("tutorial_box", new Texture(Gdx.files.internal("images/tutorial/tutorial_box.png")));
//        // Create a white box background
//        tutorialBox = new Image(skin.getDrawable("tutorial_box"));
//        tutorialBox.setSize(300, 150);
//        float boxX = stage.getViewport().getWorldWidth() * 0.1f;
//        float boxY = stage.getViewport().getWorldHeight() * 0.75f;
//        tutorialBox.setPosition(boxX, boxY);
//
//        // Create a tutorial label and position it on top of the box
//        tutorialLabel = new Label("", skin);
//        tutorialLabel.setFontScale(1.2f);  // scale font size
//        tutorialLabel.setPosition(boxX + 20, boxY + 80);
//
//        // Create a new table and set its background image
//     table = new Table();
//        table.setFillParent(true);  // Make table fill the entire stage
//       table.center();  // Center the table on the screen
//        table.setBackground(skin.getDrawable("table_bg"));  // Set the background image for the table
//
//        // Add the label and box to the table for proper layout
//        table.add(tutorialBox).size(300, 150).pad(10);  // Add box to the table with padding
//       table.row();  // Move to the next row
//        table.add(tutorialLabel).pad(10);  // Add label to the next row with padding
//
//        // Add the table to the stage
//     //  stage.addActor(table);
//    }


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

        ServiceLocator.getInputService().getEvents().addListener("playerMoved", this::onPlayerMoved);
    }

    /**
     * Called when the player moves. Proceeds to the next tutorial step.
     */
    private void onPlayerMoved() {

        if(i == 0) advanceTutorialStep();
        i++;
    }
    private void onInteraction() {

        if(i == 1) advanceTutorialStep();//hacky way to implement tutorial
        i++;

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

        ServiceLocator.getTutorialService().getEvents().addListener("itemPickedUp", this::onItemPickedUp);
    }
//   private boolean onInputKey(int keycode) {
//        if (keycode == Input.Keys.E) {s
//            logger.info("'E' key pressed, attempting to pick up item.");
//            // Trigger item pickup event
//            ServiceLocator.getInputService().getEvents().trigger("itemPickedUp");
//            return true;
//        }
//        else {
//            return false;
//        }
//    }



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

