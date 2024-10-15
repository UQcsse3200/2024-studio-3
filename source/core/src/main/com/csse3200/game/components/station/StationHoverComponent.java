package com.csse3200.game.components.station;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.rendering.RenderComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.services.ResourceService;

import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.player.InventoryComponent;

import java.util.ArrayList;
import java.util.Objects;

import com.csse3200.game.physics.components.PhysicsComponent;


/**
 * An abstract component that displays the items in an a station's
 * InventoryComponent as images hovering above the entity. This is
 * different to the InventoryDisplay UI component, which displays items
 * in slots that overlay the gameplay screen.
 * To use: add this component to an entity that has an
 * InventoryComponent. This component will NOT work if the entity
 * it is added to does not have a InventoryComponent.
 *
 * This component is also used to display key tooltips indicating
 * available interactions the player can make with the station.
 * Concrete classes should be made for each station type, extending this
 * class and implement the drawToolTips() method to match the relevant
 * key tooltips for the station.
 */
public abstract class StationHoverComponent extends RenderComponent {
    private static final Logger logger = LoggerFactory.getLogger(StationHoverComponent.class);
    private ArrayList<Texture> itemImages;
    private Texture backgroundImage;
    private Texture selectedBackgroundImage;
    protected boolean showKeys = false;
    protected InventoryComponent inventory = null;
    protected ItemComponent currentItem = null;
    protected boolean hasItem = false;
    protected Texture interactKeyImage;
    protected Texture combineKeyImage;
    protected Texture rotateKeyImage;
    protected Texture chopKeyImage;
    protected Texture placeKeyImage;
    protected Texture takeKeyImage;
    protected Texture submitKeyImage;
    protected Texture disposeKeyImage;
    protected Texture cookKeyImage;
    protected Vector2 position;
    protected Vector2 scale;
    private static final float X_OFFSET = 0.2f;
    private static final float Y_OFFSET = 1.0F;
    private static final float SLOT_WIDTH = 0.6f;
    private static final float SLOT_HEIGHT = 0.6f;
    protected final float KEY_WIDTH = 1.0f;
    protected static final float KEY_HEIGHT = 0.3f;

    @Override
    public void create() {
        super.create();
        backgroundImage = new Texture("images/inventory_ui/item_background.png");
        selectedBackgroundImage = new Texture("images/inventory_ui/item_background_selected.png");

        ResourceService resources = ServiceLocator.getResourceService();

        // images used by subclasses
        combineKeyImage = resources.getAsset("images/inventory_ui/combine_key.png", Texture.class);
        rotateKeyImage = resources.getAsset("images/inventory_ui/rotate_key.png", Texture.class);
        chopKeyImage = resources.getAsset("images/inventory_ui/chop_key.png", Texture.class);
        placeKeyImage = resources.getAsset("images/inventory_ui/place_key.png", Texture.class);
        takeKeyImage = resources.getAsset("images/inventory_ui/take_key.png", Texture.class);
        submitKeyImage = resources.getAsset("images/inventory_ui/submit_key.png", Texture.class);
        disposeKeyImage = resources.getAsset("images/inventory_ui/dispose_key.png", Texture.class);
        cookKeyImage = resources.getAsset("images/inventory_ui/cook_key.png", Texture.class);

        ServiceLocator.getRenderService().register(this);

        if (entity != null) {
            // listener for when the InventoryComponent attached to this entity is updated
            entity.getEvents().addListener("updateInventory", this::updateDisplay);

            // listeners for subclasses
            entity.getEvents().addListener("showToolTip", this::showToolTip);
            entity.getEvents().addListener("hideToolTip", this::hideToolTip);

            inventory = entity.getComponent(InventoryComponent.class);

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

        if (entity != null && inventory != null) {
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
     * Updates the inventory display of this StationHoverComponent
     */
    public void updateDisplay() {
        updateImages();
    }

    /**
     * Sets this component to display keybind tooltip icons
     * @param item the item the player is currently holding
     */
    private void showToolTip(ItemComponent item) {
        this.currentItem = item;
        this.hasItem = (item != null);
        this.showKeys = true;
    }

    /**
     * Sets this component to hide keybind tooltip icons
     */
    private void hideToolTip() {
        this.showKeys = false;
    }

    /**
     * Draws the required key tooltips for the current interactions
     * that can be done on this station. Implement in subclass
     * based on station type
     * @param batch The SpriteBatch used for drawing
     */
    public abstract void drawToolTips(SpriteBatch batch);

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

        if (this.showKeys) {
            drawToolTips(batch);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getRenderService().unregister(this);
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