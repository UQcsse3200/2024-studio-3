package com.csse3200.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

/**
 * Factory to create obstacle entities.
 *
 * <p>Each obstacle entity type should have a creation method that returns a corresponding entity.
 */
public class ObstacleFactory {

  /**
   * Creates a tree entity.
   * @return entity
   */
  public static Entity createTree() {
    Entity tree =
        new Entity()
            .addComponent(new TextureRenderComponent("images/tree.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    tree.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    tree.getComponent(TextureRenderComponent.class).scaleEntity();
    tree.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(tree, 0.5f, 0.2f);
    return tree;
  }

  /**
   * Creates an invisible physics wall.
   * @param width Wall width in world units
   * @param height Wall height in world units
   * @return Wall entity of given width and height
   */
  public static Entity createWall(float width, float height) {
    Entity wall = new Entity()
        .addComponent(new PhysicsComponent().setBodyType(BodyType.StaticBody))
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));
    //wall.setScale(width, height);
    return wall;
  }

  /**
   * Creates a border around the restaurant
   * @param s Name of the border to be spawned
   * @param tileSize Width of border according to the size of the tiles
   */
  public static Entity createBorder(String s,float tileSize) {
    Entity border = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/"+s+".png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    border.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    border.getComponent(TextureRenderComponent.class).scaleEntity();
    border.scaleWidth(tileSize);

    PhysicsUtils.setScaledCollider(border, 0.001f, 1f);
    return border;
  }

  /**
   * Creats an entry and exit door
   * @param s Name of door to be spawned
   * @param tileSize Width of door according to the size of the tiles
   */
  public static Entity createDoor(String s,float tileSize) {
    Entity door = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/"+s+".png"));
    door.scaleWidth(tileSize);
    return door;
  }




  private ObstacleFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
