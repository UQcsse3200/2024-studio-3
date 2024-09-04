package com.csse3200.game.components.maingame;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PauseMenuActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(PauseMenuActions.class);
    private GdxGame game;

    public PauseMenuActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("restart", this::onRestart);
        entity.getEvents().addListener("setting", this::onSettings);
        entity.getEvents().addListener("quit", this::onQuit);
        entity.getEvents().addListener("exitGame", this::onExit);
    }

    private void onRestart() {
        logger.info("Start game");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    private void onExit() {
        logger.info("Exit game");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

    private void onQuit() {
        logger.info("Quit game");
        game.exit();
    }

    private void onSettings() {
        logger.info("Launching settings screen");
        game.setScreen(GdxGame.ScreenType.SETTINGS);
    }

}
