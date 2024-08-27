package com.csse3200.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.components.maingame.MainGameActions;
import com.csse3200.game.components.ordersystem.DocketDisplay;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.RenderFactory;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.input.InputDecorator;
import com.csse3200.game.input.InputService;
import com.csse3200.game.physics.PhysicsEngine;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.rendering.Renderer;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.terminal.Terminal;
import com.csse3200.game.ui.terminal.TerminalDisplay;
import com.csse3200.game.components.maingame.EndDayDisplay;
import com.csse3200.game.components.maingame.MainGameExitDisplay;
import com.csse3200.game.components.gamearea.PerformanceDisplay;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
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
		"images/ordersystem/pin_line.png"
	};
	private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 6.0f);
  private final ShapeRenderer shapeRenderer;
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
		ServiceLocator.registerResourceService(new ResourceService());

		ServiceLocator.registerEntityService(new EntityService());
		ServiceLocator.registerRenderService(new RenderService());
		ServiceLocator.registerDocketService(new DocketService());

		renderer = RenderFactory.createRenderer();
		renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);
		renderer.getDebug().renderPhysicsWorld(physicsEngine.getWorld());

		loadAssets();
		createUI();

    logger.debug("Initialising main game screen entities");
    TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
    ForestGameArea forestGameArea = new ForestGameArea(terrainFactory);
    shapeRenderer = new ShapeRenderer();
    forestGameArea.create();
  }

  private void drawFrame() {
    int frameHeight = 1150;

    float x = 291;
    float y = 24;
    float width = 1533;
    float height = frameHeight;
    float lineWidth = 10;

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(0, 0, 0, 1);

    shapeRenderer.rectLine(new Vector2(x, y + height), new Vector2(x + width, y + height), lineWidth);
    shapeRenderer.rectLine(new Vector2(x, y), new Vector2(x + width, y), lineWidth);
    shapeRenderer.rectLine(new Vector2(x, y), new Vector2(x, y + height), lineWidth);
    shapeRenderer.rectLine(new Vector2(x + width, y), new Vector2(x + width, y + height), lineWidth);

    shapeRenderer.end();
  }

	@Override
	public void render(float delta) {
		physicsEngine.update();
		ServiceLocator.getEntityService().update();
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
			.addComponent(new PerformanceDisplay())
			.addComponent(new MainGameActions(this.game))
			.addComponent(new MainGameExitDisplay())
			.addComponent(new Terminal())
			.addComponent(inputComponent)
			.addComponent(new TerminalDisplay())
			// order system
			.addComponent(new DocketLineDisplay())
			//.addComponent(new DocketDisplay())
			.addComponent(new OrderActions(this.game))
			.addComponent(new MainGameOrderBtnDisplay())
		        .addComponent(new EndDayDisplay(this));
		ServiceLocator.getEntityService().register(ui);
	}
}
