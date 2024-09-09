/*
package com.csse3200.game.services;

import com.csse3200.game.events.EventHandler;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

*/
/**
 * Service class that manages the day logic in the game.
 * It transitions the game to the end-of-day screen after 5 minutes of game time elapse.
 *//*

public class DayCycleService {
    private static final Logger logger = LoggerFactory.getLogger(DayCycleService.class);
    private final GameTime gameTime;
    private final EventHandler endDayEventHandler;

    */
/**
     * Constructs a new DayCycleService instance.
     * Initializes the GameTime and EventHandler for managing day cycle events.
     *//*

    public DayCycleService() {
        gameTime = ServiceLocator.getTimeSource();
        endDayEventHandler = new EventHandler();

        logger.debug("DayCycleService created");

        scheduleEndOfDay();
    }

    */
/**
     * Schedules the end-of-day event after every 5 minutes of game time.
     *//*

    public void scheduleEndOfDay() {
        long delay = 5 * 60 * 1000L; // 5 minutes in milliseconds
        long currentTime = gameTime.getTime();
//        long nextEndOfDayTime = gameTime + delay;

        logger.debug("Scheduling end-of-day event at game time: " + nextEndOfDayTime);

        // Schedule the event to transition to the end-of-day screen
        endDayEventHandler.addListener("endDay", () -> transitionToEndOfDay());
        gameTime.scheduleEvent(endDayEventHandler, "endDay", delay, true);
    }

    */
/**
     * Transitions the game to the end-of-day screen.
     * Pauses the game time and triggers the end-of-day logic.
     *//*

    private void transitionToEndOfDay() {
        // Logic to transition to the end-of-day screen
        gameTime.pause();
        // Add the logic here to change the game screen to the end-of-day screen
    }

    */
/**
     * Retrieves the EventHandler associated with this DayCycleService.
     * The EventHandler can be used to register and trigger events related to the day cycle.
     *
     * @return the EventHandler managing day cycle-related events.
     *//*

    public EventHandler getEvents() {
        return endDayEventHandler;
    }
}*/
