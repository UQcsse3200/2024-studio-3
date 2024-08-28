package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;

/**
 * The CookIngredientComponent handles the cooking process for an ingredient in the game.
 * It manages the state of cooking, determines when the cooking is complete, and triggers
 * the appropriate actions based on the cooking status and the station's state.
 */
public class CookIngredientComponent extends Component {
    private IngredientComponent ingredient;
    private final GameTime timesource;
    private boolean isCooking;
    private long cookEndTime;

    /**
     * Constructor for CookIngredientComponent. Initializes the time source used for tracking
     * the cooking duration.
     */
    public CookIngredientComponent() {
        timesource = ServiceLocator.getTimeSource();
    }

    /**
     * Called when the component is created. This method retrieves the IngredientComponent
     * and sets up listeners for starting and stopping the cooking process.
     */
    @Override
    public void create() {
        ingredient = entity.getComponent(IngredientComponent.class);
        entity.getEvents().addListener("cookIngredient", this::cookIngredient);
        entity.getEvents().addListener("stopCookingIngredient", this::stopCookingIngredient);
    }

    /**
     * This method is called every frame to update the ingredient state. It checks if the
     * cooking process is complete or if the ingredient has been overcooked (burnt) based
     * on the time elapsed since the cooking started.
     */
    @Override
    public void update() {
        if (isCooking) {
            long current_time = timesource.getTime();

            // If 15 seconds have passed since the item was cooked,
            // the item gets burnt
            if (current_time >= cookEndTime + 15 * 1000L) {
                ingredient.burnItem();
                stopCookingIngredient();
            } else if (current_time >= cookEndTime) {
                ingredient.cookItem();
            }
        }
    }

    /**
     * Starts the cooking process for the ingredient and calculates the time at which the
     * ingredient will finish cooking. The cooking time is adjusted based on the station
     * state and an oven multiplier if the station is an oven.
     *
     * @param stationState - The station state ("HOT", "WARM", "NORMAL")
     * @param oven_multiplier - Oven cook times take longer, so we use a multiplier
     *                        to multiply the cooking type if station is "OVEN".
     */
    void cookIngredient(String stationState, int oven_multiplier) {
        isCooking = true;
        cookEndTime = timesource.getTime();

        // ingredient.getCookTime() should return value in seconds as
        // we are converting to milliseconds in this method
        switch (stationState) {
            case "HOT" -> {
                cookEndTime += (long) (ingredient.getCookTime() * 1000L * oven_multiplier * 0.25);
            }
            case "WARM" -> {
                cookEndTime += (long) (ingredient.getCookTime() * 1000L * oven_multiplier * 0.5);
            }
            case "NORMAL" -> {
                cookEndTime += ingredient.getCookTime() * 1000L * oven_multiplier;
            }
        }
    }

    /**
     * Stops the cooking process. This method is called when cooking is complete or
     * when the cooking process is manually stopped.
     */
    void stopCookingIngredient() {
        isCooking = false;
    }

    /**
     * Returns whether the ingredient is currently being cooked.
     *
     * @return true if the ingredient is cooking, false otherwise.
     */
    public boolean getIsCooking() {
        return isCooking;
    }

/*
    void setTimeSource(GameTime timesource) {
        this.timesource = timesource;
    }
*/
}

