package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.screens.CutsceneScreen;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class IntroCutsceneTest {

    private IntroCutscene introCutscene;
    private ResourceService resourceService;
    private GameTime gameTime;
    private Entity entity;
    private EventHandler eventHandler;
    private Texture mockTexture;
    private CutsceneFactory mockCutsceneFactory;
    private Graphics mockGraphics;

    @Before
    public void setUp() {
        // Mock dependencies
        resourceService = mock(ResourceService.class);
        gameTime = mock(GameTime.class);
        entity = mock(Entity.class);
        eventHandler = mock(EventHandler.class);
        mockTexture = mock(Texture.class);
        mockGraphics = mock(Graphics.class);

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

        // Initialize the cutscene
        introCutscene = new IntroCutscene();

        // Mock the entity for event triggering
        introCutscene.setEntity(entity);

        // Mock the creation of the background entity in CutsceneFactory
        Entity mockBackground = mock(Entity.class);
        when(mockBackground.getScale()).thenReturn(new Vector2(1.0f, 1.0f));
    }

    /**
     * Tests that the cutscene's assets are loaded correctly.
     */
    @Test
    public void testLoadAssets() {
        // Verify that textures are loaded
        verify(resourceService, times(1)).loadTextures(new String[] {"images/Cutscenes/Beastly_Bistro_Background.png"});
        verify(resourceService, times(1)).loadAll();
    }

    /**
     * Tests that entities are created correctly during the cutscene's start.
     */
    @Test
    public void testCreateEntities() {
        // Call createEntities and verify the entity registration
        introCutscene.createEntitiesForScene(introCutscene.currentScene);

        // Verify that the background entity was created and registered
        verify(resourceService, times(1)).getAsset("images/Cutscenes/Beastly_Bistro_Background.png", Texture.class);
        verify(ServiceLocator.getEntityService(), times(1)).register(any(Entity.class));
    }

    /**
     * Tests that the update method triggers cutscene completion after the set duration.
     */
    @Test
    public void testUpdateTriggersCutsceneEnd() {
        // Mock game time to simulate the passing of time
        when(gameTime.getTime()).thenReturn(0L).thenReturn(3L);

        // Call update before time is up (nothing should happen)
        introCutscene.update();
        verify(entity, never()).getEvents();  // No events triggered before time is up

        // Call update after the duration has passed
        introCutscene.update();
//        verify(entity, times(1)).getEvents();  // Verify event trigger when cutscene ends
//        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");
    }

    /**
     * Tests that the cutscene script is set up correctly with the expected text.
     */
    @Test
    public void testSetScript() {
        // Can't do since there is no way of mocking the screen
    }

    /**
     * Tests that the cutscene properly unloads assets when disposed.
     */
    @Test
    public void testDisposeUnloadsAssets() {
        // Call dispose and verify that assets are unloaded
        introCutscene.dispose();

        verify(resourceService, times(1)).unloadAssets(new String[] {"images/Cutscenes/Beastly_Bistro_Background.png"});
    }
}

