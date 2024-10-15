package com.csse3200.game.areas.map;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.benches.Bench;

import java.util.ArrayList;

/**
 * Map type returned by the Maplayout class when loading in a map
 * Contains a list of benches and stations
 */
public class Map {
    private final ArrayList<Bench> benches ;
    private final ArrayList<Entity> stations;

    public Map (ArrayList<Bench> benches, ArrayList<Entity> stations) {
        this.benches = benches;
        this.stations = stations;
    }

    /** Returns the list of benches */
    public ArrayList<Bench> getBenches() {
        return benches;
    }

    /** Returns the list of stations */
    public ArrayList<Entity> getStations() {
        return stations;
    }

    /** Returns the number of benches */
    public int getNumBenches() {
        return benches.size();
    }

    /** Returns the number of stations */
    public int getNumStations() {
        return stations.size();
    }


}
