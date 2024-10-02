package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Input;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import com.csse3200.game.input.InputService;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles user actions during cutscenes. Allows for transitioning between scenes,
 * skipping cutscenes, and exiting to the main menu based on user input.
 */
public class CutsceneActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(CutsceneActions.class);

    // Reference to the main game instance
    private GdxGame game;

    // Service for handling user input
    private InputService inputService;

    /**
     * Constructor for CutsceneActions.
     * @param game The current instance of the game
     */
    public CutsceneActions(GdxGame game) {
        this.game = game;
    }

    /**
     * Initializes the component, setting up event listeners for cutscene events and retrieving input service.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("cutsceneEnded", this::cutsceneEnded);
        entity.getEvents().addListener("exitCutscene", this::exitCutscene);
        entity.getEvents().addListener("nextCutscene", this::nextCutscene);
        inputService = ServiceLocator.getInputService();
    }

    /**
     * Checks for user input during cutscenes, specifically the space bar to skip to the next scene
     * or the backspace key to exit to the main menu.
     */
    @Override
    public void update() {
        // Check if the space bar is pressed to skip the current cutscene or move to the next scene
        if (inputService.keyDown(Input.Keys.SPACE)) {
            logger.debug("Space bar pressed. Moving to next cutscene or level.");
            cutsceneEnded(); // Trigger the end of the current cutscene
        }

        // Check if the backspace key is pressed to exit the cutscene and return to the main menu
        if (inputService.keyDown(Input.Keys.BACKSPACE)) {
            logger.debug("Backspace bar pressed. Moving to the main menu.");
            exitCutscene(); // Exit the cutscene and go back to the main menu
        }
    }

    /**
     * Loads the next cutscene in the sequence.
     */
    private void nextCutscene() {
        ServiceLocator.getCurrentCutscene().nextCutscene();
    }

    /**
     * Ends the current cutscene and transitions to the next level, cutscene in the game or main menu.
     */
    private void cutsceneEnded() {
        logger.debug("Transitioning to next cutscene, game level or main menu.");
        // Logic for determining what the next screen should be (either next level or cutscene).
        if (ServiceLocator.getCutsceneScreen().getVal() == 0) {
            game.setScreen(GdxGame.ScreenType.MAIN_GAME);
        } else {
            game.setScreen(GdxGame.ScreenType.MAIN_MENU);
        }
    }

    /**
     * Exits the cutscene and returns to the main menu.
     */
    private void exitCutscene() {
        logger.debug("Transitioning to main menu.");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

}
