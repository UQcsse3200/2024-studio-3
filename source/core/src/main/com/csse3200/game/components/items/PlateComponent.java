package com.csse3200.game.components.items;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.PlateFactory;
import com.csse3200.game.components.items.ItemType;

/**
 * PlateComponent manages everything about plates
 * state of plate, item on plate, availability of plate, pickup status of plate
 * servable status of plate, stacked status of plate, stackPlateArray to store id of plates in a stacked plates
 * plate id (for single plates) and quantity of plates in a stack
 */
public class PlateComponent extends ItemComponent {
    private PlateState state;
    private int id;

    /**
     * 2 possible states of plates
     */
    public enum PlateState {
        CLEAN, DIRTY
    }

    /**
     * Constructor for the PlateComponent. Initialises the plate state and id of the plate.
     * 
     * @param id - the unique id for the plate component being created. 
     */
    public PlateComponent(int id) {
        super("Plate", ItemType.PLATE, 1);
        this.state = PlateState.CLEAN;
        this.id = id;
    }

    /**
     * Creating the component. Sets up event listeners for plate
     */
    @Override
    public void create() {
        entity.getEvents().addListener("interactWithPlate", this::interactWithPlate);
    }

    /**
     * Update plate, nothing to see here.
     */
    @Override
    public void update() {
        // nothing of note.
    }

    /**
     * 
     * @return
     */
    public PlateState getPlateState() {
        return this.state;
    }

    /**
     * Returns the plate id.
     * 
     * @return - an integer representing the plate id.
     */
    public int getPlateId() {
        return this.id;
    }

    /**
     * Returns true if the plate is clean.
     * 
     * @return - true if the plate is clean, false otherwise.
     */
    public boolean isClean() {
        return this.getPlateState() == PlateState.CLEAN;
    }

    /**
     * Sets the plate state to CLEAN.
     */
    public void washPlate() {
        this.state = PlateState.CLEAN;
    }

    /**
     * Sets the plate state to DIRTY.
     */
    public void usePlate() {
        this.state = PlateState.DIRTY;
    }

}