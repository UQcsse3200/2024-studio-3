package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;

public class DocketService {
    private final EventHandler docketEventHandler;

    public DocketService() {
        docketEventHandler = new EventHandler();
    }

    public EventHandler getEvents() {
        return docketEventHandler;
    }
}
<<<<<<< HEAD

=======
>>>>>>> 4ab51577e41ddc529898371d76ebab0f7e0b03da
