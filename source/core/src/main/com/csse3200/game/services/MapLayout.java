package com.csse3200.game.services;


import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.areas.map.BenchGenerator;
import com.csse3200.game.areas.map.BenchLayout;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.StationFactory;
import com.csse3200.game.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class MapLayout extends GameArea{
    private EventHandler mapEventHandler;
    private static final String mapLevel1 = "images/map/map_test.txt";
    //private static final String mapLevel1 = "images/map/map_two.txt";
    private int strToNum;
    private int strToNum2;
    private ArrayList<Bench> benches = new ArrayList<Bench>();
    private ArrayList<Entity> stations = new ArrayList<Entity>();

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
     * Yxy ->
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

                    // Log the current square being processed a
                    logger.info("Checking square at row " + row + ", column " + col + ": " + square);

                    // Spawn single bench row when 'X'
                    if (square.equals("X")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        strToNum2 = Integer.valueOf(parts[col+2]);
                        benches.addAll(BenchGenerator.createBenchRow(strToNum + 4,
                                strToNum + strToNum2 + 4, row-4));
                        col += 3;
                        logger.info("Spawning entity at row " + row + ", column " + col);
                    }
                    // Spawn bench column when 'Y'
                    else if (square.equals("Y")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        strToNum2 = Integer.valueOf(parts[col+2]);
                        benches.addAll(BenchGenerator.createBenchColumn(strToNum + 4,
                                row-4, row + strToNum2 - 4));
                        col += 3;
                        logger.info("Spawning entity at row " + row + ", column " + col);
                    }
                    // Spawn a banana basket when 'b'
                    else if (square.equals("b")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createBananaBasket();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }

                    // Spawn a strawberry basket when 's'
                    else if (square.equals("s")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createStrawberryBasket();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }
                    // spawn a lettuce basket
                    else if (square.equals("u")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createLettuceBasket();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }


                    // spawn a tomato basket
                    else if (square.equals("t")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createTomatoBasket();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }
                    // spawn a cucumber basket
                    else if (square.equals("c")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createCucumberBasket();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }
                    // spawn a acai basket
                    else if (square.equals("a")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createAcaiBasket();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }

                    //stove E
                    else if (square.equals("E")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createStove();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }
                    //Oven O
                    else if (square.equals("O")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createOven();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }
                    //beef fridge B
                    else if (square.equals("B")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createBeefFridge();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }
                    //chocolate fridge C
                    else if (square.equals("C")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createChocolateFridge();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }
                    // cutting board G
                    else if (square.equals("G")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createCuttingBoard();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }
                    // bin N
                    else if (square.equals("N")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createBin();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
                    }
                    // servery/submission S
                    else if (square.equals("S")) {
                        strToNum = Integer.valueOf(parts[col+1]);
                        Entity station = StationFactory.createSubmissionWindow();
                        station.setPosition(strToNum+4, row-4);
                        stations.add(station);
                        col+=3;
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
        for (Entity station : stations) {
            spawnEntity(station);
        }
    }
}