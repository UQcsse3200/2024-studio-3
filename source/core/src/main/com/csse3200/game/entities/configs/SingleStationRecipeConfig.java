package com.csse3200.game.entities.configs;

import java.util.ArrayList;
import java.util.List;

public class SingleStationRecipeConfig {
    public List<String> ingredient = new ArrayList<>();
    public List<String> cuttingBoard = new ArrayList<>();
    public List<String> produceBasket = new ArrayList<>();
    public int makingTime = 0;
    public List<String> fridge = new ArrayList<>();
    public int price = 0;

    public List<String> getIngredient() {
        return ingredient;
    }

    public List<String> getCuttingBoard() {
        return cuttingBoard;
    }

    public int getMakingTime() {
        return makingTime;
    }
}
