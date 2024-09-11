package com.csse3200.game.components.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.CookIngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.components.items.MealComponent;
import com.csse3200.game.entities.factories.DishFactory;;

public class StationMealComponent extends Component {
    /**
     * String type - storing type of station
     * StationInventoryComponent inventorycomponent - instance of inventory for this station
     * TBD acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     */
    protected final String type;
    protected InventoryComponent inventoryComponent;
    protected final ArrayList<String> acceptableItems;
    private final DishFactory mealFactory;

    /**
     * General constructor
     * 
     * @param type - storing type of station
     * @param acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     */
    public StationMealComponent(String type, ArrayList<String> acceptableItems) {
        this.type = type;
        this.acceptableItems = acceptableItems;
        this.mealFactory = new DishFactory();
    }

    /**
     *  Called on creation of the station to allow outside interaction within the station.
     *  Adds the listener for set current item and for remove current item.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("Station Interaction", this::handleInteraction);
        inventoryComponent = entity.getComponent(InventoryComponent.class);
    }

    /**
     * Gets the type of station
     * 
     * @return - station type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Checks if the item can be accepted
     * 
     * @param item - to check if can be accepted
     * @return - true if it can be acceptedd, false otherwise.
     */
    public boolean isItemAccepted(ItemComponent item) {
        return true;
    }

    /**
     * Handles any interaction with station, using current state of player and station
     * inventory to determine intended interaction
     * 
     * @param playerInventoryComponent - reference to player inventory component
     * @param inventoryDisplay - reference to UI for inventory component
     */
    public void handleInteraction(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        // Pre calcs
        boolean full = playerInventoryComponent.isFull() & this.inventoryComponent.isFull();
        boolean empty = playerInventoryComponent.isEmpty() & this.inventoryComponent.isEmpty();

        if (full | empty) {
            // nothing should happen, neither can do anything
        } else if (playerInventoryComponent.isFull()) {
            // Input to station
            ItemComponent item = playerInventoryComponent.getItemFirst();
            
            // Check item is accepted
            if (!isItemAccepted(item)) {
                // item not valid in current station
            } else {
                // add item to the station and perform check for meal
                this.stationReceiveItem(item, playerInventoryComponent, inventoryDisplay);
            }
        
        } else if (inventoryComponent.isFull()) {
            // Ppayer wants meal from station, if possible results in meal in player inventory
            this.stationGiveItem(playerInventoryComponent, inventoryDisplay);
        }
    }


    /**
     * Gets a look at the current item being stored.
     * 
     * @return - current Item being stored
     */
    public ItemComponent peek() {
        return this.inventoryComponent.getItemFirst();
    }

    /**
     * Takes the item from the player and stores in station
     * 
     * @param item - valid item to be stored in the station inventory.
     * @param playerInventoryComponent - reference to player inventory
     * @param inventoryDisplay - reference to UI for inventory display
     */
    public void stationReceiveItem(ItemComponent item, InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        // increase the capacity of the inventory and add the new item
        this.inventoryComponent.increaseCapacity(this.inventoryComponent.getCapacity() + 1);
        this.inventoryComponent.addItemAt(item, this.inventoryComponent.getSize() - 1);
        playerInventoryComponent.removeAt(0);
        
        // processes a meal from the station inventory if possible
        if (this.inventoryComponent.getSize() > 1) {
            this.processMeal();
        }

        // do i need to end the interaction here?
    }

    /**
     * Takes the item from the station, and returns the old item
     * 
     * @param playerInventoryComponent - reference to player inventory
     * @param inventoryDisplay - reference to UI for inventory display
     */
    public void stationGiveItem(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        // check if there is a meal in the inventory
        int mealIndex = this.mealExists();
        if (mealIndex > 0) {
            // item swapping
            ItemComponent item = inventoryComponent.getItemAt(mealIndex);
            playerInventoryComponent.addItemAt(item,0);
            
            // Remove single item in station
            this.inventoryComponent.removeAt(mealIndex);

            // do i need this here?
            entity.getEvents().trigger("interactionEnd");
        }
    }

    /**
     * 
     * @return
     */
    private int mealExists() {
        for (int index = 0; index < this.inventoryComponent.getCapacity(); index++) {
            ItemComponent item = this.inventoryComponent.getItemAt(index);
            if (item.getItemType() == ItemType.MEAL) {
                return index;
            }
        }

        return -1;
    }

    /**
     * 
     */
    private void processMeal() {
        List<String> possibleRecipes = mealFactory.getRecipe(this.inventoryComponent.getItemNames());
        
        if (!possibleRecipes.isEmpty()) {
            // get first valid recipe
            // String currentRecipe = possibleRecipes.getFirst();
            List<IngredientComponent> ingredients = new ArrayList<>();

            // process items to be IngredientComponents
            for (int index = 0; index < this.inventoryComponent.getCapacity(); index++) {
                ItemComponent item = this.inventoryComponent.getItemAt(index);
                if (item != null) {
                    this.inventoryComponent.removeAt(index);
                    ingredients.add((IngredientComponent) item);
                }
            }
            
            // create and return the first possible meal
            MealComponent meal = new MealComponent(possibleRecipes.getFirst(), ItemType.MEAL, 0, ingredients, 0);
            this.inventoryComponent.addItem(meal);
        }
    }
}
