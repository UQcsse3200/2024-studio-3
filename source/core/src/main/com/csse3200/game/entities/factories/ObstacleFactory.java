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
   * @return Wall entity of given width and height
   */
  public static Entity createWall() {
    return new Entity()
        .addComponent(new PhysicsComponent().setBodyType(BodyType.StaticBody))
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));
  }


  public static Entity spawnBorderTile(){
    Entity border = new Entity()
            .addComponent(new TextureRenderComponent("images/frame/border_test.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

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
    door.setScale(2f,2f);
    PhysicsUtils.setScaledCollider(door, 1, 1);
    return door;
  }


  private ObstacleFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
