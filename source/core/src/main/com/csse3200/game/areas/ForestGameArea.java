package com.csse3200.game.areas;
import com.csse3200.game.components.maingame.CheckWinLoseComponent;
import com.csse3200.game.components.moral.Decision;
import com.csse3200.game.components.npc.PersonalCustomerEnums;
import com.csse3200.game.GdxGame;
import com.csse3200.game.entities.benches.Bench;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.csse3200.game.areas.map.Map;
import com.csse3200.game.services.*;
import com.csse3200.game.services.MapLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.Gdx.app;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.areas.terrain.TerrainFactory.TerrainType;
import com.csse3200.game.components.gamearea.GameAreaDisplay;
import com.csse3200.game.components.maingame.EndDayDisplay;
import com.csse3200.game.components.moral.MoralDecision;
import com.csse3200.game.components.upgrades.UpgradesDisplay;
import com.csse3200.game.entities.Entity;

import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.entities.factories.NPCFactory;
import com.csse3200.game.entities.factories.ObstacleFactory;
import com.csse3200.game.entities.factories.PlayerFactory;
import com.csse3200.game.entities.factories.StationFactory;

import com.csse3200.game.utils.math.GridPoint2Utils;

/** Forest area for the demo game with trees, a player, and some enemies. */
public class ForestGameArea extends GameArea {
  private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
  private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(5, 2);
  private static final String[] forestTextures = {
          //"images/special_NPCs/boss.png",
          //"images/special_NPCs/penguin2.png",
          "images/meals/acai_bowl.png",
          "images/meals/banana_split.png",
          "images/meals/salad.png",
          "images/meals/steak_meal.png",
          "images/meals/fruit_salad.png",
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
          "images/ingredients/raw_banana.png",
          "images/ingredients/chopped_banana.png",
          //"images/ingredients/raw_fish.png",
          "images/ingredients/cooked_fish.png",
          "images/ingredients/raw_beef.png",
          "images/ingredients/cooked_beef.png",
          "images/ingredients/burnt_beef.png",
          "images/money.png",
          "images/hourglass.png",
          "images/tooltip_bg.png",
          //"images/chef_player.png",
          "images/tiles/orange_tile.png",
          "images/tiles/blue_tile.png",
          "images/stations/oven.png",
          "images/stations/stove.png",
          "images/stations/apple_tree.png",
          "images/stations/servery.png",
          "images/stations/refrigerator.png",
          "images/fireExtinguisher/Fire_Extinguisher.png",

          "images/stations/bench_legs.png",
          "images/stations/bench_top.png",
          "images/stations/benches/left_border.png",
          "images/stations/benches/right_border.png",
          "images/stations/benches/vertical.png",
          "images/stations/benches/top.png",
          "images/stations/benches/bottom_shadow.png",
          "images/stations/benches/single.png",
          "images/stations/benches/single.png",
          "images/stations/benches/left_corner_shadow.png",
          "images/stations/benches/right_corner_shadow.png",
          "images/stations/benches/top_shadows.png",
          "images/stations/benches/garbage_bin.png",
          "images/stations/baskets/basket_acai.png",
          "images/stations/baskets/basket_banana.png",
          "images/stations/baskets/basket_cucumber.png",
          "images/stations/baskets/basket_lettuce.png",
          "images/stations/baskets/basket_strawberry.png",
          "images/stations/baskets/basket_tomato.png",
          "images/stations/fridge/fridge_meat.png",
          "images/stations/fridge/fridge_choc.png",
          "images/stations/chopping_board/choppingboardbench.png",
          "images/stations/benches/dishwasher.png",

          //These frames have some use in ObstacleFactory but it's only ever instantiated as full_door - Alex S.
          "images/frame/full_door.png",
          "images/frame/border_test.png",
          "images/frame/side_border.png",
          "images/frame/wall.png",


          "images/platecomponent/cleanplate.png",
          "images/platecomponent/dirtyplate.png",
          "images/platecomponent/stackedplates/1plates.png",
          "images/platecomponent/stackedplates/2plates.png",
          "images/platecomponent/stackedplates/3plates.png",
          "images/platecomponent/stackedplates/4plates.png",
          "images/platecomponent/stackedplates/5plates.png",
          "images/inventory_ui/slot.png",
          "images/inventory_ui/null_image.png",
          "images/inventory_ui/interact_key.png",
          "images/inventory_ui/combine_key.png",
          "images/inventory_ui/rotate_key.png",
          "images/inventory_ui/chop_key.png",
          "images/inventory_ui/place_key.png",
          "images/inventory_ui/take_key.png",
          "images/inventory_ui/submit_key.png",
          "images/inventory_ui/dispose_key.png",
          "images/inventory_ui/cook_key.png"
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
  private static final String BACKGROUND_MUSIC = "sounds/BB_BGM.mp3";
  private static final String[] forestMusic = {BACKGROUND_MUSIC};
  private static Entity customerSpawnController;

  private final TerrainFactory terrainFactory;
  private final UpgradesDisplay upgradesDisplay;
  private final GdxGame.LevelType level;

  private Entity player;
    // Reference to CheckWinLoseComponent

  /**
   * Initialise this ForestGameArea to use the provided TerrainFactory.
   * @param terrainFactory TerrainFactory used to create the terrain for the GameArea.
   * requires terrainFactory != null
   */
  public ForestGameArea(TerrainFactory terrainFactory, GdxGame.LevelType level, UpgradesDisplay upgradesDisplay) {
    super();
    this.level = level;
    this.terrainFactory = terrainFactory;
    this.upgradesDisplay = upgradesDisplay;

    ServiceLocator.registerGameArea(this);
  }

  /** Create the game area, including terrain, static entities (trees), dynamic entities (player) */
  @Override
  public void create() {
    // call load function based on the level argument
    // return list of items to spawn based on the load function
    // Baaed on lsit of items to spawn, spawn the items
    
    // Create a new interactable service
    ServiceLocator.registerInteractableService(new InteractableService());

    long time1 = ServiceLocator.getTimeSource().getTime();
    loadAssets();
    long time2 = ServiceLocator.getTimeSource().getTime();
    logger.info("Assets loaded: {}ms", time2 - time1);
    displayUI();
    long time3 = ServiceLocator.getTimeSource().getTime();
    logger.info("UI displayed: {}ms", time3 - time2);
    spawnTerrain();
    long time4 = ServiceLocator.getTimeSource().getTime();
    logger.info("Terrain spawned: {}ms", time4 - time3);
    spawnWall();
    long time5 = ServiceLocator.getTimeSource().getTime();
    logger.info("Wall spawned: {}ms", time5 - time4);


    MapLayout mapLayout = new MapLayout();
    Map result = mapLayout.load(this.level);
    long time6 = ServiceLocator.getTimeSource().getTime();
    logger.info("Map loaded: {}ms", time6 - time5);
    for (Bench bench : result.getBenches()) {
      spawnEntity(bench);
      bench.setPosition(bench.x, bench.y);
    }
    for (Entity station : result.getStations()) {
      spawnEntity(station);
    }
    long time7 = ServiceLocator.getTimeSource().getTime();
    logger.info("Map created: {}ms", time7 - time6);




    newborder();

    spawnStations();
    customerSpawnController = spawnCustomerController();
    createMoralScreen();
    createMoralSystem();

      player = spawnPlayer();
      ServiceLocator.getPlayerService().registerPlayer(player);
      ServiceLocator.getSaveLoadService().load();
      ServiceLocator.getDayNightService().getEvents().addListener("upgrade", () -> spawnPenguin(upgradesDisplay));

    // Check and trigger win/loss state
    ServiceLocator.getDayNightService().getEvents().addListener("endGame", this::checkEndOfGameState);

    createEndDayScreen();
    playMusic();

    long time8 = ServiceLocator.getTimeSource().getTime();
    logger.info("Everything else: {}ms", time8 - time7);

  }

  /**
   * Checks the player's game state using the CheckWinLoseComponent and determines whether
   * to trigger a win or loss cutscene. If the player has won, it further checks the moral
   * decisions to determine whether to trigger the "good" or "bad" ending.
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
        createTextBox("You *oink* bad critter! You're a failure! " +
                "You will not get any raise");
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
    GridPoint2 tileBounds = terrain.getMapBounds(0);

    // Left
    spawnEntityAt(
        ObstacleFactory.createWall(), GridPoint2Utils.ZERO, false, false);
    // Right
    spawnEntityAt(
        ObstacleFactory.createWall(),
        new GridPoint2(tileBounds.x, 0),
        false,
        false);
    // Top
    spawnEntityAt(
        ObstacleFactory.createWall(),
        new GridPoint2(0, tileBounds.y),
        false,
        false);
    // Bottom
    spawnEntityAt(
        ObstacleFactory.createWall(), GridPoint2Utils.ZERO, false, false);

  }


  /**
   * Spawns the wall around the restaurant
   */
  private void spawnWall() {
    GridPoint2 coords;

    for (int i=0;i<12;i++) {
      coords = new GridPoint2(i,7);
      Entity topwall = ObstacleFactory.wall();
      spawnEntityAt(topwall, coords, true, true);
      topwall.setPosition(i, 8f);
    }
    coords = new GridPoint2(3,7);
    Entity leftdoor = ObstacleFactory.door("full_door");
    spawnEntityAt(leftdoor, coords, true, true);
    leftdoor.setPosition(1f, 8f);


  }
  /**
   * Spawns the border around the restaurant
   */
  private void newborder(){
    GridPoint2 coords = new GridPoint2(0,0);

    for (int i=0;i<14;i++) {
        Entity topborder = ObstacleFactory.spawnBorderTile();
        spawnEntityAt(topborder, coords, true, true);
        topborder.setPosition(i, -0.08f);
    }

    for (int i=0;i<14;i++) {
      Entity topborder = ObstacleFactory.spawnBorderTile();
      spawnEntityAt(topborder, coords, true, true);
      topborder.setPosition(i, 8f);
    }
    for (int i=0;i<14;i++) {
      Entity topborder = ObstacleFactory.spawnBorderTile();
      spawnEntityAt(topborder, coords, true, true);
      topborder.setPosition(i, 9f);
    }

    for (int y=0;y<9;y++) {
      Entity leftborder = ObstacleFactory.spawnBorderTileVertical();
      spawnEntityAt(leftborder, coords, true, true);
       leftborder.setPosition(0, y);
    }

    for (int y=0;y<9;y++) {
      Entity leftborder = ObstacleFactory.spawnBorderTileVertical();
      spawnEntityAt(leftborder, coords, true, true);
      leftborder.setPosition(13.89f, y);
    }

    for (int y=0;y<9;y++) {
      Entity leftborder = ObstacleFactory.spawnBorderTileVertical();
      spawnEntityAt(leftborder, coords, true, true);
      leftborder.setPosition(4, y);
    }

  }

  /**
   * Renders a black border around the restaurant
   */


  private void spawnStations() {

  }

  /**
   * Spawns benches around the restaurant
   */


  private Entity spawnPlayer() {
    if (ServiceLocator.getPlayerService().getPlayer() != null) {
      logger.warn("Player exist");
      return ServiceLocator.getPlayerService().getPlayer();
    }

    Entity newPlayer = PlayerFactory.createPlayer();
    spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
    newPlayer.setPosition(PLAYER_SPAWN.x, 3.1f);

    ServiceLocator.getPlayerService().registerPlayer(newPlayer);

    return newPlayer;
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

  /**
   * Plays the background music
   */
  private void playMusic() {
    Music music = ServiceLocator.getResourceService().getAsset(BACKGROUND_MUSIC, Music.class);
    music.setLooping(true);
    music.setVolume(0.04f);
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
    ServiceLocator.getResourceService().getAsset(BACKGROUND_MUSIC, Music.class).stop();
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
          logger.warn("Thread was interrupted");
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
          logger.warn("Thread was interrupted");
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

  /**
   * Returns the level the game is currently on.
   *
   * @return level - the level the player is currently on
   */
  public GdxGame.LevelType getLevel() {
    return this.level;
  }
}

