package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.GdxGame;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.maingame.MainGameActions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;

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
	ResourceService resourceService;
	@Mock
	Texture textureMock;
	@Mock Table table;
	@Mock Entity entity;
	MainGameOrderTicketDisplay orderTicketDisplay;
	@Mock
	GdxGame mockGame;
	private MainGameActions mainGameActions;
	MockedStatic<MainGameOrderTicketDisplay> mockedStatic;
	ArrayList<Table> tableArrayList;

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

		tableArrayList = new ArrayList<>();
		tableArrayList.add(table);
		when(entity.getComponent(MainGameOrderTicketDisplay.class)).thenReturn(orderTicketDisplay);
		mockedStatic.when(MainGameOrderTicketDisplay::getTableArrayList).thenReturn(tableArrayList);

		mainGameActions = new MainGameActions(mockGame, entity);
	}

	/**
	 * Cleans up after each test by clearing the table array list.
	 */
	@AfterEach
	void tearDown() {
		mockedStatic.close();
	}

	/**
	 * Test should change game screen
	 */
	@Test
	void testOnExit() {
		mainGameActions.onExit();
		verify(mockGame, times(1)).setScreen(GdxGame.ScreenType.MAIN_MENU);
	}

	/**
	 * Test should create order when table is below the limit
	 */
	@Test
	void testOnCreateOrderBelowLimit() {
		mainGameActions.onCreateOrder(null);
		verify(orderTicketDisplay, times(1)).setRecipe(anyString());
		verify(orderTicketDisplay, times(1)).addActors();
	}

	/**
	 * Test should not create the order when the table size is full
	 */
	@Test
	void testOnCreateOrderAboveLimit() {
		int limit = 8;
		for (int i = 0; i < limit; i++) {
			tableArrayList.add(table);
		}

		mainGameActions.onCreateOrder(null);
		verify(orderTicketDisplay, never()).setRecipe(anyString());
		verify(orderTicketDisplay, never()).addActors();
	}

	/**
	 * Test should create order when preferredRecipe is empty
	 */
	@Test
	void testOnCreateOrderEmptyRecipe() {
		mainGameActions.onCreateOrder("");
		verify(orderTicketDisplay, times(1)).setRecipe(anyString());
		verify(orderTicketDisplay, times(1)).addActors();
	}

	/**
	 * Test should set Acai Bowl recipe
	 */
	@Test
	void testOnCreateAcai() {
		mainGameActions.onCreateAcai();
		verify(orderTicketDisplay, times(1)).setRecipe(RecipeNameEnums.ACAI_BOWL.getRecipeName());
		verify(orderTicketDisplay, times(1)).addActors();
	}

	/**
	 * Test should set Banana Split recipe
	 */
	@Test
	void testOnCreateBanana() {
		mainGameActions.onCreateBanana();
		verify(orderTicketDisplay, times(1)).setRecipe(RecipeNameEnums.BANANA_SPLIT.getRecipeName());
		verify(orderTicketDisplay, times(1)).addActors();
	}

	/**
	 * Test should set Salad recipe
	 */
	@Test
	void testOnCreateSalad() {
		mainGameActions.onCreateSalad();
		verify(orderTicketDisplay, times(1)).setRecipe(RecipeNameEnums.SALAD.getRecipeName());
		verify(orderTicketDisplay, times(1)).addActors();
	}

	/**
	 * Test should set Steak recipe
	 */
	@Test
	void testOnCreateSteak() {
		mainGameActions.onCreateSteak();
		verify(orderTicketDisplay, times(1)).setRecipe(RecipeNameEnums.STEAK_MEAL.getRecipeName());
		verify(orderTicketDisplay, times(1)).addActors();
	}

	/**
	 * Test should set Fruit Salad recipe
	 */
	@Test
	void testOnCreateFruitSalad() {
		mainGameActions.onCreateFruitSalad();
		verify(orderTicketDisplay, times(1)).setRecipe(RecipeNameEnums.FRUIT_SALAD.getRecipeName());
		verify(orderTicketDisplay, times(1)).addActors();
	}
}