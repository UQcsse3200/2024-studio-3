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