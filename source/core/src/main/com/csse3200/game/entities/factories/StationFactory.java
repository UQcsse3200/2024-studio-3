package com.csse3200.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

public class StationFactory {
    

    /**
   * Creates visible station.
   * @param width Station width in world units
   * @param height Station height in world units
   * @param type Type of station
   * @return Station entity of given width and height with relavent behaviors
   */
  public static Entity createStation(float width, float height, String type) {
    Entity station = new Entity()
        .addComponent(new TextureRenderComponent("images/stations/" + type + ".png"))
        .addComponent(new PhysicsComponent())
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));
        //.addComponent(new StationInventoryComponent());


        station.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
        station.getComponent(TextureRenderComponent.class).scaleEntity();
        station.scaleHeight(2.5f);
        PhysicsUtils.setScaledCollider(station, 0.5f, 0.2f);

    station.setScale(width, height);
    return station;
  }
}
