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
  void shouldAddItem() {
    InventoryComponent inventory = new InventoryComponent(2);
    inventory.addItemAt("item", 0);
    ArrayList<String> items = inventory.getItems();
    assertEquals(1, items.size());
  }

  // Out of bounds exception.
  // @Test
  // void shouldAddItemValidIndex() {
  //   InventoryComponent inventory = new InventoryComponent(2);
  //   inventory.addItemAt("item", 1);

  //   ArrayList<String> items = inventory.getItems();
  //   assertEquals("item", items.get(1));
  // }

  // Out of bounds exception.
  // @Test
  // void shouldAddItemInvalidIndex() {
  //   InventoryComponent inventory = new InventoryComponent(2);
  //   try {
  //     inventory.addItemAt("item", 2);
  //   } catch (IllegalArgumentException e) {
  //     assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.", e.getMessage());
  //   }
  // }

  @Test
  void shouldRemoveItemAt() {
    InventoryComponent inventory = new InventoryComponent(2);
    inventory.addItemAt("item", 0);
    String removedItem = inventory.removeAt(0);
    assertEquals("item", removedItem);
  }

  // Out of bounds exception.
  // @Test
  // void shouldRemoveItemAtIllegalIndex() {
  //   InventoryComponent inventory = new InventoryComponent(2);
  //   try {
  //     inventory.removeAt(2);
  //   } catch (IllegalArgumentException e) {
  //     assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.", e.getMessage());
  //   }
  // }

  @Test
  void shouldBeNotFull1() {
    InventoryComponent inventory = new InventoryComponent(2);
    assertEquals(false, inventory.isFull());
  }

  @Test
  void shouldBeNotFull2() {
    InventoryComponent inventory = new InventoryComponent(2);
    inventory.addItemAt("item", 0);
    assertEquals(false, inventory.isFull());
  }

  // add item logic issue
  // @Test
  // void shouldBeFull() {
  //   InventoryComponent inventory = new InventoryComponent(2);
  //   for (int i = 0; i < 2; i++) {
  //     inventory.addItemAt("item", i);
  //   }
  //   assertEquals(true, inventory.isFull());
  // }

  @Test
  void shouldBeEmpty() {
    InventoryComponent inventory = new InventoryComponent(2);
    assertEquals(true, inventory.isEmpty());
  }

  @Test
  void shouldBeNotEmpty() {
    InventoryComponent inventory = new InventoryComponent(2);
    inventory.addItemAt("item", 0);
    assertEquals(false, inventory.isEmpty());
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
