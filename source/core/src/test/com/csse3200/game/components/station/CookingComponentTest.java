package com.csse3200.game.components.station;

import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.GameTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.ArrayList;

@ExtendWith(GameExtension.class)
public class CookingComponentTest {
    private CookingComponent cookingComponent;

    @BeforeEach
    void init() {
        cookingComponent = new CookingComponent("OVEN");
        cookingComponent.create();
    }

    @Test
    void testAddItem() {
        // Testing to be implemented
    }

    @Test
    void testRemoveItem() {
        // Testing to be implemented
    }

    @Test
    void testGetStationType() {
        // Testing to be implemented
    }

    @Test
    void testIsCooking() {
        // Testing to be implemented
    }

    @Test
    void testGetCookingTime() {
        // Testing to be implemented
    }
}
