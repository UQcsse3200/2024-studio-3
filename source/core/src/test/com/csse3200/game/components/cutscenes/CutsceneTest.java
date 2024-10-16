package com.csse3200.game.components.cutscenes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CutsceneTest {

    private Cutscene cutscene;
    private ResourceService resourceService;
    private GameTime gameTime;
    private Entity entity;
    private EventHandler eventHandler;
    private Scene mockScene;

    @Before
    public void setUp() {
        resourceService = mock(ResourceService.class);
        gameTime = mock(GameTime.class);
        entity = mock(Entity.class);
        eventHandler = mock(EventHandler.class);
        mockScene = mock(Scene.class);

        when(entity.getEvents()).thenReturn(eventHandler);

        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerTimeSource(gameTime);

        cutscene = new Cutscene() {
            @Override
            protected void setupScenes() {}

            @Override
            protected void loadAssets() {}

            @Override
            public void createEntities() {}

            @Override
            protected void nextCutscene() {
                disposeEntities();
                currentSceneIndex++;
                if (currentSceneIndex < scenes.size()) {
                    loadScene(currentSceneIndex);
                } else {
                    entity.getEvents().trigger("cutsceneEnded");
                }
            }
        };
        cutscene.setEntity(entity);
    }

    @After
    public void tearDown() {
        ServiceLocator.clear();
    }

    @Test
    public void createAndSetUpScenes() {
        cutscene.create();
        verify(eventHandler, times(1)).addListener(eq("nextCutscene"), any(EventListener0.class));
    }

    @Test
    public void triggerCutsceneEndEvent() {
        cutscene.scenes.add(mockScene);
        cutscene.currentSceneIndex = cutscene.scenes.size();

        cutscene.nextCutscene();

        verify(eventHandler).trigger("cutsceneEnded");
    }

    @Test
    public void unloadAssetsOnDispose() {
        cutscene.textures = new String[]{"texture1.png", "texture2.png"};
        cutscene.dispose();

        verify(resourceService, times(1)).unloadAssets(cutscene.textures);
    }
}
