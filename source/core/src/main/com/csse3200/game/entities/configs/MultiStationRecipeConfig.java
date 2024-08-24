package com.csse3200.game.entities.configs;

import java.util.ArrayList;
import java.util.List;

public class MultiStationRecipeConfig extends SingleStationRecipeConfig {
    public List<String> fryingPan = new ArrayList<>();
    public List<String> oven = new ArrayList<>();
    public int burnedTime = 0;

}
