package com.csse3200.game.components.items;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.PlateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PlateComponent extends Component {
    private static final Logger logger = LoggerFactory.getLogger(PlateComponent.class);
    private PlateState state;
    private String itemOnPlate;
    private boolean isAvailable;
    private boolean isServable;
    private boolean isPickedup;
    int quantity;

    public enum PlateState {
        CLEAN, DIRTY, WASHING
    }

    public PlateComponent(int quantity) {
        this.state = PlateState.CLEAN;
        this.itemOnPlate = null;
        this.isAvailable = true;
        this.isServable = false;
        this.isPickedup = false;
        this.quantity = quantity;
    }
    @Override
    public void create() {
        logger.info("Plate create");
        this.isAvailable = true;
        entity.getEvents().addListener("interactWithPlate", this::interactWithPlate);
    }

    @Override
    public void update() {
        //still nothing here mate
    }

    public void addMealToPlate(String meal) {
        if (state == PlateState.CLEAN && itemOnPlate == null) {
            itemOnPlate = meal;
            isAvailable = false;
            isServable = true;
            logger.info("Item '{}' on plate.", meal);
            return;
        }
        logger.warn("Cant add meal");
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
            letDown(player);
        }
    }

    public void pickup(Entity player) {
        if (quantity >= 1) {
            quantity--;
            logger.info("Plate picked up. Remaining: {}", quantity);
            PlateFactory.disposePlate(entity, quantity);
        } else {
            PlateFactory.disposePlate(entity, 0);
            logger.info("Plate stack finish");
        }
    }

    public void letDown(Entity player) {
        //letdown simul
    }

    public void dispose() {
        logger.info("Plate disposed");
        isAvailable = false;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItemOnPlate(){
        return itemOnPlate;
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