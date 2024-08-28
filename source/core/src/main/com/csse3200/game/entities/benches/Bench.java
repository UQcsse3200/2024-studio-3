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
    /* bench asset dimensions
     * bench1:   15x40
     * bench2:   15x57
     * bench3-5: 107x15
     * bench4:   15x45
     * bench6:   15x105
     * bench7:   107x9
     */
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

        float width  = 0;
        float height = 0;
        float scalefactor = 8.91F;
        switch(type) {
            case "bench1":
                width  = 15f;
                height = 44f;
                scalefactor = 10.3f;
                break;
            case "bench2":
                width  = 15f;
                height = 57f;
                scalefactor = 11f;
                break;
            case "bench3-5":
                width  = 104f;
                height = 14f;
                scalefactor = 8.91f;
                break;
            case "bench4":
                width  = 15f;
                height = 43.8f;
                scalefactor = 10.3F;
                break;
            case "bench6":
                width  = 15f;
                height = 105f;
                scalefactor = 8.91F;
                break;
            case "bench6-top":
                width  = 14.1f;
                height = 48.9f;
                scalefactor = 8.91F;
                break;
            case "bench6-bottom":
                width  = 14.1f;
                height = 53f;
                scalefactor = 8.91F;
                break;
            case "bench7":
                width  = 106f;
                height = 9f;
                scalefactor = 8.91F;
        }
        if (width == 0 && height == 0) {
            throw new IllegalArgumentException(
                    "Illegal argument: 'type' does not correspond to an existing bench");
        }
        bench.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);

        PhysicsUtils.setScaledCollider(bench, 1.0F, 0.8F);


        // magic scaling factor (it is about half a pixel off)
       // float scalefactor = 8.91F;
        bench.setScale(width/scalefactor, height/scalefactor);
        // scaleY < 1 so that you can walk behind the benches
        PhysicsUtils.setScaledCollider(bench, 1, (height - 5) / height);
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
