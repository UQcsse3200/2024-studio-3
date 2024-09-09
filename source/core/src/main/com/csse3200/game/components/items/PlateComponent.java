package com.csse3200.game.components.items;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.PlateFactory;

import java.util.Arrays;

/**
 * PlateComponent manages everything about plates
 * state of plate, item on plate, availability of plate, pickup status of plate
 * servable status of plate, stacked status of plate, stackPlateArray to store id of plates in a stacked plates
 * plate id (for single plates) and quantity of plates in a stack
 */
public class PlateComponent extends Component {
    private PlateState state;
    private String itemOnPlate;
    private boolean isAvailable;
    private boolean isServable;
    private boolean isPickedup;
    private boolean isStacked;
    private int[] stackPlateArray;
    private int id;
    int quantity;

    /**
     * 3 possible states of plates
     */
    public enum PlateState {
        CLEAN, DIRTY, WASHING
    }

    /**
     * Constructor for the PlateComponent (stack). Initializes the plate state, quantity,
     * and sets up the stack array
     *
     * @param quantity the initial number of plates in the stack
     */
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

    /**
     * Initializes the stack plate array with generated plate Id based on the quantity
     *
     * @param quantity the number of plates in the stack
     */
    private void initializePlateArray(int quantity) {
        stackPlateArray = new int[quantity];
        for (int i = 0; i < quantity; i++) {
            stackPlateArray[i] = i + 1;
        }
    }

    /**
     * Creating the component. Sets up event listeners for plate
     */
    @Override
    public void create() {
        this.isAvailable = true;
        entity.getEvents().addListener("interactWithPlate", this::interactWithPlate);

    }

    /**
     * Update plate
     */
    @Override
    public void update() {
        //still nothing here mate
    }

    /**
     * Adds a meal to the plate
     * if the state == clean and itemOnPlate == null
     *
     * @param meal name of meal to add to the plate
     */
    public void addMealToPlate(String meal) {
        if (state == PlateState.CLEAN && itemOnPlate == null) {
            itemOnPlate = meal;
            isAvailable = false;
            isServable = true;
        }
    }

    /**
     * Disposes of the plate, marking it as unavailable
     */
    public void dispose() {
        isAvailable = false;
    }

    /**
     * Handle interactions with the plate when a player interacts with it
     *
     * @param fixture the physics fixture involved in the interaction
     * @param player  the player entity interacting with the plate
     *
     * @return true if the plate interaction is handled, false if not
     */
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

    /**
     * Handles interaction logic when player interacts with the plate
     *
     * @param player the player interacting with the plate
     */
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

    /**
     * Handles the logic for picking up a plate from the stack
     *
     * @param player the player picking up the plate
     */
    public void pickup(Entity player) {
        if (quantity > 0) {

            int plateId = stackPlateArray[0];
            stackPlateArray = Arrays.copyOfRange(stackPlateArray, 1, stackPlateArray.length);
            quantity--;
            PlateFactory.disposePlate(entity, quantity);

            Entity singlePlate = PlateFactory.spawnPlate(plateId);
            //Entity singlePlate = PlateFactory.spawnMealOnPlate(plateId, "salad"); //test spawnMealPlates
            PlateComponent singlePlateComponent = singlePlate.getComponent(PlateComponent.class);
            singlePlateComponent.setId(plateId);

            if (quantity == 0) {
                isStacked = false;
                PlateFactory.disposePlate(entity, 0);
            }

        }
    }

    /**
     * Handles logic to let down the plate after it has been picked up
     *
     * @param player the player putting down the plate
     */
    public void letDown(Entity player) {
        //letdown simul
    }

    /**
     * Get Quantity of stack
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get Item on single plate
     *
     * @return item on Plate
     */
    public String getItemOnPlate() {
        return itemOnPlate;
    }

    /**
     * Set Quantity of plate in stack
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return boolean isPickedUP
     */
    private boolean isPickedUp() {
        return isPickedup;
    }

    /**
     * @return boolean isAvailable
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * @return boolean isServable
     */
    public boolean isServable() {
        return isServable;
    }

    /**
     * @return boolean isWashed
     */
    public boolean isWashed() {
        return state == PlateState.CLEAN;
    }

    /**
     * @return boolean isStacked
     */
    public boolean isStacked() {
        return isStacked;
    }

    /**
     * set stacked state
     */
    public void setStacked(boolean isStacked) {
        this.isStacked = isStacked;
    }

    /**
     * @return stackPlateArray in a stacked plate entity
     */
    public int[] getPlateArray() {
        return stackPlateArray;
    }

    /**
     * set Plate Array for a stacked plate entity
     */
    public void setPlateArray(int[] plateArray) {
        this.stackPlateArray = plateArray;
    }

    /**
     * @return id of plate for a single plate
     */
    public int getId() {
        return id;
    }

    /**
     * set Plate id for a single state
     */
    public void setId(int id) {
        this.id = id;
    }
}