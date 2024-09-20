package com.csse3200.game.entities.factories;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.components.items.*;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;
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
     * 
     * @param cookedLevel - The level the fish is cooked at, can be "raw", "cooked" or "burnt".
     * @return - A fish entity.
     */
    public static Entity createFish(String cookedLevel) {
        Entity fish = createTemplateItem()
                .addComponent(new IngredientComponent("fish", ItemType.FISH, 2, 10,
                        0, cookedLevel))
                .addComponent(new TextureRenderComponent(String.format("images/ingredients/%s_fish.png", cookedLevel)));
        /**
         * Added this to allow the fish to change texture while cooking.
         */
        fish.addComponent(new CookIngredientComponent());
        PhysicsUtils.setScaledCollider(fish, 0.6f, 0.3f);
        fish.getComponent(ColliderComponent.class).setDensity(1.5f);
        return fish;
    }

    /**
     * Create a beef item.
     * 
     * @param cookedLevel - The level the beef is cooked at, can be "raw", "cooked" or "burnt".
     * @return - A beef entity.
     */
    public static Entity createBeef(String cookedLevel) {
        Entity beef = createTemplateItem()
                .addComponent(new IngredientComponent("beef", ItemType.BEEF, 2, 10,
                        0, cookedLevel))
                .addComponent(new TextureRenderComponent(String.format("images/ingredients/%s_beef.png", cookedLevel)));
        beef.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
        beef.addComponent(new CookIngredientComponent());
        PhysicsUtils.setScaledCollider(beef, 0.6f, 0.3f);
        beef.getComponent(ColliderComponent.class).setDensity(1.5f);
        return beef;
    }

    /**
     * Create a banana item.
     * 
     * @param chopLevel - The level the banana is chopped at, can be "raw", "chopped".
     * @return - A banana entity.
     */
    public static Entity createBanana(String chopLevel) {
        Entity banana = createTemplateItem()
                .addComponent(new IngredientComponent("banana", ItemType.BANANA, 1, 3,
                        10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/ingredients/%s_banana.png", chopLevel)));
        PhysicsUtils.setScaledCollider(banana, 0.6f, 0.3f);
        banana.getComponent(ColliderComponent.class).setDensity(1.5f);
        return banana;
    }

    /**
     * Create a cucumber item.
     * 
     * @param chopLevel - The level the cucumber is chopped at, can be "raw", "chopped".
     * @return - A cucumber entity.
     */
    public static Entity createCucumber(String chopLevel) {
        Entity cucumber = createTemplateItem()
                .addComponent(new IngredientComponent("cucumber", ItemType.CUCUMBER, 1, 5,
                        10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/ingredients/%s_cucumber.png", chopLevel)));
        PhysicsUtils.setScaledCollider(cucumber, 0.6f, 0.3f);
        cucumber.getComponent(ColliderComponent.class).setDensity(1.5f);
        return cucumber;
    }

    /**
     * Create a tomato item.
     * 
     * @param chopLevel - The level the tomato is chopped at, can be "raw", "chopped".
     * @return - A tomato entity.
     */
    public static Entity createTomato(String chopLevel) {
        Entity tomato = createTemplateItem()
                .addComponent(new IngredientComponent("tomato", ItemType.TOMATO, 1, 6,
                        10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/ingredients/%s_tomato.png", chopLevel)));
        PhysicsUtils.setScaledCollider(tomato, 0.6f, 0.3f);
        tomato.getComponent(ColliderComponent.class).setDensity(1.5f);
        return tomato;
    }

    /**
     * Create a strawberry item.
     * 
     * @param chopLevel - The level the strawberry is chopped at, can be "raw", "chopped".
     * @return - A strawberry entity.
     */
    public static Entity createStrawberry(String chopLevel) {
        Entity strawberry = createTemplateItem()
                .addComponent(new IngredientComponent("strawberry", ItemType.STRAWBERRY, 1, 3,
                        10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/ingredients/%s_strawberry.png", chopLevel)));

        strawberry.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
        PhysicsUtils.setScaledCollider(strawberry, 0.6f, 0.3f);
        strawberry.getComponent(ColliderComponent.class).setDensity(1.5f);
        return strawberry;
    }

    /**
     * Create a lettuce item.
     * 
     * @param chopLevel - The level the lettuce is chopped at, can be "raw", "chopped".
     * @return - A lettuce entity.
     */
    public static Entity createLettuce(String chopLevel) {
        Entity lettuce = createTemplateItem()
                .addComponent(new IngredientComponent("lettuce", ItemType.LETTUCE, 1, 10, 10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/ingredients/%s_lettuce.png", chopLevel)));
        lettuce.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
        PhysicsUtils.setScaledCollider(lettuce, 0.6f, 0.3f);
        lettuce.getComponent(ColliderComponent.class).setDensity(1.5f);
        return lettuce;
    }

    /**
     * Create a chocolate item.
     * 
     * @param chopLevel - The level the chocolate is chopped at, can be "raw", "chopped".
     * @return - A chocolate entity.
     */
    public static Entity createChocolate(String chopLevel) {
        Entity chocolate = createTemplateItem()
                .addComponent(new IngredientComponent("chocolate", ItemType.CHOCOLATE, 1, 10, 10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/ingredients/%s_chocolate.png", chopLevel)));
        PhysicsUtils.setScaledCollider(chocolate, 0.6f, 0.3f);
        chocolate.getComponent(ColliderComponent.class).setDensity(1.5f);
        return chocolate;
    }

    /**
     * Create an Açaí item.
     * 
     * @param chopLevel - The level the Açaí is chopped at, can be "raw", "chopped".
     * @return - An Açaí entity.
     */
    public static Entity createAcai(String chopLevel) {
        Entity acai = createTemplateItem()
                .addComponent(new IngredientComponent("acai", ItemType.ACAI, 1, 10, 10, chopLevel))
                .addComponent(new TextureRenderComponent(String.format("images/ingredients/%s_acai.png", chopLevel)));
        PhysicsUtils.setScaledCollider(acai, 0.6f, 0.3f);
        acai.getComponent(ColliderComponent.class).setDensity(1.5f);
        return acai;
    }

    /**
     * Create a fruit salad meal item.
     * 
     * @return - A fruitSalad entity.
     */
    public static Entity createFruitSalad(List<IngredientComponent> ingredients) {
        Entity fruitSalad = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/meals/fruit_salad.png"))
                .addComponent(new MealComponent("Fruit Salad", ItemType.FRUITSALAD, 2, ingredients, 5));
        PhysicsUtils.setScaledCollider(fruitSalad, 0.6f, 0.3f);
        fruitSalad.getComponent(ColliderComponent.class).setDensity(1.5f);
        return fruitSalad;
    }

    /**
     * Create an Açaí bowl meal item.
     * 
     * @return - An Açaí bowl entity.
     */
    public static Entity createAcaiBowl(List<IngredientComponent> ingredients) {
        Entity acaiBowl = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/meals/acai_bowl.png"))
                .addComponent(new MealComponent("Acai Bowl", ItemType.ACAIBOWL, 2, ingredients, 5));
        PhysicsUtils.setScaledCollider(acaiBowl, 0.6f, 0.3f);
        acaiBowl.getComponent(ColliderComponent.class).setDensity(1.5f);
        return acaiBowl;
    }

    /**
     * Create a salad meal item.
     * 
     * @return - An salad entity.
     */
    public static Entity createSalad(List<IngredientComponent> ingredients) {
        Entity salad = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/meals/salad.png"))
                .addComponent(new MealComponent("Salad", ItemType.SALAD, 3, ingredients, 10));
        PhysicsUtils.setScaledCollider(salad, 0.6f, 0.3f);
        salad.getComponent(ColliderComponent.class).setDensity(1.5f);
        return salad;
    }

    /**
     * Create a steak meal item.
     * 
     * @return - A steak meal entity.
     */
    public static Entity createSteakMeal(List<IngredientComponent> ingredients) {
        Entity steakMeal = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/meals/steak_meal.png"))
                .addComponent(new MealComponent("Steak Meal", ItemType.STEAKMEAL, 3, ingredients, 10));

        PhysicsUtils.setScaledCollider(steakMeal, 0.6f, 0.3f);
        steakMeal.getComponent(ColliderComponent.class).setDensity(1.5f);
        return steakMeal;
    }


    /**
     * Create a banana split meal item.
     * 
     * @return - A banana split entity.
     */
    public static Entity createBananaSplit(List<IngredientComponent> ingredients) {
        Entity bananaSplit = createTemplateItem()
                .addComponent(new TextureRenderComponent("images/meals/banana_split.png"))
                .addComponent(new MealComponent("Banana Split", ItemType.BANANASPLIT, 3, ingredients, 10));
        PhysicsUtils.setScaledCollider(bananaSplit, 0.6f, 0.3f);
        bananaSplit.getComponent(ColliderComponent.class).setDensity(1.5f);
        return bananaSplit;
    }

    /**
     * Creates the specified base item if valid.
     * 
     * @param itemName - the name of the item to be created.
     * @return - the specified item entity, null if invalid.
     */
    public static Entity createBaseItem(String itemName) {

        switch (itemName) {
                case "fish":
                        return ItemFactory.createFish("raw");
                case "beef":
                        return ItemFactory.createBeef("raw");
                case "banana":
                        return ItemFactory.createBanana("raw");
                case "cucumber":
                        return ItemFactory.createCucumber("raw");
                case "tomato":
                        return ItemFactory.createTomato("raw");
                case "strawberry":
                        return ItemFactory.createStrawberry("raw");
                case "lettuce":
                        return ItemFactory.createLettuce("raw");
                case "chocolate":
                        return ItemFactory.createChocolate("raw");
                case "acai":
                        return ItemFactory.createAcai("raw");
        }

        // Here to supress warnings about return values
        return null;
    }

    /**
     * Creates the specified meal item with the given ingredients if valid.
     * 
     * @param recipeName - the type of meal to be created.
     * @param ingredients - the ingredients to be included in the meal.
     * @return - the specified meal entity, null if invalid.
     */
    public static Entity createMeal(String recipeName, List<IngredientComponent> ingredients) {
        switch (recipeName) {
                case "fruitSalad":
                        return ItemFactory.createFruitSalad(ingredients);
                case "acaiBowl":
                        return ItemFactory.createAcaiBowl(ingredients);
                case "salad":
                        return ItemFactory.createSalad(ingredients);
                case "steakMeal":
                        return ItemFactory.createSteakMeal(ingredients);
                case "bananaSplit":
                        return ItemFactory.createBananaSplit(ingredients);
        }

        return null;
    }
}