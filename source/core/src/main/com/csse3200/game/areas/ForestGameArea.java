package com.csse3200.game.areas;


import com.badlogic.gdx.utils.Null;
import com.csse3200.game.components.cutscenes.GoodEnd;
import com.csse3200.game.components.maingame.TextDisplay;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.entities.configs.PlayerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.areas.terrain.TerrainFactory.TerrainType;
import com.csse3200.game.components.gamearea.GameAreaDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.NPCFactory;
import com.csse3200.game.components.maingame.TextDisplay;
import com.csse3200.game.entities.factories.ObstacleFactory;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.PlayerFactory;
import com.csse3200.game.entities.factories.StationFactory;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.entities.factories.PlateFactory;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.utils.math.GridPoint2Utils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** Forest area for the demo game with trees, a player, and some enemies. */
public class ForestGameArea extends GameArea {
  private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
  private static final int NUM_TREES = 7;
  private static final int NUM_GHOSTS = 2;
  private static final int NUM_CUSTOMERS_BASE = 1;
  private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(5, 3);
  private static final float WALL_WIDTH = 0.1f;
  private static final String[] forestTextures = {
    "images/special_NPCs/boss.png",
    "images/meals/acai_bowl.png",
    "images/meals/banana_split.png",
    "images/meals/salad.png",
    "images/meals/steak_meal.png",
    "images/ingredients/raw_cucumber.png",
    "images/ingredients/raw_acai.png",
    "images/ingredients/raw_tomato.png",
    "images/ingredients/raw_strawberry.png",
    "images/ingredients/chopped_strawberry.png",
    "images/ingredients/chopped_chocolate.png",
    "images/ingredients/chopped_cucumber.png",
    "images/ingredients/chopped_lettuce.png",
    "images/ingredients/chopped_acai.png",
    "images/ingredients/chopped_tomato.png",
    "images/ingredients/raw_lettuce.png",
    "images/ingredients/raw_chocolate.png",
    "images/money.png",
    "images/meals/fruit_salad.png",
    "images/ingredients/raw_banana.png",
    "images/ingredients/chopped_banana.png",
    "images/ingredients/raw_fish.png",
    "images/ingredients/cooked_fish.png",
    "images/ingredients/raw_beef.png",
    "images/ingredients/cooked_beef.png",
    "images/ingredients/burnt_beef.png",
    "images/tiles/orange_tile.png",
    "images/tiles/blue_tile.png",
    "images/stations/oven.png",
    "images/stations/stove.png",
    "images/stations/bench.png",
    "images/chef_player.png",
    "images/frame/top_border.png",
    "images/frame/left_border.png",
    "images/frame/bottom_border.png",
    "images/frame/right_border.png",
    "images/frame/vert_border.png",
    "images/frame/door.png",
    "images/frame/top_door_left_part.png",
    "images/frame/top_door_right_part.png",
    "images/frame/bottom_left_inv.png",
    "images/frame/bottom_right_inv.png",
    "images/frame/top_border_wall.png",
    "images/frame/bottom_border_wall.png",
    "images/frame/border.png",
    "images/stations/benches/bench7.png",
    "images/stations/benches/bench2.png",
    "images/stations/benches/bench3-5.png",
    "images/stations/benches/bench4.png",
    "images/stations/benches/bench6.png",
    "images/stations/benches/bench1.png",
          "images/tooltip_bg.png",
    "images/stations/benches/bench6-bottom.png",
    "images/stations/benches/bench6-top.png",
    "images/fireExtinguisher/Fire_Extinguisher.png",
          "images/platecomponent/cleanplate.png",
          "images/platecomponent/dirtyplate.png",
          "images/platecomponent/stackplate.png",
          "images/platecomponent/stackedplates/1plates.png",
          "images/platecomponent/stackedplates/2plates.png",
          "images/platecomponent/stackedplates/3plates.png",
          "images/platecomponent/stackedplates/4plates.png",
          "images/platecomponent/stackedplates/5plates.png"
  };
  private static final String[] forestTextureAtlases = {
    "images/terrain_iso_grass.atlas", "images/ghost.atlas", "images/ghostKing.atlas", "images/animal_images/gorilla.atlas",
          "images/animal_images/goose.atlas", "images/animal_images/goat.atlas", "images/animal_images/monkey.atlas",
          "images/animal_images/snow_wolf.atlas","images/player.atlas", "images/fireExtinguisher/atlas/flame.atlas"
  };
  private static final String[] forestSounds = {"sounds/Impact4.ogg"};
  private static final String backgroundMusic = "sounds/BGM_03_mp3.mp3";
  private static final String[] forestMusic = {backgroundMusic};

