package com.csse3200.game.entities.configs;

public class BaseCustomerConfig {
    public String name = "Consumer" + Math.random();
    public int countDown = 30; // in seconds
    public int patience = 100; // in seconds
    public int spawnTimer = 100; // in seconds
    public int pref = -1; //change to list of recipe or ingredients
    public int reputation = -1;
    public String texture;
}
