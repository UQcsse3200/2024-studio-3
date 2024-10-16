package com.csse3200.game.components.maingame;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.player.PlayerStatsDisplay;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.services.ServiceLocator;
import com.badlogic.gdx.Gdx;
import com.csse3200.game.services.SaveLoadService;
import com.csse3200.game.GdxGame;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * This class listens to events relevant to the Main Game Screen and does something when the button on the Pause
 * Menu is clicked.
 */
public class PauseMenuActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(PauseMenuActions.class);
    private final GdxGame game;

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
        entity.getEvents().addListener("saveGame", this::onSave);
    }

    private void onSave() {
        Stage stage = ServiceLocator.getRenderService().getStage();
        Skin skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        if (ServiceLocator.getSaveLoadService().getSaveFile().length() == 0) {
            new TextPopup("New Save File", skin, stage, game);
        }
    }

    /**
     * Restart the game.
     */
    private void onRestart() {
        logger.info("Start game");
        ServiceLocator.getLevelService().reset(); 
        PlayerStatsDisplay.reset(); 
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    /**
     * Swaps to the Main Menu screen
     */
    private void onExit() {
        logger.info("Exit game");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
        ServiceLocator.getLevelService().setCurrLevel(GdxGame.LevelType.LEVEL_1);
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