  private final TerrainFactory terrainFactory;

  private Entity player;

  public enum personalCustomerEnums{
    HANK,
    LEWIS,
    SILVER,
    JOHN,
    MOONKI,
    BASIC_CHICKEN,
    BASIC_SHEEP
  }

  /**
   * Initialise this ForestGameArea to use the provided TerrainFactory.
   * @param terrainFactory TerrainFactory used to create the terrain for the GameArea.
   * @requires terrainFactory != null
   */
  public ForestGameArea(TerrainFactory terrainFactory) {
    super();
    this.terrainFactory = terrainFactory;
  }

  /** Create the game area, including terrain, static entities (trees), dynamic entities (player) */
  @Override
  public void create() {

    loadAssets();

    displayUI();

    spawnTerrain();
    // Spawn the restaurant
    spawnDoor();
    spawnWall();
    make_border();
    spawnBenches();
    spawnStations();
    // Spawn beef
    spawnBeef("cooked");
    spawnStrawberry("chopped");
    spawnLettuce("chopped");
    Entity customerSpawnController = spawnCustomerController();
    customerSpawnController.getEvents().trigger(personalCustomerEnums.MOONKI.name());
    customerSpawnController.getEvents().trigger(personalCustomerEnums.BASIC_CHICKEN.name());

    //spawnplates
    spawnStackPlate(5); //testplate spawn
    //spawnPlatewithMeal();

    // Spawn the player
    player = spawnPlayer();
    //ServiceLocator.getEntityService().getEvents().trigger("SetText", "Boss: Rent is due");
    //triggerFiredEnd();    // Trigger the fired (bad) ending

    playMusic();
    ServiceLocator.getLevelService().getEvents().addListener("spawnCustomer", this::spawnCustomer);
  }

  private void displayUI() {
    Entity ui = new Entity();
    ui.addComponent(new GameAreaDisplay("Kitchen"));
    spawnEntity(ui);
  }

