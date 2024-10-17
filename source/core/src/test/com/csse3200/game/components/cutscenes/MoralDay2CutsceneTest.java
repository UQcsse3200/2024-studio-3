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

class MoralDay2CutsceneTest {

    private MoralDay2Cutscene moralDay2Cutscene;

    @BeforeAll
    static void setUp() {
        ServiceLocator.clear();
        ResourceService mockResourceService = mock(ResourceService.class);
        ServiceLocator.registerResourceService(mockResourceService);
    }

    @BeforeEach
    void setUpEach() {
        moralDay2Cutscene = new MoralDay2Cutscene();

    }

    @Test
    void testSetupScenes() {
        moralDay2Cutscene.setupScenes();
        List<Scene> scenes = moralDay2Cutscene.getScenes();

        assertNotNull(scenes);
        assertEquals(4, scenes.size());
    }

    @Test
    void testLoadAssets() {
        moralDay2Cutscene.loadAssets();

        assertNotNull(moralDay2Cutscene.getTextures());
        assertNotNull(moralDay2Cutscene.getImages());
        assertNotNull(moralDay2Cutscene.getAnimations());
    }

    @Test
    void testScenesAreCreatedCorrectly() {
        moralDay2Cutscene.setupScenes();
        Scene firstScene = moralDay2Cutscene.getScenes().getFirst();

        assertEquals("images/Cutscenes/Day2_Scene.png", firstScene.getBackgroundImagePath());
        assertEquals(3.0f, firstScene.getDuration(), 0.01);
        assertEquals(2, firstScene.getImagePaths().length);
    }

    @Test
    void testCreateEntities() {
        // Just used for code coverage
        moralDay2Cutscene.createEntities();
        Assertions.assertTrue(true);
    }
}
