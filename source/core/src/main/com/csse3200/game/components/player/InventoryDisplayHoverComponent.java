package com.csse3200.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.rendering.RenderComponent;
import com.csse3200.game.services.ServiceLocator;
import java.lang.IllegalArgumentException;
import com.csse3200.game.components.items.ItemComponent;
import java.util.ArrayList;
import com.csse3200.game.physics.components.PhysicsComponent;


/**
 * An component that display's the items in an entity's InventoryComponent as
 * images hovering above the entity. This is different to the InventoryDisplay
 * UI component, which displays items in slots that overlay the gameplay screen.
 *
 * To use: add this component to an entity that has an
 * InventoryComponent. This component will NOT work if the entity
 * it is added to does not have a InventoryComponent.
 * The main use for this component is to show the inventories of the stations to the player.
 */
public class InventoryDisplayHoverComponent extends RenderComponent {
    private static final Logger logger = LoggerFactory.getLogger(InventoryDisplayHoverComponent.class);
    private ArrayList<Texture> itemImages;
    private ShapeRenderer shapeRenderer;
    private Vector2 position;
    private Vector2 scale;
    private static final float X_OFFSET = 0.2f;
    private static final float Y_OFFSET = 1.0F;
    private static final float slotWidth = 0.5f;
    private static final float slotHeight = 0.5f;

    @Override
    public void create() {
        super.create();
        shapeRenderer = new ShapeRenderer();
        ServiceLocator.getRenderService().register(this);

        if (entity != null) {
            // listener for when the InventoryComponent attached to this entity is updated
            entity.getEvents().addListener("updateInventory", this::update);
            // need to use the physics body position of the entity as
            // the regular getPosition() on stations does not return the correct position.
            position = entity.getComponent(PhysicsComponent.class).getBody().getPosition();
            scale = entity.getScale();
            updateImages();
        }
    }

    /**
     *
     */
    private void updateImages() {
        itemImages = new ArrayList<>();

        if (entity != null) {
            InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
            for (ItemComponent item : inventory.getItems()) {
                if (item != null ) {
                    String itemTexturePath = item.getTexturePath();
                    Texture itemTexture;
                    if (itemTexturePath != null) {
                        itemTexture = new Texture(itemTexturePath);
                    } else {
                        // placeholder null image if item image cannot be found
                        itemTexture = new Texture("images/inventory_ui/null_image.png");
                    }
                    itemImages.add(itemTexture);
                }
            }
        }
    }

    /**
     * Updates this InventoryDisplay to reflect the current state of the InventoryComponent
     * of this component's parent entity.
     */
    public void update() {
        updateImages();
    }

    @Override
    public void draw(SpriteBatch batch)  {
        if (entity == null || position == null || scale == null)
            return;
        for (int i = 0; i < itemImages.size(); i++) {
            batch.draw(itemImages.get(i),
                position.x + X_OFFSET,
                position.y + (i * slotHeight) + Y_OFFSET,
                slotWidth,
                slotHeight
            );
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getRenderService().unregister(this);
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }

    @Override
    public void setStage(Stage mock) {

    }

}