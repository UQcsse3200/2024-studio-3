package com.csse3200.game.components.ordersystem;

import java.util.HashSet;
import java.util.Set;

public class ActiveAnimalsManager {
    private static ActiveAnimalsManager instance;
    private Set<String> activeAnimals;

    private ActiveAnimalsManager() {
        activeAnimals = new HashSet<>();
    }

    public static ActiveAnimalsManager getInstance() {
        if (instance == null) {
            instance = new ActiveAnimalsManager();
        }
        return instance;
    }

    public void addAnimal(String name) {
        activeAnimals.add(name);
        System.out.println("Active animals: " + activeAnimals);
    }

    public void removeAnimal(String name) {
        activeAnimals.remove(name);
        System.out.println("Active animals: " + activeAnimals);
    }

    public Set<String> getActiveAnimals() {
        return new HashSet<>(activeAnimals); // Return a copy to prevent external modifications
    }
}
