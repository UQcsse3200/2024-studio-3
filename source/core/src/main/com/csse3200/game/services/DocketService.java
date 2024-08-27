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
=======
}
>>>>>>> 6c5efbd49a60c7bb81758a30745ac8db2d40c97b
