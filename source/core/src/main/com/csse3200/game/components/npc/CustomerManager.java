package com.csse3200.game.components.npc;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.csse3200.game.entities.Entity;

// This class is used to link a specific customer to a specific ticket by a unique order number.
public class CustomerManager {
    private static final Map<String, Entity> orderToCustomerMap = new HashMap<>();
    private static final Logger logger = Logger.getLogger(CustomerManager.class.getName());

    public CustomerManager() {
        super();
    }
    /**
     * Add a customer to a specific order number
     * @param orderNumber The order number
     * @param customer The customer entity
     */
    public static void addCustomer(String orderNumber, Entity customer) {
        orderToCustomerMap.put(orderNumber, customer);
    }

    /**
     * Get the customer entity by the order number
     * @param orderNumber The order number
     * @return The customer entity
     */
    public static Entity getCustomerByOrder(String orderNumber) {
        return orderToCustomerMap.get(orderNumber);
    }

    /**
     * Remove the customer entity by the order number
     * @param orderNumber The order number
     */
    public static void removeCustomerByOrder(String orderNumber) {
        orderToCustomerMap.remove(orderNumber);
    }

    /**
     * Reset map
     */
    public static void reset() {
        orderToCustomerMap.clear();
    }

    public static void printMessage() {
        if(logger.isLoggable(Level.INFO))
            logger.info(orderToCustomerMap.toString());
    }
}
