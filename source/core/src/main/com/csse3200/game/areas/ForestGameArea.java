package com.csse3200.game.areas;
import com.csse3200.game.components.maingame.CheckWinLoseComponent;
import com.csse3200.game.components.moral.Decision;
import com.csse3200.game.components.npc.PersonalCustomerEnums;
import com.badlogic.gdx.utils.Null;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.maingame.TextDisplay;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.entities.configs.PlayerConfig;
import com.csse3200.game.components.moral.MoralDecisionDisplay;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.csse3200.game.components.moral.MoralDayOne;
import com.csse3200.game.components.moral.MoralDayTwo;
import com.csse3200.game.components.moral.MoralDayThree;
import com.csse3200.game.components.moral.MoralDayFour;
import com.csse3200.game.areas.map.Map;
import com.csse3200.game.services.MapLayout;
import com.csse3200.game.entities.factories.*;
import com.csse3200.game.components.moral.MoralDayTwo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.Gdx.app;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.areas.terrain.TerrainFactory.TerrainType;
import com.csse3200.game.areas.map.BenchLayout;
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
import com.csse3200.game.components.moral.MoralDecisionDisplay;

import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.utils.math.GridPoint2Utils;
import com.csse3200.game.entities.factories.NPCFactory;
import com.csse3200.game.components.maingame.TextDisplay;
import com.csse3200.game.entities.factories.ObstacleFactory;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.PlayerFactory;
import com.csse3200.game.entities.factories.StationFactory;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.entities.factories.PlateFactory;

