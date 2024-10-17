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
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BackstoryCutsceneTest {

    private BackstoryCutscene backstoryCutscene;
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

        // Initialise the BackstoryCutscene with custom behavior for nextCutscene
        backstoryCutscene = new BackstoryCutscene() {
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

        // Set the entity in the cutscene
        backstoryCutscene.setEntity(entity);
    }

    @After
    public void tearDown() {
        ServiceLocator.clear();
    }

    @Test
    public void testLoadAssets() {
        // Verify that the textures are loaded with the correct paths
        verify(resourceService, times(1)).loadTextures(new String[]{
                "images/Cutscenes/Brooklyn_Bistro_Background.png",
                "images/Cutscenes/Kitchen_Background.png",
                "images/Cutscenes/Food_Critic_Background.png",
                "images/Cutscenes/Animals_in_Kitchen_Background.png",
                "images/Cutscenes/Farm_Background.png",
                "images/Cutscenes/graveyard_mafia.png",
                "images/Cutscenes/deserted_city_opt1.png",
                "images/Cutscenes/graveyard_mafia_chef.png",
                "images/Cutscenes/new_beastly_bistro_pt2.png",
                "images/Cutscenes/new_beastly_bistro.png",
                "images/Cutscenes/resized_black_image.png"
        });

        // Verify that loadAll is called exactly once
        verify(resourceService, times(1)).loadAll();

    }

    /**
     * Tests that the scenes are set up with the correct number of scenes, background, and text.
     */
    @Test
    public void testSetupScenes() {

        // Verify the number of scenes created
        assertEquals(12, backstoryCutscene.scenes.size()); // Should match the total number of scenes added

        // Verify the first scene has the correct background, animation, and text
        Scene scene1 = backstoryCutscene.scenes.getFirst();
        assert scene1.getBackgroundImagePath().equals("images/Cutscenes/Brooklyn_Bistro_Background.png");
        assert scene1.getSceneText().size == 1;  // Text contains one item
        assert scene1.getSceneText().get(0).equals("You were once an esteemed chef at the Brooklyn Bistro, \n" +
                "an establishment specialising in only the finest cuisine... ");
    }

    /**
     * Tests that entities are created correctly during the cutscene's start.
     */
    @Test
    public void testCreateEntities() {
        // Setup and load the first scene
        backstoryCutscene.setupScenes();
        backstoryCutscene.loadScene(0);

        // Verify that the entity service is used to register the background entity
        verify(ServiceLocator.getEntityService(), times(1)).register(any(Entity.class));
    }

    /**
     * Tests that the update method triggers cutscene completion after the set duration.
     */
    @Test
    public void testUpdateTriggersCutsceneEnd() {
        // Mock game time to simulate the passing of time
        backstoryCutscene.setupScenes();
        backstoryCutscene.loadScene(0);  // Load the first scene
        when(gameTime.getTime()).thenReturn(0L, 5000L);  // Simulate time passing

        // Mock the disposeEntities method to avoid null pointer exception
        doNothing().when(entity).dispose();
        // Ensure that createdComponents in entity are initialized
        when(entity.getCreatedComponents()).thenReturn(new Array<>());
        assert Objects.equals(entity.getCreatedComponents(), new Array<>());

        // Call update before the scene duration is up
        backstoryCutscene.update();
        verify(entity, never()).getEvents();  // No events triggered before time is up

        // Set the entities to null or empty to avoid disposing errors
        backstoryCutscene.entities = new ArrayList<>();
        // Simulate the cutscene ending by setting the number of scenes to 1
        backstoryCutscene.scenes = new ArrayList<>();

        // Call update after the duration has passed
        backstoryCutscene.update();
        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");  // Verify event trigger when cutscene ends
    }


    /**
     * Tests that the cutscene properly unloads assets when disposed.
     */
    @Test
    public void testDisposeUnloadsAssets() {
        // Call dispose to unload assets
        backstoryCutscene.dispose();

        // Verify that assets are unloaded with the correct paths
        verify(resourceService, times(1)).unloadAssets(new String[]{
                "images/Cutscenes/Brooklyn_Bistro_Background.png",
                "images/Cutscenes/Kitchen_Background.png",
                "images/Cutscenes/Food_Critic_Background.png",
                "images/Cutscenes/Animals_in_Kitchen_Background.png",
                "images/Cutscenes/Farm_Background.png",
                "images/Cutscenes/graveyard_mafia.png",
                "images/Cutscenes/deserted_city_opt1.png",
                "images/Cutscenes/graveyard_mafia_chef.png",
                "images/Cutscenes/new_beastly_bistro_pt2.png",
                "images/Cutscenes/new_beastly_bistro.png",
                "images/Cutscenes/resized_black_image.png"
        });
    }
}

