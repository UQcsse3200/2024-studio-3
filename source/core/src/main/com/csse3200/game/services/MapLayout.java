package com.csse3200.game.services;


import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.areas.map.BenchGenerator;
import com.csse3200.game.areas.map.BenchLayout;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.entities.factories.StationFactory;
import com.csse3200.game.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class MapLayout extends GameArea{
    private EventHandler mapEventHandler;
    private static final String mapLevel1 = "images/map/map_test.txt";
    private int strToNum;
    private int strToNum2;
    private ArrayList<Bench> benches = new ArrayList<>();
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

    /**
     * Xab -> spawns a horizontal bench from column `a` that is `b` long
     * Yab -> spawns a vertical bench from column `a` that is `b` long
     * ba  -> spawns a banana basket at the `a` column
     * sa  -> spawns a strawberry basket at the `a` column
     * the row of the object is determined by the row of the text in the file.
     * @param level
     */
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
                    strToNum = Integer.valueOf(parts[col+1]);
                    strToNum2 = Integer.valueOf(parts[col+2]);
                    // Spawn single bench row when 'X'
                    switch (square) {
                        case "X":
                            benches.addAll(BenchGenerator.createBenchRow(strToNum + 4,
                                    strToNum2 + 4, row-4));
                            col += 3;
                            logger.info("Spawning entity at row " + row + ", column " + col);
                            break;
                        case "Y":
                            benches.addAll(BenchGenerator.createBenchColumn(strToNum + 4,
                                    row-4, Integer.parseInt(parts[col+2])));
                            col += 3;
                            logger.info("Spawning entity at row " + row + ", column " + col);
                        //case "b":
                            //spawnBananaBasket(row, strToNum);
                            //col += 2;
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

        for (Bench bench : benches) {
            spawnEntity(bench);
            bench.setPosition(bench.x, bench.y);
        }
    }
    private void spawnBananaBasket(int x, int y) {
        Entity entity = StationFactory.createBananaBasket();
        spawnEntity(entity);
        entity.setPosition(x, y);

    }
}