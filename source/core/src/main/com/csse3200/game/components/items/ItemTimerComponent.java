package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;

/**
 * TimerComponent.java
 * Timer component used for item cooking and chopping. Able to be set with a
 * time started, stopped, and paused etc. This will modify the current
 * impletmentation of item cooking
 */
public abstract class ItemTimerComponent extends Component {

    // The base time multiplier
    private static final long BASE_MULTIPLIER = 1;
  
    // The multiplier for rage mode
    private static final long RAGE_MODE_MULTIPLIER = 2;
  
    /**
     * Class member variables.
     * - gameTime the global gametime service
     * - length finishing time of the timer
     * - elapsed the current ammount of time elapsed
     * - isRunning if the timer is running starts as false when item created
     * - multiplier the current multiplier of the station if cooking
     */
    protected final GameTime gameTime = ServiceLocator.getTimeSource();
    private long prevTime;
    protected long length;
    protected long elapsed;
    protected boolean isRunning;
    private long multiplier = 1;

    /**
     * TimerComponent initialiser creates a timer which has the legnth set to
     * Float.MAX_VALUE, the time length must be set manually. This is so that
     * timings depending on upgrades and such are not prevented in the future.
     */
    public ItemTimerComponent() {
        this.length = Long.MAX_VALUE;
        this.isRunning = false;
        this.elapsed = 0;
    }

    /**
     * Function to run each frame
     */
    @Override
    public void update() {
        // Update the timing within the timer if running
        if (!this.isRunning) {
            return;
        }

        // Update the elapsed time
        this.elapsed += (gameTime.getTime() - prevTime) * multiplier;
        prevTime = gameTime.getTime();
    }

    /**
     * Set the length of the timer. This should be used before the timer has 
     * started running, but is not prevented from being used while it is running
     * however, if used the completion status of the timer could change.
     * @param length of time the timer should run for requires length > 0
     */
    protected void setLength(long length) {
        if (length <= 0) { // Invalid length 0 not allow to prevent div 0 errors
            return;
        }

        this.length = length;
    }

    /**
     * Sets the timer to start running.
     */
    protected void startTimer() {
        isRunning = true;
        prevTime = gameTime.getTime();
    }

    /**
     * Set the timer to stop running.
     */
    protected void stopTimer() {
        isRunning = false;
    }

    /**
     * Determines if the timer is finished.
     * @return true if the time elapsed is greater than the length, false
     * otherwise
     */
    protected boolean isFinished() {
        return (elapsed >= length);
    }

    /**
     * Gets the percentage completion of the timer. e.g. if the length is 10s
     * and 3s has passed 30f will be returned.
     * @return The percentage completion of the timer.
     */
    public float getCompletionPercent() {
        return ((float) elapsed / (float) length) * 100;
    }

    /**
     * Method to update an item to a new state depending on the item type.
     * Abtract since cooking and chopping will have different implementations.
     */
    protected abstract void updateItem();

    /**
     * Method to call when rage mode is activated by the user. Reduces the time
     * that the item will take to cook
     */
    protected void rageModeOn() {
        multiplier = RAGE_MODE_MULTIPLIER;
    }

    /**
     * Method to call when rage mode is deactivated by the user. Changes back
     * the time that the item takes to cook back to normal.
     */
    protected void rageModeOff() {
        multiplier = BASE_MULTIPLIER;
    }

}