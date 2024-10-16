package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IngredientStationHandlerComponent extends Component {
    /**
     * String type - storing type of station
     * StationInventoryComponent inventory component - instance of inventory for this station
     * TBD acceptableItems - HashMap, HashSet etc. of mappings for acceptable items based on station
     */
    protected final String type;
    protected InventoryComponent inventoryComponent;
    protected StationCollectionComponent collectionComponent;
    protected final String ingredient;
    private static final Logger logger = LoggerFactory.getLogger(IngredientStationHandlerComponent.class);

    /**
     * General constructor
     * @param type - storing type of station
     */
    public IngredientStationHandlerComponent(String type, String ingredient) {
        this.type = type;
        this.ingredient = ingredient;
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

        IngredientComponent itemComponent = getIngredient(this.ingredient);

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
     * Gets the ingredient stored at this station
     * @return String of ingredient
     */
    public String getIngredientName() { return this.ingredient;}

    /**
     * Handles any interaction with station, using current state of player and station
     * inventory to determine intended interaction
     * @param playerInventoryComponent reference to player inventory component
     * @param inventoryDisplay reference to individual inventory display
     * @param type the type of interaction attempt
     */
    public void handleInteraction(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay, String type) {
        if (!type.equals("default")) { // Return if not default interaction
            return;
        }
        
        if (!playerInventoryComponent.isFull()) {
            stationGiveItem(playerInventoryComponent, inventoryDisplay);
            logger.debug("INTERACTED WITH BASKET");
        }
    }

    /**
     Takes the item from the station, and returns the old item
     @param playerInventoryComponent reference to player inventory
     */
    public void stationGiveItem(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        ItemComponent item = this.inventoryComponent.getItemFirst();
        playerInventoryComponent.addItemAt(item,0);
        inventoryDisplay.update();
        this.inventoryComponent.removeAt(0);

        // Create Entity and give an Item Component
        IngredientComponent itemComponent = getIngredient(this.ingredient);

        this.inventoryComponent.addItemAt(itemComponent, 0);
    }

    /**
     * Function to get the ingredient of the specified type
     * @param ingredientType the type of ingredient to get as a string
     * @return the ingredient component of the created entity
     */
    private IngredientComponent getIngredient(String ingredientType) {
        Entity newItem = collectionComponent.collectItem(ingredientType);

        return newItem.getComponent(IngredientComponent.class);
    }

}
