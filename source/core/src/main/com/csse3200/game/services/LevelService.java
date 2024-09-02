package com.csse3200.game.services;

import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.screens.MainGameScreen;
import org.slf4j.LoggerFactory;

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
        levelEventHandler.addListener("startLevel", this::levelControl);
        levelEventHandler.addListener("createCustomer", ForestGameArea::spawnCustomer);
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

    /**
     * Depending on what level the game is switching to,
     * a method relating to each level will be called, in order to
     * spawn customers with different parameters
     *
     * @param level the level number
     */
    public void levelControl(int level) {
        int spawnCap = 0;
        switch (level) {
            case 1:
                spawnCap = 5;
            case 2:
                spawnCap = 7;
            case 3:
                spawnCap = 8;
        }
        levelEventHandler.trigger("startSpawning", spawnCap);
    }
}
