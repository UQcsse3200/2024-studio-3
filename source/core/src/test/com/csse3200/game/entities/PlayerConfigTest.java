package com.csse3200.game.entities.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerConfigTest {

    private PlayerConfig playerConfig;

    @BeforeEach
    public void setUp() {
        // Initialize a new PlayerConfig instance before each test
        playerConfig = new PlayerConfig();
    }

    @Test
    public void testDefaultInventorySize() {
        // Verify that the default inventory size is 1
        assertEquals(1, playerConfig.inventorySize, "Default inventory size should be 1");
    }

    @Test
    public void testDefaultGold() {
        // Verify that the default gold is 1
        assertEquals(1, playerConfig.gold, "Default gold should be 1");
    }

    @Test
    public void testDefaultFavouriteColour() {
        // Verify that the default favourite colour is "none"
        assertEquals("none", playerConfig.favouriteColour, "Default favourite colour should be 'none'");
    }

    @Test
    public void testDefaultSpritePath() {
        // Verify that the default sprite path is "images/player/player.atlas"
        assertEquals("images/player/player.atlas", playerConfig.spritePath, "Default sprite path should be 'images/player/player.atlas'");
    }
}
