package com.csse3200.game.components.station;

import com.csse3200.game.components.station.StationHoverComponent;
import com.csse3200.game.components.station.StationMealComponent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csse3200.game.components.player.InventoryComponent;

public class MixingBenchHoverComponent extends StationHoverComponent {
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
            // player can place item
            batch.draw(placeKeyImage,
                    position.x + KEY_X_OFFSET,
                    position.y + currentOffset,
                    KEY_WIDTH,
                    KEY_HEIGHT
            );
            currentOffset -= OFFSET_SPACING;
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

        if (!hasItem && inventory.getSize() > 1) {
            // draw rotate key
            batch.draw(rotateKeyImage,
                    position.x + KEY_X_OFFSET,
                    position.y + currentOffset,
                    KEY_WIDTH,
                    KEY_HEIGHT
            );
            currentOffset -= OFFSET_SPACING;
        }

        StationMealComponent mealComponent = entity.getComponent(StationMealComponent.class);
        if (!hasItem && mealComponent.hasMeal()) {
            // draw combine key
            batch.draw(combineKeyImage,
                    position.x + KEY_X_OFFSET,
                    position.y + currentOffset,
                    KEY_WIDTH,
                    KEY_HEIGHT
            );
            currentOffset -= OFFSET_SPACING;
        }

    }

    @Override
    public void draw(SpriteBatch batch) {
        if (entity == null || position == null || scale == null)
            return;

        super.draw(batch);

        if (this.showKeys) {
            drawToolTips(batch);
        }
    }

}