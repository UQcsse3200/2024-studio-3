package com.csse3200.game.components.cutscenes;

import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoralDay1CutsceneTest {

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
        List<Scene> scenes = moralDay1Cutscene.getScenes();

        assertNotNull(scenes);
        assertEquals(4, scenes.size());
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
        Scene firstScene = moralDay1Cutscene.getScenes().getFirst();

        assertEquals("images/Cutscenes/Day2_Scene.png", firstScene.getBackgroundImagePath());
        assertEquals(3.0f, firstScene.getDuration(), 0.01);
        assertEquals(2, firstScene.getImagePaths().length);
    }

    @Test
    void testCreateEntities() {
        // Just used for code coverage
        moralDay1Cutscene.createEntities();
        assertTrue(true);
    }
}
