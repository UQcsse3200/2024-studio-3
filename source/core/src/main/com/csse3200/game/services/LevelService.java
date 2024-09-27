package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**Allows for global access and control of the game levels*/
public class LevelService {
    private static final Logger logger = LoggerFactory.getLogger(LevelService.class);
    private final EventHandler levelEventHandler;
    private int currLevel;
    private int currGold;
    private boolean playerFinishedLevel;
    private final int[] madeGoodDecision = {0,0,0,0};

    /**
     * Constructor method, initialises both private variables
     */
    public LevelService() {
        levelEventHandler = new EventHandler();
        currLevel = 1;
        currGold = 50;
        playerFinishedLevel = false;
        levelEventHandler.addListener("startLevel", this::levelControl);
    }

    /**
     * Checks if a player has only made "good" moral decisions, by iterating
     * through the array tracking the flags for if the player made a good decision or not
     *
     * @return Whether the player only made "good" choices or not
     */
    public boolean checkIfPlayerMadeAllGoodDecisions() {
        for (int i : madeGoodDecision) {
            if (i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the value at the given index to 1, symbolising that a player
     * made a "good" moral choice
     *
     * @param day the day/level a choice was made on converted to an array index
     */
    public void playerMadeGoodMoralDecision(int day) {
        int levelNo = day - 1;
        madeGoodDecision[levelNo] = 1;
    }

    /**
     * Changes a boolean value to symbolise whether the player actually finished a level
     * or not and therefore if they can progress to the next level
     */
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
        if (playerFinishedLevel) {
            incrementLevel();
            togglePlayerFinishedLevel();
        }
        return currLevel;
    }

    /**
     * Return the current amount of gold the player has
     *
     * @return how much gold the player has
     */
    public int getCurrGold() {
        return currGold;
    }

    /**
     * Set the gold tracking variable to the given value
     *
     * @param gold the value to be set
     */
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
        }
        levelEventHandler.trigger("startSpawning", spawnCap);
    }
}
