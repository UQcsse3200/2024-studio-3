package com.csse3200.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.utils.math.Vector2Utils;

import java.util.HashMap;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {
  private Vector2 walkDirection = Vector2.Zero.cpy();
  public static float WALK_SPEED = 1f;
  private static final HashMap<Integer, Integer> keyFlags = new HashMap<>();
  private static final String WALK_STOP = "walkStop";
  private boolean isChopping = false;
  private static final String INTERACT = "interact";
  private boolean isInteracting = false;

  public KeyboardPlayerInputComponent() {
    super(5);
  }

  /**
   * Triggers player events on specific keycodes.
   *
   * @return whether the input was processed
   * @see InputProcessor#keyDown(int)
   */
  @Override
  public boolean keyDown(int keycode) {
    keyFlags.put(keycode, 1);

    // Check if were chopping, and we receive a different key
    // If so stop chopping
    // This in effect freezes the player
    if (isChopping && keycode != Keys.Q) {
      // We know item exits and is patchable
      entity.getEvents().trigger(INTERACT, "stopChop");
      isChopping = false;
    }

    if (keycode == Keys.O) {
      entity.getEvents().trigger("createOrder");
      return true;
    }
    
    if (keycode == Keys.E) {
      // Trigger an interaction attempt
      entity.getEvents().trigger(INTERACT, "default");
      return true;
    } else if (keycode == Keys.J) {
      // Trigger an attempt to combine existing items in a mixing station
      entity.getEvents().trigger(INTERACT, "combine");
      return true;
    } else if (keycode == Keys.K) {
      // Trigger an attempt to rotate inventory of a station to update item display
      entity.getEvents().trigger(INTERACT, "rotate");
      return true;
    } else if (keycode == Keys.Q) {
      // Attempt to trigger an interaction to chops
      entity.getEvents().trigger(INTERACT, "chop");
      isChopping = true;
    }

    if (!isInteracting) {
      switch (keycode) {
        case Keys.W:
          walkDirection.add(Vector2Utils.UP);
          triggerWalkEvent();
          return true;
        case Keys.A:
          walkDirection.add(Vector2Utils.LEFT);
          triggerWalkEvent();
          return true;
        case Keys.S:
          walkDirection.add(Vector2Utils.DOWN);
          triggerWalkEvent();
          return true;
        case Keys.D:
          walkDirection.add(Vector2Utils.RIGHT);
          triggerWalkEvent();
          return true;
        case Keys.G:
          ServiceLocator.getEntityService().getEvents().trigger("goodEnd");
          return true;
        case Keys.X:
          ServiceLocator.getEntityService().getEvents().trigger("badEnd");
          return true;
        case Keys.Z:
          ServiceLocator.getEntityService().getEvents().trigger("loseEnd");
          return true;
        case Keys.M:
          int day = 0;
          ServiceLocator.getEntityService().getMoralScreen().getEvents().trigger("triggerMoralScreen",day);
          return true;
        case Keys.P:
          ServiceLocator.getRandomComboService().deactivateUpgrade();
          ServiceLocator.getEntityService().getEvents().trigger("toggleEndDayScreen");
          return true;
        case Keys.B:
            ServiceLocator.getEntityService().getEvents().trigger("leaveEarly");
            return true;
        default:
          return false;
      }
    }

    return false;
  }


  /**
   * Triggers player events on specific keycodes.
   *
   * @return whether the input was processed
   * @see InputProcessor#keyUp(int)
   */
  @Override
  public boolean keyUp(int keycode) {
    keyFlags.put(keycode, 0);

    // If the 'Q' key is release we want to stop chopping
    // Also check if any other key but R is pressed too
    if (keycode != Keys.R && (keycode == Keys.Q || isChopping)) {
      // We know item exits and is patchable
      entity.getEvents().trigger(INTERACT, "stopChop");
      isChopping = false;
    }

    return switch (keycode) {
      case Keys.W, Keys.A, Keys.S, Keys.D -> {
        triggerWalkEvent();
        yield true;
      }
      default -> false;
    };
  }

  public void setWalkSpeed(float speed) {
    WALK_SPEED = speed;
  }

  private void triggerWalkEvent() {

    Vector2 lastDir = this.walkDirection.cpy();
    this.walkDirection = keysToVector().scl(WALK_SPEED);
    Directions dir = keysToDirection();
    if(dir == Directions.NONE)
    {
      entity.getEvents().trigger(WALK_STOP);
      entity.getEvents().trigger("walkStopAnimation", lastDir);
      return;
    }

    switch(dir) {
      case UP -> entity.getEvents().trigger("walkUp");
      case DOWN -> entity.getEvents().trigger("walkDown");
      case LEFT -> entity.getEvents().trigger("walkLeft");
      case RIGHT -> entity.getEvents().trigger("walkRight");
      case UP_LEFT -> entity.getEvents().trigger("walkUpLeft");
      case UP_RIGHT -> entity.getEvents().trigger("walkUpRight");
      case DOWN_RIGHT -> entity.getEvents().trigger("walkDownRight");
      case DOWN_LEFT -> entity.getEvents().trigger("walkDownLeft");
      default -> entity.getEvents().trigger(WALK_STOP);
    }
    entity.getEvents().trigger("walk",walkDirection);
  }

  private Vector2 keysToVector() {
    float xCom = 0f;
    float yCom = 0f;

    if (isPressed(Keys.D)) xCom += 1f;
    if (isPressed(Keys.A)) xCom -= 1f;
    if (isPressed(Keys.W)) yCom += 1f;
    if (isPressed(Keys.S)) yCom -= 1f;

    // Normalize the vector for diagonal movement
    float length = (float) Math.sqrt(xCom * xCom + yCom * yCom);
    if (length > 0) {
      xCom /= length;
      yCom /= length;
    }

    return new Vector2(xCom, yCom).scl(WALK_SPEED);
  }

  private boolean isPressed(int keycode) {
    return keyFlags.getOrDefault(keycode, 0) == 1;
  }

  private Directions keysToDirection() {
    boolean up = isPressed(Keys.W);
    boolean down = isPressed(Keys.S);
    boolean left = isPressed(Keys.A);
    boolean right = isPressed(Keys.D);

    if (up && right) return Directions.UP_RIGHT;
    if (up && left) return Directions.UP_LEFT;
    if (down && right) return Directions.DOWN_RIGHT;
    if (down && left) return Directions.DOWN_LEFT;
    if (up) return Directions.UP;
    if (down) return Directions.DOWN;
    if (right) return Directions.RIGHT;
    if (left) return Directions.LEFT;

    return Directions.NONE;
    }



  @Override
  public void create() {
    super.create();
    entity.getEvents().addListener("interactionEnd", this::whenInteractionEnds);
  }

  private void whenInteractionEnds() {
    isInteracting = false;
  }

  private enum Directions {
    NONE,
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT
  }


}

