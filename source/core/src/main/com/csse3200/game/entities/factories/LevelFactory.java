package com.csse3200.game.entities.factories;

import com.csse3200.game.components.levels.LevelComponent;
import com.csse3200.game.entities.Entity;

public class LevelFactory {
    public static Entity createSpawnControllerEntity() {
        return new Entity().addComponent(new LevelComponent());
    }
}
