package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;

public class ChopIngredientComponent extends Component {
    private String ingredient;
    private final GameTime timesource;
    private boolean isChopping;

    long chopTime;

    public ChopIngredientComponent() {
        timesource = ServiceLocator.getTimeSource();
    }
    @Override
    public void create() {
//        ingredient = entity.getComponent(IngredientComponent.class);
        //ingredient = "fish";
        // TODO: Add event listeners for cooking and stop cooking here
        entity.getEvents().addListener("chop", this::chopIngredient);
        entity.getEvents().addListener("off", this::stopChopping);
    }

    @Override
    public void update() {
        if (isChopping) {
            long current_time = timesource.getTime();

            if (current_time >= chopTime) {
//                ingredient.chopIngredient();
                System.out.println("Salad chopped");
                stopChopping();
            }
        }
    }

    void chopIngredient() {
        isChopping = true;
//      choptime = timesource.getTime() + ingredient.getChopTime() * 1000L

    }
    void stopChopping() {
        isChopping = false;
    }

}


