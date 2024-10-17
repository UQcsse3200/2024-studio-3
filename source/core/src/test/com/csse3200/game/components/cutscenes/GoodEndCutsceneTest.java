package com.csse3200.game.components.cutscenes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.csse3200.game.components.cutscenes.scenes.AnimatedScene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

class GoodEndCutsceneTest {

    private GoodEndCutscene goodEndCutscene;

    @BeforeEach
    void setUp() {
        // Mock the ServiceLocator's ResourceService
        ResourceService resourceService = Mockito.mock(ResourceService.class);
        ServiceLocator.registerResourceService(resourceService);

        // Create an instance of BadEndCutscene
        goodEndCutscene = new GoodEndCutscene();
    }

    @Test
    void testConstructor() {
        // Verify the initial state of the object
        assertTrue(goodEndCutscene.isAnimatedScenes);
    }

    @Test
    void testSetupScenes() {
        // Act
        goodEndCutscene.setupScenes();

        // Assert
        assertEquals(14, goodEndCutscene.getAnimatedScenes().size()); // Assuming getAnimatedScenes() is accessible
        AnimatedScene firstScene = goodEndCutscene.getAnimatedScenes().get(0);
        assertEquals("good_end1", firstScene.getAnimName());
        assertEquals("images/Cutscenes/cutscene_goodEnd1.atlas", firstScene.getAtlasFilePath());
        assertEquals(20, firstScene.getDuration());
    }

    @Test
    void testCreateEntities() {
        // Since this method is redundant, we simply call it to ensure no exception occurs
        assertDoesNotThrow(() -> goodEndCutscene.createEntities());
    }

}
