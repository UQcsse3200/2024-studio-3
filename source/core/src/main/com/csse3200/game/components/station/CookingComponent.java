package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

/**
 * A component used to handle changing the state of an item being passed through a station.
 */
public class CookingComponent extends Component {

    /**
     * Called on creation of the station and adds listeners.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("Add Station Item", this::addItem);
        entity.getEvents().addListener("Remove Station Item", this::removeItem);
    }

    /**
     * Called every frame to incrementally cook items.
     */
    @Override
    public void update() {
        // Add to cooking timer and cook item
    }

    /**
     * Method triggered when item added to station.
     */
    private void addItem() {

    }

    /**
     * Method triggered when item removed from station.
     */
    private void removeItem() {

    }
}
