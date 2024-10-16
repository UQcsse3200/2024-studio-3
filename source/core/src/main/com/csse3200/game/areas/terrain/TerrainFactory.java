package com.csse3200.game.areas.terrain;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.csse3200.game.areas.terrain.TerrainComponent.TerrainOrientation;
import com.csse3200.game.components.CameraComponent;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

import java.util.Objects;

/** Factory for creating game terrains. */
public class TerrainFactory {
  private static final GridPoint2 MAP_SIZE = new GridPoint2(7, 4);
  private static final int CUST_TILE_COUNT = 13;
  private final OrthographicCamera camera;
  private final TerrainOrientation orientation;

  /**
   * Create a terrain factory with Orthogonal orientation
   *
   * @param cameraComponent Camera to render terrains to. Must be ortographic.
   */
  public TerrainFactory(CameraComponent cameraComponent) {
    this(cameraComponent, TerrainOrientation.ORTHOGONAL);
  }

  /**
   * Create a terrain factory
   *
   * @param cameraComponent Camera to render terrains to. Must be orthographic.
   * @param orientation orientation to render terrain at
   */
  public TerrainFactory(CameraComponent cameraComponent, TerrainOrientation orientation) {
    this.camera = (OrthographicCamera) cameraComponent.getCamera();
    this.orientation = orientation;
  }

  /**
   * Create a terrain of the given type, using the orientation of the factory. This can be extended
   * to add additional game terrains.
   *
   * @param terrainType Terrain to create
   * @return Terrain component which renders the terrain
   */
  public TerrainComponent createTerrain(TerrainType terrainType) {
    ResourceService resourceService = ServiceLocator.getResourceService();
    switch (terrainType) {
      case KITCHEN_DEMO:
        TextureRegion orthoFloor =
            new TextureRegion(resourceService.getAsset("images/tiles/orange_tile.png", Texture.class));
        TextureRegion custTile =
            new TextureRegion(resourceService.getAsset("images/tiles/blue_tile.png", Texture.class));
        return createKitchenDemoTerrain(2f, orthoFloor, custTile);


      default:
        return null;
    }
  }

  /**
   * Create a kitchen demo terrain
   * @param tileWorldSize Size of the tiles in world units
   * @param floor Floor tile
   * @param customertiles Customer tile
   * @return Terrain component for the kitchen demo
   */
  private TerrainComponent createKitchenDemoTerrain(
      float tileWorldSize, TextureRegion floor, TextureRegion customertiles) {
    // Customer_tiles are the blue tiles where the customers/animals are going to be
    GridPoint2 tilePixelSize = new GridPoint2(floor.getRegionWidth(), floor.getRegionHeight());
    TiledMap tiledMap = createKitchenDemoTiles(tilePixelSize, floor, customertiles);
    TiledMapRenderer renderer = createRenderer(tiledMap, tileWorldSize / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation, tileWorldSize);
  }


  private TiledMapRenderer createRenderer(TiledMap tiledMap, float tileScale) {
      if (Objects.requireNonNull(orientation) == TerrainOrientation.ORTHOGONAL) {
          return new OrthogonalTiledMapRenderer(tiledMap, tileScale);
      }
      return null;
  }


  /**
   * Create a tiled map for the kitchen demo
   * @param tileSize Size of the tiles
   * @param floor Floor tile
   * @param customertiles Customer tile
   * @return Tiled map for the kitchen demo
   */
  private TiledMap createKitchenDemoTiles(
      GridPoint2 tileSize,  TextureRegion floor, TextureRegion customertiles) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile floorTile= new TerrainTile(floor);
    TerrainTile customerTile = new TerrainTile(customertiles);
    // Bench tile

    TiledMapTileLayer layer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y, tileSize.x , tileSize.y);

    // Size for blue tiles
    GridPoint2 modifiedsize = new GridPoint2(MAP_SIZE.x/2 , MAP_SIZE.y);

    // Create base orange tiles
    fillTiles(layer, MAP_SIZE, floorTile);
    // Create blue tiles with modified map size

    fillBlueTiles(layer, modifiedsize, customerTile, CUST_TILE_COUNT);

    tiledMap.getLayers().add(layer);
    tiledMap.getLayers().add(layer);
    return tiledMap;
  }

  /**
   * Fill a layer with a single tile (customer area)
   *
   * @param layer Layer to fill
   * @param map Size of the layer
   * @param tile Tile to fill the layer with
   */
  private static void fillBlueTiles(
      TiledMapTileLayer layer, GridPoint2 map, TerrainTile tile, int amount) {
    for (int x = 0; x < map.x-1 ; x++) {
      for (int y = 0; y < map.y; y++) {
        Cell cell = new Cell();
        cell.setTile(tile);
        layer.setCell(x,y,cell);
      }
    }
  }

  /**
   * Fill a layer with a single tile (rest of the map)
   *
   * @param layer Layer to fill
   * @param map Size of the layer
   * @param tile Tile to fill the layer with
   */
  private static void fillTiles(TiledMapTileLayer layer, GridPoint2 map, TerrainTile tile) {
    for (int x = 0; x < map.x; x++) {
      for (int y = 0; y < map.y; y++) {
        Cell cell = new Cell();
        cell.setTile(tile);
        layer.setCell(x, y, cell);
      }
    }
  }


  /**
   * This enum should contain the different terrains in your game, e.g. Kitchen, cave, home, all with
   * the same oerientation. But for demonstration purposes, the base code has the same level in 3
   * different orientations.
   */
  public enum TerrainType {
    KITCHEN_DEMO,
    Level2
  }
}
