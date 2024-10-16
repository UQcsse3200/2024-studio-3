package com.csse3200.game.components.player;

import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.badlogic.gdx.Input.Keys;


@ExtendWith(GameExtension.class)
class KeyboardPlayerInputComponentTest {

  private KeyboardPlayerInputComponent inputComponent;
  private Entity entityMock;
  private EventHandler eventHandlerMock;

  @BeforeEach
  void setUp() {
    entityMock = mock(Entity.class);
    eventHandlerMock = mock(EventHandler.class);
    when(entityMock.getEvents()).thenReturn(eventHandlerMock);

    inputComponent = new KeyboardPlayerInputComponent();
    inputComponent.setEntity(entityMock);
  }
  @Test
  void shouldTriggerDiagonalWalkUpRight() {
    inputComponent.keyDown(Keys.W);
    inputComponent.keyDown(Keys.D);
    verify(eventHandlerMock).trigger("walkUpRight");
  }
  @Test
  void shouldTriggerDiagonalWalkDownLeft() {
    inputComponent.keyDown(Keys.S);
    inputComponent.keyDown(Keys.A);
    verify(eventHandlerMock).trigger("walkDownLeft");
  }

  @Test
  void shouldIgnoreNonMovementKeysWhileMoving() {
    inputComponent.keyDown(Keys.W); // Start moving up
    verify(eventHandlerMock, times(1)).trigger("walkUp");

    inputComponent.keyDown(Keys.F); // Press an unrelated key (F)
    verify(eventHandlerMock, never()).trigger("walkStop"); // Movement should continue

    inputComponent.keyUp(Keys.W); // Release W, stop moving
    verify(eventHandlerMock, times(1)).trigger("walkStop");
  }

  @Test
  void shouldTriggerInteract() {
    inputComponent.keyDown(Keys.E);
    verify(eventHandlerMock, times(1)).trigger("interact", "default");
  }

  @Test
  void shouldTriggerCombine() {
    inputComponent.keyDown(Keys.J);
    verify(eventHandlerMock, times(1)).trigger("interact", "combine");
  }

  @Test
  void shouldTriggerRotate() {
    inputComponent.keyDown(Keys.K);
    verify(eventHandlerMock, times(1)).trigger("interact", "rotate");
  }

  @Test
  void shouldStopOnButtonRelease() {
    inputComponent.keyDown(Keys.W);
    inputComponent.keyUp(Keys.W);
    verify(eventHandlerMock).trigger("walkStop");

    inputComponent.keyDown(Keys.A);
    inputComponent.keyUp(Keys.A);
    verify(eventHandlerMock, times(2)).trigger("walkStop");
  }

}
