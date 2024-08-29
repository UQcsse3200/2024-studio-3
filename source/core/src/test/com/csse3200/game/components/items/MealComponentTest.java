package com.csse3200.game.components.items;

import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class MealComponentTest {

    private IngredientComponent ingredient1;
    private IngredientComponent ingredient2;
    private IngredientComponent ingredient3;
    private IngredientComponent ingredient4;
    private IngredientComponent ingredient5;
    private IngredientComponent ingredient6;
    private IngredientComponent ingredient7;
    private IngredientComponent ingredient8;
    private IngredientComponent ingredient9;

    @BeforeEach
    void setUp() {

        ingredient1 = new IngredientComponent("Tomato", ItemType.TOMATO, 10, 6, 3,
                "raw");
        ingredient2 = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10, 5, 3,
                "cooked");
        ingredient3 = new IngredientComponent("Beef", ItemType.BEEF, 12, 10, 0,
                "burnt");
        ingredient4 = new IngredientComponent("Lettuce", ItemType.LETTUCE, 5, 0, 0,
                "raw");
        ingredient5 = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10, 5, 3,
                "chopped");
        ingredient6 = new IngredientComponent("Tomato", ItemType.TOMATO, 10, 6, 3,
                "chopped");
        ingredient7 = new IngredientComponent("Banana", ItemType.BANANA, 10, 0, 3,
                "raw");
        ingredient8 = new IngredientComponent("Strawberry", ItemType.STRAWBERRY, 10, 0,
                3, "chopped");
        ingredient9 = new IngredientComponent("Chocolate", ItemType.CHOCOLATE, 10, 0,
                3, "chopped");
    }

    @Test
    void mealComponentConstructorTest() {

        List<IngredientComponent> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);
        MealComponent meal = new MealComponent("steakMeal", ItemType.STEAKMEAL, 32, ingredients,
                20);

        assertEquals("steakMeal", meal.getItemName());
        assertEquals(ItemType.STEAKMEAL, meal.getItemType());
        assertEquals(32, meal.getWeight());
        assertEquals(ingredients, meal.getIngredients());
        assertEquals(20, meal.getPrice());
        assertEquals(33, meal.getQuality());
    }

    @Test
    void mealComponentConstructorWithLettuceTest() {
        List<IngredientComponent> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient5);
        ingredients.add(ingredient4);
        MealComponent meal = new MealComponent("salad", ItemType.SALAD, 25, ingredients,
                15);
        assertEquals("salad", meal.getItemName());
        assertEquals(ItemType.SALAD, meal.getItemType());
        assertEquals(25, meal.getWeight());
        assertEquals(ingredients, meal.getIngredients());
        assertEquals(15, meal.getPrice());
        assertEquals(67, meal.getQuality());
    }

    @Test
    void mealComponentGetQualityNoPenalty() {
        List<IngredientComponent> ingredients = new ArrayList<>();
        ingredients.add(ingredient6);
        ingredients.add(ingredient5);
        ingredients.add(ingredient4);
        MealComponent meal = new MealComponent("salad", ItemType.SALAD, 25, ingredients,
                15);
        assertEquals(100, meal.getQuality());
    }

    @Test
    void mealComponentGetQualityHalfPenalty() {
        List<IngredientComponent> ingredients = new ArrayList<>();
        ingredients.add(ingredient7);
        ingredients.add(ingredient8);
        MealComponent meal = new MealComponent("fruitSalad", ItemType.FRUITSALAD, 20, ingredients,
                15);
        assertEquals(50, meal.getQuality());
    }

    @Test
    void setIngredientsTest() {
        List<IngredientComponent> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);
        MealComponent meal = new MealComponent("steakMeal", ItemType.STEAKMEAL, 32, ingredients,
                25);
        assertEquals(33, meal.getQuality());

        List<IngredientComponent> newIngredients = new ArrayList<>();
        newIngredients.add(ingredient7);
        newIngredients.add(ingredient8);
        meal.setIngredients(newIngredients);

        assertEquals(2, meal.getIngredients().size());
        assertEquals(50, meal.getQuality());
    }

    @Test
    void addIngredientTest() {
        List<IngredientComponent> ingredients = new ArrayList<>();
        ingredients.add(ingredient9);
        ingredients.add(ingredient8);

        MealComponent meal = new MealComponent("fruitSalad", ItemType.FRUITSALAD, 20, ingredients,
                15);
        assertEquals(100, meal.getQuality());

        meal.addIngredient(ingredient7);
        assertEquals(3, meal.getIngredients().size());
        assertEquals(67, meal.getQuality());
    }

    @Test
    void deleteIngredientTest() {
        List<IngredientComponent> ingredients = new ArrayList<>();
        ingredients.add(ingredient7);
        ingredients.add(ingredient8);
        ingredients.add(ingredient9);

        MealComponent meal = new MealComponent("bananaSplit", ItemType.BANANASPLIT, 32, ingredients,
                18);
        assertEquals(67, meal.getQuality());

        meal.deleteIngredient(ingredient9);
        assertEquals(2, meal.getIngredients().size());
        assertEquals(50, meal.getQuality());
    }

    @Test
    void emptyIngredientsTest() {
        List<IngredientComponent> ingredients = new ArrayList<>();
        MealComponent meal = new MealComponent("EmptyMealTester", ItemType.STEAKMEAL, 0, ingredients,
                0);

        assertEquals(0, meal.getQuality());
    }

    @Test
    void setPriceTest() {
        List<IngredientComponent> ingredients = new ArrayList<>();
        ingredients.add(ingredient9);
        ingredients.add(ingredient8);
        MealComponent meal = new MealComponent("fruitSalad", ItemType.FRUITSALAD, 20, ingredients,
                15);

        meal.setPrice(25);
        assertEquals(25, meal.getPrice());
    }

}
