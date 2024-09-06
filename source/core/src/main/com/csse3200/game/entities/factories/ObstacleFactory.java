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

    PhysicsUtils.setScaledCollider(border, 1f, 1f);
    return border;
  }

  /**
   * Creates a separation border between customer and chef
   */
  public static Entity verticalSeparation(){
    Entity border = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/vertical_border.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    float width=-1f,height=131.9f,scalefactor=11f;
    border.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    PhysicsUtils.setScaledCollider(border, 1.0F, 0.8F);
    border.setScale(width/scalefactor, height/scalefactor);
    PhysicsUtils.setScaledCollider(border, 1, (height - 5) / height);
    return border;
  }

  public static Entity horizontalSeparation(){
    Entity border = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/horizontal_border.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    float width = 177.4f, height = 1f, scalefactor = 11f;
    border.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    PhysicsUtils.setScaledCollider(border, 1.0F, 0.8F);
    border.setScale(width/scalefactor, height/scalefactor);
    PhysicsUtils.setScaledCollider(border, 1, (height - 5) / height);
    return border;
  }

  public static Entity Door(String type){
    Entity border = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/" + type + ".png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));
    float width = 22f, height = 10.2f, scalefactor = 11f;
    border.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    PhysicsUtils.setScaledCollider(border, 1.0F, 0.8F);
    border.setScale(width/scalefactor, height/scalefactor);
    PhysicsUtils.setScaledCollider(border, 1, (height - 5) / height);
    return border;
  }

  public static Entity wall(){
    Entity border = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/wall.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));
    float width = 132f, height = 10.2f, scalefactor = 11f;
    border.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    PhysicsUtils.setScaledCollider(border, 1.0F, 0.8F);
    border.setScale(width/scalefactor, height/scalefactor);
    PhysicsUtils.setScaledCollider(border, 1, (height - 5) / height);
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
