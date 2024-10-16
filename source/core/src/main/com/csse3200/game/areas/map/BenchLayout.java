package com.csse3200.game.areas.map;

import java.util.ArrayList;

import com.csse3200.game.entities.benches.Bench;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Class for managing bench layouts
 */
public class BenchLayout {
    static final Logger LOGGER = Logger.getLogger(BenchLayout.class.getPackage().getName());
    /**
     * Create a level one bench layout
     * @return ArrayList of benches
     */
    public static ArrayList<Bench> levelOne() {
        ArrayList<Bench> benches = new ArrayList<>();
        // Bottom bench row
        //benches.addAll(BenchGenerator.createBenchRowFlat(4, 15, 0));
        // Top shadow bench row
        benches.addAll(BenchGenerator.createBenchRow(4, 6, 6));
        // Left vertical bench column
        benches.addAll(BenchGenerator.createBenchColumn(9, 1, 4));
        benches.addAll(BenchGenerator.createBenchColumn(4, 3, 6));

        // Random single bench
        //benches.add(new Bench(7, 6));
        for (Bench bench : benches) {
            LOGGER.log(Level.INFO, "added bench: " + bench.type + ", " + bench.x + ", " + bench.y);
        }
        return benches;
    }

}