/** Forest area for the demo game with trees, a player, and some enemies. */
public class ForestGameArea extends GameArea {
  private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
  private static final int NUM_TREES = 7;
  private static final int NUM_GHOSTS = 2;
  private static final int NUM_CUSTOMERS_BASE = 1;
  private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(5, 2);
  private static final float WALL_WIDTH = 0.1f;
  private static final String[] forestTextures = {
    "images/special_NPCs/boss.png",
    "images/special_NPCs/penguin2.png",
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
    "images/hourglass.png",
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
    "images/stations/baskets/basket_banana.png",
    "images/stations/baskets/basket_cucumber.png",
    "images/stations/baskets/basket_lettuce.png",
    "images/stations/baskets/basket_strawberry.png",
    "images/stations/baskets/basket_tomato.png",
    "images/chef_player.png",
    "images/frame/top_border.png",
    "images/frame/left_border.png",
    "images/frame/bottom_border.png",
    "images/frame/right_door.png",
    "images/frame/left_door.png",
    "images/frame/door.png",
    "images/frame/top_door_left_part.png",
    "images/frame/top_door_right_part.png",
    "images/frame/bottom_left_inv.png",
    "images/frame/bottom_right_inv.png",
    "images/frame/full_door.png",
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
          "images/stations/benches/garbage_bin.png",
          "images/stations/baskets/basket_acai.png",
          "images/stations/fridge/fridge_meat.png",
          "images/stations/fridge/fridge_choc.png",
          "images/stations/chopping_board/choppingboardbench.png",
    "images/stations/benches/left_corner_shadow.png",
    "images/stations/benches/right_corner_shadow.png",
    "images/stations/benches/top_shadows.png",
    "images/frame/vertical_border.png",
    "images/frame/horizontal_border.png",
    "images/frame/topleft_door.png",
    "images/frame/topright_door.png",
    "images/frame/bottomleft_door.png",
    "images/frame/bottomright_door.png",
          "images/frame/border_test.png",
          "images/frame/side_border.png",
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
          "images/Cutscenes/cutscene_badEnd.atlas",
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
  private final GdxGame.LevelType level;

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
  public ForestGameArea(TerrainFactory terrainFactory, GdxGame.LevelType level, UpgradesDisplay upgradesDisplay) {
    super();
    this.level = level;
    this.terrainFactory = terrainFactory;
    this.upgradesDisplay = upgradesDisplay;
    //this.textDisplay = textDisplay;

    ServiceLocator.registerGameArea(this);
  }

  /** Create the game area, including terrain, static entities (trees), dynamic entities (player) */
  @Override
  public void create() {
    // call load function based on the level argument
    // return list of items to spawn based on the load function
    // Baaed on lsit of items to spawn, spawn the items
    loadAssets();
    displayUI();
    spawnTerrain();
    spawnWall();
    MapLayout mapLayout = new MapLayout();
    Map result = mapLayout.load(this.level);
    for (Bench bench : result.getBenches()) {
      spawnEntity(bench);
      bench.setPosition(bench.x, bench.y);
    }
    for (Entity station : result.getStations()) {
      spawnEntity(station);
        //station.setPosition(station.getPosition().x, station.getPosition().y);
    }

      //ServiceLocator.getMapLayout().getEvents().trigger("load", "level1");

    new_border();
    //ticketDetails();
    spawnStations();
    customerSpawnController = spawnCustomerController();
    createMoralScreen();
    createMoralSystem();
    //spawnplates
      spawnStackPlate(5); //testplate spawn

      //spawnPlatewithMeal();
      player = spawnPlayer();
      ServiceLocator.getPlayerService().registerPlayer(player);
      //ServiceLocator.getSaveLoadService().setCombatStatsComponent(player.getComponent(CombatStatsComponent.class));
      ServiceLocator.getSaveLoadService().load();
      ServiceLocator.getDayNightService().getEvents().addListener("upgrade", () -> {
          spawnPenguin(upgradesDisplay);});

    // Check and trigger win/loss state
    ServiceLocator.getDayNightService().getEvents().addListener("endGame", this::checkEndOfGameState);

    createEndDayScreen();
    playMusic();
  }

  /**
   * Checks the player's game state using the CheckWinLoseComponent and determines whether
   * to trigger a win or loss cutscene. If the player has won, it further checks the moral
   * decisions to determine whether to trigger the "good" or "bad" ending.
   *
   * The function performs the following:
   * 1. If the player has lost (gameState is "LOSE"), it triggers the "loseEnd" event and
   *    displays a losing message to the player.
   * 2. If the player has won (gameState is "WIN"), it checks the player's moral decisions
   *    using the MoralDecision component:
   *    - If the player made any bad decisions, it triggers the "badEnd" event and displays
   *      a corresponding message.
   *    - If no bad decisions are found, it triggers the "goodEnd" event and displays a positive
   *      message.
   */
  private void checkEndOfGameState() {
    String gameState = player.getComponent(CheckWinLoseComponent.class).checkGameState();

    if ("LOSE".equals(gameState)) {
      createTextBox("You *oink* two-legged moron! You're ruining my " +
              "business' *oink* reputation! Get out!");
      ServiceLocator.getEntityService().getEvents().trigger("loseEnd");
    }

    else if ("WIN".equals(gameState)) {
      List<Decision> decisionList = ServiceLocator.getEntityService()
              .getMoralSystem()
              .getComponent(MoralDecision.class)
              .getListOfDecisions();

      boolean hasBadDecisions = false;
      for (Decision decision : decisionList) {
        if (!decision.isGood()) {
          hasBadDecisions = true;
          break;
        }
      }

      if (hasBadDecisions) {
        createTextBox("You *oink* amazing critter! You're a master! " +
                "Enjoy a 40c raise for your efforts!");
        ServiceLocator.getEntityService().getEvents().trigger("badEnd");
      } else {
        createTextBox("You *oink* amazing critter! You're a master! " +
                "Enjoy a 40c raise for your efforts!");
        ServiceLocator.getEntityService().getEvents().trigger("goodEnd");
      }
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
   * Spawns the wall around the restaurant
   */
  private void spawnWall() {
    GridPoint2 coords;
    Vector2 pos;

    for (int i=0;i<12;i++) {
      coords = new GridPoint2(i,7);
      Entity top_wall = ObstacleFactory.wall();
      spawnEntityAt(top_wall, coords, true, true);
      top_wall.setPosition(i, 8f);
    }
    coords = new GridPoint2(3,7);
    Entity left_door = ObstacleFactory.door("full_door");
    spawnEntityAt(left_door, coords, true, true);
    left_door.setPosition(1f, 8f);


  }
  /**
   * Spawns the border around the restaurant
   */
  private void new_border(){
    GridPoint2 coords = new GridPoint2(0,0);
    Vector2 pos;

    for (int i=0;i<14;i++) {
        Entity top_border = ObstacleFactory.spawnBorderTile();
        spawnEntityAt(top_border, coords, true, true);
        top_border.setPosition(i, -0.08f);
    }

    for (int i=0;i<14;i++) {
      Entity top_border = ObstacleFactory.spawnBorderTile();
      spawnEntityAt(top_border, coords, true, true);
      top_border.setPosition(i, 8f);
    }
    for (int i=0;i<14;i++) {
      Entity top_border = ObstacleFactory.spawnBorderTile();
      spawnEntityAt(top_border, coords, true, true);
      top_border.setPosition(i, 9f);
    }

    for (int y=0;y<9;y++) {
      Entity left_border = ObstacleFactory.spawnBorderTileVertical();
      spawnEntityAt(left_border, coords, true, true);
       left_border.setPosition(0, y);
    }

    for (int y=0;y<9;y++) {
      Entity left_border = ObstacleFactory.spawnBorderTileVertical();
      spawnEntityAt(left_border, coords, true, true);
      left_border.setPosition(13.89f, y);
    }

    for (int y=0;y<9;y++) {
      Entity left_border = ObstacleFactory.spawnBorderTileVertical();
      spawnEntityAt(left_border, coords, true, true);
      left_border.setPosition(4, y);
    }

  }

  /**
   * Renders a black border around the restaurant
   */


  private void spawnStations() {
    int a = 0;
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

    GridPoint2 fireExtinguisherPos = new GridPoint2(3, 1);
    Entity fireExtinguisher = StationFactory.createFireExtinguisher();
    spawnEntityAt(fireExtinguisher, fireExtinguisherPos, false, false);
  }
  /**
   * Spawns benches around the restaurant
   */
  private void spawnBenches() {
    for (Bench bench : BenchLayout.levelOne()) {
      spawnEntity(bench);
      bench.setPosition(bench.x, bench.y);
    }
  }

  private Entity spawnPlayer() {
    Entity newPlayer;
    PlayerConfig playerConfig = new PlayerConfig();
    newPlayer = PlayerFactory.createPlayer();
    spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
    newPlayer.setPosition(PLAYER_SPAWN.x, 2.5f);
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
        GridPoint2 position = new GridPoint2(1, 3);
        Vector2 targetPos = new Vector2(3, 3);
        Entity customer = NPCFactory.createCustomerPersonal(name, targetPos);
        spawnEntityAt(customer, position, true, true);

  }

  private void spawnBasicCustomer(String name) {
    GridPoint2 position = new GridPoint2(0, 3);
    Vector2 targetPos = new Vector2(3, 3);
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
    GridPoint2 platePosition = new GridPoint2(5, 3);
    spawnEntityAt(newPlate, platePosition, true, false);
    newPlate.setScale(1.0f, 1.0f);
    newPlate.setPosition(newPlate.getPosition().x - 6f , newPlate.getPosition().y + 0f);
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
  private void spawnPenguin(UpgradesDisplay upgradesDisplay){
    GridPoint2 position = new GridPoint2(1, 3);

    Vector2 targetPos = new Vector2(1, 0);
    // Create the penguin entity
    Entity penguin = NPCFactory.createUpgradeNPC(targetPos, upgradesDisplay);


    // Spawn the penguin at the desired position
    spawnEntityAt(penguin, position, false, false);

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
            //.addComponent(new MoralDecisionDisplay())
            //.addComponent(new MoralDayOne());
            //.addComponent(new MoralDayTwo());
            //addComponent(new MoralDayThree());
            .addComponent(new MoralDayFour());
//            .addComponent(new MoralDecision());

    ServiceLocator.getEntityService().registerMoral(moralScreen);
  }

  private void createMoralSystem() {
    Entity moralSystem = new Entity();
    moralSystem
            .addComponent(new MoralDecision());
    ServiceLocator.getEntityService().registerMoralSystem(moralSystem);
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

