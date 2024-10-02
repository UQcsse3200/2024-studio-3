package com.csse3200.game.components.settingsmenu;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.services.*;

/**
 * This class listens to events relevant to the Load game Screen and does something when one of the
 * events is triggered.
 */
public class LoadGameActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(LoadGameActions.class);
    private GdxGame game;

    public LoadGameActions(GdxGame game) {

        this.game = game;
    }

    /**
     * Exits the game.
     */
    private void onExit() {
        logger.info("Exit load game");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

    /**
     * Swaps to the Main Game screen.
     */
    private void onStart(String saveFile) {
        logger.info("Start game");
        ServiceLocator.getSaveLoadService().setSaveFile(saveFile);
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    @Override
    public void create() {

        entity.getEvents().addListener("start", this::onStart);
        entity.getEvents().addListener("exit", this::onExit);
    }
}