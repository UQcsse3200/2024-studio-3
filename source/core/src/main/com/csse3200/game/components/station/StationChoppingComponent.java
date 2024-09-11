package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

/**
 * StationChoppingComponent.java
 *
 * This class controls the chopping of items within a station. This class is 
 * meant to be a component of the cutting board and blender component and allows
 * the station to transform the item to a 'chopped' state.
 */
public class StationChoppingComponent extends Component {

    protected StationItemHandlerComponent itemHandler;

    @Override
    public void create() {
        itemHandler = entity.getComponent(StationItemHandlerComponent.class);
        entity.getEvents().addListener("Chop Ingredient", this::chopIngredient);
        entity.getEvents().addListener("Stop Chopping Ingredient", this::stopChoppingIngredient);
    }

    /**
     * Function to start the chopping of the ingredient.
     */
    public void chopIngredient() {
        if (itemHandler.peek() == null) {
            return; // No item in station cannot do anything
        }

        // Start the ingredient chopping process
        itemHandler
            .peek()
            .getEntity()
            .getEvents()
            .trigger("chopIngredient");
    }

    /**
     * Function to stop the chopping of an ingredient within a station
     */
    public void stopChoppingIngredient() {
        if (itemHandler.peek() == null) {
            return;
        }

        itemHandler
            .peek()
            .getEntity()
            .getEvents()
            .trigger("stopChoppingIngredient");
    }
    
}