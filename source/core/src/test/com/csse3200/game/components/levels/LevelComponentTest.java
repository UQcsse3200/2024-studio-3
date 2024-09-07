package com.csse3200.game.components.levels;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.LevelService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;

class LevelComponentTest {
    @Spy LevelComponent levelComponentSpy;
    @Spy Entity entitySpy;

    @BeforeEach
    void setUp() {
        entitySpy.addComponent(levelComponentSpy);
    }
}