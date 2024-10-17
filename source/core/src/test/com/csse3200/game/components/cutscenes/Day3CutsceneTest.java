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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class Day3CutsceneTest {

    private Day3Cutscene day3Cutscene;
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

        day3Cutscene = new Day3Cutscene() {
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

        day3Cutscene.setEntity(entity);
    }

    @After
    public void tearDown() {
        ServiceLocator.clear();
    }

    @Test
    public void testLoadAssets() {
        day3Cutscene.loadAssets();
        verify(resourceService, times(2)).loadTextures(new String[]{
                "images/Cutscenes/Day3_Scene.png"
        });
        verify(resourceService, times(1)).loadTextures(new String[]{
                "images/Cutscenes/Character Artwork/rhino_sprite.png",
                "images/Cutscenes/Character Artwork/panda_sprite.png",
                "images/Cutscenes/Character Artwork/player_sprite_back_turned.png"
        });
        verify(resourceService, times(2)).loadAll();
    }

    @Test
    public void testSetupScenes() {
        day3Cutscene.setupScenes();  // Set up the scenes

        // Check that there are exactly 2 scenes created
        assertEquals(4, day3Cutscene.scenes.size(), "Expected 2 scenes but got " + day3Cutscene.scenes.size());

        // Verify that the first scene has the correct background and text
        Scene scene1 = day3Cutscene.scenes.getFirst();
        assert scene1.getBackgroundImagePath().equals("images/Cutscenes/Day3_Scene.png");
        assert scene1.getSceneText().size == 3 : "Scene 1 text size mismatch";

        // Verify that the second scene has the correct background and text
        Scene scene2 = day3Cutscene.scenes.get(1);
        assert scene2.getBackgroundImagePath().equals("images/Cutscenes/Day3_Scene.png");
        assert scene2.getSceneText().size == 3 : "Scene 2 text size mismatch";
    }

    @Test
    public void testCreateEntities() {
        day3Cutscene.setupScenes();
        day3Cutscene.loadScene(0);
        verify(ServiceLocator.getEntityService(), times(3)).register(any(Entity.class));
    }

    @Test
    public void testUpdateTriggersCutsceneEnd() {
        day3Cutscene.setupScenes();
        day3Cutscene.loadScene(0);
        when(gameTime.getTime()).thenReturn(0L, 4L);
        doNothing().when(entity).dispose();
        when(entity.getCreatedComponents()).thenReturn(new Array<>());

        day3Cutscene.update();
        verify(entity, never()).getEvents();

        day3Cutscene.entities = new ArrayList<>();
        day3Cutscene.scenes = new ArrayList<>();

        day3Cutscene.update();
        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");
    }

    @Test
    public void testDisposeUnloadsAssets() {
        day3Cutscene.dispose();
        verify(resourceService, times(1)).unloadAssets(new String[]{
                "images/Cutscenes/Day3_Scene.png"
        });
    }

    @Test
    public void testNoTransitionWhenNoScenesRemain() {
        day3Cutscene.setupScenes();
        day3Cutscene.currentSceneIndex = day3Cutscene.scenes.size() - 1;
        day3Cutscene.nextCutscene();
        assert day3Cutscene.currentSceneIndex == day3Cutscene.scenes.size();
        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");
    }

    @Test
    public void testStartCreatesEntities() {
        day3Cutscene.setupScenes();
        day3Cutscene.start();
        verify(ServiceLocator.getEntityService(), times(day3Cutscene.entities.size())).register(any(Entity.class));
    }

    @Test
    public void testSetTextForScene() {
        day3Cutscene.setupScenes();
        day3Cutscene.loadScene(0);
        assert day3Cutscene.currentText.equals("Mafia Boss > Heard the health inspector gave you a hard time. Typical.");
        assertEquals(1, day3Cutscene.textIndex);
    }
}
