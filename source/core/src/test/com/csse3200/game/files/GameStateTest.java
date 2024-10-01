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
    private static final Logger logger = LoggerFactory.getLogger(GameStateTest.class);
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
    void testSetAndGetModDay() {
        gameState.setModDay(12);
        assertEquals(12, gameState.getModDay());
    }

    @Test
    void testSetAndGetModHour() {
        gameState.setModHour(10);
        assertEquals(10, gameState.getModHour());
    }

    @Test
    void testSetAndGetModMin() {
        gameState.setModMin(45);
        assertEquals(45, gameState.getModMin());
    }
}