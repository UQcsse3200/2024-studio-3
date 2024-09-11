package com.csse3200.game.entities;

import com.badlogic.gdx.utils.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.events.EventHandler;

/**
 * Provides a global access point for entities to register themselves. This allows for iterating
 * over entities to perform updates each loop. All game entities should be registered here.
 *
 * Avoid adding additional state here! Global access is often the easy but incorrect answer to
 * sharing data.
 */
public class EntityService {
  private static final Logger logger = LoggerFactory.getLogger(EntityService.class);
  private static final int INITIAL_CAPACITY = 16;

  private final Array<Entity> entities = new Array<>(false, INITIAL_CAPACITY);
  private final EventHandler entityEventHandler;

  private Entity moralScreen;

  private Entity endDayScreen;

  public EntityService() {
    entityEventHandler = new EventHandler();
  }


  public Array<Entity> getEntities() { return entities; }

  /**
   * Register a new entity with the entity service. The entity will be created and start updating.
   * @param entity new entity.
   */
  public void register(Entity entity) {
    logger.debug("Registering {} in entity service", entity);
    entities.add(entity);
    entity.create();
  }

  /**
   * Unregister an entity with the entity service. The entity will be removed and stop updating.
   * @param entity entity to be removed.
   */
  public void unregister(Entity entity) {
    logger.debug("Unregistering {} in entity service", entity);
    entities.removeValue(entity, true);
  }

  public EventHandler getEvents() {
    return entityEventHandler;
  }

  /**
   * Update all registered entities. Should only be called from the main game loop.
   */
  public void update() {
    for (Entity entity : entities) {
      entity.earlyUpdate();
      entity.update();
    }
  }

  /**
   * Dispose all entities.
   */
  public void dispose() {
    for (Entity entity : entities) {
      entity.dispose();
    }
  }

  public Entity getMoralScreen() {
      return this.moralScreen;
  }


  public void registerMoral(Entity moralScreen) {
    register(moralScreen);
    this.moralScreen = moralScreen;
  }

  public void unregisterMoral(){
    unregister(moralScreen);
    this.moralScreen = null;

  }

  public void registerEndDay(Entity endDayScreen) {
    register(endDayScreen);
    this.endDayScreen = endDayScreen;
  }

  public void unregisterEndDay(){
    unregister(endDayScreen);
    this.endDayScreen = null;
  }

  public Entity getEndDayScreen() {
    return this.endDayScreen;
  }
}
