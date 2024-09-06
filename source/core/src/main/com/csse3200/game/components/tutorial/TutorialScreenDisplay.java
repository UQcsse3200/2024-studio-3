package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csse3200.game.GdxGame;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Displays tutorial-related UI components and manages tutorial flow.
 */
public class TutorialScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(TutorialScreenDisplay.class);
    private final GdxGame game;
    private Label tutorialLabel;
    private int tutorialStep = 0;  // tracks the current tutorial step
    private MainGameOrderTicketDisplay orderTicketDisplay;


    public TutorialScreenDisplay(GdxGame game) {
        this.game = game;
        this.orderTicketDisplay = new MainGameOrderTicketDisplay();
    }

    @Override
    public void create() {
        super.create();
        setupUI();
        advanceTutorialStep();  // start the tutorial from the first step
    }


    /**
     * Sets up the tutorial UI components.
     */
    private void setupUI() {
        tutorialLabel = new Label("", skin);  // create a label for tutorial instructions
        stage.addActor(tutorialLabel);
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
        // implement all other movement tutorial code ehre
        ServiceLocator.getInputService().getEvents().addListener("playerMoved", this::onPlayerMoved);
    }

    /**
     * Called when the player moves. Proceeds to the next tutorial step.
     */
    private void onPlayerMoved() {
        advanceTutorialStep();
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
        tutorialLabel.setText("Press SPACE to pick up the item."); // NEEDS TO BE MODIFIED ONCE WE TALK TO OTHER TEAM
        // all other item pickup tutorial code to be implemend here

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
