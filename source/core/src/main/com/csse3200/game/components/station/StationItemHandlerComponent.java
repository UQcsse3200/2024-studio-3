package com.csse3200.game.components.station;

import java.util.ArrayList;
import java.util.Objects;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.CookIngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.components.player.InventoryDisplay;

public class StationItemHandlerComponent extends Component {
    /**
     * String type - storing type of station
     * StationInventoryComponent inventorycomponent - instance of inventory for this station
     * TBD acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     */
    protected final String type;
    protected InventoryComponent inventoryComponent;
    protected final ArrayList<String> acceptableItems;

    // General TODO:
    // Add trigger calls to external for failed interactions
    // Introduce an actual structure for acceptable items, json parsing etc
    // Processing in Inventory component, animation, timing and mapping
    // Create subclass for each station where needed, eg classic bench will need
    //      to call add second component method that we dont want all stations to be able to access

    /**
     * General constructor
     * @param type - storing type of station
     * @param acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     */
    public StationItemHandlerComponent(String type, ArrayList<String> acceptableItems) {
        this.type = type;
        this.acceptableItems = acceptableItems;

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
     * @return station type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Checks if the item can be accepted
     * @param item to check if can be accepted
     * @return true if it can be acceptedd, false otherwise.
     */
    public boolean isItemAccepted(ItemComponent item) {
        // TODO: Change this back after doing proper item acceptance

        /*String itemName = item.getItemName();

        for (String acceptableItem : this.acceptableItems) {
            if (acceptableItem.equals(itemName)) {
                return true;
            }
        }
        return false;*/
        return true;
    }

    /**
     * Handles any interaction with station, using current state of player and station
     * inventory to determine intended interaction
     * @param playerInventoryComponent reference to player inventory component
     */
    public void handleInteraction(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        // Pre calcs
        boolean full = playerInventoryComponent.isFull() & this.inventoryComponent.isFull();
        boolean empty = playerInventoryComponent.isEmpty() & this.inventoryComponent.isEmpty();

        if (full | empty) {
            // Throw an invalid interaction, red cross on station etc
            //entity.getEvents().trigger("showTooltip", "Why would you even try...");

        // Input to station
        } else if (playerInventoryComponent.isFull()) {
            ItemComponent item = playerInventoryComponent.getItemFirst();
            // Check item is accepted
            if (!isItemAccepted(item)) {
                // Throw an accept failure, interaction as item not valid in current station
                //entity.getEvents().trigger("showTooltip", "We don't accept that trash here...");
            } else {
                this.stationReceiveItem(item, playerInventoryComponent, inventoryDisplay);
            }
        // Output from station
        } else if (inventoryComponent.isFull()) {
            // Player wants item from station
            this.stationGiveItem(playerInventoryComponent, inventoryDisplay);
        }
    }


    /**
     *
     * @return current Item being stored
     */
    public ItemComponent peek() {
        return this.inventoryComponent.getItemFirst();
    }

    /**
     * Function to assist in updating the item to a new state
     * @param stop should the item be stopped from cooking 1 if yes, 0 otherwise
     */
    private void updateItem(int stop) {
        // Get the item in the inventory
        ItemComponent item = inventoryComponent.getItemFirst();

        // Attempt to cook the ingredient if it is cookable
        if (Objects.equals(this.type, "oven") || Objects.equals(this.type, "stove")) {
            boolean isCookable = false;

            if (item.getEntity().getComponent(IngredientComponent.class) == null) {
                return;
            }

            // Get if the item is cookable
            isCookable = item.getEntity().getComponent(IngredientComponent.class).getIsCookable();

            if (isCookable) {
                // TODO: Might not be able to properly cook item until timers are here but this works for now
                //item.getEntity().getComponent(IngredientComponent.class).cookItem();
                //item.getEntity().getComponent(CookIngredientComponent.class).cookIngredient("HOT", 1);
                //entity.getComponent(StationCookingComponent.class).cookIngredient();
                if (stop == 0) {
                    entity.getEvents().trigger("Cook Ingredient");
                } else {
                    entity.getEvents().trigger("Stop Cooking Ingredient");
                }
                
            }
        } else if (Objects.equals(this.type, "cutting board") || Objects.equals(this.type, "blender")) {
            boolean isChoppable = false;

            if (item.getEntity().getComponent(IngredientComponent.class) == null) {
                return;
            }

            // Get if the item is choppable
            isChoppable = item.getEntity().getComponent(IngredientComponent.class).getIsChoppable();

            if (isChoppable) {
                if (stop == 0) {
                    entity.getEvents().trigger("Chop Ingredient");
                } else {
                    entity.getEvents().trigger("Stop Chopping Ingredient");
                }
            }
        }

        // FOR DEBUGGING
        // Get the item info to display
        /*IngredientComponent itemInfo = item.getEntity().getComponent(IngredientComponent.class);
        String name = itemInfo.getItemName();
        boolean isCookable = itemInfo.getIsCookable();
        boolean isChoppable = itemInfo.getIsChoppable();
        String itemState = itemInfo.getItemState();
        boolean isCooking = false;

        if (isCookable) {
            CookIngredientComponent c = item.getEntity().getComponent(CookIngredientComponent.class);
            isCooking = c.getIsCooking();
        }

        String formatString = String
        .format("ItemInfo: %s\ncookable %b\nchoppable %b\nCooking %b\nstate %s", name, isCookable, isChoppable, isCooking, itemState);
        entity.getEvents().trigger("showTooltip", formatString);*/
    }


    /**
         Takes the item from the player and stores in station
         @param playerInventoryComponent reference to player inventory
         @param inventoryDisplay reference to UI for inventory display
     */
    public void stationReceiveItem(ItemComponent item, InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        this.inventoryComponent.addItemAt(item, 0);
        // 0 by default, same position as getItemFirst()
        playerInventoryComponent.removeAt(0);
        
        // Need timers and animation start here, for Animations and Timer task ticket
        this.updateItem(0);
        
        // TODO: make  a serving station component
    }

    /**
        Takes the item from the station, and returns the old item
        @param playerInventoryComponent reference to player inventory
        @param inventoryDisplay reference to UI for inventory display
     */
    public void stationGiveItem(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay) {
        // These are placeholders and don't currently go anywhere
        //if (type.equals("COOK_TOP") || type.equals("OVEN")) {
        //    entity.getEvents().trigger("stopCookingIngredient");
        //} else {
        //    entity.getEvents().trigger("stopChoppingIngredient");
        //}
//        entity.getEvents().trigger("showTooltip", "You took something from the station!");

        // Stop the cooking process on the item
        this.updateItem(1);

        ItemComponent item = inventoryComponent.getItemFirst();
        playerInventoryComponent.addItemAt(item,0);
        // Remove single item in station
        this.inventoryComponent.removeAt(0);

        entity.getEvents().trigger("interactionEnd");
    }

}
