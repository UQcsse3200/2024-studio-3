package com.csse3200.game.components;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.services.ServiceLocator;

import java.util.Map;
/**
 * Finds the closest interactable object inside a range of the player's position.
 * This component is used everytime the player moves to update the nearby
 * interactable entities.
 */
public class SensorComponent extends Component {

    private static final float INTERACTION_RANGE = 1.15f;

    private Vector2 playerPosition;
    private Map<Entity, Vector2> interactables;

    @Override
    public void create() {
        playerPosition = entity.getComponent(PhysicsComponent.class).getBody().getPosition();
        interactables = ServiceLocator.getInteractableService().getInteractables();
    }
    
    /**
     * Updates the closest interactable object to the player.
     * @return the closest interactable object to the player.
     */
    public Entity getClosestInteractable() {
        Entity closestEntity = null;

        float closestDistance = Float.MAX_VALUE;

        for (Map.Entry<Entity, Vector2> entry : interactables.entrySet()) {
            Entity entity = entry.getKey();
            Vector2 entityPosition = entry.getValue();

            float distance = playerPosition.dst(entityPosition);

            if (distance <= INTERACTION_RANGE && distance < closestDistance) {
                closestDistance = distance;
                closestEntity = entity;
            }
        }

        return closestEntity;        
    }

}
