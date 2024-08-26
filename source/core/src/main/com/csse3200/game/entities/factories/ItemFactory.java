package com.csse3200.game.entities.factories;

import java.util.Arrays;
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

import java.util.List;

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
                .addComponent(new IngredientComponent("Fish", ItemType.FISH, 2, 10, cookedLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_fish.png", cookedLevel)));
        PhysicsUtils.setScaledCollider(fish, 0.6f, 0.3f);
        fish.getComponent(ColliderComponent.class).setDensity(1.5f);
        fish.getComponent(TextureRenderComponent.class).scaleEntity();
        return fish;
    }

    /**
     * Create a beef item.
     * @param cookedLevel - The level the beef is cooked at, can be "raw", "cooked" or "burnt".
     * @return A beef entity.
     */
    public static Entity createBeef(String cookedLevel) {
        Entity beef = createTemplateItem()
                .addComponent(new IngredientComponent("Beef", ItemType.BEEF, 2, 10, cookedLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_beef.png", cookedLevel)));
        PhysicsUtils.setScaledCollider(beef, 0.6f, 0.3f);
        beef.getComponent(ColliderComponent.class).setDensity(1.5f);
        beef.getComponent(TextureRenderComponent.class).scaleEntity();
        return beef;

    }

    /**
     * Create a banana item.
     * @param chopLevel - The level the banana is chopped at, can be "raw", "chopped".
     * @return A banana entity.
     */
    public static Entity createBanana(String chopLevel) {
        Entity banana = createTemplateItem()
                .addComponent(new IngredientComponent("Banana", ItemType.BANANA, 1, 3, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_banana.png", chopLevel)));
        PhysicsUtils.setScaledCollider(banana, 0.6f, 0.3f);
        banana.getComponent(ColliderComponent.class).setDensity(1.5f);
        banana.getComponent(TextureRenderComponent.class).scaleEntity();
        return banana;

    }

    /**
     * Create a fruit salad item.
     * @param  - The level the banana is chopped at, can be "raw", "chopped".
     * @return A banana entity.
     */
    public static Entity createFruitSalad() {
        List<IngredientComponent> ingredients = Arrays.asList(new IngredientComponent("Banana", ItemType.BANANA, 1, 3, "chopped"),
                                                            new IngredientComponent("Strawberry", ItemType.STRAWBERRY, 1, 3, "chopped"));

        Entity fruitSalad = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/fruit_salad.png"))
                .addComponent(new MealComponent("Fruit Salad", ItemType.FRUITSALAD, 2, ingredients, 5));
        PhysicsUtils.setScaledCollider(fruitSalad, 0.6f, 0.3f);
        fruitSalad.getComponent(ColliderComponent.class).setDensity(1.5f);
        fruitSalad.getComponent(TextureRenderComponent.class).scaleEntity();
        return fruitSalad;
    }

}