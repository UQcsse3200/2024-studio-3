package com.csse3200.game.entities.factories;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.station.IngredientStationHandlerComponent;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.components.station.StationServingComponent;
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
            .addComponent(new TooltipsDisplay())
        .addComponent(new StationItemHandlerComponent("oven", new ArrayList<>()))
            .addComponent(new InventoryComponent(1));

    oven.getComponent(InteractionComponent.class).setAsBox(oven.getScale());

    oven.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    oven.getComponent(TextureRenderComponent.class).scaleEntity();
    oven.scaleHeight(1.5f);

    PhysicsUtils.setScaledCollider(oven, 0.3f, 0.2f);
    // Add station reference
    PhysicsComponent physicsComponent = oven.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(oven);
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
            .addComponent(new InventoryComponent(1))
        .addComponent(new StationItemHandlerComponent("stove", new ArrayList<>()));

    stove.getComponent(InteractionComponent.class).setAsBox(stove.getScale());
    stove.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    stove.getComponent(TextureRenderComponent.class).scaleEntity();
    stove.scaleHeight(1.5f);

    PhysicsUtils.setScaledCollider(stove, 0.3f, 0.2f);
    // Add station reference
    PhysicsComponent physicsComponent = stove.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(stove);
    return stove;
  }

  /**
   * Creates an apple tree, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createAppleTree() {
    Entity apple = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/apple_tree.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("apples"));

    apple.getComponent(InventoryComponent.class).addItem(new ItemComponent("Apples", ItemType.APPLE, 1));

    // Physics components
    apple.getComponent(InteractionComponent.class).setAsBox(apple.getScale());
    apple.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    apple.getComponent(TextureRenderComponent.class).scaleEntity();
    apple.scaleHeight(2f);
    PhysicsUtils.setScaledCollider(apple, 0.3f, 0.2f);

    // Add station reference
    PhysicsComponent physicsComponent = apple.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(apple);
    return apple;
  }
  public static Entity createFeetBenchTable() {
    Entity benchTable = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/bench_legs.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay());

    benchTable.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    benchTable.getComponent(TextureRenderComponent.class).scaleEntity();
    benchTable.scaleHeight(1.5f);

    PhysicsUtils.setScaledCollider(benchTable, 0.6f, 0.4f);
    return benchTable;
  }

  public static Entity createTopBenchTable() {
    Entity benchTable = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/bench_top.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new InventoryComponent(1));
            // Change this handler to the combining one
            //.addComponent(new StationItemHandlerComponent("benchTop", new ArrayList<>()));

    //benchTable.getComponent(InteractionComponent.class).setAsBox(benchTable.getScale());
    benchTable.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    benchTable.getComponent(TextureRenderComponent.class).scaleEntity();
    benchTable.scaleHeight(1.5f);

    PhysicsUtils.setScaledCollider(benchTable, 0.6f, 0.4f);
    // Add station reference
    PhysicsComponent physicsComponent = benchTable.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(benchTable);
    return benchTable;
  }

  public static Entity createMainBenchTable() {
    Entity benchTable = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/bench_middle.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new InventoryComponent(1));
            // Change this handler to the combining one
            //.addComponent(new StationItemHandlerComponent("benchMiddle", new ArrayList<>()));

    //benchTable.getComponent(InteractionComponent.class).setAsBox(benchTable.getScale());
    benchTable.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    benchTable.getComponent(TextureRenderComponent.class).scaleEntity();
    benchTable.scaleHeight(1.5f);

    PhysicsUtils.setScaledCollider(benchTable, 0.6f, 0.4f);
    // Add station reference
    PhysicsComponent physicsComponent = benchTable.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(benchTable);
    return benchTable;
  }

  public static Entity createSubmissionWindow() {
    Entity submission = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/servery.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new InventoryComponent(1))
            .addComponent(new StationServingComponent());
    submission.getComponent(InteractionComponent.class).setAsBox(submission.getScale());
    submission.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    submission.getComponent(TextureRenderComponent.class).scaleEntity();
    submission.scaleHeight(2f);
    PhysicsUtils.setScaledCollider(submission, 0.3f, 0.2f);

    // Add station reference
    PhysicsComponent physicsComponent = submission.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(submission);
    return submission;
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
        .addComponent(new StationItemHandlerComponent(type, new ArrayList<>()))
        .addComponent(new InventoryComponent(1));

    station.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    station.getComponent(TextureRenderComponent.class).scaleEntity();
    station.scaleHeight(height);

    PhysicsUtils.setScaledCollider(station, 1f, 1f);

    return station;
  }
}
