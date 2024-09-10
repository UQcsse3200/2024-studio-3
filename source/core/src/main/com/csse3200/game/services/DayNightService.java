package com.csse3200.game.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.events.EventHandler; 




public class DayNightService {
    private static final Logger logger = LoggerFactory.getLogger(DayNightService.class);
    private static final long FIVE_MINUTES = 5 * 60 * 1000; //5*60*1000; // 5 minutes in milliseconds
    private long lastCheckTime;
    private final GameTime gameTime;
    private boolean endOfDayTriggered = false;
    private final EventHandler enddayEventHandler; 

    public DayNightService() {
        gameTime = ServiceLocator.getTimeSource(); 
        enddayEventHandler = new EventHandler(); 
    
        this.lastCheckTime = gameTime.getTime(); 
        create(); 
    }

    /**
     * Use the create method to set up event listeners for the end-of-day cycle.
     */
    public void create() {
    enddayEventHandler.addListener("temp", this::startNewDay);
    }

    public void update() {
        long currentTime = gameTime.getTime(); // Get the current game time
        // Check if 5 minutes have passed and trigger the end of the day
        if (currentTime - lastCheckTime >= FIVE_MINUTES && !endOfDayTriggered) {
            endOfDayTriggered = true; 
            gameTime.setTimeScale(0);
            ServiceLocator.getDocketService().getEvents().trigger("Dispose");
            enddayEventHandler.trigger("endOfDay"); // Trigger the end of the day event
        }
    }

    /**
     * Starts a new day, updating the day counter, resuming the game time, and resetting orders.
     */
    private void startNewDay() {
        logger.info("it has been triggered");
        enddayEventHandler.trigger("newday");
        // // Resume the game time and reset the last check time
        lastCheckTime = gameTime.getTime(); // Reset lastCheckTime to the current time
        endOfDayTriggered = false;
        gameTime.setTimeScale(1); // Resume game time
    }

    public EventHandler getEvents() {
        return enddayEventHandler;
      }
}





