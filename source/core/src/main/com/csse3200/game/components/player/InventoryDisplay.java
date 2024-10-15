
package com.csse3200.game.components.player;

import java.util.ArrayList;
import java.util.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.components.items.ItemComponent;

/**
 * A UI component used to display slots of an InventoryComponent and the
 * items they hold. To use: add this component to an entity that has a
 * InventoryComponent. This component will not work if the entity
 * it is added to does not have a InventoryComponent.
 */
public class InventoryDisplay extends UIComponent {
    private Table table;
    private final int slotSize;
    private ArrayList<Stack> slots;

    /**
     * Creates an InventoryDisplay with a default background slot image and slot
     * size.
     * Requires that the entity this component is being added to also have
     * an InventoryComponent.
     */
    public InventoryDisplay() {
        this.slotSize = 200;
    }

    /**
     * Creates an InventoryDisplay that displays slots with the size provided.
     * @param slotSize how large the slots will be displayed.
     * @throws java.lang.IllegalArgumentException if slotSize is less than 1
     * Requires that the entity this component is being added to also have
     * an InventoryComponent.
    */
    public InventoryDisplay(int slotSize) {
        if (slotSize < 1) {
            throw new IllegalArgumentException("slotSize must be a positive non-zero integer");
        }
        this.slotSize = slotSize;
    }

    public int getSlotSize() {
        return this.slotSize;
    }

    @Override
    public void create() {
        super.create();

        if (entity != null) {
            // listener for when the InventoryComponent attached to this entity is updated
            entity.getEvents().addListener("updateInventory", this::update);
        }

        addActors();
    }

    /**
     * Creates the required actors to display each inventory slot, and adds them
     * to the UI table.
     */
    private void addActors() {
        // Create table
        table = new Table();
        slots = new ArrayList<>();
        table.bottom().left();
        table.padBottom(10f);

        // create slot images
        for (int i = 0; i < entity.getComponent(InventoryComponent.class).getCapacity(); i++) {
            Stack currentStack = new Stack();
            slots.add(currentStack);
            currentStack.add(new Image(ServiceLocator.getResourceService().getAsset("images/inventory_ui/slot.png", Texture.class)));

            // add item image if there is an item in the slot
            ItemComponent item = entity.getComponent(InventoryComponent.class).getItemAt(i);
            if (item != null) {
                Table itemPadding = new Table();

                String itemTexturePath = item.getTexturePath();
                Image itemImage;


                // null image if no texture found for item
                itemImage = new Image(ServiceLocator.getResourceService().getAsset(Objects.requireNonNullElse(itemTexturePath, "images/inventory_ui/null_image.png"), Texture.class));

                itemPadding.add(itemImage).pad(20);
                currentStack.add(itemPadding);
            }
            table.add(currentStack).size(slotSize).padLeft(20);

        }

        stage.addActor(table);
    }

    /**
     * Updates the UI display to reflect the current state of the InventoryComponent
     * of this component's parent entity.
     */
    private void updateDisplay() {

        for (int i = 0; i < slots.size(); i++) {
            Stack currentStack = slots.get(i);
            currentStack.clear();
            currentStack.add(new Image(ServiceLocator.getResourceService().getAsset(
                    "images/inventory_ui/slot.png",
                    Texture.class)));

            // add item image if there is an item in the slot
            ItemComponent item = entity.getComponent(InventoryComponent.class).getItemAt(i);
            if (item != null) {
                Table itemPadding = new Table();

                String itemTexturePath = item.getTexturePath();
                Image itemImage;
                // null image if no texture found for item
                itemImage = new Image(ServiceLocator.getResourceService().getAsset(
                        Objects.requireNonNullElse(itemTexturePath,
                        "images/inventory_ui/null_image.png"),
                        Texture.class));

                itemPadding.add(itemImage).pad(20);
                currentStack.add(itemPadding);
            }
        }

        }

    /**
     * Updates this InventoryDisplay to reflect the current state of the InventoryComponent
     * of this component's parent entity.
     */
    @Override
    public void update() {
        updateDisplay();
    }

    @Override
    public void draw(SpriteBatch batch)  {
        // changing position on window resizing is handled by the stage
    }

    @Override
    public void dispose() {
        super.dispose();
        table.remove();
        for (Stack slot : slots) {
            slot.remove();
        }
    }

    @Override
    public void setStage(Stage mock) {
        this.stage = mock;
    }
}