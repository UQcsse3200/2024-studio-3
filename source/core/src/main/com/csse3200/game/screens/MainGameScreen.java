package com.csse3200.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.GdxGame;
import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.components.maingame.*;
import com.csse3200.game.components.maingame.MainGameActions;
import com.csse3200.game.components.tutorial.KeybindsButtonDisplay;
import com.csse3200.game.components.upgrades.*;
import com.csse3200.game.components.ordersystem.*;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.LevelFactory;
import com.csse3200.game.entities.factories.NPCFactory;
import com.csse3200.game.entities.factories.RenderFactory;
import com.csse3200.game.entities.factories.UIFactory;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.input.InputDecorator;
import com.csse3200.game.input.InputService;
import com.csse3200.game.physics.PhysicsEngine;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.rendering.Renderer;
import com.csse3200.game.services.*;
import com.csse3200.game.ui.terminal.Terminal;
import com.csse3200.game.ui.terminal.TerminalDisplay;
import com.csse3200.game.components.maingame.MainGameExitDisplay;
import com.csse3200.game.components.gamearea.PerformanceDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.ordersystem.DocketLineDisplay;
import com.csse3200.game.services.GameTime;


/**
 * The game screen containing the main game.
 *
 * <p>Details on libGDX screens: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class MainGameScreen extends ScreenAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
	private static final String[] mainGameTextures = {
			"images/heart.png",
			"images/textbox.png",
			// order system assets
			"images/ordersystem/docket_background.png",
			"images/ordersystem/pin_line2.png",
			"images/ordersystem/pin_line.png",
			"images/endday.png",
			"images/bird.png",
			"images/point.png",
			"images/coin.png",
			"images/finish.png",
			"images/red_overlay.jpg",
			"images/red_fill.png",
			"images/white_background.png",
			"images/box_background.png",
			"images/box_background2.png",
			"images/box_background3.png",
			"images/box_background4.png",
			"images/calendar.png",
			"images/Upgrade_display.png",
			"images/pause_menu2.png",
			"images/pause_menu.png",
			"images/recipe_card.png",
			"images/textbox.png",
			//background daylight cycle assets
			"images/background_images/1.0.png",
			"images/background_images/1.5.png",
			"images/background_images/2.0.png",
			"images/background_images/2.5.png",
			"images/background_images/3.0.png",
			"images/background_images/3.5.png",
			"images/background_images/4.0.png",
			"images/background_images/4.5.png",
			"images/background_images/5.0.png",
			"images/background_images/5.5.png",
			"images/background_images/6.0.png",
			"images/background_images/6.5.png",
			"images/background_images/7.0.png",
			"images/background_images/7.5.png",
			"images/background_images/8.0.png",
			"images/background_images/8.5.png",
			"images/background_images/9.0.png",
			"images/background_images/9.5.png",
			"images/background_images/10.0.png",
			"images/background_images/10.5.png",
			"images/background_images/11.0.png",
			"images/background_images/11.5.png",
			"images/background_images/12.0.png",
			"images/background_images/12.5.png",
			"images/background_images/13.0.png",
			"images/background_images/13.5.png",
			"images/background_images/14.0.png",
			"images/background_images/14.5.png",
			"images/background_images/15.0.png",
			"images/background_images/15.5.png",
			"images/background_images/16.0.png",
			"images/background_images/16.5.png",
			"images/background_images/17.0.png",
			"images/background_images/17.5.png",
			"images/background_images/18.0.png",
			"images/background_images/18.5.png"
	};
	// Modified the camera position to fix layout
	private static final Vector2 CAMERA_POSITION = new Vector2(7f, 4.5f);

	public final GdxGame game;
	public Renderer renderer;
	public final PhysicsEngine physicsEngine;
	public boolean isPaused = false;
	public DocketLineDisplay docketLineDisplay;
	public MainGameOrderTicketDisplay orderTicketDisplay;

	/**
	 * Constructs the main game screen
	 * @param game the GdxGame
	 */
	public MainGameScreen(GdxGame game) {
		this.game = game;
		MainGameOrderTicketDisplay.resetOrderNumb();

		logger.debug("Initialising main game screen services");
		ServiceLocator.registerTimeSource(new GameTime());

		PhysicsService physicsService = new PhysicsService();
		ServiceLocator.registerPhysicsService(physicsService);
		physicsEngine = physicsService.getPhysics();

		ServiceLocator.registerInputService(new InputService());
		ServiceLocator.registerResourceService(new ResourceService());
		ServiceLocator.registerPlayerService(new PlayerService());

		ServiceLocator.registerEntityService(new EntityService());
		ServiceLocator.registerRenderService(new RenderService());
		ServiceLocator.registerDocketService(new DocketService());

		ServiceLocator.registerDayNightService(new DayNightService());
		ServiceLocator.registerRandomComboService(new RandomComboService());
		ServiceLocator.registerLevelService(new LevelService());
		ServiceLocator.registerMapLayout(new MapLayout());

		logger.warn("Is SaveService null? " + (ServiceLocator.getSaveLoadService() == null));
		//ServiceLocator.registerSaveLoadService(new SaveLoadService());
		ServiceLocator.registerGameScreen(this);

		ServiceLocator.registerTicketDetails(new TicketDetails());

		renderer = RenderFactory.createRenderer();
		renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);
		renderer.getDebug().renderPhysicsWorld(physicsEngine.getWorld());

		loadAssets();
		createUI();

		logger.debug("Initialising main game screen entities");
		TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());

		GdxGame.LevelType currLevel = ServiceLocator.getLevelService().getCurrLevel();
		UpgradesDisplay upgradesDisplay = new UpgradesDisplay(this);
		ForestGameArea forestGameArea = new ForestGameArea(terrainFactory, currLevel, upgradesDisplay);
		forestGameArea.create();

		Entity spawnControllerEntity = LevelFactory.createSpawnControllerEntity();
		ServiceLocator.getEntityService().register(spawnControllerEntity);


		ServiceLocator.getLevelService().getEvents().trigger("setGameArea", forestGameArea);
		ServiceLocator.getLevelService().getEvents().trigger("startLevel", currLevel);
		//ServiceLocator.getLevelService().getEvents().trigger("mapLevel", currLevel);
	}

	/**
	 * Render the screen
	 * @param delta time span between the current frame and the last frame in seconds.
	 */
	@Override
	public void render(float delta) {
		if (!isPaused) {
			physicsEngine.update();
			ServiceLocator.getDayNightService().update();

			ServiceLocator.getEntityService().update();
		}
		renderer.render();
		Gdx.gl.glClearColor(0f/255f, 0f/255f, 0f/255f, 1);
	}

	/**
	 * Resize the screen and docket
	 * @param width width of screen
	 * @param height height of screen
	 */
	@Override
	public void resize(int width, int height) {
		logger.warn("HERE");
		if (width == 0) {
			width = 1;
		}
		if (height == 0) {
			height = 1;
		}
		renderer.resize(width, height);
		docketLineDisplay.resize();
		if (orderTicketDisplay != null) {
			orderTicketDisplay.updateDocketSizes();
		}
		logger.warn("Resized renderer: ({} x {})", width, height);
	}

	/**
	 * Pause game
	 */
	@Override
	public void pause() {
		logger.info("Game paused");
		isPaused = true;
		ServiceLocator.getTimeSource().pause();
		for (Entity entity : ServiceLocator.getEntityService().getEntities()) {
			AITaskComponent aiComponent = entity.getComponent(AITaskComponent.class);
			if (aiComponent != null) {
				aiComponent.pause();
			}
		}
	}

	/**
	 * Resume game
	 */
	@Override
	public void resume() {
		logger.info("Game resumed");
		isPaused = false;
		ServiceLocator.getTimeSource().resume();
		for (Entity entity : ServiceLocator.getEntityService().getEntities()) {
			AITaskComponent aiComponent = entity.getComponent(AITaskComponent.class);
			if (aiComponent != null) {
				aiComponent.resume();
			}
		}
	}

	/**
	 * Dispose of services
	 */
	@Override
	public void dispose() {
		logger.debug("Disposing main game screen");

		renderer.dispose();
		unloadAssets();

		ServiceLocator.getEntityService().dispose();
		ServiceLocator.getRenderService().dispose();
		ServiceLocator.getResourceService().dispose();

		ServiceLocator.clear();
	}

	/**
	 * Reset screen UI
	 */
	public void resetScreen() {
		NPCFactory.reset();
		EntityService entityService = ServiceLocator.getEntityService();
		entityService.dispose();
		ServiceLocator.registerEntityService(new EntityService());
		createUI();
	}

	/**
	 * Loads assets to resourceService
	 */
	void loadAssets() {
		logger.debug("Loading assets");
		ResourceService resourceService = ServiceLocator.getResourceService();
		resourceService.loadTextures(mainGameTextures);
		resourceService.loadTextures(DocketMealDisplay.getMealDocketTextures());
		ServiceLocator.getResourceService().loadAll();
	}

	/**
	 * Unloads the assets from resourceService
	 */
	void unloadAssets() {
		logger.debug("Unloading assets");
		ResourceService resourceService = ServiceLocator.getResourceService();
		resourceService.unloadAssets(mainGameTextures);
		resourceService.unloadAssets(DocketMealDisplay.getMealDocketTextures());
	}

	/**
	 * Get game
	 * @return the GDXGame
	 */
	public GdxGame getGame() {
		return game;
	}

	/**
	 * Creates the main game's ui including components for rendering ui elements to the screen and
	 * capturing and handling ui input.
	 */
	void createUI() {
		logger.debug("Creating ui");
		Stage stage = ServiceLocator.getRenderService().getStage();
		InputComponent inputComponent =
				ServiceLocator.getInputService().getInputFactory().createForTerminal();

		Entity ui = new Entity();
		ui.addComponent(new GameBackgroundDisplay())
				.addComponent(new InputDecorator(stage, 10))
				.addComponent(docketLineDisplay = new DocketLineDisplay())
				.addComponent(new DocketLineDisplay())
				.addComponent(new PerformanceDisplay())
				.addComponent(new MainGameActions(this.game, UIFactory.createDocketUI()))
				.addComponent(new MainGameExitDisplay())
				.addComponent(new Terminal())
				.addComponent(inputComponent)
				.addComponent(new TerminalDisplay())
				.addComponent(new KeybindsButtonDisplay())
				.addComponent(new OrderActions())
				.addComponent(new PauseMenuActions(this.game))
				.addComponent(new PauseMenuDisplay(this))
				.addComponent(new RageUpgrade())
				.addComponent(new LoanUpgrade())
				.addComponent(new SpeedBootsUpgrade())
				.addComponent(new ExtortionUpgrade())
				.addComponent(new DancePartyUpgrade())
				.addComponent(new PauseMenuActions(this.game))
				.addComponent(new PauseMenuDisplay(this))
				.addComponent(new UpgradesDisplay(this))
				.addComponent(new RecipeCardDisplay(this));

		ServiceLocator.getEntityService().register(ui);
	}
}