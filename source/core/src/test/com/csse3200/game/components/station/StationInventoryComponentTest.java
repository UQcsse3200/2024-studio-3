package com.csse3200.game.components.station;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class StationInventoryComponentTest {
    private GameTime mockTime;
    private Entity testEntity;
    private StationInventoryComponent inventoryComponent;
    private StationItemHandlerComponent handlerComponent;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        mockTime = mock(GameTime.class);
        ServiceLocator.registerTimeSource(mockTime);

        testEntity = new Entity();

        inventoryComponent = spy(new StationInventoryComponent());
        testEntity.addComponent(inventoryComponent);

        ArrayList<String> acceptableItems = new ArrayList<>();
        acceptableItems.add("acai");
        acceptableItems.add("banana");
        handlerComponent = spy(new StationItemHandlerComponent("OVEN", acceptableItems));
        testEntity.addComponent(handlerComponent);

        testEntity.create();
    }

    @Test
    void shouldSetCurrentItemTest() {
        inventoryComponent.addItem("meat");
        assertEquals("meat", inventoryComponent.getCurrentItem().orElse("Empty"));
    }

    @Test
    void shouldCheckItemPresentTest() {
        inventoryComponent.addItem("meat");
        //testEntity.getEvents().trigger("give station item", "acai");
        assertTrue(inventoryComponent.isItemPresent());
    }

    @Test
    void shouldNotRemoveWhenStationIsEmpty() {
        Optional<String> removedItem = inventoryComponent.removeCurrentItem();
        assertTrue(removedItem.isEmpty());
    }

    @Test
    void shouldCheckItemAbsentTest() {
        assertFalse(inventoryComponent.isItemPresent());
    }

    @Test
    void shouldSetItemAccepted() {
        inventoryComponent.addItem("meat");
        assertTrue(inventoryComponent.isItemPresent());
    }

    @Test
    void shouldRemoveCurrentItem() {
        inventoryComponent.addItem("meat");
        String output = inventoryComponent.removeCurrentItem().orElse("Empty");
        assertEquals("meat", output);
        assertFalse(inventoryComponent.isItemPresent());
    }

    /**
     * Tests that the respective event triggers affects both
     * InventoryComponent.checkRecipe, StationItemHandlerComponent.giveItem,
     * InventoryComponent.removeItem and StationItemHandler.takeItem.
     */
    @Test
    void testGiveEventTrigger() {
        testEntity.getEvents().trigger("give station item", "acai");
        verify(handlerComponent).giveItem("acai"); // verifies that giveItem method was called
        verify(inventoryComponent).checkRecipe("acai"); // verifies that checkRecipe method was called
        testEntity.getEvents().trigger("take item");
        verify(handlerComponent).takeItem(); // verifies that takeItem method was called
        verify(inventoryComponent).removeItem(); // verifies that removeItem method was called
    }

    /**
     * Tests that cooking does not commence unless the ingredients are part of a valid recipe
     */
    @Test
    void testAddOneItem() {
        testEntity.getEvents().trigger("give station item", "banana");
        assertFalse(inventoryComponent.isCooking());
    }

    /**
     * Tests that cooking stops upon removal
     */
    @Test
    void testRemoveItem() {
        testEntity.getEvents().trigger("give station item", "acai");
        testEntity.getEvents().trigger("give station item", "banana");
        assertTrue(inventoryComponent.isCooking());
        testEntity.getEvents().trigger("take item");
        assertFalse(inventoryComponent.isCooking());
    }

    /**
     * Tests that the cooking time updates correctly
     */
    @Test
    void testUpdateCooking() {
        testEntity.getEvents().trigger("give station item", "acai");
        testEntity.getEvents().trigger("give station item", "banana");

        when(mockTime.getDeltaTime()).thenReturn((float) 500L);
        inventoryComponent.update();
        assertEquals(9500L, inventoryComponent.getCookingTime());
        inventoryComponent.update();
        assertEquals(9000, inventoryComponent.getCookingTime());
    }

    /**
     * Tests the getCookingTime method
     */
    @Test
    void testGetCookingTime() {
        testEntity.getEvents().trigger("give station item", "acai");
        testEntity.getEvents().trigger("give station item", "banana");
        assertEquals(10000, inventoryComponent.getCookingTime());
    }
}
