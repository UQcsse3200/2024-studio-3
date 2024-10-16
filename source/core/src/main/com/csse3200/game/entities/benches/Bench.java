package com.csse3200.game.entities.benches;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.components.station.StationMealComponent;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.station.*;

/**
 * This class is responsible for creating benches in the game.
 * Benches are static entities that can be interacted with by the player.
 * This initialiser handles applying the texture, scaling and collisions of the bench.
 */
public class Bench extends Entity{
    public String type;
    public int x;
    public int y;

    /**
     * initialiser creates a bench of a certain type at a location
     * @param type - file name of bench image
     * @param x - x coordinate
     * @param y - y coordinate
     */
    public Bench(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;

        setScale(1f, 1f);

        addComponent(new TextureRenderComponent("images/stations/benches/" + type + ".png"));
        addComponent(new PhysicsComponent().setBodyType(BodyType.StaticBody));
        addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE));

        addComponent(new TooltipsDisplay());
        addComponent(new InventoryComponent(4));
        addComponent(new MixingBenchHoverComponent());
        addComponent(new StationMealComponent("combining", new ArrayList<>()));

        ServiceLocator.getInteractableService().registerEntity(this);
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
                .addComponent(new StationItemHandlerComponent(type));
        float width  = 1f;
        float height = 1f;
        bench.setScale(width, height);
        bench.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
        PhysicsUtils.setScaledCollider(bench, 1.5f, 0.75f);
        return bench;
    }

    // get x coordinate
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
