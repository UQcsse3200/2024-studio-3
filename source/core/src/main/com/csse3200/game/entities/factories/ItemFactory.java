package com.csse3200.game.entities.factories;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.components.items.*;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ServiceLocator;

public class ItemFactory {
    public static Entity createTemplateItem(){
        return new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER));
    }

    /**
     * Create a fish item.
     * @param cookedLevel - The level the fish is cooked at, can be "raw", "cooked" or "burnt".
     * @return A fish entity.
     */
    public static Entity createFish(String cookedLevel) {
        Entity fish = createTemplateItem()
                .addComponent(new IngredientComponent("Fish",ItemType.RAWFISH, 2, 10, cookedLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_fish.png", cookedLevel)));
        PhysicsUtils.setScaledCollider(fish, 0.6f, 0.3f);
        fish.getComponent(ColliderComponent.class).setDensity(1.5f);
        fish.getComponent(TextureRenderComponent.class).scaleEntity();
        return fish;
    }
}