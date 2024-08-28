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
    public static Entity createTemplateItem() {
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
                .addComponent(new IngredientComponent("Fish", ItemType.FISH, 2, 10,
                        0, cookedLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_fish.png", cookedLevel)));
        /**
         * Added this to allow the fish to change texture while cooking.
         */
        fish.addComponent(new CookIngredientComponent());
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
                .addComponent(new IngredientComponent("Beef", ItemType.BEEF, 2, 10,
                        0, cookedLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_beef.png", cookedLevel)));
        beef.addComponent(new CookIngredientComponent());
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
                .addComponent(new IngredientComponent("Banana", ItemType.BANANA, 1, 3,
                        10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_banana.png", chopLevel)));
        PhysicsUtils.setScaledCollider(banana, 0.6f, 0.3f);
        banana.getComponent(ColliderComponent.class).setDensity(1.5f);
        banana.getComponent(TextureRenderComponent.class).scaleEntity();
        return banana;
    }

    /**
     * Create a cucumber item.
     * @param chopLevel - The level the cucumber is chopped at, can be "raw", "chopped".
     * @return A cucumber entity.
     */
    public static Entity createCucumber(String chopLevel) {
        Entity cucumber = createTemplateItem()
                .addComponent(new IngredientComponent("Cucumber", ItemType.CUCUMBER, 1, 5,
                        10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_cucumber.png", chopLevel)));
        PhysicsUtils.setScaledCollider(cucumber, 0.6f, 0.3f);
        cucumber.getComponent(ColliderComponent.class).setDensity(1.5f);
        cucumber.getComponent(TextureRenderComponent.class).scaleEntity();
        return cucumber;
    }

    /**
     * Create a tomato item.
     * @param chopLevel - The level the tomato is chopped at, can be "raw", "chopped".
     * @return A tomato entity.
     */
    public static Entity createTomato(String chopLevel) {
        Entity tomato = createTemplateItem()
                .addComponent(new IngredientComponent("Tomato", ItemType.TOMATO, 1, 6,
                        10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_tomato.png", chopLevel)));
        PhysicsUtils.setScaledCollider(tomato, 0.6f, 0.3f);
        tomato.getComponent(ColliderComponent.class).setDensity(1.5f);
        tomato.getComponent(TextureRenderComponent.class).scaleEntity();
        return tomato;
    }

    /**
     * Create a strawberry item.
     * @param chopLevel - The level the strawberry is chopped at, can be "raw", "chopped".
     * @return A strawberry entity.
     */
    public static Entity createStrawberry(String chopLevel) {
        Entity strawberry = createTemplateItem()
                .addComponent(new IngredientComponent("Strawberry", ItemType.STRAWBERRY, 1, 3,
                        10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_strawberry.png", chopLevel)));
        PhysicsUtils.setScaledCollider(strawberry, 0.6f, 0.3f);
        strawberry.getComponent(ColliderComponent.class).setDensity(1.5f);
        strawberry.getComponent(TextureRenderComponent.class).scaleEntity();
        return strawberry;
    }

    /**
     * Create a lettuce item.
     * @param chopLevel - The level the lettuce is chopped at, can be "raw", "chopped".
     * @return A lettuce entity.
     */
    public static Entity createLettuce(String chopLevel) {
        Entity lettuce = createTemplateItem()
                .addComponent(new IngredientComponent("Lettuce", ItemType.LETTUCE, 1, 10, 10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_lettuce.png", chopLevel)));
        PhysicsUtils.setScaledCollider(lettuce, 0.6f, 0.3f);
        lettuce.getComponent(ColliderComponent.class).setDensity(1.5f);
        lettuce.getComponent(TextureRenderComponent.class).scaleEntity();
        return lettuce;
    }

    /**
     * Create a chocolate item.
     * @param chopLevel - The level the chocolate is chopped at, can be "raw", "chopped".
     * @return A chocolate entity.
     */
    public static Entity createChocolate(String chopLevel) {
        Entity chocolate = createTemplateItem()
                .addComponent(new IngredientComponent("Chocolate", ItemType.CHOCOLATE, 1, 10, 10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_chocolate.png", chopLevel)));
        PhysicsUtils.setScaledCollider(chocolate, 0.6f, 0.3f);
        chocolate.getComponent(ColliderComponent.class).setDensity(1.5f);
        chocolate.getComponent(TextureRenderComponent.class).scaleEntity();
        return chocolate;
    }

    /**
     * Create an Açaí item.
     * @param chopLevel - The level the Açaí is chopped at, can be "raw", "chopped".
     * @return An Açaí entity.
     */
    public static Entity createAcai(String chopLevel) {
        Entity acai = createTemplateItem()
                .addComponent(new IngredientComponent("Acai", ItemType.ACAI, 1, 10, 10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/%s_acai.png", chopLevel)));
        PhysicsUtils.setScaledCollider(acai, 0.6f, 0.3f);
        acai.getComponent(ColliderComponent.class).setDensity(1.5f);
        acai.getComponent(TextureRenderComponent.class).scaleEntity();
        return acai;
    }

    /**
     * Create a fruit salad meal item.
     * @return A fruitSalad entity.
     */
    public static Entity createFruitSalad() {
        List<IngredientComponent> ingredients = Arrays.asList(new IngredientComponent("Banana", ItemType.BANANA, 1, 3, 10,"chopped"),
                                                            new IngredientComponent("Strawberry", ItemType.STRAWBERRY, 1, 3, 10, "chopped"));

        Entity fruitSalad = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/fruit_salad.png"))
                .addComponent(new MealComponent("Fruit Salad", ItemType.FRUITSALAD, 2, ingredients, 5));
        PhysicsUtils.setScaledCollider(fruitSalad, 0.6f, 0.3f);
        fruitSalad.getComponent(ColliderComponent.class).setDensity(1.5f);
        fruitSalad.getComponent(TextureRenderComponent.class).scaleEntity();
        return fruitSalad;
    }

    /**
     * Create an Açaí bowl meal item.
     * @return An Açaí bowl entity.
     */
    public static Entity createAcaiBowl() {
        List<IngredientComponent> ingredients = Arrays.asList(new IngredientComponent("Banana", ItemType.BANANA, 1, 3, 10,"chopped"),
                new IngredientComponent("Acai", ItemType.ACAI, 1, 3, 10, "chopped"));

        Entity acaiBowl = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/acai_bowl.png"))
                .addComponent(new MealComponent("Acai Bowl", ItemType.ACAIBOWL, 2, ingredients, 5));
        PhysicsUtils.setScaledCollider(acaiBowl, 0.6f, 0.3f);
        acaiBowl.getComponent(ColliderComponent.class).setDensity(1.5f);
        acaiBowl.getComponent(TextureRenderComponent.class).scaleEntity();
        return acaiBowl;
    }

    /**
     * Create a salad meal item.
     * @return An salad entity.
     */
    public static Entity createSalad() {
        List<IngredientComponent> ingredients = Arrays.asList(new IngredientComponent("Tomato", ItemType.TOMATO, 1, 3, 10,"chopped"),
                new IngredientComponent("Lettuce", ItemType.LETTUCE, 1, 3, 10,"chopped"),
                new IngredientComponent("Cucumber", ItemType.CUCUMBER, 1, 3, 10, "chopped"));

        Entity salad = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/salad.png"))
                .addComponent(new MealComponent("Salad", ItemType.SALAD, 3, ingredients, 10));
        PhysicsUtils.setScaledCollider(salad, 0.6f, 0.3f);
        salad.getComponent(ColliderComponent.class).setDensity(1.5f);
        salad.getComponent(TextureRenderComponent.class).scaleEntity();
        return salad;
    }

    /**
     * Create a steak meal item.
     * @return A steak meal entity.
     */
    public static Entity createSteakMeal() {
        List<IngredientComponent> ingredients = Arrays.asList(new IngredientComponent("Tomato", ItemType.TOMATO, 1, 3, 10,"chopped"),
                new IngredientComponent("Beef", ItemType.BEEF, 1, 3, 10,"cooked"),
                new IngredientComponent("Cucumber", ItemType.CUCUMBER, 1, 3, 10, "chopped"));

        Entity steakMeal = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/steak_meal.png"))
                .addComponent(new MealComponent("Steak Meal", ItemType.STEAKMEAL, 3, ingredients, 10));
        PhysicsUtils.setScaledCollider(steakMeal, 0.6f, 0.3f);
        steakMeal.getComponent(ColliderComponent.class).setDensity(1.5f);
        steakMeal.getComponent(TextureRenderComponent.class).scaleEntity();
        return steakMeal;
    }


    /**
     * Create a banana split meal item.
     * @return A banana split entity.
     */
    public static Entity createBananaSplit() {
        List<IngredientComponent> ingredients = Arrays.asList(new IngredientComponent("Banana", ItemType.BANANA, 1, 3, 10,"chopped"),
                new IngredientComponent("Chocolate", ItemType.CHOCOLATE, 1, 3, 10,"chopped"),
                new IngredientComponent("Strawberry", ItemType.STRAWBERRY, 1, 3, 10, "chopped"));

        Entity bananaSplit = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/banana_split.png"))
                .addComponent(new MealComponent("Banana Split", ItemType.BANANASPLIT, 3, ingredients, 10));
        PhysicsUtils.setScaledCollider(bananaSplit, 0.6f, 0.3f);
        bananaSplit.getComponent(ColliderComponent.class).setDensity(1.5f);
        bananaSplit.getComponent(TextureRenderComponent.class).scaleEntity();
        return bananaSplit;
    }


}