package com.csse3200.game.components.items;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PlateComponent extends Component {
    private static final Logger logger = LoggerFactory.getLogger(PlateComponent.class);
    private PlateState state;
    private String itemOnPlate;
    private boolean isAvailable;
    private boolean isServable;
    private final GameTime timeSource;
    private long washEndTime;

    public enum PlateState {
        CLEAN, DIRTY, IN_USE, WASHING
    }

    public PlateComponent() {
        this.state = PlateState.DIRTY;
        this.itemOnPlate = null;
        this.isAvailable = true;
        this.timeSource = ServiceLocator.getTimeSource();
    }
    @Override
    public void create() {
        logger.info("Plate create");
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isServable() {
        return isServable;
    }

    public boolean isWashed() {
        return state == PlateState.CLEAN;
    }

    public void washPlate() {
        if (state == PlateState.DIRTY) {
            state = PlateState.WASHING;
            washEndTime = timeSource.getTime() + 3000;
            logger.info("Plate washing");
        } else {
            logger.warn("Cannot wash plate");
        }
    }

    @Override
    public void update() {
        if (state == PlateState.WASHING && timeSource.getTime() >= washEndTime) {
            state = PlateState.CLEAN;
            logger.info("Plate clean");
        }
    }

    public boolean addToPlate(String item) {
        if (state == PlateState.CLEAN && itemOnPlate == null) {
            itemOnPlate = item;
            state = PlateState.IN_USE;
            logger.info("Item '{}' added to the plate.", item);
            return true;
        }
        logger.warn("Cannot add item");
        return false;
    }

    public String removeFromPlate() {
        if (state == PlateState.IN_USE) {
            String removedItem = itemOnPlate;
            itemOnPlate = null;
            state = PlateState.CLEAN;
            logger.info("Item '{}' removed from the plate.", removedItem);
            return removedItem;
        }
        logger.warn("Nothing to remove");
        return null;
    }

    public void pickup() {
        if (isAvailable) {
            isAvailable = false;
            logger.info("Plate picked up");
        } else {
            logger.warn("Cannot pickup plate");
        }
    }

    public void letDown() {
        if (!isAvailable) {
            isAvailable = true;
            logger.info("Plate let down");
        } else {
            logger.warn("Cannot letdown plate");
        }
    }

    //Method check isServable --check if it is a meal or not (extract from ItemType to extract meals)
    //if isServable can be served to customers else, can only interact in kitchen


    public void dispose() {
        logger.info("Plate disposed");
        isAvailable = false;
    }

}