package com.csse3200.game.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.events.EventHandler; 




public class DayNightService {
    private static final Logger logger = LoggerFactory.getLogger(DayNightService.class);
    private static final long FIVE_MINUTES = 10 * 1000; //5*60*1000; // 5 minutes in milliseconds
    private long lastCheckTime;
    private final GameTime gameTime;
    private boolean endOfDayTriggered = false;
    private final EventHandler enddayEventHandler;
    private final EventHandler docketServiceEventHandler;

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
        create();
    }

    /**
     * Use the create method to set up event listeners for the end-of-day cycle.
     *
     * Our working version was with the "decisionDone" listener, however, we limited our
     * Day cycle to end for just one day on request of Team 6 and added an "animationDone" listener
     * instead for when team 6 extends their functionality for multiple days.
     * This should listen to a trigger for when the animation is done executing at the end of a day,
     * but currently team 6 has decided to keep it simple and end the game in one day.
     */
    public void create() {
        // ***Working version of Day cycle used "decisionDone"***
        enddayEventHandler.addListener("decisionDone", this::startNewDay);

//        enddayEventHandler.addListener("animationDone", this::startNewDay);
    }

    public void update() {
        long currentTime = gameTime.getTime(); // Get the current game time
        // Check if 5 minutes have passed and trigger the end of the day
        if (currentTime - lastCheckTime >= FIVE_MINUTES && !endOfDayTriggered) {
            endOfDayTriggered = true; 
            gameTime.setTimeScale(0);
            docketServiceEventHandler.trigger("Dispose");
            enddayEventHandler.trigger("endOfDay"); // Trigger the end of the day event
        }
    }

    /**
     * Starts a new day, updating the day counter, resuming the game time, and resetting orders.
     *
     * Our working version was with the "decisionDone" listener, however, we limited our
     * Day cycle to end for just one day on request of Team 6 and hence commented out our last check
     * time updates
     */
    private void startNewDay() {
        logger.info("It's a new Day!");
        enddayEventHandler.trigger("newday");

        // ***Working version of day cycle used the following commented out lastCheckTime update***
        lastCheckTime = gameTime.getTime();
        endOfDayTriggered = false;
        gameTime.setTimeScale(1); // Resume game time
    }

    public EventHandler getEvents() {
        return enddayEventHandler;
      }

    public boolean getEndOfDayTriggered() {
        return endOfDayTriggered;
    }
}





