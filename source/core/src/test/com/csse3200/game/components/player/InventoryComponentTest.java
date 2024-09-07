package com.csse3200.game.components.player;

import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemType;

import java.util.ArrayList;
import java.util.Arrays;

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

    ItemComponent item = new ItemComponent("Beef", ItemType.BEEF, 1);
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
  
    item = new ItemComponent("Beef", ItemType.BEEF, 1);
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

    assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.",
            e.getMessage());
    
    e = assertThrows(IllegalArgumentException.class, () -> {
      inventory.setSelected(5);
    });

    assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.",
            e.getMessage());
  }

  @Test
  void shouldGetSelectedItem() {
    InventoryComponent inventory = new InventoryComponent(5);
    ItemComponent item = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(item);

    inventory.setSelected(0);
    assertEquals(item, inventory.getSelectedItem());

    inventory.setSelected(1);
    assertNull(inventory.getSelectedItem());
  }

  @Test
  void shouldGetEmptyItems() {
    InventoryComponent inventory = new InventoryComponent(2);
    ArrayList<ItemComponent> items = inventory.getItems();
    assertNotNull(items);
    for (ItemComponent item : items) {
        assertNull(item);
    }
  }

  @Test
  void shouldAddItems() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(chocolate);
    inventory.addItem(beef);

    // what the items ArrayList should look like
    ArrayList<ItemComponent> ideal = new ArrayList<>(Arrays.asList(chocolate, beef));
    assertEquals(ideal, inventory.getItems());
  }

  @Test
  void shouldReturnItemClone() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(chocolate);
    inventory.addItem(beef);
    ArrayList<ItemComponent> clone = inventory.getItems();
    // modify the clone
    clone.remove(beef);
    assertNotEquals(clone, inventory.getItems());
  }

  @Test
  void shouldBeEmptyAfterRemove() {
    // add and remove items
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(chocolate);
    inventory.addItem(beef);
    inventory.removeAt(0);
    inventory.removeAt(1);
    assertTrue(inventory.isEmpty());
  }

  @Test
  void shouldGetNull() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(beef);
    assertNull(inventory.getItemAt(1));
  }

  @Test
  void shouldGetCorrectItem() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(beef);
    assertEquals(beef, inventory.getItemAt(0));
  }

  @Test
  void shouldNotGetItemWithNegIndex() {
    InventoryComponent inventory = new InventoryComponent(2);
    try {
      inventory.getItemAt(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.",
              e.getMessage());
    }
  }

  @Test
  void shouldNotGetItemWithInvalidIndex() {
    InventoryComponent inventory = new InventoryComponent(2);
    try {
      inventory.getItemAt(3);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.",
              e.getMessage());
    }
  }

  @Test
  void firstItemShouldBeNull() {
    InventoryComponent inventory = new InventoryComponent(2);
    assertNull(inventory.getItemFirst());
  }

  @Test
  void shouldGetFirstItem() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(beef);
    assertEquals(beef, inventory.getItemFirst());
  }

  @Test
  void lastItemShouldBeNull() {
    InventoryComponent inventory = new InventoryComponent(2);
    assertNull(inventory.getItemLast());
  }

  @Test
  void shouldGetLastItem() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    inventory.addItem(chocolate);
    inventory.addItem(beef);
    assertEquals(beef, inventory.getItemLast());
  }

  @Test
  void shouldIncreaseAndKeepExistingItems() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    inventory.addItem(chocolate);
    inventory.increaseCapacity(3);
    assertEquals(chocolate, inventory.getItemAt(0));
    assertNull(inventory.getItemAt(1));
  }

  @Test
  void shouldAddToFirstSlot() {
    InventoryComponent inventory = new InventoryComponent(3);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    ItemComponent acai = new ItemComponent("Acai", ItemType.ACAI, 1);
    inventory.addItemAt(chocolate, 0);
    inventory.addItemAt(beef, 2);
    inventory.addItem(acai);
    assertEquals(acai, inventory.getItemAt(1));
  }

  @Test
  void shouldAddItemAt() {
    InventoryComponent inventory = new InventoryComponent(3);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    inventory.addItemAt(chocolate, 1);
    assertEquals(chocolate, inventory.getItemAt(1));
  }

  @Test
  void shouldAddAndIncreaseSize() {
    InventoryComponent inventory = new InventoryComponent(3);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    inventory.addItemAt(chocolate, 1);
    assertEquals(1, inventory.getSize());
  }

  @Test
  void shouldNotAddAtNegativeIndex() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    try {
      inventory.addItemAt(chocolate,-3);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.",
              e.getMessage());
    }
  }

  @Test
  void shouldNotAddAtInvalidIndex() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    try {
      inventory.addItemAt(chocolate,3);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.",
              e.getMessage());
    }
  }

  @Test
  void shouldNotAddWhenAlreadyOccupied() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    ItemComponent acai = new ItemComponent("Acai", ItemType.ACAI, 1);
    inventory.addItemAt(chocolate,1);

    try {
      inventory.addItemAt(acai,1);
    } catch (IllegalArgumentException e) {
      assertEquals("Index in Inventory already occupied by an Item.", e.getMessage());
    }
  }

  @Test
  void shouldRemoveAtIndex() {
    InventoryComponent inventory = new InventoryComponent(2);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    inventory.addItemAt(chocolate,1);
    assertEquals(chocolate, inventory.removeAt(1));
  }

  @Test
  void shouldDecreaseSize() {
    InventoryComponent inventory = new InventoryComponent(5);
    assertFalse(inventory.isFull());

    ItemComponent item;
    for (int i = 0; i < 3; i++) {
      item = new ItemComponent("Beef", ItemType.BEEF, 1);
      inventory.addItem(item);
    }
    inventory.removeAt(1);
    assertEquals(2, inventory.getSize());
  }

  @Test
  void shouldNotRemoveAtNegativeIndex() {
    InventoryComponent inventory = new InventoryComponent(2);
    try {
      inventory.removeAt(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.",
              e.getMessage());
    }
  }

  @Test
  void shouldNotRemoveAt() {
    InventoryComponent inventory = new InventoryComponent(2);
    try {
      inventory.removeAt(3);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid index parameter. Must be non-negative and within the current size of the inventory.",
              e.getMessage());
    }
  }

  @Test
  void shouldNotRemoveEmptySlot() {
    InventoryComponent inventory = new InventoryComponent(2);
    try {
      inventory.removeAt(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Index in Inventory does not contain an Item.", e.getMessage());
    }
  }

  @Test
  void shouldAddMultipleItemsAndRemove() {
    InventoryComponent inventory = new InventoryComponent(3);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    ItemComponent acai = new ItemComponent("Acai", ItemType.ACAI, 1);
    inventory.addItemAt(chocolate, 0);
    inventory.addItemAt(beef, 2);
    inventory.addItem(acai);
    // remove all but 1st element
    inventory.removeAt(2);
    inventory.removeAt(1);
    assertEquals(chocolate, inventory.getItemAt(0));
    inventory.removeAt(0);
    // should be empty
    assertTrue(inventory.isEmpty());
  }

  @Test
  void shouldFillInventoryRemoveAndAddAgain() {
    InventoryComponent inventory = new InventoryComponent(3);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    ItemComponent acai = new ItemComponent("Acai", ItemType.ACAI, 1);
    // fill inventory
    inventory.addItem(chocolate);
    inventory.addItem(beef);
    inventory.addItem(acai);
    assertTrue(inventory.isFull());
    // empty inventory
    for (int i = 0; i < 3; i++) {
      inventory.removeAt(i);
    }
    assertTrue(inventory.isEmpty());
    // add items again
    inventory.addItemAt(chocolate, 0);
    assertEquals(chocolate, inventory.getItemAt(0));
  }

  @Test
  void shouldIncreaseCapacityAndAdd() {
    InventoryComponent inventory = new InventoryComponent(1);
    ItemComponent chocolate = new ItemComponent("Chocolate", ItemType.CHOCOLATE, 1);
    inventory.addItem(chocolate);
    assertTrue(inventory.isFull());
    // increase the capacity
    inventory.increaseCapacity(3);
    // add more items
    ItemComponent beef = new ItemComponent("Beef", ItemType.BEEF, 1);
    ItemComponent acai = new ItemComponent("Acai", ItemType.ACAI, 1);
    inventory.addItem(beef);
    inventory.addItem(acai);
    assertTrue(inventory.isFull());
    // check if everything added right
    assertEquals(chocolate, inventory.getItemAt(0));
    assertEquals(beef, inventory.getItemAt(1));
    assertEquals(acai, inventory.getItemAt(2));
  }
}
