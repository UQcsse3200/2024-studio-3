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
public class StationItemHandlerComponentTest {

    public Entity station;
    public StationItemHandlerComponent handler;
    public InventoryComponent stationInventory;
    public StationCookingComponent cookingComponent;

    public InventoryComponent playerInventory;
    public InventoryDisplay playerInventoryDisplay;

    public Entity testEntity1;
    public Entity testEntity2;

    // Before each to reset all out values and all created in the pre station
    // task
    @BeforeEach
    public void BeforeEach() {
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
    }

    public void preItemHandlingTests() {
        playerInventory = new InventoryComponent(1);
        playerInventoryDisplay = new InventoryDisplay();
    }

    // Pre oven test tasks
    public void preOvenTests() {
        // Create a station stationInventory and handler and add it to station entity
        stationInventory = new InventoryComponent(1);
        handler  = new StationItemHandlerComponent("oven");
        cookingComponent = new StationCookingComponent();

        // Create a station with these components
        station = new Entity();
        station.addComponent(handler);
        station.addComponent(stationInventory);
        station.addComponent(cookingComponent);
        //when(station.getComponent(StationItemHandlerComponent.class)).thenReturn(handler);
        //when(station.getComponent(InventoryComponent.class)).thenReturn(stationInventory);
        handler.setEntity(station);
        stationInventory.setEntity(station);
        cookingComponent.setEntity(station);
        station.create();
    }

    // pre stove making tasks
    public void preStoveTests() {
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
    public void TestOvenGetType() {
        preOvenTests();

        assertNotNull(handler);
        assertTrue(handler.getType().equals("oven"));
    }

    // Test that the oven can correctly accept / deny items
    @Test
    public void TestOvenAcceptedItems() {
        IngredientComponent mockIngredientComponent = mock(IngredientComponent.class);
        when(mockIngredientComponent.getItemName()).thenReturn("fake item");

        preOvenTests();

        String[] oven = {"tomato", "cucumber"};
        Entity[] ovenItem = {ItemFactory.createBaseItem(oven[0]), ItemFactory.createBaseItem(oven[1])};

        assertNotNull(ovenItem[0]);
        assertNotNull(ovenItem[1]);

        for (Entity item : ovenItem) {
            assertTrue(handler.isItemAccepted(item.getComponent(IngredientComponent.class)));
        }
        
        assertFalse(handler.isItemAccepted(mockIngredientComponent));
    }

    @Test
    public void TestOvenHandlesInteractionStationBothEmpty() {
        preOvenTests();
        preItemHandlingTests();

        // Assert both have nothing in them to start
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 0);
        assertTrue(playerInventory.getSize() == 0);

        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay);

