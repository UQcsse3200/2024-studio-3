package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.entities.factories.DishFactory; // TODO should I??

import java.util.*;

/**
 * A component used to handle changing the state of an item being passed through a station.
 */
public class CookingComponent extends Component {
    enum StationType { // TODO this is a placeholder way of distinguishing station types
        CUTTING_BOARD,
        OVEN,
        FRYING_PAN,
        TABLE
    }

    private StationType stationType;
    private StationInventoryComponent inventoryComponent;
    private DishFactory dishFactory;
    private GameTime gameTime;
    private long cookingTime;
    private boolean isCooking;
    private String targetRecipe;

    /**
     * Constructs a station cooking component.
     * @param stationType the string station tpye.
     */
    public CookingComponent(String stationType) {
        // TODO still not sure if this should be in create() method instead
        switch (stationType) {
            case "CUTTING_BOARD":
                this.stationType = StationType.CUTTING_BOARD;
                break;
            case "OVEN":
                this.stationType = StationType.OVEN;
                break;
            case "FRYING_PAN":
                this.stationType = StationType.FRYING_PAN;
                break;
            case "TABLE":
                this.stationType = StationType.TABLE;
                break;
            default:
                this.stationType = StationType.TABLE;
                break;
        }
    }

    /**
     * Called on creation of the station and adds listeners.
     */
    @Override
    public void create() {
        inventoryComponent = entity.getComponent(StationInventoryComponent.class);
        entity.getEvents().addListener("Add Station Item", this::addItem);
        entity.getEvents().addListener("Remove Station Item", this::removeItem);
    }

    /**
     * Called every frame to incrementally cook items, scaled by time scale.
     */
    @Override
    public void update() {
        // Add to cooking timer and cook item
        if (isCooking) {
            if (cookingTime < 0) { // Recipe is fully cooked
                inventoryComponent.setCurrentItem(targetRecipe);
            }
            cookingTime -= gameTime.getDeltaTime();
        }
    }

    /**
     * Method triggered when item added to station.
     */
    private void addItem() {
        // Converting List<Optional<String>> to List<String>
        List<String> templist = new ArrayList<String>();
        for (Optional<String> x : inventoryComponent.getItems()) {
            templist.add(x.get());
        }

        List<String> possibleRecipes = DishFactory.getRecipe(templist);
        if (possibleRecipes.size() == 1) {
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
    private void removeItem() {
        isCooking = false;
    }

    /** @return the string station tpye. */
    public String getStationType() {
        return stationType.name();
    }

    /** @return true if the item is being cooked, false otherwise. */
    public boolean isCooking() {
        return isCooking;
    }

    /** @return time remaining to make the recipe in seconds, scaled by time scale. */
    public long getCookingTime() {
        return cookingTime;
    }
}
