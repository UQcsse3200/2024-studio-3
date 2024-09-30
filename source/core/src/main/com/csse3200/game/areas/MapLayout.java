package com.csse3200.game.areas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class MapLayout {
    private final String name;
    private final int width;
    private final int height;


    public MapLayout(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static MapLayout load(Reader reader) throws IOException {

        // Load map from file
        BufferedReader mapReader = new BufferedReader(reader);
        String line;
        line = mapReader.readLine();
        final String name = line;
        line = mapReader.readLine();
        final int width = Integer.parseInt(line);
        line = mapReader.readLine();
        final int height = Integer.parseInt(line);
        line = mapReader.readLine();
        final String seperator = line;
        line = mapReader.readLine();
        return new MapLayout(name, width, height);
    }
}

