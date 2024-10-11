package com.csse3200.game.components;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.services.ServiceLocator;

import java.util.Map;
/**
 * Finds the closest interactable object inside a range of the player
 */
public class SensorComponent extends Component {

    Vector2 playerPosition;
    Map<Entity, Vector2> interactables;

    @Override
    public void create() {
        playerPosition = entity.getComponent(PhysicsComponent.class).getBody().getPosition();
        interactables = ServiceLocator.getInteractableService().getInteractables();
    }
    
    public Entity getClosestInteractable() {
        Entity closestEntity = null;

        float closestDistance = Float.MAX_VALUE;

        for (Map.Entry<Entity, Vector2> entry : interactables.entrySet()) {
            Entity entity = entry.getKey();
            Vector2 entityPosition = entry.getValue();

            float distance = playerPosition.dst(entityPosition);

            if (distance <= 1.15f && distance < closestDistance) {
                closestDistance = distance;
                closestEntity = entity;
            }
        }

        return closestEntity;        
    }

}
