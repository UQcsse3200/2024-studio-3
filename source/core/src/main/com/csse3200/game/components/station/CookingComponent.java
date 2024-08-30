package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.entities.factories.DishFactory; // TODO should I??
import com.csse3200.game.services.ServiceLocator;

import java.util.*;

/**
 * A component used to handle changing the state of an item being passed through a station.
 */
public class CookingComponent extends Component {
    private StationInventoryComponent inventoryComponent;
    private GameTime gameTime;
    private long cookingTime;
    private boolean isCooking;
    private String targetRecipe;

    /**
     * Called on creation of the station and adds listeners.
     */
    @Override
    public void create() {
        inventoryComponent = entity.getComponent(StationInventoryComponent.class);
        gameTime = ServiceLocator.getTimeSource();
        entity.getEvents().addListener("give station item", this::addItem);
        entity.getEvents().addListener("take item", this::removeItem);
    }

    /**
     * Called every frame to incrementally cook items, scaled by time scale.
     */
    @Override
    public void update() {
        // Add to cooking timer and cook item
        if (isCooking) {
            if (cookingTime < 0) { // Recipe is fully cooked
                // remove current items
                for (Optional<String> x : inventoryComponent.getItems()) {
                    inventoryComponent.removeCurrentItem();
                }
                // replace with dish from recipe
                inventoryComponent.setCurrentItem(targetRecipe);
            }
            cookingTime -= gameTime.getDeltaTime();
        }
    }

    /**
     * Method triggered when item added to station.
     */
    public void addItem(String item) {
        // Converting List<Optional<String>> to List<String>
        List<String> templist = new ArrayList<String>();
        for (Optional<String> x : inventoryComponent.getItems()) {
            if (x.isPresent()) templist.add(x.get());
        }

        List<String> possibleRecipes = DishFactory.getRecipe(templist);
        if (possibleRecipes.size() == 1 && !isCooking) {
            // TODO check that it completely matching the recipe
            targetRecipe = possibleRecipes.get(0);
            cookingTime = 10000; // TODO edit placeholder, get cooking time from recipes?
            isCooking = true;
        }
        else
        {
            // TODO
        }
    }

    /**
     * Method triggered when item removed from station.
     */
    public void removeItem() {
        isCooking = false;
    }

    /** @return true if the item is being cooked, false otherwise. */
    public boolean isCooking() {
        return isCooking;
    }

    /** @return time remaining to make the recipe in seconds, scaled by time scale. */
    public long getCookingTime() {
        return cookingTime;
    }

    /** @return the name of the target recipe */
    public String getTargetRecipe() {
        return targetRecipe;
    }
}
