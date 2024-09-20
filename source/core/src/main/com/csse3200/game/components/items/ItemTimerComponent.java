package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;

/**
 * TimerComponent.java
 * 
 * Timer component used for item cooking and chopping. Able to be set with a
 * time started, stopped, and paused etc. This will modify the current
 * impletmentation of item cooking
 */
public abstract class ItemTimerComponent extends Component {

    /**
     * Class member variables.
     * @param gameTime the global gametime service
     * @param length finishing time of the timer
     * @param elapsed the current ammount of time elapsed
     * @param isRunning if the timer is running starts as false when item created
     */
    protected final GameTime gameTime = ServiceLocator.getTimeSource();
    protected float length;
    protected float elapsed;
    protected boolean isRunning;

    /**
     * TimerComponent initialiser creates a timer which has the legnth set to
     * Float.MAX_VALUE, the time length must be set manually. This is so that
     * timings depending on upgrades and such are not prevented in the future.
     */
    public ItemTimerComponent() {
        this.length = Float.MAX_VALUE;
        this.isRunning = false;
        this.elapsed = 0;
    }

    /**
     * Function to run on the creation of the component and it's registration
     */
    @Override
    public void create() {
        // Add appriopriate event listeners
        entity.getEvents().addListener("cookIngredient", this::startTimer);
        entity.getEvents().addListener("stopCookingIngredient", this::stopTimer);
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
        this.elapsed += gameTime.getDeltaTime();
    }

    /**
     * Set the length of the timer. This should be used before the timer has 
     * started running, but is not prevented from being used while it is running
     * however, if used the completion status of the timer could change.
     * @param length of time the timer should run for requires length > 0
     */
    protected void setLength(float length) {
        if (length <= 0) { // Invalid length 0 not allow to prevent div 0 errors
            return;
        }

        this.length = length;
    }

    /**
     * Sets the timer to start running.
     */
    public void startTimer() {
        isRunning = true;
    }

    /**
     * Set the timer to stop running.
     */
    public void stopTimer() {
        isRunning = false;
    }

    /**
     * Determines if the timer is finished.
     * @return true if the time elapsed is greater than the length, false
     * otherwise
     */
    public boolean isFinished() {
        return (elapsed >= length);
    }

    /**
     * Get the time elapsed since the timer has been started
     * @return the ammount of time in seconds since the timer started
    */
    public float getElapsedTime() {
        return elapsed;
    }

    /**
     * Gets the percentage completion of the timer. e.g. if the length is 10s
     * and 3s has passed 30 will be returned.
     * @return The percentage completion of the timer.
     */
    public float getCompletionPercent() {
        return (elapsed / length) * 100;
    }

    /**
     * Method to update an item to a new state depending on the item type.
     * Abtract since cooking and chopping will have different implementations.
     */
    protected abstract void updateItem();

}
