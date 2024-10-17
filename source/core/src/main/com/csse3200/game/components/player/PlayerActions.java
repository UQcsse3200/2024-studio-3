package com.csse3200.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.SensorComponent;

/**
 * Action component for interacting with the player. Player events should be initialised in create()
 * and when triggered should call methods within this class.
 */
public class PlayerActions extends Component {
  private static final Vector2 MAX_SPEED = new Vector2(3f, 3f); // Metres per second
  private static final float MIN_X_POSITION = 3.52f; // Minimum X position - where the separation border is at
  private static final float MAX_X_POSITION = 15.1f; // Maximum X position - where the right border is at
  private PhysicsComponent physicsComponent;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private boolean moving = false;
  private SensorComponent sensor;
  private InventoryComponent playerInventory;
  private InventoryDisplay displayInventory;
  private Entity closestEntity = null;

    @Override
  public void create() {
    physicsComponent = entity.getComponent(PhysicsComponent.class);
    sensor = entity.getComponent(SensorComponent.class);
    playerInventory = entity.getComponent(InventoryComponent.class);
    displayInventory = entity.getComponent(InventoryDisplay.class);
    entity.getEvents().addListener("walk", this::walk);
    entity.getEvents().addListener("walkStop", this::stopWalking);
    entity.getEvents().addListener("attack", this::attack);
    entity.getEvents().addListener("interact", this::interact);
  }

    @Override
    public void update() {
        Body body = physicsComponent.getBody();
        Vector2 position = body.getPosition();

        if (moving) {
          updateInteraction();

            // Stop if it's at min x position or max x position
            if (position.x < MIN_X_POSITION) {
                position.x = MIN_X_POSITION;
                body.setTransform(MIN_X_POSITION, position.y, body.getAngle());
                stopWalking();
            } else if (position.x > MAX_X_POSITION) {
                position.x = MAX_X_POSITION;
                body.setTransform(MAX_X_POSITION, position.y, body.getAngle());
                stopWalking();
            } else {
                updateSpeed();
            }
        }
    }

  /**
   * Updates the player's interaction with nearby objects. This method checks for the closest
   * interactable object within the sensor's range. If an interactable object is found, it triggers
   * the display of a tooltip with interaction details. If no interactable object is nearby, it hides
   * the tooltip.
   * */
  private void updateInteraction() {
    Entity oldClosestEntity = closestEntity;
    closestEntity = sensor.getClosestInteractable();

    if (oldClosestEntity != null) {
      oldClosestEntity.getEvents().trigger("hideToolTip");
    }

    if (closestEntity != null) {
      closestEntity.getEvents().trigger("showToolTip", playerInventory.getItemFirst());
    }
  }

  private void updateSpeed() {
    Body body = physicsComponent.getBody();
    Vector2 velocity = body.getLinearVelocity();
    Vector2 desiredVelocity = walkDirection.cpy().scl(MAX_SPEED);

    if (body.getPosition().x < MIN_X_POSITION || body.getPosition().x > MAX_X_POSITION) {
      // Do not apply any movement if out of bounds
      return;
    }

    Vector2 impulse = desiredVelocity.sub(velocity).scl(body.getMass());
    body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
  }

  /**
   * Triggers an interaction event. It holds the logic in how to interact with a given station
   */
  void interact(String type) {
    if (closestEntity == null) {
      return;
    }

    // Check if the player has an item in its inventory and isn't trying to do the default action
    if (playerInventory.getSize() != 0 && !type.equals("default")) return;

    closestEntity.getEvents().trigger("Station Interaction", playerInventory, displayInventory, type);
    updateInteraction();
  }

  /**
   * Moves the player towards a given direction.
   *
   * @param direction direction to move in
   */
  public void walk(Vector2 direction) {
    this.walkDirection = direction;
    moving = true;
  }

  /**
   * Stops the player from walking.
   */
  public void stopWalking() {
    this.walkDirection = Vector2.Zero.cpy();
    updateSpeed();
    moving = false;
  }

  /**
   * Makes the player attack.
   */
  void attack() {
    Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/Impact4.ogg", Sound.class);
    attackSound.play();
  }
}
