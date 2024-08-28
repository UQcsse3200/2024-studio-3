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

    @BeforeEach
    void setUp() {

        ingredient1 = new IngredientComponent("Tomato", ItemType.TOMATO, 10, 6, 3,
                "raw");
        ingredient2 = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10, 5, 3,
                "cooked");
        ingredient3 = new IngredientComponent("Beef", ItemType.BEEF, 12, 10, 0,
                "burnt");
        ingredient4 = new IngredientComponent("Lettuce", ItemType.LETTUCE, 5, 0, 0, "raw");
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

}
