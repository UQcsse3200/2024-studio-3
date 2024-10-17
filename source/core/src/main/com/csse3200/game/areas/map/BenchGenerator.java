package com.csse3200.game.areas.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;

import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * Utility class for creating benches
 */
public class BenchGenerator {
    static private final Logger LOGGER = Logger.getLogger(BenchGenerator.class.getPackage().getName());
    /**
     * Creates a column of benches
     * @param x - x coordinate
     * @param startY - start y coordinate
     * @param endY - end y coordinate
     * @return - returns an ArrayList of the benches created.
     */
    public static ArrayList<Bench> createBenchColumn(int x, int startY, int endY) {
        ArrayList<Bench> arr = new ArrayList<>();
        // add the bottom part of the bench and set collisions for whole column
        Bench b = new Bench("bottom_shadow", x, startY);
        b.addComponent(new ColliderComponent()
                .setAsBoxAligned(new Vector2(1f, endY - startY + 1),
                        PhysicsComponent.AlignX.LEFT, PhysicsComponent.AlignY.BOTTOM));
        arr.add(b);

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
    /**
     * Creates a row of benches
     * @param startX - start x coordinate
     * @param endX - end x coordinate
     * @param y - y coordinate
     * @return - returns an ArrayList of the benches created.
     */
    public static ArrayList<Bench> createBenchRow(int startX, int endX, int y) {
        ArrayList<Bench> arr = new ArrayList<>();
        // add the left part of the bench and set collisions for whole row
        Bench b = new Bench("left_corner_shadow", startX, y);
        b.addComponent(new ColliderComponent()
                .setAsBoxAligned(new Vector2(endX - startX + 1, 1f),
                        PhysicsComponent.AlignX.LEFT, PhysicsComponent.AlignY.BOTTOM));
        arr.add(b);

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

    /**
     * Creates a single bench
     * @param x - x coordinate
     * @param y - y coordinate
     * @return - returns an ArrayList of the benches created.
     */
    public static ArrayList<Bench> singleBench(int x, int y) {
        ArrayList<Bench> arr = new ArrayList<>();
        Bench b = new Bench("vertical", x, y);
        b.addComponent(new ColliderComponent()
                .setAsBoxAligned(new Vector2(1f, 1f),
                        PhysicsComponent.AlignX.LEFT, PhysicsComponent.AlignY.BOTTOM));
        arr.add(b);
        return arr;
    }

    /**
     * Creates a single shadow bench
     * @param x - x coordinate
     * @param y - y coordinate
     * @return - returns an ArrayList of the benches created.
     */
    public static ArrayList<Bench> singleShadowBench(int x, int y) {
        ArrayList<Bench> arr = new ArrayList<>();
        Bench b = new Bench("bottom_shadow", x, y);
        b.addComponent(new ColliderComponent()
                .setAsBoxAligned(new Vector2(1f, 1f),
                        PhysicsComponent.AlignX.LEFT, PhysicsComponent.AlignY.BOTTOM));
        arr.add(b);
        return arr;
    }
    /**
     * Creates a single 'blocker' bench
     * @param x - x coordinate
     * @param y - y coordinate
     * @return - returns an ArrayList of the benches created.
     */
    public static ArrayList<Bench> singleBlocker(int x, int y) {
        ArrayList<Bench> arr = new ArrayList<>();
        Bench b = new Bench("single", x, y);
        b.addComponent(new ColliderComponent()
                .setAsBoxAligned(new Vector2(1f, 1f),
                        PhysicsComponent.AlignX.LEFT, PhysicsComponent.AlignY.BOTTOM));
        arr.add(b);
        return arr;
    }
}