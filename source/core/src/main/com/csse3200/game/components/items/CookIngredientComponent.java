package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;

public class CookIngredientComponent extends Component {
    //private IngredientComponent ingredient;
    private final GameTime timesource;
    private boolean isCooking;
    private long cookEndTime;

    public CookIngredientComponent() {
        timesource = ServiceLocator.getTimeSource();
    }

    @Override
    public void create() {
//        ingredient = entity.getComponent(IngredientComponent.class);
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
            if (current_time >= cookEndTime + 15 * 1000) {
                //ingredient.burnItem();
                stopCookingIngredient();
            } else if (current_time >= cookEndTime) {
//                ingredient.cookIngredient();
            }
        }
    }

    /**
     * This starts the cooking process and calculates the time at which the ingredient
     * finishes cooking.
     *
     * @param stationState - The station state ("Hot", "Warm", "Normal")
     * @param oven_multiplier - Oven cook times take longer, so we use a multiplier
     *                        to multiply the cooking type if station is "OVEN".
     */
    void cookIngredient(String stationState, int oven_multiplier) {
        isCooking = true;
        switch (stationState) {
            case "Hot" -> {
                // cookEndTime = timesource.getTime() + ingredient.getCookTime() * 1000L * oven_multiplier * 0.25;
            }
            case "Warm" -> {
                // cookEndTime = timesource.getTime() + ingredient.getCookTime() * 1000L * oven_multiplier * 0.5;
            }
            default -> {
                // cookEndTime = timesource.getTime() + ingredient.getCookTime() * 1000L * oven_multiplier;
            }
        }
    }

    /**
     * This ends the cooking process
     */
    void stopCookingIngredient() {
        isCooking = false;
    }
}

