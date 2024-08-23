package com.csse3200.game.components.player;

import com.badlogic.gdx.utils.Null;
import com.csse3200.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.lang.IllegalArgumentException;

/* team 1 feature branch, work here */

/**
 * A generic inventory component that is used to keep track of stored items
 * for an entity
 */
public class InventoryComponent extends Component {
  private static final Logger logger = LoggerFactory.getLogger(InventoryComponent.class);
  private ArrayList<String> items; // String as a placeholder for future ItemComponent class
  private int size; // maximum number of items that can be stored
  private final String indexException = "Invalid index parameter. Must be non-negative and within the current size of the inventory.";
  private final String sizeException = "Invalid size parameter. Must be an integer > 0";

  public InventoryComponent(int size) {
      setSize(size);
      items = new ArrayList<String>();
  }

  /**
   * Returns the capacity of this inventory component. I.e. the
   * maximum number of items it can hold.

   * @return - the set size of the inventory.
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Sets the size of this item component to the newSize provided.
   * 
   * @param newSize - the size to set this item component's capacity to. Must be a positive, non-zero integer.
   * @throws java.lang.IllegalArgumentException - if newSize is < 1.
   */
  public void setSize(int newSize) {
      if (newSize < 1) {
        throw new IllegalArgumentException(sizeException);
      }
      this.size = newSize;
  }

    /**
     * Returns an ArrayList containing the items stored in this
     * InventoryComponent. Currently, these are represented as strings.
     * 
     * @return - an ArrayList containing items currently stored in the inventory.
     */
  public ArrayList<String> getItems() {
      return (ArrayList) items.clone();
  }

    /**
     * Returns true if the number items currently stored in the
     * Inventory is equal to the maximum size of the inventory, false otherwise.
     * 
     * @return - true if the ArrayList is full, false otherwise.
     */
  public boolean isFull() {
      return items.size() == this.size;
  }

    /**
     * Returns true if the Inventory is empty.
     * 
     * @return - true if the ArrayList is empty, true otherwise.
     */
  public boolean isEmpty() {
    return items.isEmpty();
  }

    /**
     * Returns the item at index if it exists in the current inventory.
     * 
     * @param index - the index being queried.
     * @return the item at index if it exists in the current inventory, null if there is nothing there.
     * @throws java.lang.IllegalArgumentException - if the given index is negative or out of bounds.
     */
  public String getItemAt(int index) {
    if (index < 0 || index > size) {
      // index out of bounds
      throw new IllegalArgumentException(indexException);
    } else if (index > items.size()) {
      // index in bounds but nothing is stored there
      return null;
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
  public String getItemFirst() {
    if (!items.isEmpty()) {
      return items.getFirst();
    } else {
      return null;
    }
  }

    /**
     * Returns the last element of the Inventory if it is not empty.
     * 
     * @return - the item at the last index of the Inventory if it exists, null otherwise.
     */
  public String getItemLast() {
    if (!items.isEmpty()) {
      return items.getLast();
    } else {
      return null;
    }
  }

    /**
     * Increases the capacity of the Inventory if the given newSize is larger 
     * than the current size.
     * 
     * @param newSize the new capacity of the Inventory.
     */
  public void increaseSize(int newSize) {
    if (newSize > size) {
      setSize(newSize);
    }
  }

    /**
     * Checks if at least one instance of item is in the Inventory. 
     * 
     * @param item - the item to search the Inventory for.
     * @return - true if there is at least one instance of item, false otherwise.
     */
  public boolean find(String item) {
    return items.contains(item);
  }

    /**
     * Adds the item to the end of the Inventory, if there is capacity.
     * 
     * @param item - the item to be added to the Inventory.
     */
  public void appendItem(String item) {
    if (!this.isFull()) {
      items.add(item);
      size++;
    } 
  }

    /**
     * Adds the item to the start of the Inventory, if there is capacity.
     * 
     * @param item - the item to be added to the Inventory.
     */
  public void prependItem(String item) {
    if (!this.isFull()) {
      items.addFirst(item);
      size++;
    }
  }

    /**
     * Adds the item to the Inventory at the given index, if there is capacity.
     * 
     * @param item - the item to be added to the Inventory.
     * @param index - the index where the item will be inserted.
     * @throws java.lang.IllegalArgumentException - if the given index is negative or out of bounds.
     */
  public void addItemAt(String item, int index) {
    if (index > this.getSize() || index < 0) {
      // index out of bounds
      throw new IllegalArgumentException(indexException);
    } 

    if (!this.isFull()) {
      items.add(index, item);
      this.size++;
    } 
  }

    /**
     * Removes and returns the item from the Inventory at the given index, if it exists.
     * 
     * @param index - the index where an item will be removed.
     * @throws java.lang.IllegalArgumentException - if the given index is negative or out of bounds.
     */
  public String removeAt(int index) {
    if (index < 0 || index > size) {
      // index out of bounds
      throw new IllegalArgumentException(indexException);
    } else if (index > items.size()) {
      // nothing in bounds but nothing is stored there
      return null;
    } else {
      // bingo, we can remove from this index
      String item = items.get(index);
      items.remove(index);
      size--;

      return item;
    } 
  }

}
