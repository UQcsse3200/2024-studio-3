package com.csse3200.game.components.items;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.PlateFactory;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


public class PlateComponent extends Component {
    private static final Logger logger = LoggerFactory.getLogger(PlateComponent.class);
    private PlateState state;
    private String itemOnPlate;
    private boolean isAvailable;
    private boolean isServable;
    private boolean isPickedup;
    private int plateIndex;
    int quantity;
    //private final GameTime timeSource;
    //private long washEndTime;

    public enum PlateState {
        CLEAN, DIRTY, IN_USE, WASHING
    }

    public PlateComponent(int quantity) {
        this.state = PlateState.CLEAN;
        this.itemOnPlate = null;
        this.isAvailable = true;
        this.isServable = false;
        this.isPickedup = false;
        this.plateIndex = -1;
        this.quantity = quantity;
        //this.timeSource = ServiceLocator.getTimeSource(); --unused until cleanplate is implemented
    }
    @Override
    public void create() {
        logger.info("Plate create");
        System.out.println("Plate create");
        this.isAvailable = true;
        entity.getEvents().addListener("interactWithPlate", this::interactWithPlate);
    }

    @Override
    public void update() {
        //still nothing here mate
    }

    public boolean addMealToPlate(String meal) {
        if (state == PlateState.CLEAN && itemOnPlate == null) {
            itemOnPlate = meal;
            state = PlateState.IN_USE;
            logger.info("Item '{}' added to the plate.", meal);
            return true;
        }
        logger.warn("Cannot add item");
        return false;
    }

    public static boolean handlePlateInteraction(Fixture fixture, Entity player) {
        //System.out.println("handlePlateInteraction trigger");
        if (fixture.getUserData() instanceof Entity plateEntity) {
            PlateComponent plateComponent = plateEntity.getComponent(PlateComponent.class);
            //System.out.println("instance create");
            if (plateComponent != null) {
                plateComponent.interactWithPlate(player);
                //System.out.println("platecomp Null");
                return true;
            }
        }
        return false;
    }

    public void interactWithPlate(Entity player) {
        //System.out.println("interactwithplate trigger");
        InventoryComponent inventory = player.getComponent(InventoryComponent.class);
        if (inventory == null) {
            //logger.info("inventory invalid");
            return;
        }else{
            //logger.info("inventory valid");
        }

        if (isAvailable()) {
            pickup(player);
        } else if (isPickedUp()) {
            letDown(player, plateIndex);
        }
    }

    public void pickup(Entity player) {
        if (quantity > 1) {
            quantity--;
            logger.info("Plate picked up. Remaining quantity: {}", quantity);
            PlateFactory.disposePlate(entity, quantity);
        } else {
            PlateFactory.disposePlate(entity, 0);
            logger.info("Plate stack finish");
        }
    }

    public void letDown(Entity player, int plateIndex) {
        //letdown simul
    }

    public void dispose() {
        logger.info("Plate disposed");
        isAvailable = false;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private boolean isPickedUp() {
        return isPickedup;
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

}