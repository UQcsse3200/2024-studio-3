package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;

/**
 * The ChopIngredientComponent handles the chopping process for an ingredient in the game.
 * It manages the state of chopped, determines when the chopping is complete, and triggers
 * the appropriate actions based on the chopping status.
 */
public class ChopIngredientComponent extends Component {
    private IngredientComponent ingredient;
    private final GameTime timesource;
    private boolean isChopping;
    private long chopEndTime;

    /**
     * Constructor for ChopIngredientComponent. Initializes the time source used for tracking
     * the chopping duration.
     */
    public ChopIngredientComponent() {
        timesource = ServiceLocator.getTimeSource();
    }
    /**
     * Called when the component is created. This method retrieves the IngredientComponent
     * and sets up listeners for starting and stopping the chopping process.
     */
    @Override
    public void create() {
        ingredient = entity.getComponent(IngredientComponent.class);
        entity.getEvents().addListener("chopIngredient", this::chopIngredient);
        entity.getEvents().addListener("stopChoppingIngredient", this::stopChoppingIngredient);
    }

    /**
     * This method is called every frame to update the ingredient state. It checks if the
     * chopping process is active, then checks if the end duration has been reached to trigger
     * the action of changing the chopped state of the item
     */
    @Override
    public void update() {
        if (isChopping) {
            long current_time = timesource.getTime();
            if (current_time >= chopEndTime) {
                ingredient.chopItem();
                stopChoppingIngredient();
            }
        }
    }

    /**
     * Starts the chopping process for the ingredient and calculates the time at which the
     * ingredient will finish chopping. The chopping time is determined by the ingredient's
     * chop time
     */
    void chopIngredient() {
        isChopping = true;
        // The following needs to be updated with ingredient.getChopTime()
        // We're waiting for Items team to add it in
        chopEndTime = timesource.getTime() + ingredient.getCookTime() * 1000L;
    }

    /**
     * Stops the chopping process. This method is called when chopping is complete
     */
    void stopChoppingIngredient() {
        isChopping = false;
    }

}


