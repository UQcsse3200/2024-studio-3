package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;

import java.util.logging.Level;

/**Allows for global access and control of the game levels*/
public class LevelService {
    private final EventHandler levelEventHandler;
    private int currLevel;

    /**
     * Constructor method, initialises both private variables
     */
    public LevelService() {
        levelEventHandler = new EventHandler();
        currLevel = 1;
        //levelEventHandler.addListener("nextLevel", someClass::someMethod, currLevel);
    }

    /**
     * Gets the private variable containing an EventHandler object and returns it
     *
     * @return  the EventHandler object for this service
     */
    public EventHandler getEvents() {
        return levelEventHandler;
    }

    /**
     * Gets the integer representation of the current level of the game
     *
     * @return  the current level number
     */
    public int getCurrLevel() {
        return currLevel;
    }

    /**
     * Increases the integer representing the current level by one
     */
    public void incrementLevel() {
        currLevel++;
    }

    /**
     * Manually set the level number
     *
     * @param newLevel the new level number
     */
    public void setCurrLevel(int newLevel) {
        currLevel = newLevel;
    }
}
