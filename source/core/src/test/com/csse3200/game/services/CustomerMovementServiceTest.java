package com.csse3200.game.services;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;


class CustomerMovementServiceTest {

    private CustomerMovementService customerMovementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerMovementService = new CustomerMovementService();
    }

    @Test
    void testRegisterMovement() {
        int customerId = 1;
        Vector2 targetPos = new Vector2(10, 20);

        customerMovementService.registerMovement(customerId, targetPos);

        assertEquals(targetPos, customerMovementService.getMovementTarget(customerId),
                "The target position should be correctly registered.");
    }

    @Test
    void testUpdateMovement() {
        int customerId = 2;
        Vector2 initialTarget = new Vector2(30, 40);
        Vector2 newTarget = new Vector2(50, 60);

        customerMovementService.registerMovement(customerId, initialTarget);
        customerMovementService.updateMovement(customerId, newTarget);

        assertEquals(newTarget, customerMovementService.getMovementTarget(customerId),
                "The target position should be correctly updated.");
    }

    @Test
    void testUpdateMovementCustomerNotFound() {
        int customerId = 3;
        Vector2 newTarget = new Vector2(70, 80);

        customerMovementService.updateMovement(customerId, newTarget);

        assertNull(customerMovementService.getMovementTarget(customerId),
                "The target position should remain null if the customer is not found.");
    }

    @Test
    void testRemoveMovement() {
        int customerId = 4;
        Vector2 targetPos = new Vector2(90, 100);

        customerMovementService.registerMovement(customerId, targetPos);
        customerMovementService.removeMovement(customerId);

        assertNull(customerMovementService.getMovementTarget(customerId),
                "The target position should be null after removal.");
    }

    @Test
    void testRemoveMovementCustomerNotFound() {
        int customerId = 5;

        customerMovementService.removeMovement(customerId);

        assertNull(customerMovementService.getMovementTarget(customerId),
                "The target position should remain null if the customer was not registered.");
    }

    @Test
    void testGetMovementTarget() {
        int customerId = 6;
        Vector2 targetPos = new Vector2(110, 120);

        customerMovementService.registerMovement(customerId, targetPos);

        assertEquals(targetPos, customerMovementService.getMovementTarget(customerId),
                "The target position should be correctly retrieved.");
    }
}
