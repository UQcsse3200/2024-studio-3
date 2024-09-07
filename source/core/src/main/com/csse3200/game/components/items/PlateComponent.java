package com.csse3200.game.components.items;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
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
    private boolean isPickedup;
    private int plateIndex;
    //private final GameTime timeSource;
    //private long washEndTime;

    public enum PlateState {
        CLEAN, DIRTY, IN_USE, WASHING
    }

    public PlateComponent() {
        this.state = PlateState.DIRTY;
        this.itemOnPlate = null;
        this.isAvailable = true;
        this.isServable = false;
        this.isPickedup = false;
        this.plateIndex = -1;
        //this.timeSource = ServiceLocator.getTimeSource(); --unused until cleanplate is implemented
    }
    @Override
    public void create() {
        logger.info("Plate create");
        this.isAvailable = true;
        entity.getEvents().addListener("interactWithPlate", this::interactWithPlate);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    //Method check isServable --check if it is a meal or not (extract from ItemType to extract meals)
    //if isServable can be served to customers else, can only interact in kitchen
    public boolean isServable() {
        return isServable;
    }

    public boolean isWashed() {
        return state == PlateState.CLEAN;
    }

    /* wait to be implemented with washing station
    public void washPlate() {
        if (state == PlateState.DIRTY) {
            state = PlateState.WASHING;
            washEndTime = timeSource.getTime() + 3000;
            logger.info("Plate washing");
        } else {
            logger.warn("Cannot wash plate");
        }
    }
    */
    @Override
    public void update() {

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

    public static boolean handlePlateInteraction(Fixture fixture, Entity player) {
        if (fixture.getUserData() instanceof Entity plateEntity) {
            PlateComponent plateComponent = plateEntity.getComponent(PlateComponent.class);
            if (plateComponent != null) {
                plateComponent.interactWithPlate(player);
                return true;
            }
        }
        return false;
    }

    public void interactWithPlate(Entity player) {
        InventoryComponent inventory = player.getComponent(InventoryComponent.class);
        if (inventory == null) {
            logger.info("No inventory component");
            return;
        }

        if (isAvailable()) {
            pickup(player);
        } else if (isPickedUp()) {
            letDown(player, plateIndex);
        }
    }


    public void pickup(Entity player) {
        InventoryComponent inventory = player.getComponent(InventoryComponent.class);
        if (isAvailable && inventory.isEmpty()) {
            isAvailable = false;
            ItemComponent plateItem = new ItemComponent("Plate", ItemType.PLATE, 1);
            inventory.addItem(plateItem);
            logger.info("plate pick up");
        } else {
            if (inventory.isFull()){
                logger.info("Inventory full.");
            }else{
                logger.info("Cannot pickup plate because other stuff");
            }

        }
    }

    public void letDown(Entity player, int plateIndex) {
        if (!isAvailable) {
            isAvailable = true;
            InventoryComponent inventory = player.getComponent(InventoryComponent.class);
            inventory.removeAt(plateIndex);
            logger.info("Plate let down");
        } else {
            logger.warn("Cannot letdown plate");
        }
    }

    private boolean isPickedUp() {
        return isPickedup == true;
    }

    public void dispose() {
        logger.info("Plate disposed");
        isAvailable = false;
    }

}