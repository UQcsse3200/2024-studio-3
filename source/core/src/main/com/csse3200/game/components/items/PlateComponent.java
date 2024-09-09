package com.csse3200.game.components.items;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.PlateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


public class PlateComponent extends Component {
    private static final Logger logger = LoggerFactory.getLogger(PlateComponent.class);
    private PlateState state;
    private String itemOnPlate;
    private boolean isAvailable;
    private boolean isServable;
    private boolean isPickedup;
    private boolean isStacked;
    private int[] stackPlateArray;
    private int id;
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
        this.isStacked = true;
        this.quantity = quantity;
        this.id = -1;
        initializePlateArray(quantity);
    }

    private void initializePlateArray(int quantity) {
        stackPlateArray = new int[quantity];
        for (int i = 0; i < quantity; i++) {
            stackPlateArray[i] = i + 1;
        }
    }

    @Override
    public void create() {
        //logger.info("Plate stack create");
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
            //logger.info("Item '{}' on plate.", meal);
            return;
        }
        //logger.warn("cannot add meal");
    }

    public void dispose() {
        logger.info("Plate disposed");
        isAvailable = false;
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
            return;
        }

        if (isAvailable()) {
            pickup(player);
        } else if (isPickedUp()) {
            letDown(player);
        }
    }

    public void pickup(Entity player) {
        if (quantity > 0) {

            int plateId = stackPlateArray[0];
            stackPlateArray = Arrays.copyOfRange(stackPlateArray, 1, stackPlateArray.length);
            quantity--;
            //logger.info("Plate picked up with ID {}. Remain: {}", plateId, quantity);
            PlateFactory.disposePlate(entity, quantity);
            //logger.info("Remaining array IDs: {}", Arrays.toString(stackPlateArray));

            Entity singlePlate = PlateFactory.spawnPlate(plateId);
            //Entity singlePlate = PlateFactory.spawnMealOnPlate(plateId, "salad"); //test sahaja
            PlateComponent singlePlateComponent = singlePlate.getComponent(PlateComponent.class);
            singlePlateComponent.setId(plateId);

            if (quantity == 0) {
                isStacked = false;
                /*PlateFactory.disposePlate(entity, 0);*/
                //logger.info("Plate stack finished.");
                //logger.info("Remaining array IDs: {}", Arrays.toString(stackPlateArray));
            }

        } else {
            //logger.warn("No plates left");
        }
    }

    public void letDown(Entity player) {
        //letdown simul
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItemOnPlate() {
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

    public boolean isStacked() {
        return isStacked;
    }

    public void setStacked(boolean isStacked) {
        this.isStacked = isStacked;
    }

    public int[] getPlateArray() {
        return stackPlateArray;
    }

    public void setPlateArray(int[] plateArray) {
        this.stackPlateArray = plateArray;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}