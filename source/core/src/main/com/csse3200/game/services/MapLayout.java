package com.csse3200.game.services;


import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.areas.map.BenchLayout;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

public class MapLayout extends GameArea{
    private EventHandler mapEventHandler;
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

        Reader rd = null;
        if (level == "level1") {
            try {
                rd = new BufferedReader(new FileReader("images/map/map_test.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        // Load map from file
        BufferedReader mapReader = new BufferedReader(rd);
        String line;
        try {
            line = mapReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final String name = line;
        try {
            line = mapReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final int width = Integer.parseInt(line);
        try {
            line = mapReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final int height = Integer.parseInt(line);
        try {
            line = mapReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.info(line);
        try {
            while ( (line=mapReader.readLine())!= null) {
                String [] parts = line.split("");
                logger.info(parts[0]);
                if (parts[0].equals("b")) {
                    logger.info("GELL");
                    logger.info(line);
                    // create array containing map tiles
                    for (Bench bench : BenchLayout.levelOne()) {
                        spawnEntity(bench);
                        bench.setPosition(bench.x, bench.y);
                    }
                    }

                }

                mapReader.close();
                return;
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }
}

