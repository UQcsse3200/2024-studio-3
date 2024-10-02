package com.csse3200.game.components.npc;

import java.util.HashMap;
import java.util.Map;

import com.csse3200.game.entities.Entity;

// This class is used to link a specific customer to a specific ticket by a unique order number.
public class CustomerManager {
    private static Map<String, Entity> orderToCustomerMap = new HashMap<>();

    public static void addCustomer(String orderNumber, Entity customer) {
        orderToCustomerMap.put(orderNumber, customer);
    }

    public static Entity getCustomerByOrder(String orderNumber) {
        return orderToCustomerMap.get(orderNumber);
    }

    public static void removeCustomerByOrder(String orderNumber) {
        orderToCustomerMap.remove(orderNumber);
    }
}
