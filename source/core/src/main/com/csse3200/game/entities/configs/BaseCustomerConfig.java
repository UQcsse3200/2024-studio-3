package com.csse3200.game.entities.configs;

public class BaseCustomerConfig {
    private static final int MAX = 999999;
    private static final int MIN = 100000;
    private static final int RANGE = MAX - MIN + 1;

    public static String type = "Consumer" + Math.random();
    public static int countDown = 30; // in seconds
    public static final int patience = 100; // in seconds

    public static String texture;
    public static String preference = "placeholder"; //change to list of recipe or ingredients

    public static final int customerId = (int)(Math.random() * RANGE) + MIN;
}
