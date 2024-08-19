package com.csse3200.game.entities.configs;

public class BaseCustomerConfig {
    public String name = "Consumer" + Math.random();
    public int preference = 0; // TODO: change to list of recipe or ingredients
    public int time = 30; // in seconds
    public int patience = 100; // in seconds
    public int reputation = 0;
    public int spawnTimer = 100; // in seconds
}
