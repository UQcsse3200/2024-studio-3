package com.csse3200.game.entities.benches;

import java.util.ArrayList;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

public class Bench {
    /**
     * Creates visible bench.
     *
     * @param height Station bench in world units
     * @param type   Type of bench
     * @return Station entity of given width and height with relavent behaviors
     */
    public static Entity createBench(String type, float height) {
        Entity bench = new Entity()
                .addComponent(new TextureRenderComponent("images/stations/" + type + ".png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
                .addComponent(new StationItemHandlerComponent(type, new ArrayList<>()));


        bench.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
        bench.getComponent(TextureRenderComponent.class).scaleEntity();
        bench.scaleHeight(height);

        PhysicsUtils.setScaledCollider(bench, 1.2F, 0.8F);

        return bench;
    }

    /**
     * Creates visible station.
     *
     * @param height Station height in world units
     * @param type   Type of station
     * @param xScale Bench collision in x
     * @param yScale Bench collision in y
     * @return Station entity of given width and height with relavent behaviors
     */
    public static Entity createBench(String type, float height, float xScale, float yScale) {
        Entity bench = new Entity()
                .addComponent(new TextureRenderComponent("images/stations/" + type + ".png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
                .addComponent(new StationItemHandlerComponent(type, new ArrayList<>()));


        bench.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
        bench.getComponent(TextureRenderComponent.class).scaleEntity();
        bench.scaleHeight(height);

        PhysicsUtils.setScaledCollider(bench, xScale, yScale);

        return bench;
    }

}