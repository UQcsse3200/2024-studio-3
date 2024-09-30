package com.csse3200.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.components.cutscenes.CutsceneActions;
import com.csse3200.game.components.cutscenes.CutsceneArea;
import com.csse3200.game.components.cutscenes.CutsceneScreenDisplay;
import com.csse3200.game.components.cutscenes.CutsceneTextDisplay;
import com.csse3200.game.components.gamearea.PerformanceDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.RenderFactory;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.input.InputDecorator;
import com.csse3200.game.input.InputService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.rendering.Renderer;
import com.csse3200.game.services.*;
import com.csse3200.game.ui.terminal.Terminal;
import com.csse3200.game.ui.terminal.TerminalDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The CutsceneScreen class represents the screen used during cutscenes in the game.
 * It handles loading and displaying the cutscene, rendering the UI, and managing assets and services.
 */
public class CutsceneScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CutsceneScreen.class);

    // Camera position for the cutscene screen
    private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 6.0f);

    private final GdxGame game;
    private final Renderer renderer;

    // Textures used for the cutscene screen
    private static final String[] cutsceneScreenTextures = {"images/textbox.png"};

    private CutsceneScreenDisplay cutsceneScreenDisplay;

    /**
     * Constructor for the CutsceneScreen.
     *
     * @param game       The main game instance.
     * @param cutsceneVal The cutscene value to determine which cutscene to load.
     */
    public CutsceneScreen(GdxGame game, int cutsceneVal) {
        this.game = game;

        logger.debug("Initialising main game screen services");
        // Register essential services for cutscene operation
        ServiceLocator.registerTimeSource(new GameTime());
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerCutsceneScreen(this);

        // Initialize renderer and set the camera position
        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);

        loadAssets();  // Load assets required for the cutscene
        createUI();  // Create and set up the user interface

        logger.debug("Initialising Cutscene game screen entities");
        TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
        CutsceneArea cutsceneArea = new CutsceneArea(cutsceneVal);  // Initialize the cutscene area
        cutsceneArea.create();  // Create the cutscene area
    }

    /**
     * Renders the cutscene screen, updating the rendering system each frame.
     *
     * @param delta Time in seconds since the last frame.
     */
    @Override
    public void render(float delta) {
        renderer.render();  // Render the screen using the renderer
    }

    /**
     * Handles resizing the cutscene screen and adjusting the renderer.
     *
     * @param width  New width of the screen.
     * @param height New height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);  // Resize the renderer to fit the new dimensions
        logger.trace("Resized renderer: ({} x {})", width, height);
    }

    /**
     * Disposes of the cutscene screen and all associated resources.
     */
    @Override
    public void dispose() {
        logger.debug("Disposing cutscene screen");

        renderer.dispose();  // Dispose the renderer
        unloadAssets();  // Unload the assets used by the screen

        ServiceLocator.getEntityService().dispose();  // Dispose of services
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getResourceService().dispose();

        ServiceLocator.clear();  // Clear all services from the service locator
    }

    /**
     * Loads the assets required for the cutscene screen.
     */
    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(cutsceneScreenTextures);  // Load cutscene screen textures
        resourceService.loadAll();  // Load all queued assets
    }

    /**
     * Unloads the assets used by the cutscene screen.
     */
    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(cutsceneScreenTextures);  // Unload cutscene screen textures
    }

    /**
     * Creates the user interface for the cutscene screen.
     * This includes setting up the stage, input, and other UI components like cutscene text display.
     */
    private void createUI() {
        logger.debug("Creating UI");
        Stage stage = ServiceLocator.getRenderService().getStage();  // Get the stage from the render service
        InputComponent inputComponent = ServiceLocator.getInputService().getInputFactory().createForTerminal();  // Create input component
        cutsceneScreenDisplay = new CutsceneScreenDisplay(this.game);  // Initialize the cutscene screen display

        // Create and configure the UI entity with various components
        Entity ui = new Entity();
        ui.addComponent(new InputDecorator(stage, 10))
                .addComponent(new PerformanceDisplay())
                .addComponent(new Terminal())
                .addComponent(inputComponent)
                .addComponent(new TerminalDisplay())
                .addComponent(new CutsceneActions(this.game))
                .addComponent(cutsceneScreenDisplay)
                .addComponent(new CutsceneTextDisplay(this));

        // Register the UI entity with the entity service
        ServiceLocator.getEntityService().register(ui);
    }

    /**
     * Gets the CutsceneScreenDisplay component for this screen.
     *
     * @return The cutscene screen display.
     */
    public CutsceneScreenDisplay getCutsceneScreenDisplay() {
        return cutsceneScreenDisplay;
    }

    /**
     * Gets the GdxGame instance associated with this screen.
     *
     * @return The main game instance.
     */
    public GdxGame getGame() {
        return game;
    }
}
