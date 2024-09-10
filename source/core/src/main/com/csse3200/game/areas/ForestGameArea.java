package com.csse3200.game.areas;


import com.csse3200.game.components.npc.PersonalCustomerEnums;
import com.badlogic.gdx.utils.Null;
import com.csse3200.game.GdxGame;
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
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.utils.math.GridPoint2Utils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import java.util.ArrayList;
import java.util.List;
//import java.util.concurrent.TimeUnit;

import static com.badlogic.gdx.Gdx.app;




/** Forest area for the demo game with trees, a player, and some enemies. */
public class ForestGameArea extends GameArea {
  private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
  private static final int NUM_TREES = 7;
  private static final int NUM_GHOSTS = 2;
  private static final int NUM_CUSTOMERS_BASE = 1;
  private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(5, 4);
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
    "images/tiles/bench_test.png",
    "images/tiles/blue_tile.png",
    "images/stations/oven.png",
    "images/stations/stove.png",
          "images/stations/apple_tree.png",
          "images/stations/bench_middle.png",
          "images/stations/bench_legs.png",
          "images/stations/bench_top.png",
    "images/stations/bench.png",
    "images/stations/servery.png",
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
    "images/fireExtinguisher/Fire_Extinguisher.png",
    "images/stations/benches/single.png",
    "images/stations/benches/middle.png",
    "images/stations/benches/left_border.png",
    "images/stations/benches/right_border.png",
    "images/stations/benches/vertical.png",
    "images/stations/benches/top.png",
    "images/stations/benches/final.png",
    "images/stations/benches/bottom_shadow.png",
    "images/stations/benches/shadow_bottom_top.png",
    "images/stations/benches/left_shadow.png",
    "images/stations/benches/left_corner_shadow.png",
    "images/stations/benches/right_corner_shadow.png",
    "images/stations/benches/top_shadows.png",
    "images/frame/vertical_border.png",
    "images/frame/horizontal_border.png",
    "images/frame/topleft_door.png",
    "images/frame/topright_door.png",
    "images/frame/bottomleft_door.png",
    "images/frame/bottomright_door.png",
    "images/frame/wall.png"
  };
  private static final String[] forestTextureAtlases = {
    "images/terrain_iso_grass.atlas", "images/ghost.atlas", "images/ghostKing.atlas", "images/animal_images/gorilla.atlas",
          "images/animal_images/goose.atlas", "images/animal_images/goat.atlas", "images/animal_images/monkey.atlas",
          "images/animal_images/snow_wolf.atlas","images/player.atlas", "images/fireExtinguisher/atlas/flame.atlas",
          "images/special_NPCs/boss.atlas"
  };
  private static final String[] forestSounds = {"sounds/Impact4.ogg"};
  private static final String backgroundMusic = "sounds/BB_BGM.mp3";
  private static final String[] forestMusic = {backgroundMusic};
  private static Entity customerSpawnController;

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
    //this.textDisplay = textDisplay;
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
    customerSpawnController = spawnCustomerController();
    // Spawn the player
    player = spawnPlayer();
    //ServiceLocator.getEntityService().getEvents().trigger("SetText", "Boss: Rent is due");
    //triggerFiredEnd();    // Trigger the fired (bad) ending
    //triggerRaiseEnd();    // Trigger the raise (good) ending


    playMusic();
  }

  public Entity getCustomerSpawnController() {
    return customerSpawnController;
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
    stove.setPosition(stove.getPosition().x + 2.7f , stove.getPosition().y + 1.3f);

    GridPoint2 appleTreePos = new GridPoint2( 5, 4);
    Entity appleTree = StationFactory.createAppleTree();
    spawnEntityAt(appleTree, appleTreePos, false, false);
    appleTree.setPosition(appleTree.getPosition().x + 4.2f , appleTree.getPosition().y - 1.3f);

    GridPoint2 serveryPos = new GridPoint2(3,0);
    Entity servery = StationFactory.createSubmissionWindow();
    spawnEntityAt(servery, serveryPos, false, false);
    servery.setPosition(servery.getPosition().x, servery.getPosition().y + 1.3f);

    // Bench
    GridPoint2 middlePos = new GridPoint2(5,4);
    Entity middle = StationFactory.createMainBenchTable();
    spawnEntityAt(middle, middlePos, false, false);
    middle.setPosition(middle.getPosition().x - 2.6f, middle.getPosition().y - 1.3f);

    GridPoint2 topPos = new GridPoint2(5,4);
    Entity top = StationFactory.createTopBenchTable();
    spawnEntityAt(top, topPos, false, false);
    top.setPosition(top.getPosition().x - 2.6f, top.getPosition().y);

    GridPoint2 bottomPos = new GridPoint2(5,4);
    Entity bottom = StationFactory.createFeetBenchTable();
    spawnEntityAt(bottom, bottomPos, false, false);
    bottom.setPosition(bottom.getPosition().x - 2.6f, bottom.getPosition().y - 2.6f);
  }

    /**
     * spawn a bench
     * @param type: bench filename
     * @param x: x coordinate
     * @param y: y coordinate
     *         note: coordinates begin at bottom left of screen
     */
  private void spawnBench(String type, int x, int y) {
    //Spawn a flame, this is temporary and for testing purposes
    GridPoint2 flamePos = new GridPoint2(1,1);
    Entity flame = StationFactory.createFlame();
    spawnEntityAt(flame, flamePos, false, false);

    GridPoint2 fireExtinguisherPos = new GridPoint2(3, 4);
    Entity fireExtinguisher = StationFactory.createFireExtinguisher();
    spawnEntityAt(fireExtinguisher, fireExtinguisherPos, false, false);
  }


  /**
   * @param type - The type of bench to spawn.
   * @param startX - The x-coordinate to start spawning the bench.
   * @param endX - The x-coordinate to end spawning the bench.
   * @param y - The y-coordinate to spawn the bench.
   * Spawns a row of benches of the specified type.
   */
  private void spawnBenchRow(String type, int startX, int endX, float y) {
    for (int i = startX; i <= endX; i++) {
      Entity bench = Bench.createBench(type);
      spawnEntityAt(bench, new GridPoint2(i, (int) y), false, false);
      bench.setPosition(i, y);
    }
  }

  /**
   * @param type - The type of bench to spawn.
   * @param x - The x-coordinate to spawn the bench.
   * @param startY - The y-coordinate to start spawning the bench.
   * @param endY - The y-coordinate to end spawning the bench.
   * Spawns a column of benches of the specified type.
   */
  private void spawnBenchColumn(String type, float x, int startY, int endY) {
    for (int i = startY; i <= endY; i++) {
      Entity bench = Bench.createBench(type);
      spawnEntityAt(bench, new GridPoint2((int) x, i), false, false);
      bench.setPosition(x, i);
    }
  }
  /**
   * @param type - The type of bench to spawn.
   * @param x - The x-coordinate to spawn the bench.
   * @param y - The y-coordinate to spawn the bench.
   * Spawns a single bench tile of the specified type.
   */
  private void spawnSingleBench(String type, float x, float y) {
    Entity bench = Bench.createBench(type);
    spawnEntityAt(bench, new GridPoint2((int) x, (int) y), false, false);
    bench.setPosition(x, y);
  }
  /**
   * Spawns benches around the restaurant
   */
  private void spawnBenches() {
      List<Bench> benches = new ArrayList<Bench>();
      benches.add(new Bench("bench3-5", 98, 224));
      benches.add(new Bench("bench7", 98, 25));
      benches.add(new Bench("bench2", 96, 72));
      //benches.add(new Bench("bench6-bottom", 343,27));
      //benches.add(new Bench("bench6-top", 343,131));
      //benches.add(new Bench("bench4", 217, 160));
      //benches.add(new Bench("bench1", 217, 26));

      for (int i = 0; i < benches.size(); i++) {
          Bench bench = benches.get(i);
          spawnBench(bench.type, bench.x, bench.y);
      }
    // Bottom bench row
    spawnSingleBench("left_border", 4, 1f);
    spawnBenchRow("middle", 5, 14, 1f);
    spawnSingleBench("right_border", 15, 1f);

    // Top shadow bench row
    spawnBenchRow("shadow_bottom_top", 5, 14, 10f);
    spawnSingleBench("left_shadow", 4, 10f);

    // Middle vertical benches (long bench setup)
    spawnSingleBench("single", 9f, 10f); // Middle part of long bench
    spawnBenchColumn("vertical", 9f, 7, 9);  // Middle vertical section
    spawnSingleBench("left_corner_shadow", 9f, 7f); // Bottom-left corner shadow

    // Top horizontal shadows near middle
    spawnBenchRow("top_shadows", 10, 12, 7f);

    // Long bench bottom part (left shadow + right shadow)
    spawnSingleBench("left_corner_shadow", 11, 3f);
    spawnSingleBench("top_shadows", 12, 3f);
    spawnSingleBench("right_corner_shadow", 13, 3f);

    // Right side of long bench (vertical and top fin)
    spawnSingleBench("top", 13f, 7f);
    spawnBenchColumn("vertical", 13f, 4, 6);

    // Left side of long bench (final vertical + top fin)
    spawnSingleBench("vertical", 11f, 4f); // Left vertical part of the long bench
    spawnSingleBench("top", 11f, 5f); // Top left fin for long bench

    // Right vertical bench column
    spawnBenchColumn("vertical", 15f, 2, 9);
    spawnSingleBench("top", 15f, 10f);

    // Left vertical bench column
    spawnBenchColumn("vertical", 4f, 4, 6);
    spawnSingleBench("bottom_shadow", 4f, 3f);
    spawnSingleBench("top", 4f, 7f);

    // Middle long bench (vertical section)
    spawnBenchColumn("vertical", 9f, 2, 4);
    spawnSingleBench("final", 7f, 3f);
    spawnSingleBench("top", 9f, 5f);

    // Additional benches near middle area
    spawnSingleBench("middle", 7f, 5f);
    spawnSingleBench("middle", 8f, 5f);
    spawnSingleBench("left_border", 6f, 5f);

    // Top left section
    spawnSingleBench("bottom_shadow", 6f, 7f);
    spawnBenchColumn("vertical", 6f, 8, 9);
    spawnSingleBench("single", 6f, 10f);
  }

  private Entity spawnPlayer() {
    Entity newPlayer;
    PlayerConfig playerConfig = new PlayerConfig();
    newPlayer = PlayerFactory.createPlayer();
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
    newBeef.setPosition(9.2f, 8);
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
    spawnEntityAt(newStrawberry, new GridPoint2(6, 5), false, true);
    newStrawberry.setScale(0.5f,0.5f);
    newStrawberry.setPosition(7.3f, 5f);

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

  private Entity spawnCustomerController() {
    Entity spawnController = new Entity();
    spawnController.getEvents().addListener(PersonalCustomerEnums.HANK.name(), this::spawnHank);
    spawnController.getEvents().addListener(PersonalCustomerEnums.LEWIS.name(), this::spawnLewis);
    spawnController.getEvents().addListener(PersonalCustomerEnums.SILVER.name(), this::spawnSilver);
    spawnController.getEvents().addListener(PersonalCustomerEnums.JOHN.name(), this::spawnJohn);
    spawnController.getEvents().addListener(PersonalCustomerEnums.MOONKI.name(), this::spawnMoonki);
    spawnController.getEvents().addListener(PersonalCustomerEnums.BASIC_SHEEP.name(), this::spawnBasicSheep);
    spawnController.getEvents().addListener(PersonalCustomerEnums.BASIC_CHICKEN.name(), this::spawnBasicChicken);
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
        createTextBox("You *oink* two-legged moron! You're ruining my " +
                "business' *oink* reputation! Get out!");
        Thread.sleep(20000);
        app.exit();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Thread was interrupted");
      }
    });

    // Shutdown the executor to prevent zombie threads
    executor.shutdown();

  }

  private void triggerRaiseEnd() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
      try {
        Thread.sleep(10000);
        spawnBoss();
        createTextBox("You *oink* amazing critter! You're a master! " +
                "Enjoy a 40c raise for your efforts!");
        Thread.sleep(20000);
        app.exit();
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

}
