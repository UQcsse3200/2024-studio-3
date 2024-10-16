package com.csse3200.game.files;

import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
class GameStateTest {

    private GameState gameState;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
    }

    @Test
    void testSetAndGetDay() {
        gameState.setDay(5);
        assertEquals(5, gameState.getDay());
    }

    @Test
    void testSetAndGetMoney() {
        gameState.setMoney(1000);
        assertEquals(1000, gameState.getMoney());
    }

    @Test
    void testSetAndGetModTime() {
        gameState.setModTime("THIS AN EXAMPLE");
        assertEquals("THIS AN EXAMPLE", gameState.getModTime());
    }
}