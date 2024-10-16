package com.csse3200.game.components.station;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A component used to display interaction key tooltips for
 * a bin station component.
 */
public class BinHoverComponent extends StationHoverComponent {
    private static final float KEY_Y_OFFSET = 0.9f;

    /**
     * Draws the required key tooltips for the current interactions
     * that can be done on this station.
     * @param batch The SpriteBatch used for drawing
     */
    public void drawToolTips(SpriteBatch batch) {
        if (hasItem) {
            // player can dispose item
            batch.draw(disposeKeyImage,
                    position.x,
                    position.y + KEY_Y_OFFSET,
                    KEY_WIDTH,
                    KEY_HEIGHT
            );
        }
    }

    @Override
    public void draw(SpriteBatch batch)  {
        if (entity == null || position == null || scale == null)
            return;
        if (this.showKeys) {
            drawToolTips(batch);
        }
    }
}