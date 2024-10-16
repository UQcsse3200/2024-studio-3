package com.csse3200.game.components.npc;

import java.util.HashMap;
import java.util.Map;

import com.csse3200.game.entities.Entity;

// This class is used to link a specific customer to a specific ticket by a unique order number.
public class CustomerManager {
    private static Map<String, Entity> orderToCustomerMap = new HashMap<>();

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
}
