package com.csse3200.game.components.ordersystem;

import java.util.HashSet;
import java.util.Set;

public class ActiveAnimalsManager {
    // Use a static inner helper class for thread-safe lazy initialization
    private static class SingletonHelper {
        private static final ActiveAnimalsManager INSTANCE = new ActiveAnimalsManager();
    }

    private final Set<String> activeAnimals;

    // Private constructor to prevent external instantiation
    private ActiveAnimalsManager() {
        activeAnimals = new HashSet<>();
    }

    // Method to return the singleton instance
    public static ActiveAnimalsManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void addAnimal(String name) {
        activeAnimals.add(name);
    }

    public void removeAnimal(String name) {
        activeAnimals.remove(name);
    }

    public Set<String> getActiveAnimals() {
        return new HashSet<>(activeAnimals); // Return a copy to prevent external modifications
    }
}
