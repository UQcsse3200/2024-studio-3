package com.csse3200.game.components.cutscenes;

import com.csse3200.game.entities.EntityService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GoodEndTest {

    @Before
    public void setUp() {
        // Register EntityService with ServiceLocator to track entity spawning
        EntityService entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);
    }

    @Test
    public void testTexturesLoaded() {
        String[] expectedTextures = {
                "images/ingredients/raw_beef.png",
                "images/ingredients/cooked_beef.png",
                "images/ingredients/burnt_beef.png"
        };

        // Verify that the forest textures are correctly defined
        assertArrayEquals(expectedTextures, GoodEnd.getForestTextures());
    }
}
