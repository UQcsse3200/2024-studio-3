package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;

/**
 * A component used to handle changing the state of an item being passed through a station.
 */
public class CookingComponent extends Component {
    private StationInventoryComponent inventoryComponent;
    private GameTime gameTime;
    private long cookingTime;
    private boolean isCooking;

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
     * Called every frame to incrementally cook items.
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
}
