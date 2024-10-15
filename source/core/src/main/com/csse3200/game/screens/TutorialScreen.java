package com.csse3200.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.maingame.*;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.components.upgrades.*;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.LevelFactory;
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
import com.csse3200.game.components.gamearea.PerformanceDisplay;
import com.csse3200.game.components.tutorial.TutorialScreenDisplay;
import com.csse3200.game.components.maingame.TextDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.ordersystem.DocketLineDisplay;

/**
 * The game screen containing the tutorial.
 *
 */
public class TutorialScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TutorialScreen.class);
    private static final String[] mainGameTextures = {
            "images/heart.png",
            "images/ordersystem/docket_background.png",
            "images/ordersystem/pin_line2.png",
            "images/bird.png",
            "images/textbox.png",
            "images/inventory_ui/slot.png",
    };

    private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 6.0f);

    private final GdxGame game;
    private final Renderer renderer;
    private final PhysicsEngine physicsEngine;
    private boolean isPaused = false;
    private ResourceService resourceService;
    private Entity player;

    public TutorialScreen(GdxGame game) {
        this.game = game;

        // Register ResourceService before calling loadAssets
        ServiceLocator.registerResourceService(new ResourceService());
        this.resourceService = ServiceLocator.getResourceService(); // Now it's initialized

        loadAssets(); // You can now load the assets safely

        logger.debug("Initialising tutorial screen services");
        ServiceLocator.registerTimeSource(new GameTime());

        PhysicsService physicsService = new PhysicsService();
        ServiceLocator.registerPhysicsService(physicsService);
        physicsEngine = physicsService.getPhysics();

        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerLevelService(new LevelService());
        ServiceLocator.registerPlayerService(new PlayerService());

        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerDocketService(new DocketService());

        ServiceLocator.registerDayNightService(new DayNightService());

        MainGameScreen mainGameScreen = new MainGameScreen(game);
        ServiceLocator.registerGameScreen(mainGameScreen);

        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);
        renderer.getDebug().renderPhysicsWorld(physicsEngine.getWorld());

        createUI();  // Create the UI after loading the assets

        logger.debug("Initialising tutorial screen entities");

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
        logger.debug("Disposing tutorial screen");

        renderer.dispose();
        unloadAssets();

        ServiceLocator.getEntityService().dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getResourceService().dispose();

        ServiceLocator.clear();
    }

    private void loadAssets() {
        logger.debug("Loading assets");

        resourceService.loadTextures(mainGameTextures);
        resourceService.loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        resourceService.unloadAssets(mainGameTextures);
    }

    /**
     * Creates the main game's UI, including components for rendering UI elements to the screen and
     * capturing and handling UI input.
     */
    private void createUI() {
        logger.debug("Creating UI");
        Stage stage = ServiceLocator.getRenderService().getStage();
        InputComponent inputComponent = ServiceLocator.getInputService().getInputFactory().createForTerminal();

        Entity ui = new Entity();
        ui.addComponent(new GameBackgroundDisplay())
                .addComponent(new InputDecorator(stage, 10))
                .addComponent(new DocketLineDisplay())
                .addComponent(new PerformanceDisplay())
                //.addComponent(new MainGameActions(this.game, UIFactory.createDocketUI()))
                //.addComponent(new MainGameExitDisplay())
                .addComponent(new Terminal())
                .addComponent(inputComponent)
                .addComponent(new TerminalDisplay())
                .addComponent(new OrderActions())
                .addComponent(new PauseMenuActions(this.game))
                .addComponent(new RageUpgrade())
                .addComponent(new LoanUpgrade())
                .addComponent(new SpeedBootsUpgrade())
                .addComponent(new ExtortionUpgrade())
                .addComponent(new DancePartyUpgrade())
                .addComponent(new TutorialScreenDisplay(this.game))
                .addComponent(new TextDisplay(this));

        ServiceLocator.getEntityService().register(ui);
    }
}