  private void spawnTerrain() {
    // Background terrain
    terrain = terrainFactory.createTerrain(TerrainType.KITCHEN_DEMO);
    spawnEntity(new Entity().addComponent(terrain));

    // Terrain walls
    float tileSize = terrain.getTileSize();
    GridPoint2 tileBounds = terrain.getMapBounds(0);
    Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

    // Left
    spawnEntityAt(
        ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y), GridPoint2Utils.ZERO, false, false);
    // Right
    spawnEntityAt(
        ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y),
        new GridPoint2(tileBounds.x, 0),
        false,
        false);
    // Top
    spawnEntityAt(
        ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH),
        new GridPoint2(0, tileBounds.y),
        false,
        false);
    // Bottom
    spawnEntityAt(
        ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH), GridPoint2Utils.ZERO, false, false);

  }

  /**
   * Spawns the entry and exit doors of the restaurant
   */
  private void spawnDoor() {
    float tileSize = terrain.getTileSize();
    GridPoint2 tileBounds = terrain.getMapBounds(0);
    Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

    //Entry door
    GridPoint2 position = new GridPoint2(0,(int)tileBounds.y-1);
    Entity door = ObstacleFactory.createDoor("top_door_left_part",tileSize);
    spawnEntityAt(door,position,true,false);

    position = new GridPoint2(1,(int)tileBounds.y-1);
    door = ObstacleFactory.createDoor("top_door_right_part",tileSize);
    spawnEntityAt(door,position,true,false);

    //Exit door
    position = new GridPoint2(0,0);
    door = ObstacleFactory.createDoor("bottom_left_inv",tileSize);
    spawnEntityAt(door,position,true,false);

    position = new GridPoint2(1,0);
    door = ObstacleFactory.createDoor("bottom_right_inv",tileSize);
    spawnEntityAt(door,position,true,false);
  }

  /**
   * Spawns the wall around the restaurant
   */
  private void spawnWall() {
    float tileSize = terrain.getTileSize();
    GridPoint2 tileBounds = terrain.getMapBounds(0);
    Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

    for(int x=0;x<2;x++){
      GridPoint2 position = new GridPoint2(x,0);
      Entity left = ObstacleFactory.createBorder("border",tileSize);
      spawnEntityAt(left,position,true,false);
    }

    for(int x=2;x<(int)tileBounds.x;x++){
      GridPoint2 position = new GridPoint2(x,(int)tileBounds.y-1);
      Entity top = ObstacleFactory.createBorder("top_border_wall",tileSize);
      spawnEntityAt(top,position,true,false);


      position = new GridPoint2(x,0);
      Entity bottom = ObstacleFactory.createBorder("bottom_border_wall",tileSize);
      spawnEntityAt(bottom,position,true,false);
    }

  }

  /**
   * Renders a black border around the restaurant
   */
  private void make_border(){
    float tileSize = terrain.getTileSize();
    GridPoint2 tileBounds = terrain.getMapBounds(0);
    Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

    /**
    //top border
    for(int x=0;x<(int)tileBounds.x; x++){
      GridPoint2 position=new GridPoint2(x,(int)tileBounds.y-1);
      Entity top = ObstacleFactory.createBorder("top_border",tileSize);
      spawnEntityAt(top,position,true,false);
    }
     */

    /**
    //left border
    for(int y=0;y<(int)tileBounds.y;y++){
      GridPoint2 position = new GridPoint2(0,y);
      Entity left = ObstacleFactory.createBorder("left_border",tileSize);
      spawnEntityAt(left,position,true,false);
    }
     */

    //bottom border
    for(int x=0;x<(int)tileBounds.x; x++){
      GridPoint2 positon = new GridPoint2(x,0);
      Entity bottom = ObstacleFactory.createBorder("bottom_border",tileSize);
      spawnEntityAt(bottom,positon,true,false);
    }

    //right border
    for(int y=0;y<(int)tileBounds.y;y++){
      GridPoint2 position = new GridPoint2((int)tileBounds.x-1,y);
      Entity right = ObstacleFactory.createBorder("right_border",tileSize);
      spawnEntityAt(right,position,true,false);
    }


    //separation border
    for(int y=0;y<(int)tileBounds.y;y++) {
      GridPoint2 position = new GridPoint2(2,y);
      Entity separate = ObstacleFactory.createBorder("left_border",tileSize);
      spawnEntityAt(separate,position,true,false);
    }

  }

  private void spawnStations() {
    GridPoint2 ovenPos = new GridPoint2(5,4);
    Entity oven = StationFactory.createOven();
    spawnEntityAt(oven, ovenPos, true, false);
    oven.setPosition(oven.getPosition().x , oven.getPosition().y + 1.3f);

    GridPoint2 stovePos = new GridPoint2(5,4);
    Entity stove = StationFactory.createStove();
    spawnEntityAt(stove, stovePos, false, false);
    stove.setPosition(stove.getPosition().x + 2.7f , stove.getPosition().y + 1.3f);

    //Spawn a flame, this is temporary and for testing purposes
    GridPoint2 flamePos = new GridPoint2(1,1);
    Entity flame = StationFactory.createFlame();
    spawnEntityAt(flame, flamePos, false, false);

    GridPoint2 fireExtinguisherPos = new GridPoint2(3, 4);
    Entity fireExtinguisher = StationFactory.createFireExtinguisher();
    spawnEntityAt(fireExtinguisher, fireExtinguisherPos, false, false);
  }

    /**
     * spawn a bench
     * @param type: bench filename
     * @param x: x coordinate
     * @param y: y coordinate
     *         note: coordinates begin at bottom left of screen
     */
  private void spawnBench(String type, int x, int y) {
      GridPoint2 coords = new GridPoint2(x,y);
      Entity bench = Bench.createBench(type);
      spawnEntityAt(bench, coords, true, true);
      Vector2 pos = bench.getPosition();

      // this is very scuffed but it aligns.
      bench.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));
  }
  /**
   * Render and spawn all benches.
   */
  private void spawnBenches() {
      List<Bench> benches = new ArrayList<Bench>();
      benches.add(new Bench("bench3-5", 98, 224));
      benches.add(new Bench("bench7", 98, 25));
      benches.add(new Bench("bench2", 96, 72));
      benches.add(new Bench("bench6-bottom", 343,27));
      benches.add(new Bench("bench6-top", 343,131));
      benches.add(new Bench("bench4", 217, 160));
      benches.add(new Bench("bench1", 217, 26));

      for (int i = 0; i < benches.size(); i++) {
          Bench bench = benches.get(i);
          spawnBench(bench.type, bench.x, bench.y);
      }
  }

  private Entity spawnPlayer() {
    Entity newPlayer;
    PlayerConfig playerConfig = new PlayerConfig();
    newPlayer = PlayerFactory.createPlayer(playerConfig);
    spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
    return newPlayer;
  }

  /**
   * Spawn a fish item.
   * @param cookedLevel - The level the fish is cooked at, can be "raw", "cooked" or "burnt".
   * @return A fish entity.
   */
  private Entity spawnFish(String cookedLevel) {
    Entity newFish = ItemFactory.createFish(cookedLevel);
    spawnEntityAt(newFish, new GridPoint2(15, 15), true, true);
    return newFish;
  }

  /**
   * Spawn a beef item.
   * @param cookedLevel - The level the beef is cooked at, can be "raw", "cooked" or "burnt".
   * @return A beef entity.
   */
  private Entity spawnBeef(String cookedLevel) {
    Entity newBeef = ItemFactory.createBeef(cookedLevel);
    spawnEntityAt(newBeef, new GridPoint2(3, 3), true, true);
    newBeef.setScale(0.5f,0.5f);
    return newBeef;
  }

  /**
   * Spawn a banana item.
   * @param choppedLevel - The level the banana is chopped at, can be "raw" or "chopped".
   * @return A banana entity.
   */
  private Entity spawnBanana(String choppedLevel) {
    Entity newBanana = ItemFactory.createBanana(choppedLevel);
    spawnEntityAt(newBanana, new GridPoint2(3, 3), true, true);
    newBanana.setScale(0.5f,0.5f);
    return newBanana;
  }

  /**
   * Spawn a tomato item.
   * @param choppedLevel - The level the tomato is chopped at, can be "raw" or "chopped".
   * @return A tomato entity.
   */
  private Entity spawnTomato(String choppedLevel) {
    Entity newTomato = ItemFactory.createTomato(choppedLevel);
    spawnEntityAt(newTomato, new GridPoint2(8, 8), true, true);
    newTomato.setScale(0.5f,0.5f);
    return newTomato;
  }

  /**
   * Spawn a cucumber item.
   * @param choppedLevel - The level the cucumber is chopped at, can be "raw" or "chopped".
   * @return A cucumber entity.
   */
  private Entity spawnCucumber(String choppedLevel) {
    Entity newCucumber = ItemFactory.createCucumber(choppedLevel);
    spawnEntityAt(newCucumber, new GridPoint2(9, 9), true, true);
    newCucumber.setScale(0.5f,0.5f);
    return newCucumber;
  }

  /**
   * Spawn a strawberry item.
   * @param choppedLevel - The level the strawberry is chopped at, can be "raw" or "chopped".
   * @return A strawberry entity.
   */
  private Entity spawnStrawberry(String choppedLevel) {
    Entity newStrawberry = ItemFactory.createStrawberry(choppedLevel);
    spawnEntityAt(newStrawberry, new GridPoint2(5, 5), true, true);
    newStrawberry.setScale(0.5f,0.5f);
    return newStrawberry;
  }

  /**
   * Spawn a lettuce item.
   * @param choppedLevel - The level the lettuce is chopped at, can be "raw" or "chopped".
   * @return A lettuce entity.
   */
  private Entity spawnLettuce(String choppedLevel) {
    Entity newLettuce = ItemFactory.createLettuce(choppedLevel);
    spawnEntityAt(newLettuce, new GridPoint2(4, 4), true, true);
    newLettuce.setScale(0.5f,0.5f);
    return newLettuce;
  }

  /**
   * Spawn a chocolate item.
   * @param choppedLevel - The level the chocolate is chopped at, can be "raw" or "chopped".
   * @return A chocolate entity.
   */
  private Entity spawnChocolate(String choppedLevel) {
    Entity newChocolate = ItemFactory.createChocolate(choppedLevel);
    spawnEntityAt(newChocolate, new GridPoint2(4, 8), true, true);
    newChocolate.setScale(0.5f,0.5f);
    return newChocolate;
  }

  /**
   * Spawn an Açaí item.
   * @param choppedLevel - The level the Açaí is chopped at, can be "raw" or "chopped".
   * @return A chocolate entity.
   */
  private Entity spawnAcai(String choppedLevel) {
    Entity newAcai = ItemFactory.createAcai(choppedLevel);
    spawnEntityAt(newAcai, new GridPoint2(4, 8), true, true);
    newAcai.setScale(0.65f,0.65f);
    return newAcai;
  }

  /**
   * Spawn a FruitSalad item.
   * @return A FruitSalad entity.
   */
  private Entity spawnFruitSalad() {
    Entity newFruitSalad = ItemFactory.createFruitSalad();
    spawnEntityAt(newFruitSalad, new GridPoint2(3, 3), true, true);
    newFruitSalad.setScale(0.5f,0.5f);
    return newFruitSalad;
  }

  private Entity spawnCustomerController() {
    Entity spawnController = new Entity();
    spawnController.getEvents().addListener(personalCustomerEnums.HANK.name(), this::spawnHank);
    spawnController.getEvents().addListener(personalCustomerEnums.LEWIS.name(), this::spawnLewis);
    spawnController.getEvents().addListener(personalCustomerEnums.SILVER.name(), this::spawnSilver);
    spawnController.getEvents().addListener(personalCustomerEnums.JOHN.name(), this::spawnJohn);
    spawnController.getEvents().addListener(personalCustomerEnums.MOONKI.name(), this::spawnMoonki);
    spawnController.getEvents().addListener(personalCustomerEnums.BASIC_SHEEP.name(), this::spawnBasicSheep);
    spawnController.getEvents().addListener(personalCustomerEnums.BASIC_CHICKEN.name(), this::spawnBasicChicken);
    return spawnController;
  }

  private void spawnCustomer(String name) {
        GridPoint2 position = new GridPoint2(1, 5);
        Vector2 targetPos = new Vector2(3, 5);
        Entity customer = NPCFactory.createCustomerPersonal(name, targetPos);
        spawnEntityAt(customer, position, true, true);
  }

  private void spawnBasicCustomer(String name) {
    GridPoint2 position = new GridPoint2(1, 5);
    Vector2 targetPos = new Vector2(3, 5);
    Entity customer = NPCFactory.createBasicCustomer(name, targetPos);
    spawnEntityAt(customer, position, true, true);
  }

  private void spawnHank() {
    spawnCustomer("Hank");
  }
  private void spawnLewis() {
    spawnCustomer("Lewis");
  }
  private void spawnSilver() {
    spawnCustomer("Silver");
  }
  private void spawnJohn() {
    spawnCustomer("John");
  }
  private void spawnMoonki() {
    spawnCustomer("Moonki");
  }
  private void spawnBasicChicken() {
    spawnBasicCustomer("Basic Chicken");
  }
  private void spawnBasicSheep() {
    spawnBasicCustomer("Basic Sheep");
  }

  /**
   * Spawn an AcaiBowl item.
   * @return An AcaiBowl entity.
   */
  private Entity spawnAcaiBowl() {
    Entity newAcaiBowl = ItemFactory.createAcaiBowl();
    spawnEntityAt(newAcaiBowl, new GridPoint2(16, 10), true, true);
    newAcaiBowl.setScale(0.65f,0.65f);
    return newAcaiBowl;
  }

  /**
   * Spawn a Salad item.
   * @return A Salad entity.
   */
  private Entity spawnSalad() {
    Entity newSalad = ItemFactory.createSalad();
    spawnEntityAt(newSalad, new GridPoint2(13, 10), true, true);
    newSalad.setScale(0.5f,0.5f);
    return newSalad;
  }

  /**
   * Spawn a SteakMeal item.
   * @return A SteakMeal entity.
   */
  private Entity spawnSteakMeal() {
    Entity newSteakMeal = ItemFactory.createSteakMeal();
    spawnEntityAt(newSteakMeal, new GridPoint2(10, 9), true, true);
    newSteakMeal.setScale(0.5f,0.5f);
    return newSteakMeal;
  }

  /**
   * Spawn a BananaSplit item.
   * @return A BananaSplit entity.
   */
  private Entity spawnBananaSplit() {
    Entity newBananaSplit = ItemFactory.createBananaSplit();
    spawnEntityAt(newBananaSplit, new GridPoint2(14, 12), true, true);
    newBananaSplit.setScale(0.5f,0.5f);
    return newBananaSplit;
  }

