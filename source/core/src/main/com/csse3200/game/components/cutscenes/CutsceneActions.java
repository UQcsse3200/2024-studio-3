package com.csse3200.game.components.cutscenes;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
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
    private final GdxGame game;

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
        if(logger.isDebugEnabled()) {
            logger.debug(String.valueOf(ServiceLocator.getCutsceneScreen().getVal()));
        }
        if (ServiceLocator.getCutsceneScreen().getVal() == GdxGame.CutsceneType.GOOD_END ||
            ServiceLocator.getCutsceneScreen().getVal() == GdxGame.CutsceneType.BAD_END ||
            ServiceLocator.getCutsceneScreen().getVal() == GdxGame.CutsceneType.LOSE) {
            game.setScreen(GdxGame.ScreenType.MAIN_MENU);
        } else {
            game.setScreen(GdxGame.ScreenType.MAIN_GAME);
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
