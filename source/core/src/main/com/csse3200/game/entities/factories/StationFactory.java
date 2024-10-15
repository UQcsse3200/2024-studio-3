package com.csse3200.game.entities.factories;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.components.FlameComponent;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.player.InventoryComponent;
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

  private StationFactory() {
    //Private instantiation.
  }

    /**
   * Creates visible oven.
   * @return Oven entity with relavent behaviors 
   */
  public static Entity createOven() {
    AnimationRenderComponent animator =
            new AnimationRenderComponent(ServiceLocator.getResourceService().getAsset("images/stations/oven/oven.atlas", TextureAtlas.class));
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
        .addComponent(new CookingHoverComponent())
        .addComponent(new StationProgressDisplay());


    //set scale
    oven.scaleHeight(1f);
    //ensure cant be pushed
    oven.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    PhysicsUtils.setScaledCollider(oven, 1f, 1f);

    // Add station reference
    PhysicsComponent physicsComponent = oven.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();

    body.setUserData(oven);
    oven.addComponent(animator);
    animator.startAnimation("OvenDefault");
    return oven;
  }

  /**
   * Creates visible stove.
   * @return Stove entity with relavent behaviors
   */
  public static Entity createCuttingBoard() {
    Entity cuttingBoard = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/chopping_board/choppingboardbench.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new InventoryComponent(1))
            .addComponent(new ChoppingBoardHoverComponent())
            .addComponent(new StationProgressDisplay())
            .addComponent(new StationChoppingComponent())
            .addComponent(new StationItemHandlerComponent("cutting board"));

    cuttingBoard.getComponent(InteractionComponent.class).setAsBox(cuttingBoard.getScale());
    cuttingBoard.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    cuttingBoard.getComponent(TextureRenderComponent.class).scaleEntity();
    cuttingBoard.scaleHeight(1f);

    PhysicsUtils.setScaledCollider(cuttingBoard, 1f, 1f);
    // Add station reference
    PhysicsComponent physicsComponent = cuttingBoard.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(cuttingBoard);
    return cuttingBoard;
  }

    /**
   * Creates visible stove.
   * @return Stove entity with relevant behaviors
   */
  public static Entity createStove() {
    Entity stove = new Entity()
        .addComponent(new TextureRenderComponent("images/stations/stove.png"))
        .addComponent(new PhysicsComponent())
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
        .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
        .addComponent(new TooltipsDisplay())
        .addComponent(new InventoryComponent(1))
        .addComponent(new CookingHoverComponent())
        .addComponent(new StationCookingComponent())  
        .addComponent(new StationItemHandlerComponent("stove"))
        .addComponent(new StationProgressDisplay());

    stove.getComponent(InteractionComponent.class).setAsBox(stove.getScale());
    stove.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    stove.getComponent(TextureRenderComponent.class).scaleEntity();
    stove.scaleHeight(1f);

    PhysicsUtils.setScaledCollider(stove, 1f, 1f);
    // Add station reference
    PhysicsComponent physicsComponent = stove.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(stove);
    return stove;
  }

  public static Entity createBin() {
    Entity bin = new Entity()
        .addComponent(new TextureRenderComponent("images/stations/benches/garbage_bin.png"))
        .addComponent(new PhysicsComponent())
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
        .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
        .addComponent(new StationBinComponent())
        .addComponent(new BinHoverComponent());

    bin.getComponent(InteractionComponent.class).setAsBox(bin.getScale());
    bin.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    bin.getComponent(TextureRenderComponent.class).scaleEntity();
    bin.scaleHeight(1f);

    PhysicsUtils.setScaledCollider(bin, 1f, 1f);
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
   * Creates an banana basket, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createBananaBasket() {
    Entity banana = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/baskets/basket_banana.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new BasketHoverComponent())
            .addComponent(new StationCollectionComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("bananaTree", "banana"));
    // Physics components

    banana.getComponent(InteractionComponent.class).setAsBox(banana.getScale());
    banana.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    banana.getComponent(TextureRenderComponent.class).scaleEntity();
    banana.scaleHeight(1f);
    PhysicsUtils.setScaledCollider(banana, 1f, 1f);
    // Add station reference
    PhysicsComponent physicsComponent = banana.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(banana);
    return banana;
  }

  /**
   * Creates an strawberry basket, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createStrawberryBasket() {
    Entity strawberry = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/baskets/basket_strawberry.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new BasketHoverComponent())
            .addComponent(new StationCollectionComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("strawberriesStation", "strawberry"));
    // Physics components
    strawberry.getComponent(InteractionComponent.class).setAsBox(strawberry.getScale());
    strawberry.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    strawberry.getComponent(TextureRenderComponent.class).scaleEntity();
    strawberry.scaleHeight(1f);
    PhysicsUtils.setScaledCollider(strawberry, 1f, 1f);
    // Add station reference
    PhysicsComponent physicsComponent = strawberry.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(strawberry);
    return strawberry;
  }

  /**
   * Creates an strawberry basket, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createAcaiBasket() {
    Entity acai = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/baskets/basket_acai.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new BasketHoverComponent())
            .addComponent(new StationCollectionComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("acaiStation", "acai"));
    // Physics components
    acai.getComponent(InteractionComponent.class).setAsBox(acai.getScale());
    acai.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    acai.getComponent(TextureRenderComponent.class).scaleEntity();
    acai.scaleHeight(1f);
    PhysicsUtils.setScaledCollider(acai, 1f, 1f);
    // Add station reference
    PhysicsComponent physicsComponent = acai.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(acai);
    return acai;
  }

  /**
   * Creates an strawberry basket, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createLettuceBasket() {
    Entity lettuce = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/baskets/basket_lettuce.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new BasketHoverComponent())
            .addComponent(new StationCollectionComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("lettuceStation", "lettuce"));
    // Physics components
    lettuce.getComponent(InteractionComponent.class).setAsBox(lettuce.getScale());
    lettuce.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    lettuce.getComponent(TextureRenderComponent.class).scaleEntity();
    lettuce.scaleHeight(1f);
    PhysicsUtils.setScaledCollider(lettuce, 1f, 1f);
    // Add station reference
    PhysicsComponent physicsComponent = lettuce.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(lettuce);
    return lettuce;
  }

  /**
   * Creates an strawberry basket, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createTomatoBasket() {
    Entity tomato = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/baskets/basket_tomato.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new StationCollectionComponent())
            .addComponent(new BasketHoverComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("tomatoStation", "tomato"));
    // Physics components
    tomato.getComponent(InteractionComponent.class).setAsBox(tomato.getScale());
    tomato.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    tomato.getComponent(TextureRenderComponent.class).scaleEntity();
    tomato.scaleHeight(1f);
    PhysicsUtils.setScaledCollider(tomato, 1f, 1f);
    // Add station reference
    PhysicsComponent physicsComponent = tomato.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(tomato);
    return tomato;
  }


  /**
   * Creates an cucumber basket, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createCucumberBasket() {
    Entity cucumber = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/baskets/basket_cucumber.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new StationCollectionComponent())
            .addComponent(new BasketHoverComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("cucumberStation", "cucumber"));
    // Physics components
    cucumber.getComponent(InteractionComponent.class).setAsBox(cucumber.getScale());
    cucumber.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    cucumber.getComponent(TextureRenderComponent.class).scaleEntity();
    cucumber.scaleHeight(1f);
    PhysicsUtils.setScaledCollider(cucumber, 1f, 1f);
    // Add station reference
    PhysicsComponent physicsComponent = cucumber.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(cucumber);
    return cucumber;
  }

  /**
   * Creates an steak fridge, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createBeefFridge() {
    Entity beef = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/fridge/fridge_meat.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new BasketHoverComponent())
            .addComponent(new StationCollectionComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("beefStation", "beef"));
    // Physics components
    beef.getComponent(InteractionComponent.class).setAsBox(beef.getScale());
    beef.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    beef.getComponent(TextureRenderComponent.class).scaleEntity();
    beef.scaleHeight(1f);
    PhysicsUtils.setScaledCollider(beef, 0.8f, 0.8f);
    // Add station reference
    PhysicsComponent physicsComponent = beef.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(beef);
    return beef;
  }

  /**
   * Creates an steak fridge, a type of ingredient station
   * @return Entity of type station with added components and references
   */
  public static Entity createChocolateFridge() {
    Entity chocolate = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/fridge/fridge_choc.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new BasketHoverComponent())
            .addComponent(new StationCollectionComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new IngredientStationHandlerComponent("beefStation", "chocolate"));
    // Physics components
    chocolate.getComponent(InteractionComponent.class).setAsBox(chocolate.getScale());
    chocolate.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    chocolate.getComponent(TextureRenderComponent.class).scaleEntity();
    chocolate.scaleHeight(1f);
    PhysicsUtils.setScaledCollider(chocolate, 0.8f, 0.8f);
    // Add station reference
    PhysicsComponent physicsComponent = chocolate.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(chocolate);
    return chocolate;
  }

  /**
   * Creates an dishwasher, a type of item station which gives out clean plates. 
   * @return Entity of type station with added components and references
   */
  public static Entity createDishwasher() {
    Entity dishwasher = new Entity()
            .addComponent(new TextureRenderComponent("images/stations/benches/dishwasher.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new TooltipsDisplay())
            .addComponent(new BasketHoverComponent())
            .addComponent(new StationCollectionComponent())
            .addComponent(new InventoryComponent(1))
            .addComponent(new PlateStationHandlerComponent());
    
    // Physics components
    dishwasher.getComponent(InteractionComponent.class).setAsBox(dishwasher.getScale());
    dishwasher.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    dishwasher.getComponent(TextureRenderComponent.class).scaleEntity();
    dishwasher.scaleHeight(1f);
    PhysicsUtils.setScaledCollider(dishwasher, 1f, 1f);
    // Add station reference
    PhysicsComponent physicsComponent = dishwasher.getComponent(PhysicsComponent.class);
    Body body = physicsComponent.getBody();
    body.setUserData(dishwasher);
    return dishwasher;
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
            .addComponent(new MixingBenchHoverComponent())
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
            .addComponent(new SubmitHoverComponent())
            .addComponent(new StationServingComponent());

    submission.getComponent(InteractionComponent.class).setAsBox(submission.getScale());
    submission.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    PhysicsUtils.setScaledCollider(submission, 1f, 1f);
    submission.scaleHeight(1f);

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
