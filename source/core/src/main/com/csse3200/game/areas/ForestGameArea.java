package com.csse3200.game.areas;

import com.csse3200.game.entities.benches.Bench;
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

/** Forest area for the demo game with trees, a player, and some enemies. */
public class ForestGameArea extends GameArea {
  private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
  private static final int NUM_TREES = 7;
  private static final int NUM_GHOSTS = 2;
  private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(5, 3);
  private static final float WALL_WIDTH = 0.1f;
  private static final String[] forestTextures = {
    "images/raw_cucumber.png",
    "images/raw_tomato.png",
    "images/raw_strawberry.png",
    "images/raw_lettuce.png",
    "images/raw_chocolate.png",
    "images/money.png",
    "images/fruit_salad.png",
    "images/raw_banana.png",
    "images/chopped_banana.png",
    "images/raw_fish.png",
    "images/cooked_fish.png",
    "images/raw_beef.png",
    "images/cooked_beef.png",
    "images/burnt_beef.png",
    "images/tiles/orange_tile.png",
    "images/tiles/blue_tile.png",
    "images/stations/oven.png",
    "images/stations/stove.png",
    "images/stations/bench.png",
    "images/chef_player.png",
    "images/stations/line.png",
    "images/stations/bench2.png",
    "images/stations/top_border.png",
    "images/stations/left_border.png",
    "images/stations/bottom_border.png",
    "images/stations/right_border.png",
    "images/stations/door.png",
    "images/stations/top_door_left_part.png",
    "images/stations/top_door_right_part.png",
    "images/stations/bottom_left_inv.png",
    "images/stations/bottom_right_inv.png",
    "images/stations/top_border_wall.png",
    "images/stations/bottom_border_wall.png",
    "images/stations/border.png",
    "images/stations/benches/bench7.png",
    "images/stations/benches/bench2.png",
    "images/stations/benches/bench3-5.png",
    "images/stations/benches/bench4.png",
    "images/stations/benches/bench6.png",
    "images/stations/benches/bench1.png",
  };
  private static final String[] forestTextureAtlases = {
    "images/terrain_iso_grass.atlas", "images/ghost.atlas", "images/ghostKing.atlas"
  };
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
    make_border();
    spawnBenches();
    spawnStations();
    // Spawn beef
    spawnBeef("raw");

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
    terrain = terrainFactory.createTerrain(TerrainType.FOREST_DEMO);
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

  private void spawnDoor() {
    float tileSize = terrain.getTileSize();
    GridPoint2 tileBounds = terrain.getMapBounds(0);
    Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

    GridPoint2 position = new GridPoint2(0,(int)tileBounds.y-1);
    Entity door = ObstacleFactory.createDoor("top_door_left_part",tileSize);
    spawnEntityAt(door,position,true,false);

    position = new GridPoint2(1,(int)tileBounds.y-1);
    door = ObstacleFactory.createDoor("top_door_right_part",tileSize);
    spawnEntityAt(door,position,true,false);

    position = new GridPoint2(0,0);
    door = ObstacleFactory.createDoor("bottom_left_inv",tileSize);
    spawnEntityAt(door,position,true,false);

    position = new GridPoint2(1,0);
    door = ObstacleFactory.createDoor("bottom_right_inv",tileSize);
    spawnEntityAt(door,position,true,false);
  }

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

  private void make_border(){
    float tileSize = terrain.getTileSize();
    GridPoint2 tileBounds = terrain.getMapBounds(0);
    Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

    //top border
    for(int x=0;x<(int)tileBounds.x; x++){
      GridPoint2 position=new GridPoint2(x,(int)tileBounds.y-1);
      Entity top = ObstacleFactory.createBorder("top_border",tileSize);
      spawnEntityAt(top,position,true,false);
    }

    //left border
    for(int y=0;y<(int)tileBounds.y;y++){
      GridPoint2 position = new GridPoint2(0,y);
      Entity left = ObstacleFactory.createBorder("left_border",tileSize);
      spawnEntityAt(left,position,true,false);
    }

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
    GridPoint2 ovenPos = new GridPoint2(5,5);
    Entity oven = StationFactory.createOven();
    spawnEntityAt(oven, ovenPos, true, false);

    GridPoint2 stovePos = new GridPoint2(6,5);
    Entity stove = StationFactory.createStove();
    spawnEntityAt(stove, stovePos, true, false);
  }

  /**
   * Render and spawn all benches.
   */
  private void spawnBenches() {
    // Top bench
    GridPoint2 benchPos5 = new GridPoint2(5,5);
    Entity station5 = StationFactory.createStation("bench7", 1.0f);
    spawnEntityAt(station5, benchPos5, true, false);
    Vector2 pos5 = station5.getPosition();
    station5.setPosition(pos5.x - (terrain.getTileSize() / 2), pos5.y);

    // Bottom bench
    GridPoint2 benchPos4 = new GridPoint2(5,1);
    Entity station4 = StationFactory.createStation("bench7", 1.0f);
    spawnEntityAt(station4, benchPos4, true, false);
    Vector2 pos4 = station4.getPosition();
    station4.setPosition(pos4.x - (terrain.getTileSize() / 2), pos4.y - (terrain.getTileSize() / 2));

    // Left bench
    GridPoint2 benchPos1 = new GridPoint2(2,2);
    Entity station1 = StationFactory.createStation("bench2", 4.5f);
    spawnEntityAt(station1, benchPos1, false, false);
    Vector2 pos1 = station1.getPosition();
    station1.setPosition(pos1.x, pos1.y);

    // Right bench
    GridPoint2 benchPos6 = new GridPoint2(7,0);
    Entity station6 = StationFactory.createStation("bench6", 10.0f);
    spawnEntityAt(station6, benchPos6, false, false);
    Vector2 pos6 = station6.getPosition();
    station6.setPosition(pos6.x + (terrain.getTileSize() / 4), pos6.y + (terrain.getTileSize() / 2));

    // Center top bench
    GridPoint2 benchPos2 = new GridPoint2(5,3);
    Entity station2 = StationFactory.createStation("bench1", 3.5f);
    spawnEntityAt(station2, benchPos2, true, false);
    Vector2 pos2 = station2.getPosition();
    station2.setPosition(pos2.x - (terrain.getTileSize() / 2), pos2.y + (terrain.getTileSize() / 4));

    // Center bottom bench
    GridPoint2 benchPos3 = new GridPoint2(5,0);
    Entity station3 = StationFactory.createStation("bench1", 3.5f);
    spawnEntityAt(station3, benchPos3, true, false);
    Vector2 pos3 = station3.getPosition();
    station3.setPosition(pos3.x - (terrain.getTileSize() / 2), pos3.y + (terrain.getTileSize() / 2));
  }

  private Entity spawnPlayer() {
    Entity newPlayer = PlayerFactory.createPlayer();
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

  private Entity spawnFruitSalad() {
    Entity newFruitSalad = ItemFactory.createFruitSalad();
    spawnEntityAt(newFruitSalad, new GridPoint2(3, 3), true, true);
    newFruitSalad.setScale(0.5f,0.5f);
    return newFruitSalad;
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
}
