package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;

public class StationChoppingComponent extends Component {

    private StationItemHandlerComponent itemHandler;

    @Override
    public void create() {
        itemHandler = entity.getComponent(StationItemHandlerComponent.class);
    }
    
}