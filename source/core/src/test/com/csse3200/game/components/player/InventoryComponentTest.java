package com.csse3200.game.components.player;

import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemType;

import java.util.ArrayList;

@ExtendWith(GameExtension.class)
class InventoryComponentTest {
  @Test
  void shouldInitialize() {
    InventoryComponent inventory = new InventoryComponent(2);
    assertEquals(2, inventory.getCapacity());
  }

  @Test
  void shouldNotInitialize() {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      new InventoryComponent(-1);
    });
    
    assertEquals("Invalid size parameter. Must be an integer > 0.", e.getMessage());

    e = assertThrows(IllegalArgumentException.class, () -> {
      new InventoryComponent(0);
    });
    
    assertEquals("Invalid size parameter. Must be an integer > 0.", e.getMessage());
  }

  @Test
  void shouldGetSize() {
    InventoryComponent inventory = new InventoryComponent(2);
    assertEquals(0, inventory.getSize());
  }

  @Test
  void shouldSetCapacity() {
    InventoryComponent inventory = new InventoryComponent(2);

    inventory.setCapacity(3);
    assertEquals(3, inventory.getCapacity());
  }

  @Test
  void shouldNotSetCapacity() {
    InventoryComponent inventory = new InventoryComponent(2);

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      inventory.setCapacity(0);
    });
    
    assertEquals("Invalid size parameter. Must be an integer > 0.", e.getMessage());

    e = assertThrows(IllegalArgumentException.class, () -> {
      inventory.setCapacity(-1);
    });
    
    assertEquals("Invalid size parameter. Must be an integer > 0.", e.getMessage());
  }

  @Test
  void shouldIncreaseCapacity() {
    InventoryComponent inventory = new InventoryComponent(2);

    inventory.increaseCapacity(3);
    assertEquals(3, inventory.getCapacity());
  }

  @Test
  void shouldNotIncreaseCapacity() {
    InventoryComponent inventory = new InventoryComponent(3);

    inventory.increaseCapacity(1);
    assertEquals(3, inventory.getCapacity()); // Probably needs to be some kind of exception
  }

  @Test
  void shouldAddItem() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent item = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(item);
    assertEquals(1, inventory.getSize());
  }

  // @Test
  // void shouldRemoveItem() {
  //   InventoryComponent inventory = new InventoryComponent(2);
  //   ItemComponent item = new ItemComponent("Beef", ItemType.BEEF, 1);
  //   inventory.addItem(item);
  //   assertEquals(1, inventory.getSize());
  // }


  @Test
  void shouldBeEmpty() {
    InventoryComponent inventory = new InventoryComponent(2);
    assertTrue(inventory.isEmpty());
  }

  @Test
  void shouldBeNotEmpty() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent item = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(item);
    assertFalse(inventory.isEmpty());
  }

  @Test
  void shouldBeFull() {
    InventoryComponent inventory = new InventoryComponent(5);
    ItemComponent item;
    for (int i = 0; i < 5; i++) {
      item = new ItemComponent("Beef", ItemType.BEEF, 1);
      inventory.addItem(item);
    }
    assertTrue(inventory.isFull());
  }

  @Test
  void shouldBeNotFull() {
    InventoryComponent inventory = new InventoryComponent(5);
    assertFalse(inventory.isFull());

    ItemComponent item;
    for (int i = 0; i < 3; i++) {
      item = new ItemComponent("Beef", ItemType.BEEF, 1);
      inventory.addItem(item);
    }
    assertFalse(inventory.isFull());
  }

  @Test
  void shouldFindItem() {
    InventoryComponent inventory = new InventoryComponent(5);
    assertTrue(inventory.find(null));
    
    ItemComponent item = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(item);
    assertTrue(inventory.find(item));
  }

  @Test
  void shouldNotFindItem() {
    InventoryComponent inventory = new InventoryComponent(5);

    ItemComponent item = new ItemComponent("Chicken", ItemType.CHICKEN, 1);
    assertFalse(inventory.find(item));
  }

  @Test
  void shouldNotAddItem() {
    InventoryComponent inventory = new InventoryComponent(5);
    ItemComponent item;
    for (int i = 0; i < 5; i++) {
      item = new ItemComponent("Beef", ItemType.BEEF, 1);
      inventory.addItem(item);
    }
  
    item = new ItemComponent("Chicken", ItemType.CHICKEN, 1);
    inventory.addItem(item);
    assertFalse(inventory.find(item)); // Needs some kind of reminder when adding to a full inventory
  }

  @Test
  void shouldSetSelected() {
    InventoryComponent inventory = new InventoryComponent(5);

    inventory.setSelected(2);
    assertEquals(2, inventory.getSelectedIndex());

    inventory.setSelected(0);
    assertEquals(0, inventory.getSelectedIndex());

    inventory.setSelected(4);
    assertEquals(4, inventory.getSelectedIndex());
  }

  @Test
  void shouldNotSetSelected() {
    InventoryComponent inventory = new InventoryComponent(5);

    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
      inventory.setSelected(-1);
    });

    assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.", e.getMessage());
    
    e = assertThrows(IllegalArgumentException.class, () -> {
      inventory.setSelected(5);
    });

    assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.", e.getMessage());
  }

  @Test
  void shouldGetSelectedItem() {
    InventoryComponent inventory = new InventoryComponent(5);
    ItemComponent item = new ItemComponent("Chicken", ItemType.CHICKEN, 1);
    inventory.addItem(item);

    inventory.setSelected(0);
    assertTrue(item.equals(inventory.getSelectedItem()));

    inventory.setSelected(1);
    assertNull(inventory.getSelectedItem());
  }

  // @Test
  // void shouldReturnItems() {
  //   InventoryComponent inventory = new InventoryComponent(2);
  //   assertEquals(0, inventory.getSize());
  // }


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

  // @Test
  // void shouldRemoveItemAt() {
  //   InventoryComponent inventory = new InventoryComponent(2);
  //   inventory.addItemAt("item", 0);
  //   String removedItem = inventory.removeAt(0);
  //   assertEquals("item", removedItem);
  // }

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

  /*
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
  */
  // add item logic issue.
  // @Test
  // void shouldBeFull() {
  //   InventoryComponent inventory = new InventoryComponent(2);
  //   for (int i = 0; i < 2; i++) {
  //     inventory.addItemAt("item", i);
  //   }
  //   assertEquals(true, inventory.isFull());
  // }

  // @Test
  // void shouldBeEmpty() {
  //   InventoryComponent inventory = new InventoryComponent(2);
  //   assertEquals(true, inventory.isEmpty());
  // }

  // @Test
  // void shouldBeNotEmpty() {
  //   InventoryComponent inventory = new InventoryComponent(2);
  //   inventory.addItemAt("item", 0);
  //   assertEquals(false, inventory.isEmpty());
  // }

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
