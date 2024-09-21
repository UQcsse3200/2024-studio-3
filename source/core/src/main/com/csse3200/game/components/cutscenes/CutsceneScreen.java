package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.components.ordersystem.DocketMealDisplay;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.LevelFactory;
import com.csse3200.game.entities.factories.RenderFactory;
import com.csse3200.game.input.InputService;
import com.csse3200.game.physics.PhysicsEngine;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.rendering.Renderer;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CutsceneScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CutsceneScreen.class);

    // Set a camera position for the introduction screen
    private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 6.0f);

    private final GdxGame game;
    private final Renderer renderer;
    private boolean isPaused = false;

    private static final String[] introScreenTextures = {};

    public CutsceneScreen(GdxGame game) {
        this.game = game;

        logger.debug("Initialising main game screen services");
        ServiceLocator.registerTimeSource(new GameTime());

        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());

        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerCutsceneScreen(this);

        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);

        loadAssets();

        logger.debug("Initialising Cutscene game screen entities");
        TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
        CutsceneArea cutsceneArea = new CutsceneArea();
        cutsceneArea.create();
        Entity spawnControllerEntity = LevelFactory.createSpawnControllerEntity();
        ServiceLocator.getEntityService().register(spawnControllerEntity);
        int currLevel = ServiceLocator.getLevelService().getCurrLevel();
        ServiceLocator.getLevelService().getEvents().trigger("setGameArea", cutsceneArea);
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(introScreenTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    public GdxGame getGame() {
        return game;
    }
}
