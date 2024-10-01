package com.csse3200.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.GdxGame;
import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.ai.tasks.Task;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.components.maingame.*;
import com.csse3200.game.components.levels.LevelComponent;
import com.csse3200.game.components.maingame.MainGameActions;
import com.csse3200.game.components.mainmenu.MainMenuBackground;
import com.csse3200.game.components.tasks.PathFollowTask;
import com.csse3200.game.components.upgrades.LoanUpgrade;
import com.csse3200.game.components.upgrades.RageUpgrade;
import com.csse3200.game.components.upgrades.RandomCombination;
import com.csse3200.game.components.ordersystem.*;
import com.csse3200.game.components.moral.MoralDecision;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.components.upgrades.SpeedBootsUpgrade;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.LevelFactory;
import com.csse3200.game.entities.factories.RenderFactory;
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
import com.csse3200.game.components.maingame.EndDayDisplay;
import com.csse3200.game.components.maingame.MainGameExitDisplay;
import com.csse3200.game.components.maingame.TextDisplay;
import com.csse3200.game.components.gamearea.PerformanceDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.player.InventoryDisplay;
import java.util.Arrays;
import com.csse3200.game.components.ordersystem.DocketLineDisplay;
import com.csse3200.game.components.player.InventoryDisplay;
import java.util.Arrays;

/**
 * The game screen containing the main game.
 *
 * <p>Details on libGDX screens: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class MainGameScreen extends ScreenAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
	private static final String[] mainGameTextures = {
			"images/heart.png",
			// order system assets
			"images/ordersystem/docket_background.png",
			"images/ordersystem/pin_line.png",
			"images/bird.png",
			"images/point.png",
			"images/coin.png",
			"images/textbox.png",
			"images/red_overlay.jpg",
			"images/red_fill.png",
			"images/white_background.png",
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

  private final GdxGame game;
  private final Renderer renderer;
  private final PhysicsEngine physicsEngine;
  private boolean isPaused = false;
  private DocketLineDisplay docketLineDisplay;
  private MainGameOrderTicketDisplay orderTicketDisplay;

	public MainGameScreen(GdxGame game) {
		this.game = game;

		logger.debug("Initialising main game screen services");
		ServiceLocator.registerTimeSource(new GameTime());

		PhysicsService physicsService = new PhysicsService();
		ServiceLocator.registerPhysicsService(physicsService);
		physicsEngine = physicsService.getPhysics();

		ServiceLocator.registerInputService(new InputService());
		ServiceLocator.registerPlayerService(new PlayerService());
		ServiceLocator.registerResourceService(new ResourceService());

		ServiceLocator.registerEntityService(new EntityService());
		ServiceLocator.registerRenderService(new RenderService());
		ServiceLocator.registerDocketService(new DocketService());
        ServiceLocator.registerDayNightService(new DayNightService());
		ServiceLocator.registerLevelService(new LevelService());
		ServiceLocator.registerGameScreen(this);

		ServiceLocator.registerTicketDetails(new TicketDetails());

		renderer = RenderFactory.createRenderer();
		renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);
		renderer.getDebug().renderPhysicsWorld(physicsEngine.getWorld());

		loadAssets();
		createUI();

		logger.debug("Initialising main game screen entities");
		TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
		ForestGameArea forestGameArea = new ForestGameArea(terrainFactory);
		forestGameArea.create();
		Entity spawnControllerEntity = LevelFactory.createSpawnControllerEntity();
		ServiceLocator.getEntityService().register(spawnControllerEntity);
		int currLevel = ServiceLocator.getLevelService().getCurrLevel();
		ServiceLocator.getLevelService().getEvents().trigger("setGameArea", forestGameArea);
		ServiceLocator.getLevelService().getEvents().trigger("startLevel", currLevel);
	}

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

	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
		docketLineDisplay.resize();
		if (orderTicketDisplay != null) {
			orderTicketDisplay.updateDocketSizes();
		}
		logger.trace("Resized renderer: ({} x {})", width, height);
	}

	@Override
	public void pause() {
		logger.info("Game paused");
		isPaused = true;
		for (Entity entity : ServiceLocator.getEntityService().getEntities()) {
			AITaskComponent aiComponent = entity.getComponent(AITaskComponent.class);
			if (aiComponent != null) {
				aiComponent.pause();
			}
		}
	}

	@Override
	public void resume() {
		logger.info("Game resumed");
		isPaused = false;
		for (Entity entity : ServiceLocator.getEntityService().getEntities()) {
			AITaskComponent aiComponent = entity.getComponent(AITaskComponent.class);
			if (aiComponent != null) {
				aiComponent.resume();
			}
		}
	}

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

	public void resetScreen() {
		EntityService entityService = ServiceLocator.getEntityService();
		entityService.dispose();
		ServiceLocator.registerEntityService(new EntityService());
		createUI();
	}

	private void loadAssets() {
		logger.debug("Loading assets");
		ResourceService resourceService = ServiceLocator.getResourceService();
		resourceService.loadTextures(mainGameTextures);
		resourceService.loadTextures(DocketMealDisplay.getMealDocketTextures());
		ServiceLocator.getResourceService().loadAll();
	}

	private void unloadAssets() {
		logger.debug("Unloading assets");
		ResourceService resourceService = ServiceLocator.getResourceService();
		resourceService.unloadAssets(mainGameTextures);
		resourceService.unloadAssets(DocketMealDisplay.getMealDocketTextures());
	}


	public GdxGame getGame() {
		return game;
	}

	/**
	 * Creates the main game's ui including components for rendering ui elements to the screen and
	 * capturing and handling ui input.
	 */
	private void createUI() {
		logger.debug("Creating ui");
		Stage stage = ServiceLocator.getRenderService().getStage();
		InputComponent inputComponent =
				ServiceLocator.getInputService().getInputFactory().createForTerminal();

		docketLineDisplay = new DocketLineDisplay();

		Entity ui = new Entity();
		ui.addComponent(new GameBackgroundDisplay())
		.addComponent(new InputDecorator(stage, 10))
		  	.addComponent(docketLineDisplay)
			.addComponent(new PerformanceDisplay())
			.addComponent(new MainGameActions(this.game))
			.addComponent(new MainGameExitDisplay())
			.addComponent(new Terminal())
			.addComponent(inputComponent)
			.addComponent(new TerminalDisplay())
			.addComponent(new OrderActions(this.game))
			.addComponent(new PauseMenuActions(this.game))
			.addComponent(new PauseMenuDisplay(this))
			.addComponent(new RageUpgrade())
			.addComponent(new LoanUpgrade())
			.addComponent(new RandomCombination())
				.addComponent(new SpeedBootsUpgrade());



		//temporary moral display
//			.addComponent(new MoralDisplayTemp(this));
		ServiceLocator.getEntityService().register(ui);
	}
}
