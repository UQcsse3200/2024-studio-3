package com.csse3200.game.components.station.loader;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * StationAcceptableItems is a class used to both load and retrieve items from
 * recipie.json to determine if an item is allowed within a station or not.
 * This class contains static variables so the items will only need to be loaded
 * once and the class tracks this. 
 * When asking for the accepted items this class will return a hashset of the 
 * items as when creating the collection of items this is the easier way to 
 * do it.
 */
public class StationAcceptableItemsGetter {
    // String for the name of the file where recipies are stored
    private static final String filePath = 
            "configs/station.json";

    // For some reason the class loader isn't working so this is it for now
    // TODO: Get the class loader working with this
    private static Json json = new Json();
    private static FileHandle handle = new FileHandle(filePath);
    private static StationAcceptableItemsConfig configs = json.fromJson(StationAcceptableItemsConfig.class, handle);

    /**
     * Function to read the acceptable items file and calculate acceptable items
     * using that
     */
    public static ArrayList<String> getAcceptableItems(String type) {
        return switch(type) {
            case "oven" -> configs.getOvenConfig();
            case "stove" -> configs.getStoveConfig();
            case "cutting board" -> configs.getCuttingBoardConfig();
            case "blender" -> configs.getBlenderConfig();
            case "produce basket" -> configs.getProduceBasketConfig();
            case "fridge" -> configs.getFridgeConfig();
            default -> null;
        };
    }
}
