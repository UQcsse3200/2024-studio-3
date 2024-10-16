package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MoralDay1CutsceneTest {

    private MoralDay1Cutscene moralDay1Cutscene;

    @BeforeAll
    static void setUp() {
        ServiceLocator.clear();
        ResourceService mockResourceService = mock(ResourceService.class);
        ServiceLocator.registerResourceService(mockResourceService);
    }

    @BeforeEach
    void setUpEach() {
        moralDay1Cutscene = new MoralDay1Cutscene();

    }

    @Test
    void testSetupScenes() {
        moralDay1Cutscene.setupScenes();
        Array<Scene> scenes = moralDay1Cutscene.getScenes();

        assertNotNull(scenes);
        assertEquals(2, scenes.size);
    }

    @Test
    void testLoadAssets() {
        moralDay1Cutscene.loadAssets();

        assertNotNull(moralDay1Cutscene.getTextures());
        assertNotNull(moralDay1Cutscene.getImages());
        assertNotNull(moralDay1Cutscene.getAnimations());
    }

    @Test
    void testScenesAreCreatedCorrectly() {
        moralDay1Cutscene.setupScenes();
        Scene firstScene = moralDay1Cutscene.getScenes().get(0);

        assertEquals("images/Cutscenes/Day2_Scene.png", firstScene.getBackgroundImagePath());
        assertEquals(3.0f, firstScene.getDuration(), 0.01);
        assertEquals(2, firstScene.getImagePaths().length);
    }

    @Test
    void testGetSetScenes() {
        Array<Scene> scenes = new Array<>();
        scenes.add(new Scene("Background"));
        moralDay1Cutscene.setScenes(scenes);
        assertEquals(scenes, moralDay1Cutscene.getScenes());
    }

    @Test
    void testGetSetTextures() {
        String[] textures = new String[]{"example", "textures", "here"};
        moralDay1Cutscene.setTextures(textures);
        assertEquals(textures, moralDay1Cutscene.getTextures());
    }

    @Test
    void testGetSetImages() {
        String[] images = new String[]{"example", "images", "here"};
        moralDay1Cutscene.setImages(images);
        assertEquals(images, moralDay1Cutscene.getImages());
    }

    @Test
    void testGetSetAnimations() {
        String[] animations = new String[]{"example", "animations", "here"};
        moralDay1Cutscene.setAnimations(animations);
        assertEquals(animations, moralDay1Cutscene.getAnimations());
    }

    @Test
    void testCreateEntities() {
        // Just used for code coverage
        moralDay1Cutscene.createEntities();
    }
}
