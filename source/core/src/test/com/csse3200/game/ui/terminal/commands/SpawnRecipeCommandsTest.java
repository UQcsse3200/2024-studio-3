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

    // Setup method to initialise objects and mock behaviour before each test
    @BeforeEach
    void setUp() {
        spawnRecipeCommands = new SpawnRecipeCommands();

        // Clear exisiting services and set up mocked inventory and player inventories
        ServiceLocator.clear();
        inventoryComponent = mock(InventoryComponent.class);
        player = mock(Entity.class);
        
        // Conifigure the players inventory component to be retrieved when requested
        when(player.getComponent(InventoryComponent.class)).thenReturn(inventoryComponent);
        
        // Mocking the PlayerService and registering it in ServiceLocator
        PlayerService playerService = mock(PlayerService.class);
        when(playerService.getPlayer()).thenReturn(player);
        ServiceLocator.registerPlayerService(playerService); 

        // Mocking the static methods in the ItemFactory class
        mockedItemFactory = Mockito.mockStatic(ItemFactory.class);
    }

    @Test
    void testSpawnBananaSplit() {
        // Arrange the command arguments to simulate spawning a Banana Split
        ArrayList<String> args = new ArrayList<>();
        args.add("bananasplit");

        // Mock inventory state and item creation process, 
        // assuming the inventory is empty 
        when(inventoryComponent.getSize()).thenReturn(0);
        when(inventoryComponent.isFull()).thenReturn(false);

        // Mock the creation of items in ItemFactory
        Entity strawberry = mock(Entity.class);
        Entity chocolate = mock(Entity.class);
        Entity banana = mock(Entity.class);
        Entity bananaSplitMeal = mock(Entity.class);

        // Configure ingredient components for each mocked entity
        when(strawberry.getComponent(IngredientComponent.class)).thenReturn(mock(IngredientComponent.class));
        when(chocolate.getComponent(IngredientComponent.class)).thenReturn(mock(IngredientComponent.class));
        when(banana.getComponent(IngredientComponent.class)).thenReturn(mock(IngredientComponent.class));

        // Configure the banana split meal entity with its component
        when(bananaSplitMeal.getComponent(MealComponent.class)).thenReturn(mock(MealComponent.class));

        // Stubbing the ItemFactory methods to return the mocked items and meal
        mockedItemFactory.when(() -> ItemFactory.createBaseItem("strawberry")).thenReturn(strawberry);
        mockedItemFactory.when(() -> ItemFactory.createBaseItem("chocolate")).thenReturn(chocolate);
        mockedItemFactory.when(() -> ItemFactory.createBaseItem("banana")).thenReturn(banana);
        mockedItemFactory.when(() -> ItemFactory.createMeal(eq("bananaSplit"), anyList())).thenReturn(bananaSplitMeal);


        // Act by executing the spawn action with the arguments
        boolean result = spawnRecipeCommands.action(args);

        // Assert that the item was successfully spawned and added to the inventory
        assertTrue(result);
        verify(inventoryComponent).addItem(any(MealComponent.class));
    }

    @Test
    void testInvalidArgument() {
        // Arrange the invalid recipe argument
        ArrayList<String> args = new ArrayList<>();
        args.add("invalidRecipe");

        // Act by executing the command with an invalid recipe
        boolean result = spawnRecipeCommands.action(args);

        // Assert that the action failed and no item was added to the inventory
        assertFalse(result);
        verify(inventoryComponent, never()).addItem(any(ItemComponent.class));
    }

    @Test
    void testSpawnFruitSalad() {
        // Arrange the command arguments to simulate spawning a Fruit Salad
        ArrayList<String> args = new ArrayList<>();
        args.add("fruitSalad");

        // Mock the inventory state
        when(inventoryComponent.getSize()).thenReturn(0);
        when(inventoryComponent.isEmpty()).thenReturn(false);

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

        // Act by executing the soawn action with the arguments
        boolean result = spawnRecipeCommands.action(args);

        // Assert that the fruit salad successfully spawned and added to inventory
        assertTrue(result);
        verify(inventoryComponent).addItem(any(MealComponent.class)); 
    }

    @Test
    void testNoArgs() {
        // Arrange with no command arguments passed
        ArrayList<String> args = new ArrayList<>(); // No arguments passed

        // Act by executing the command with no arguments
        boolean result = spawnRecipeCommands.action(args);

        // Assert that the action failed due to missing arguments
        assertFalse(result); // Should return false due to missing arguments
        verify(inventoryComponent, never()).addItem(any(ItemComponent.class)); // No item should be added
    }

    @AfterEach
    void tearDown() {
        mockedItemFactory.close();
        ServiceLocator.clear();
    }
}

