package com.csse3200.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.components.maingame.*;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.components.tutorial.KeybindsButtonDisplay;
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
public class TutorialScreen extends MainGameScreen {
    private static final Logger logger = LoggerFactory.getLogger(TutorialScreen.class);
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
            "images/recipe_card.png",
            "images/textbox.png",
            "images/pause_menu.png",
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

    private static final Vector2 CAMERA_POSITION = new Vector2(7f, 4.5f);

    public TutorialScreen(GdxGame game) {
        super(game);

        MainGameOrderTicketDisplay.resetOrderNumb();

        logger.debug("Initialising main game screen services");
        ServiceLocator.registerTimeSource(new GameTime());

        logger.warn("Is SaveService null? " + (ServiceLocator.getSaveLoadService() == null));
        ServiceLocator.registerGameScreen(this);

        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);
        renderer.getDebug().renderPhysicsWorld(physicsEngine.getWorld());

        loadAssets();
        createUI();

        logger.debug("Initialising main game screen entities");
        TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
        ServiceLocator.getLevelService().setCurrLevel(GdxGame.LevelType.LEVEL_1);
        GdxGame.LevelType currLevel = ServiceLocator.getLevelService().getCurrLevel();
        UpgradesDisplay upgradesDisplay = new UpgradesDisplay(this);
        ForestGameArea forestGameArea = new ForestGameArea(terrainFactory, currLevel, upgradesDisplay);
        forestGameArea.create();

        Entity spawnControllerEntity = LevelFactory.createSpawnControllerEntity();
        ServiceLocator.getEntityService().register(spawnControllerEntity);


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
        logger.debug("Disposing tutorial screen");

        renderer.dispose();
        unloadAssets();

        ServiceLocator.getEntityService().dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getResourceService().dispose();

        ServiceLocator.clear();
    }

    void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(mainGameTextures);
        resourceService.loadAll();
    }

    void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(mainGameTextures);
    }

    /**
     * Creates the main game's UI, including components for rendering UI elements to the screen and
     * capturing and handling UI input.
     */
    void createUI() {
        logger.debug("Creating UI");
        Stage stage = ServiceLocator.getRenderService().getStage();
        InputComponent inputComponent = ServiceLocator.getInputService().getInputFactory().createForTerminal();

        Entity ui = new Entity();
        ui.addComponent(new GameBackgroundDisplay())
                .addComponent(new InputDecorator(stage, 10))
                .addComponent(docketLineDisplay = new DocketLineDisplay())
                .addComponent(new PerformanceDisplay())
                .addComponent(new MainGameActions(game, UIFactory.createDocketUI()))
                .addComponent(new KeybindsButtonDisplay())
                .addComponent(new MainGameExitDisplay())
                .addComponent(new Terminal())
                .addComponent(inputComponent)
                .addComponent(new TerminalDisplay())
                .addComponent(new OrderActions())
                .addComponent(new OrderActions())
                .addComponent(new RageUpgrade())
                .addComponent(new TutorialScreenDisplay(game))
                .addComponent(new TextDisplay(this));

        ServiceLocator.getEntityService().register(ui);
    }
}