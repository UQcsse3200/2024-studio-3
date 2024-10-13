package com.csse3200.game.areas.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * Utility class for creating benches
 */
public class BenchGenerator {
    static final Logger LOGGER = Logger.getLogger(BenchGenerator.class.getPackage().getName());
    /**
     * Creates a column of benches
     * @param x - x coordinate
     * @param startY - start y coordinate
     * @param endY - end y coordinate
     * @return - returns an ArrayList of the benches created.
     */
    public static ArrayList<Bench> createBenchColumn(int x, int startY, int endY) {
        ArrayList<Bench> arr = new ArrayList<Bench>();
        // add the bottom part of the bench
        arr.add(new Bench("bottom_shadow", x, startY));
        if (endY - startY > 0){ // the bench has 2 or more segments
            // add the middle parts of the bench
            for (int i = startY+1; i < endY; i++) {
                arr.add(new Bench("vertical", x, i));
            }
            // add the top part of the bench
            arr.add(new Bench("top", x, endY));
        }
        //  LOGGER.log(Level.INFO, "added column of " + arr.size() + " benches");
        return arr;
    }
    public static ArrayList<Bench> createBenchRow(int startX, int endX, int y) {
        ArrayList<Bench> arr = new ArrayList<Bench>();
        // add the left part of the bench
        arr.add(new Bench("left_corner_shadow", startX, y));
        if (endX - startX > 0){ // the bench has 2 or more segments
            // add the middle parts of the bench
            for (int i = startX+1; i < endX; i++) {
                arr.add(new Bench("top_shadows", i, y));
            }
            // add the right part of the bench
            arr.add(new Bench("right_corner_shadow", endX, y));
        }
        LOGGER.log(Level.INFO, "added row of " + arr.size() + " benches");
        return arr;
    }
    public static ArrayList<Bench> createBenchRowFlat(int startX, int endX, int y) {
        ArrayList<Bench> arr = new ArrayList<Bench>();
        // add the left part of the bench
        arr.add(new Bench("left_border", startX, y));
        if (endX - startX > 1){ // the bench has 2 or more segments
            // add the middle parts of the bench
            for (int i = startX+1; i < endX; i++) {
                arr.add(new Bench("middle", i, y));
            }
            // add the right part of the bench
            arr.add(new Bench("right_border", endX, y));
        }
        LOGGER.log(Level.INFO, "added row of " + arr.size() + " flattened benches");
        return arr;
    }
}