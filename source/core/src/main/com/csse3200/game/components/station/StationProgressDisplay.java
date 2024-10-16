package com.csse3200.game.components.station;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.rendering.RenderComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemTimerComponent;
import com.csse3200.game.components.items.ChopIngredientComponent;
import com.csse3200.game.components.items.CookIngredientComponent;
import com.csse3200.game.physics.components.PhysicsComponent;


/**
 * A component used for a station entity that displays a progress bar indicating
 * the chop/cook percentage of the item held in the station. The progress
 * bar will only display if there is an item currently placed in
 * the station inventory, and if the item has a ChopIngredientComponent
 * or a CookIngredientComponent (and the completion percent is less than 100%).
 *
 * To use: add this component to an entity that has a
 * StationItemHandlerComponent. This component will NOT work if the entity
 * it is added to does not have a StationItemHandlerComponent.
 */
public class StationProgressDisplay extends RenderComponent {
    private static final Logger logger = LoggerFactory.getLogger(StationProgressDisplay.class);
    private Texture barOutline;
    private Texture barFill;
    private Vector2 position;
    private Vector2 scale;
    private float barPercentage;
    private boolean displayBar;
    private static final float X_OFFSET = 0.0f;
    private static final float Y_OFFSET = 0.05F;
    private static final float barMaxWidth = 1.0f;
    private static final float barHeight = 0.2f;


    @Override
    public void create() {
        super.create();
        //ServiceLocator.getRenderService().register(this);
        barOutline = new Texture("images/inventory_ui/station_progress_bar_outline.png");
        barFill = new Texture("images/inventory_ui/station_progress_bar_fill.png");
        barPercentage = 0.0f;
        displayBar = false;

        if (entity != null) {
            // need to use the physics body position of the entity as
            // the regular getPosition() on stations does not return the correct position.
            position = entity.getComponent(PhysicsComponent.class).getBody().getPosition();
            scale = entity.getScale();
        }
    }

    @Override
    public void update() {
        ItemComponent item = entity.getComponent(StationItemHandlerComponent.class).peek();
        if (item == null) {
            resetBar();
            return;
        }

        ItemTimerComponent timerItem = item.getEntity().getComponent(ChopIngredientComponent.class);
        if (timerItem == null) {
            // could not find a chop component, check if theres a cook component
            timerItem = item.getEntity().getComponent(CookIngredientComponent.class);
        }
        if (timerItem == null) {
            resetBar();
            return;
        }

        barPercentage = timerItem.getCompletionPercent() / 100;
        logger.info(String.valueOf(barPercentage));
        if (barPercentage < 1.0f) {
            // if completion percent is less than 100%, then display the
            // progress bar
            displayBar = true;
        } else if (barPercentage >= 1.0f && timerItem instanceof CookIngredientComponent
                && ((CookIngredientComponent) timerItem).getIsCooking()) {
            barPercentage = 1.0f;
            displayBar = true;
        } else {
            displayBar = false;
            entity.getEvents().trigger("updateInventory");
        }
    }

    private void resetBar() {
        displayBar = false;
        barPercentage = 0.0f;
    }


    @Override
    public void draw(SpriteBatch batch)  {
        if (entity == null || position == null || scale == null) {
            return;
        }
        if (displayBar) {
            batch.draw(barOutline,
                    position.x + X_OFFSET,
                    position.y + Y_OFFSET,
                    barMaxWidth,
                    barHeight
            );
            batch.draw(barFill,
                    position.x + X_OFFSET,
                    position.y + Y_OFFSET,
                    barMaxWidth * barPercentage,
                    barHeight
            );
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (barOutline != null) {
            barOutline.dispose();
        }
        if (barFill != null) {
            barFill.dispose();
        }
        // ServiceLocator.getRenderService().unregister(this);
    }

    @Override
    public int getLayer() {
        return 3; // currently overlays the player, but decreasing this to 1 makes it hide behind the stations
    }

    @Override
    public void setStage(Stage mock) {
        //Left empty as not needed for component
    }

}