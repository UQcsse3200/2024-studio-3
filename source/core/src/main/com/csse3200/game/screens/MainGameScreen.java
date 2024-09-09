package com.csse3200.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.components.maingame.MainGameActions;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.components.ordersystem.OrderActions;
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
import com.csse3200.game.components.ordersystem.DocketLineDisplay;


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
			"images/textbox.png"
	};
	// Modified the camera position to fix layout
	private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 6.0f);

  private final GdxGame game;
  private final Renderer renderer;
  private final PhysicsEngine physicsEngine;
  private boolean isPaused = false;

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
		ServiceLocator.registerLevelService(new LevelService());

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
			ServiceLocator.getEntityService().update();
		}
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
		logger.trace("Resized renderer: ({} x {})", width, height);
	}

	@Override
	public void pause() {
		logger.info("Game paused");
		isPaused = true;
	}

	@Override
	public void resume() {
		logger.info("Game resumed");
		isPaused = false;
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

	private void loadAssets() {
		logger.debug("Loading assets");
		ResourceService resourceService = ServiceLocator.getResourceService();
		resourceService.loadTextures(mainGameTextures);
		ServiceLocator.getResourceService().loadAll();
	}

	private void unloadAssets() {
		logger.debug("Unloading assets");
		ResourceService resourceService = ServiceLocator.getResourceService();
		resourceService.unloadAssets(mainGameTextures);
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

		Entity ui = new Entity();
		ui.addComponent(new InputDecorator(stage, 10))
		  .addComponent(new DocketLineDisplay())
			.addComponent(new PerformanceDisplay())
			.addComponent(new MainGameActions(this.game))
			.addComponent(new MainGameExitDisplay())
			.addComponent(new Terminal())
			.addComponent(inputComponent)
			.addComponent(new TerminalDisplay())
			.addComponent(new OrderActions(this.game))
			.addComponent(new MainGameOrderBtnDisplay())
				.addComponent(new EndDayDisplay(this))
				.addComponent(new MoralDecisionDisplay(this))
				.addComponent(new TextDisplay(this));
		ServiceLocator.getEntityService().register(ui);
	}
}
