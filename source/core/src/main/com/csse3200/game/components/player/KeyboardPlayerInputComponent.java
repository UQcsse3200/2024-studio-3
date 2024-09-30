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
  //private static final float ROOT2INV = 1f / (float) Math.sqrt(2f);
  private Vector2 walkDirection = Vector2.Zero.cpy();
  public float walkSpeed = 1f;
  private static HashMap<Integer, Integer> keyFlags = new HashMap<>();
  private static final String WALKSTOP = "walkStop";
  //private Entity player;
  private boolean isChopping;

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

    // Check if were chopping and we recieve a different key
    // If so stop chopping
    // This in effect freezes the player
    if (isChopping && keycode != Keys.Q) {
      // We know item exits and is choppable
      entity.getEvents().trigger("interact", "stopChop");
    }

    if (keycode == Keys.O) {
      entity.getEvents().trigger("createOrder");
      return true;
    }
    
    if (keycode == Keys.E) {
      // Trigger an interaction attempt
      entity.getEvents().trigger("interact", "default");
      return true;
    } else if (keycode == Keys.J) {
      // Trigger an attempt to combine existing items in a mixing station
      entity.getEvents().trigger("interact", "combine");
      return true;
    } else if (keycode == Keys.K) {
      // Trigger an attempt to rotate inventory of a station to update item display
      entity.getEvents().trigger("interact", "rotate");
      return true;
    } else if (keycode == Keys.Q) {
      // Attempt to trigger an interaction to chops
      entity.getEvents().trigger("interact", "chop");
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
        case Keys.M:
          int day = 0;
          ServiceLocator.getEntityService().getMoralScreen().getEvents().trigger("triggerMoralScreen",day);
          return true;
        case Keys.P:
          ServiceLocator.getEntityService().getEvents().trigger("toggleEndDayScreen");
          return true;
          case Keys.L:
              ServiceLocator.getEntityService().getEvents().trigger("leaveEarly");
              return true;
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
      // We know item exits and is choppable
      entity.getEvents().trigger("interact", "stopChop");
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
    walkSpeed = speed;
  }

  private void triggerWalkEvent() {

    Vector2 lastDir = this.walkDirection.cpy();
    this.walkDirection = keysToVector().scl(walkSpeed);
    Directions dir = keysToDirection();
    if(dir == Directions.NONE)
    {
      entity.getEvents().trigger(WALKSTOP);
      entity.getEvents().trigger("walkStopAnimation", lastDir);
      return;
    }

    switch(dir) {
      case UP -> {
        entity.getEvents().trigger("walkUp");
      }
      case DOWN -> {
        entity.getEvents().trigger("walkDown");
      }
      case LEFT -> {
        entity.getEvents().trigger("walkLeft");
      }
      case RIGHT -> {
        entity.getEvents().trigger("walkRight");
      }
      case UPLEFT -> {
        entity.getEvents().trigger("walkUpLeft");
      }
      case UPRIGHT -> {
        entity.getEvents().trigger("walkUpRight");
      }
      case DOWNRIGHT -> {
        entity.getEvents().trigger("walkDownRight");
      }
      case DOWNLEFT -> {
        entity.getEvents().trigger("walkDownLeft");
      }
      default -> {
        entity.getEvents().trigger(WALKSTOP);
      }
    }
    if (entity != null)
    {
      entity.getEvents().trigger("walk",walkDirection);
    }




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

    return new Vector2(xCom, yCom).scl(walkSpeed);
  }

  private boolean isPressed(int keycode) {
    return keyFlags.getOrDefault(keycode, 0) == 1;
  }

  private Directions keysToDirection() {
    boolean up = isPressed(Keys.W);
    boolean down = isPressed(Keys.S);
    boolean left = isPressed(Keys.A);
    boolean right = isPressed(Keys.D);

    if (up && right) return Directions.UPRIGHT;
    if (up && left) return Directions.UPLEFT;
    if (down && right) return Directions.DOWNRIGHT;
    if (down && left) return Directions.DOWNLEFT;
    if (up) return Directions.UP;
    if (down) return Directions.DOWN;
    if (right) return Directions.RIGHT;
    if (left) return Directions.LEFT;

    return Directions.NONE;
    };



  @Override
  public void create() {
    super.create();
    entity.getEvents().addListener("interactionEnd", this::whenInteractionEnds);
    // Meant to restrict movement on some stations, not a current feature and clashing
    // with existing system
    //entity.getEvents().addListener("startInteraction", this::startInteraction);
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
    UPLEFT,
    UPRIGHT,
    DOWNLEFT,
    DOWNRIGHT
  }


}

