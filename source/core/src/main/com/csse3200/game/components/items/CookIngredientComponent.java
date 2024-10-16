package com.csse3200.game.components.items;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csse3200.game.services.ServiceLocator;

/**
 * The CookIngredientComponent handles the cooking process for an ingredient in the game.
 * It manages the state of cooking, determines when the cooking is complete, and triggers
 * the appropriate actions based on the cooking status and the station's state.
 */
public class CookIngredientComponent extends ItemTimerComponent {
    
    private static final float ITEM_BURNING_TIME = 15;

    // The ingredient componetn of the item being used
    private IngredientComponent item;
    private static final Logger logger = LoggerFactory.getLogger(ChopIngredientComponent.class);

    /**
     * CookIngredientComponent constructor, takes no parameters as the length of
     * time that the timer goes for must be set manually.
     */
    public CookIngredientComponent() {
        super();
    }

    @Override
    public void create() {
        super.create();
        // On creation add triggers for rage mode to the timer
         ServiceLocator.getEntityService().getEvents().addListener("rageModeOn", this::rageModeOn);
        ServiceLocator.getEntityService().getEvents().addListener("rageModeOff", this::rageModeOff);

        // Add appriopriate event listeners
        entity.getEvents().addListener("cookIngredient", this::startTimer);
        entity.getEvents().addListener("stopCookingIngredient", this::stopTimer);

        // Get the item so that it can be updated correctly and correct time gotten
        item = entity.getComponent(IngredientComponent.class);
        setLength(item.getCookTime() * 1000L); // More logic can be added here when required

        // Log the info
        String s = String.format("The timer for item: %s, has been created", item.getItemName());
        //logger.info(s);
    }

    @Override
    public void update() {
        // Update the timing within the timer if running
        if (!this.isRunning) {
            return; // super.update() does same check but this needed for early return 
        }

        // Update the elapsed time
        super.update();

        String s = String.format("The completion of %s is at %.2f percent", item.getItemName(), getCompletionPercent());
        logger.info(s);

        // Check if the timer is finished
        if (isFinished()) {
            updateItem();
        }
    }

    /**
     * Update the item component to reflect its new state.
     */
    protected void updateItem() {
        // Update item state
        item.cookItem();

        // Check if the item should be burned
        if (elapsed >= length + ITEM_BURNING_TIME * 1000) {
            item.burnItem();
            stopTimer(); // Only stop the timer if the item has been burned
        }

        // Put the info to the console
        String s = String.format("The state of item: %s, has been update to %s", item.getItemName(), item.getItemState());
        logger.info(s);
    }

    /**
     * Get if the item is currently cooking
     * @return true if the item is cooking, false otherwise.
     */
    public boolean getIsCooking() {
        return isRunning;
    }
}