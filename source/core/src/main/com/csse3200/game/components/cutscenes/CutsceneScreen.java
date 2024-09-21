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

    private static final String[] cutsceneScreenTextures = {};

    public CutsceneScreen(GdxGame game, int cutsceneVal) {
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
        CutsceneArea cutsceneArea = new CutsceneArea(cutsceneVal);
        cutsceneArea.create();
    }

    @Override
    public void render(float delta) {
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
        logger.trace("Resized renderer: ({} x {})", width, height);
    }

    @Override
    public void dispose() {
        logger.debug("Disposing cutscene screen");

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
        resourceService.loadTextures(cutsceneScreenTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(cutsceneScreenTextures);
        resourceService.unloadAssets(DocketMealDisplay.getMealDocketTextures());
    }

    public GdxGame getGame() {
        return game;
    }
}
