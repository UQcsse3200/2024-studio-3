package com.csse3200.game.entities.configs;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the properties stored in cooking config files to be loaded by the Dish Factory.
 */
public class CookingConfig {
    public List<String> acai_bowl =new ArrayList<>() {{
            add("acai");
            add("banana");
        }};

    public List<String> salad =new ArrayList<>() {{
        add("tomato");
        add("lettuce");
        add("cucumber");
    }};

    public List<String> fruit_salad =new ArrayList<>() {{
        add("banana");
        add("strawberry");
    }};

    public List<String> steak_meal =new ArrayList<>() {{
        add("beef");
        add("tomato");
        add("cucumber");
    }};

    public List<String> banana_split =new ArrayList<>() {{
        add("banana");
        add("strawberry");
        add("chocolate");
    }};

}



