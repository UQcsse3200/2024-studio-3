package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Input;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import com.csse3200.game.input.InputService;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CutsceneActions extends Component {
    private static  final Logger logger = LoggerFactory.getLogger(CutsceneActions.class);
    private GdxGame game;
    private InputService inputService;

    public CutsceneActions(GdxGame game) {this.game = game;}

    @Override
    public void create() {
        entity.getEvents().addListener("cutsceneEnded", this::cutsceneEnded);
        entity.getEvents().addListener("exitCutscene", this::exitCutscene);
        inputService = ServiceLocator.getInputService();
    }

    @Override
    public void update() {
        // Check for space bar press
        if (inputService.keyDown(Input.Keys.SPACE)) {
            logger.debug("Space bar pressed. Moving to next cutscene or level.");
            cutsceneEnded();
        }
        if (inputService.keyDown(Input.Keys.BACKSPACE)){
            logger.debug("Backspace bar pressed. Moving to the main menu");
            exitCutscene();
        }
    }

    private void cutsceneEnded() {
        logger.debug("Transitioning to next cutscene or game level.");
        // Need better logic to determine which level of the game it should be on.
        // Could also end up transitioning to the next cutscene maybe
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    private void exitCutscene() {
        logger.debug("Transitioning to main menu");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

}
