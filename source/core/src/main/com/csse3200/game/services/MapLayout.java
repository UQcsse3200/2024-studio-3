package com.csse3200.game.services;


import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.areas.map.BenchGenerator;
import com.csse3200.game.areas.map.BenchLayout;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class MapLayout extends GameArea{
    private EventHandler mapEventHandler;
    private static final String mapLevel1 = "images/map/map_test.txt";
    private ArrayList<Bench> testArray = new ArrayList<>();
    private Bench bench;
    private static final Logger logger = LoggerFactory.getLogger(MapLayout.class);



    public MapLayout() {
        mapEventHandler = new EventHandler();
        mapEventHandler.addListener("load", this::load);
    }

    @Override
    public void create() {
        return;
    }

    public EventHandler getEvents() {
        return mapEventHandler;
    }

    public void load(String level) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(mapLevel1));
            String line;
            int row = 0;

            logger.info("Reading the grid...");

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Log the entire line
                logger.info("Line " + row + ": " + line);

                // Split the line into individual characters
                String[] parts = line.split("");

                for (int col = 0; col < parts.length; col++) {
                    String square = parts[col];

                    // Log the current square being processed
                    logger.info("Checking square at row " + row + ", column " + col + ": " + square);

                    // Spawn single bench when 'X'
                    if (square.equals("X")) {
                        testArray.addAll(BenchGenerator.createBenchRow(col+4, col+4, row-4));
                        logger.info("Spawning entity at row " + row + ", column " + col);
                    }
                    // Spawn benchrowflat when 'x'
                    else if (square.equals("b")) {
                        testArray.addAll(BenchGenerator.createBenchRowFlat(col+4, col+4, row-4));
                        logger.info("Spawning entity at row " + row + ", column " + col);
                    }
                }
                row++;
            }
        } catch (IOException e) {
            logger.warn("An error occurred while reading the file: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                logger.warn("Failed to close the reader: " + ex.getMessage());
            }
        }

        /*
        for (Bench bench : BenchLayout.levelOne()) {
            spawnEntity(bench);
            bench.setPosition(bench.x, bench.y);
        }
        */
        for (Bench bench : testArray) {
            spawnEntity(bench);
            bench.setPosition(bench.x, bench.y);
        }
    }
}