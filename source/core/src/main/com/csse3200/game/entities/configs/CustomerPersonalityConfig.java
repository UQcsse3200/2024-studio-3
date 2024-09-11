package com.csse3200.game.entities.configs;

public class CustomerPersonalityConfig extends BaseCustomerConfig{
    private static final int MAX = 999999;
    private static final int MIN = 100000;
    private static final int RANGE = MAX - MIN + 1;

    public String name;
    public int reputation = -1;
}
