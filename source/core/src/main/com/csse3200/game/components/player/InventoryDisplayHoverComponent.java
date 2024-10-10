package com.csse3200.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.rendering.RenderComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.items.ItemComponent;
import java.util.ArrayList;
import java.util.Objects;
import com.csse3200.game.components.station.StationMealComponent;
import com.csse3200.game.physics.components.PhysicsComponent;


/**
 * A component that display's the items in an entity's InventoryComponent as
 * images hovering above the entity. This is different to the InventoryDisplay
 * UI component, which displays items in slots that overlay the gameplay screen.
 * To use: add this component to an entity that has an
 * InventoryComponent. This component will NOT work if the entity
 * it is added to does not have a InventoryComponent.
 * The main use for this component is to show the inventories of the stations to the player.
 */
public class InventoryDisplayHoverComponent extends RenderComponent {
    private static final Logger logger = LoggerFactory.getLogger(InventoryDisplayHoverComponent.class);
    private ArrayList<Texture> itemImages;
    private Texture backgroundImage;
    private Texture selectedBackgroundImage;
    private boolean showKeys = false;
    private boolean isMixingStation = false;
    private Texture interactKeyImage;
    private Texture combineKeyImage;
    private Texture rotateKeyImage;
    private ShapeRenderer shapeRenderer;
    private Vector2 position;
    private Vector2 scale;
    private static final float X_OFFSET = 0.2f;
    private static final float Y_OFFSET = 1.0F;
    private static final float SLOT_WIDTH = 0.6f;
    private static final float SLOT_HEIGHT = 0.6f;
    private static final float KEY_WIDTH = 1.0f;
    private static final float KEY_HEIGHT = 0.3f;

    @Override
    public void create() {
        super.create();
        backgroundImage = new Texture("images/inventory_ui/item_background.png");
        selectedBackgroundImage = new Texture("images/inventory_ui/item_background_selected.png");
        interactKeyImage = new Texture("images/inventory_ui/interact_key.png");
        combineKeyImage = new Texture("images/inventory_ui/combine_key.png");
        rotateKeyImage = new Texture("images/inventory_ui/rotate_key.png");
        shapeRenderer = new ShapeRenderer();
        ServiceLocator.getRenderService().register(this);

        if (entity != null) {
            // listener for when the InventoryComponent attached to this entity is updated
            entity.getEvents().addListener("updateInventory", this::update);

            entity.getEvents().addListener("showToolTip", this::showToolTip);
            entity.getEvents().addListener("hideToolTip", this::hideToolTip);

            isMixingStation = entity.getComponent(StationMealComponent.class) != null;

            // need to use the physics body position of the entity as
            // the regular getPosition() on stations does not return the correct position.
            position = entity.getComponent(PhysicsComponent.class).getBody().getPosition();
            scale = entity.getScale();
            updateImages();
        }
        logger.info("Created InventoryDisplayHoverComponent");
    }

    /**
     * Updates the item images to reflect the current items
     * in the inventory
     */
    private void updateImages() {
        itemImages = new ArrayList<>();

        if (entity != null) {
            InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
            for (ItemComponent item : inventory.getItems()) {
                if (item != null ) {
                    String itemTexturePath = item.getTexturePath();
                    Texture itemTexture;
                    // placeholder null image if item image cannot be found
                    itemTexture = new Texture(Objects.requireNonNullElse(
                            itemTexturePath,
                            "images/inventory_ui/null_image.png"));
                    itemImages.add(itemTexture);
                }
            }
        }
    }



    /**
     * Updates this InventoryDisplay to reflect the current state of the InventoryComponent
     * of this component's parent entity.
     */
    @Override
    public void update() {
        updateImages();
    }

    /**
     * Sets this component to display keybind tooltip icons
     */
    private void showToolTip() {
        this.showKeys = true;
    }

    /**
     * Sets this component to hide keybind tooltip icons
     */
    private void hideToolTip() {
        this.showKeys = false;
    }

    @Override
    public void draw(SpriteBatch batch)  {
        if (entity == null || position == null || scale == null)
            return;
        for (int i = 0; i < itemImages.size(); i++) {
            // draw selected background image for the next item to be taken out
            // (if there is more than 1 item displayed)
            if (i == itemImages.size() - 1 && itemImages.size() > 1) {
                batch.draw(selectedBackgroundImage,
                    position.x + X_OFFSET,
                    position.y + (i * SLOT_HEIGHT) + Y_OFFSET,
                    SLOT_WIDTH,
                    SLOT_HEIGHT
                );
            } else {
                batch.draw(backgroundImage,
                    position.x + X_OFFSET,
                    position.y + (i * SLOT_HEIGHT) + Y_OFFSET,
                    SLOT_WIDTH,
                    SLOT_HEIGHT
                );
            }
            batch.draw(itemImages.get(i),
                position.x + X_OFFSET + 0.1f,
                position.y + (i * SLOT_HEIGHT) + Y_OFFSET + 0.1f,
                SLOT_WIDTH - 0.2f,
                SLOT_HEIGHT - 0.2f
            );
            }
        if (showKeys) {
            batch.draw(interactKeyImage,
                    position.x,
                    position.y + 0.7f,
                    KEY_WIDTH,
                    KEY_HEIGHT
            );
            if (isMixingStation) {
                batch.draw(rotateKeyImage,
                        position.x,
                        position.y + 0.4f,
                        KEY_WIDTH,
                        KEY_HEIGHT
                );
                batch.draw(combineKeyImage,
                        position.x,
                        position.y + 0.1f,
                        KEY_WIDTH,
                        KEY_HEIGHT
                );
            }

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
    public int getLayer() {
        return 3; // currently overlays the player, but decreasing this to 1 makes it hide behind the stations
    }

    @Override
    public void setStage(Stage mock) {
        // This function needed to exist but not needed to be implemented
    }

}