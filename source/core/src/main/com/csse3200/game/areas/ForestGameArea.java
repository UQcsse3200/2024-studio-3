package com.csse3200.game.areas;

import net.dermetfan.gdx.scenes.scene2d.ui.Popup.PositionBehavior.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.areas.terrain.TerrainFactory.TerrainType;
import com.csse3200.game.components.gamearea.GameAreaDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.Benches.Bench;
import com.csse3200.game.entities.factories.NPCFactory;
import com.csse3200.game.entities.factories.ObstacleFactory;
import com.csse3200.game.entities.factories.PlayerFactory;
import com.csse3200.game.entities.factories.StationFactory;
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
    "images/stations/oven.png",
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
          "images/stations/border.png"
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

    // Create the benches
    spawnBenches();


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
    GridPoint2 ovenPos = new GridPoint2(28,24);
    Entity oven = StationFactory.createOven();
    spawnEntityAt(oven, ovenPos, true, false);

    GridPoint2 stovePos = new GridPoint2(23,24);
    Entity stove = StationFactory.createStove();
    spawnEntityAt(stove, stovePos, true, false);
  }

  // Spawn benches
  private void spawnBenches() {

    // Ordering bench
    GridPoint2 benchPos1 = new GridPoint2(2,2);
    Entity station1 = Bench.createBench("bench", 4.5f);
    spawnEntityAt(station1, benchPos1, true, false);
    Vector2 pos1 = station1.getPosition();

    // Middle benches
    GridPoint2 benchPos2 = new GridPoint2(5,3);
    Entity station2 = Bench.createBench("bench", 3.5f);
    spawnEntityAt(station2, benchPos2, true, false);
    Vector2 pos2 = station2.getPosition();

    GridPoint2 benchPos3 = new GridPoint2(5,1);
    Entity station3 = Bench.createBench("bench", 3.5f);
    spawnEntityAt(station3, benchPos3, true, false);
    Vector2 pos3 = station3.getPosition();

    // Long benches
    GridPoint2 benchPos4 = new GridPoint2(8,0);
    Entity station4 = Bench.createBench("bench", 4.0f);
    spawnEntityAt(station4, benchPos4, false, false);
    Vector2 pos4 = station4.getPosition();

    GridPoint2 benchPos5 = new GridPoint2(8,1);
    Entity station5 = Bench.createBench("bench", 4.0f);
    spawnEntityAt(station5, benchPos5, false, false);
    Vector2 pos5 = station5.getPosition();

    GridPoint2 benchPos6 = new GridPoint2(8,2);
    Entity station6 = Bench.createBench("bench", 4.0f);
    spawnEntityAt(station6, benchPos6, false, false);
    Vector2 pos6 = station6.getPosition();

    GridPoint2 benchPos7 = new GridPoint2(8,3);
    Entity station7 = Bench.createBench("bench", 4.0f);
    spawnEntityAt(station7, benchPos7, false, false);
    Vector2 pos7 = station7.getPosition();

    GridPoint2 benchPos8 = new GridPoint2(8,4);
    Entity station8 = Bench.createBench("bench", 4.0f);
    spawnEntityAt(station8, benchPos8, false, false);
    Vector2 pos8 = station8.getPosition();

    // station2.setPosition(pos.x - (station2.getCenterPosition().x - pos.x), pos.y);
    // need to get width of tile
    station1.setPosition(pos1.x - (terrain.getTileSize() / 2), pos1.y);

    station2.setPosition(pos2.x - (terrain.getTileSize() / 2), pos2.y);

    station3.setPosition(pos3.x - (terrain.getTileSize() / 2), pos3.y);

    station4.setPosition(pos4.x - (terrain.getTileSize() / 2), pos4.y);

    station5.setPosition(pos5.x - (terrain.getTileSize() / 2), pos5.y);

    station6.setPosition(pos6.x - (terrain.getTileSize() / 2), pos6.y);

    station7.setPosition(pos7.x - (terrain.getTileSize() / 2), pos7.y);

    station8.setPosition(pos8.x - (terrain.getTileSize() / 2), pos8.y);

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
