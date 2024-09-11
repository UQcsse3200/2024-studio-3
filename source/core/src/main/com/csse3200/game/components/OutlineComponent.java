package com.csse3200.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.Component;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ServiceLocator;

/**
 * Component for adding and removing an outline effect on entities.
 */
public class OutlineComponent extends Component {
    private static final Color OUTLINE_COLOR = Color.RED;  // Outline color
    private static final Color ORIGINAL_COLOR = Color.WHITE;  // Original color
    private static final float OUTLINE_SCALE_FACTOR = 1.1f;  // Slightly enlarge for outline effect

    private boolean isOutlined = false;
    private TextureRenderComponent textureRenderComponent;

    @Override
    public void create() {
        textureRenderComponent = entity.getComponent(TextureRenderComponent.class);
        if (textureRenderComponent == null) {
            System.out.println("Error: TextureRenderComponent not found on entity.");
        }
    }

    /**
     * Adds an outline effect by rendering a slightly enlarged version of the texture with the outline color.
     */
    public void addOutline(SpriteBatch batch) {
        if (textureRenderComponent != null) {
            Vector2 position = entity.getPosition();
            Vector2 scale = entity.getScale();

            // Apply the outline color to the batch
            batch.setColor(OUTLINE_COLOR);

            // Draw the texture slightly larger to simulate an outline
            batch.draw(ServiceLocator.getResourceService().getAsset(textureRenderComponent.getTexturePath(), com.badlogic.gdx.graphics.Texture.class),
                    position.x - (scale.x * 0.05f), position.y - (scale.y * 0.05f),  // Offset to center the outline
                    scale.x * OUTLINE_SCALE_FACTOR, scale.y * OUTLINE_SCALE_FACTOR);

            // Reset the batch color to original
            batch.setColor(ORIGINAL_COLOR);

            // Draw the original texture on top of the outline
            batch.draw(ServiceLocator.getResourceService().getAsset(textureRenderComponent.getTexturePath(), com.badlogic.gdx.graphics.Texture.class),
                    position.x, position.y, scale.x, scale.y);
        } else {
            System.out.println("Error: Unable to apply outline. No TextureRenderComponent found.");
        }
    }

    /**
     * Removes the outline effect by rendering the texture with the original color.
     */
    public void removeOutline(SpriteBatch batch) {
        if (textureRenderComponent != null) {
            Vector2 position = entity.getPosition();
            Vector2 scale = entity.getScale();

            // Render the original texture without any outline
            batch.setColor(ORIGINAL_COLOR);
            batch.draw(ServiceLocator.getResourceService().getAsset(textureRenderComponent.getTexturePath(), com.badlogic.gdx.graphics.Texture.class),
                    position.x, position.y, scale.x, scale.y);
        } else {
            System.out.println("Error: Unable to remove outline. No TextureRenderComponent found.");
        }
    }

    /**
     * Updates the rendering to show the outline if enabled.
     */
    public void render(SpriteBatch batch) {
        if (isOutlined) {
            addOutline(batch);  // Apply the outline effect
        } else {
            removeOutline(batch);  // Reset to normal rendering
        }
    }

    /**
     * Set the outline state.
     */
    public void setOutlined(boolean outlined) {
        this.isOutlined = outlined;
    }

    /**
     * Getter for the isOutlined variable to be used in tests.
     */
    public boolean isOutlined() {
        return this.isOutlined;
    }
}
