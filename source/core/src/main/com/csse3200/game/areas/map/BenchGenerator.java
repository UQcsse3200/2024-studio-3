package com.csse3200.game.entities.benches;

import java.util.ArrayList;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

/**
 * Utility class for creating benches
 */
public class BenchGenerator {

    /**
     * Creates a column of benches
     * @param area - GameArea for the benches to be spawned in
     * @param type - type of bench (for file path)
     * @param x - x coordinate
     * @param startY - start y coordinate
     * @param endY - end y coordinate
     * @return - returns an ArrayList of the benches created.
     */
    public static ArrayList<Bench> createBenchColumn(
            String type, int x, int startY, int endY) {
        ArrayList<Bench> arr = new ArrayList<Bench>();
        for (int i = startY; i <= endY; i++) {
            arr.add(new Bench(type, x, i));
        }
        return arr;
    }
    public static ArrayList<Bench> createBenchRow(
            String type, int startX, int endX, int y) {
        ArrayList<Bench> arr = new ArrayList<Bench>();
        for (int i = startX; i <= endX; i++) {
            arr.add(new Bench(type, i, y));
        }
        return arr;
    }
}