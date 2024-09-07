package com.csse3200.game.components.maingame;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Game Screen and does something when the button on the Pause
 * Menu is clicked.
 */
public class PauseMenuActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(PauseMenuActions.class);
    private GdxGame game;

    /**
     * Constructs a PauseMenuActions instance
     *
     * @param game the game
     */
    public PauseMenuActions(GdxGame game) {
        this.game = game;
    }

    /**
     * Initializes the component and sets up event listeners.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("restart", this::onRestart);
        entity.getEvents().addListener("setting", this::onSettings);
        entity.getEvents().addListener("quit", this::onQuit);
        entity.getEvents().addListener("exitGame", this::onExit);
    }

    /**
     * Restart the game.
     */
    private void onRestart() {
        logger.info("Start game");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    /**
     * Swaps to the Main Menu screen
     */
    private void onExit() {
        logger.info("Exit game");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

    /**
     * Quit the game
     */
    private void onQuit() {
        logger.info("Quit game");
        game.exit();
    }

    /**
     * Swaps to the Setting screen
     */
    private void onSettings() {
        logger.info("Launching settings screen");
        game.setScreen(GdxGame.ScreenType.SETTINGS);
    }
}
