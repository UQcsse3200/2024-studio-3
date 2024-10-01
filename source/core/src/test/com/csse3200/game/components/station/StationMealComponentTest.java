package com.csse3200.game.components.station;

import com.csse3200.game.components.items.*;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class StationMealComponentTest {
    protected StationMealComponent mealHandler;
    protected InventoryComponent stationInventory;
    protected InventoryComponent playerInventory;
    protected Entity mockEntity;
    protected Entity mockStation;
    protected Entity mockMealEntity;
    protected EventHandler mockStationEvents;
    protected EventHandler mockPlayerEvents;
    protected EventHandler mockMealEventHandler;
    protected GameTime mockTime;
    protected InventoryDisplay inventoryDisplay;
    protected ArrayList<String> acceptableItems;

    @BeforeEach
    void setUp() {
        // Clear service locator before each
        ServiceLocator.clear();

        // Set-up services for Item entity creation
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        // Set up mock time source
        mockTime = mock(GameTime.class);
        ServiceLocator.registerTimeSource(mockTime);

        // mock the entities and events
        mockEntity = mock(Entity.class);
        mockStation = mock(Entity.class);
        mockMealEntity = mock(Entity.class);
        mockStationEvents = mock(EventHandler.class);
        mockMealEventHandler = mock(EventHandler.class);

        // and the inventories
        playerInventory = new InventoryComponent(1);
        stationInventory = new InventoryComponent(4); // initialised to 4 atm

        // and inventoryDisplay (not actually used in code so is just here as a placeholder I guess)
        inventoryDisplay = new InventoryDisplay();

        // map calls to entities to return correct things
        when(mockEntity.getEvents()).thenReturn(mockPlayerEvents);
        when(mockStation.getEvents()).thenReturn(mockStationEvents);
        when(mockMealEntity.getEvents()).thenReturn(mockMealEventHandler);
        when(mockEntity.getComponent(InventoryComponent.class)).thenReturn(playerInventory);
        when(mockStation.getComponent(InventoryComponent.class)).thenReturn(stationInventory);
        when(mockMealEntity.getComponent(InventoryComponent.class)).thenReturn(stationInventory);
        when(mockStation.getComponent(StationMealComponent.class)).thenReturn(mealHandler);

        // Fake a useless recourse service
        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(null);
        ServiceLocator.registerResourceService(mockResourceService);

        // Set up time
        when(mockTime.getTime()).thenReturn(1000L, 10000L);

        // all acceptable items in the game (only ingredients might need to change)
        acceptableItems = new ArrayList<>();
        acceptableItems.add("fish");
        acceptableItems.add("banana");
        acceptableItems.add("beef");
        acceptableItems.add("acai");
        acceptableItems.add("lettuce");
        acceptableItems.add("cucumber");
        acceptableItems.add("tomato");
        acceptableItems.add("strawberry");
        acceptableItems.add("chocolate");

        // initialise the station meal component
        mealHandler = new StationMealComponent("combining", acceptableItems);

        // assign mock meal entity to mealHandler
        mealHandler.setEntity(mockMealEntity);
        mealHandler.create();
    }

    @Test
    void shouldSetAcceptableItems() {
        ArrayList<String> actualItems = mealHandler.acceptableItems;
        assertEquals(acceptableItems, actualItems);
    }

    @Test
    void shouldSetStationType() {
        assertEquals("combining", mealHandler.getType());
    }

    @Test
    void shouldAcceptItem() {
        ItemComponent item = new ItemComponent("banana", ItemType.BANANA, 1);
        assertTrue(mealHandler.isItemAccepted(item));
    }

    // note there is no shouldntAcceptItem() since isItemAccepted() always returns true

    @Test
    void tooLittleShouldntMakeMeal() {
        ItemComponent banana = new ItemComponent("lettuce", ItemType.LETTUCE, 1);
        playerInventory.addItem(banana);
        // call the combine function to "attempt" to combine
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of a meal type
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item instanceof MealComponent) {
                // if it exists, fail
                fail();
            }
        }
        // otherwise all good
        assertTrue(true);
    }

    @Test
    void shouldntMakeMealWhenJustAdding() {
        // mock items to be added (that make a valid meal)
        ItemComponent item1 = new ItemComponent("banana", ItemType.BANANA, 1);
        ItemComponent item2 = new ItemComponent("strawberry", ItemType.STRAWBERRY, 1);
        // pretend ingredients were added to station through interaction
        stationInventory.addItem(item1);
        stationInventory.addItem(item2);
        // check if any component in inventory is of a meal type
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item instanceof MealComponent) {
                // if it exists, fail
                fail();
            }
        }
        // otherwise all good
        assertTrue(true);
    }

    @Test
    void incorrectIngrsShouldntMakeMeal() {
        // mock items to be added
        ItemComponent item1 = new ItemComponent("banana", ItemType.BANANA, 1);
        ItemComponent item2 = new ItemComponent("lettuce", ItemType.LETTUCE, 1);
        // add items and mock keypress for combining
        stationInventory.addItem(item1);
        stationInventory.addItem(item2);
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of a meal type
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item instanceof MealComponent) {
                // if it exists, fail
                fail();
            }
        }
        // otherwise all good
        assertTrue(true);
    }

    @Test
    void shouldMakeAcaiBowl() {
        // acai banana
        ItemComponent acai = new IngredientComponent("acai", ItemType.ACAI, 1, 0, 0, "unknown");
        ItemComponent banana = new IngredientComponent("banana", ItemType.BANANA, 1, 0, 0, "unknown");
        // add items and mock keypress for combining
        stationInventory.addItem(acai);
        stationInventory.addItem(banana);
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of an acai bowl meal type
        boolean found = false;
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item != null && item.getItemName().equals("acai bowl")) {
                found = true;
            }
        }
        // assert true if found
        assertTrue(found);
    }

    @Test
    void shouldMakeBananaSplit() {
        // banana strawberry chocolate
        ItemComponent strawberry = new IngredientComponent("strawberry", ItemType.STRAWBERRY, 1, 0, 0, "unknown");
        ItemComponent banana = new IngredientComponent("banana", ItemType.BANANA, 1, 0, 0, "unknown");
        ItemComponent chocolate = new IngredientComponent("chocolate", ItemType.ACAI, 1, 0, 0, "unknown");
        // add items and mock keypress for combining
        stationInventory.addItem(strawberry);
        stationInventory.addItem(banana);
        stationInventory.addItem(chocolate);
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of a banana split meal type
        boolean found = false;
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item != null && item.getItemName().equals("Banana Split")) {
                found = true;
            }
        }
        // assert true if found
        assertTrue(found);
    }

    @Test
    void shouldMakeFruitSalad() {
        // banana strawberry
        ItemComponent strawberry = new IngredientComponent("strawberry", ItemType.STRAWBERRY, 1, 0, 0, "unknown");
        ItemComponent banana = new IngredientComponent("banana", ItemType.BANANA, 1, 0, 0, "unknown");
        // add items and mock keypress for combining
        stationInventory.addItem(strawberry);
        stationInventory.addItem(banana);
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of a fruit salad meal type
        boolean found = false;
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item != null && item.getItemName().equals("fruit salad")) {
                found = true;
            }
        }
        // assert true if found
        assertTrue(found);
    }

    @Test
    void shouldMakeSalad() {
        // tomato cucumber lettuce
        ItemComponent tomato = new IngredientComponent("tomato", ItemType.TOMATO, 1, 0, 0, "unknown");
        ItemComponent cucumber = new IngredientComponent("cucumber", ItemType.CUCUMBER, 1, 0, 0, "unknown");
        ItemComponent lettuce = new IngredientComponent("lettuce", ItemType.LETTUCE, 1, 0, 0, "unknown");
        // add items and mock keypress for combining
        stationInventory.addItem(tomato);
        stationInventory.addItem(cucumber);
        stationInventory.addItem(lettuce);
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of a salad meal type
        boolean found = false;
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item != null && item.getItemName().equals("Salad")) {
                found = true;
            }
        }
        // assert true if found
        assertTrue(found);
    }

    @Test
    void shouldMakeSteakMeal() {
        // beef tomato cucumber
        ItemComponent tomato = new IngredientComponent("tomato", ItemType.TOMATO, 1, 0, 0, "unknown");
        ItemComponent cucumber = new IngredientComponent("cucumber", ItemType.CUCUMBER, 1, 0, 0, "unknown");
        ItemComponent beef = new IngredientComponent("beef", ItemType.BEEF, 1, 0, 0, "unknown");
        // add items and mock keypress for combining
        stationInventory.addItem(tomato);
        stationInventory.addItem(cucumber);
        stationInventory.addItem(beef);
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of a steak meal type
        boolean found = false;
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item != null && item.getItemName().equals("Steak Meal")) {
                found = true;
            }
        }
        // assert true if found
        assertTrue(found);
    }


}
