//package com.csse3200.game.components.cutscenes;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Graphics;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.utils.Array;
//import com.csse3200.game.components.Component;
//import com.csse3200.game.components.cutscenes.scenes.Scene;
//import com.csse3200.game.entities.Entity;
//import com.csse3200.game.entities.EntityService;
//import com.csse3200.game.events.EventHandler;
//import com.csse3200.game.services.GameTime;
//import com.csse3200.game.services.ResourceService;
//import com.csse3200.game.services.ServiceLocator;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//public class Day2CutsceneTest {
//
//    private Day2Cutscene day2Cutscene;
//    private ResourceService resourceService;
//    private GameTime gameTime;
//    private Entity entity;
//
//    @Before
//    public void setUp() {
//        resourceService = mock(ResourceService.class);
//        gameTime = mock(GameTime.class);
//        entity = mock(Entity.class);
//        EventHandler eventHandler = mock(EventHandler.class);
//        Texture mockTexture = mock(Texture.class);
//        Graphics mockGraphics = mock(Graphics.class);
//
//        when(entity.getEvents()).thenReturn(eventHandler);
//        when(mockTexture.getHeight()).thenReturn(600);
//        when(mockTexture.getWidth()).thenReturn(800);
//        when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(mockTexture);
//        Gdx.graphics = mockGraphics;
//        when(mockGraphics.getHeight()).thenReturn(1200);
//
//        ServiceLocator.registerResourceService(resourceService);
//        ServiceLocator.registerTimeSource(gameTime);
//        ServiceLocator.registerEntityService(mock(EntityService.class));
//
//        day2Cutscene = new Day2Cutscene() {
//            @Override
//            protected void nextCutscene() {
//                disposeEntities();
//                currentSceneIndex++;
//                if (currentSceneIndex < scenes.size()) {
//                    loadScene(currentSceneIndex);
//                } else {
//                    entity.getEvents().trigger("cutsceneEnded");
//                }
//            }
//        };
//
//        day2Cutscene.setEntity(entity);
//    }
//
//    @After
//    public void tearDown() {
//        ServiceLocator.clear();
//    }
//
//    @Test
//    public void testLoadAssets() {
//        day2Cutscene.loadAssets();
//        verify(resourceService, times(2)).loadTextures(new String[]{
//                "images/Cutscenes/Day2_Scene.png"
//        });
//        verify(resourceService, times(2)).loadTextures(new String[]{
//                "images/Cutscenes/Character Artwork/reporter_sprite.png",
//                "images/Cutscenes/Character Artwork/player_sprite_back_turned.png",
//                "images/Cutscenes/ExclamationMark.png"
//        });
//        verify(resourceService, times(2)).loadAll();
//    }
//
//    @Test
//    public void testSetupScenes() {
//        day2Cutscene.setupScenes();  // Set up the scenes
//
//        // Log the number of scenes for debugging
//        System.out.println("Number of scenes: " + day2Cutscene.scenes.size());
//
//        // Check that there are exactly 2 scenes created
//        assert day2Cutscene.scenes.size() == 4 : "Expected 2 scenes but got " + day2Cutscene.scenes.size();
//
//        // Verify that the first scene has the correct background and text
//        Scene scene1 = day2Cutscene.scenes.get(0);
//        assert scene1.getBackgroundImagePath().equals("images/Cutscenes/Day2_Scene.png");
//        assert scene1.getSceneText().size == 3 : "Scene 1 text size mismatch";
//
//        // Verify that the second scene has the correct background and text
//        Scene scene2 = day2Cutscene.scenes.get(1);
//        assert scene2.getBackgroundImagePath().equals("images/Cutscenes/Day2_Scene.png");
//        assert scene2.getSceneText().size == 5 : "Scene 2 text size mismatch";
//    }
//
//
//
//    @Test
//    public void testCreateEntities() {
//        day2Cutscene.setupScenes();
//        day2Cutscene.loadScene(0);
//        verify(ServiceLocator.getEntityService(), times(3)).register(any(Entity.class));
//    }
//
//    @Test
//    public void testUpdateTriggersCutsceneEnd() {
//        day2Cutscene.setupScenes();
//        day2Cutscene.loadScene(0);
//        when(gameTime.getTime()).thenReturn(0L, 4L);
//        doNothing().when(entity).dispose();
//        when(entity.getCreatedComponents()).thenReturn(new Array<>());
//        assert Objects.equals(entity.getCreatedComponents(), new Array<Component>());
//
//        day2Cutscene.update();
//        verify(entity, never()).getEvents();
//
//        day2Cutscene.entities = new ArrayList<>();
//        day2Cutscene.scenes = new ArrayList<>();
//
//        day2Cutscene.update();
//        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");
//    }
//
//    @Test
//    public void testDisposeUnloadsAssets() {
//        // Trigger the disposal of assets
//        day2Cutscene.dispose();
//
//        // Verify that Day2_Scene.png is unloaded in the first call
//        verify(resourceService, times(1)).unloadAssets(new String[]{
//                "images/Cutscenes/Day2_Scene.png"
//        });
//
//
//    }
//
//
//
//    @Test
//    public void testNoTransitionWhenNoScenesRemain() {
//        day2Cutscene.setupScenes();
//        day2Cutscene.currentSceneIndex = day2Cutscene.scenes.size() - 1;
//        day2Cutscene.nextCutscene();
//        assert day2Cutscene.currentSceneIndex == day2Cutscene.scenes.size();
//        verify(entity.getEvents(), times(1)).trigger("cutsceneEnded");
//    }
//
//    @Test
//    public void testStartCreatesEntities() {
//        day2Cutscene.setupScenes();
//        day2Cutscene.start();
//        verify(ServiceLocator.getEntityService(), times(day2Cutscene.entities.size())).register(any(Entity.class));
//    }
//
//    @Test
//    public void testSetTextForScene() {
//        day2Cutscene.setupScenes();  // Set up the scenes
//        day2Cutscene.loadScene(0);    // Load the first scene (Scene 1)
//
//
//        // Check that the current text is the first line of Scene 1
//        assert day2Cutscene.currentText.equals("Reporter > Good morning! Surprise, surprise! I hope you're ready. ");
//
//        // Check that the textIndex has been updated to 1 (next line)
//        assert day2Cutscene.textIndex == 1;
//    }
//
//}
