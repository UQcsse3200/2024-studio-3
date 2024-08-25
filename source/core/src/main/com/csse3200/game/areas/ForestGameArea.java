package com.csse3200.game.areas;

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
import com.csse3200.game.entities.factories.StationFactory;
import com.csse3200.game.entities.factories.PlayerFactory;
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
    "images/box_boy_leaf.png",
    "images/tree.png",
    "images/ghost_king.png",
    "images/ghost_1.png",
    "images/new_orange.png",
    "images/blue_tile.png",
    "images/grass_1.png",
    "images/grass_2.png",
    "images/grass_3.png",
    "images/hex_grass_1.png",
    "images/hex_grass_2.png",
    "images/hex_grass_3.png",
    "images/iso_grass_1.png",
    "images/iso_grass_2.png",
    "images/iso_grass_3.png",
    "images/stations/oven.png", "images/stations/bench.png", "images/chef_player.png", "images/stations/line.png",
    "images/stations/oven.png", "images/stations/bench2.png", "images/chef_player.png",
          "images/stations/top_border.png", "images/stations/left_border.png", "images/stations/bottom_border.png", "images/stations/right_border.png",
          "images/stations/door.png",
          "images/stations/top_door_left_part.png", "images/stations/top_door_right_part.png",
          "images/stations/bottom_door_left_part.png", "images/stations/bottom_door_right_part.png",
          "images/stations/top_border_wall.png", "images/stations/bottom_border_wall.png"
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
    //spawnTrees();
    //spawning the restaurant
    spawnDoor();
    spawnWall();
    make_border();

//    spawnTrees();
    player = spawnPlayer();
    //spawnGhosts();
    //spawnGhostKing();
    // Create the cooking stations
//    spawnStations();



//    playMusic();
  }



  private void displayUI() {
    Entity ui = new Entity();
    ui.addComponent(new GameAreaDisplay("Box Forest"));
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
    door = ObstacleFactory.createDoor("bottom_door_left_part",tileSize);
    spawnEntityAt(door,position,true,false);

    position = new GridPoint2(1,0);
    door = ObstacleFactory.createDoor("bottom_door_right_part",tileSize);
    spawnEntityAt(door,position,true,false);
  }

  private void spawnWall() {
    float tileSize = terrain.getTileSize();
    GridPoint2 tileBounds = terrain.getMapBounds(0);
    Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

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

  private void spawnTrees() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    for (int i = 0; i < NUM_TREES; i++) {
      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
      Entity tree = ObstacleFactory.createTree();
      spawnEntityAt(tree, randomPos, true, false);
    }
  }

  private void spawnStations() {
    GridPoint2 ovenPos = new GridPoint2(3,5);
    Entity station2 = StationFactory.createStation("oven");
    spawnEntityAt(station2, ovenPos, true, false);
  }


  private Entity spawnPlayer() {
    Entity newPlayer = PlayerFactory.createPlayer();
    spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
    return newPlayer;
  }

  private void spawnGhosts() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    for (int i = 0; i < NUM_GHOSTS; i++) {
      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
      Entity ghost = NPCFactory.createGhost(player);
      spawnEntityAt(ghost, randomPos, true, true);
    }
  }

  private void spawnGhostKing() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
    Entity ghostKing = NPCFactory.createGhostKing(player);
    spawnEntityAt(ghostKing, randomPos, true, true);
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
