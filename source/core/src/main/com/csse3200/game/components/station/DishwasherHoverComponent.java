package com.csse3200.game.components.station;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csse3200.game.components.items.PlateComponent;
import com.csse3200.game.components.player.InventoryComponent;

public class DishwasherHoverComponent extends StationHoverComponent {

    private static final float FIRST_OFFSET = 0.7f;
    private static final float OFFSET_SPACING = 0.3f;
    private static final float KEY_X_OFFSET = 0.1f;

    /**
     * Draws the required key tooltips for the current interactions
     * that can be done on this station.
     * @param batch The SpriteBatch used for drawing
     */
    public void drawToolTips(SpriteBatch batch) {
        float currentOffset = FIRST_OFFSET;

        inventory = entity.getComponent(InventoryComponent.class);

        if (hasItem && inventory.getSize() < inventory.getCapacity()) {

            // Check if the item is cookable
            if (currentItem != null && currentItem instanceof PlateComponent) {
                PlateComponent plateComponent = (PlateComponent) currentItem;

                if (currentItem.getEntity().getComponent(PlateComponent.class) != null
                        && !plateComponent.isClean()) {

                    batch.draw(placeKeyImage,
                            position.x + KEY_X_OFFSET,
                            position.y + currentOffset,
                            KEY_WIDTH,
                            KEY_HEIGHT
                    );
                    currentOffset -= OFFSET_SPACING;
                }
            }
        }

        if (!hasItem && inventory.getSize() > 0) {
            // player can take item
            batch.draw(takeKeyImage,
                    position.x + KEY_X_OFFSET,
                    position.y + currentOffset,
                    KEY_WIDTH,
                    KEY_HEIGHT
            );
            currentOffset -= OFFSET_SPACING;
        }
    }    
    
}
