package com.csse3200.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.upgrades.RageUpgrade;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.BodyUserData;
import com.csse3200.game.components.items.PlateComponent;
import com.csse3200.game.components.station.FireExtinguisherHandlerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.SensorComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private SensorComponent interactionSensor;
  private InventoryComponent playerInventory;
  private InventoryDisplay displayInventory;

  @Override
  public void create() {
    physicsComponent = entity.getComponent(PhysicsComponent.class);
    interactionSensor = entity.getComponent(SensorComponent.class);
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
    updateInteraction();
  }

  /**
   * Updates the player's interaction with nearby objects. This method checks for the closest
   * interactable object within the sensor's range. If an interactable object is found, it triggers
   * the display of a tooltip with interaction details. If no interactable object is nearby, it hides
   * the tooltip.
   * */
  private void updateInteraction() {
    interactionSensor.update();
    Fixture interactable = interactionSensor.getClosestFixture();
    if (interactable != null) {

      //This is where you show the tooltip / outline for the closest station
//      String interactionKey = "Press E ";  // Hardcoded for simplicity, could be dynamic
//      String itemName = "Some Task";  // Placeholder for actual item name
      // Trigger show tooltip event with interaction details
//      entity.getEvents().trigger("showTooltip", interactionKey + ": " + itemName);

    } else {
      // Hide tooltip if no interactable is nearby
      entity.getEvents().trigger("hideTooltip");
    }

  }

  private void updateSpeed() {
    Body body = physicsComponent.getBody();
    Vector2 velocity = body.getLinearVelocity();
    Vector2 desiredVelocity = walkDirection.cpy().scl(MAX_SPEED);
    // impulse = (desiredVel - currentVel) * mass

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
  void interact() {
    // Get the closest fixture all call an interact method on it
    Fixture interactable = interactionSensor.getClosestFixture();
    if (interactable != null) {
      // We need to notify the input that we are inside an interaction
      //entity.getEvents().trigger("startInteraction");

      // Uses attached information to Fixture on station creation to identify entity belonging
      // too
      Entity station = ((BodyUserData) interactable.getBody().getUserData()).entity;

      // Handle if it was a fire extinguisher
      boolean interactingWithFireExtinguisher = FireExtinguisherHandlerComponent.handleFireExtinguisher(interactable, entity);
      if (interactingWithFireExtinguisher) {
        // No more interacting after this
        return;
      }

      boolean interactingWithPlate = PlateComponent.handlePlateInteraction(interactable, entity);
      if (interactingWithPlate) {
        // Interaction handled by PlateComponent for plates
        return;
      }
      // Code to freeze player, not a current feature
      entity.getEvents().trigger("startInteraction");
      // Logic for what interaction even to call on the station
      station.getEvents().trigger("Station Interaction", playerInventory, displayInventory);
    }
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