        // Assert both have nothing in them after the interaction
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 0);
        assertTrue(playerInventory.getSize() == 0);
    }

    // Test when station empty but player inventory full
    @Test
    public void TestOvenHandlesInteractionStationOvenEmptyItemAllowed() {
        preOvenTests();
        preItemHandlingTests();

        // Create an item to put into the players inventory that is allowed for oven
        testEntity1 = ItemFactory.createBaseItem("tomato");
        playerInventory
            .addItem(testEntity1.getComponent(IngredientComponent.class));

        // Assert they have correct ammount of items and confirm item
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 0);
        assertTrue(playerInventory.getSize() == 1);
        assertTrue(playerInventory.getItemAt(0).getItemName().toLowerCase().equals("tomato"));

        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay);

        // Assert they have correct ammount of items and confirm item
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 1);
        assertTrue(stationInventory.getItemAt(0).getItemName().toLowerCase().equals("tomato"));
        assertTrue(playerInventory.getSize() == 0);
    }

    // Test oven correctly handles the item not being allowed
    @Test
    public void TestOvenHandlesInteractionStationEmptyItemNotAllowed() {
        preOvenTests();
        preItemHandlingTests();

        testEntity1 = ItemFactory.createBaseItem("beef");

        playerInventory.addItem(testEntity1.getComponent(IngredientComponent.class));


        // Assert they have correct ammount of items and confirm item
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 0);
        assertTrue(playerInventory.getSize() == 1);
        assertTrue(playerInventory.getItemAt(0).getItemName().toLowerCase().equals("beef"));

        // Simulate the interaction
        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay);

        // Assert that nothing has changed
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 0);
        assertTrue(playerInventory.getSize() == 1);
        assertTrue(playerInventory.getItemAt(0).getItemName().toLowerCase().equals("beef"));
    }

    // Test when station full but player empty
    @Test
    public void TestOvenHandlesInteractionOvenFullPlayerEmpty() {
        preOvenTests();
        preItemHandlingTests();

        testEntity1 = ItemFactory.createBaseItem("tomato");

        // Add entity to station inventory
        stationInventory.addItem(testEntity1.getComponent(IngredientComponent.class));

        // Assert they have correct ammount of items and confirm item
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 1);
        assertTrue(stationInventory.getItemAt(0).getItemName().toLowerCase().equals("tomato"));
        assertTrue(playerInventory.getSize() == 0);

        // Simulate the interaction
        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay);

        // Assert they have correct ammount of items and confirm item
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 0);
        assertTrue(playerInventory.getSize() == 1);
        assertTrue(playerInventory.getItemAt(0).getItemName().toLowerCase().equals("tomato"));
    }

    // Test when both are full i.e. nothing should happen on interaction
    @Test
    public void TestOvenHandlesInteractionBothInventoryFull() {
        preOvenTests();
        preItemHandlingTests();

        // entity 1 for the station
        testEntity1 = ItemFactory.createBaseItem("tomato");
        stationInventory.addItem(testEntity1.getComponent(IngredientComponent.class));

        // entity 2 for the player
        testEntity2 = ItemFactory.createBaseItem("cucumber");
        playerInventory.addItem(testEntity2.getComponent(IngredientComponent.class));

        // Assert they have correct ammount of items and confirm item
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 1);
        assertTrue(stationInventory.getItemAt(0).getItemName().toLowerCase().equals("tomato"));
        assertTrue(playerInventory.getSize() == 1);
        assertTrue(playerInventory.getItemAt(0).getItemName().toLowerCase().equals("cucumber"));

        // Simulate the interaction
        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay);

        // Assert that nothing should have changed i.e. no swap occured
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 1);
        assertTrue(stationInventory.getItemAt(0).getItemName().toLowerCase().equals("tomato"));
        assertTrue(playerInventory.getSize() == 1);
        assertTrue(playerInventory.getItemAt(0).getItemName().toLowerCase().equals("cucumber"));
    }

    // Test that the oven starts cooking ingredient when placed in it
    @Test
    public void TestOvenHandlesInteractionItemStartsCooking() {
        preStoveTests();
        preItemHandlingTests();

        // Create an item to put into the players inventory that is allowed for oven
        testEntity1 = ItemFactory.createBaseItem("beef");
        testEntity1.create();
        playerInventory
            .addItem(testEntity1.getComponent(IngredientComponent.class));

        // Assert they have correct ammount of items and confirm item
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 0);
        assertTrue(playerInventory.getSize() == 1);
        assertTrue(playerInventory.getItemAt(0).getItemName().toLowerCase().equals("beef"));

        station
            .getComponent(StationItemHandlerComponent.class)
            .handleInteraction(playerInventory, playerInventoryDisplay);

        // Assert they have correct ammount of items and confirm item
        assertTrue(station.getComponent(InventoryComponent.class).getSize() == 1);
        assertTrue(stationInventory.getItemAt(0).getItemName().toLowerCase().equals("beef"));
        assertTrue(playerInventory.getSize() == 0);

        // Now interaction is confirmed check that the item starts cooking
        boolean isCooking = stationInventory.getItemAt(0).getEntity().getComponent(CookIngredientComponent.class).getIsCooking();
        assertTrue(isCooking);
    }

}
