package com.csse3200.game.components.interaction;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
class InteractionComponentTest {
  @BeforeEach
  void beforeEach() {
    ServiceLocator.registerPhysicsService(new PhysicsService());
  }

  @Test
  public void shouldBeSensor() {
    Entity entity = createEntity();
    InteractionComponent component = entity.getComponent(InteractionComponent.class);

    assertTrue(component.getFixture().isSensor());
  }

  @Test
  public void shouldBeInteractable() {
    Entity entity = createEntity();
    InteractionComponent component = entity.getComponent(InteractionComponent.class);
    short interactableLayer = PhysicsLayer.INTERACTABLE;
    assertTrue(component.getLayer() == interactableLayer);
    assertTrue(PhysicsLayer.contains(interactableLayer, component.getFixture().getFilterData().categoryBits));
  }

  @Test
  public void shouldHaveFixture() {
    Entity entity = createEntity();
    InteractionComponent component = entity.getComponent(InteractionComponent.class);
    assertNotNull(component.getFixture());
  }

  Entity createEntity() {
    Entity entity = new Entity();
    entity.addComponent(new PhysicsComponent());
    InteractionComponent component = new InteractionComponent(PhysicsLayer.INTERACTABLE);
    entity.addComponent(component);
    entity.create();
    return entity;
  }
}