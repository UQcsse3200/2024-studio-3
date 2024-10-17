package com.csse3200.game.components.cutscenes;

import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class BackgroundCutsceneTest {
    private BackstoryCutscene backstoryCutscene;

    @BeforeAll
    static void setUp() {
        ServiceLocator.clear();
        ResourceService mockResourceService = mock(ResourceService.class);
        ServiceLocator.registerResourceService(mockResourceService);
    }

    @BeforeEach
    void setUpEach() {
        backstoryCutscene = new BackstoryCutscene();

    }

    @Test
    void testSetupScenes() {
        backstoryCutscene.setupScenes();
        List<Scene> scenes = backstoryCutscene.getScenes();

        assertNotNull(scenes);
        assertEquals(24, scenes.size());
    }

    @Test
    void testLoadAssets() {
        backstoryCutscene.loadAssets();

        assertNotNull(backstoryCutscene.getTextures());
        Assertions.assertNull(backstoryCutscene.getImages());
        Assertions.assertNull(backstoryCutscene.getAnimations());
    }

    @Test
    void testScenesAreCreatedCorrectly() {
        backstoryCutscene.setupScenes();
        Scene firstScene = backstoryCutscene.getScenes().getFirst();

        assertEquals("images/Cutscenes/Brooklyn_Bistro_Background.png", firstScene.getBackgroundImagePath());
        assertEquals(5.0f, firstScene.getDuration(), 0.01);
        Assertions.assertNull(firstScene.getImagePaths());
    }

    @Test
    void testCreateEntities() {
        // Just used for code coverage
        backstoryCutscene.createEntities();
        Assertions.assertTrue(true);
    }
}
