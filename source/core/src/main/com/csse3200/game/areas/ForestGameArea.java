package com.csse3200.game.areas;

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
import com.csse3200.game.entities.factories.ObstacleFactory;
import com.csse3200.game.entities.factories.PlayerFactory;
import com.csse3200.game.entities.factories.StationFactory;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.utils.math.GridPoint2Utils;
import com.csse3200.game.utils.math.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Forest area for the demo game with trees, a player, and some enemies. */
public class ForestGameArea extends GameArea {
  private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
  private static final int NUM_TREES = 7;
  private static final int NUM_GHOSTS = 2;
  private static final int NUM_CUSTOMERS_BASE = 1;
  private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(3, 3);
  private static final float WALL_WIDTH = 0.1f;
  private static final String[] forestTextures = {
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
          "images/tiles/bench_test.png",
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
          "images/tooltip_bg.png",
    "images/stations/benches/bench6-bottom.png",
    "images/stations/benches/bench6-top.png",
    "images/fireExtinguisher/Fire_Extinguisher.png",
          "images/stations/benches/bench_test.png",
          "images/stations/benches/middle.png",
          "images/stations/benches/left_border.png",
            "images/stations/benches/right_border.png",
            "images/stations/benches/vertical.png",
            "images/stations/benches/top_fin.png",
          "images/stations/benches/bottom_fin.png",
          "images/stations/benches/bottom_shadow_final.png",
            "images/stations/benches/shadow_bottom_top.png",
            "images/stations/benches/top_top_final.png",
            "images/stations/benches/left_shadow.png",
            "images/stations/benches/right_shadow.png",
            "images/stations/benches/top_left_corner_final.png",
            "images/stations/benches/top_left_corner.png",
    "images/stations/benches/bench6-top.png",
          "images/frame/vertical_border.png",
          "images/frame/horizontal_border.png",
          "images/frame/topleft_door.png",
          "images/frame/topright_door.png",
          "images/frame/bottomleft_door.png",
          "images/frame/bottomright_door.png",
          "images/frame/wall.png"
  };
  private static final String[] forestTextureAtlases = {"images/terrain_iso_grass.atlas", "images/ghost.atlas", "images/ghostKing.atlas", "images/player.atlas", "images/fireExtinguisher/atlas/flame.atlas"};
  private static final String[] forestSounds = {"sounds/Impact4.ogg"};
  private static final String backgroundMusic = "sounds/BGM_03_mp3.mp3";
  private static final String[] forestMusic = {backgroundMusic};

  private final TerrainFactory terrainFactory;

  private Entity player;

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
    spawnBenches();
    make_border();

    spawnStations();
    // Spawn beef
    spawnBeef("cooked");
    spawnStrawberry("chopped");
   spawnLettuce("chopped");
    spawnCustomer();

    // Spawn the player
    player = spawnPlayer();

