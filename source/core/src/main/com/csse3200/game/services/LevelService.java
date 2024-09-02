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
        switch (level) {
            case 1:
                levelEventHandler.trigger("startSpawning", 5);
                /*CURRENT IDEA FOR FIX
                *
                * Private variable that gets reassigned when this method is called
                * The variable has a start time, calculate time since then,
                * if enough time has passed, new customer
                *
                * Basically what exists but more sophisticated
                *
                * Maybe, just maybe. Make an entity to hijack the update method?????
                * */

        }
    }
}
