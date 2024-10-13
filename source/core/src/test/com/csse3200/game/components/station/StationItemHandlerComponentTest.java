package com.csse3200.game.components.station;

import com.csse3200.game.components.items.CookIngredientComponent;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
class StationItemHandlerComponentTest {

    public Entity station;
    public StationItemHandlerComponent handler;
    public InventoryComponent stationInventory;
    public StationCookingComponent cookingComponent;

    public InventoryComponent playerInventory;
    public InventoryDisplay playerInventoryDisplay;

    public Entity testEntity1;
    public Entity testEntity2;

    public String type;

    // Before each to reset all out values and all created in the pre station
    // task
    @BeforeEach
    void BeforeEach() {
        ServiceLocator.clear();

        // Set up stuff so item creation can function
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(null);
        ServiceLocator.registerResourceService(mockResourceService);

        GameTime mockTime = mock(GameTime.class);
        when(mockTime.getTime()).thenReturn(1000L, 10000L);
        ServiceLocator.registerTimeSource(mockTime);

        // Reset all class variables to null
        station = null;
        handler = null;
        stationInventory = null;

        playerInventory = null;
        playerInventoryDisplay = null;

        testEntity1 = null;
        testEntity2 = null;

        type = "default";
    }

    void preItemHandlingTests() {
        playerInventory = new InventoryComponent(1);
        playerInventoryDisplay = new InventoryDisplay();
    }

    // Pre oven test tasks
    void preOvenTests() {
        // Create a station stationInventory and handler and add it to station entity
        stationInventory = new InventoryComponent(1);
        handler  = new StationItemHandlerComponent("oven");
        cookingComponent = new StationCookingComponent();

        // Create a station with these components
        station = new Entity();
        station.addComponent(handler);
        station.addComponent(stationInventory);
        station.addComponent(cookingComponent);
        handler.setEntity(station);
        stationInventory.setEntity(station);
        cookingComponent.setEntity(station);
        station.create();
    }

    // pre stove making tasks
    void preStoveTests() {
        // Create a station stationInventory and handler and add it to station entity
        stationInventory = new InventoryComponent(1);
        handler  = new StationItemHandlerComponent("stove");
        cookingComponent = new StationCookingComponent();

        station = new Entity();
        station.addComponent(handler);
        station.addComponent(stationInventory);
        station.addComponent(cookingComponent);
        handler.setEntity(station);
        stationInventory.setEntity(station);
        cookingComponent.setEntity(station);
        station.create();
    }

    // Test that the component is properly aware of its own type
    @Test
    void TestOvenGetType() {
        preOvenTests();

        assertNotNull(handler);
        assertEquals("oven", handler.getType());
    }

    // Test that the oven can correctly accept / deny items
    @Test
    void TestOvenAcceptedItems() {
        IngredientComponent mockIngredientComponent = mock(IngredientComponent.class);
        when(mockIngredientComponent.getItemName()).thenReturn("fake item");

        preOvenTests();

        String[] oven = {"tomato", "cucumber"};
        Entity[] ovenItem = {ItemFactory.createBaseItem(oven[0]), ItemFactory.createBaseItem(oven[1])};

        assertNotNull(ovenItem[0]);
        assertNotNull(ovenItem[1]);

        for (Entity item : ovenItem) {
            assert item != null;
            assertTrue(handler.isItemAccepted(item.getComponent(IngredientComponent.class)));
        }
        
        assertFalse(handler.isItemAccepted(mockIngredientComponent));
    }

    @Test
    void TestOvenHandlesInteractionStationBothEmpty() {
        preOvenTests();
        preItemHandlingTests();

        // Assert both have nothing in them to start
        assertEquals(0, station.getComponent(InventoryComponent.class).getSize());
        assertEquals(0, playerInventory.getSize());

        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay, type);

