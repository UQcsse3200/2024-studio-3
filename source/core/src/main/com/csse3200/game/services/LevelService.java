package com.csse3200.game.services;

import com.csse3200.game.GdxGame;
import com.csse3200.game.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**Allows for global access and control of the game levels*/
public class LevelService {
    private static final Logger logger = LoggerFactory.getLogger(LevelService.class);
    private EventHandler levelEventHandler;
    private GdxGame.LevelType currLevel;
    private int baseGold = 50;
    private int currGold;
    private boolean playerFinishedLevel;

    /**
     * Constructor method, initialises both private variables
     */
    public LevelService() {
        levelEventHandler = new EventHandler();
        currLevel = GdxGame.LevelType.LEVEL_1;

        currGold = baseGold; 
        playerFinishedLevel = false;
        levelEventHandler.addListener("startLevel", this::levelControl);
       // levelEventHandler.addListener("mapLevel", this::loadMap);
        //levelEventHandler.addListener("createCustomer", ForestGameArea::spawnCustomer);
        //ServiceLocator.getLevelService().getEvents().addListener("spawnCustomer", this::spawnCustomer);
    }
    public void reset() {
        currLevel = GdxGame.LevelType.LEVEL_1; 
        setCurrGold(baseGold); 
        playerFinishedLevel = false; 
        ServiceLocator.getLevelService().getEvents().trigger("startLevel", currLevel);
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
    public GdxGame.LevelType getCurrLevel() {
        if (playerFinishedLevel) {
            incrementLevel();
            togglePlayerFinishedLevel();
        }
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
        switch (currLevel) {
            case LEVEL_1 -> currLevel = GdxGame.LevelType.LEVEL_2;
            case LEVEL_2 -> currLevel = GdxGame.LevelType.LEVEL_3;
            case LEVEL_3 -> currLevel = GdxGame.LevelType.LEVEL_4;
            case LEVEL_4 -> currLevel = GdxGame.LevelType.LEVEL_5;
            case LEVEL_5 -> currLevel = GdxGame.LevelType.DONE;
            default -> currLevel = GdxGame.LevelType.LEVEL_1;
        }
    }

    /**
     * Manually set the level number
     *
     * @param newLevel the new level number
     */
    public void setCurrLevel(GdxGame.LevelType newLevel) {
            currLevel = newLevel;
    }

    /**
     * Depending on what level the game is switching to,
     * a method relating to each level will be called, in order to
     * spawn customers with different parameters
     *
     * @param level the level number
     */
    public void levelControl(GdxGame.LevelType level) {
        int spawnCap = switch(level) {
            case LEVEL_0 -> 1;
            case LEVEL_1 -> 1;
            case LEVEL_2 -> 3;
            case LEVEL_3 -> 4;
            case LEVEL_4 -> 5;
            case LEVEL_5 -> 7;
            case DONE -> 9;
            default -> 1;
        };

        levelEventHandler.trigger("startSpawning", spawnCap);
    }
}
