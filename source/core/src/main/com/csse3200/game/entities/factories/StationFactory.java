package com.csse3200.game.entities.factories;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.components.FlameComponent;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.station.FireExtinguisherHandlerComponent;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ServiceLocator;

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
            .addComponent(new TooltipsDisplay())
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
            .addComponent(new TooltipsDisplay())
        .addComponent(new StationItemHandlerComponent("stove", new ArrayList<>()));


    stove.getComponent(InteractionComponent.class).setAsBox(stove.getScale());
    stove.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    stove.getComponent(TextureRenderComponent.class).scaleEntity();
    stove.scaleHeight(1.5f);

    PhysicsUtils.setScaledCollider(stove, 0.3f, 0.2f);
    
    return stove;
  }
  public static Entity createFireExtinguisher() {
    Entity fireExtinguisher = new Entity()
            .addComponent(new TextureRenderComponent("images/fireExtinguisher/Fire_Extinguisher.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new FireExtinguisherHandlerComponent());
    fireExtinguisher.getComponent(InteractionComponent.class).setAsBox(fireExtinguisher.getScale());
    fireExtinguisher.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    fireExtinguisher.getComponent(TextureRenderComponent.class).scaleEntity();
    fireExtinguisher.scaleHeight(1.5f);
    InteractionComponent feInteractionComponent = fireExtinguisher.getComponent(InteractionComponent.class);
    feInteractionComponent.create();
    feInteractionComponent.getFixture().setUserData(fireExtinguisher);
    return fireExtinguisher;
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

  public static Entity createFlame() {
    Entity flame = new Entity()
            .addComponent(new FlameComponent())
            .addComponent(new PhysicsComponent())
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE));
    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/fireExtinguisher/atlas/flame.atlas", TextureAtlas.class));
    System.out.println("Adding flame animation");
    animator.addAnimation("flame", 0.1f, Animation.PlayMode.LOOP);
    System.out.println("Done adding flame animation");

    flame.addComponent(animator);

    flame.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    return flame;
  }
}
