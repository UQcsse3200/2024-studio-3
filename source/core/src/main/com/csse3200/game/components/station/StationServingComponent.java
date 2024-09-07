package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

/**
 * StationServingComponent.java
 * @version 1.0
 * @author Callan Vragalis
 */
public class StationServingComponent extends Component {

    private StationItemHandlerComponent itemHandler;

    @Override
    public void create() {
        entity.getEvents().addListener("Submit Meal", this::submitMeal);
    }

    // this will go in Serving station when component completed
    public void submitMeal(String item) {
        //TODO:
        //call getCurrentBigTicketInfo() to get values of bigticket, returning a string[]
        //String[] bigTicketInfo =
        // call made to other teams function

        //TBD(item, bigTicketInfo[0], bigTicketInfo[1], bigTicketInfo[2]);
        //AKA item being submitted, order number of ticket, meal of ticket, time left of ticket.
        return;
    }
    
}
