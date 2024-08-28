package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;

public class CookIngredientComponent extends Component {
    private IngredientComponent ingredient;
    private final GameTime timesource;
    private boolean isCooking;
    private long cookEndTime;

    public CookIngredientComponent() {
        timesource = ServiceLocator.getTimeSource();
    }

    @Override
    public void create() {
        ingredient = entity.getComponent(IngredientComponent.class);
        entity.getEvents().addListener("cookIngredient", this::cookIngredient);
        entity.getEvents().addListener("stopCookingIngredient", this::stopCookingIngredient);
    }

    /**
     * This method is called every frame to update ingredient state
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
     * This starts the cooking process and calculates the time at which the ingredient
     * finishes cooking.
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
     * This ends the cooking process
     */
    void stopCookingIngredient() {
        isCooking = false;
    }

    /**
     * Returns whether the item is being cooked
     * @return isCooking
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

