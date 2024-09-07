package com.csse3200.game.components.station;

import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FireExtinguisherHandlerComponentTest {
    private FireExtinguisherHandlerComponent handler;
    private InventoryComponent inventory;
    private Entity mockEntity;
    private EventHandler mockEvents;
    private Entity mockStation;
    @BeforeEach
    void setUp() {
        // Create handler to test
        handler = new FireExtinguisherHandlerComponent();
        // Create a mock inventory to test handler against
        inventory = new InventoryComponent(1);
        // Mock Entity and EventHandler so stuff actually runs
        mockEntity = mock(Entity.class);
        mockEvents = mock(EventHandler.class);
        // Map any InventoryComponent calls to actually return mock inventory
        when(mockEntity.getComponent(InventoryComponent.class)).thenReturn(inventory);
        when(mockEntity.getEvents()).thenReturn(mockEvents);

        mockStation = mock(Entity.class);
        when(mockStation.getComponent(FireExtinguisherHandlerComponent.class)).thenReturn(handler);
    }

    @Test
    void shouldGiveExtinguisher() {
        for(int i = 0; i < inventory.getSize(); i++) {
            assertNotSame(
                    inventory.getItemAt(i).getItemType(),
                    ItemType.FIREEXTINGUISHER,
                    "Should not hold fire extinguisher");
        }
        handler.givePutExtinguisher(mockEntity);
        boolean found = false;
        for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItemAt(i).getItemType() == ItemType.FIREEXTINGUISHER) {
                found = true;
            }
        }
        assertTrue(found, "Should have fire extinguisher in inventory");
    }

    @Test
    void shouldReturnExtinguisher() {
        this.shouldGiveExtinguisher();
        handler.givePutExtinguisher(mockEntity);
        for(int i = 0; i < inventory.getSize(); i++) {
            assertNotSame(
                    inventory.getItemAt(i).getItemType(),
                    ItemType.FIREEXTINGUISHER,
                    "Should not hold fire extinguisher");
        }
    }
}
