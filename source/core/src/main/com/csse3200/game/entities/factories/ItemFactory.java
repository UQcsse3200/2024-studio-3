package com.csse3200.game.entities.factories;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.components.items.*;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.services.ServiceLocator;

import java.util.List;

public class ItemFactory {
    private ItemFactory() {
        // No instantiation allowed
    }
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
        return new Entity()
                .addComponent(new IngredientComponent("fish", ItemType.FISH, 2, 10,
                        0, cookedLevel))
                .addComponent(new CookIngredientComponent());
    }

    /**
     * Create a beef item.
     * 
     * @param cookedLevel - The level the beef is cooked at, can be "raw", "cooked" or "burnt".
     * @return - A beef entity.
     */
    public static Entity createBeef(String cookedLevel) {
        return new Entity()
                .addComponent(new IngredientComponent("beef", ItemType.BEEF, 2, 10,
                        0, cookedLevel))
                .addComponent(new CookIngredientComponent());
    }

    /**
     * Create a banana item.
     * 
     * @param chopLevel - The level the banana is chopped at, can be "raw", "chopped".
     * @return - A banana entity.
     */
    public static Entity createBanana(String chopLevel) {

        return new Entity()
                .addComponent(new IngredientComponent("banana", ItemType.BANANA, 1, 3,
                        10, chopLevel))
                .addComponent(new ChopIngredientComponent());
    }

    /**
     * Create a cucumber item.
     * 
     * @param chopLevel - The level the cucumber is chopped at, can be "raw", "chopped".
     * @return - A cucumber entity.
     */
    public static Entity createCucumber(String chopLevel) {
        return new Entity()
                .addComponent(new IngredientComponent("cucumber", ItemType.CUCUMBER, 1, 5,
                        10, chopLevel))
                .addComponent(new ChopIngredientComponent());
    }

    /**
     * Create a tomato item.
     * 
     * @param chopLevel - The level the tomato is chopped at, can be "raw", "chopped".
     * @return - A tomato entity.
     */
    public static Entity createTomato(String chopLevel) {
        return new Entity()
                .addComponent(new IngredientComponent("tomato", ItemType.TOMATO, 1, 6,
                        10, chopLevel))
                .addComponent(new ChopIngredientComponent());
    }

    /**
     * Create a strawberry item.
     * 
     * @param chopLevel - The level the strawberry is chopped at, can be "raw", "chopped".
     * @return - A strawberry entity.
     */
    public static Entity createStrawberry(String chopLevel) {
        return new Entity()//createTemplateItem()
                .addComponent(new IngredientComponent("strawberry", ItemType.STRAWBERRY, 1, 3,
                        10, chopLevel))
                .addComponent(new ChopIngredientComponent());
    }

    /**
     * Create a lettuce item.
     * 
     * @param chopLevel - The level the lettuce is chopped at, can be "raw", "chopped".
     * @return - A lettuce entity.
     */
    public static Entity createLettuce(String chopLevel) {
        return new Entity()
                .addComponent(new IngredientComponent("lettuce", ItemType.LETTUCE, 1, 10, 10, chopLevel))
                .addComponent(new ChopIngredientComponent());
    }

    /**
     * Create a chocolate item.
     * 
     * @param chopLevel - The level the chocolate is chopped at, can be "raw", "chopped".
     * @return - A chocolate entity.
     */
    public static Entity createChocolate(String chopLevel) {
        return new Entity()
                .addComponent(new IngredientComponent("chocolate", ItemType.CHOCOLATE, 1, 10, 10, chopLevel))
                .addComponent(new ChopIngredientComponent());
    }

    /**
     * Create an Açaí item.
     * 
     * @param chopLevel - The level the Açaí is chopped at, can be "raw", "chopped".
     * @return - An Açaí entity.
     */
    public static Entity createAcai(String chopLevel) {
        return new Entity()
                .addComponent(new IngredientComponent("acai", ItemType.ACAI, 1, 10, 10, chopLevel))
                .addComponent(new ChopIngredientComponent());
    }

    /**
     * Create a fruit salad meal item.
     * 
     * @return - A fruitSalad entity.
     */
    public static Entity createFruitSalad(List<IngredientComponent> ingredients) {
        return new Entity()
                .addComponent(new MealComponent("fruit salad", ItemType.FRUITSALAD, 2, ingredients, 5));
    }

    /**
     * Create an Açaí bowl meal item.
     * 
     * @return - An Açaí bowl entity.
     */
    public static Entity createAcaiBowl(List<IngredientComponent> ingredients) {
        return new Entity()
                .addComponent(new MealComponent("acai bowl", ItemType.ACAIBOWL, 2, ingredients, 5));
    }

    /**
     * Create a salad meal item.
     * 
     * @return - An salad entity.
     */
    public static Entity createSalad(List<IngredientComponent> ingredients) {
        return new Entity()
                .addComponent(new MealComponent("salad", ItemType.SALAD, 3, ingredients, 10));
    }

    /**
     * Create a steak meal item.
     * 
     * @return - A steak meal entity.
     */
    public static Entity createSteakMeal(List<IngredientComponent> ingredients) {
        return new Entity()
                .addComponent(new MealComponent("steak meal", ItemType.STEAKMEAL, 3, ingredients, 10));
    }


    /**
     * Create a banana split meal item.
     * 
     * @return - A banana split entity.
     */
    public static Entity createBananaSplit(List<IngredientComponent> ingredients) {
        return new Entity()
                .addComponent(new MealComponent("banana split", ItemType.BANANASPLIT, 3, ingredients, 10));
    }

    /**
     * Creates the specified base item if valid.
     * 
     * @param itemName - the name of the item to be created.
     * @return - the specified item entity, null if invalid.
     */
    public static Entity createBaseItem(String itemName) {
        Entity entity = switch (itemName) {
                case "fish" -> ItemFactory.createFish("raw");
                case "beef" -> ItemFactory.createBeef("raw");
                case "banana" -> ItemFactory.createBanana("raw");
                case "cucumber" -> ItemFactory.createCucumber("raw");
                case "tomato" -> ItemFactory.createTomato("raw");
                case "strawberry" -> ItemFactory.createStrawberry("raw");
                case "lettuce" -> ItemFactory.createLettuce("raw");
                case "chocolate" -> ItemFactory.createChocolate("raw");
                case "acai" -> ItemFactory.createAcai("raw");
                default -> null;
        };

        if (entity == null) {
                return null;
        }

        // Register the entity
        ServiceLocator.getEntityService().register(entity);
        return entity;
    }

    /**
     * Creates the specified meal item with the given ingredients if valid.
     * 
     * @param recipeName - the type of meal to be created.
     * @param ingredients - the ingredients to be included in the meal.
     * @return - the specified meal entity, null if invalid.
     */
    public static Entity createMeal(String recipeName, List<IngredientComponent> ingredients) {
        Entity entity = switch(recipeName) {
                case "fruitSalad" -> ItemFactory.createFruitSalad(ingredients);
                case "acaiBowl" -> ItemFactory.createAcaiBowl(ingredients);
                case "salad" ->  ItemFactory.createSalad(ingredients);
                case "steakMeal" -> ItemFactory.createSteakMeal(ingredients);
                case "bananaSplit" ->ItemFactory.createBananaSplit(ingredients);
                default -> null;
        };

        if (entity == null) {
                return null;
        }

        // Unregister the ingredients as they have no need to be entities and
        // receive updates anymore
        for (IngredientComponent ingredient : ingredients) {
                ServiceLocator.getEntityService().unregister(ingredient.getEntity());
        }

        // Register the entity
        ServiceLocator.getEntityService().register(entity);
        return entity;
    }

    /**
     * Create a plate item.
     * 
     * @return - A Plate item entity.
     */
    public static Entity createPlate() {
        return new Entity()
                .addComponent(new PlateComponent(0));
    }
}