package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ServiceLocator;

/**
 * A factory class for creating entities used in cutscenes, such as backgrounds and animations.
 */
public class CutsceneFactory {

    /**
     * Creates a background entity with the specified image path. The background
     * will be scaled to fit the screen and centered on the screen.
     *
     * @param bgImgPath The file path of the background image to use.
     * @return A new background entity.
     */
    public static Entity createBackground(String bgImgPath) {
        Entity background = new Entity();

        // Create and add a texture component to the entity for rendering the background
        TextureRenderComponent textureComponent = new BackgroundRenderComponent(bgImgPath);
        background.addComponent(textureComponent);
        textureComponent.scaleEntity();  // Scale the entity based on the texture size

        // Calculate the aspect ratio of the image
        float aspectRatio = textureComponent.getWidth() / textureComponent.getHeight();

        // Calculate the scale factor needed to fit the screen height-wise, applying a scaling factor of 2.7
        float screenToHeight = Gdx.graphics.getHeight() / textureComponent.getHeight() * 2.7f;

        // Set the entity's scale to maintain the image's aspect ratio and fit the screen height-wise
        background.setScale(screenToHeight * aspectRatio, screenToHeight);

        // Center the background entity on the screen
        float y_pos = -screenToHeight / 2;
        float x_pos = -(screenToHeight * aspectRatio) / 2;
        background.setPosition(new Vector2(x_pos, y_pos));

        return background;
    }

    /**
     * Creates an animation entity with the specified image path. The entity
     * will have a texture component for rendering the animation.
     * Creates an animation called "idle" which is the only possible
     *
     * @param animationImgPath The file path of the animation image to use.
     * @param animName The name of the animation that will be played.
     * @return A new animation entity.
     */
    public static Entity createAnimation(String animationImgPath, String animName) {
        Entity animation = new Entity();

        // Create and add a texture component to the entity for rendering the animation
        AnimationRenderComponent animator = new AnimationRenderComponent(
                ServiceLocator.getResourceService()
                        .getAsset(animationImgPath, TextureAtlas.class));
        animator.addAnimation(animName, 0.3f, Animation.PlayMode.LOOP);

        // Scale the entity based on the texture size
        animator.scaleEntity();

        return animation;
    }

    /**
     * Creates an image entity with the specified image path. The entity
     * will have a texture component for rendering the image.
     *
     * @param imgPath The file path of the animation image to use.
     * @return A new animation entity.
     */
    public static Entity createImage(String imgPath, float imageScale) {
        Entity animation = new Entity();

        // Create and add a texture component to the entity for rendering the animation
        TextureRenderComponent textureComponent = new TextureRenderComponent(imgPath);
        animation.addComponent(textureComponent);

        // Scale the entity based on the texture size
        textureComponent.scaleEntity();
        animation.setScale(animation.getScale().x * imageScale, animation.getScale().y * imageScale);

        return animation;
    }
}
