package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;

import java.util.logging.Level;

public class LevelService {
    private final EventHandler levelEventHandler;
    private int currLevel;

    public LevelService() {
        levelEventHandler = new EventHandler();
        currLevel = 1;
    }

    public EventHandler getEvents() {
        return levelEventHandler;
    }

    public int getCurrLevel() {
        return currLevel;
    }

    public void incrementLevel() {
        currLevel++;
    }
}
