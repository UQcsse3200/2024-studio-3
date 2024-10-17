package com.csse3200.game.components.station;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Give the player a fire extinguisher when they interact with it
 */
public class FireExtinguisherHandlerComponent extends Component {
    private static final Logger logger = LoggerFactory.getLogger(FireExtinguisherHandlerComponent.class);

    @Override
    public void create() {
        entity.getEvents().addListener("getPutFireExtinguisher", this::givePutExtinguisher);
    }

    /**
     * Handles getting and putting back the fire extinguisher when the player interacts
     * @param fixture The fixture of the object that was interacted with
     * @param player The player entity
     * @return Whether the fire extinguisher was handled
     */
    public static boolean handleFireExtinguisher(Fixture fixture, Entity player) {
        if (fixture.getUserData() instanceof Entity parentEntity) {
            // Check if the fire extinguisher is attached
            FireExtinguisherHandlerComponent fireExtinguisher = parentEntity.getComponent(FireExtinguisherHandlerComponent.class);
            if (fireExtinguisher != null) {
                fireExtinguisher.givePutExtinguisher(player);
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * Gives or puts back the fire extinguisher from the inventory
     * @param player The player entity component
     */
    public void givePutExtinguisher(Entity player) {
        InventoryComponent inventory = player.getComponent(InventoryComponent.class);
        if (inventory == null) {
            logger.error("No inventory component found");
            return;
        }
        // Check to see if already holding a fire extinguisher
        boolean hasFireExtinguisher = false;
        int fireExtinguisherIndex = -1;
        List<ItemComponent> items = inventory.getItems();
        for (int index = 0; index < inventory.getCapacity(); index++) {
            ItemComponent item = items.get(index);
            if (item != null && item.getItemType() == ItemType.FIREEXTINGUISHER) {
                // Has a fire extinguisher
                hasFireExtinguisher = true;
                fireExtinguisherIndex = index;
                break;
            }
        }

        if (hasFireExtinguisher) {
            // Give back the fire extinguisher
            this.takeExtinguisher(player, fireExtinguisherIndex);
        } else {
            // Give the player the fire extinguisher
            this.giveExtinguisher(player);
        }
    }

    /**
     * Takes the fire extinguisher from the player
     * @param player The player entity component
     * @param fireExtinguisherIndex The index of the fireExtinguisher
     */
    private void takeExtinguisher(Entity player, int fireExtinguisherIndex) {
        InventoryComponent inventory = player.getComponent(InventoryComponent.class);
        inventory.removeAt(fireExtinguisherIndex);
    }

    /**
     * Gives the player a fire extinguisher
     * @param player THe player entity component
     */
    private void giveExtinguisher(Entity player) {
        InventoryComponent inventory = player.getComponent(InventoryComponent.class);
        // Check if the inventory is full
        if (inventory.isFull()) {
            logger.info("Can't give the player the fire extinguisher since inventory is full");
        } else {
            // Create a fire extinguisher
            ItemComponent fireExtinguisher = new ItemComponent("Fire Extinguisher", ItemType.FIREEXTINGUISHER, 1);
            inventory.addItem(fireExtinguisher);
        }
    }
}
