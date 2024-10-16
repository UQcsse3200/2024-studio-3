package com.csse3200.game.files;

import com.csse3200.game.components.moral.*;
import java.util.List;

 public class GameState {
    // Mod = Modification Time
    private int day;
    private int money;
    private String ModTime;
    private List<Decision> decisions;

    public GameState() {}

    /**
     * Sets the day of the game state.
     *
     * @param day integer representing how many days into the game.
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Gets the current day of the game state.
     *
     * @return integer representing how many days into the game.
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the money of the player in the current game state.
     *
     * @param money integer representing the player's money.
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Gets the money of the player in the current game state.
     *
     * @return integer representing the player's money.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Gets the modification time of the game state.
     *
     * @return integer representing the last modification time.
     */
    public String getModTime() {
        return ModTime;
    }

    /**
     * Sets the modification time of the game state.
     *
     * @param modTime integer representing the last modification day.
     */
    public void setModTime(String modTime) {
        this.ModTime = modTime;
    }

    public List<Decision> getDecisions() {
        return decisions;
    }

    public void setDecisions(List<Decision> decisions) {
        this.decisions = decisions;
    }
 }