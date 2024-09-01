package com.csse3200.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.utils.math.Vector2Utils;

import java.util.HashMap;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {
  private static final float ROOT2INV = 1f / (float) Math.sqrt(2f);
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private static final float WALK_SPEED = 1f;
  private static HashMap<Integer, Integer> keyFlags = new HashMap<>();
  private static final String WALKSTOP = "walkStop";
  private Entity player;

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
    if (keycode == Keys.E) {
      // Trigger an interaction attempt
      entity.getEvents().trigger("interact");
      return true;
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
      }
    }

    if (keycode == Keys.SPACE) {
      entity.getEvents().trigger("attack");
      return true;
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
    return switch (keycode) {
      case Keys.W, Keys.S, Keys.A, Keys.D -> {
        triggerWalkEvent();
        yield true;
      }
      default -> false;
    };
  }

  private void triggerWalkEvent() {

    Vector2 lastDir = this.walkDirection.cpy();
    this.walkDirection = keysToVector().scl(WALK_SPEED);
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

    return new Vector2(xCom, yCom).scl(WALK_SPEED);
  }

  private boolean isPressed(int keycode) {
    return keyFlags.getOrDefault(keycode, 0) == 1;
  }

  private Directions keysToDirection() {
    int dirFlags = 0b0101 +
            ((isPressed(Keys.W) ? 1 : 0) << 2) - ((isPressed(Keys.S) ? 1 : 0) << 2) +
            (isPressed(Keys.D) ? 1 : 0) - (isPressed(Keys.A) ? 1 : 0);
    return switch (dirFlags) {
      case 0b1001 -> Directions.UP;
      case 0b1010 -> Directions.UPRIGHT;
      case 0b1000 -> Directions.UPLEFT;
      case 0b0001 -> Directions.DOWN;
      case 0b0010 -> Directions.DOWNRIGHT;
      case 0b0000 -> Directions.DOWNLEFT;
      case 0b0110 -> Directions.RIGHT;
      case 0b0100 -> Directions.LEFT;
      default -> Directions.NONE;
    };

  }


  @Override
  public void create() {
    super.create();
    entity.getEvents().addListener("interactionEnd", this::whenInteractionEnds);
    entity.getEvents().addListener("startInteraction", this::startInteraction);
  }

  /**
   * Called when the player is attempting to interact and there is also a station that can be interacted with
   */
  private void startInteraction() {
    isInteracting = !isInteracting;
  
    if (isInteracting) {
      walkDirection.set(Vector2.Zero); 
      triggerWalkEvent();             
    }
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

