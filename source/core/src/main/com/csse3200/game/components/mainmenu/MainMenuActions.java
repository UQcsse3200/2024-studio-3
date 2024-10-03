package com.csse3200.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.tutorial.Confirmationpopup;
import com.csse3200.game.screens.TutorialScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.services.ServiceLocator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * This class listens to events relevant to the Main Menu Screen and does something when one of the
 * events is triggered.
 */
public class MainMenuActions extends Component {
  private static final Logger logger = LoggerFactory.getLogger(MainMenuActions.class);
  private GdxGame game;

  public MainMenuActions(GdxGame game) {
    this.game = game;
  }

  @Override
  public void create() {
    entity.getEvents().addListener("start", this::onStart);
    entity.getEvents().addListener("load", this::onLoad);
    entity.getEvents().addListener("exit", this::onExit);
    entity.getEvents().addListener("settings", this::onSettings);
    entity.getEvents().addListener("tutorial", this::onTutorial);
    entity.getEvents().addListener("cutscene", this::onCutscene);
  }

  /**
   * Swaps to the Main Game screen.
   */
  private void onStart() {
    logger.info("Start game");
    Stage stage = ServiceLocator.getRenderService().getStage();
    Skin skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
    new Confirmationpopup("Game Tuto Confirm", skin, stage, game);

  }

  /**
   * Intended for loading a saved game state.
   * Load functionality is not actually implemented.
   */
  private void onLoad() {
    logger.info("Load game");
  }

  /**
   * Exits the game.
   */
  private void onExit() {
    logger.info("Exit game");
    game.exit();
  }

  /**
   * Swaps to the Settings screen.
   */
  private void onSettings() {
    logger.info("Launching settings screen");
    game.setScreen(GdxGame.ScreenType.SETTINGS);
  }

  /**
   * Swaps to the Tutorial Screen.
   */
  public void onTutorial() {
    logger.debug("Tutorial button clicked");

//    // Stop any ongoing tasks in MainMenuDisplay (e.g., animalMoveTask)
    ServiceLocator.getMainMenuDisplay().stopBackgroundTasks();

    // Transition to the tutorial screen
    game.setScreen(new TutorialScreen(game));
  }

  /**
   * Starts the cutscene
   */
  public void onCutscene() {
    logger.info("Starting cutscene");

    // Stop any background tasks
    ServiceLocator.getMainMenuDisplay().stopBackgroundTasks();

    // Now we can transition to the cutscene
    game.setScreen(GdxGame.ScreenType.CUTSCENE);
  }

}
