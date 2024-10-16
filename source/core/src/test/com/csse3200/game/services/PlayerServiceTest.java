package com.csse3200.game.services;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class PlayerServiceTest {

    private PlayerService playerService;
    private Entity mockPlayer;

    @BeforeEach
    void setUp() {
        // Initialize PlayerService and mock player entity
        playerService = new PlayerService();
        mockPlayer = mock(Entity.class);
    }

    @Test
    public void testGetEvents() {
        // Test if the EventHandler is properly initialized and not null
        EventHandler eventHandler = playerService.getEvents();
        assertNotNull(eventHandler, "The event handler should not be null");
    }

    @Test
    void testRegisterPlayer() {
        // Register a mock player entity and check if it's correctly stored
        playerService.registerPlayer(mockPlayer);
        assertEquals(mockPlayer, playerService.getPlayer(), "The registered player should match the retrieved player");
    }

    @Test
    void testGetPlayer() {
        // Register a mock player entity and check if getPlayer returns the correct entity
        playerService.registerPlayer(mockPlayer);
        Entity player = playerService.getPlayer();
        assertNotNull(player, "The player should not be null after registration");
        assertEquals(mockPlayer, player, "The retrieved player should be the registered player");
    }

    @Test
    void testGetPlayerWhenNotRegistered() {
        // Test that getPlayer returns null if no player has been registered
        Entity player = playerService.getPlayer();
        assertNull(player, "The player should be null if no player is registered");
    }
}
