public static class GameState {
    private int day;
    private int money;

    /**
     * Sets the day of the game state
     * @param day - integer determining how many days into game
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Gets the day of the game state
     * @return integer determining how many days into game
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the money of the player in the current game state
     * @param money - integer which is the money of the player
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * gets the money of the player in the current game state
     * @return integer which is the money of the player
     */
    public int getMoney() {
        return money;
    }
}