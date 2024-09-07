package com.csse3200.game.components.station;

import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(GameExtension.class)
public class StationInventoryComponentTest {
/*
    @Test
    void shouldSetCurrentItemTest() {
        StationInventoryComponent inventory = new StationInventoryComponent();
        inventory.setCurrentItem("meat");
        assertEquals("meat", inventory.getCurrentItem().orElse("Empty"));
    }

    @Test
    void shouldCheckItemPresentTest() {
        StationInventoryComponent inventory = new StationInventoryComponent();
        inventory.setCurrentItem("meat");
        assertTrue(inventory.isItemPresent());
    }

    @Test
    void shouldNotRemoveWhenStationIsEmpty() {
        StationInventoryComponent inventory = new StationInventoryComponent();
        Optional<String> removedItem = inventory.removeCurrentItem();
        assertTrue(removedItem.isEmpty());
    }

    @Test
    void shouldCheckItemAbsentTest() {
        StationInventoryComponent inventory = new StationInventoryComponent();
        assertFalse(inventory.isItemPresent());
    }

    @Test
    void shouldSetItemAccepted() {
        StationInventoryComponent inventory = new StationInventoryComponent();
        inventory.setCurrentItem("meat");
        assertTrue(inventory.isItemPresent());
    }

    @Test
    void shouldRemoveCurrentItem() {
        StationInventoryComponent inventory = new StationInventoryComponent();
        inventory.setCurrentItem("meat");
        String output = inventory.removeCurrentItem().orElse("Empty");
        assertEquals("meat", output);
        assertFalse(inventory.isItemPresent());
    }
*/
}
