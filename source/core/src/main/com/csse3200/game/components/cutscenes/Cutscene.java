package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.cutscenes.scenes.AnimatedScene;
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

    // List of animated scenes in the cutscene
    protected List<AnimatedScene> animatedScenes = new ArrayList<>();

    // List of entities involved in the cutscene
    protected List<Entity> entities = new ArrayList<>();

    // Text to be displayed during the cutscene
    protected Array<String> cutsceneText = new Array<>();

    // Index of the current scene
    protected int currentSceneIndex = 0;

    // Start time of the current scene
    protected float timeStart = 0f;

    // The current scene being displayed
    public Scene currentScene;
    public String currentText;

    // The current animated scene being displayed
    protected AnimatedScene currentAnimatedScene;

    // The name of the animation as found in the atlas file
    protected String animName;

    // Whether the scenes are fully animated
    public boolean IsAnimatedScenes = false;

    // Game time service to keep track of time
    protected GameTime gameTime;

    // Assets used in the cutscene
    protected String[] textures;
    protected String[] animations;

    protected String[] images;

    // The index the text is under, after it increases too large, then it goes to the next scene
    protected int textIndex;

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
        textIndex--;
    }

    /**
     * Updates the cutscene, transitioning to the next scene if the current scene's duration has passed.
     */
    @Override
    public void update() {
        float currentTime = gameTime.getTime();
        // Check if the current scene has finished
        if (!IsAnimatedScenes) {
            if ((currentScene != null) && (currentTime - timeStart) > currentScene.getDuration()) {
                logger.info("Scene {} finished. Moving to next scene.", currentSceneIndex);
                nextCutscene();
            }
        } else {
            if ((currentAnimatedScene != null) && (currentTime - timeStart) > currentAnimatedScene.getDuration()) {
                logger.info("Scene {} finished. Moving to next scene.", currentSceneIndex);
                nextCutscene();
            }
        }
    }

    /**
     * Transitions to the next scene in the cutscene. If there are no more scenes, it triggers the "cutsceneEnded" event.
     */
    protected void nextCutscene() {
        disposeEntities();  // Dispose of current entities before moving to the next scene

        currentSceneIndex++;
        if (!IsAnimatedScenes) {
            if (currentSceneIndex < scenes.size()) {
                logger.info("Loading next scene: {}", currentSceneIndex);
                loadScene(currentSceneIndex);
            } else {
                logger.info("Cutscene finished. Triggering next event.");
                ServiceLocator.getCutsceneScreen().getCutsceneScreenDisplay().getEntity().getEvents().trigger("cutsceneEnded");
            }
        } else {
            if (currentSceneIndex < animatedScenes.size()) {
                logger.info("Loading next scene: {}", currentSceneIndex);
                loadScene(currentSceneIndex);
            } else {
                logger.info("Cutscene finished. Triggering next event.");
                ServiceLocator.getCutsceneScreen().getCutsceneScreenDisplay().getEntity().getEvents().trigger("cutsceneEnded");
            }
        }
    }

    /**
     * Loads a scene by its index, initializing entities and assets for that scene.
     * @param sceneIndex Index of the scene to load
     */
    protected void loadScene(int sceneIndex) {
        if (!IsAnimatedScenes) {
            if (sceneIndex >= scenes.size()) {
                logger.error("No more scenes available.");
                nextCutscene();
                return;
            }

            currentScene = scenes.get(sceneIndex);
            logger.info("Loading scene {}", sceneIndex);

            loadAssetsForScene(currentScene);  // Load assets needed for the current scene
            createEntitiesForScene(currentScene);  // Create entities for the current scene


            setTextForScene(currentScene);
        } else {
            if (sceneIndex >= animatedScenes.size()) {
                logger.error("No more scenes available.");
                nextCutscene();
                return;
            }



            currentAnimatedScene = animatedScenes.get(sceneIndex);
            animName = currentAnimatedScene.getAnimName();
            logger.info("Loading scene {}", sceneIndex);

            loadAssetsForScene(currentAnimatedScene);  // Load assets needed for the current scene

            createEntitiesForScene(currentAnimatedScene);  // Create entities for the current scene

        }
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
     * Loads the assets needed for a specific scene, including textures and animations.
     * @param scene The scene for which to load assets
     */
    protected void loadAssetsForScene(AnimatedScene scene) {
        textures = new String[] {scene.getAtlasFilePath()};
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextureAtlases(textures);

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
            String[] animationPaths = scene.getAnimationImagePaths();
            Vector2[] animationPositions = scene.getAnimationPositions();
            for (int i = 0; i < animationPaths.length; i++) {
                String animationPath = animationPaths[i];
                Vector2 animationPosition = animationPositions[i];
                // Assume that the animation is called idle for now.
                Entity animation = CutsceneFactory.createAnimation(animationPath, "idle");
                entities.add(animation);
                animation.setPosition(animationPosition);
                ServiceLocator.getEntityService().register(animation);
            }
        }

        //Create the image entities if they exist in the scene
        if (scene.getImagePaths() != null) {
            String[] imagePaths = scene.getImagePaths();
            Vector2[] imagePositions = scene.getImagePositions();
            for (int i = 0; i < imagePaths.length; i++) {
                String imagePath = imagePaths[i];
                Vector2 imagePosition = imagePositions[i];
                // Assume that the animation is called idle for now.
                Entity image = CutsceneFactory.createImage(imagePath);
                entities.add(image);
                image.setPosition(imagePosition);
                ServiceLocator.getEntityService().register(image);
            }
        }
    }

    public void setTextForScene(Scene scene) {
        Array<String> sceneText = scene.getSceneText();
        if (sceneText.size > textIndex) {
            currentText = sceneText.get(textIndex);
            textIndex++;
        }
        else {
            textIndex = 0;
            nextCutscene();
        }
    }

    /**
     * Creates entities for the given scene, such as background and animation entities.
     * @param scene The scene for which to create entities
     */
    protected void createEntitiesForScene(AnimatedScene scene) {
        // Create animation entities
        Entity animation = CutsceneFactory.createFullAnimation(scene.getAtlasFilePath(), animName);
        entities.add(animation);
        ServiceLocator.getEntityService().register(animation);
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
