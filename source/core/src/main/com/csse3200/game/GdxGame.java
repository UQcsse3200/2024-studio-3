package com.csse3200.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.csse3200.game.components.cutscenes.Cutscene;
import com.csse3200.game.entities.factories.NPCFactory;
import com.csse3200.game.screens.*;
import com.csse3200.game.files.UserSettings;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.screens.MainMenuScreen;
import com.csse3200.game.screens.SettingsScreen;
import com.csse3200.game.screens.LoadGameScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.services.*;

import static com.badlogic.gdx.Gdx.app;
/**
 * Entry point of the non-platform-specific game logic. Controls which screen is currently running.
 * The current screen triggers transitions to other screens. This works similarly to a finite state
 * machine (See the State Pattern).
 */
public class GdxGame extends Game {
  private static final Logger logger = LoggerFactory.getLogger(GdxGame.class);
  private Screen previousScreen;
  private ScreenType currentScreenType;
  private Cutscene currentCutscene;


  @Override
  public void create() {
    logger.info("Creating game");
    loadSettings();

    // Initially set to the main menu screen
    // I want to set the background ot an image

    Gdx.gl.glClearColor(234f/255f, 221/255f, 202/255f, 1);

    ServiceLocator.registerGame(this);

    setScreen(ScreenType.MAIN_MENU);
  }

  /**
   * Loads the game's settings.
   */
  private void loadSettings() {
    logger.debug("Loading game settings");
    UserSettings.Settings settings = UserSettings.get();
    UserSettings.applySettings(settings);
  }

  /**
   * Sets the game's screen to a new screen of the provided type.
   * @param screenType screen type
   */
  public void setScreen(ScreenType screenType) {
    NPCFactory.reset();
    logger.info("Setting game screen to {}", screenType);
    Screen currentScreen = getScreen();
    currentScreenType = screenType;

    previousScreen = currentScreen;  // Save the current screen before changing
    SaveLoadService system = ServiceLocator.getSaveLoadService();
    if (currentScreen != null) {
      currentScreen.dispose();
    }
    ServiceLocator.registerSaveLoadService(system); // I know this is probably bad practice but i need it to work

    logger.warn("Is AWDAdwKA null? {} ",ServiceLocator.getSaveLoadService() == null);

    setScreen(newScreen(screenType));
  }

  public void setScreen(ScreenType screenType, CutsceneType cutsceneType) {
    logger.info("Setting cutscene screen to {} with cutscene {}", screenType, cutsceneType);
    Screen currentScreen = getScreen();

    previousScreen = currentScreen;  // Save the current screen before changing
    SaveLoadService system = ServiceLocator.getSaveLoadService();
    if (currentScreen != null) {
      currentScreen.dispose();
    }
    ServiceLocator.registerSaveLoadService(system); // I know this is probably bad practice but i need it to work

    logger.warn("Is AWDAdwKA null? {} ",ServiceLocator.getSaveLoadService() == null);

    setScreen(newScreen(screenType, cutsceneType));
  }

  /**
   * Get the previous game's screen
   * @return previous screen
   */
  public Screen getPreviousScreen() {
    return previousScreen;
  }

  /**
   * Get the previous game's screen
   * @return previous screen
   */
  public ScreenType getCurrentScreenType() {
    return currentScreenType;
  }

  @Override
  public void dispose() {
    logger.debug("Disposing of current screen");
    getScreen().dispose();
  }

  /**
   * Create a new screen of the provided type.
   * @param screenType screen type
   * @return new screen
   */
  private Screen newScreen(ScreenType screenType) {
    switch (screenType) {
      case MAIN_MENU:
        return new MainMenuScreen(this);
      case MAIN_GAME:
        return new MainGameScreen(this);
      case SETTINGS:
        return new SettingsScreen(this);
      case LOAD_GAME:
        return new LoadGameScreen(this);
      case CUTSCENE:
        return new CutsceneScreen(this, CutsceneType.MORAL_2);
      case MORAL_SCENE_1:
        return new CutsceneScreen(this, CutsceneType.MORAL_1);
      case MORAL_SCENE_2:
        return new CutsceneScreen(this, CutsceneType.MORAL_2);
      case MORAL_SCENE_3:
        return new CutsceneScreen(this, CutsceneType.MORAL_3);
      case MORAL_SCENE_4:
        return new CutsceneScreen(this, CutsceneType.MORAL_4);

      case GOOD_END:
        return new CutsceneScreen(this, CutsceneType.GOOD_END);
      case BAD_END:
        return new CutsceneScreen(this, CutsceneType.BAD_END);
      case LOSE_END:
        return new CutsceneScreen(this, CutsceneType.LOSE);
      default:
        return null;
    }
  }

  private Screen newScreen(ScreenType screenType, CutsceneType cutsceneType) {
    if (screenType != ScreenType.CUTSCENE) {
      return null;
    }
    switch (cutsceneType) {
      case BACK_STORY:
        return new CutsceneScreen(this, CutsceneType.BACK_STORY);
      case DAY_2:
        return new CutsceneScreen(this, CutsceneType.DAY_2);
      case DAY_3:
        return new CutsceneScreen(this, CutsceneType.DAY_3);
      case DAY_4:
        return new CutsceneScreen(this, CutsceneType.DAY_4);
      case GOOD_END:
        return new CutsceneScreen(this, CutsceneType.GOOD_END);
      case BAD_END:
        return new CutsceneScreen(this, CutsceneType.BAD_END);
      case LOSE:
        return new CutsceneScreen(this, CutsceneType.LOSE);

      case MORAL_1:
        return new CutsceneScreen(this, CutsceneType.MORAL_1);

      default:
        return null;
    }
  }

  /**
   * Set the current cutscene being displayed.
   * @param cutscene The active cutscene.
   */
  public void setCurrentCutscene(Cutscene cutscene) {
    this.currentCutscene = cutscene;
  }

  /**
   * Get the current cutscene being displayed.
   * @return The active cutscene.
   */
  public Cutscene getCurrentCutscene() {
    return currentCutscene;
  }


  public enum ScreenType {
    MAIN_MENU, MAIN_GAME, SETTINGS, LOAD_GAME, CUTSCENE, GOOD_END, BAD_END, LOSE_END, MORAL_SCENE_1, MORAL_SCENE_2, MORAL_SCENE_3, MORAL_SCENE_4
  }

  public enum CutsceneType {
    BACK_STORY, DAY_2, DAY_3, DAY_4, GOOD_END, BAD_END, LOSE, MORAL_1, MORAL_2, MORAL_3, MORAL_4
  }

  public enum LevelType {
    LEVEL_0, LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5, DONE
  }

  /**
   * Exit the game.
   */
  public void exit() {
    app.exit();
  }
}
