package com.csse3200.game.components.station;

import java.util.Random;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.entities.Entity;

public class IngredientStationHandlerComponent extends Component {
    /**
     * String type - storing type of station
     * StationInventoryComponent inventorycomponent - instance of inventory for this station
     * TBD acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     */
    protected final String type;
    protected InventoryComponent inventoryComponent;
    protected StationCollectionComponent collectionComponent;
    private final String[] ingredientList = {"acai", "beef", "banana", "lettuce", "cucumber", "tomato", "strawberry", "chocolate", "fish"};

    // General TODO:
    // Add trigger calls to external for failed interactions
    // Introduce an actual structure for acceptable items, json parsing etc
    // Processing in Inventory component, animation, timing and mapping
    // Create subclass for each station where needed, eg classic bench will need
    //      to call add second component method that we dont want all stations to be able to access

    /**
     * General constructor
     * @param type - storing type of station
     */
    public IngredientStationHandlerComponent(String type) {
        this.type = type;

    }

    /**
     *  Called on creation of the station to allow outside interaction within the station.
     *  Adds the listener for set current item and for remove current item.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("Station Interaction", this::handleInteraction);
        this.inventoryComponent = entity.getComponent(InventoryComponent.class);
        this.collectionComponent = entity.getComponent(StationCollectionComponent.class);

        // Get a random ingredient for now
        IngredientComponent itemComponent = getRandomIngredient();

        ///this.inventoryComponent.addItemAt(new ItemComponent("Apples", ItemType.APPLE, 1), 0);
        this.inventoryComponent.addItemAt(itemComponent, 0);
    }

    /**
     * Gets the type of station
     * @return station type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Handles any interaction with station, using current state of player and station
     * inventory to determine intended interaction
     * @param playerInventoryComponent reference to player inventory component
     */
    public void handleInteraction(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        if (playerInventoryComponent.isFull()) {
            // do nothing
        } else {
            stationGiveItem(playerInventoryComponent, inventoryDisplay);
        }
    }

    /**
     Takes the item from the station, and returns the old item
     @param playerInventoryComponent reference to player inventory
     */
    public void stationGiveItem(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        // entity.getEvents().trigger("showTooltip", "You took something from the station!");
        ItemComponent item = this.inventoryComponent.getItemFirst();
        playerInventoryComponent.addItemAt(item,0);
        this.inventoryComponent.removeAt(0);

        // TODO: create a new entity instead of the item component and then get the item
        // component from the entity and then put that in the inventory
        // For now just get a random ingredient WILL CHANGE LATER
        IngredientComponent itemComponent = getRandomIngredient();

        ///this.inventoryComponent.addItemAt(new ItemComponent("Apples", ItemType.APPLE, 1), 0);
        this.inventoryComponent.addItemAt(itemComponent, 0);
        inventoryDisplay.update();
        entity.getEvents().trigger("interactionEnd");
    }

    private IngredientComponent getRandomIngredient() {
        int numIngredients = ingredientList.length;
        int randomInt = new Random().nextInt(numIngredients);
        String ingredient = ingredientList[randomInt];
        Entity newItem = collectionComponent.collectItem(ingredient);
        IngredientComponent itemComponent = newItem.getComponent(IngredientComponent.class);

        return itemComponent;
    }
}
