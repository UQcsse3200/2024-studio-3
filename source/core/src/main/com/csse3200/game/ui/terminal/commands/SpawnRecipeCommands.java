package com.csse3200.game.ui.terminal.commands;

import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.entities.Entity;


import java.util.ArrayList;


/**
 * A command for toggling debug mode on and off.
 */
public class SpawnRecipeCommands implements Command {
  private static final Logger logger = LoggerFactory.getLogger(SpawnRecipeCommands.class);
  /**
   * Toggles debug mode on or off if the corresponding argument is received.
   * @param args command arguments
   */
  public boolean action(ArrayList<String> args) {
    if (!isValid(args)) {
      logger.debug("Invalid arguments received for 'spawn' command: {}", args);
      return false;
    }

    String arg = args.get(0);
    switch (arg) {
      case "bananasplit":
        // Spawn banana split in players inventory
        //InventoryComponent playerInventory = new InventoryComponent(1);
        //ItemComponent bananaSplit = new ItemComponent("BananaSplit", ItemType.BANANASPLIT, 1);
        //playerInventory.addItem(bananaSplit);
        return true;
      case "steakmeal":
        //WIP
        // Spawn steak meal in players inventory
        //ItemComponent steakMeal = new ItemComponent("SteakMeal", ItemType.STEAKMEAL, 1);
        //InventoryComponent.addItem(steakMeal);
        return true;
      case "acaibowl":
        // Spawn acai bowl in player inventory
        //ItemComponent acaiBowl = new ItemComponent("AcaiBowl", ItemType.ACAIBOWL, 1);
        //InventoryComponent.addItem(acaiBowl);
        return true;
      case "salad":
        // Spawn salad in player inventory
        //ItemComponent salad = new ItemComponent("Salad", ItemType.SALAD, 1);
        //InventoryComponent.addItem(salad);
        return true;
      case "fruitSalad":
        //ItemComponent fruitSalad = new ItemComponent("fruitSalad", ItemType.FRUITSALAD, 1);
        //InventoryComponent.addItem(fruitSalad);
        return true; 
      default:
        logger.debug("Unrecognised argument received for 'spawn' command: {}", args);
        return false;
    }
  }

  /**
   * Validates the command arguments.
   * @param args command arguments
   * @return is valid
   */
  boolean isValid(ArrayList<String> args) {
    return args.size() == 1;
  }
}


