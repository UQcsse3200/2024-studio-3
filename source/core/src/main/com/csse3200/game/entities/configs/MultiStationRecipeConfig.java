package com.csse3200.game.entities.configs;

import java.util.ArrayList;
import java.util.List;

public class MultiStationRecipeConfig extends SingleStationRecipeConfig {
    public static final List<String> fryingPan = new ArrayList<>();
    public static final List<String> oven = new ArrayList<>();
    public static final List<String> blender = new ArrayList<>();
    public static final int burnedTime = 0;

    public List<String> getFryingPan() {
        return fryingPan;
    }

    public List<String> getOven() {
        return oven;
    }

    public Integer getBurnedTime() {
        return burnedTime;
    }
}