        // Assert both have nothing in them after the interaction
        assertEquals(0, station.getComponent(InventoryComponent.class).getSize());
        assertEquals(0, playerInventory.getSize());
    }

    // Test when station empty but player inventory full
    @Test
    void TestOvenHandlesInteractionStationOvenEmptyItemAllowed() {
        preOvenTests();
        preItemHandlingTests();

        // Create an item to put into the players inventory that is allowed for oven
        testEntity1 = ItemFactory.createBaseItem("tomato");
        assert testEntity1 != null;
        playerInventory
            .addItem(testEntity1.getComponent(IngredientComponent.class));

        // Assert they have correct amount of items and confirm item
        assertEquals(0, station.getComponent(InventoryComponent.class).getSize());
        assertEquals(1, playerInventory.getSize());
        assertEquals("tomato", playerInventory.getItemAt(0).getItemName().toLowerCase());

        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay, type);

        // Assert they have correct amount of items and confirm item
        assertEquals(1, station.getComponent(InventoryComponent.class).getSize());
        assertEquals("tomato", stationInventory.getItemAt(0).getItemName().toLowerCase());
        assertEquals(0, playerInventory.getSize());
    }

    // Test oven correctly handles the item not being allowed
    @Test
    void TestOvenHandlesInteractionStationEmptyItemNotAllowed() {
        preOvenTests();
        preItemHandlingTests();

        testEntity1 = ItemFactory.createBaseItem("beef");

        assert testEntity1 != null;
        playerInventory.addItem(testEntity1.getComponent(IngredientComponent.class));


        // Assert they have correct amount of items and confirm item
        assertEquals(0, station.getComponent(InventoryComponent.class).getSize());
        assertEquals(1, playerInventory.getSize());
        assertEquals("beef", playerInventory.getItemAt(0).getItemName().toLowerCase());

        // Simulate the interaction
        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay, type);

        // Assert that nothing has changed
        assertEquals(0, station.getComponent(InventoryComponent.class).getSize());
        assertEquals(1, playerInventory.getSize());
        assertEquals("beef", playerInventory.getItemAt(0).getItemName().toLowerCase());
    }

    // Test when station full but player empty
    @Test
    void TestOvenHandlesInteractionOvenFullPlayerEmpty() {
        preOvenTests();
        preItemHandlingTests();

        testEntity1 = ItemFactory.createBaseItem("tomato");

        // Add entity to station inventory
        assert testEntity1 != null;
        stationInventory.addItem(testEntity1.getComponent(IngredientComponent.class));

        // Assert they have correct amount of items and confirm item
        assertEquals(1, station.getComponent(InventoryComponent.class).getSize());
        assertEquals("tomato", stationInventory.getItemAt(0).getItemName().toLowerCase());
        assertEquals(0, playerInventory.getSize());

        // Simulate the interaction
        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay, type);

        // Assert they have correct amount of items and confirm item
        assertEquals(0, station.getComponent(InventoryComponent.class).getSize());
        assertEquals(1, playerInventory.getSize());
        assertEquals("tomato", playerInventory.getItemAt(0).getItemName().toLowerCase());
    }

    // Test when both are full i.e. nothing should happen on interaction
    @Test
    void TestOvenHandlesInteractionBothInventoryFull() {
        preOvenTests();
        preItemHandlingTests();

        // entity 1 for the station
        testEntity1 = ItemFactory.createBaseItem("tomato");
        assert testEntity1 != null;
        stationInventory.addItem(testEntity1.getComponent(IngredientComponent.class));

        // entity 2 for the player
        testEntity2 = ItemFactory.createBaseItem("cucumber");
        assert testEntity2 != null;
        playerInventory.addItem(testEntity2.getComponent(IngredientComponent.class));

        // Assert they have correct amount of items and confirm item
        assertEquals(1, station.getComponent(InventoryComponent.class).getSize());
        assertEquals("tomato", stationInventory.getItemAt(0).getItemName().toLowerCase());
        assertEquals(1, playerInventory.getSize());
        assertEquals("cucumber", playerInventory.getItemAt(0).getItemName().toLowerCase());

        // Simulate the interaction
        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay, type);

        // Assert that nothing should have changed i.e. no swap occurred
        assertEquals(1, station.getComponent(InventoryComponent.class).getSize());
        assertEquals("tomato", stationInventory.getItemAt(0).getItemName().toLowerCase());
        assertEquals(1, playerInventory.getSize());
        assertEquals("cucumber", playerInventory.getItemAt(0).getItemName().toLowerCase());
    }

    // Test that the oven starts cooking ingredient when placed in it
    @Test
    void TestOvenHandlesInteractionItemStartsCooking() {
        preStoveTests();
        preItemHandlingTests();

        // Create an item to put into the players inventory that is allowed for oven
        testEntity1 = ItemFactory.createBaseItem("beef");
        assert testEntity1 != null;
        testEntity1.create();
        playerInventory
            .addItem(testEntity1.getComponent(IngredientComponent.class));

        // Assert they have correct amount of items and confirm item
        assertEquals(0, station.getComponent(InventoryComponent.class).getSize());
        assertEquals(1, playerInventory.getSize());
        assertEquals("beef", playerInventory.getItemAt(0).getItemName().toLowerCase());

        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay, type);

        // Assert they have correct amount of items and confirm item
        assertEquals(1, station.getComponent(InventoryComponent.class).getSize());
        assertEquals("beef", stationInventory.getItemAt(0).getItemName().toLowerCase());
        assertEquals(0, playerInventory.getSize());

        // Now interaction is confirmed check that the item starts cooking
        boolean isCooking = stationInventory.getItemAt(0).getEntity().getComponent(CookIngredientComponent.class).getIsCooking();
        assertTrue(isCooking);
    }

}
