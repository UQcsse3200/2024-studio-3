package com.csse3200.game.components.maingame;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.input.InputService;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Tests for MainGameActions class
 */
@ExtendWith(GameExtension.class)
public class MainGameActionsTest {
	private MainGameActions mainGameActions;
	private Entity entity;
	private EntityService entityService;

	/**
	 * Set up
	 */
	@BeforeEach
	void setUp() {
		entityService = new EntityService();
		ServiceLocator.clear();
		ServiceLocator.registerEntityService(entityService);
		entity = new Entity();
		entity.addComponent(new MainGameActions(new GdxGame()));
		mainGameActions = entity.getComponent(MainGameActions.class);
	}


}
