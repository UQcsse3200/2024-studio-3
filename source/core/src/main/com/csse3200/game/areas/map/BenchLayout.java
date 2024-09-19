package com.csse3200.game.areas.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.areas.map.BenchGenerator;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

/**
 * Class for managing bench layouts
 */
public class BenchLayout {
    public static ArrayList<Bench> levelOne() {
        ArrayList<Bench> benches = new ArrayList<Bench>();
        // Bottom bench row
        benches.addAll(BenchGenerator.createBenchRowFlat(4, 15, 1));
        // Top shadow bench row
        benches.addAll(BenchGenerator.createBenchRow(4, 14, 10));
        // Left vertical bench column
        benches.addAll(BenchGenerator.createBenchColumn(4, 3, 7));
        // Random single bench
        benches.add(new Bench(7, 8));
        return benches;
    }
}