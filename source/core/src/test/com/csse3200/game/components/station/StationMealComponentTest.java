package com.csse3200.game.components.station;

import com.csse3200.game.components.items.*;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
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

import java.util.Arrays;
import java.util.List;

//import javax.swing.text.html.parser.Entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
class StationMealComponentTest {
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
    protected List<String> acceptableItems;
    protected Entity mockPlate;

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
        mockPlate = mock(Entity.class);
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
        acceptableItems = Arrays.asList(
                "fish", "banana", "beef", "acai", "lettuce",
                "cucumber", "tomato", "strawberry", "chocolate", "plate"
        );

        // initialise the station meal component
        mealHandler = new StationMealComponent("combining", acceptableItems);

        // assign mock meal entity to mealHandler
        mealHandler.setEntity(mockMealEntity);
        mealHandler.create();
    }

    @Test
    void shouldSetAcceptableItems() {
        List<String> actualItems = mealHandler.acceptableItems;
        assertEquals(acceptableItems, actualItems);
    }

    @Test
    void shouldSetStationType() {
        assertEquals("combining", mealHandler.getType());
    }

    @Test
    void shouldAcceptItem() {
        ItemComponent item = new ItemComponent("banana", ItemType.BANANA, 1);
        assertTrue(mealHandler.isItemAccepted());
    }

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
    void incorrectIngredientsShouldntMakeMeal() {
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
        PlateComponent plate = new PlateComponent(0);
        // add items and mock keypress for combining
        stationInventory.addItem(acai);
        stationInventory.addItem(banana);
        stationInventory.addItem(plate);
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of an açaí bowl meal type
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
        PlateComponent plate = new PlateComponent(0);
        // add items and mock keypress for combining
        stationInventory.addItem(strawberry);
        stationInventory.addItem(banana);
        stationInventory.addItem(chocolate);
        stationInventory.addItem(plate);
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of a banana split meal type
        boolean found = false;
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item != null && item.getItemName().equals("banana split")) {
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
        PlateComponent plate = new PlateComponent(0);
        // add items and mock keypress for combining
        stationInventory.addItem(strawberry);
        stationInventory.addItem(banana);
        stationInventory.addItem(plate);
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
        PlateComponent plate = new PlateComponent(0);
        // add items and mock keypress for combining
        stationInventory.addItem(tomato);
        stationInventory.addItem(cucumber);
        stationInventory.addItem(lettuce);
        stationInventory.addItem(plate);
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of a salad meal type
        boolean found = false;
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item != null && item.getItemName().equals("salad")) {
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
        PlateComponent plate = new PlateComponent(0);
        // add items and mock keypress for combining
        stationInventory.addItem(tomato);
        stationInventory.addItem(cucumber);
        stationInventory.addItem(beef);
        stationInventory.addItem(plate);
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        // check if any component in inventory is of a steak meal type
        boolean found = false;
        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item != null && item.getItemName().equals("steak meal")) {
                found = true;
            }
        }
        // assert true if found
        assertTrue(found);
    }

    @Test
    void bothEmptyInventoriesShouldDoNothing() {
        assertTrue(playerInventory.isEmpty());
        assertTrue(stationInventory.isEmpty());

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");
        
        assertTrue(playerInventory.isEmpty());
        assertTrue(stationInventory.isEmpty());
    }

    @Test
    void bothFullInventoriesShouldDoNothing() {
        ItemComponent banana = new ItemComponent("banana", ItemType.BANANA, 1);
        playerInventory.addItem(banana);
        assertTrue(playerInventory.isFull());

        ItemComponent tomato = new IngredientComponent("tomato", ItemType.TOMATO, 1, 0, 0, "unknown");
        ItemComponent cucumber = new IngredientComponent("cucumber", ItemType.CUCUMBER, 1, 0, 0, "unknown");
        ItemComponent beef = new IngredientComponent("beef", ItemType.BEEF, 1, 0, 0, "unknown");
        ItemComponent lettuce = new IngredientComponent("lettuce", ItemType.LETTUCE, 1, 0, 0, "unknown");
        stationInventory.addItem(tomato);
        stationInventory.addItem(cucumber);
        stationInventory.addItem(beef);
        stationInventory.addItem(lettuce);
        assertTrue(stationInventory.isFull());

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");

        for (int index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item instanceof MealComponent) {
                // if it exists, fail
                fail();
            }
        }
        
        assertTrue(playerInventory.isFull());
        assertTrue(stationInventory.isFull());
    }

    @Test
    void fullPlayerInventoryShouldTransferAcceptedItemToEmptyStation() {
        ItemComponent banana = new ItemComponent("banana", ItemType.BANANA, 1);
        playerInventory.addItem(banana);
        assertTrue(playerInventory.isFull());
        assertTrue(stationInventory.isEmpty());

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "receive");

        assertTrue(playerInventory.isEmpty());
        assertEquals(banana, stationInventory.getItemFirst());
    }

    @Test
    void fullPlayerInventoryShouldTransferAcceptedItemToNonEmptyStation() {
        ItemComponent banana = new ItemComponent("banana", ItemType.BANANA, 1);
        playerInventory.addItem(banana);
        assertTrue(playerInventory.isFull());

        ItemComponent beef = new IngredientComponent("beef", ItemType.BEEF, 1, 0, 0, "unknown");
        ItemComponent lettuce = new IngredientComponent("lettuce", ItemType.LETTUCE, 1, 0, 0, "unknown");
        stationInventory.addItem(beef);
        stationInventory.addItem(lettuce);
        assertFalse(stationInventory.isEmpty());

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "receive");

        assertTrue(playerInventory.isEmpty());
        assertTrue(stationInventory.find(banana));
    }

    @Test
    void fullInventoriesShouldNotTransferItems() {
        ItemComponent banana = new ItemComponent("lettuce", ItemType.LETTUCE, 1);
        playerInventory.addItem(banana);
        assertTrue(playerInventory.isFull());

        ItemComponent tomato = new IngredientComponent("tomato", ItemType.TOMATO, 1, 0, 0, "unknown");
        ItemComponent cucumber = new IngredientComponent("cucumber", ItemType.CUCUMBER, 1, 0, 0, "unknown");
        ItemComponent beef = new IngredientComponent("beef", ItemType.BEEF, 1, 0, 0, "unknown");
        ItemComponent lettuce = new IngredientComponent("lettuce", ItemType.LETTUCE, 1, 0, 0, "unknown");
        stationInventory.addItem(tomato);
        stationInventory.addItem(cucumber);
        stationInventory.addItem(beef);
        stationInventory.addItem(lettuce);
        assertTrue(stationInventory.isFull());

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "receive");

        assertTrue(playerInventory.isFull());
        assertFalse(stationInventory.find(banana));
    }

    @Test
    void peekShouldReturnFirstItemInStation() {
        ItemComponent item = new ItemComponent("banana", ItemType.BANANA, 1);
        stationInventory.addItem(item);
        
        ItemComponent peekedItem = mealHandler.peek();
        
        assertEquals(item, peekedItem);
    }

    @Test
    void onlyMealShouldBeTransferredToPlayerWhenMealIsMade() {
        ItemComponent strawberry = new IngredientComponent("strawberry", ItemType.STRAWBERRY, 1, 0, 0, "unknown");
        ItemComponent banana = new IngredientComponent("banana", ItemType.BANANA, 1, 0, 0, "unknown");
        PlateComponent plate = new PlateComponent(0);
        
        playerInventory.addItem(strawberry);
        stationInventory.addItem(banana);
        stationInventory.addItem(plate);

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "receive");
        assertTrue(playerInventory.isEmpty());
        assertTrue(stationInventory.find(banana));

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "combine");

        boolean found = false;
        int index;
        for (index = 0; index < stationInventory.getCapacity(); index++) {
            ItemComponent item = stationInventory.getItemAt(index);
            if (item != null && item.getItemName().equals("fruit salad")) {
                found = true;
                break;
            }
        }

        assertTrue(found);
        ItemComponent item = stationInventory.getItemAt(index);
        
        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "receive");
        assertEquals(item, playerInventory.getItemFirst());
    }

    @Test
    void rotateEmptyInventoryShouldDoNothing() {
        assertTrue(stationInventory.isEmpty());

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "rotate");

        assertTrue(stationInventory.isEmpty());
    }

    @Test
    void rotateSingleItemInventoryShouldDoNothing() {
        ItemComponent banana = new IngredientComponent("banana", ItemType.BANANA, 1, 0, 0, "unknown");
        stationInventory.addItem(banana);

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "rotate");

        assertEquals(1, stationInventory.getSize());
        assertEquals(banana, stationInventory.getItemFirst());
    }

    @Test
    void rotateMultipleItemsShouldChangeOrder() {
        ItemComponent banana = new IngredientComponent("banana", ItemType.BANANA, 1, 0, 0, "unknown");
        ItemComponent tomato = new IngredientComponent("tomato", ItemType.TOMATO, 1, 0, 0, "unknown");
        ItemComponent beef = new IngredientComponent("beef", ItemType.BEEF, 1, 0, 0, "unknown");
        stationInventory.addItem(banana);
        stationInventory.addItem(tomato);
        stationInventory.addItem(beef);

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "rotate");

        assertEquals(3, stationInventory.getSize());
        assertEquals(tomato, stationInventory.getItemFirst());
        assertEquals(beef, stationInventory.getItemAt(1));
        assertEquals(banana, stationInventory.getItemAt(2));
    }

    @Test
    void rotateFullInventoryShouldChangeOrder() {
        ItemComponent banana = new IngredientComponent("banana", ItemType.BANANA, 1, 0, 0, "unknown");
        ItemComponent tomato = new IngredientComponent("tomato", ItemType.TOMATO, 1, 0, 0, "unknown");
        ItemComponent beef = new IngredientComponent("beef", ItemType.BEEF, 1, 0, 0, "unknown");
        ItemComponent lettuce = new IngredientComponent("lettuce", ItemType.LETTUCE, 1, 0, 0, "unknown");
        stationInventory.addItem(banana);
        stationInventory.addItem(tomato);
        stationInventory.addItem(beef);
        stationInventory.addItem(lettuce);

        mealHandler.handleInteraction(playerInventory, inventoryDisplay, "rotate");

        assertTrue(stationInventory.isFull());
        assertEquals(tomato, stationInventory.getItemFirst());
        assertEquals(beef, stationInventory.getItemAt(1));
        assertEquals(lettuce, stationInventory.getItemAt(2));
        assertEquals(banana, stationInventory.getItemAt(3));
    }
}
