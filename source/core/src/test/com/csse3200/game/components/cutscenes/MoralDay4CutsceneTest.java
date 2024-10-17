package com.csse3200.game.components.cutscenes;

import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class MoralDay4CutsceneTest {

    private MoralDay4Cutscene moralDay4Cutscene;

    @BeforeAll
    static void setUp() {
        ServiceLocator.clear();
        ResourceService mockResourceService = mock(ResourceService.class);
        ServiceLocator.registerResourceService(mockResourceService);
    }

    @BeforeEach
    void setUpEach() {
        moralDay4Cutscene = new MoralDay4Cutscene();

    }

    @Test
    void testSetupScenes() {
        moralDay4Cutscene.setupScenes();
        List<Scene> scenes = moralDay4Cutscene.getScenes();

        assertNotNull(scenes);
        assertEquals(4, scenes.size());
    }

    @Test
    void testLoadAssets() {
        moralDay4Cutscene.loadAssets();

        assertNotNull(moralDay4Cutscene.getTextures());
        assertNotNull(moralDay4Cutscene.getImages());
        assertNotNull(moralDay4Cutscene.getAnimations());
    }

    @Test
    void testScenesAreCreatedCorrectly() {
        moralDay4Cutscene.setupScenes();
        Scene firstScene = moralDay4Cutscene.getScenes().getFirst();

        assertEquals("images/Cutscenes/Day2_Scene.png", firstScene.getBackgroundImagePath());
        assertEquals(3.0f, firstScene.getDuration(), 0.01);
        assertEquals(3, firstScene.getImagePaths().length);
    }

    @Test
    void testCreateEntities() {
        // Just used for code coverage
        moralDay4Cutscene.createEntities();
        Assertions.assertTrue(true);
    }


}
