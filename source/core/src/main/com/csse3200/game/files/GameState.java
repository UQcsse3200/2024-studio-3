package com.csse3200.game.files;

 public class GameState {
    // Mod = Modification Time
    private int day;
    private int money;
    private int ModDay;
    private int ModHour;
    private int ModMin;

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
     * Gets the modification day of the game state.
     *
     * @return integer representing the last modification day.
     */
    public int getModDay() {
        return ModDay;
    }

    /**
     * Gets the modification hour of the game state.
     *
     * @return integer representing the last modification hour.
     */
    public int getModHour() {
        return ModHour;
    }

    /**
     * Gets the modification minute of the game state.
     *
     * @return integer representing the last modification minute.
     */
    public int getModMin() {
        return ModMin;
    }

    /**
     * Sets the modification day of the game state.
     *
     * @param modDay integer representing the last modification day.
     */
    public void setModDay(int modDay) {
        ModDay = modDay;
    }

    /**
     * Sets the modification hour of the game state.
     *
     * @param modHour integer representing the last modification hour.
     */
    public void setModHour(int modHour) {
        ModHour = modHour;
    }

    /**
     * Sets the modification minute of the game state.
     *
     * @param modMin integer representing the last modification minute.
     */
    public void setModMin(int modMin) {
        ModMin = modMin;
    }
}