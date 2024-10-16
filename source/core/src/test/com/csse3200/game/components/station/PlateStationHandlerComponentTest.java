package com.csse3200.game.components.station;

import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.components.items.PlateComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.services.GameTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
class PlateStationHandlerComponentTest {

    private PlateStationHandlerComponent handler;
    private InventoryComponent stationInventory;
    private InventoryComponent playerInventory;
    private InventoryDisplay playerInventoryDisplay;

    @BeforeEach
    void setup() {
        // Clear previous service locators
        ServiceLocator.clear();
        ServiceLocator.clear();

        // Set up stuff so item creation can function
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(null);
        ServiceLocator.registerResourceService(mockResourceService);

        // Initialize mock services
        GameTime mockTime = mock(GameTime.class);
        when(mockTime.getTime()).thenReturn(1000L);
        ServiceLocator.registerTimeSource(mockTime);

        // Set up the station and its components
        stationInventory = new InventoryComponent(1);
        //StationCollectionComponent mockCollectionComponent = mock(StationCollectionComponent.class);
        handler = new PlateStationHandlerComponent();

        Entity station = new Entity();
        station.addComponent(handler);
        station.addComponent(stationInventory);
        //station.addComponent(mockCollectionComponent);
        handler.setEntity(station);
        stationInventory.setEntity(station);
        //Entity mockPlate = ItemFactory.createPlate();

        // Mock collection to return the correct item
        //when(mockCollectionComponent.collectItem(anyString()))
        //        .thenReturn(mockCollectedItem);

        station.create();

        // Setup player inventory and display
        playerInventory = new InventoryComponent(1);
        playerInventoryDisplay = mock(InventoryDisplay.class);
    }

    @Test
    void testStationGivesItemToPlayer() {
        // Simulate player interaction with the station
        handler.handleInteraction(playerInventory, playerInventoryDisplay, "default");

        // Verify player received the item
        assertEquals(1, playerInventory.getSize());
        assertEquals("plate", playerInventory.getItemAt(0).getItemName().toLowerCase());

        // Verify station is repopulated with a new item
        assertEquals(1, stationInventory.getSize());
        assertEquals("plate", stationInventory.getItemAt(0).getItemName().toLowerCase());

        // Verify the player display was updated
        verify(playerInventoryDisplay, times(1)).update();
    }

    @Test
    void testStationInteractionWhenPlayerInventoryIsFull() {
        // Fill the player inventory
        Entity tomatoItem = ItemFactory.createBaseItem("tomato");
        assert tomatoItem != null;
        playerInventory.addItem(tomatoItem.getComponent(IngredientComponent.class));

        // Simulate player interaction with the station
        handler.handleInteraction(playerInventory, playerInventoryDisplay, "default");

        // Verify that no item was added to player inventory and station did not change
        assertEquals(1, playerInventory.getSize());  // Player inventory remains full
        assertEquals("tomato", playerInventory.getItemAt(0).getItemName().toLowerCase());
        assertEquals(1, stationInventory.getSize());
    }

    @Test
    void testStationRepopulatesAfterGivingItem() {
        // Add an item to the station's inventory
        Entity plate = ItemFactory.createPlate();
        assert plate != null;
        stationInventory.addItem(plate.getComponent(PlateComponent.class));

        // Simulate player interaction with the station
        handler.handleInteraction(playerInventory, playerInventoryDisplay, "default");

        // Verify that the item from the station was transferred to the player
        assertEquals(1, playerInventory.getSize());
        assertEquals("plate", playerInventory.getItemAt(0).getItemName().toLowerCase());

        // Verify that the station was repopulated with a new banana item
        assertEquals(1, stationInventory.getSize());
        assertEquals("plate", stationInventory.getItemAt(0).getItemName().toLowerCase());
    }

    @Test
    void testNoInteractionOnNonDefaultType() {
        // Simulate interaction with a different type
        handler.handleInteraction(playerInventory, playerInventoryDisplay, "non-default");

        // Verify nothing happens
        assertEquals(0, playerInventory.getSize());
        assertEquals(1, stationInventory.getSize());
    }
}
