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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LoseEndTest {

    private LoseCutscene loseCutscene;
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
        loseCutscene = new LoseCutscene() {
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

        // Mock the entity for event triggering
        loseCutscene.setEntity(entity);
    }

    @After
    public void tearDown() {
        ServiceLocator.clear();
    }

    /**
     * Tests that the cutscene's assets are loaded correctly.
     */
    @Test
    public void testLoadAssets() {
        // Verify that the textures and animations are loaded
        verify(resourceService, times(1)).loadTextures(new String[]{
                "images/Cutscenes/Beastly_Bistro_Background.png",
                "images/Cutscenes/Graveyard_Scene.png"
        });
        verify(resourceService, times(1)).loadTextureAtlases(new String[]{"images/player/Cook_Model32.png"});
        verify(resourceService, times(1)).loadAll();
    }

    /**
     * Tests that the scenes are set up with the correct text, background, and animation.
     */
    @Test
    public void testSetupScenes() {

        // Verify the number of scenes created
        assert loseCutscene.scenes.size() == 1;

        // Verify the first scene has the correct background, animation, and text
        Scene scene1 = loseCutscene.scenes.getFirst();
        assert scene1.getBackgroundImagePath().equals("images/Cutscenes/Graveyard_Scene.png");
        assertEquals(2, scene1.getSceneText().size);  // Text contains two items
    }

    /**
     * Tests that entities are created correctly during the cutscene's start.
     */
    @Test
    public void testCreateEntities() {
        // Mock a scene for entity creation
        loseCutscene.setupScenes();
        loseCutscene.loadScene(0);  // Load the first scene

        // Verify that the entity service is used to register the background entity
        verify(ServiceLocator.getEntityService(), times(1)).register(any(Entity.class));
    }

    /**
     * Tests that the update method triggers cutscene completion after the set duration.
     */
    @Test
    public void testUpdateTriggersCutsceneEnd() {
        // Mock game time to simulate the passing of time
        loseCutscene.setupScenes();
        loseCutscene.loadScene(0);  // Load the first scene
        when(gameTime.getTime()).thenReturn(0L, 4L);  // Simulate time passing

        // Mock the dispose method to avoid null pointer exception
        doNothing().when(entity).dispose();
        // Ensure that createdComponents in entity are initialized
        when(entity.getCreatedComponents()).thenReturn(new Array<>());
        assert Objects.equals(entity.getCreatedComponents(), new Array<Component>());

        // Call update before the scene duration is up
        loseCutscene.update();
        verify(entity, never()).getEvents();  // No events triggered before time is up

        // Set the entities to null to avoid disposing errors
        loseCutscene.entities = new ArrayList<>();
        // Set the number of scenes to 1 so that it simulates ending a cutscene
        loseCutscene.scenes = new ArrayList<>();

        // Call update after the duration has passed
        loseCutscene.update();
        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");  // Verify event trigger when cutscene ends
    }

    /**
     * Tests that the cutscene properly unloads assets when disposed.
     */
    @Test
    public void testDisposeUnloadsAssets() {
        loseCutscene.dispose();

        verify(resourceService, times(1)).unloadAssets(new String[]{
                "images/Cutscenes/Beastly_Bistro_Background.png",
                "images/Cutscenes/Graveyard_Scene.png"
        });
    }

    @Test
    public void testNoTransitionWhenNoScenesRemain() {
        loseCutscene.setupScenes();
        loseCutscene.currentSceneIndex = loseCutscene.scenes.size() - 1;

        loseCutscene.nextCutscene();

        assert loseCutscene.currentSceneIndex == loseCutscene.scenes.size();
        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");  // Ensure the event is triggered
    }
    @Test
    public void testStartCreatesEntities() {
        loseCutscene.setupScenes();

        loseCutscene.start();

        verify(ServiceLocator.getEntityService(), times(loseCutscene.entities.size())).register(any(Entity.class));
    }

    @Test
    public void testSetTextForScene() {
        loseCutscene.setupScenes();
        loseCutscene.loadScene(0);
        assert loseCutscene.currentText.equals("\"Its quite a shame...\"");

        assertEquals(1, loseCutscene.textIndex);
    }

}
