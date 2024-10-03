package com.csse3200.game.components.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.*;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.DishFactory;
import com.csse3200.game.entities.factories.ItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StationMealComponent extends Component {
    /**
     * String type - storing type of station
     * StationInventoryComponent inventorycomponent - instance of inventory for this station
     * TBD acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     */
    // A logger for the class
    private static final Logger logger = LoggerFactory.getLogger(StationMealComponent.class);
    protected final String type;    
    protected InventoryComponent inventoryComponent;        // initialised with capacity = 4
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
     * @param playerInventoryComponent reference to player inventory component
     * @param inventoryDisplay reference to individual inventory display
     * @param type the type of interaction attempt
     */
    public void handleInteraction(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay, String type) {
        // Log info to console
        logger.info("BEFORE STATION ITEMS {}\nBEFORE PLAYER ITEMS {}", 
                this.inventoryComponent.getItemNames(), 
                playerInventoryComponent.getItemNames());

        // Check if interaction was a chopping attempt
        if (type.equals("chop") || type.equals("stopChop")) {
            return; // Do nothing exit func
        }

        // Don't do anything if 'chop' is sent
        if (type.equals("receive")) {
        }

        // Check if interaction was a combine attempt
        if (type.equals("combine")) {
            this.processMeal();

            // Log info to console
            logger.info("Combination attempt made");
            logger.info("AFTER STATION ITEMS {}\nAFTER PLAYER ITEMS {}",
                    this.inventoryComponent.getItemNames(), 
                    playerInventoryComponent.getItemNames());
            return;
        }

        // Check if player is trying to rotate position of items for selection
        if (type.equals("rotate")) {
            if (this.inventoryComponent.isEmpty() || this.inventoryComponent.getSize() == 1) {
                // Don't allow rotate
                logger.info("Rotate attempt made, nothing to rotate");

            } else {
                this.rotateInventory();
            }
            return;
        }

        // Pre calcs
        boolean empty = playerInventoryComponent.isEmpty() & this.inventoryComponent.isEmpty();
        boolean full = playerInventoryComponent.isFull() & this.inventoryComponent.isFull();

        if (empty | full) {
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
        
        } else if (!inventoryComponent.isEmpty()) {
            // Player wants meal from station, if possible results in meal in player inventory
            this.stationGiveItem(playerInventoryComponent, inventoryDisplay);
        }

        // Log info to console
        logger.info("AFTER STATION ITEMS {}\nAFTER PLAYER ITEMS {}",
                this.inventoryComponent.getItemNames(), 
                playerInventoryComponent.getItemNames());
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
        // add an item to the station inventory if there is room
        if (!this.inventoryComponent.isFull()) {
            this.inventoryComponent.addItem(item);
            playerInventoryComponent.removeAt(0);
        }
    }

    /**
     * Takes the item from the station, and returns the old item
     * 
     * @param playerInventoryComponent - reference to player inventory
     * @param inventoryDisplay - reference to UI for inventory display
     */
    public void stationGiveItem(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        ItemComponent item = this.inventoryComponent.removeItem();
        playerInventoryComponent.addItemAt(item,0);
    }

    /**
     * If possible, creates a meal from the current items in the station inventory
     * and return it to the station inventory.
     */
    private void processMeal() {
        Optional<String> possibleRecipe = mealFactory.getRealRecipe(this.inventoryComponent.getItemNames()); 
        
        if (!possibleRecipe.isEmpty()) {
            // get first valid recipe
            String recipe = possibleRecipe.get();
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
            Entity mealEntity = ItemFactory.createMeal(recipe, ingredients);
            MealComponent meal = mealEntity.getComponent(MealComponent.class);
            // MealComponent meal = new MealComponent(recipe, ItemType.MEAL, 0, ingredients, 0);
            this.inventoryComponent.addItemAt(meal, 0);
        }
    }

    /**
     * Rotate the items in station inventory when input received from user
     */
    private void rotateInventory() {
        ItemComponent last = this.inventoryComponent.removeItem();
        for (int index = this.inventoryComponent.getSize() - 1; index >= 0; index--) {
            if (this.inventoryComponent.getItemAt(index) != null) {
                ItemComponent holder = this.inventoryComponent.removeAt(index);
                this.inventoryComponent.addItemAt(last, index);
                last = holder;
            }
        }
        this.inventoryComponent.addItem(last);
    }
}
