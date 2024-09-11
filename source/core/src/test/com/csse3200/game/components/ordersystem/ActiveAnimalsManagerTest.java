package com.csse3200.game.components.ordersystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ActiveAnimalsManagerTest {

    private ActiveAnimalsManager manager;

    @BeforeEach
    void setUp() {
        manager = ActiveAnimalsManager.getInstance();
        // Clear the set before each test to ensure test independence
        manager.getActiveAnimals().clear();
    }

    @Test
    void testSingletonInstance() {
        ActiveAnimalsManager anotherInstance = ActiveAnimalsManager.getInstance();
        assertSame(manager, anotherInstance, "There should only be one instance of ActiveAnimalsManager");
    }

    @Test
    void testAddAnimal() {
        manager.addAnimal("Monkey");
        Set<String> activeAnimals = manager.getActiveAnimals();
        assertTrue(activeAnimals.contains("Monkey"), "The animal should be added to the active animals set");
    }

    @Test
    void testRemoveAnimal() {
        manager.addAnimal("Gorilla");
        manager.removeAnimal("Gorilla");
        Set<String> activeAnimals = manager.getActiveAnimals();
        assertFalse(activeAnimals.contains("Gorilla"), "The animal should be removed from the active animals set");
    }

    @Test
    void testGetActiveAnimals() {
        manager.addAnimal("Cat");
        Set<String> activeAnimals = manager.getActiveAnimals();
        assertEquals(Set.of("Cat"), activeAnimals, "The active animals set should contain the added animal");
    }
}
