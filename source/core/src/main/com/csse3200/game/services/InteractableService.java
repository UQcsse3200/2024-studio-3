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
    private final Map<Entity, Vector2> interactables = new HashMap<>();
    
    /**
     * Register an interactable enitity to the service
     * @param entity - the entity to register
     */
    public void registerEntity(Entity entity) {
        PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);

        if (physicsComponent == null) {
            return;
        }

        interactables.put(entity, physicsComponent.getBody().getPosition());
    }

    /**
     * Remove the entity from the interactables map
     * @param entity to be removed
     */
    public void unregisterEntity(Entity entity) {
        interactables.remove(entity);
    }

    /**
     * Clear all entities from the interactables map
     */
    public void clearEntities() {
        interactables.clear();
    }

    /**
     * Get the interactables map which returns an Entity and its position as
     * entries in a mao
     * @return the interactables map
     */
    public Map<Entity, Vector2> getInteractables() {
        return interactables;
    }

}
