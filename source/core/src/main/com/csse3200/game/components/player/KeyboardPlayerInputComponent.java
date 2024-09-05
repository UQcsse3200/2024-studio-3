package com.csse3200.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.utils.math.Vector2Utils;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {
  private final Vector2 walkDirection = Vector2.Zero.cpy();
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
    // A temporary key to auto trigger cutscenes for testing
    if (keycode == Keys.L) {
      System.out.println("WHOOOOOOOOOOO");

        // Need to have some sort of event trigger
        // then when the trigger is... triggered GameArea does the ending
        // this will allow me to pass the necessary variables directly

      return true;
    }

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
    if (!isInteracting) {
      switch (keycode) {
        case Keys.W:
          walkDirection.sub(Vector2Utils.UP);
          triggerWalkEvent();
          return true;
        case Keys.A:
          walkDirection.sub(Vector2Utils.LEFT);
          triggerWalkEvent();
          return true;
        case Keys.S:
          walkDirection.sub(Vector2Utils.DOWN);
          triggerWalkEvent();
          return true;
        case Keys.D:
          walkDirection.sub(Vector2Utils.RIGHT);
          triggerWalkEvent();
          return true;
      }
    }
    return false;
  }

  private void triggerWalkEvent() {
    if (walkDirection.epsilonEquals(Vector2.Zero)) {
      entity.getEvents().trigger("walkStop");
    } else {
      entity.getEvents().trigger("walk", walkDirection);
    }
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


}

