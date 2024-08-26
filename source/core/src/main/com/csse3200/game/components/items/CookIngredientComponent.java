package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;


/**
 * Waiting for Items team to do PR before integrating their Ingredient part
 */
public class CookIngredientComponent extends Component {
    //private IngredientComponent ingredient;
    private String ingredient;
    private final GameTime timesource;
    private boolean isCooking;
    long cookTime;

    public CookIngredientComponent() {
        timesource = ServiceLocator.getTimeSource();
    }

    @Override
    public void create() {
//        ingredient = entity.getComponent(IngredientComponent.class);
        //ingredient = "fish";
        // TODO: Add event listeners for cooking and stop cooking here
        entity.getEvents().addListener("on", this::cookIngredient);
        entity.getEvents().addListener("off", this::stopCooking);
    }

    /**
     * This method is called every frame to update ingredient state
     */
    @Override
    public void update() {
        if (isCooking) {
            long current_time = timesource.getTime();

            // If 15 seconds have passed since the item was cooked,
            // the item gets burned
            if (current_time >= cookTime + 15000) {
                //ingredient.burnItem();
                System.out.println("Fish is burnt");
                stopCooking();
            } else if (current_time >= cookTime) {
//                ingredient.cookIngredient();
                System.out.println("Fish is cooked");
            }
        }
    }

    /**
     * This starts the cooking process
     */
    void cookIngredient() {
        isCooking = true;
//        cookTime = timesource.getTime() + ingredient.getCookTime() * 1000L;
        cookTime = timesource.getTime() + 20000;
    }

    /**
     * This ends the cooking process
     */
    void stopCooking() {
        isCooking = false;
    }
}

