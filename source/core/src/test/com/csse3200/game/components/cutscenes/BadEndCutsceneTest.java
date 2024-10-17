package com.csse3200.game.components.cutscenes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.csse3200.game.components.cutscenes.scenes.AnimatedScene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

class BadEndCutsceneTest {

    private BadEndCutscene badEndCutscene;
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        // Mock the ServiceLocator's ResourceService
        resourceService = Mockito.mock(ResourceService.class);
        ServiceLocator.registerResourceService(resourceService);

        // Create an instance of BadEndCutscene
        badEndCutscene = new BadEndCutscene();
    }

    @Test
    void testConstructor() {
        // Verify the initial state of the object
        assertTrue(badEndCutscene.isAnimatedScenes);
    }

    @Test
    void testSetupScenes() {
        // Act
        badEndCutscene.setupScenes();

        // Assert
        assertEquals(16, badEndCutscene.getAnimatedScenes().size()); // Assuming getAnimatedScenes() is accessible
        AnimatedScene firstScene = badEndCutscene.getAnimatedScenes().get(0);
        assertEquals("bad_end", firstScene.getAnimName());
        assertEquals("images/Cutscenes/cutscene_badEnd.atlas", firstScene.getAtlasFilePath());
        assertEquals(20, firstScene.getDuration());
    }

    @Test
    void testCreateEntities() {
        // Since this method is redundant, we simply call it to ensure no exception occurs
        assertDoesNotThrow(() -> badEndCutscene.createEntities());
    }
}