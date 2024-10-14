package com.csse3200.game.components.station;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.ServiceLocator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
public class StationBinComponentTest {

    public Entity mockStation;
    public StationBinComponent stationBinComponent;

    public Entity mockPlayer;

    public Entity mockItem;


    @BeforeEach
    void BeforeEach() {
        ServiceLocator.clear();
        ServiceLocator.registerEntityService(new EntityService());

        // Create the station
        stationBinComponent = new StationBinComponent();
        mockStation = new Entity().addComponent(stationBinComponent);
        ServiceLocator.getEntityService().register(mockStation);

        // Create a mock player 
        mockPlayer = new Entity()
                .addComponent(new InventoryComponent(1))
                .addComponent(mock(InventoryDisplay.class));

        // Create a mock item
        mockItem = new Entity().addComponent(new ItemComponent("test", null, 1));
        ServiceLocator.getEntityService().register(mockItem);

        // Ensure that the item is correctly registered to the entity service for upcoming tests
        assertTrue(ServiceLocator.getEntityService().getEntities().contains(mockItem, true));

        // Place the initial item into the player inventory
        mockPlayer.getComponent(InventoryComponent.class).addItem(mockItem.getComponent(ItemComponent.class));

        // Ensure that the item is correctly added to the player inventory
        assertTrue(mockPlayer.getComponent(InventoryComponent.class).getItemFirst() == mockItem.getComponent(ItemComponent.class));
    }

    @Test
    public void TestBinDoesntAcceptNonDefaultInteraction() {
        stationBinComponent.handleInteraction(mockPlayer.getComponent(InventoryComponent.class), mockPlayer.getComponent(InventoryDisplay.class), "non-default");

        // Ensure that the item is still in player inventoy
        assertTrue(mockPlayer.getComponent(InventoryComponent.class).getSize() == 1);
        assertTrue(mockPlayer.getComponent(InventoryComponent.class).getItemFirst() == mockItem.getComponent(ItemComponent.class));

        // Ensure that the item is correctly registered to the entity service 
        assertTrue(ServiceLocator.getEntityService().getEntities().contains(mockItem, true));
    }

    @Test
    public void TestBinHandlesDefaultInteractionDirectCall() {
        stationBinComponent.handleInteraction(mockPlayer.getComponent(InventoryComponent.class), mockPlayer.getComponent(InventoryDisplay.class), "default");

        // Ensure that the item is removed from the player inventory
        assertTrue(mockPlayer.getComponent(InventoryComponent.class).getSize() == 0);

        // Ensure that the item is correctly removed from the entity service
        assertFalse(ServiceLocator.getEntityService().getEntities().contains(mockItem, true));
    }

    @Test
    public void TestBinHandlesDefaultInteractionThroughEventListener() {
        mockStation.getEvents().trigger("Station Interaction", mockPlayer.getComponent(InventoryComponent.class), mockPlayer.getComponent(InventoryDisplay.class), "default");

        // Ensure that the item is removed from the player inventory
        assertTrue(mockPlayer.getComponent(InventoryComponent.class).getSize() == 0);

        // Ensure that the item is correctly removed from the entity service
        assertFalse(ServiceLocator.getEntityService().getEntities().contains(mockItem, true));
    }    
}
