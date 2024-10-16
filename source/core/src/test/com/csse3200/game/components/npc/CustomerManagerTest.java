package com.csse3200.game.components.npc;

import com.csse3200.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerManagerTest {

    private Entity testCustomer;
    private String testOrderNumber = "12345";
    private static final Logger logger = Logger.getLogger(CustomerManager.class.getName());

    @BeforeEach
    public void setUp() {
        // Initialize a test customer entity
        testCustomer = new Entity();
        // Reset the CustomerManager map before each test
        CustomerManager.reset();
    }

    @Test
    public void testAddCustomer() {
        // Add a customer to the manager
        CustomerManager.addCustomer(testOrderNumber, testCustomer);
        // Verify the customer was added
        assertEquals(testCustomer, CustomerManager.getCustomerByOrder(testOrderNumber), 
                     "The customer should be added to the map");
    }

    @Test
    public void testGetCustomerByOrder() {
        // Add a customer and retrieve it
        CustomerManager.addCustomer(testOrderNumber, testCustomer);
        Entity retrievedCustomer = CustomerManager.getCustomerByOrder(testOrderNumber);
        // Verify that the retrieved customer matches the added one
        assertEquals(testCustomer, retrievedCustomer, 
                     "The retrieved customer should match the one that was added");
    }

    @Test
    public void testRemoveCustomerByOrder() {
        // Add a customer and then remove it
        CustomerManager.addCustomer(testOrderNumber, testCustomer);
        CustomerManager.removeCustomerByOrder(testOrderNumber);
        // Verify the customer is no longer in the map
        assertNull(CustomerManager.getCustomerByOrder(testOrderNumber), 
                   "The customer should be removed from the map");
    }

    @Test
    public void testReset() {
        // Add a customer, then reset the map
        CustomerManager.addCustomer(testOrderNumber, testCustomer);
        CustomerManager.reset();
        // Verify the map is empty
        assertNull(CustomerManager.getCustomerByOrder(testOrderNumber), 
                   "The map should be empty after reset");
    }

    @Test
    public void testPrintMessage() {
        // Add a customer and verify that printMessage() runs without errors
        CustomerManager.addCustomer(testOrderNumber, testCustomer);
        assertDoesNotThrow(() -> CustomerManager.printMessage(), 
                           "printMessage() should not throw an exception");
    }
}
