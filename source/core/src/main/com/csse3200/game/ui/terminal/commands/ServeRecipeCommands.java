package com.csse3200.game.ui.terminal.commands;

import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.MealComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.station.StationServingComponent;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.entities.Entity;
import java.util.ArrayList;


/**
 * A command for serving recipes to the customer.
 */
public class ServeRecipeCommands implements Command {
  private static final Logger logger = LoggerFactory.getLogger(ServeRecipeCommands.class);
  /**
   * Processes the 'serve' command and serves the specified meal to the customer.
   *
   * @param args List of command arguments (expects 1 argument specifying the recipe name)
   * @return true if the command executed successfully, false otherwise
   */
  public boolean action(ArrayList<String> args) {
    // Validate the input arguments
    if (!isValid(args)) {
      logger.debug("Invalid arguments received for 'serve' command: {}", args);
      return false;
    }

    String arg = args.get(0);
    // Get the player entity and their inventory
    Entity player = ServiceLocator.getPlayerService().getPlayer();
    InventoryComponent playerInventory = player.getComponent(InventoryComponent.class);
    //StationServingComponent stationServingComponent = new StationServingComponent();




    // Handle different spawn commands based on the argument
    switch (arg) {
      case "bananasplit":
      // Remove the current item if inventory is not empty
        if (playerInventory.getSize() >= 1) {
            playerInventory.removeItem();
        }

        // Create ingredients for the Banana Split recipe
        Entity strawberry = ItemFactory.createBaseItem("strawberry");
        Entity chocolate = ItemFactory.createBaseItem("chocolate");
        Entity banana = ItemFactory.createBaseItem("banana");
    
        // Add ingredients to a list
        ArrayList<IngredientComponent> bananaSplitIngredients = new ArrayList<>();
        bananaSplitIngredients.add(strawberry.getComponent(IngredientComponent.class));
        bananaSplitIngredients.add(chocolate.getComponent(IngredientComponent.class));
        bananaSplitIngredients.add(banana.getComponent(IngredientComponent.class));
        
        // Create the Banana Split meal using the ingredients
        Entity bananaSplitEntity = ItemFactory.createMeal("bananaSplit", bananaSplitIngredients);
        ItemComponent bananaSplitItem = bananaSplitEntity.getComponent(MealComponent.class);
        
        // Add the Banana Split meal to the player's inventory
        playerInventory.addItem(bananaSplitItem);

        // Serve the meal out of the players inventory
        //stationServingComponent.submitMeal(bananaSplitItem);

        return true;

      case "steakmeal":
        // Remove the current item if the inventory is not empty
        if (playerInventory.getSize() >= 1) {
          playerInventory.removeItem();
        }

        // Create ingredients for the Steak Meal recipe
        Entity beef = ItemFactory.createBaseItem("beef");
        Entity tomato = ItemFactory.createBaseItem("tomato");
        Entity cucumber = ItemFactory.createBaseItem("cucumber");
    
        // Add ingredients to a list
        ArrayList<IngredientComponent> steakMealIngredients = new ArrayList<>();
        steakMealIngredients.add(beef.getComponent(IngredientComponent.class));
        steakMealIngredients.add(tomato.getComponent(IngredientComponent.class));
        steakMealIngredients.add(cucumber.getComponent(IngredientComponent.class));
        
        // Create the Steak Meal meal and add it to the player's inventory
        Entity steakMealEntity = ItemFactory.createMeal("steakMeal", steakMealIngredients);
        ItemComponent steakMealItem = steakMealEntity.getComponent(MealComponent.class);
        playerInventory.addItem(steakMealItem);

        // Serve the meal out of the players inventory
        //stationServingComponent.submitMeal(steakMealItem);

        return true;

      case "acaibowl":
        // Create an acai bowl recipe
        if (playerInventory.getSize() >= 1) {
            playerInventory.removeItem();
        }
        Entity acai = ItemFactory.createBaseItem("acai");
        Entity acaiBowlBanana = ItemFactory.createBaseItem("banana");
    
        ArrayList<IngredientComponent> acaiBowlIngredients = new ArrayList<>();
        acaiBowlIngredients.add(acai.getComponent(IngredientComponent.class));
        acaiBowlIngredients.add(acaiBowlBanana.getComponent(IngredientComponent.class));
        
        Entity acaiBowlEntity = ItemFactory.createMeal("acaiBowl", acaiBowlIngredients);
        ItemComponent acaiBowlItem = acaiBowlEntity.getComponent(MealComponent.class);
        playerInventory.addItem(acaiBowlItem);

        // Serve the meal out of the players inventory
        //stationServingComponent.submitMeal(acaiBowlItem);

        return true;

      case "salad":
        // Create a salad recipe
        if (playerInventory.getSize() >= 1) {
            playerInventory.removeItem();
        }
        Entity saladTomato = ItemFactory.createBaseItem("tomato");
        Entity lettuce = ItemFactory.createBaseItem("lettuce");
        Entity saladCucumber = ItemFactory.createBaseItem("cucumber");
    
        ArrayList<IngredientComponent> saladIngredients = new ArrayList<>();
        saladIngredients.add(saladTomato.getComponent(IngredientComponent.class));
        saladIngredients.add(lettuce.getComponent(IngredientComponent.class));
        saladIngredients.add(saladCucumber.getComponent(IngredientComponent.class));
        
        Entity saladEntity = ItemFactory.createMeal("salad", saladIngredients);
        ItemComponent saladItem = saladEntity.getComponent(MealComponent.class);
        playerInventory.addItem(saladItem);

        // Serve the meal out of the players inventory
        //stationServingComponent.submitMeal(saladItem);

        return true;

      case "fruitSalad":
        // Create a fruit salad recipe
        if (playerInventory.getSize() >= 1) {
            playerInventory.removeItem();
        }
        Entity saladBanana = ItemFactory.createBaseItem("banana");
        Entity saladStrawberry = ItemFactory.createBaseItem("strawberry");
    
        ArrayList<IngredientComponent> fruitSaladIngredients = new ArrayList<>();
        fruitSaladIngredients.add(saladBanana.getComponent(IngredientComponent.class));
        fruitSaladIngredients.add(saladStrawberry.getComponent(IngredientComponent.class));
        
        Entity fruitSaladEntity = ItemFactory.createMeal("fruitSalad", fruitSaladIngredients);
        ItemComponent fruitSaladItem = fruitSaladEntity.getComponent(MealComponent.class);
        playerInventory.addItem(fruitSaladItem);

        // Serve the meal out of the players inventory
        //stationServingComponent.submitMeal(fruitSaladItem);

        return true; 

      default:
        // Log unrecognised commands
        logger.debug("Unrecognised argument received for 'serve' command: {}", args);
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


