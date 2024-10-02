package com.csse3200.game.services;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.cutscenes.Cutscene;
import com.csse3200.game.components.cutscenes.CutsceneTextDisplay;
import com.csse3200.game.screens.CutsceneScreen;
import com.csse3200.game.components.mainmenu.MainMenuDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csse3200.game.areas.GameArea;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.input.InputService;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.screens.MainGameScreen;


/**
 * A simplified implementation of the Service Locator pattern:
 * https://martinfowler.com/articles/injection.html#UsingAServiceLocator
 *
 * <p>Allows global access to a few core game services.
 * Warning: global access is a trap and should be used <i>extremely</i> sparingly.
 * Read the wiki for details (https://github.com/UQcsse3200/game-engine/wiki/Service-Locator).
 */
public class ServiceLocator {
  private static final Logger logger = LoggerFactory.getLogger(ServiceLocator.class);
  private static EntityService entityService;
  private static RenderService renderService;
  private static PhysicsService physicsService;
  private static GameTime timeSource;
  private static InputService inputService;
  private static PlayerService playerService;
  private static GameArea gameArea;
  private static MainGameScreen gameScreen;
  private static CutsceneScreen cutsceneScreen;
  private static Cutscene currentCutscene;
  private static MainMenuDisplay mainMenuDisplay;
  private static GdxGame gameInstance;
  private static CutsceneTextDisplay cutsceneTextDisplay;


  private static ResourceService resourceService;

  private static TicketDetails ticketDetails;
  //Me new stuff :)


  private static DocketService docketService;
  private static LevelService levelService;

  private static DayNightService dayNightService;
  private static OrderActions orderActions; //new

  // New services (e.g. CustomerMovementService, DialogueService)
  private static CustomerMovementService customerMovementService;

  public static EntityService getEntityService() {
    return entityService;
  }

  public static RenderService getRenderService() {
    return renderService;
  }

  public static PhysicsService getPhysicsService() {
    return physicsService;
  }

  public static GameTime getTimeSource() {
    return timeSource;
  }

  public static InputService getInputService() {
    return inputService;
  }

  public static PlayerService getPlayerService() {
    return playerService;
  }

  public static ResourceService getResourceService() {
    return resourceService;
  }

  public static DocketService getDocketService() {
    return docketService;
  }


  public static TicketDetails getTicketDetails() {
    return ticketDetails;
  }

  public static DayNightService getDayNightService() { //new
    return dayNightService;
  }


  public static OrderActions getOrderActions() {
    return orderActions;

  }

  public static LevelService getLevelService() {
    return levelService;
  }

  public static GameArea getGameArea() {
    return gameArea;
  }

  public static MainGameScreen getGameScreen() {
    return gameScreen;
  }

  public static CutsceneScreen getCutsceneScreen() {
    return cutsceneScreen;
  }

  public static Cutscene getCurrentCutscene() {
    return currentCutscene;
  }

  // New getters for additional services
  public static CustomerMovementService getCustomerMovementService() {
    return customerMovementService;
  }

  public static void setCurrentCutscene(Cutscene cutscene) {
    currentCutscene = cutscene;
  }

  // Register methods for services
  public static void registerEntityService(EntityService service) {
    entityService = service;
  }

  public static void registerDocketService(DocketService service) {
    if (docketService != null) {
      logger.warn("Docket service is being overwritten!");
    }
    logger.debug("Registering docket service {}", service);
    docketService = service;
  }

  public static void registerRenderService(RenderService service) {
    logger.debug("Registering render service {}", service);
    renderService = service;
  }

  public static void registerPhysicsService(PhysicsService service) {
    logger.debug("Registering physics service {}", service);
    physicsService = service;
  }

  public static void registerTimeSource(GameTime source) {
    logger.debug("Registering time source {}", source);
    timeSource = source;
  }

  public static void registerInputService(InputService service) {
    if (inputService != null) {
      logger.warn("Input service is being overwritten!");
    }
    logger.debug("Registering input service {}", service);
    inputService = service;
  }

  /**
   * Register player service
   *
   * @param service PlayerService
   */
  public static void registerPlayerService(PlayerService service) {
    if (playerService != null) {
      logger.warn("Player service is being overwritten!");
    }
    logger.debug("Registering player service {}", service);
    playerService = service;
  }

  public static void registerResourceService(ResourceService source) {
    logger.debug("Registering resource service {}", source);
    resourceService = source;
  }


  public static void registerDayNightService(DayNightService service) { //new
    logger.debug("Registering day-night service: {}", service);
    dayNightService = service;
  }


  public static void registerOrderActions(OrderActions source) {
    logger.debug("Registering order action {}", source);
    orderActions = source;
  }


  public static void registerLevelService(LevelService source) {
    if (levelService == null) {
      levelService = source;
    } else {
      logger.warn("Level service is already assigned, ignoring register");
    }
  }

  public static void registerTicketDetails(TicketDetails source) {
    logger.debug("Registering resource service {}", source);
    ticketDetails = source;
  }


  public static void registerGameArea(GameArea game) {
    if (gameArea != null) {
      logger.warn("Game is already registered!");
    } else {
      logger.debug("Registering game");
      gameArea = game;
    }

  }


  public static void registerMainMenuDisplay(MainMenuDisplay display) {
    mainMenuDisplay = display;
  }

  public static void registerGameScreen(MainGameScreen game) {
    if (gameScreen != null) {
      logger.warn("Game Screen is already registered!");
    } else {
      logger.debug("Registering game screen");
      gameScreen = game;
    }
  }

  public static void registerCutsceneScreen(CutsceneScreen scene) {
    if (cutsceneScreen != null) {
      logger.warn("Game Screen is already registered!");
    } else {
      logger.debug("Registering cutscene screen");
      cutsceneScreen = scene;
    }
  }

  // New register methods for additional services
  public static void registerCustomerMovementService(CustomerMovementService service) {
    if (customerMovementService != null) {
      logger.warn("CustomerMovementService is being overwritten!");
    }
    logger.debug("Registering customer movement service {}", service);
    customerMovementService = service;
  }


  // Clear all services
  public static void clear() {
    entityService = null;
    renderService = null;
    physicsService = null;
    timeSource = null;
    inputService = null;
    resourceService = null;
    docketService = null;
    orderActions = null;
    playerService = null;
    gameArea = null;
    gameScreen = null;
    customerMovementService = null;
    dayNightService = null;
  }

  private ServiceLocator() {
    throw new IllegalStateException("Instantiating static util class");
  }


  public static MainMenuDisplay getMainMenuDisplay() {
    return ServiceLocator.mainMenuDisplay;
  }

  public static GdxGame getGdxGame() {
    if (gameInstance == null) {
      throw new IllegalStateException("GdxGame instance has not been set");
    }
    return gameInstance;
  }

  public static void setGdxGame(GdxGame game) {
    if (gameInstance != null) {
      logger.warn("Overwriting an existing GdxGame instance.");
    }
    gameInstance = game;
    logger.debug("GdxGame instance has been set.");
  }


  /**
   * Gets the current CutsceneTextDisplay instance.
   *
   * @return the CutsceneTextDisplay instance, or null if not initialized.
   */
  public static CutsceneTextDisplay getCutsceneTextDisplay() {
    return cutsceneTextDisplay;
  }

  /**
   * Sets the CutsceneTextDisplay instance.
   *
   * @param display The CutsceneTextDisplay instance to set.
   */
  public static void setCutsceneTextDisplay(CutsceneTextDisplay display) {
    cutsceneTextDisplay = display;
  }
}



