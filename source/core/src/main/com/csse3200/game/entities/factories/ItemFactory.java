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
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER));
    }

    /**
     * Create a fish item
     * @param cookedLevel - The level the fish is cooked at, can be "raw", "cooked" or "burnt"
     * @return A fish entity
     */
    public static Entity createFish(String cookedLevel) {
        return createTemplateItem()
                .addComponent(new ItemComponent("Fish", ItemType.RAWFISH, 2))
                .addComponent(new IngredientComponent("Fish",ItemType.RAWFISH, 2, 10))
                .addComponent(new TextureRenderComponent(String.format("images/%s_fish.png", cookedLevel)));
    }
}