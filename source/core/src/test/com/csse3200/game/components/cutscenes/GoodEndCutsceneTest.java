package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.Component;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class GoodEndCutsceneTest {

    /**

    private GoodEndCutscene goodEndCutscene;
    private ResourceService resourceService;
    private GameTime gameTime;
    private Entity entity;

    @Before
    public void setUp() {
        // Mock dependencies
        resourceService = mock(ResourceService.class);
        gameTime = mock(GameTime.class);
        entity = mock(Entity.class);
        EventHandler eventHandler = mock(EventHandler.class);
        Texture mockTexture = mock(Texture.class);
        Graphics mockGraphics = mock(Graphics.class);

        // Mock the entity's event system
        when(entity.getEvents()).thenReturn(eventHandler);

        // Mock texture dimensions
        when(mockTexture.getHeight()).thenReturn(600);
        when(mockTexture.getWidth()).thenReturn(800);

        // Mock resource service to return the texture
        when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(mockTexture);

        // Set Gdx.graphics to the mock
        Gdx.graphics = mockGraphics;

        // Set up the mocked behavior for getHeight()
        when(mockGraphics.getHeight()).thenReturn(1200);

        // Register the mock services in ServiceLocator
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerTimeSource(gameTime);
        ServiceLocator.registerEntityService(mock(EntityService.class));

        // Initialize the cutscene with it not calling the service locator and instead just triggering own event
        goodEndCutscene = new GoodEndCutscene() {
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

        // Mock the entity for event triggering
        goodEndCutscene.setEntity(entity);
    }

    @After
    public void tearDown() {
        ServiceLocator.clear();
    }


    @Test
    public void testLoadAssets() {
        // Verify that the textures and animations are loaded
        verify(resourceService, times(1)).loadTextures(new String[]{
                "images/Cutscenes/Beastly_Bistro_Background.png",
                "images/Cutscenes/Graveyard_Scene.png",
                "images/Cutscenes/good_end_0.png",
                "images/Cutscenes/good_end_1.png",
                "images/Cutscenes/good_end_2.png",
                "images/Cutscenes/good_end_3.png"
        });
        verify(resourceService, times(1)).loadTextureAtlases(new String[]{"images/player/Cook_Model32.png"});
        verify(resourceService, times(1)).loadAll();
    }


    @Test
    public void testSetupScenes() {

        // Verify the number of scenes created
        assert goodEndCutscene.scenes.size() == 5;

        // Verify the first scene has the correct background, animation, and text
        Scene scene1 = goodEndCutscene.scenes.getFirst();
        assert scene1.getBackgroundImagePath().equals("images/Cutscenes/good_end_0.png");
        assert scene1.getSceneText().size == 1;  // Text contains one item
    }


    @Test
    public void testCreateEntities() {
        // Mock a scene for entity creation
        goodEndCutscene.setupScenes();
        goodEndCutscene.loadScene(0);  // Load the first scene

        // Verify that the entity service is used to register the background entity
        verify(ServiceLocator.getEntityService(), times(1)).register(any(Entity.class));
    }


    @Test
    public void testUpdateTriggersCutsceneEnd() {
        // Mock game time to simulate the passing of time
        goodEndCutscene.setupScenes();
        goodEndCutscene.loadScene(0);  // Load the first scene
        when(gameTime.getTime()).thenReturn(0L, 4L);  // Simulate time passing

        // Mock the dispose method to avoid null pointer exception
        doNothing().when(entity).dispose();
        // Ensure that createdComponents in entity are initialized
        when(entity.getCreatedComponents()).thenReturn(new Array<>());
        assert Objects.equals(entity.getCreatedComponents(), new Array<Component>());

        // Call update before the scene duration is up
        goodEndCutscene.update();
        verify(entity, never()).getEvents();  // No events triggered before time is up

        // Set the entities to null to avoid disposing errors
        goodEndCutscene.entities = new ArrayList<>();
        // Set the number of scenes to 1 so that it simulates ending a cutscene
        goodEndCutscene.scenes = new ArrayList<>();

        // Call update after the duration has passed
        goodEndCutscene.update();
        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");  // Verify event trigger when cutscene ends
    }


    @Test
    public void testDisposeUnloadsAssets() {
        goodEndCutscene.dispose();

        verify(resourceService, times(1)).unloadAssets(new String[]{
                "images/Cutscenes/Beastly_Bistro_Background.png",
                "images/Cutscenes/Graveyard_Scene.png",
                "images/Cutscenes/good_end_0.png",
                "images/Cutscenes/good_end_1.png",
                "images/Cutscenes/good_end_2.png",
                "images/Cutscenes/good_end_3.png"
        });
    }

    @Test
    public void testNoTransitionWhenNoScenesRemain() {
        goodEndCutscene.setupScenes();
        goodEndCutscene.currentSceneIndex = goodEndCutscene.scenes.size() - 1;

        goodEndCutscene.nextCutscene();

        assert goodEndCutscene.currentSceneIndex == goodEndCutscene.scenes.size();
        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");  // Ensure the event is triggered
    }
    @Test
    public void testStartCreatesEntities() {
        goodEndCutscene.setupScenes();

        goodEndCutscene.start();

        verify(ServiceLocator.getEntityService(), times(goodEndCutscene.entities.size())).register(any(Entity.class));
    }

    @Test
    public void testSetTextForScene() {
        goodEndCutscene.setupScenes();
        goodEndCutscene.loadScene(0);
        assert goodEndCutscene.currentText.equals("\"Get out here\"");

        assert goodEndCutscene.textIndex == 1;
    }

    **/



}
