package com.csse3200.game.entities.configs;

import java.util.ArrayList;
import java.util.List;

public class MultiStationRecipeConfig extends SingleStationRecipeConfig {
    public List<String> fryingPan = new ArrayList<>();
    public List<String> oven = new ArrayList<>();
    public List<String> blender = new ArrayList<>();
    public int burnedTime = 0;

    public List<String> ingredient;
    public List<String> cuttingBoard;
    public int makingTime;

    public List<String> getIngredient() {
        return ingredient;
    }

    public List<String> getCuttingBoard() {
        return cuttingBoard;
    }

    public List<String> getFryingPan() {
        return fryingPan;
    }

    public List<String> getOven() {
        return oven;
    }

    public int getMakingTime() {
        return makingTime;
    }

    public Integer getBurnedTime() {
        return burnedTime;
    }
}
