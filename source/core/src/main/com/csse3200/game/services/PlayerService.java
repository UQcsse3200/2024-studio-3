package com.csse3200.game.services;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;

/**
 * Player trigger events
 */
public class PlayerService {
	private final EventHandler playerEventHandler;
	private Entity player;

	/**
	 * Creates new PlayerService instance
	 * Initialises the EventHandler to manage player events
	 */
	public PlayerService() {
		playerEventHandler = new EventHandler();
	}

	/**
	 * Gets the PlayerService EventHandler
	 * @return the event handler that manages player events
	 */
	public EventHandler getEvents() {
		return playerEventHandler;
	}

	/**
   * Register the player entity.
   * @param playerEntity The player entity to register.
   */
    public void registerPlayer(Entity playerEntity) {
      this.player = playerEntity;
    }

    /**
     * Get the player entity.
     * @return the player entity.
     */
    public Entity getPlayer() {
      return this.player;
  }
}
