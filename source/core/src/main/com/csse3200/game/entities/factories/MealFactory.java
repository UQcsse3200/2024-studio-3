package com.csse3200.game.entities.factories;

import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.components.items.*;

import java.util.List;

import static com.csse3200.game.entities.factories.ItemFactory.createTemplateItem;

public class MealFactory {
    public static Entity createMeal(String itemName, ItemType itemType, int weight, List<IngredientComponent> ingredients,
                                    int price, String path_to_texture) {
        return createTemplateItem()
                .addComponent(new TextureRenderComponent(path_to_texture))
                .addComponent(new MealComponent(itemName, itemType, weight, ingredients, price));
    }
}

