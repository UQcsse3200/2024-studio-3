package com.csse3200.game.components.cutscenes;

import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.components.upgrades.UpgradesDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.services.ServiceLocator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GoodEndTest {

    private GoodEnd goodEnd;
    private TerrainFactory terrainFactory;
    private GdxGame.LevelType levelType;
    private UpgradesDisplay upgradesDisplay;
    private EntityService entityService;

    @Before
    public void setUp() {
        terrainFactory = mock(TerrainFactory.class);
        levelType = GdxGame.LevelType.LEVEL_1;  // Choose a suitable level type
        upgradesDisplay = mock(UpgradesDisplay.class);

        // Register EntityService with ServiceLocator to track entity spawning
        entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);

        // Initialize GoodEnd instance
        goodEnd = new GoodEnd(terrainFactory, levelType, upgradesDisplay);
    }

    @Test
    public void testTrigger() {
        // Trigger the cutscene
        goodEnd.trigger();

        // Verify that the create method was called and beef was spawned
        verify(entityService, times(1)).register(any(Entity.class));
    }

    @Test
    public void testSpawnBeefEnd() {
        // Prepare mock for beef entity
        Entity beefEntity = mock(Entity.class);
        when(ItemFactory.createBeef("cooked")).thenReturn(beefEntity);

        // Manually call the create method to test beef spawning
        goodEnd.create();

        // Verify the entity was spawned at the correct location
        verify(entityService, times(1)).register(beefEntity);

        // Verify the beef entity was scaled correctly
        verify(beefEntity, times(1)).setScale(0.5f, 0.5f);
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
