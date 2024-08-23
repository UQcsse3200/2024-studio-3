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
import static com.csse3200.game.entities.factories.ItemFactory.createTemplateItem;

public class IngredientFactory {
    public static Entity createIngridient(String name, ItemType type, int weight,
                                          int cook_time, String url_to_texture) {
        return createTemplateItem()
                .addComponent(new TextureRenderComponent(url_to_texture))
                .addComponent(new IngredientComponent(name, type, weight, cook_time));
    }
}