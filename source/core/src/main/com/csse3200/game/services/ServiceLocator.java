package com.csse3200.game.services;

import com.csse3200.game.components.ordersystem.Docket;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.input.InputService;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private static ResourceService resourceService;

  private static TicketDetails ticketDetails;
  //Me new stuff :)

  private static DocketService docketService;
  private static LevelService levelService;

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

  public static LevelService getLevelService(){
    return levelService;

  }

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



  public static void registerLevelService(LevelService source) {
    if (levelService == null) {
      levelService = source;
    } else {
      logger.warn("Level service is already assigned, ignoring register");
    }
  }
  public static void registerTicketDetails(TicketDetails source){
    logger.debug("Registering resource service {}", source);
    ticketDetails = source;
  }

  public static void clear() {
    entityService = null;
    renderService = null;
    physicsService = null;
    timeSource = null;
    inputService = null;
    resourceService = null;
    docketService = null;
    playerService = null;
    ticketDetails = null;
  }

  private ServiceLocator() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
