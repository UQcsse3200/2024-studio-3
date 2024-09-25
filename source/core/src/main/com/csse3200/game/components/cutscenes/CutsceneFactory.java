package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.TextureRenderComponent;

public class CutsceneFactory {
    public static Entity createBackground(String bgImgPath) {
        Entity background = new Entity();

        // Load texture dimensions
        TextureRenderComponent textureComponent = new TextureRenderComponent(bgImgPath);
        background.addComponent(textureComponent);
        textureComponent.scaleEntity();
        float aspectRatio = textureComponent.getWidth() / textureComponent.getHeight();
        float screenToHeight = Gdx.graphics.getHeight() / textureComponent.getHeight() * 2.7f;

        // Set entity size to fill the screen height wise
        background.setScale(screenToHeight * aspectRatio, screenToHeight);

        // Center the background on the screen
        float y_pos = -screenToHeight / 2;
        float x_pos = -(screenToHeight * aspectRatio) / 2;
        background.setPosition(new Vector2(x_pos, y_pos));

        return background;
    }
}
