package com.csse3200.game.components.station.loader;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * StatonAcceptableItemsConfig
 * 
 * Config class to read the station item json file from. This is used to extract
 * all of the different ingredients that can go in each station
 */
public class StationAcceptableItemsConfig {
    public String[] oven;
    public String[] stove;
    public String[] cuttingBoard;
    public String[] blender;
    public String[] produceBasket;
    public String[] fridge;
    
    /**
     * Function to get the oven config
     * @return the items accepted into the over
     */
    public List<String> getOvenConfig() {
        return new ArrayList<>(Arrays.asList(oven));
    }

    /**
     * Function to get the stove config
     * @return the items accepted into the stove
     */
    public List<String> getStoveConfig() {
        return new ArrayList<>(Arrays.asList(stove));
    }

    /**
     * Function to get the cutting board config
     * @return the items accepted into the cutting board
     */
    public List<String> getCuttingBoardConfig() {
        return new ArrayList<>(Arrays.asList(cuttingBoard));
    }

    /**
     * Function to get the blender config
     * @return the items accepted into the blender
     */
    public List<String> getBlenderConfig() {
        return new ArrayList<>(Arrays.asList(blender));
    }

    /**
     * Function to get the produce basket config
     * @return the items accepted into the provde basket
     */
    public List<String> getProduceBasketConfig() {
        return new ArrayList<>(Arrays.asList(produceBasket));
    }

    /**
     * Function to get the fridge config
     * @return the items accepted into the fridge
     */
    public List<String> getFridgeConfig() {
        return new ArrayList<>(Arrays.asList(fridge));
    }
}
