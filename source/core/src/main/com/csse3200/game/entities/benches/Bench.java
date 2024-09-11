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
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

/**
 * This class is responsible for creating benches in the game.
 * Benches are static entities that can be interacted with by the player.
 */
public class Bench extends Entity{
    public String type;
    public float x;
    public float y;

    public Bench(String type, int x, int y) {
        this.type = type;
        this.x    = x;
        this.y    = y;
    }

    public Bench(String type, float x, float y) {
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
        float width  = 1f;
        float height = 1f;
        bench.setScale(width, height);
        bench.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
        PhysicsUtils.setScaledCollider(bench, 1.05f, 0.75f);
        return bench;
    }
    public void create() {
        addComponent(new TextureRenderComponent("images/stations/benches/" + type + ".png"));
        addComponent(new PhysicsComponent());
        addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));
        addComponent(new StationItemHandlerComponent(type, new ArrayList<>()));

        float width  = 1f;
        float height = 1f;

        setScale(width, height);
        getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
        PhysicsUtils.setScaledCollider(this, 1.2f, 0.75f);
    }

}
