package com.csse3200.game.services;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerService {
	private final EventHandler playerEventHandler;

	private Entity player;
	private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

	public PlayerService() {
		playerEventHandler = new EventHandler();
	}

	public EventHandler getEvents() {
		return playerEventHandler;
	}


//	public void setPlayer(Entity player) {
//		logger.warn("Setting player entity: {}", player);
//		this.player = player;
//	}
//
//	public Entity getPlayer() {
//		logger.warn("getting player: {}", player);
//
//		return player;
//	}
//
//	public <T extends Component> T getPlayerComponent(Class<T> componentClass) {
//		logger.warn("getting player component: {}", componentClass);
//
//		if (player != null) {
//			return player.getComponent(componentClass);
//		}
//		return null;
//	}
}
