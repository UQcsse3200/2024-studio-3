package com.csse3200.game.entities.factories;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

public class StationFactory {
    /**
   * Creates visible oven.
   * @return Oven entity with relavent behaviors 
   */
  public static Entity createOven() {
    Entity oven = new Entity()
        .addComponent(new TextureRenderComponent("images/stations/oven.png"))
        .addComponent(new PhysicsComponent())
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
        .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
        .addComponent(new StationItemHandlerComponent("oven", new ArrayList<>()));


    oven.getComponent(InteractionComponent.class).setAsBox(oven.getScale());

    oven.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    oven.getComponent(TextureRenderComponent.class).scaleEntity();
    oven.scaleHeight(1.5f);

    PhysicsUtils.setScaledCollider(oven, 0.3f, 0.2f);
    
    return oven;
  }


    /**
   * Creates visible stove.
   * @return Stove entity with relavent behaviors
   */
  public static Entity createStove() {
    Entity stove = new Entity()
        .addComponent(new TextureRenderComponent("images/stations/stove.png"))
        .addComponent(new PhysicsComponent())
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
        .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
        .addComponent(new StationItemHandlerComponent("stove", new ArrayList<>()));


    stove.getComponent(InteractionComponent.class).setAsBox(stove.getScale());
    stove.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    stove.getComponent(TextureRenderComponent.class).scaleEntity();
    stove.scaleHeight(1.5f);

    PhysicsUtils.setScaledCollider(stove, 0.3f, 0.2f);
    
    return stove;
  }

  /**
   * Creates visible station.
   * @param height Station height in world units
   * @param type Type of station
   * @return Station entity of given width and height with relavent behaviors
   */
  public static Entity createStation(String type, float height) {
    Entity station = new Entity()
        .addComponent(new TextureRenderComponent("images/stations/benches/"+ type + ".png"))
        .addComponent(new PhysicsComponent())
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
        .addComponent(new StationItemHandlerComponent(type, new ArrayList<>()));


    station.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    station.getComponent(TextureRenderComponent.class).scaleEntity();
    station.scaleHeight(height);

    PhysicsUtils.setScaledCollider(station, 1f, 1f);

    return station;
  }
}
