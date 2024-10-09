package com.csse3200.game.ui.terminal.commands;

import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.MealComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ServiceLocator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class SpawnRecipeCommandsTest {

    private SpawnRecipeCommands spawnRecipeCommands;
    private InventoryComponent inventoryComponent;
    private Entity player;
    private MockedStatic<ItemFactory> mockedItemFactory;

    @BeforeEach
    void setUp() {
        spawnRecipeCommands = new SpawnRecipeCommands();

        ServiceLocator.clear();
        // Mocking the player's inventory and the service locator to retrieve the player
        inventoryComponent = mock(InventoryComponent.class);
        player = mock(Entity.class);
        
        when(player.getComponent(InventoryComponent.class)).thenReturn(inventoryComponent);
        
        PlayerService playerService = mock(PlayerService.class);
        when(playerService.getPlayer()).thenReturn(player);
        ServiceLocator.registerPlayerService(playerService); // Mocking ServiceLocator for player service

        mockedItemFactory = Mockito.mockStatic(ItemFactory.class);
    }

    @Test
    void testSpawnBananaSplit() {
        // Arrange
        ArrayList<String> args = new ArrayList<>();
        args.add("bananasplit");

        // Mock inventory size and behavior
        when(inventoryComponent.getSize()).thenReturn(0); // Assume inventory is empty

        // Mock the creation of items in ItemFactory
        Entity strawberry = mock(Entity.class);
        Entity chocolate = mock(Entity.class);
        Entity banana = mock(Entity.class);
        Entity bananaSplitMeal = mock(Entity.class);

        // Mock the ingredient components
        when(strawberry.getComponent(IngredientComponent.class)).thenReturn(mock(IngredientComponent.class));
        when(chocolate.getComponent(IngredientComponent.class)).thenReturn(mock(IngredientComponent.class));
        when(banana.getComponent(IngredientComponent.class)).thenReturn(mock(IngredientComponent.class));

        // Mock meal creation
        when(bananaSplitMeal.getComponent(MealComponent.class)).thenReturn(mock(MealComponent.class));

        // Stubbing the ItemFactory methods
        mockedItemFactory.when(() -> ItemFactory.createBaseItem("strawberry")).thenReturn(strawberry);
        mockedItemFactory.when(() -> ItemFactory.createBaseItem("chocolate")).thenReturn(chocolate);
        mockedItemFactory.when(() -> ItemFactory.createBaseItem("banana")).thenReturn(banana);
        mockedItemFactory.when(() -> ItemFactory.createMeal(eq("bananaSplit"), anyList())).thenReturn(bananaSplitMeal);


        // Act
        boolean result = spawnRecipeCommands.action(args);

        // Assert
        assertTrue(result); // Expect true since it's a valid recipe
        verify(inventoryComponent).addItem(any(MealComponent.class)); // Ensure item was added to inventory
    }

    @Test
    void testInvalidArgument() {
        // Arrange
        ArrayList<String> args = new ArrayList<>();
        args.add("invalidRecipe");

        // Act
        boolean result = spawnRecipeCommands.action(args);

        // Assert
        assertFalse(result); // Should return false due to invalid recipe
        verify(inventoryComponent, never()).addItem(any(ItemComponent.class)); // Ensure no items were added
    }
/*
    @Test
    void testRemoveExistingItemFromInventory() {
        // Arrange
        ArrayList<String> args = new ArrayList<>();
        args.add("steakmeal");

        // Mock inventory size to simulate that it's not empty
        when(inventoryComponent.getSize()).thenReturn(1); // Assume there's already an item in the inventory

        // Act
        boolean result = spawnRecipeCommands.action(args);

        // Assert
        assertTrue(result);
        verify(inventoryComponent).removeItem(); // Ensure the existing item was removed
    }
*/
    @Test
    void testSpawnFruitSalad() {
        // Arrange
        ArrayList<String> args = new ArrayList<>();
        args.add("fruitSalad");

        // Mock the inventory
        when(inventoryComponent.getSize()).thenReturn(0);

        // Mock the creation of items in ItemFactory
        Entity banana = mock(Entity.class);
        Entity strawberry = mock(Entity.class);
        Entity fruitSaladMeal = mock(Entity.class);

        // Mock ingredient components
        when(banana.getComponent(IngredientComponent.class)).thenReturn(mock(IngredientComponent.class));
        when(strawberry.getComponent(IngredientComponent.class)).thenReturn(mock(IngredientComponent.class));

        // Mock meal creation
        when(fruitSaladMeal.getComponent(MealComponent.class)).thenReturn(mock(MealComponent.class));

        // Stubbing the ItemFactory methods
        mockedItemFactory.when(() -> ItemFactory.createBaseItem("banana")).thenReturn(banana);
        mockedItemFactory.when(() -> ItemFactory.createBaseItem("strawberry")).thenReturn(strawberry);
        mockedItemFactory.when(() -> ItemFactory.createMeal(eq("fruitSalad"), anyList())).thenReturn(fruitSaladMeal);

        // Act
        boolean result = spawnRecipeCommands.action(args);

        // Assert
        assertTrue(result);
        verify(inventoryComponent).addItem(any(MealComponent.class)); // Ensure the fruit salad was added
    }

    @Test
    void testNoArgs() {
        // Arrange
        ArrayList<String> args = new ArrayList<>(); // No arguments passed

        // Act
        boolean result = spawnRecipeCommands.action(args);

        // Assert
        assertFalse(result); // Should return false due to missing arguments
        verify(inventoryComponent, never()).addItem(any(ItemComponent.class)); // No item should be added
    }

    @AfterEach
    void tearDown() {
        mockedItemFactory.close();
        ServiceLocator.clear();
    }
}
