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



  public static Entity spawnBorderTile(){
    Entity border = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/border_test.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

   // float width=-1f,height=131,scalefactor=11f;
    float height = 1f;
    border.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);

    PhysicsUtils.setScaledCollider(border, 1, (height-5)/height );
    return border;
  }

  public static Entity spawnBorderTileVertical(){
    Entity border = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/side_border.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    // float width=-1f,height=131,scalefactor=11f;
    float height = 16;
    border.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);

    PhysicsUtils.setScaledCollider(border, 1, (height-5)/height );
    return border;
  }


  public static Entity wall(){
    Entity border = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/wall.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    border.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    border.setScale(3,2);
    PhysicsUtils.setScaledCollider(border, 1, 1);
    return border;
  }

  public static Entity door(String s){
    Entity door = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/"+s+".png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    door.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    door.setScale(3,2);
    PhysicsUtils.setScaledCollider(door, 1, 1);
    return door;
  }


  private ObstacleFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
