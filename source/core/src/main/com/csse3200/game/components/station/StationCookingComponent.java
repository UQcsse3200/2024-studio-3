package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

/**
 * StationCookingComponent.java
 * This class controls the cooking of items within a station. This is designed
 * to be used by cooking stations which include the stove and the over.
 */
public class StationCookingComponent extends Component  {

    protected StationItemHandlerComponent itemHandler;

    @Override
    public void create() {
        itemHandler = entity.getComponent(StationItemHandlerComponent.class);
        entity.getEvents().addListener("Cook Ingredient", this::cookIngredient);
        entity.getEvents().addListener("Stop Cooking Ingredient", this::stopCookingIngredient);
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
            .trigger("cookIngredient");
    }

    /**
     * Function to spot the cooking of an ingredient within the station
     */
    public void stopCookingIngredient() {
        if (itemHandler.peek() == null) {
            return;
        }

        itemHandler
            .peek()
            .getEntity()
            .getEvents()
            .trigger("stopCookingIngredient");
    }

}
