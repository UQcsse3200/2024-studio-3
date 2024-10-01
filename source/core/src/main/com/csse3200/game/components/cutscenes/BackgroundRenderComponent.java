package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.graphics.Texture;
import com.csse3200.game.rendering.RenderComponent;
import com.csse3200.game.rendering.Renderer;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ServiceLocator;

public class BackgroundRenderComponent extends TextureRenderComponent {
    private static final int DEFAULT_LAYER = 1;
    public BackgroundRenderComponent(String texturePath) {
        super(texturePath);
    }

    @Override
    public int getLayer() {
        return DEFAULT_LAYER;
    }
}
