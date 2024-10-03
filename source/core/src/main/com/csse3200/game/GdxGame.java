package com.csse3200.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.csse3200.game.components.cutscenes.BackstoryCutscene;
import com.csse3200.game.components.cutscenes.Cutscene;
import com.csse3200.game.screens.CutsceneScreen;
import com.csse3200.game.files.UserSettings;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.screens.MainMenuScreen;
import com.csse3200.game.screens.SettingsScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.Gdx.app;
import com.csse3200.game.screens.LoadGameScreen;
/**
 * Entry point of the non-platform-specific game logic. Controls which screen is currently running.
 * The current screen triggers transitions to other screens. This works similarly to a finite state
 * machine (See the State Pattern).
 */
public class GdxGame extends Game {
  private static final Logger logger = LoggerFactory.getLogger(GdxGame.class);
  private Screen previousScreen;
  private Texture backgroundTexture;
  private SpriteBatch batch;
  private Cutscene currentCutscene;


  @Override
  public void create() {
    logger.info("Creating game");
    loadSettings();

    // Initially set to the main menu screen
    // I want to set the background ot an image

    Gdx.gl.glClearColor(234f/255f, 221/255f, 202/255f, 1);

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
    logger.info("Setting game screen to {}", screenType);
    Screen currentScreen = getScreen();

    previousScreen = currentScreen;  // Save the current screen before changing

    if (currentScreen != null) {
      currentScreen.dispose();
    }

    setScreen(newScreen(screenType));
  }

  /**
   * Get the previous game's screen
   * @return previous screen
   */
  public Screen getPreviousScreen() {
    return previousScreen;
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
        return new CutsceneScreen(this, 0);
      case GOOD_END:
        return new CutsceneScreen(this, 1);
      case BAD_END:
        return new CutsceneScreen(this, 2);
      case LOSE_END:
        return new CutsceneScreen(this, 3);
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
    MAIN_MENU, MAIN_GAME, SETTINGS, LOAD_GAME, CUTSCENE, GOOD_END, BAD_END, LOSE_END
  }

  /**
   * Exit the game.
   */
  public void exit() {
    app.exit();
  }
}
