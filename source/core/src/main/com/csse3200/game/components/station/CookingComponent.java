package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;

/**
 * A component used to handle changing the state of an item being passed through a station.
 */
public class CookingComponent extends Component {
    enum StationType { // TODO this is a placeholder way of distinguishing station types
        CUTTING_BOARD,
        OVEN,
        FRYING_PAN,
        TABLE
    }

    private StationType stationType;
    private StationInventoryComponent inventoryComponent;
    private GameTime gameTime;
    private long cookingTime;
    private boolean isCooking;

    /**
     * Constructs a station cooking component.
     * @param stationType the string station tpye.
     */
    public CookingComponent(String stationType) {
        // TODO still not sure if this should be in create() method instead
        switch (stationType) {
            case "CUTTING_BOARD":
                this.stationType = StationType.CUTTING_BOARD;
                break;
            case "OVEN":
                this.stationType = StationType.OVEN;
                break;
            case "FRYING_PAN":
                this.stationType = StationType.FRYING_PAN;
                break;
            case "TABLE":
                this.stationType = StationType.TABLE;
                break;
            default:
                this.stationType = StationType.TABLE;
                break;
        }
    }

    /**
     * Called on creation of the station and adds listeners.
     */
    @Override
    public void create() {
        inventoryComponent = entity.getComponent(StationInventoryComponent.class);
        entity.getEvents().addListener("Add Station Item", this::addItem);
        entity.getEvents().addListener("Remove Station Item", this::removeItem);
    }

    /**
     * Called every frame to incrementally cook items, scaled by time scale.
     */
    @Override
    public void update() {
        // Add to cooking timer and cook item
        if (inventoryComponent.isItemPresent() && isCooking) { // TODO might be redundant
            // TODO add method to check if overcooked
            cookingTime += gameTime.getDeltaTime();
        }
    }

    /**
     * Method triggered when item added to station.
     */
    private void addItem() {
        // TODO check valid item first - can store invalid but can't cook it
        cookingTime = 0;
        isCooking = true;
    }

    /**
     * Method triggered when item removed from station.
     */
    private void removeItem() {
        isCooking = false;
    }

    /**
     * Checks whether the station is being used.
     * @return true if the item is being cooked, false otherwise.
     */
    public boolean isCooking() {
        return isCooking;
    }

    /**
     * Tells how long the item has been cooked for in seconds, scaled by time scale.
     * @return time passed since the item was placed on the station in seconds, scaled by time scale.
     */
    public long getCookingTime() {
        return cookingTime;
    }
}
