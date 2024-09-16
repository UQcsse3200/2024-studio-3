package com.csse3200.game.components.station.loader;

import java.util.ArrayList;
import java.util.List;

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
    
    public ArrayList<String> getOvenConfig() {
        ArrayList<String> ovenList = new ArrayList<>();
        
        for (String item : oven) {
            ovenList.add(item);
        }

        return ovenList;
    }

    public ArrayList<String> getStoveConfig() {
        ArrayList<String> stoveList = new ArrayList<>();

        for (String item : stove) {
            stoveList.add(item);
        }

        return stoveList;
    }

    public ArrayList<String> getCuttingBoardConfig() {
        ArrayList<String> cuttingList = new ArrayList<>();

        for (String item : cuttingBoard) {
            cuttingList.add(item);
        }

        return cuttingList;
    }

    public ArrayList<String> getBlenderConfig() {
        ArrayList<String> cuttingList = new ArrayList<>();

        for (String item : blender) {
            cuttingList.add(item);
        }

        return cuttingList;
    }

    public ArrayList<String> getProduceBasketConfig() {
        ArrayList<String> basketList = new ArrayList<>();

        for (String item : produceBasket) {
            basketList.add(item);
        }

        return basketList;
    }

    public ArrayList<String> getFridgeConfig() {
        ArrayList<String> fridgeList = new ArrayList<>();

        for (String item : fridge) {
            fridgeList.add(item);
        }

        return fridgeList;
    }
}
