package com.csse3200.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.SensorComponent;

/**
 * Action component for interacting with the player. Player events should be initialised in create()
 * and when triggered should call methods within this class.
 */
public class PlayerActions extends Component {
  private static final Vector2 MAX_SPEED = new Vector2(3f, 3f); // Metres per second

  private PhysicsComponent physicsComponent;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private boolean moving = false;
  private SensorComponent interactionSensor;

  @Override
  public void create() {
    physicsComponent = entity.getComponent(PhysicsComponent.class);
    interactionSensor = entity.getComponent(SensorComponent.class);
    entity.getEvents().addListener("walk", this::walk);
    entity.getEvents().addListener("walkStop", this::stopWalking);
    entity.getEvents().addListener("attack", this::attack);
    entity.getEvents().addListener("interact", this::interact);
  }

  @Override
  public void update() {
    if (moving) {
      updateSpeed();
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
      Vector2 objectPosition = interactable.getBody().getPosition();  // Get object position
//      System.out.println("Interactable object found at: " + objectPosition);
      String interactionKey = "Press E";
      String itemName = "to interact";
      // Create a TooltipInfo object with the text and position
      TooltipsDisplay.TooltipInfo tooltipInfo = new TooltipsDisplay.TooltipInfo(interactionKey + " " + itemName, objectPosition);

      // Trigger the event with the TooltipInfo object
      entity.getEvents().trigger("showTooltip", tooltipInfo);

    } else {
      entity.getEvents().trigger("hideTooltip");
    }
  }

  private void updateSpeed() {
    Body body = physicsComponent.getBody();
    Vector2 velocity = body.getLinearVelocity();
    Vector2 desiredVelocity = walkDirection.cpy().scl(MAX_SPEED);
    // impulse = (desiredVel - currentVel) * mass
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
      // Logic for what interaction even to call on the station
      entity.getEvents().trigger("Add Station Item");
    }
  }

  /**
   * Moves the player towards a given direction.
   *
   * @param direction direction to move in
   */
  void walk(Vector2 direction) {
    this.walkDirection = direction;
    moving = true;
  }

  /**
   * Stops the player from walking.
   */
  void stopWalking() {
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
