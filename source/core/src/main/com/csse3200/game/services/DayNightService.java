package com.csse3200.game.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.events.EventHandler; 



/**
 * The DayNightService class handles the day-night cycle within the game. It keeps track
 * of game time and triggers events when certain time thresholds, such as the end of a day,
 * are reached. It utilizes an event handler to manage day transitions and in-game events
 * triggered by time changes.
 */
public class DayNightService {
    private static final Logger logger = LoggerFactory.getLogger(DayNightService.class);
    public  long FIVE_MINUTES = 5 * 60 * 1000; // 5 minutes in milliseconds
    public long lastCheckTime;
    public long lastCheckTime2;
    private final GameTime gameTime;
    private boolean endOfDayTriggered = false;
    private boolean pastSecond = false;
    private final EventHandler enddayEventHandler;
    private final EventHandler docketServiceEventHandler;

    /**
     * Constructs a new DayNightService. Initializes the game time and event handler,
     * and sets up the day-night cycle tracking.
     */
    public DayNightService() {
        this(new EventHandler());
    }

    public DayNightService(EventHandler enddayEventHandler) {
        this(enddayEventHandler, ServiceLocator.getDocketService().getEvents());
    }

    public DayNightService(EventHandler enddayEventHandler, EventHandler docketServiceEventHandler) {
        gameTime = ServiceLocator.getTimeSource();
        this.enddayEventHandler = enddayEventHandler;
        this.docketServiceEventHandler = docketServiceEventHandler;

        this.lastCheckTime = gameTime.getTime();
        this.lastCheckTime2 = gameTime.getTime();
        create();
    }

    /**
     * Sets up event listeners for handling end-of-day and timer-related events.
     *
     * Our working version was with the "decisionDone" listener, however, we limited our
     * Day cycle to end for just one day on request of Team 6 and added an "animationDone" listener
     * instead for when team 6 extends their functionality for multiple days.
     * This should listen to a trigger for when the animation is done executing at the end of a day,
     * but currently team 6 has decided to keep it simple and end the game in one day.
     *
     * Okay, when resolving merge conflict, seems team 6 kept our "decisionDone" listener,
     * So I'm going to keep that in for now.
     */
    public void create() {
        // ***Working version of Day cycle used "decisionDone"***
        enddayEventHandler.addListener("decisionDone", this::startNewDay);
        // enddayEventHandler.addListener("animationDone", this::startNewDay);

        enddayEventHandler.addListener("callpastsecond", this::updatepastSecond);
    }

    /**
     * Updates the game state by checking if enough time has passed to trigger
     * end-of-day events or timer-related events.
     */
    public void update() {
        long currentTime = gameTime.getTime(); // Get the current game time

        // Check if 5 minutes have passed and trigger the end of the day
        if(currentTime - lastCheckTime2 >= 1000 && !pastSecond){
            pastSecond = true;
            enddayEventHandler.trigger("Second");
            lastCheckTime2 = currentTime;
        }

        if (currentTime - lastCheckTime >= FIVE_MINUTES && !endOfDayTriggered) {
            endOfDayTriggered = true; 
            gameTime.setTimeScale(0);
            docketServiceEventHandler.trigger("Dispose");
            enddayEventHandler.trigger("endOfDay"); // Trigger the end of the day event
        }
    }

    /**
     * Starts a new day, resuming the game time and resetting relevant counters and orders.
     * This method is triggered after the end-of-day sequence has completed.
     *
     * Our working version was with the "decisionDone" listener, however, we limited our
     * Day cycle to end for just one day on request of Team 6 and hence commented out our last check
     * time updates
     */
    private void startNewDay() {
        ServiceLocator.getDayNightService().getEvents().trigger("endGame");
        logger.info("It's a new Day!");
        enddayEventHandler.trigger("newday");
        // // Resume the game time and reset the last check time
        lastCheckTime = gameTime.getTime(); // Reset lastCheckTime to the current time
        endOfDayTriggered = false;
        gameTime.setTimeScale(1); // Resume game time
    }

    public EventHandler getEvents() {
        return enddayEventHandler;
      }

    public boolean getEndOfDayTriggered() {
        return endOfDayTriggered;
    }

    public void updatepastSecond() {
        pastSecond = false;
    }

}





