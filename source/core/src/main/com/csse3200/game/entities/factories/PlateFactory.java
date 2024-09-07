package com.csse3200.game.entities.factories;

import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.items.PlateComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

public class PlateFactory {

    /**
     * Create a template plate entity with common components.
     *
     * @return A basic plate entity.
     */
    private static Entity createTemplatePlate() {
        return new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
                .addComponent(new TooltipsDisplay())
                .addComponent(new PlateComponent());
    }

    /**
     * Create a plate item.
     * The plate starts as dirty and can be cleaned before being used.
     *
     * @return A plate entity.
     */
    public static Entity createPlate() {
        Entity plate = createTemplatePlate()
                .addComponent(new PlateComponent())
                .addComponent(new TextureRenderComponent("images/platecomponent/cleanplate.png"));
        PhysicsUtils.setScaledCollider(plate, 0.6f, 0.3f);
        plate.getComponent(ColliderComponent.class).setDensity(1.0f);

        return plate;
    }
}