package com.csse3200.game.services;
import com.csse3200.game.events.EventHandler;

/**
 * Player trigger events
 */
public class PlayerService {
	private final EventHandler playerEventHandler;

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
}
