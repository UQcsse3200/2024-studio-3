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

            long time1 = ServiceLocator.getTimeSource().getTime();
            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                long lineTime = ServiceLocator.getTimeSource().getTime();

                // Split the line into individual characters
                String[] parts = line.split("");

                for (int col = 0; col < parts.length; col+=4) {
                    //long colTime = ServiceLocator.getTimeSource().getTime();

                    readBench(parts, row, col);

                    //logger.info("Col " + col + " (" + (ServiceLocator.getTimeSource().getTime() - colTime) + "ms)" + ": " + line);
                }
                // Log the entire line
                logger.info("Line " + row + " (" + (ServiceLocator.getTimeSource().getTime() - lineTime) + "ms)" + ": " + line);

                row++;
            }
            long time2 = ServiceLocator.getTimeSource().getTime();
            logger.info("Map file read: {}ms", time2 - time1);
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
        long initTime = ServiceLocator.getTimeSource().getTime();
//        for (Bench b : benches) {
//            b.init();
//        }
        logger.info("initialising benches (" + (ServiceLocator.getTimeSource().getTime() - initTime) + "ms)");

        return new Map(benches, stations);

    }

    public void readBench(String[] parts, int row, int col) {
        String type = parts[col];

        // Log the current square being processed a
        // logger.info("Checking square at row " + row + ", column " + col + ": " + square);

        // Spawn single bench row when 'X'
        if (type.equals("X")) {
            int strToNum = Integer.valueOf(parts[col + 1]);
            int strToNum2 = Integer.valueOf(parts[col + 2] );
            benches.addAll(BenchGenerator.createBenchRow(strToNum + 4, strToNum + 3 + strToNum2, row - 4));
        }
        // Spawn bench column when 'Y'
        else if (type.equals("Y")) {
            int strToNum = Integer.valueOf(parts[col + 1]);
            int strToNum2 = Integer.valueOf(parts[col + 2]);
            benches.addAll(BenchGenerator.createBenchColumn(strToNum + 4, row - 4, row + strToNum2 - 4));
        }
        else if (type.equals("Q")) {
            int strToNum = Integer.valueOf(parts[col + 1]);
            benches.addAll(BenchGenerator.singleShadowBench(strToNum + 4, row - 4));
        }
        else if (type.equals("P")) {
            int strToNum = Integer.valueOf(parts[col + 1]);
            benches.addAll(BenchGenerator.singleBlocker(strToNum + 4, row - 4));
        }
        // Spawn a station
        else if (validateStation(type)) {
            int strToNum = Integer.valueOf(parts[col + 1]);
            stations.add(readStation(type, strToNum, row));
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