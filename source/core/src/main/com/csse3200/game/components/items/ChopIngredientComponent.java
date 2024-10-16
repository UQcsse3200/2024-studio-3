package com.csse3200.game.components.items;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csse3200.game.services.ServiceLocator;

/**
 * The ChopIngredientComponent handles the chopping process for an ingredient in the game.
 * It manages the state of chopped, determines when the chopping is complete, and triggers
 * the appropriate actions based on the chopping status.
 */
public class ChopIngredientComponent extends ItemTimerComponent {

    // The ingredient componetn of the item being used
    private IngredientComponent item;
    private static final Logger logger = LoggerFactory.getLogger(ChopIngredientComponent.class);

    /**
     * ChopIngredientComponent constructor, takes no parameters as the length of
     * time that the timer goes for must be set manually.
     */
    public ChopIngredientComponent() {
        super();
    }

    /**
     * create is called when the entity is registered
     */
    @Override
    public void create() {
        super.create();
        // Add event listeners for the rage mode
        ServiceLocator.getEntityService().getEvents().addListener("rageModeOn", this::rageModeOn);
        ServiceLocator.getEntityService().getEvents().addListener("rageModeOff", this::rageModeOff);

        // Add appriopriate event listeners
        entity.getEvents().addListener("chopIngredient", this::startTimer);
        entity.getEvents().addListener("stopChoppingIngredient", this::stopTimer);

        // Get the item so that it can be updated correctly and the length can be correct
        item = entity.getComponent(IngredientComponent.class);
        setLength(item.getChopTime() * 1000L); // More logic can be added here when required

        // Log the info
        String s = String.format("The timer for item: %s, has been created", item.getItemName());
        //logger.info(s);
    }

    /**
     * update is called on each frame
     */
    @Override
    public void update() {
        // Update the timing within the timer if running
        if (!this.isRunning) {
            return; // super.update() does same check but this needed for early return 
        }

        // update the elapsed time
        super.update();

        String s = String.format("The completion of %s is at %.2f percent", item.getItemName(), getCompletionPercent());
        logger.info(s);

        // Check if the timer is finished
        if (isFinished()) {
            updateItem();
        }
    }

    /**
     * Update the item component to reflect its new state. Should not be 
     * manually calleds
     */
    protected void updateItem() {
        // Update item state
        item.chopItem();

        // stop the timer from running to prevent any errors and to help 
        // test for proper player pausing
        stopTimer();

        // Put the info to the console
        String s = String.format("The state of item: %s, has been update to chopped", item.getItemName());
        logger.info(s);
    }

    /**
     * Get if the item is currently cooking
     * @return true if the item is cooking, false otherwise.
     */
    public boolean getIsChopping() {
        return isRunning;
    }
    
}