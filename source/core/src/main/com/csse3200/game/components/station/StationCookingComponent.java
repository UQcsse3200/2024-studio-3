package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

/**
 * StationCookingComponent.java
 * </p>
 * This class controls the cooking of items within a station. This is designed
 * to be used by cooking stations which include the stove and the over.
 */
public class StationCookingComponent extends Component  {

    private StationItemHandlerComponent itemHandler;

    @Override
    public void create() {
        itemHandler = entity.getComponent(StationItemHandlerComponent.class);
        entity.getEvents().addListener("Cook Ingredient", this::cookIngredient);
    }

    /**
     * Function to start the cooking of the ingredient within the station.
     */
    public void cookIngredient() {
        if (itemHandler.peek() == null) {
            return; // No item in the station
        }

        // Trigger the interaction to cook the ingredient since it exists
        itemHandler
            .peek()
            .getEntity()
            .getEvents()
            .trigger("cookIngredient", "HOT", 1);
        // TODO: modify the call to cookIngredient so that the correct timees
        // can be used for each ingerdient
        // May need to use the item handler for this
    }

}
