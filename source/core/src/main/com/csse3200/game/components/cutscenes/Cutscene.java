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

public abstract class Cutscene extends Component {
    private static final Logger logger = LoggerFactory.getLogger(Cutscene.class);
    protected List<Scene> scenes = new ArrayList<>();
    protected List<Entity> entities = new ArrayList<>();
    protected Array<String> cutsceneText = new Array<>();
    protected int currentSceneIndex = 0;
    protected float timeStart = 0f;
    protected Scene currentScene;
    protected GameTime gameTime;
    protected String[] textures;
    protected String[] animations;
    protected String[] sounds;
    protected String[] music;

    public Cutscene() {
        this.gameTime = ServiceLocator.getTimeSource();
        loadAssets();
        setupScenes();
    }

    @Override
    public void create() {
        entity.getEvents().addListener("nextCutscene", this::nextCutscene);
        loadScene(currentSceneIndex);
    }

    @Override
    public void update() {
        float currentTime = gameTime.getTime();
        if (currentScene != null && (currentTime - timeStart) > currentScene.getDuration()) {
            logger.info("Scene {} finished. Moving to next scene.", currentSceneIndex);
            nextCutscene();
        }
    }

    protected void nextCutscene() {
        disposeEntities();

        currentSceneIndex++;
        if (currentSceneIndex < scenes.size()) {
            logger.info("Loading next scene: {}", currentSceneIndex);
            loadScene(currentSceneIndex);
        } else {
            logger.info("Cutscene finished. Triggering next event.");
            entity.getEvents().trigger("cutsceneEnded");
        }
    }

    protected void loadScene(int sceneIndex) {
        if (sceneIndex >= scenes.size()) {
            logger.error("No more scenes available.");
            nextCutscene();
            return;
        }

        currentScene = scenes.get(sceneIndex);
        logger.info("Loading scene {}", sceneIndex);

        loadAssetsForScene(currentScene);
        createEntitiesForScene(currentScene);

        // Reset timer for scene duration
        timeStart = gameTime.getTime();
    }

    protected void loadAssetsForScene(Scene scene) {
        textures = new String[] {scene.getBackgroundImagePath()};
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);

        if (scene.getAnimationImagePaths() != null) {
            resourceService.loadTextures(scene.getAnimationImagePaths());
        }

        resourceService.loadAll();
    }

    protected void createEntitiesForScene(Scene scene) {
        // Create the background entity
        Entity background = CutsceneFactory.createBackground(scene.getBackgroundImagePath());
        entities.add(background);
        ServiceLocator.getEntityService().register(background);

        // Create animation entities if they exist
        if (scene.getAnimationImagePaths() != null) {
            for (String animationPath : scene.getAnimationImagePaths()) {
                Entity animation = CutsceneFactory.createAnimation(animationPath);
                entities.add(animation);
                ServiceLocator.getEntityService().register(animation);
            }
        }
    }

    protected void disposeEntities() {
        for (Entity entity : entities) {
            ServiceLocator.getEntityService().unregister(entity);
            entity.dispose();
        }
        entities.clear();
    }

    protected abstract void setupScenes();

    protected abstract void loadAssets();

    public void start() {
        createEntities();
    }

    public void dispose() {
        unloadAssets();
        disposeEntities();
    }

    protected void unloadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(textures);
    }

    public boolean isCompleted() {
        logger.error("Not implemented: isCompleted()");
        return true;
    }

    public abstract void createEntities();
}
