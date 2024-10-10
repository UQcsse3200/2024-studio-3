package com.csse3200.game.services;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.components.PhysicsComponent;

import java.util.HashMap;

/**
 * Class to register interactable objects to and to call when the player attempts
 * to interact with them. Uses a system to determine which object to interact with
 * based upon the player's proximity to the object and the type it is. i.e. a 
 * station will have more weight than a bench for instance
 */
public class InteractableService {

    // This assumes the position of the entity is static in the world
    private static final Map<Entity, Vector2> interactables = new HashMap<Entity, Vector2>();
    
    /**
     * Register an interactable enitity to the service
     * @param entity - the entity to register
     */
    public static void registerEntity(Entity entity) {
        PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);

        if (physicsComponent == null) {
            return;
        }

        interactables.put(entity, physicsComponent.getBody().getPosition());
    }

    /**
     * Get the player instance from the player service
     * @return the current player instance 
     */
    private static Entity getPlayer() {
        return ServiceLocator.getPlayerService().getPlayer();
    }

    /**
     * Get the closest entity to the player
     * @return the closest entity to the player
     */
    public static Entity getClosestEntity() {
        // Get the player and have a variable for the clostest entity
        Entity player = getPlayer();
        Entity closestEntity = null;

        // Get the player position
        Vector2 playerPosition = player.getComponent(PhysicsComponent.class).getBody().getPosition();
        float closestDistance = Float.MAX_VALUE;

        for (Map.Entry<Entity, Vector2> entry : interactables.entrySet()) {
            Entity entity = entry.getKey();
            Vector2 entityPosition = entry.getValue();

            float distance = playerPosition.dst(entityPosition);

            if (/*distance <= 1f &&*/distance < closestDistance) {
                closestDistance = distance;
                closestEntity = entity;
            }
        }

        return closestEntity;
    }


}
