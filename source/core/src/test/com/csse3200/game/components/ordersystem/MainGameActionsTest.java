package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.UIFactory;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.maingame.MainGameActions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the MainGameActionsTest class.
 */
@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class MainGameActionsTest {
	@Mock
	RenderService renderService;
	@Spy
	OrthographicCamera camera;
	@Mock
	Stage stage;
	@Mock
	Viewport viewport;
	@Mock
	DocketService docketService;
	@Mock
	EntityService entityService;
	@Mock
	PlayerService playerService;
	@Mock
	EventHandler eventHandler;
	@Mock
	EventHandler eventHandler2;
	@Mock
	EventHandler eventHandler3;
	@Mock
	ResourceService resourceService;
	@Mock
	Texture textureMock;
	@Mock
	Entity entity;
	MainGameOrderTicketDisplay orderTicketDisplay;
	GdxGame mockGame;
	private static final Logger logger = LoggerFactory.getLogger(MainGameActionsTest.class);
	private MainGameActions mainGameActions;
	MockedStatic<MainGameOrderTicketDisplay> mockedStatic;


	/**
	 * Sets up the environment before each test by initializing services
	 */
	@BeforeEach
	void setUp() {
		ServiceLocator.registerRenderService(renderService);
		ServiceLocator.registerDocketService(docketService);
		ServiceLocator.registerPlayerService(playerService);
		ServiceLocator.registerEntityService(entityService);
		ServiceLocator.registerResourceService(resourceService);

		lenient().when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(textureMock);
		lenient().when(renderService.getStage()).thenReturn(stage);
		lenient().when(renderService.getStage().getViewport()).thenReturn(viewport);
		lenient().when(renderService.getStage().getViewport().getCamera()).thenReturn(camera);
		lenient().when(ServiceLocator.getPlayerService().getEvents()).thenReturn(eventHandler2);

		orderTicketDisplay = mock(MainGameOrderTicketDisplay.class);
		mockedStatic = mockStatic(MainGameOrderTicketDisplay.class);

		ArrayList<Table> mockOrderList = new ArrayList<>();
		mockOrderList.add(mock(Table.class));
		mockedStatic.when(MainGameOrderTicketDisplay::getTableArrayList).thenReturn(mockOrderList);

		mainGameActions = new MainGameActions(mockGame, orderTicketDisplay);
	}

	/**
	 * Cleans up after each test by clearing the table array list.
	 */
	@AfterEach
	void tearDown() {
		mockedStatic.close();
	}


	/**
	 * Test should create order when table is below the limit
	 */
	@Test
	public void testOnCreateOrderBelowLimit() {
		mainGameActions.onCreateOrder("acaiBowl");
		mainGameActions.onCreateOrder("acaiBowl");
		verify(orderTicketDisplay, times(2)).setRecipe("acaiBowl");
		verify(orderTicketDisplay, times(2)).addActors();
	}

	/**
	 * Test should not create the order when the table size is full
	 */
	@Test
	public void testOnCreateOrderAboveLimit() {
		ArrayList<Table> mockOrderList = new ArrayList<>();

		int limit = 8;
		for (int i = 0; i < limit; i++) {
			mockOrderList.add(mock(Table.class));
		}
		mockedStatic.when(MainGameOrderTicketDisplay::getTableArrayList).thenReturn(mockOrderList);
		MainGameActions mainGameActions = new MainGameActions(mock(GdxGame.class), orderTicketDisplay);
		mainGameActions.onCreateOrder("acaiBowl");

		verify(orderTicketDisplay, never()).setRecipe(anyString());
	}
}