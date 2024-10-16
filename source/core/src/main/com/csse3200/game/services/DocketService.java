package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;

/**
 * Service class that manages docket-related events in the game.
 * It provides a central point for registering and triggering events associated with dockets.
 */
public class DocketService {
    private final EventHandler docketEventHandler;

    /**
     * Constructs a new DocketService instance.
     * Initialises the EventHandler that will manage all docket-related events.
     */
    public DocketService() {
        this(new EventHandler());
    }

    public DocketService(EventHandler docketEventHandler) {
        this.docketEventHandler = docketEventHandler;
    }


    /**
     * Retrieves the EventHandler associated with this DocketService.
     * The EventHandler can be used to register and trigger events related to dockets.
     *
     * @return the EventHandler managing docket-related events.
     */
    public EventHandler getEvents() {
        return docketEventHandler;
    }
}
