package com.csse3200.game.entities.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaseEntityConfigTest {

    private BaseEntityConfig baseEntityConfig;

    @BeforeEach
    public void setUp() {
        // Initialize a new BaseEntityConfig instance before each test
        baseEntityConfig = new BaseEntityConfig();
    }

    @Test
    public void testDefaultHealth() {
        // Verify that the default health is 1
        assertEquals(1, baseEntityConfig.health, "Default health should be 1");
    }

    @Test
    public void testDefaultBaseAttack() {
        // Verify that the default base attack is 0
        assertEquals(0, baseEntityConfig.baseAttack, "Default base attack should be 0");
    }
}
