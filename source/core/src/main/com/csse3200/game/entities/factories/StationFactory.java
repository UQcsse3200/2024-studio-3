package com.csse3200.game.entities.factories;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.components.FlameComponent;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplayHoverComponent;
import com.csse3200.game.components.station.*;
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
    AnimationRenderComponent animator =
            new AnimationRenderComponent(ServiceLocator.getResourceService().getAsset("images/stations/oven/oven.atlas", TextureAtlas.class));
    System.out.println("Adding Oven animation");
    animator.addAnimation("Oven", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("OvenDefault", 0.2f, Animation.PlayMode.LOOP);


    Entity oven = new Entity()

        .addComponent(new PhysicsComponent())
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
        .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
        .addComponent(new TooltipsDisplay())
        .addComponent(new StationCookingComponent())
        .addComponent(new StationItemHandlerComponent("oven"))
        .addComponent(new InventoryComponent(1))
        .addComponent(new InventoryDisplayHoverComponent());



    oven.scaleHeight(0.64f);
    PhysicsUtils.setScaledCollider(oven, 1f, 1f);

    // Add station reference
    PhysicsComponent physicsComponent = oven.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(oven);
    oven.addComponent(animator);
    animator.startAnimation("Oven");
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
        .addComponent(new InventoryDisplayHoverComponent())
        .addComponent(new StationCookingComponent())
        .addComponent(new StationItemHandlerComponent("stove"));

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

  public static Entity createBin() {
    Entity bin = new Entity()
        .addComponent(new TextureRenderComponent("images/stations/refrigerator.png"))
        .addComponent(new PhysicsComponent())
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
        .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
        .addComponent(new StationBinComponent());

    bin.getComponent(InteractionComponent.class).setAsBox(bin.getScale());
    bin.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    bin.getComponent(TextureRenderComponent.class).scaleEntity();
    bin.scaleHeight(1.5f);

    PhysicsUtils.setScaledCollider(bin, 0.3f, 0.2f);
    // Add station reference
    PhysicsComponent physicsComponent = bin.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(bin);
    return bin;
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
   * Creates an apple tree, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createBananaTree() {
    Entity apple = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/apple_tree.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new StationCollectionComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("bananaTree", "banana"));

    // Physics components
    apple.getComponent(InteractionComponent.class).setAsBox(apple.getScale());
    apple.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    apple.getComponent(TextureRenderComponent.class).scaleEntity();
    apple.scaleHeight(1.5f);
    PhysicsUtils.setScaledCollider(apple, 0.3f, 0.2f);

    // Add station reference
    PhysicsComponent physicsComponent = apple.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(apple);
    return apple;
  }

  /**
   * Creates an apple tree, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createStrawberries() {
    Entity strawberry = new Entity()
            .addComponent(new TextureRenderComponent("images/ingredients/raw_strawberry.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new StationCollectionComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("strawberriesStation", "strawberry"));

    // Physics components
    strawberry.getComponent(InteractionComponent.class).setAsBox(strawberry.getScale());
    strawberry.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    strawberry.getComponent(TextureRenderComponent.class).scaleEntity();
    strawberry.scaleHeight(0.5f);
    PhysicsUtils.setScaledCollider(strawberry, 0.3f, 0.2f);

    // Add station reference
    PhysicsComponent physicsComponent = strawberry.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(strawberry);
    return strawberry;
  }

  /**
   * Creates the leg section of the bench table, a station where combinations of ingredients are done.
   * @return Entity of type station with added components and references
   */
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

  /**
   * Creates the top section of the bench table, a station where combinations of ingredients are done.
   * @return Entity of type station with added components and references
   */
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

  /**
   * Creates the main section of the bench table, a station where combinations of ingredients are done.
   * @return Entity of type station with added components and references
   */
  public static Entity createMainBenchTable() {
    Entity benchTable = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/benches/right_border.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new InventoryComponent(4))
            .addComponent(new InventoryDisplayHoverComponent())
            .addComponent(new StationMealComponent("combining", new ArrayList<>()));
            // Change this handler to the combining one
            //.addComponent(new StationItemHandlerComponent("benchMiddle", new ArrayList<>()));

    benchTable.getComponent(InteractionComponent.class).setAsBox(benchTable.getScale());
    benchTable.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    benchTable.getComponent(TextureRenderComponent.class).scaleEntity();
    benchTable.scaleHeight(1f);
    benchTable.scaleWidth(1f);
    PhysicsUtils.setScaledCollider(benchTable, 1.05f, 0.75f);
    // Add station reference
    PhysicsComponent physicsComponent = benchTable.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(benchTable);
    return benchTable;
  }

  /**
   * Creates the submission window, this is where submissions are made with dishes
   * @return Entity of type station with added components and references
   */
  public static Entity createSubmissionWindow() {


    Entity submission = new Entity()
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new InventoryComponent(1))
            .addComponent(new StationServingComponent());


    submission.getComponent(InteractionComponent.class).setAsBox(submission.getScale());
    submission.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    PhysicsUtils.setScaledCollider(submission, 1f, 1f);

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/stations/Servery_Animation/servery.atlas", TextureAtlas.class));
    animator.addAnimation("servery_idle", 0.1f, Animation.PlayMode.LOOP);

    submission.addComponent(animator);

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
        .addComponent(new StationItemHandlerComponent(type))
        .addComponent(new InventoryComponent(1));

    station.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    station.getComponent(TextureRenderComponent.class).scaleEntity();
    station.scaleHeight(height);

    PhysicsUtils.setScaledCollider(station, 1f, 1f);

    return station;
  }

  public static Entity createFlame() {
    Entity flame = new Entity()
            .addComponent(new FlameComponent());
    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/fireExtinguisher/atlas/flame.atlas", TextureAtlas.class));
    animator.addAnimation("flame", 0.1f, Animation.PlayMode.LOOP);

    flame.addComponent(animator);
    return flame;
  }
}
