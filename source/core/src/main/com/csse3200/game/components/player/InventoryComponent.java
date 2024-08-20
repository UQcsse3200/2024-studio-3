package com.csse3200.game.components.player;

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

  public InventoryComponent(int size) {
      setSize(size);
      items = new ArrayList<String>();
  }

  /**
   * Returns the size of this inventory component. I.e. the
   * maximum number of items it can hold
   * @return entity's health
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Sets the size of this item component to the newSize provided
   * @throws java.lang.IllegalArgumentException if newSize is < 1
   * @param newSize the size to set this item component's capacity to. Must be a positive, non-zero integer
   */
  public void setSize(int newSize) {
      if (newSize < 1) {
        throw new IllegalArgumentException("Invalid size parameter. Must be an integer > 0");
      }
      this.size = newSize;
  }

    /**
     * Returns an ArrayList containing the items stored in this
     * InventoryComponent. Currently, these are represented as strings
     * @return an ArrayList containing items currently stored in the inventory
     */
  public ArrayList<String> getItems() {
      return (ArrayList) items.clone();
  }

    /**
     * Returns true if the number items currently stored in the
     * Inventory is equal to the maximum size of the inventory, false otherwise
     * @return If the inventory is full or not
     */
  public boolean isFull() {
      return items.size() == this.size;
  }
}
