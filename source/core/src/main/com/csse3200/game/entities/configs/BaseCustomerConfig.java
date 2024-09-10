package com.csse3200.game.entities.configs;

public class BaseCustomerConfig {
    private static final int MAX = 999999;
    private static final int MIN = 100000;
    private static final int RANGE = MAX - MIN + 1;

    public String type = "Consumer" + Math.random();
    public int countDown = 30; // in seconds
    public int patience = 100; // in seconds

    public String texture;
    public String preference = "placeholder"; //change to list of recipe or ingredients

    public int Customer_id = (int)(Math.random() * RANGE) + MIN;
}
