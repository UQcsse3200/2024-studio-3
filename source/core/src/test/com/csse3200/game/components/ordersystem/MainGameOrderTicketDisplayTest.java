package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ServiceLocator;
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

import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
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
	MainGameOrderTicketDisplay orderTicketDisplay;
	@Mock CombatStatsComponent combatStatsComponent;
	private static final Logger logger = LoggerFactory.getLogger(MainGameOrderTicketDisplayTest.class);

	/**
	 * Sets up the environment before each test by initializing services and MainGameOrderTicketDisplay instance
	 */
	@BeforeEach
	void setUp() {
		ServiceLocator.registerRenderService(renderService);
		ServiceLocator.registerDocketService(docketService);
		ServiceLocator.registerPlayerService(playerService);

		when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
		when(ServiceLocator.getRenderService().getStage().getViewport()).thenReturn(viewport);
		lenient().when(ServiceLocator.getRenderService().getStage().getViewport().getCamera()).thenReturn(camera);
		when(ServiceLocator.getDocketService().getEvents()).thenReturn(eventHandler);
		when(ServiceLocator.getPlayerService().getEvents()).thenReturn(eventHandler2);

		orderTicketDisplay = new MainGameOrderTicketDisplay();
//        String[] recipeNames = {"acaiBowl", "salad", "fruitSalad", "steakMeal", "bananaSplit"};
//        String randomRecipe = recipeNames[new Random().nextInt(recipeNames.length)];
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
		MainGameOrderTicketDisplay.getTableArrayList().clear();
	}

	/**
	 * Tests that create() are initializes UI components correctly
	 */
	@Test
	public void testCreateInitializesComponents() {
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
	public void testAddActorsAddsUIComponents() {
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
		assertEquals("Timer: 30000", countdownLabel.getText().toString());
	}

	@Test
	void shouldAddTablesToArrayAndRemove() {
		orderTicketDisplay.addActors();
		orderTicketDisplay.addActors();

		assertEquals(2, (MainGameOrderTicketDisplay.getTableArrayList()).size());
		orderTicketDisplay.dispose();
		assertEquals(0, (MainGameOrderTicketDisplay.getTableArrayList()).size());
	}

	@Test
	void testEnlargementOfLastDocket() {
		for (int i = 0; i < 5; i++) {
			orderTicketDisplay.addActors();
		}
		orderTicketDisplay.updateDocketSizes();
		Table lastTable = MainGameOrderTicketDisplay.getTableArrayList().get(MainGameOrderTicketDisplay.getTableArrayList().size() - 1);
		assertEquals(170f * (orderTicketDisplay.getViewportWidth()/1920f), lastTable.getWidth(), 0.1f);
		assertEquals(200f * (orderTicketDisplay.getViewportHeight()/1080f), lastTable.getHeight(), 0.1f);

		float expectedX = orderTicketDisplay.getViewportWidth() - 320f;
		float expectedY = 900f * (orderTicketDisplay.getViewportHeight()/1080f);

		assertEquals(expectedX, lastTable.getX(), 0.1f);
		assertEquals(expectedY, lastTable.getY(), 0.1f);

	}

	/**
	 * tests countdown decreases correctly
	 */
	@Test
	public void testUpdateCountdownDecreasesCorrectly() {
		orderTicketDisplay.create();
		orderTicketDisplay.addActors();
		long startTime = MainGameOrderTicketDisplay.getStartTimeArrayList().get(0);
		long elapsedTime = TimeUtils.timeSinceMillis(startTime);
		orderTicketDisplay.update();
		assertEquals(
		  "Timer: " +
			((orderTicketDisplay.getTimer() - elapsedTime)/1000),
		  MainGameOrderTicketDisplay.getCountdownLabelArrayList().get(0).getText().toString(), "Timer should orderTicketDisplay correct countdown");
	}

	@Test
	public void testDisposeClearsComponents() {
		orderTicketDisplay.addActors();
		stage.dispose();

		verify(stage).dispose();
	}

	/**
	 * test stage disposes
	 */
	@Test
	void testStageDispose() {
		orderTicketDisplay.addActors();
		combatStatsComponent = mock(CombatStatsComponent.class);
		orderTicketDisplay.combatStatsComponent = combatStatsComponent;

		Assertions.assertNotNull(MainGameOrderTicketDisplay.getTableArrayList(), "Table ArrayList should not be null");
		assertFalse(MainGameOrderTicketDisplay.getTableArrayList().isEmpty(), "Table ArrayList should not be empty");
		assertNotNull(MainGameOrderTicketDisplay.getBackgroundArrayList(), "Background ArrayList should not be null");
		assertFalse(MainGameOrderTicketDisplay.getBackgroundArrayList().isEmpty(), "Background ArrayList should not be empty");

		Table table = MainGameOrderTicketDisplay.getTableArrayList().get(0);
		Docket background = MainGameOrderTicketDisplay.getBackgroundArrayList().get(0);

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
	 * test stage sets
	 */
	@Test
	void testSetStage() {
		orderTicketDisplay.setStage(stage);
		when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
		assertEquals(ServiceLocator.getRenderService().getStage(), stage);
	}


	@Test
	void testEnlargementOfSingleDocket() {
		orderTicketDisplay.addActors();
		orderTicketDisplay.updateDocketSizes();
		Table singleTable = MainGameOrderTicketDisplay.getTableArrayList().get(0);

		assertEquals(
		  170f * (orderTicketDisplay.getViewportWidth()/1920f), singleTable.getWidth(),
		  0.1f, "Docket width is incorrect.");
		assertEquals(200f * (orderTicketDisplay.getViewportHeight()/1080f), singleTable.getHeight(),
		  0.1f, "Docket height is incorrect.");

		float expectedX = orderTicketDisplay.getViewportWidth() - 320f; //orderTicketDisplay.getViewportWidth() - 260f
		float expectedY = 900f * (orderTicketDisplay.getViewportHeight()/1080f);

		assertEquals(expectedX, singleTable.getX(), 0.1f, "Docket X position is incorrect.");
		assertEquals(expectedY, singleTable.getY(), 0.1f, "Docket Y position is incorrect.");
	}

	@Test
	void testNotEnlargedDocketSizes() {
		for (int i = 0; i < 5; i++) {
			orderTicketDisplay.addActors();
		}

		orderTicketDisplay.updateDocketSizes();
		for (int i = 0; i < MainGameOrderTicketDisplay.getTableArrayList().size() - 1; i++) {
			Table table = MainGameOrderTicketDisplay.getTableArrayList().get(i);
			assertEquals(120f * (orderTicketDisplay.getViewportWidth()/1920f), table.getWidth(), 0.1f);
			assertEquals(150f * (orderTicketDisplay.getViewportHeight()/1080f), table.getHeight(), 0.1f);
		}
	}

	@Test
	void testGetZIndex() {
		assertEquals(2f, orderTicketDisplay.getZIndex());
	}

}


