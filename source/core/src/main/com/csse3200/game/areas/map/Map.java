package com.csse3200.game.areas.map;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.benches.Bench;

import java.util.ArrayList;

public class Map {
    private final ArrayList<Bench> benches ;
    private final ArrayList<Entity> stations;

    public Map (ArrayList<Bench> benches, ArrayList<Entity> stations) {
        this.benches = benches;
        this.stations = stations;
    }

    public ArrayList<Bench> getBenches() {
        return benches;
    }

    public ArrayList<Entity> getStations() {
        return stations;
    }
}
