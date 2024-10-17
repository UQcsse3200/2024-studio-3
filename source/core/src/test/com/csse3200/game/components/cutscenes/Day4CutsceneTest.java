package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class Day4CutsceneTest {

    private Day4Cutscene day4Cutscene;
    private ResourceService resourceService;
    private GameTime gameTime;
    private Entity entity;

    @Before
    public void setUp() {
        resourceService = mock(ResourceService.class);
        gameTime = mock(GameTime.class);
        entity = mock(Entity.class);
        EventHandler eventHandler = mock(EventHandler.class);
        Texture mockTexture = mock(Texture.class);
        Graphics mockGraphics = mock(Graphics.class);

        when(entity.getEvents()).thenReturn(eventHandler);
        when(mockTexture.getHeight()).thenReturn(600);
        when(mockTexture.getWidth()).thenReturn(800);
        when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(mockTexture);
        Gdx.graphics = mockGraphics;
        when(mockGraphics.getHeight()).thenReturn(1200);

        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerTimeSource(gameTime);
        ServiceLocator.registerEntityService(mock(EntityService.class));

        day4Cutscene = new Day4Cutscene() {
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

        day4Cutscene.setEntity(entity);
    }

    @After
    public void tearDown() {
        ServiceLocator.clear();
    }

    @Test
    public void testLoadAssets() {
        day4Cutscene.loadAssets();
        verify(resourceService, times(2)).loadTextures(new String[]{
                "images/Cutscenes/Day3_Scene.png"
        });
        verify(resourceService, times(2)).loadTextures(new String[]{
                "images/Cutscenes/Character Artwork/rhino_sprite.png",
                "images/Cutscenes/Character Artwork/player_sprite_back_turned.png"
        });
        verify(resourceService, times(2)).loadAll();
    }

    @Test
    public void testSetupScenes() {
        day4Cutscene.setupScenes();  // Set up the scenes

        // Check that there is exactly 1 scene created
        assert day4Cutscene.scenes.size() == 2 : "Expected 1 scene but got " + day4Cutscene.scenes.size();

        // Verify that the first scene has the correct background and text
        Scene scene1 = day4Cutscene.scenes.getFirst();
        assert scene1.getBackgroundImagePath().equals("images/Cutscenes/Day3_Scene.png");
        assertEquals(3, scene1.getSceneText().size);
    }

    @Test
    public void testCreateEntities() {
        day4Cutscene.setupScenes();
        day4Cutscene.loadScene(0);
        verify(ServiceLocator.getEntityService(), times(3)).register(any(Entity.class));
    }

    @Test
    public void testUpdateTriggersCutsceneEnd() {
        day4Cutscene.setupScenes();
        day4Cutscene.loadScene(0);
        when(gameTime.getTime()).thenReturn(0L, 4L);
        doNothing().when(entity).dispose();
        when(entity.getCreatedComponents()).thenReturn(new Array<>());

        day4Cutscene.update();
        verify(entity, never()).getEvents();

        day4Cutscene.entities = new ArrayList<>();
        day4Cutscene.scenes = new ArrayList<>();

        day4Cutscene.update();
        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");
    }

    @Test
    public void testDisposeUnloadsAssets() {
        day4Cutscene.dispose();
        verify(resourceService, times(1)).unloadAssets(new String[]{
                "images/Cutscenes/Day3_Scene.png"
        });
    }

    @Test
    public void testNoTransitionWhenNoScenesRemain() {
        day4Cutscene.setupScenes();
        day4Cutscene.currentSceneIndex = day4Cutscene.scenes.size() - 1;
        day4Cutscene.nextCutscene();
        assert day4Cutscene.currentSceneIndex == day4Cutscene.scenes.size();
        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");
    }

    @Test
    public void testStartCreatesEntities() {
        day4Cutscene.setupScenes();
        day4Cutscene.start();
        verify(ServiceLocator.getEntityService(), times(day4Cutscene.entities.size())).register(any(Entity.class));
    }

    @Test
    public void testSetTextForScene() {
        day4Cutscene.setupScenes();
        day4Cutscene.loadScene(0);
        assert day4Cutscene.currentText.equals("Mafia Boss > Heh, seems like the animals are done laughing at you.");
        assertEquals(1, day4Cutscene.textIndex);
    }
}
