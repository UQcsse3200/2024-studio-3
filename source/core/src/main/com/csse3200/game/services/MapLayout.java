package com.csse3200.game.services;


import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.map.BenchGenerator;
import com.csse3200.game.areas.map.Map;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.StationFactory;
import com.csse3200.game.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

/**
 * Class to load the map layout from a text file
 */
public class MapLayout {
    private final EventHandler mapEventHandler;
    private static final String mapBase = "images/map/map_base.txt";
    private static final String mapLevel1 = "images/map/map_one.txt";
    private static final String mapLevel2 = "images/map/map_two.txt";
    private static final String mapLevel3 = "images/map/map_three.txt";
    private static final String mapLevel4 = "images/map/map_four.txt";
    private static final String mapLevel5 = "images/map/map_five.txt";
    private ArrayList<Bench> benches = new ArrayList<>();
    private ArrayList<Entity> stations = new ArrayList<>();
    private String mapName;
    private int mapWidth;
    private int mapHeight;

    private final String[] validStations = {"b", "s", "u", "t", "c", "a", "E", "O", "B", "C", "G", "N", "S", "F"};

    private static final Logger logger = LoggerFactory.getLogger(MapLayout.class);


    /**
     * Constructor for the MapLayout class
     */
    public MapLayout() {
        mapEventHandler = new EventHandler();
        mapEventHandler.addListener("load", this::load);
    }

    public EventHandler getEvents() {
        return mapEventHandler;
    }

    /** Load the map layout from a text file
     * Yab -> spawn vertical bench starting at column `a` that is `b` cells long
     * Xab -> spawn horizontal bench starting at column `a` that is `b` cells long
     * []a  -> for any station, spawns a station based on [] at column `a`
     * the row of the object depends on what line it's on in the file.
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
                String logMessage = "Map Name: " + mapName;
                logger.info(logMessage);
            }

            if ((line = reader.readLine()) != null) {
                mapWidth = Integer.parseInt(line); // 2nd line is the width
                String logMessage = "Map Width: " + mapWidth;
                logger.info(logMessage);
            }

            if ((line = reader.readLine()) != null) {
                mapHeight = Integer.parseInt(line); // 3rd line is the height
                String logMessage = "Map Height: " + mapHeight;
                logger.info(logMessage);
            }

            if ((line = reader.readLine()) != null) {
                String logMessage = "Map Separator: " + line;
                logger.info(logMessage);
            }
            int row = 4;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                long lineTime = ServiceLocator.getTimeSource().getTime();

                // Split the line into individual characters
                String[] parts = line.split("");

                for (int col = 0; col < parts.length; col+=4) {
                    //long colTime = ServiceLocator.getTimeSource().getTime();

                    benches.addAll(parseLine(parts, row, col));

                    //logger.info("Col " + col + " (" + (ServiceLocator.getTimeSource().getTime() - colTime) + "ms)" + ": " + line);
                }
                // Log the entire line
                String logMessage =
                        "Line " + row + " (" + (ServiceLocator.getTimeSource().getTime() - lineTime) + "ms)" + ": " + line;
                logger.info(logMessage);

                row++;
            }
        } catch (IOException e) {
            String logMessage = "An error occurred while reading the file: " + e.getMessage();
            logger.warn(logMessage);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                String logMessage = "Failed to close the reader: " + ex.getMessage();
                logger.warn(logMessage);
            }
        }
        return new Map(benches, stations);
    }

    /**
     * parses a line from the map file
     * @param parts - the string to parse
     * @param row - the row of the line
     * @param col - the column index
     * @return the list of benches
     */
    public ArrayList<Bench> parseLine(String[] parts, int row, int col)
    {
        if (parts == null) return new ArrayList<>();
        String type = parts[col];

        // Log the current square being processed a
        // logger.info("Checking square at row " + row + ", column " + col + ": " + square);

        // Spawn single bench row when 'X'
        if (type.equals("X")) {
            int strToNum = Integer.parseInt(parts[col + 1]);
            int strToNum2 = Integer.parseInt(parts[col + 2] );
            return BenchGenerator.createBenchRow(strToNum + 4, strToNum + 3 + strToNum2, row - 4);
        }
        // Spawn bench column when 'Y'
        else if (type.equals("Y")) {
            int strToNum = Integer.parseInt(parts[col + 1]);
            int strToNum2 = Integer.parseInt(parts[col + 2]);
            return BenchGenerator.createBenchColumn(strToNum + 4, row - 4, row + strToNum2 - 4);
        }
        else if (type.equals("Q")) {
            int strToNum = Integer.parseInt(parts[col + 1]);
            return BenchGenerator.singleShadowBench(strToNum + 4, row - 4);
        }
        else if (type.equals("P")) {
            int strToNum = Integer.parseInt(parts[col + 1]);
            return BenchGenerator.singleBlocker(strToNum + 4, row - 4);
        }
        // Spawn a station
        else if (validateStation(type)) {
            int strToNum = Integer.parseInt(parts[col + 1]);
            stations.add(readStation(type, strToNum, row));
        }
        return new ArrayList<>();
    }

    /**
     * Read a station from the map file
     * @param type - the type of station
     * @param col - the column of the station
     * @param row - the row of the station
     * @return - an Entity object
     */
    public Entity readStation(String type, int col, int row) {
        Entity station = switch (type) {
            case "b" -> StationFactory.createBananaBasket();
            case "s" -> StationFactory.createStrawberryBasket();
            case "u" -> StationFactory.createLettuceBasket();
            case "t" -> StationFactory.createTomatoBasket();
            case "c" -> StationFactory.createCucumberBasket();
            case "a" -> StationFactory.createAcaiBasket();
            case "E" -> StationFactory.createStove();
            case "O" -> StationFactory.createOven();
            case "B" -> StationFactory.createBeefFridge();
            case "C" -> StationFactory.createChocolateFridge();
            case "G" -> StationFactory.createCuttingBoard();
            case "N" -> StationFactory.createBin();
            case "S" -> StationFactory.createSubmissionWindow();
            case "F" -> StationFactory.createFireExtinguisher();
            default -> new Entity();
        };
        station.setPosition(col + 4, row - 4);

        ServiceLocator.getInteractableService().registerEntity(station);

        return station;
    }

    /**
     * Validate a station
     * @param str - the station to validate
     * @return - a boolean indicating if the station is valid
     */
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