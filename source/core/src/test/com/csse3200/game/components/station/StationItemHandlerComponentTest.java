package com.csse3200.game.components.station;

import com.csse3200.game.events.EventHandler;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class StationItemHandlerComponentTest {

    private StationItemHandlerComponent handler;
    private StationInventoryComponent mockInventory;
    private Entity mockEntity;
    private EventHandler mockEvents;

    @BeforeEach
    public void setUp() {
        ArrayList<String> acceptableItems = new ArrayList<>();
        acceptableItems.add("meat");
        acceptableItems.add("vegetable");
        acceptableItems.add("cheese");
        // Create handler to test
        handler = new StationItemHandlerComponent("oven", acceptableItems);
        // Create a mock inventory to test handler agaisnt
        mockInventory = mock(StationInventoryComponent.class);
        // Mock Entity and EventHandler so stuff actually runs
        mockEntity = mock(Entity.class);
        mockEvents = mock(EventHandler.class);
        // Map any InventoryComponent calls to actually return mock inventory
        when(mockEntity.getComponent(StationInventoryComponent.class)).thenReturn(mockInventory);
        when(mockEntity.getEvents()).thenReturn(mockEvents);

        handler.setEntity(mockEntity);
        handler.create();
    }

    @Test
    public void testGetType() {
        assertEquals("oven", handler.getType());
    }

    @Test
    public void testIsItemAccepted() {
        assertTrue(handler.isItemAccepted("meat"));
        assertFalse(handler.isItemAccepted("stone"));
    }

    @Test
    public void testHasItem_whenItemIsPresent() {
        // Assumes inventory works, sets an expected value true
        // and checks handler returns same value
        when(mockInventory.isItemPresent()).thenReturn(true);
        assertTrue(handler.hasItem());
    }

    @Test
    public void testHasItem_whenItemIsNotPresent() {
        // Assumes inventory works, sets an expected value false
        // and checks handler returns same value
        when(mockInventory.isItemPresent()).thenReturn(false);
        assertFalse(handler.hasItem());
    }

    @Test
    public void testGiveItem_whenStationIsEmptyAndItemAccepted() {
        // Assumes inventory works, sets an expected value true
        // and checks handler returns same value after method run
        when(mockInventory.isItemPresent()).thenReturn(false);
        handler.giveItem("meat");
        // verify mocks up a scenario, in this case that
        // setting Inventory happens once, cant actually check handler
        // as takeItem is void, include check later
        verify(mockInventory).addItem("meat");
    }

    @Test
    public void testGiveItem_whenStationIsNotEmpty() {
        when(mockInventory.isItemPresent()).thenReturn(true);
        handler.giveItem("meat");
        // cant actually check handler
        // as takeItem is void, include check later
        // never() checks that no interaction actually occured, in this case
        // we expect nothing to happen as station is full with one item
        verify(mockInventory, never()).addItem(anyString());
    }

    @Test
    public void testGiveItem_whenItemNotAccepted() {
        when(mockInventory.isItemPresent()).thenReturn(false);
        handler.giveItem("stone");
        verify(mockInventory, never()).addItem(anyString());
    }

    @Test
    public void testTakeItem_whenItemIsPresent() {
        Optional<String> item = Optional.of("meat");
        when(mockInventory.removeCurrentItem()).thenReturn(item);
        handler.takeItem();
        verify(mockInventory).removeCurrentItem();
    }

    @Test
    public void testTakeItem_whenItemIsNotPresent() {
        when(mockInventory.removeCurrentItem()).thenReturn(Optional.empty());
        handler.takeItem();
        verify(mockInventory).removeCurrentItem();
    }
}

