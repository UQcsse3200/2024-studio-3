package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;

public class ChopIngredientComponent extends Component {
    private String ingredient;
    private final GameTime timesource;
    private boolean isChopping;
    private long chopEndTime;

    public ChopIngredientComponent() {
        timesource = ServiceLocator.getTimeSource();
    }
    @Override
    public void create() {
//        ingredient = entity.getComponent(IngredientComponent.class);
        entity.getEvents().addListener("chopIngredient", this::chopIngredient);
        entity.getEvents().addListener("stopChoppingIngredient", this::stopChopping);
    }

    /**
     * This method is called every frame to update ingredient state
     */
    @Override
    public void update() {
        if (isChopping) {
            long current_time = timesource.getTime();
            if (current_time >= chopEndTime) {
//                ingredient.chopIngredient();
                stopChopping();
            }
        }
    }

    /**
     * This starts the chopping process and calculates the time at which the ingredient
     * finishes chopping.
     */
    void chopIngredient() {
        isChopping = true;
//      chopEndTime = timesource.getTime() + ingredient.getChopTime() * 1000L

    }

    /**
     * This ends the chopping process
     */
    void stopChopping() {
        isChopping = false;
    }

}


