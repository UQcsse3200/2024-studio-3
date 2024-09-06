package com.csse3200.game.entities.benches;

import java.util.ArrayList;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

public class Bench extends Entity{
    public String type;
    public int x;
    public int y;
    public Bench(String type, int x, int y) {
        this.type = type;
        this.x    = x;
        this.y    = y;
    }

    /**
     * Creates visible bench.
     *
     * @param type   Type of bench
     * @return Station entity of given width and height with relevant behaviors
     */
    public static Entity createBench(String type) {
        Entity bench = new Entity()
                .addComponent(new TextureRenderComponent("images/stations/benches/" + type + ".png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
                .addComponent(new StationItemHandlerComponent(type, new ArrayList<>()));
       // float width  = 7f;
        //float height = 7f;
        //float scalefactor = 7f;
        bench.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
        return bench;
    }

}
