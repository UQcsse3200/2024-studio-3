package com.csse3200.game.components.player;

import com.csse3200.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.items.ItemComponent;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

/**
 * A generic inventory component that is used to keep track of stored items
 * for an entity
 */
public class InventoryComponent extends Component {
  private static final Logger logger = LoggerFactory.getLogger(InventoryComponent.class);
  private ArrayList<ItemComponent> items;
  private int capacity; // maximum number of items that can be stored
  private int size;     // current number of items in the Inventory 
  private int selected; // index of currently selected item
  private final String indexException = "Invalid index parameter. Must be non-negative and within the current size of the inventory.";
  private final String sizeException = "Invalid size parameter. Must be an integer > 0.";
  private final String itemException = "Index in Inventory already occupied by an Item.";
  private final String nullException = "Index in Inventory does not contain an Item.";

  /**
   * Creates inventory component
   * @param capacity the players inventory size
   */
  public InventoryComponent(int capacity) {
    setCapacity(capacity);
    items = new ArrayList<>(capacity);
    for (int i = 0; i < capacity; i++) {        
      items.add(null);
    }
    size = 0;
    setSelected(0);
  }

  /**
   * Returns the capacity of this inventory component. I.e. the
   * maximum number of items it can hold.

   * @return - the set capacity of the inventory.
   */
  public int getCapacity() {
    return this.capacity;
  }

  /**
   * Returns the current number of items in the inventory component. 

   * @return - the number of items in the inventory.
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Sets the size of this item component to the newSize provided.
   * 
   * @param newCapacity - the size to set this item component's capacity to. Must be a positive, non-zero integer.
   * @throws IllegalArgumentException - if newSize is less than 1.
   */
  public void setCapacity(int newCapacity) {
      if (newCapacity < 1) {
        throw new IllegalArgumentException(sizeException);
      }
      this.capacity = newCapacity;
  }

  /**
   * Sets the currently selected item to the index specified.
   * 
   * @param index - the index to set the currently selected item to
   * @throws IllegalArgumentException - if index is not within 0 and this.getSize()
   */
  public void setSelected(int index) {
    if (index < 0 || index >= this.getCapacity()) {
      throw new IllegalArgumentException(indexException);
    }
    this.selected = index;
  }

  /**
   * Returns the index of the currently selected item.
   * @return - the index of the currently selected item.
   */
  public int getSelectedIndex() {
    return this.selected;
  }

  /**
   * Returns the currently selected item of the inventory
   * @return - the item that is currently selected
   */
  public ItemComponent getSelectedItem() {
    return this.getItemAt(this.getSelectedIndex());
  }

    /**
     * Returns an ArrayList containing the items stored in this
     * InventoryComponent.
     * 
     * @return - an ArrayList containing items currently stored in the inventory.
     */
  public ArrayList<ItemComponent> getItems() {
      return (ArrayList) items.clone();
  }

    /**
     * Returns true if Inventory data structure is full.
     * 
     * @return - true if the ArrayList is full, false otherwise.
     */
  public boolean isFull() {
      return size == this.capacity;
  }

    /**
     * Returns true if the Inventory data structure is empty.
     * 
     * @return - true if the ArrayList is empty, true otherwise.
     */
  public boolean isEmpty() {
    return size == 0;
  }

    /**
     * Returns the item at index if it exists in the current inventory.
     * 
     * @param index - the index being queried.
     * @return the item at index if it exists in the current inventory, null if there is nothing there.
     * @throws IllegalArgumentException - if the given index is negative or out of bounds.
     */
  public ItemComponent getItemAt(int index) {
    if (index < 0 || index > capacity) {
      // index out of bounds
      throw new IllegalArgumentException(indexException);
    } else {
      // item at index
      return items.get(index);
    } 
  }

    /**
     * Returns the first element of the Inventory if it is not empty.
     * 
     * @return - the item at the first index of the Inventory if it exists, null otherwise.
     */
  public ItemComponent getItemFirst() {
    if (capacity > 0) {
      return items.getFirst();
    } 
    return null;
  }

    /**
     * Returns the last element of the Inventory if it is not empty.
     * 
     * @return - the item at the last index of the Inventory if it exists, null otherwise.
     */
  public ItemComponent getItemLast() {
    if (capacity > 0) {
      return items.getLast();
    }
    return null;
  }

    /**
     * Increases the capacity of the Inventory if the given newSize is larger 
     * than the current size.
     * 
     * @param newCapacity the new capacity of the Inventory.
     */
  public void increaseCapacity(int newCapacity) {
    if (newCapacity > capacity) {
      for (int i = capacity; i < newCapacity; i++) {
        items.add(null);
      }
      setCapacity(newCapacity);
    }
  }

    /**
     * Checks if at least one instance of item is in the Inventory. 
     * 
     * @param item - the item to search the Inventory for.
     * @return - true if there is at least one instance of item, false otherwise.
     */
  public boolean find(ItemComponent item) {
    return items.contains(item);
  }

    /**
     * Adds the item to the first empty index of the inventory.
     * 
     * @param item - the item to be added to the Inventory.
     */
  public void addItem(ItemComponent item) {
    if (!this.isFull()) {
      int i = 0;
      while (items.get(i) != null) {
        i++;
      }
      items.set(i, item);
      size++;
    } 
  }
  
    /**
     * Adds the item to the Inventory at the given index, if there is capacity.
     * 
     * @param item - the item to be added to the Inventory.
     * @param index - the index where the item will be inserted.
     * @throws IllegalArgumentException - if the given index is negative or out of bounds.
     */
  public void addItemAt(ItemComponent item, int index) {
    if (index > capacity || index < 0) {
      // index out of bounds
      throw new IllegalArgumentException(indexException);
    } else if (items.get(index) != null) {
      throw new IllegalArgumentException(itemException);
    }

    if (!this.isFull()) {
      items.set(index, item);
      size++;
    } 
  }

    /**
     * Removes and returns the item from the Inventory at the given index, if it exists.
     * 
     * @param index - the index where an item will be removed.
     * @throws IllegalArgumentException - if the given index is negative or out of bounds.
     */
  public ItemComponent removeAt(int index) {
    if (index < 0 || index > capacity) {
      // index out of bounds
      throw new IllegalArgumentException(indexException);
    } else if (items.get(index) == null) {
        throw new IllegalArgumentException(nullException);
    } 
  
    if (!this.isEmpty()) {
      // bingo, we can remove from this index
      ItemComponent item = items.get(index);
      items.set(index, null);
      size--;

      return item; 
    }
    return null;
  }
}