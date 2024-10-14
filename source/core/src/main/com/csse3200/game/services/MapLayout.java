package com.csse3200.game.services;


import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.areas.map.BenchGenerator;
import com.csse3200.game.areas.map.BenchLayout;
import com.csse3200.game.areas.map.Map;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.StationFactory;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.services.InteractableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.Objects;

public class MapLayout {
    private EventHandler mapEventHandler;
    private static final String mapBase = "images/map/map_base.txt";
    private static final String mapLevel1 = "images/map/map_one.txt";
    private static final String mapLevel2 = "images/map/map_two.txt";
    private static final String mapLevel3 = "images/map/map_three.txt";
    private static final String mapLevel4 = "images/map/map_four.txt";
    private static final String mapLevel5 = "images/map/map_five.txt";
    private int strToNum;
    private int strToNum2;
    private ArrayList<Bench> benches = new ArrayList<Bench>();
    private ArrayList<Entity> stations = new ArrayList<Entity>();
    private String mapName;
    private int mapWidth;
    private int mapHeight;
    private String mapSeparator;
    private Bench bench;

    private final String[] validStations = {"b", "s", "u", "t", "c", "a", "E", "O", "B", "C", "G", "N", "S", "F"};

    private static final Logger logger = LoggerFactory.getLogger(MapLayout.class);


    public MapLayout() {
        mapEventHandler = new EventHandler();
        mapEventHandler.addListener("load", this::load);
    }

    public EventHandler getEvents() {
        return mapEventHandler;
    }

    /**
     * Yab -> spawn vertical bench starting at column `a` that is `b` cells long
     * Xab -> spawn horizontal bench starting at column `a` that is `b` cells long
     * []a  -> for any station, spawns a station based on [] at column `a`
     * the row of the object depends on what line its on in the file.
     *
     * @param level - integer 1-5 corresponding to level
     */
    public Map load(GdxGame.LevelType level) {

        String mapLevel = mapBase;
        BufferedReader reader = null;
        switch (level) {
            case LEVEL_1 -> mapLevel = mapLevel1;
            case LEVEL_2 -> mapLevel = mapLevel2;
            case LEVEL_3 -> mapLevel = mapLevel3;
            case LEVEL_4 -> mapLevel = mapLevel4;
            case LEVEL_5 -> mapLevel = mapLevel5;
        }

        try {
            reader = new BufferedReader(new FileReader(mapLevel));
            String line;


            logger.info("Reading the grid...");
            if ((line = reader.readLine()) != null) {
                mapName = line; // 1st line is the map name
                logger.info("Map Name: " + mapName);
            }

            if ((line = reader.readLine()) != null) {
                mapWidth = Integer.parseInt(line); // 2nd line is the width
                logger.info("Map Width: " + mapWidth);
            }

            if ((line = reader.readLine()) != null) {
                mapHeight = Integer.parseInt(line); // 3rd line is the height
                logger.info("Map Height: " + mapHeight);
            }

            if ((line = reader.readLine()) != null) {
                mapSeparator = line; // 4th line is a separator (e.g., "===")
                logger.info("Map Separator: " + mapSeparator);
            }
            int row = 4;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Log the entire line
                logger.info("Line " + row + ": " + line);


                // Split the line into individual characters
                String[] parts = line.split("");

                for (int col = 0; col < parts.length; col++) {
                    String square = parts[col];

                    // Log the current square being processed a
//                    logger.info("Checking square at row " + row + ", column " + col + ": " + square);

                    // Spawn single bench row when 'X'
                    if (square.equals("X")) {
                        strToNum = Integer.valueOf(parts[col + 1]);
                        if (parts.length == 4) {
                            strToNum2 = Integer.valueOf(parts[col + 2]);
                            strToNum2 = strToNum2 + Integer.valueOf(parts[col + 3]);
                        } else {
                            strToNum2 = 1;
                        }
                        strToNum2 = Integer.valueOf(parts[col + 2] );
                        benches.addAll(readBench("X", strToNum, strToNum2, row));
                        col += 3;
//                        logger.info("Spawning entity at row " + row + ", column " + col);
                    }
                    // Spawn bench column when 'Y'
                    else if (square.equals("Y")) {
                        strToNum = Integer.valueOf(parts[col + 1]);
                        strToNum2 = Integer.valueOf(parts[col + 2]);
                        benches.addAll(readBench("Y", strToNum, strToNum2, row));
                        col += 3;
                        logger.info("Spawning entity at row " + row + ", column " + col);
                    }
                    // Spawn a station
                    else if (validateStation(square)) {
                        strToNum = Integer.valueOf(parts[col + 1]);
                        stations.add(readStation(square, strToNum, row));
                        col += 3;
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
        return new Map(benches, stations);

    }

    public ArrayList<Bench> readBench(String type, int startCol, int size, int row) {
        switch (type) {
            case "X":
                if (size == 1) {
                    return BenchGenerator.singleBench(startCol + 4, row - 4);
                }

                return BenchGenerator.createBenchRow(startCol + 4, startCol + size +4, row - 4);
            case "Y":
                return BenchGenerator.createBenchColumn(startCol + 4, row - 4, row + size - 4);
            default:
                return new ArrayList<Bench>();
        }
    }

    public Entity readStation(String type, int col, int row) {
        Entity station;
        switch (type) {
            case "b":
                station = StationFactory.createBananaBasket();
                break;
            case "s":
                station = StationFactory.createStrawberryBasket();
                break;
            case "u":
                station = StationFactory.createLettuceBasket();
                break;
            case "t":
                station = StationFactory.createTomatoBasket();
                break;
            case "c":
                station = StationFactory.createCucumberBasket();
                break;
            case "a":
                station = StationFactory.createAcaiBasket();
                break;
            case "E":
                station = StationFactory.createStove();
                break;
            case "O":
                station = StationFactory.createOven();
                break;
            case "B":
                station = StationFactory.createBeefFridge();
                break;
            case "C":
                station = StationFactory.createChocolateFridge();
                break;
            case "G":
                station = StationFactory.createCuttingBoard();
                break;
            case "N":
                station = StationFactory.createBin();
                break;
            case "S":
                station = StationFactory.createSubmissionWindow();
                break;
            case "F":
                station = StationFactory.createFireExtinguisher();
                break;
            default:
                station = new Entity();
                break;
        }
        station.setPosition(col + 4, row - 4);

        ServiceLocator.getInteractableService().registerEntity(station);

        return station;
    }

    public boolean validateStation(String str) {
        for (String station : validStations) {
            if (str.equals(station)) {
                return true;
            }
        }
        return false;
    }

    public String getMapName() {
        return mapName;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}