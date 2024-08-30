package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;

import java.util.logging.Level;

public class LevelService {
    private final EventHandler levelEventHandler;

    public LevelService() {
        levelEventHandler = new EventHandler();
    }

    public EventHandler getEvents() {
        return levelEventHandler;
    }
}
