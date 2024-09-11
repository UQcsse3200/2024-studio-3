package com.csse3200.game.services;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class CustomerMovementService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerMovementService.class);
    private final Map<Integer, Vector2> customerMovementTargets = new HashMap<>();
    private final EventHandler customerEventHandler;

    public CustomerMovementService() {
        customerEventHandler = new EventHandler();
    }

    public EventHandler getEvents() {
        return customerEventHandler;
    }
    /**
     * Registers a new customer movement with their target position.
     *
     * @param customerId the ID of the customer
     * @param targetPos the target position
     */
    public void registerMovement(int customerId, Vector2 targetPos) {
        customerMovementTargets.put(customerId, targetPos);
        logger.debug("Registered movement for customer {} to target position {}", customerId, targetPos);
    }

    /**
     * Updates the movement target position for a customer.
     *
     * @param customerId the ID of the customer
     * @param targetPos the new target position
     */
    public void updateMovement(int customerId, Vector2 targetPos) {
        if (customerMovementTargets.containsKey(customerId)) {
            customerMovementTargets.put(customerId, targetPos);
            logger.debug("Updated movement for customer {} to new target position {}", customerId, targetPos);
        } else {
            logger.warn("Customer {} not found, cannot update movement", customerId);
        }
    }

    /**
     * Removes the movement registration for a customer.
     *
     * @param customerId the ID of the customer
     */
    public void removeMovement(int customerId) {
        if (customerMovementTargets.remove(customerId) != null) {
            logger.debug("Removed movement registration for customer {}", customerId);
        } else {
            logger.warn("Customer {} not found, cannot remove movement", customerId);
        }
    }

    /**
     * Gets the current target position for a customer.
     *
     * @param customerId the ID of the customer
     * @return the target position, or null if no movement is registered for the customer
     */
    public Vector2 getMovementTarget(int customerId) {
        return customerMovementTargets.get(customerId);
    }
}
