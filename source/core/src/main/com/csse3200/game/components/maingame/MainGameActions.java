package com.csse3200.game.components.maingame;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

/**
 * This class listens to events relevant to the Main Game Screen and does something when one of the
 * events is triggered.
 */
public class MainGameActions extends Component {
	private static final Logger logger = LoggerFactory.getLogger(MainGameActions.class);
	private GdxGame game;
	private Entity ui;
	private MainGameOrderTicketDisplay docketDisplayer;
	private OrderActions orderActions;
	String[] recipeNames = {"acaiBowl", "salad", "fruitSalad", "steakMeal", "bananaSplit"};

	/**
	 * Constructs a MainGameActions instance
	 *
	 * @param game the game
	 */
	public MainGameActions(GdxGame game) {
		this.game = game;
	}

	/**
	 * Initializes the component, sets up event listeners, and creates UI entities.
	 */
	@Override
	public void create() {
		if (ui == null) {
			ui = new Entity();
			String randomRecipe = recipeNames[new Random().nextInt(recipeNames.length)];
			docketDisplayer = new MainGameOrderTicketDisplay(randomRecipe);
			docketDisplayer.setStage(ServiceLocator.getRenderService().getStage());
			orderActions = new OrderActions(game);

			ui.addComponent(docketDisplayer);
			entity.getEvents().addListener("exit", this::onExit);
			entity.getEvents().addListener("createOrder", this::onCreateOrder);
			ServiceLocator.getEntityService().register(ui);
		}
	}

	/**
	 * Swaps to the Main Menu screen.
	 */
	private void onExit() {
		logger.info("Exiting main game screen");
		game.setScreen(GdxGame.ScreenType.MAIN_MENU);
	}

	/**
	 * Create Order Docket
	 */
	private void onCreateOrder() {
//		String randomRecipe = recipeNames[new Random().nextInt(recipeNames.length)];
//		docketDisplayer = new MainGameOrderTicketDisplay(randomRecipe);
//		docketDisplayer.setStage(ServiceLocator.getRenderService().getStage());
//		ui.addComponent(docketDisplayer);
		docketDisplayer.addActors();
//		logger.info("Order created with recipe: {}", randomRecipe);
	}

	/**
	 * Handles the event when an order is done.
	 * Unregisters and disposes of the UI entity.
	 */
	private void onOrderDone() {
		ServiceLocator.getEntityService().unregister(ui);
		ui.dispose();
		ui = null;
		logger.info("Order entity disposed");
	}
}