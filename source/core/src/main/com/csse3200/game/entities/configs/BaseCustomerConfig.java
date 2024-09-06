package com.csse3200.game.entities.configs;

public class BaseCustomerConfig {
    public String type = "Consumer" + Math.random();
    public int countDown = 30; // in seconds
    public int patience = 100; // in seconds
    public int spawnTimer = 100; // in seconds

    public String texture;
}
