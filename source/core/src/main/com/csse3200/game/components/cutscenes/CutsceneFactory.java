package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.station.StationServingComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
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
        TextureRenderComponent textureComponent = new TextureRenderComponent(bgImgPath);
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
     *
     * @param animationImgPath The file path of the animation image to use.
     * @return A new animation entity.
     */
    public static Entity createAnimation(String animationImgPath) {
        Entity animation = new Entity();

        // Create and add a texture component to the entity for rendering the animation
        TextureRenderComponent textureComponent = new TextureRenderComponent(animationImgPath);
        animation.addComponent(textureComponent);

        // Scale the entity based on the texture size
        textureComponent.scaleEntity();

        return animation;
    }

    /**
     * Creates an animation entity with the specified atlas path.
     *
     * @param animationAtlasPath The file path of the atlas file to use.
     * @return A new animation entity.
     */
    public static Entity createProperAnimation(String animationAtlasPath) {
        Entity animation = new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
                .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
                .addComponent(new TooltipsDisplay())
                .addComponent(new InventoryComponent(1))
                .addComponent(new StationServingComponent());

        animation.getComponent(InteractionComponent.class).setAsBox(animation.getScale());
        animation.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
        PhysicsUtils.setScaledCollider(animation, 1f, 1f);


        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService().getAsset(animationAtlasPath, TextureAtlas.class)); // images/stations/Servery_Animation/servery.atlas
        animator.addAnimation("bad_end", 0.1f, Animation.PlayMode.LOOP);

        animation.addComponent(animator);

        /**
         TextureRenderComponent textureComponent = new TextureRenderComponent(animationImgPath);
         animation.addComponent(textureComponent);

         // Scale the entity based on the texture size
         textureComponent.scaleEntity();
         */

        return animation;
    }
}