    //playMusic();
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
    GridPoint2 coords;
    Vector2 pos;
    Entity top_left_door = ObstacleFactory.Door("topleft_door");
    coords = new GridPoint2(0,264);
    spawnEntityAt(top_left_door, coords, true, true);
    pos = top_left_door.getPosition();
    top_left_door.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));

    Entity top_right_door = ObstacleFactory.Door("topright_door");
    coords = new GridPoint2(48,264);
    spawnEntityAt(top_right_door, coords, true, true);
    pos = top_right_door.getPosition();
    top_right_door.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));

    Entity bottom_left_door = ObstacleFactory.Door("bottomleft_door");
    coords = new GridPoint2(0,1);
    spawnEntityAt(bottom_left_door, coords, true, true);
    pos = bottom_left_door.getPosition();
    bottom_left_door.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));

    Entity bottom_right_door = ObstacleFactory.Door("bottomright_door");
    coords = new GridPoint2(48,1);
    spawnEntityAt(bottom_right_door, coords, true, true);
    pos = bottom_right_door.getPosition();
    bottom_right_door.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));
  }

  /**
   * Spawns the wall around the restaurant
   */
  private void spawnWall() {
    GridPoint2 coords;
    Vector2 pos;
    Entity top_wall = ObstacleFactory.wall();
    coords = new GridPoint2(99,264);
    spawnEntityAt(top_wall, coords, true, true);
    pos = top_wall.getPosition();
    top_wall.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));

    Entity bottom_wall = ObstacleFactory.wall();
    coords = new GridPoint2(99,1);
    spawnEntityAt(bottom_wall, coords, true, true);
    pos = bottom_wall.getPosition();
    bottom_wall.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));
  }

  /**
   * Renders a black border around the restaurant
   */
  private void make_border(){
    GridPoint2 coords = new GridPoint2(0,0);
    Vector2 pos;

    //top border
    Entity top_border = ObstacleFactory.horizontalSeparation();
    coords = new GridPoint2(2,286);
    spawnEntityAt(top_border, coords, true, true);
    pos = top_border.getPosition();
    top_border.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));

    Entity top_down_border = ObstacleFactory.horizontalSeparation();
    coords = new GridPoint2(2,263);
    spawnEntityAt(top_down_border, coords, true, true);
    pos = top_down_border.getPosition();
    top_down_border.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));

    //bottom border
    Entity bottom_border = ObstacleFactory.horizontalSeparation();
    coords = new GridPoint2(2,-1);
    spawnEntityAt(bottom_border, coords, true, true);
    pos = bottom_border.getPosition();
    bottom_border.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));

    Entity bottom_up_border = ObstacleFactory.horizontalSeparation();
    coords = new GridPoint2(2,23);
    spawnEntityAt(bottom_up_border, coords, true, true);
    pos = bottom_up_border.getPosition();
    bottom_up_border.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));

    //left border
    Entity left_border = ObstacleFactory.verticalSeparation();
    coords = new GridPoint2(0,3);
    spawnEntityAt(left_border, coords, true, true);
    pos = left_border.getPosition();
    left_border.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));

    //right border
    Entity right_border = ObstacleFactory.verticalSeparation();
    coords = new GridPoint2(385,3);
    spawnEntityAt(right_border, coords, true, true);
    pos = right_border.getPosition();
    right_border.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));

    //separation border
    Entity sep_border = ObstacleFactory.verticalSeparation();
    coords = new GridPoint2(96,3);
    spawnEntityAt(sep_border, coords, true, true);
    pos = sep_border.getPosition();
    sep_border.setPosition((pos.x / (24 * (terrain.getTileSize()))) + 0.02f, pos.y / (24 * (terrain.getTileSize())));
  }

  private void spawnStations() {
    GridPoint2 ovenPos = new GridPoint2(5,4);
    Entity oven = StationFactory.createOven();
    spawnEntityAt(oven, ovenPos, true, false);
    oven.setPosition(oven.getPosition().x , oven.getPosition().y + 0.9f);

    GridPoint2 stovePos = new GridPoint2(5,4);
    Entity stove = StationFactory.createStove();
    spawnEntityAt(stove, stovePos, false, false);
    stove.setPosition(stove.getPosition().x + 2.7f , stove.getPosition().y + 0.9f);

    //Spawn a flame, this is temporary and for testing purposes
    GridPoint2 flamePos = new GridPoint2(1,1);
    Entity flame = StationFactory.createFlame();
    spawnEntityAt(flame, flamePos, false, false);

    GridPoint2 fireExtinguisherPos = new GridPoint2(3, 5);
    Entity fireExtinguisher = StationFactory.createFireExtinguisher();
    spawnEntityAt(fireExtinguisher, fireExtinguisherPos, false, false);

  }


  /**
   * Render and spawn all benches.
   */
  private void spawnBenches() {
    //bottom
    Entity bench1 = Bench.createBench("left_border");
    spawnEntityAt(bench1, new GridPoint2(4,1), false, false);
    bench1.setPosition(4, 1f);
    for (int i = 5; i < 15; i++) {
      Entity bench = Bench.createBench("middle");
      spawnEntityAt(bench, new GridPoint2(i, 1), false, false);
      bench.setPosition(i, 1f);
    }
    Entity bench2 = Bench.createBench("right_border");
    spawnEntityAt(bench2, new GridPoint2(15,1), false, false);
    bench2.setPosition(15, 1f);
    // extra benches
    Entity bench_extra = Bench.createBench("left_border");
    spawnEntityAt(bench_extra, new GridPoint2( 11,3), false, false);
    bench_extra.setPosition(11, 3f);
    for (int i = 12; i < 14; i++) {
      Entity bench = Bench.createBench("middle");
      spawnEntityAt(bench, new GridPoint2(i, 3), false, false);
      bench.setPosition(i, 3f);
    }
    Entity bench_extra_2 = Bench.createBench("right_border");
    spawnEntityAt(bench_extra_2, new GridPoint2( 13,3), false, false);
    bench_extra_2.setPosition(13, 3f);
    for (int i = 4; i < 8; i++) {
      Entity bench = Bench.createBench("vertical");
      spawnEntityAt(bench, new GridPoint2(13, i), false, false);
      bench.setPosition(13f, i);
    }

    Entity bench_extra_3 = Bench.createBench("middle");
    spawnEntityAt(bench_extra_3, new GridPoint2( 11,8), false, false);
    bench_extra_3.setPosition(11, 7f);

    Entity bench_extra_7 = Bench.createBench("top_fin");
    spawnEntityAt(bench_extra_7, new GridPoint2( 11,4), false, false);
    bench_extra_7.setPosition(11f, 4f);
    for (int i = 12; i < 13; i++) {
      Entity bench = Bench.createBench("middle");
      spawnEntityAt(bench, new GridPoint2(i, 7), false, false);
      bench.setPosition(i, 7f);
    }

    Entity bench_extra_4 = Bench.createBench("top_fin");
    spawnEntityAt(bench_extra_4, new GridPoint2( 13,7), false, false);
    bench_extra_4.setPosition(13, 7f);

    //right
    for (int i = 2; i < 10; i++) {
      Entity bench = Bench.createBench("vertical");
      spawnEntityAt(bench, new GridPoint2(15, i), false, false);
      bench.setPosition(15f, i);
    }
    Entity bench4 = Bench.createBench("top_fin");
    spawnEntityAt(bench4, new GridPoint2(15,10), false, false);
    bench4.setPosition(15f, 10);

    //second row of top for shadows
    for (int i = 5; i < 15; i++) {
      Entity bench = Bench.createBench("shadow_bottom_top");
      spawnEntityAt(bench, new GridPoint2(i, 10), false, false);
      bench.setPosition(i, 10f);
    }
    Entity bench5 = Bench.createBench("left_shadow");
    spawnEntityAt(bench5, new GridPoint2(4,10), false, false);
    bench5.setPosition(4, 10f);

    for (int i = 4; i < 7; i++) {
      Entity bench = Bench.createBench("vertical");
      spawnEntityAt(bench, new GridPoint2(4, i), false, false);
      bench.setPosition(4f, i);
    }
    Entity bench7 = Bench.createBench("bottom_shadow_final");
    spawnEntityAt(bench7, new GridPoint2(4,3), false, false);
    bench7.setPosition(4f, 3);
    Entity bench_block = Bench.createBench("left_border");
    spawnEntityAt(bench_block, new GridPoint2(9,8), false, false);
    bench_block.setPosition(10f,8f);
    Entity bench8 = Bench.createBench("top_fin");
    spawnEntityAt(bench8, new GridPoint2(4,7), false, false);
    bench8.setPosition(4f, 7);

    //middle 1
    for (int i = 2; i < 5; i++) {
      Entity bench = Bench.createBench("vertical");
      spawnEntityAt(bench, new GridPoint2(9, i), false, false);
      bench.setPosition(9f, i);
    }
    for (int i = 4; i < 5; i++) {
      Entity bench = Bench.createBench("vertical");
      spawnEntityAt(bench, new GridPoint2(6, i), false, false);
      bench.setPosition(6f, i);
    }
    Entity bench_bottom = Bench.createBench("bottom_fin");
    spawnEntityAt(bench_bottom, new GridPoint2(6,2), false, false);
    bench_bottom.setPosition(6f, 3f);
    Entity bench9 = Bench.createBench("top_fin");
    spawnEntityAt(bench9, new GridPoint2(9,4), false, false);
    bench9.setPosition(9f, 5);
    //middle 2
    for (int i = 7; i < 9; i++) {
      Entity bench = Bench.createBench("vertical");
      spawnEntityAt(bench, new GridPoint2(9, i), false, false);
      bench.setPosition(9f, i);
    }

    Entity bench12 = Bench.createBench("vertical");
    spawnEntityAt(bench12, new GridPoint2(9,9), false, false);
    bench12.setPosition(9f, 9);
    Entity bench11 = Bench.createBench("bench_test");
    spawnEntityAt(bench11, new GridPoint2(9,10), false, false);
    bench11.setPosition(9f, 10);
    Entity bench10 = Bench.createBench("bottom_shadow_final");
    spawnEntityAt(bench10, new GridPoint2(9,7), false, false);
    bench10.setPosition(9f, 7f);
    Entity bench20 = Bench.createBench("vertical");
    spawnEntityAt(bench20, new GridPoint2(6,7), true, true);
    bench20.setPosition(6f, 8f);
    Entity bench21 = Bench.createBench("bench_test");
    spawnEntityAt(bench21, new GridPoint2(9,10), false, false);
    bench21.setPosition(6f, 10f);
    Entity bench13 = Bench.createBench("vertical");
    spawnEntityAt(bench13, new GridPoint2(6,7), false, false);
    bench13.setPosition(6f, 9f);
    Entity bench14 = Bench.createBench("middle");
    spawnEntityAt(bench14, new GridPoint2(7,5), true, true);
    bench14.setPosition(7f, 5f);
    Entity bench19 = Bench.createBench("middle");
    spawnEntityAt(bench19, new GridPoint2(7,5), true, true);
    bench19.setPosition(8f, 5f);
    Entity bench15 = Bench.createBench("bottom_shadow_final");
    spawnEntityAt(bench15, new GridPoint2(6,6), false, false);
    bench15.setPosition(6f, 7f);
    Entity bench16 = Bench.createBench("left_border");
    spawnEntityAt(bench16, new GridPoint2(6,5), true, true);
    bench16.setPosition(6f, 5f);

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
    spawnEntityAt(newBeef, new GridPoint2(4, 4), true, true);
    newBeef.setScale(0.5f,0.5f);
    newBeef.setPosition(9 + 0.2f, 8);
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
    spawnEntityAt(newStrawberry, new GridPoint2(6, 4), true, false);
    newStrawberry.setScale(0.5f,0.5f);
    newStrawberry.setPosition(7 + 0.2f, 4);

    return newStrawberry;
  }

  /**
   * Spawn a lettuce item.
   * @param choppedLevel - The level the lettuce is chopped at, can be "raw" or "chopped".
   * @return A lettuce entity.
   */
  private Entity spawnLettuce(String choppedLevel) {
    Entity newLettuce = ItemFactory.createLettuce(choppedLevel);
    spawnEntityAt(newLettuce, new GridPoint2(5, 4), true, true);
    newLettuce.setScale(0.5f,0.5f);
    newLettuce.setPosition(9 + 0.2f, 9);
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

  private void spawnCustomer() {
    GridPoint2 position = new GridPoint2(1, 5);
    //System.out.println("1");

    //System.out.println("2");
    Vector2 targetPos3 = new Vector2(3, 5); // Target position for ghost king
    Entity customer = NPCFactory.createGhostKing(player, targetPos3);
    spawnEntityAt(customer, position, true, true);
//    for (int i = 0; i < NUM_CUSTOMERS_BASE; i++) {
//      customer = NPCFactory.createCustomer();
//      spawnEntityAt(customer, position, true, true);
//      System.out.println("Customer spawned");
//    }

    //System.out.println("3");
  }

//    private void spawnCustomerPersonal() {
//        GridPoint2 position = new GridPoint2(1, 5);
//       //System.out.println("1");
//        Entity customer = NPCFactory.createCustomer(targetPosition);
//        //System.out.println("2");
//        spawnEntityAt(customer, position, true, true);
//        //System.out.println("3");
//    }


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
}
