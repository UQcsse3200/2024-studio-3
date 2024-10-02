package com.csse3200.game.components.cutscenes;

import com.csse3200.game.rendering.TextureRenderComponent;

/**
 * BackgroundRenderComponent is a specialized component for rendering background textures.
 * It extends TextureRenderComponent and sets a default layer for the background rendering.
 */
public class BackgroundRenderComponent extends TextureRenderComponent {
    // Default layer for the background, ensuring it is rendered on a specific layer
    private static final int DEFAULT_LAYER = 1;

    /**
     * Constructor that initializes the background with a texture path.
     * @param texturePath the file path of the texture to be rendered as the background.
     */
    public BackgroundRenderComponent(String texturePath) {
        super(texturePath);  // Calls the constructor of the parent class, TextureRenderComponent
    }

    /**
     * Overrides the getLayer method to return the default layer.
     * This ensures that the background is always rendered on the specified layer.
     * @return the layer on which the background should be rendered.
     */
    @Override
    public int getLayer() {
        return DEFAULT_LAYER;  // Return the default layer, which is set to 1
    }
}
