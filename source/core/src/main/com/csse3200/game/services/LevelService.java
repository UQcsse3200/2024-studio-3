package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**Allows for global access and control of the game levels*/
public class LevelService {
    private static final Logger logger = LoggerFactory.getLogger(LevelService.class);
    private EventHandler levelEventHandler;
    private int currLevel;
    private int currGold;
    private boolean playerFinishedLevel;

    /**
     * Constructor method, initialises both private variables
     */
    public LevelService() {
        levelEventHandler = new EventHandler();
        currLevel = 1;
        currGold = 50;
        levelEventHandler.addListener("startLevel", this::levelControl);
        //levelEventHandler.addListener("createCustomer", ForestGameArea::spawnCustomer);
        //ServiceLocator.getLevelService().getEvents().addListener("spawnCustomer", this::spawnCustomer);
    }

    public void togglePlayerFinishedLevel() {
        playerFinishedLevel = !playerFinishedLevel;
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

    public int getCurrGold() {
        return currGold;
    }

    public void setCurrGold(int gold) {
        currGold = gold;
        logger.info("Gold is {}", getCurrGold());
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
            case 0:
                spawnCap = 1;
                break;
            case 1:
                spawnCap = 3;
                break;
            case 2:
                spawnCap = 4;
                break;
            case 3:
                spawnCap = 5;
                break;
            case 4:
                spawnCap = 7;
                break;
            case 5:
                spawnCap = 9;
                break;
            case 6:
                spawnCap = 11;
                break;
            case 7:
                spawnCap = 8;
                break;
            case 8:
                spawnCap = 12;
                break;
            case 9:
                spawnCap = 15;
                break;
            case 10:
                spawnCap = 17;
                break;
        }
        levelEventHandler.trigger("startSpawning", spawnCap);
        incrementLevel();
    }
}
