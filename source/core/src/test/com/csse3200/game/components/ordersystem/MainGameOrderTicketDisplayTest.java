package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the MainGameOrderTicketDisplay class.
 */
@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class MainGameOrderTicketDisplayTest {
	@Mock RenderService renderService;
	@Spy OrthographicCamera camera;
	@Mock Stage stage;
	@Mock Viewport viewport;
	@Mock DocketService docketService;
	@Mock PlayerService playerService;
	@Mock EventHandler eventHandler;
	@Mock EventHandler eventHandler2;
	@Mock ResourceService resourceService;
	@Mock Texture textureMock;
	MainGameOrderTicketDisplay orderTicketDisplay;
	@Mock CombatStatsComponent combatStatsComponent;
	@Mock TicketDetails ticketDetails; // Mocked TicketDetails
    private MockedStatic<ServiceLocator> serviceLocatorMock;
	RandomComboService randomComboService;
	private static final Logger logger = LoggerFactory.getLogger(MainGameOrderTicketDisplayTest.class);

	/**
	 * Sets up the environment before each test by initializing services and MainGameOrderTicketDisplay instance
	 */
	@BeforeEach
	void setUp() {
		ServiceLocator.registerRenderService(renderService);
		ServiceLocator.registerDocketService(docketService);
		ServiceLocator.registerPlayerService(playerService);
		resourceService = mock(ResourceService.class);
		ServiceLocator.registerResourceService(resourceService);
		textureMock = mock(Texture.class);
		randomComboService = new RandomComboService(new EventHandler());

		serviceLocatorMock = Mockito.mockStatic(ServiceLocator.class);

		serviceLocatorMock.when(ServiceLocator::getRenderService).thenReturn(renderService);
        serviceLocatorMock.when(ServiceLocator::getDocketService).thenReturn(docketService);
        serviceLocatorMock.when(ServiceLocator::getPlayerService).thenReturn(playerService);
        serviceLocatorMock.when(ServiceLocator::getResourceService).thenReturn(resourceService);
        serviceLocatorMock.when(ServiceLocator::getTicketDetails).thenReturn(ticketDetails);
		serviceLocatorMock.when(ServiceLocator::getRandomComboService).thenReturn(randomComboService);

		resourceService = mock(ResourceService.class);
        textureMock = mock(Texture.class);

        lenient().when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(textureMock);

		when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
		when(ServiceLocator.getRenderService().getStage().getViewport()).thenReturn(viewport);
		lenient().when(ServiceLocator.getRenderService().getStage().getViewport().getCamera()).thenReturn(camera);
		when(ServiceLocator.getDocketService().getEvents()).thenReturn(eventHandler);
		when(ServiceLocator.getPlayerService().getEvents()).thenReturn(eventHandler2);

		orderTicketDisplay = new MainGameOrderTicketDisplay(renderService, playerService);
		orderTicketDisplay.setRecipe("acaiBowl");
		Entity entity = new Entity();
		entity.addComponent(orderTicketDisplay);
		orderTicketDisplay.create();
	}

	/**
	 * Cleans up after each test by clearing the table array list.
	 */
	@AfterEach
	void tearDown() {
		serviceLocatorMock.close();
		MainGameOrderTicketDisplay.getTableArrayList().clear();
	}

	/**
	 * Tests that create() are initializes UI components correctly
	 */
	@Test
	void testCreateInitializesComponents() {
		orderTicketDisplay.create();

		Assertions.assertNotNull(
		  MainGameOrderTicketDisplay.getTableArrayList(), "Table should be initialized");
		assertNotNull(
		  MainGameOrderTicketDisplay.getCountdownLabelArrayList(), "Countdown label should be initialized");
		assertNotNull(
		  MainGameOrderTicketDisplay.getStartTimeArrayList(), "Start time should be set");
	}

	/**
	 * test addActors() creates table
	 */
	@Test
	void testAddActorsAddsUIComponents() {
		orderTicketDisplay.addActors();
		verify(stage).addActor(any(Table.class));
	}

	/**
	 * test addActors() timer label
	 */
	@Test
	void testAddActors() {
		orderTicketDisplay.addActors();
		verify(stage).addActor(any(Table.class));

		assertEquals(1, MainGameOrderTicketDisplay.getTableArrayList().size());

		Table table = MainGameOrderTicketDisplay.getTableArrayList().getFirst();
		Assertions.assertNotNull(table);

		Label countdownLabel = MainGameOrderTicketDisplay.getCountdownLabelArrayList().getFirst();
		Assertions.assertNotNull(countdownLabel);
	}

	/**
	 * Test should reset Order Nmber to Zero
	 */
	@Test
	void shouldResetOrderNumbtoZero() {
		orderTicketDisplay.addActors();
		MainGameOrderTicketDisplay.resetOrderNumb();
		assertEquals(0, (MainGameOrderTicketDisplay.getOrderNumb()));
	}

	/**
	 * Test should add and remove tables to MainGameOrderTicketDisplay array
	 */
	@Test
	void shouldAddTablesToArrayAndRemove() {
		orderTicketDisplay.addActors();
		orderTicketDisplay.addActors();

		orderTicketDisplay.dispose();
		assertEquals(0, orderTicketDisplay.getTableArrayList().size());
	}

	/**
	 * Test docket sizes are sized correctly
	 */
	@Test
	void testDocketSizesNormalAndEnlarged() {
		orderTicketDisplay.addActors();
		orderTicketDisplay.addActors();
		orderTicketDisplay.addActors();

		assertEquals(3, MainGameOrderTicketDisplay.getTableArrayList().size());

		orderTicketDisplay.updateDocketSizes();

		float normalDocketWidth = 120f * (orderTicketDisplay.getViewportWidth() / 1920f);
		float normalDocketHeight = 150f * (orderTicketDisplay.getViewportHeight() / 1080f);

		float enlargedDocketWidth = 170f * (orderTicketDisplay.getViewportWidth() / 1920f);
		float enlargedDocketHeight = 200f * (orderTicketDisplay.getViewportHeight() / 1080f);

		for (int i = 0; i < MainGameOrderTicketDisplay.getTableArrayList().size() - 1; i++) {
			Table table = MainGameOrderTicketDisplay.getTableArrayList().get(i);
			assertEquals(normalDocketWidth, table.getWidth(), 0.1f);
			assertEquals(normalDocketHeight, table.getHeight(), 0.1f);
		}

		Table lastTable = MainGameOrderTicketDisplay.getTableArrayList().getLast();
		assertEquals(enlargedDocketWidth, lastTable.getWidth(), 0.1f);
		assertEquals(enlargedDocketHeight, lastTable.getHeight(), 0.1f);
	}

	/**
	 * Test should dispose the stage
	 */
	@Test
	public void testDisposeClearsComponents() {
		orderTicketDisplay.addActors();
		stage.dispose();

		verify(stage).dispose();
	}

	/**
	 * Test should dispose of stage
	 */
	@Test
	void testStageDispose() {
		orderTicketDisplay.addActors();
		combatStatsComponent = mock(CombatStatsComponent.class);
		orderTicketDisplay.setCombatStatsComponent(combatStatsComponent);

		Assertions.assertNotNull(MainGameOrderTicketDisplay.getTableArrayList(), "Table ArrayList should not be null");
		assertFalse(MainGameOrderTicketDisplay.getTableArrayList().isEmpty(), "Table ArrayList should not be empty");
		assertNotNull(MainGameOrderTicketDisplay.getBackgroundArrayList(), "Background ArrayList should not be null");
		assertFalse(MainGameOrderTicketDisplay.getBackgroundArrayList().isEmpty(), "Background ArrayList should not be empty");

		Table table = MainGameOrderTicketDisplay.getTableArrayList().getFirst();
		Docket background = MainGameOrderTicketDisplay.getBackgroundArrayList().getFirst();

		Assertions.assertNotNull(table, "Table should not be null");
		Assertions.assertNotNull(background, "Background should not be null");

		boolean hasChildrenBeforeDispose = !table.getChildren().isEmpty();

		logger.info("Table: {}", table);
		logger.info("Background: {}", background);

		orderTicketDisplay.stageDispose(background, table, 0);

		assertTrue(table.getChildren().isEmpty(), "Table should be cleared of children.");
		assertTrue(hasChildrenBeforeDispose, "Table should have had children before dispose.");
	}

	/**
	 * Test should set the stage
	 */
	@Test
	void testSetStage() {
		orderTicketDisplay.setStage(stage);
		when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
		assertEquals(ServiceLocator.getRenderService().getStage(), stage);
	}

	/**
	 * Tests if only one docket is present, then checks if it has enlarged docket dimensions.
	 */
	@Test
	void testDocketEnlargement() {
		// Add a new docket
		orderTicketDisplay.addActors();

		orderTicketDisplay.updateDocketSizes();

		Table lastDocketTable = MainGameOrderTicketDisplay.getTableArrayList().getLast();

		float lastDocketWidth = lastDocketTable.getWidth();
		float lastDocketHeight = lastDocketTable.getHeight();

		float expectedEnlargedWidth = 1280f * 0.08f * MainGameOrderTicketDisplay.getScalingFactor(
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()) * 1.7f;
		float expectedEnlargedHeight = 800f * 0.25f * MainGameOrderTicketDisplay.getScalingFactor(
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()) * 1.7f;

		assertTrue(Math.abs(lastDocketWidth - expectedEnlargedWidth) < 1.0f, "Docket width is not as expected");
		assertTrue(Math.abs(lastDocketHeight - expectedEnlargedHeight) < 1.0f, "Docket height is not as expected");
	}

	/**
	 * Tests getting recipe name.
	 */
	@Test
	void testGetRecipeName() {
		Recipe recipe = orderTicketDisplay.getRecipe();

		assertNotNull(recipe, "Recipe should not be null");
		assertEquals("acaiBowl", recipe.getName(), "The recipe name should be 'acaiBowl'");
	}

	/**
	 * Tests getting the current recipe.
	 */
	@Test
	void testGetCurrentRecipeName() {
		String recipeName = orderTicketDisplay.getCurrentRecipeName();
		assertEquals("acaiBowl", recipeName, "The current recipe name should be 'acaiBowl'");
	}

	/**
	 * Tests set paused and its paused timers.
	 */
	@Test
	void testSetPaused() {
		assertFalse(orderTicketDisplay.isPaused());
		orderTicketDisplay.setPaused(true);

		assertTrue(orderTicketDisplay.isPaused());
		assertTrue(orderTicketDisplay.getPauseStartTime() > 0);
		orderTicketDisplay.setPaused(false);

		assertFalse(orderTicketDisplay.isPaused());
		long elapsedTime = System.currentTimeMillis() - orderTicketDisplay.getPauseStartTime();
		assertTrue(orderTicketDisplay.getTotalPausedDuration() >= elapsedTime);
	}

	/**
	 * Tests if ESCAPE key toggles true or false paused values.
	 */
	@Test
	void testTogglePause() {
		assertFalse(orderTicketDisplay.isPaused());

		boolean result = orderTicketDisplay.handleKeyDown(Input.Keys.ESCAPE);

		assertTrue(orderTicketDisplay.isPaused());
		assertTrue(result);

		result = orderTicketDisplay.handleKeyDown(Input.Keys.ESCAPE);

		assertFalse(orderTicketDisplay.isPaused());
		assertTrue(result);
	}

	/**
	 * Helper method to test if a meal is displayed correctly.
	 */
	private void testMealDisplay(String recipeName) {
		orderTicketDisplay.setRecipe(recipeName);
		orderTicketDisplay.create();
		orderTicketDisplay.addActors();

		Table table = MainGameOrderTicketDisplay.getTableArrayList().getFirst();
		assertNotNull(table, "Table should not be null for " + recipeName);
	}

	@Test
	void testSteakMeal() {
		testMealDisplay("steakMeal");
	}

	@Test
	void testSalad() {
		testMealDisplay("salad");
	}

	@Test
	void testFruitSalad() {
		testMealDisplay("fruitSalad");
	}

	@Test
	void testBananaSplit() {
		testMealDisplay("bananaSplit");
	}

	/**
	 * Test should get the Z index
	 */
	@Test
	void testGetZIndex() {
		assertEquals(3f, orderTicketDisplay.getZIndex());
	}
}


