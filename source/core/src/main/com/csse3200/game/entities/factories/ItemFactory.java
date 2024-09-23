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
        Entity fish = new Entity()
                .addComponent(new IngredientComponent("fish", ItemType.FISH, 2, 10,
                        0, cookedLevel))
                .addComponent(new CookIngredientComponent());
        return fish;
    }

    /**
     * Create a beef item.
     * 
     * @param cookedLevel - The level the beef is cooked at, can be "raw", "cooked" or "burnt".
     * @return - A beef entity.
     */
    public static Entity createBeef(String cookedLevel) {
        Entity beef = new Entity()
                .addComponent(new IngredientComponent("beef", ItemType.BEEF, 2, 10,
                        0, cookedLevel))
                .addComponent(new CookIngredientComponent());
        return beef;
    }

    /**
     * Create a banana item.
     * 
     * @param chopLevel - The level the banana is chopped at, can be "raw", "chopped".
     * @return - A banana entity.
     */
    public static Entity createBanana(String chopLevel) {
        Entity banana = new Entity()
                .addComponent(new IngredientComponent("banana", ItemType.BANANA, 1, 3,
                        10, chopLevel))
                .addComponent(new CookIngredientComponent());

        return banana;
    }

    /**
     * Create a cucumber item.
     * 
     * @param chopLevel - The level the cucumber is chopped at, can be "raw", "chopped".
     * @return - A cucumber entity.
     */
    public static Entity createCucumber(String chopLevel) {
        Entity cucumber = new Entity()
                .addComponent(new IngredientComponent("cucumber", ItemType.CUCUMBER, 1, 5,
                        10, chopLevel))
                .addComponent(new ChopIngredientComponent());
        return cucumber;
    }

    /**
     * Create a tomato item.
     * 
     * @param chopLevel - The level the tomato is chopped at, can be "raw", "chopped".
     * @return - A tomato entity.
     */
    public static Entity createTomato(String chopLevel) {
        Entity tomato = new Entity()
                .addComponent(new IngredientComponent("tomato", ItemType.TOMATO, 1, 6,
                        10, chopLevel))
                .addComponent(new ChopIngredientComponent());
        return tomato;
    }

    /**
     * Create a strawberry item.
     * 
     * @param chopLevel - The level the strawberry is chopped at, can be "raw", "chopped".
     * @return - A strawberry entity.
     */
    public static Entity createStrawberry(String chopLevel) {
        Entity strawberry = new Entity()//createTemplateItem()
                .addComponent(new IngredientComponent("strawberry", ItemType.STRAWBERRY, 1, 3,
                        10, chopLevel))
                .addComponent(new ChopIngredientComponent());
        return strawberry;
    }

    /**
     * Create a lettuce item.
     * 
     * @param chopLevel - The level the lettuce is chopped at, can be "raw", "chopped".
     * @return - A lettuce entity.
     */
    public static Entity createLettuce(String chopLevel) {
        Entity lettuce = new Entity()
                .addComponent(new IngredientComponent("lettuce", ItemType.LETTUCE, 1, 10, 10, chopLevel))
                .addComponent(new ChopIngredientComponent());
        return lettuce;
    }

    /**
     * Create a chocolate item.
     * 
     * @param chopLevel - The level the chocolate is chopped at, can be "raw", "chopped".
     * @return - A chocolate entity.
     */
    public static Entity createChocolate(String chopLevel) {
        Entity chocolate = new Entity()
                .addComponent(new IngredientComponent("chocolate", ItemType.CHOCOLATE, 1, 10, 10, chopLevel))
                .addComponent(new ChopIngredientComponent());
        return chocolate;
    }

    /**
     * Create an Açaí item.
     * 
     * @param chopLevel - The level the Açaí is chopped at, can be "raw", "chopped".
     * @return - An Açaí entity.
     */
    public static Entity createAcai(String chopLevel) {
        Entity acai = new Entity()
                .addComponent(new IngredientComponent("acai", ItemType.ACAI, 1, 10, 10, chopLevel))
                .addComponent(new ChopIngredientComponent());
        return acai;
    }

    /**
     * Create a fruit salad meal item.
     * 
     * @return - A fruitSalad entity.
     */
    public static Entity createFruitSalad(List<IngredientComponent> ingredients) {
        Entity fruitSalad = new Entity()
                .addComponent(new MealComponent("fruit salad", ItemType.FRUITSALAD, 2, ingredients, 5));
        return fruitSalad;
    }

    /**
     * Create an Açaí bowl meal item.
     * 
     * @return - An Açaí bowl entity.
     */
    public static Entity createAcaiBowl(List<IngredientComponent> ingredients) {
        Entity acaiBowl = new Entity()
                .addComponent(new MealComponent("acai bowl", ItemType.ACAIBOWL, 2, ingredients, 5));
        return acaiBowl;
    }

    /**
     * Create a salad meal item.
     * 
     * @return - An salad entity.
     */
    public static Entity createSalad(List<IngredientComponent> ingredients) {
        Entity salad = new Entity()
                .addComponent(new MealComponent("Salad", ItemType.SALAD, 3, ingredients, 10));
        return salad;
    }

    /**
     * Create a steak meal item.
     * 
     * @return - A steak meal entity.
     */
    public static Entity createSteakMeal(List<IngredientComponent> ingredients) {
        Entity steakMeal = new Entity()
                .addComponent(new MealComponent("Steak Meal", ItemType.STEAKMEAL, 3, ingredients, 10));
        return steakMeal;
    }


    /**
     * Create a banana split meal item.
     * 
     * @return - A banana split entity.
     */
    public static Entity createBananaSplit(List<IngredientComponent> ingredients) {
        Entity bananaSplit = new Entity()
                .addComponent(new MealComponent("Banana Split", ItemType.BANANASPLIT, 3, ingredients, 10));
        return bananaSplit;
    }

    /**
     * Creates the specified base item if valid.
     * 
     * @param itemName - the name of the item to be created.
     * @return - the specified item entity, null if invalid.
     */
    public static Entity createBaseItem(String itemName) {

        Entity entity = null;

        switch (itemName) {
                case "fish":
                        entity = ItemFactory.createFish("raw");
                        break;
                case "beef":
                        entity = ItemFactory.createBeef("raw");
                        break;
                case "banana":
                        entity = ItemFactory.createBanana("raw");
                        break;
                case "cucumber":
                        entity = ItemFactory.createCucumber("raw");
                        break;
                case "tomato":
                        entity = ItemFactory.createTomato("raw");
                        break;
                case "strawberry":
                        entity = ItemFactory.createStrawberry("raw");
                        break;
                case "lettuce":
                        entity = ItemFactory.createLettuce("raw");
                        break;
                case "chocolate":
                        entity = ItemFactory.createChocolate("raw");
                        break;
                case "acai":
                        entity = ItemFactory.createAcai("raw");
                        break;
                default:
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
                default -> null;
        };

        if (entity == null) {
                return null;
        }
        
        // Register the entity
        ServiceLocator.getEntityService().register(entity);
        return entity;

        /*switch (recipeName) {
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
        }*/

        //return null;
    }
}