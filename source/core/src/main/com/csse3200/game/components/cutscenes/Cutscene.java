package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cutscene in the game. A cutscene consists of multiple scenes,
 * and this class manages scene transitions, asset loading/unloading, and entity management for the cutscene.
 */
public abstract class Cutscene extends Component {
    private static final Logger logger = LoggerFactory.getLogger(Cutscene.class);

    // List of scenes in the cutscene
    protected List<Scene> scenes = new ArrayList<>();

    // List of entities involved in the cutscene
    protected List<Entity> entities = new ArrayList<>();

    // Text to be displayed during the cutscene
    protected Array<String> cutsceneText = new Array<>();

    // Index of the current scene
    protected int currentSceneIndex = 0;

    // Start time of the current scene
    protected float timeStart = 0f;

    // The current scene being displayed
    protected Scene currentScene;

    // Game time service to keep track of time
    protected GameTime gameTime;

    // Assets used in the cutscene
    protected String[] textures;
    protected String[] animations;
    protected String[] sounds;
    protected String[] music;

    /**
     * Constructor for the Cutscene class. Initializes the game time and loads assets and scenes.
     */
    public Cutscene() {
        this.gameTime = ServiceLocator.getTimeSource();
        loadAssets();
        setupScenes();
    }

    /**
     * Creates the cutscene by setting up event listeners and loading the first scene.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("nextCutscene", this::nextCutscene);
        loadScene(currentSceneIndex);
    }

    /**
     * Updates the cutscene, transitioning to the next scene if the current scene's duration has passed.
     */
    @Override
    public void update() {
        float currentTime = gameTime.getTime();
        // Check if the current scene has finished
        if (currentScene != null && (currentTime - timeStart) > currentScene.getDuration()) {
            logger.info("Scene {} finished. Moving to next scene.", currentSceneIndex);
            nextCutscene();
        }
    }

    /**
     * Transitions to the next scene in the cutscene. If there are no more scenes, it triggers the "cutsceneEnded" event.
     */
    protected void nextCutscene() {
        disposeEntities();  // Dispose of current entities before moving to the next scene

        currentSceneIndex++;
        if (currentSceneIndex < scenes.size()) {
            logger.info("Loading next scene: {}", currentSceneIndex);
            loadScene(currentSceneIndex);
        } else {
            logger.info("Cutscene finished. Triggering next event.");
            entity.getEvents().trigger("cutsceneEnded");
        }
    }

    /**
     * Loads a scene by its index, initializing entities and assets for that scene.
     * @param sceneIndex Index of the scene to load
     */
    protected void loadScene(int sceneIndex) {
        if (sceneIndex >= scenes.size()) {
            logger.error("No more scenes available.");
            nextCutscene();
            return;
        }

        currentScene = scenes.get(sceneIndex);
        logger.info("Loading scene {}", sceneIndex);

        loadAssetsForScene(currentScene);  // Load assets needed for the current scene
        createEntitiesForScene(currentScene);  // Create entities for the current scene

        // Reset the timer to track how long the scene is active
        timeStart = gameTime.getTime();
    }

    /**
     * Loads the assets needed for a specific scene, including textures and animations.
     * @param scene The scene for which to load assets
     */
    protected void loadAssetsForScene(Scene scene) {
        textures = new String[] {scene.getBackgroundImagePath()};
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);

        // Load any animations specific to the scene
        if (scene.getAnimationImagePaths() != null) {
            resourceService.loadTextures(scene.getAnimationImagePaths());
        }

        resourceService.loadAll();
    }

    /**
     * Creates entities for the given scene, such as background and animation entities.
     * @param scene The scene for which to create entities
     */
    protected void createEntitiesForScene(Scene scene) {
        // Create the background entity for the scene
        Entity background = CutsceneFactory.createBackground(scene.getBackgroundImagePath());
        entities.add(background);
        ServiceLocator.getEntityService().register(background);

        // Create animation entities if they exist in the scene
        if (scene.getAnimationImagePaths() != null) {
            for (String animationPath : scene.getAnimationImagePaths()) {
                Entity animation = CutsceneFactory.createAnimation(animationPath);
                entities.add(animation);
                ServiceLocator.getEntityService().register(animation);
            }
        }
    }

    /**
     * Disposes of all entities in the current scene.
     */
    protected void disposeEntities() {
        for (Entity entity : entities) {
            ServiceLocator.getEntityService().unregister(entity);
            entity.dispose();
        }
        entities.clear();  // Clear the list after disposing of all entities
    }

    /**
     * Abstract method to set up scenes for the cutscene. This should be implemented by subclasses.
     */
    protected abstract void setupScenes();

    /**
     * Abstract method to load the assets required for the cutscene. This should be implemented by subclasses.
     */
    protected abstract void loadAssets();

    /**
     * Starts the cutscene by creating the necessary entities.
     */
    public void start() {
        createEntities();
    }

    /**
     * Disposes of the cutscene by unloading assets and disposing of entities.
     */
    public void dispose() {
        unloadAssets();
        disposeEntities();
    }

    /**
     * Unloads the assets used in the cutscene.
     */
    protected void unloadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(textures);
    }

    /**
     * Checks if the cutscene has been completed. Currently not implemented.
     * @return boolean indicating if the cutscene is completed
     */
    public boolean isCompleted() {
        logger.error("Not implemented: isCompleted()");
        return true;
    }

    /**
     * Abstract method to create entities for the cutscene. This should be implemented by subclasses.
     */
    public abstract void createEntities();
}
