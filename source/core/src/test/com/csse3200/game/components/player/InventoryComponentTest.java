package com.csse3200.game.components.player;

import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;

@ExtendWith(GameExtension.class)
class InventoryComponentTest {
  @Test
  void shouldSetSize() {
    InventoryComponent inventory = new InventoryComponent(2);
    assertEquals(2, inventory.getSize());

    inventory.setSize(1);
    assertEquals(1, inventory.getSize());
  }

  @Test
  void shouldSetIllegalSize() {
    try {
      InventoryComponent inventory = new InventoryComponent(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid size parameter. Must be an integer > 0", e.getMessage());
    }
  }

  @Test
  void shouldReturnItems() {
    InventoryComponent inventory = new InventoryComponent(2);
    ArrayList<String> items = inventory.getItems();
    assertEquals(0, items.size());

  }

  @Test
  void shouldBeNotFull() {
    InventoryComponent inventory = new InventoryComponent(2);
    assertEquals(false, inventory.isFull());
  }

  @Test
  void shouldBeFull() {
    InventoryComponent inventory = new InventoryComponent(2);
    // TODO: needs adding functionality to be implemented
    // should be similar to shouldNotBeFull() except 2 items should be added to inventory
    // and then method checks that its full
  }

  @Test
  void shouldGetItems() {
    InventoryComponent inventory = new InventoryComponent(2);
    // TODO: needs adding functionality to be implemented
  }

  @Test
  void shouldAddItems() {
    //
  }

  @Test
  void shouldntAddItems() {
    // (if inventory is full)
  }

  @Test
  void shouldRemoveItems() {
    //
  }

  @Test
  void shouldntRemoveItems() {
    // (if inventory is empty)
  }

  @Test
  void shouldSelectItems() {
    // idk if we are going to have this method?
  }

  @Test
  void shouldAddAndResize() {
    // (basically resizing with stuff in inventory)
  }

}
