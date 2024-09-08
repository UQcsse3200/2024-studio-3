package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

/**
 * StationChoppingComponent.java
 * </p>
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
    
}