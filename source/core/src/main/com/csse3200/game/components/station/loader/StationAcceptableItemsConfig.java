package com.csse3200.game.components.station.loader;

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
    public ArrayList<String> getOvenConfig() {
        return new ArrayList<>(Arrays.asList(oven));
    }

    /**
     * Function to get the stove config
     * @return the items accepted into the stove
     */
    public ArrayList<String> getStoveConfig() {
        return new ArrayList<>(Arrays.asList(stove));
    }

    /**
     * Function to get the cutting board config
     * @return the items accepted into the cutting board
     */
    public ArrayList<String> getCuttingBoardConfig() {
        return new ArrayList<>(Arrays.asList(cuttingBoard));
    }

    /**
     * Function to get the blender config
     * @return the items accepted into the blender
     */
    public ArrayList<String> getBlenderConfig() {
        return new ArrayList<>(Arrays.asList(blender));
    }

    /**
     * Function to get the produce basket config
     * @return the items accepted into the provde basket
     */
    public ArrayList<String> getProduceBasketConfig() {
        return new ArrayList<>(Arrays.asList(produceBasket));
    }

    /**
     * Function to get the fridge config
     * @return the items accepted into the fridge
     */
    public ArrayList<String> getFridgeConfig() {
        return new ArrayList<>(Arrays.asList(fridge));
    }
}
