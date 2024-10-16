package com.csse3200.game.components.station;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A component used to display interaction key tooltips for
 * a submission station component.
 */
public class SubmitHoverComponent extends StationHoverComponent {
    private static final float KEY_X_OFFSET = 0.1f;
    private static final float KEY_Y_OFFSET = 0.6f;

    /**
     * Draws the required key tooltips for the current interactions
     * that can be done on this station.
     * @param batch The SpriteBatch used for drawing
     */
    public void drawToolTips(SpriteBatch batch) {
        // Check the current item can be submitted
        StationServingComponent servingComponent = entity.getComponent(StationServingComponent.class);

        if (hasItem && servingComponent.canSubmitMeal(currentItem)) {
            // player can dispose item
            batch.draw(submitKeyImage,
                    position.x + KEY_X_OFFSET,
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