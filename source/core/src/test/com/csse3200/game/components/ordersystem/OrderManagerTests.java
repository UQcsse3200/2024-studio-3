//package com.csse3200.game.components.ordersystem;
//
//import com.csse3200.game.components.npc.CustomerComponent;
//import com.csse3200.game.entities.Entity;
//import com.csse3200.game.events.EventHandler;
//import com.csse3200.game.extensions.GameExtension;
//import com.csse3200.game.services.ServiceLocator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//import com.csse3200.game.files.FileLoader;
//
///**
// * Unit tests for the OrderManager class.
// */
//@ExtendWith(GameExtension.class)
//@ExtendWith(MockitoExtension.class)
//class OrderManagerTest {
//
//    @BeforeEach
//    void setup() {
//
//    }
//
//    @Test
//    void getRecipeTest() {
//        OrderManager.loadRecipes();
//        Recipe recipe = OrderManager.getRecipe("bananaSplit");
//
//        assertNotNull(recipe, "Recipe should not be null");
//        assertEquals("bananaSplit", recipe.getName(), "Recipe name should be bananaSplit");
//    }
//}
