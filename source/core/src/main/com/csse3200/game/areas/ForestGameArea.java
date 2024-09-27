package com.csse3200.game.areas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.Gdx.app;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.areas.terrain.TerrainFactory.TerrainType;
import com.csse3200.game.components.gamearea.GameAreaDisplay;
import com.csse3200.game.components.maingame.CheckWinLoseComponent;
import com.csse3200.game.components.maingame.EndDayDisplay;
import com.csse3200.game.components.moral.MoralDecision;
import com.csse3200.game.components.npc.PersonalCustomerEnums;
import com.csse3200.game.components.player.TouchPlayerInputComponent;
import com.csse3200.game.components.upgrades.UpgradesDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.entities.configs.PlayerConfig;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.entities.factories.NPCFactory;
import com.csse3200.game.entities.factories.ObstacleFactory;
import com.csse3200.game.entities.factories.PlateFactory;
import com.csse3200.game.entities.factories.PlayerFactory;
import com.csse3200.game.entities.factories.StationFactory;
import com.csse3200.game.screens.MoralDecisionDisplay;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.utils.math.GridPoint2Utils;





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
    "images/special_NPCs/penguin.png",
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
    "images/stations/bin.png",
    "images/stations/apple_tree.png",
    "images/stations/bench_middle.png",
    "images/stations/bench_legs.png",
    "images/stations/bench_top.png",
    "images/stations/bench.png",
    "images/stations/servery.png",
    "images/stations/refrigerator.png",
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
    "images/frame/wall.png",
          "images/platecomponent/cleanplate.png",
          "images/platecomponent/dirtyplate.png",
          "images/platecomponent/stackplate.png",
          "images/platecomponent/stackedplates/1plates.png",
          "images/platecomponent/stackedplates/2plates.png",
          "images/platecomponent/stackedplates/3plates.png",
          "images/platecomponent/stackedplates/4plates.png",
          "images/platecomponent/stackedplates/5plates.png",
          "images/inventory_ui/slot.png",
          "images/inventory_ui/null_image.png",
  };
  private static final String[] forestTextureAtlases = {
    "images/terrain_iso_grass.atlas",
    "images/ghost.atlas",
    "images/ghostKing.atlas",
    "images/animal_images/gorilla.atlas",
    "images/animal_images/goose.atlas",
    "images/animal_images/goat.atlas",
    "images/animal_images/monkey.atlas",
    "images/animal_images/snow_wolf.atlas",
    "images/player.atlas",
    "images/fireExtinguisher/atlas/flame.atlas",
    "images/stations/oven/oven.atlas",
    "images/terrain_iso_grass.atlas",
    "images/ghost.atlas",
    "images/ghostKing.atlas",
    "images/animal_images/gorilla.atlas",
    "images/animal_images/goose.atlas",
    "images/animal_images/goat.atlas",
    "images/animal_images/monkey.atlas",
    "images/animal_images/snow_wolf.atlas",
    "images/fireExtinguisher/atlas/flame.atlas",
    "images/player/player.atlas",
    "images/player/acaiBowl.atlas",
    "images/player/bananaSplit.atlas",
    "images/player/burntBeef.atlas",
    "images/player/choppedAcai.atlas",
    "images/player/choppedBanana.atlas",
    "images/player/choppedChocolate.atlas",
    "images/player/choppedCucumber.atlas",
    "images/player/choppedLettuce.atlas",
    "images/player/choppedStrawberry.atlas",
    "images/player/choppedTomato.atlas",
    "images/player/cookedBeef.atlas",
    "images/player/cookedFish.atlas",
    "images/player/fruitSalad.atlas",
    "images/player/rawAcai.atlas",
    "images/player/rawBanana.atlas",
    "images/player/rawBeef.atlas",
    "images/player/rawChocolate.atlas",
    "images/player/rawCucumber.atlas",
    "images/player/rawFish.atlas",
    "images/player/rawLettuce.atlas",
    "images/player/rawStrawberry.atlas",
    "images/player/rawTomato.atlas",
    "images/player/salad.atlas",
    "images/player/steak.atlas",
    "images/player/playerPlate.atlas",
    "images/player/playerDirtyPlate.atlas",
          "images/player/playerFireExtinguisher.atlas",
          "images/special_NPCs/boss.atlas", "images/stations/Servery_Animation/servery.atlas",
          "images/special_NPCs/penguin.atlas",
  };
  private static final String[] forestSounds = {"sounds/Impact4.ogg"};
  private static final String backgroundMusic = "sounds/BB_BGM.mp3";
  private static final String[] forestMusic = {backgroundMusic};
  private static Entity customerSpawnController;

  private final TerrainFactory terrainFactory;
  private final UpgradesDisplay upgradesDisplay; 

  private Entity player;
  private CheckWinLoseComponent winLoseComponent;  // Reference to CheckWinLoseComponent


  // Define the win/lose conditions
  private int winAmount = 60;      // Example value for winning gold amount
  private int loseThreshold = 50;   // Example value for losing threshold

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
  public ForestGameArea(TerrainFactory terrainFactory, UpgradesDisplay upgradesDisplay) {
    super();
    this.terrainFactory = terrainFactory;
    this.upgradesDisplay = upgradesDisplay; 
    //this.textDisplay = textDisplay;

    ServiceLocator.registerGameArea(this);
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


    //ticketDetails();


    //spawnBenches();

    spawnStations();
    // Spawn beef
//    spawnBeef("cooked");
//    spawnStrawberry("chopped");
//    spawnLettuce("chopped");
    customerSpawnController = spawnCustomerController();

    //spawnplates
      spawnStackPlate(5); //testplate spawn
      //spawnPlatewithMeal();


    // Spawn the player
    player = spawnPlayer();
    ServiceLocator.getDayNightService().getEvents().addListener("upgrade", () -> {
      spawnPenguin(upgradesDisplay);});
    

    // Check and trigger win/lose state
    ServiceLocator.getDayNightService().getEvents().addListener("endGame", this::checkEndOfDayGameState);

    createMoralScreen();
    createEndDayScreen();
    playMusic();
  }

  /***
   * Checks using the checkWinLoseComponent if to call a cutscene and which one to call
   */
  private void checkEndOfDayGameState() {
    String gameState = player.getComponent(CheckWinLoseComponent.class).checkGameState();

    if ("LOSE".equals(gameState)) {
      createTextBox("You *oink* two-legged moron! You're ruining my " +
              "business' *oink* reputation! Get out!");
      triggerFiredEnd();  // Trigger the fired (bad) ending
    } else if ("WIN".equals(gameState)) {
      createTextBox("You *oink* amazing critter! You're a master! " +
              "Enjoy a 40c raise for your efforts!");
      triggerRaiseEnd();  // Trigger the raise (good) ending
    }
  }


  public Entity getCustomerSpawnController() {
    return customerSpawnController;
  }

  private void displayUI() {
    Entity ui = new Entity();
    ui.addComponent(new GameAreaDisplay("Kitchen"));
    spawnEntity(ui);
  }
  /*
  private void ticketDetails(){
    Entity ticket = new Entity();
    ticket.addComponent(new TicketDetails());
    spawnEntity(ticket);

  }*/

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
    oven.setPosition(oven.getPosition().x + 0.5f, oven.getPosition().y + 2f);

    GridPoint2 stovePos = new GridPoint2(5,4);
    Entity stove = StationFactory.createStove();
    spawnEntityAt(stove, stovePos, false, false);
    stove.setPosition(stove.getPosition().x + 2.7f , stove.getPosition().y + 1.3f);

    GridPoint2 binPos = new GridPoint2(5,4);
    Entity bin = StationFactory.createBin();
    spawnEntityAt(bin, binPos, false, false);
    bin.setPosition(bin.getPosition().x + 6f , bin.getPosition().y - 6f);

    GridPoint2 bananaTreePos = new GridPoint2( 5, 4);
    Entity bananaTree = StationFactory.createBananaTree();
    spawnEntityAt(bananaTree, bananaTreePos, false, false);
    bananaTree.setPosition(bananaTree.getPosition().x + 4.2f , bananaTree.getPosition().y - 4f);

    GridPoint2 strawberryPos = new GridPoint2( 5, 4);
    Entity strawberryStation = StationFactory.createStrawberries();
    spawnEntityAt(strawberryStation, strawberryPos, false, false);
    strawberryStation.setPosition(strawberryStation.getPosition().x + 4.2f , strawberryStation.getPosition().y - 5f);

    GridPoint2 serveryPos = new GridPoint2(1,1);
    Entity servery = StationFactory.createSubmissionWindow();
    spawnEntityAt(servery, serveryPos, false, false);
    servery.setPosition(servery.getPosition().x + 2, servery.getPosition().y + 0.5f);
    servery = StationFactory.createSubmissionWindow();
    spawnEntityAt(servery, serveryPos, false, false);
    servery.setPosition(servery.getPosition().x + 2, servery.getPosition().y);

    // Bench
    GridPoint2 middlePos = new GridPoint2(5,4);
    Entity middle = StationFactory.createMainBenchTable();
    spawnEntityAt(middle, middlePos, false, false);
    middle.setPosition(middle.getPosition().x - 2.6f, middle.getPosition().y - 3.9f);
