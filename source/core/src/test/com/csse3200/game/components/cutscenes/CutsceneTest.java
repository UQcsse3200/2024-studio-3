package com.csse3200.game.components.cutscenes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.AnimatedScene;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CutsceneTest {

    private Cutscene cutscene;
    private ResourceService resourceService;
    private GameTime gameTime;
    private EventHandler eventHandler;
    private Scene mockScene;

    @Before
    public void setUp() {
        resourceService = mock(ResourceService.class);
        gameTime = mock(GameTime.class);
        Entity entity = mock(Entity.class);
        eventHandler = mock(EventHandler.class);
        mockScene = mock(Scene.class);

        EntityService entityService = mock(EntityService.class);

        when(entity.getEvents()).thenReturn(eventHandler);
        when(gameTime.getTime()).thenReturn((long) 0);
        ServiceLocator.clear();
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerTimeSource(gameTime);
        ServiceLocator.registerEntityService(entityService);

        cutscene = new Cutscene() {
            @Override
            protected void setupScenes() {
                // Empty for testing
            }

            @Override
            protected void loadAssets() {
                // Empty for testing
            }

            @Override
            public void createEntities() {
                // Empty for testing
            }

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

    @Test
    public void testGetSetScenes() {
        List<Scene> scenes = new ArrayList<>();
        scenes.add(new Scene("Background"));
        cutscene.setScenes(scenes);
        assertEquals(scenes, cutscene.getScenes());
    }

    @Test
    public void testGetSetTextures() {
        String[] textures = new String[]{"example", "textures", "here"};
        cutscene.setTextures(textures);
        assertEquals(textures, cutscene.getTextures());
    }

    @Test
    public void testGetSetImages() {
        String[] images = new String[]{"example", "images", "here"};
        cutscene.setImages(images);
        assertEquals(images, cutscene.getImages());
    }

    @Test
    public void testGetSetAnimations() {
        String[] animations = new String[]{"example", "animations", "here"};
        cutscene.setAnimations(animations);
        assertEquals(animations, cutscene.getAnimations());
    }

    @Test
    public void testNextCutsceneMovesToNextScene() {
        String texturePath = "Example.path";
        String[] paths = new String[] {texturePath};
        resourceService.loadTextures(paths);
        Scene scene1 = new Scene("images/Cutscenes/Day2_Scene.png");
        Scene scene2 = new Scene("images/Cutscenes/Day2_Scene.png");

        List<Scene> scenes = new ArrayList<>();
        scenes.add(scene1);
        scenes.add(scene2);

        Texture mockTexture = mock(Texture.class);
        when(resourceService.getAsset("images/Cutscene/Day2_Scene.png", Texture.class))
                .thenReturn(mockTexture);

        cutscene.setScenes(scenes);
        cutscene.create();
        when(gameTime.getTime()).thenReturn((long) 6.0f);  // Simulate time passing

        cutscene.update();
        assertEquals(3, cutscene.currentSceneIndex);  // Should move to the next scene
    }

    @Test
    public void testNextCutsceneTriggersEndWhenNoScenesLeft() {
        Scene scene = mock(Scene.class);
        when(scene.getDuration()).thenReturn(5.0f);

        List<Scene> scenes = new ArrayList<>();
        scenes.add(scene);

        cutscene.setScenes(scenes);
        cutscene.create();
        cutscene.currentSceneIndex = 1;  // Simulate being at the end of the scenes

        cutscene.nextCutscene();
        verify(eventHandler, times(2)).trigger("cutsceneEnded");
    }

    @Test
    public void testTextForSceneChangesCorrectly() {
        Scene scene = mock(Scene.class);
        Array<String> sceneText = new Array<>();
        sceneText.add("Text 1");
        sceneText.add("Text 2");

        when(scene.getSceneText()).thenReturn(sceneText);
        List<Scene> scenes = new ArrayList<>();
        scenes.add(scene);

        cutscene.setScenes(scenes);
        cutscene.create();
        cutscene.setTextForScene(scene);

        assertEquals("Text 1", cutscene.currentText);

        cutscene.setTextForScene(scene);  // Move to next text
        assertEquals("Text 2", cutscene.currentText);
    }

    @Test
    public void testAnimatedSceneLoadsCorrectly() {
        cutscene.isAnimatedScenes = true;
        AnimatedScene animatedScene = mock(AnimatedScene.class);
        when(animatedScene.getDuration()).thenReturn(5.0f);
        when(animatedScene.getAtlasFilePath()).thenReturn("atlas.atlas");

        cutscene.animatedScenes.add(animatedScene);
        cutscene.loadScene(0);

        verify(resourceService, times(1)).loadTextureAtlases(new String[]{"atlas.atlas"});
    }

    @Test
    public void testNextCutsceneMoralDecision() {
        Scene scene1 = mock(Scene.class);
        Scene scene2 = mock(Scene.class);
        when(scene1.getSceneText()).thenReturn(new Array<>(new String[]{"Option A", "Option B"}));
        when(scene2.getSceneText()).thenReturn(new Array<>(new String[]{"Decision made"}));

        cutscene.scenes.add(scene1);
        cutscene.scenes.add(scene2);
        cutscene.create();

        cutscene.setTextForSceneMoral(scene1);
        assertEquals("Option A", cutscene.currentText);

        cutscene.setTextForSceneMoral(scene1);
        assertEquals("Option B", cutscene.currentText);

        // After exhausting text, move to the next scene
        cutscene.setTextForSceneMoral(scene1);
        assertEquals(1, cutscene.currentSceneIndex);
        cutscene.setTextForSceneMoral(scene2);
        assertEquals("Decision made", cutscene.currentText);
    }

    @Test
    public void testSceneTransitionAfterDuration() {
        Scene scene1 = mock(Scene.class);
        when(scene1.getDuration()).thenReturn(3.0f);
        List<Scene> scenes = new ArrayList<>();
        scenes.add(scene1);

        cutscene.setScenes(scenes);
        cutscene.create();
        when(gameTime.getTime()).thenReturn((long) 4.0f);

        cutscene.update();
        verify(eventHandler, times(2)).trigger("cutsceneEnded");  // Should trigger after scene ends
    }
}
