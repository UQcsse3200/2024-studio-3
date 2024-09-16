package com.csse3200.game.components.station;

import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
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

    public InventoryComponent playerInventory;
    public InventoryDisplay playerInventoryDisplay;

    public Entity testEntity1;
    public Entity testEntity2;

    // Before each to reset all out values and all created in the pre station
    // task
    @BeforeEach
    public void BeforeEach() {
        // Set up stuff so item creation can function
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(null);
        ServiceLocator.registerResourceService(mockResourceService);

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

        // Create a station with these components
        station = new Entity();
        station.addComponent(handler);
        station.addComponent(stationInventory);
        //when(station.getComponent(StationItemHandlerComponent.class)).thenReturn(handler);
        //when(station.getComponent(InventoryComponent.class)).thenReturn(stationInventory);
        handler.setEntity(station);
        stationInventory.setEntity(station);
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

}
