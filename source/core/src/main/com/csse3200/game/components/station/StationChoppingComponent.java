package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

/**
 * StationChoppingComponent.java
 * </p>
 * This class controls the chopping of items within a station. This class is 
 * meant to be a component of the cutting board and blender component and allows
 * the station to transform the item to a 'chopped' state.
 */
public class StationChoppingComponent extends Component {

    private StationItemHandlerComponent itemHandler;

    @Override
    public void create() {
        itemHandler = entity.getComponent(StationItemHandlerComponent.class);
    }
    
}