//
//    GridPoint2 topPos = new GridPoint2(5,4);
//    Entity top = StationFactory.createTopBenchTable();
//    spawnEntityAt(top, topPos, false, false);
//    top.setPosition(top.getPosition().x - 2.6f, top.getPosition().y - 2.6f);
//
//    GridPoint2 bottomPos = new GridPoint2(5,4);
//    Entity bottom = StationFactory.createFeetBenchTable();
//    spawnEntityAt(bottom, bottomPos, false, false);
//    bottom.setPosition(bottom.getPosition().x - 2.6f, bottom.getPosition().y - 5.2f);
  }

  /**
   * spawn a bench
   *         note: coordinates begin at bottom left of screen
   */
  private void spawnBench() {
    //Spawn a flame, this is temporary and for testing purposes
    GridPoint2 flamePos = new GridPoint2(1,1);
    Entity flame = StationFactory.createFlame();
    spawnEntityAt(flame, flamePos, false, false);

    GridPoint2 fireExtinguisherPos = new GridPoint2(4, 4);
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
     spawnBench(); // temporary, spawns a fire extinguisher and a fire (?)

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

//    // Long bench bottom part (left shadow + right shadow)
//    spawnSingleBench("left_corner_shadow", 11, 3f);
//    spawnSingleBench("top_shadows", 12, 3f);
//    spawnSingleBench("right_corner_shadow", 13, 3f);
//
//    // Right side of long bench (vertical and top fin)
//    spawnSingleBench("top", 13f, 7f);
//    spawnBenchColumn("vertical", 13f, 4, 6);
//
//    // Left side of long bench (final vertical + top fin)
//    spawnSingleBench("vertical", 11f, 4f); // Left vertical part of the long bench
//    spawnSingleBench("top", 11f, 5f); // Top left fin for long bench

//    // Right vertical bench column
//    spawnBenchColumn("vertical", 15f, 2, 9);
//    spawnSingleBench("top", 15f, 10f);

    // Left vertical bench column
    spawnBenchColumn("vertical", 4f, 4, 6);
    spawnSingleBench("bottom_shadow", 4f, 3f);
    spawnSingleBench("top", 4f, 7f);

//    // Middle long bench (vertical section)
//    spawnBenchColumn("vertical", 9f, 2, 4);
//    spawnSingleBench("final", 7f, 3f);
//    spawnSingleBench("top", 9f, 5f);

    // Additional benches near middle area
//    spawnSingleBench("middle", 7f, 5f);
//    spawnSingleBench("middle", 8f, 5f);
//    spawnSingleBench("left_border", 6f, 5f);

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

  private Entity spawnCustomerController() {
    Entity spawnController = new Entity();
    spawnController.getEvents().addListener(PersonalCustomerEnums.HANK.name(), this::spawnHank);
    spawnController.getEvents().addListener(PersonalCustomerEnums.LEWIS.name(), this::spawnLewis);
    spawnController.getEvents().addListener(PersonalCustomerEnums.SILVER.name(), this::spawnSilver);
    spawnController.getEvents().addListener(PersonalCustomerEnums.JOHN.name(), this::spawnJohn);
    spawnController.getEvents().addListener(PersonalCustomerEnums.MOONKI.name(), this::spawnMoonki);
    spawnController.getEvents().addListener(PersonalCustomerEnums.BASIC_SHEEP.name(), this::spawnBasicSheep);
    spawnController.getEvents().addListener(PersonalCustomerEnums.BASIC_CHICKEN.name(), this::spawnBasicChicken);

    // Called on the game area as this can happen in any game area
    spawnController.getEvents().addListener("SpawnFlame", this::spawnFlame);

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
    GridPoint2 platePosition = new GridPoint2(3, 2);
    spawnEntityAt(newPlate, platePosition, true, false);
    newPlate.setScale(1.0f, 1.0f);

    return newPlate;
  }

  /**
   * Spawn Stack Plate item but with meals
   * @return A newPlate entity with meal
   */
  private Entity spawnPlatewithMeal() {
    Entity newPlate = PlateFactory.spawnMealOnPlate(1,"salad");
    GridPoint2 platePosition = new GridPoint2(6, 4);
    spawnEntityAt(newPlate, platePosition, true, false);
    newPlate.setScale(0.8f, 0.8f);

    return newPlate;
  }

  /**
   * Plays the background music
   */
  private void playMusic() {
    Music music = ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class);
    music.setLooping(true);
    music.setVolume(0.02f);
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

  /**
   * Spawns a boss entity
   */
  private void spawnBoss() {
    GridPoint2 position = new GridPoint2(1, 5);
    Vector2 targetPos = new Vector2(2, 6);
    Entity boss = NPCFactory.createBoss(targetPos);
    spawnEntityAt(boss, position, false, false);
  }

  // Spawn Upgrade NPC
  private void spawnPenguin(UpgradesDisplay upgradesDisplay) {
    GridPoint2 position = new GridPoint2(1, 5);
    Vector2 targetPos = new Vector2(1, 4);
    Vector2 targetPos2 = new Vector2(2, 0);

    // Create the penguin entity
    Entity penguin = NPCFactory.createUpgradeNPC(player, targetPos, targetPos2, upgradesDisplay);


    // Spawn the penguin at the desired position
    spawnEntityAt(penguin, position, true, true);

}


  /**
   * Triggers the Fired cutscene
   */
  private void triggerFiredEnd() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
      try {
        spawnBoss();
        Thread.sleep(10000);
        createTextBox("You *oink* two-legged moron! You're ruining my " +
                "business' *oink* reputation! Get out!");
        Thread.sleep(10000);
        app.exit();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Thread was interrupted");
      }
    });
    executor.shutdown();
  }

  /**
   * Triggers the Raise cutscene
   */
  private void triggerRaiseEnd() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
      try {
        spawnBoss();
        Thread.sleep(10000);
        createTextBox("You *oink* amazing critter! You're a master! " +
                "Enjoy a 40c raise for your efforts!");
        Thread.sleep(10000);
        app.exit();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Thread was interrupted");
      }
    });
    executor.shutdown();
  }

  /**
   * Creates a text box on the screen
   * @param text A string with desired text to appear
   */
  private void createTextBox(String text) {
    for (Entity entity: ServiceLocator.getEntityService().getEntities()) {
      entity.getEvents().trigger("SetText", text);
    }
  }


  /**
   * Create the moral decision screen
   */
  private void createMoralScreen() {
    Entity moralScreen = new Entity();
    moralScreen
            .addComponent(new MoralDecisionDisplay())
            .addComponent(new MoralDecision());
    ServiceLocator.getEntityService().registerMoral(moralScreen);
  }

  /**
   * Create the end day screen
   */
  private void createEndDayScreen() {
    Entity endDayScreen = new Entity();
    endDayScreen
            .addComponent(new EndDayDisplay());
    ServiceLocator.getEntityService().registerEndDay(endDayScreen);
  }
}