//  private void spawnGhosts() {
//    GridPoint2 minPos = new GridPoint2(0, 0);
//    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);
//
//    for (int i = 0; i < NUM_GHOSTS; i++) {
//      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
//      Entity ghost = NPCFactory.createGhost(player);
//      spawnEntityAt(ghost, randomPos, true, true);
//    }
//  }
//
//  private void spawnGhostKing() {
//    GridPoint2 minPos = new GridPoint2(0, 0);
//    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);
//
//    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
//    Entity ghostKing = NPCFactory.createGhostKing(player);
//    spawnEntityAt(ghostKing, randomPos, true, true);
//  }

  /**
   * Spawn Stack Plate item.
   * @param quantity - amount of stack.
   * @return A newPlate entity.
   */
  private Entity spawnStackPlate(int quantity) {
    Entity newPlate = PlateFactory.spawnPlateStack(quantity);
    GridPoint2 platePosition = new GridPoint2(6, 1);
    spawnEntityAt(newPlate, platePosition, true, false);
    newPlate.setScale(1.0f, 1.0f);

    return newPlate;
  }

  /**
   * Spawn Stack Plate item but with meals.
   * @return A newPlate entity with meal.
   */
  private Entity spawnPlatewithMeal() {
    Entity newPlate = PlateFactory.spawnMealOnPlate(1,"salad");
    GridPoint2 platePosition = new GridPoint2(6, 4);
    spawnEntityAt(newPlate, platePosition, true, false);
    newPlate.setScale(0.8f, 0.8f);

    return newPlate;
  }


  private void playMusic() {
    Music music = ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class);
    music.setLooping(true);
    music.setVolume(0.3f);
    music.play();
  }

  private void loadAssets() {
    logger.debug("Loading assets");
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.loadTextures(forestTextures);
    resourceService.loadTextureAtlases(forestTextureAtlases);
    resourceService.loadSounds(forestSounds);
    resourceService.loadMusic(forestMusic);

    while (!resourceService.loadForMillis(10)) {
      // This could be upgraded to a loading screen
      logger.info("Loading... {}%", resourceService.getProgress());
    }
  }

  private void unloadAssets() {
    logger.debug("Unloading assets");
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.unloadAssets(forestTextures);
    resourceService.unloadAssets(forestTextureAtlases);
    resourceService.unloadAssets(forestSounds);
    resourceService.unloadAssets(forestMusic);
  }

  @Override
  public void dispose() {
    super.dispose();
    ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).stop();
    this.unloadAssets();
  }

  private void spawnBoss() {
    GridPoint2 position = new GridPoint2(1, 5);
    Vector2 targetPos = new Vector2(2, 6); // Target position for ghost king
    Entity boss = NPCFactory.createBoss(targetPos);
    spawnEntityAt(boss, position, false, false);
  }

  private void triggerFiredEnd() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
      try {
        Thread.sleep(10000);
        spawnBoss();
        createTextBox("You *oink* two-legged moron! You're ruining my business' *oink* reputation!. Get out!");

      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Thread was interrupted");
      }
    });

    // Shutdown the executor to prevent zombie threads
    executor.shutdown();
  }

  private void createTextBox(String text) {
    for (Entity entity: ServiceLocator.getEntityService().getEntities()) {
      entity.getEvents().trigger("SetText", text);
    }
  }

  private void triggerGoodEnd() {
    // pain
  }
}
