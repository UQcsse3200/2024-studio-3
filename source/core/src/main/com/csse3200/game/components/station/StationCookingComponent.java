package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

/**
 * StationCookingComponent.java
 * </p>
 * This class controls the cooking of items within a station. This is designed
 * to be used by cooking stations which include the stove and the over.
 */
public class StationCookingComponent extends Component  {

    private StationItemHandlerComponent itemHandler;

    @Override
    public void create() {
        itemHandler = entity.getComponent(StationItemHandlerComponent.class);
    }

//    @Override
//    public void update() {
//        if (!itemHandler.hasItem()) {
//            return;
//        }
//
//        this.cookItem();
//    }

//    private void cookItem() {
//        itemHandler.peekItem().getEvents().trigger("cookIngredient", "HOT", 1);
//    }


    
}
