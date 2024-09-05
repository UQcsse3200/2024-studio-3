package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;

/**
 * Service class that manages tutorial-related events.
 * Provides a central point for registering and triggering tutorial events.
 */
public class TutorialService {
    private final EventHandler tutorialEventHandler;

    public TutorialService() {
        tutorialEventHandler = new EventHandler();
    }
    public EventHandler getEvents() {
        return tutorialEventHandler;
    }
}
