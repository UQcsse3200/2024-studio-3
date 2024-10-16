package com.csse3200.game.files;

import com.csse3200.game.entities.configs.CookingConfig;
import com.csse3200.game.entities.factories.DishFactory;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(GameExtension.class)
public class RecipeFileLoaderTest {

    public CookingConfig configs;
    @BeforeEach
    public void setUp() {
        configs = FileLoader.readClass(CookingConfig.class, "configs/recipe.json");
    }

    @Test
    void acaiBowl() {
        List<String> expectedIngredients = Arrays.asList("acai", "banana");
        List<String> actualIngredients = configs.acaiBowl.ingredient;
        assertEquals(expectedIngredients, actualIngredients);

        List<String> actualStation = configs.acaiBowl.cuttingBoard;
        assertEquals(expectedIngredients, actualStation);

        List<String> actualCollectStation = configs.acaiBowl.produceBasket;
        assertEquals(expectedIngredients, actualCollectStation);

        List<String> actualBlender = configs.acaiBowl.blender;
        assertEquals(expectedIngredients, actualBlender);

        assertEquals(6, configs.acaiBowl.makingTime);
        assertEquals(20, configs.acaiBowl.price);
    }

    @Test
    void salad() {
        List<String> expectedIngredients = Arrays.asList("tomato", "cucumber", "lettuce");
        List<String> actualIngredients = configs.salad.ingredient;
        assertEquals(expectedIngredients, actualIngredients);

        List<String> actualStation = configs.salad.cuttingBoard;
        assertEquals(expectedIngredients, actualStation);

        List<String> actualCollectStation = configs.salad.produceBasket;
        assertEquals(expectedIngredients, actualCollectStation);

        assertEquals(6, configs.salad.makingTime);
        assertEquals(25, configs.salad.price);
    }

    @Test
    void fruitSalad() {
        List<String> expectedIngredients = Arrays.asList("banana", "strawberry");
        List<String> actualIngredients = configs.fruitSalad.ingredient;
        assertEquals(expectedIngredients, actualIngredients);

        List<String> actualStation = configs.fruitSalad.cuttingBoard;
        assertEquals(expectedIngredients, actualStation);

        List<String> actualCollectStation = configs.fruitSalad.produceBasket;
        assertEquals(expectedIngredients, actualCollectStation);

        assertEquals(6, configs.fruitSalad.makingTime);
        assertEquals(20, configs.fruitSalad.price);
    }
    @Test
    void steakMeal() {
        List<String> expectedIngredients = Arrays.asList("beef", "tomato", "cucumber");
        List<String> actualIngredients = configs.steakMeal.ingredient;
        assertEquals(expectedIngredients, actualIngredients);

        List<String> expectedStation1 = Arrays.asList("tomato","cucumber");
        List<String> actualStation1 = configs.steakMeal.cuttingBoard;
        assertEquals(expectedStation1, actualStation1);

        List<String> expectedStation2 = List.of("beef");
        List<String> actualStation2 = configs.steakMeal.fryingPan;
        assertEquals(expectedStation2, actualStation2);

        List<String> actualStation3 = configs.steakMeal.oven;
        List<String> expectedStation3 = List.of();
        assertEquals(expectedStation3, actualStation3);

        List<String> actualCollectStation = configs.steakMeal.produceBasket;
        assertEquals(expectedStation1, actualCollectStation);

        List<String> actualCollectStation2 = configs.steakMeal.fridge;
        assertEquals(expectedStation2, actualCollectStation2);

        assertEquals(12, configs.steakMeal.makingTime);
        assertEquals(8, configs.steakMeal.burnedTime);
        assertEquals(40, configs.steakMeal.price);
    }

    @Test
    void bananaSplit() {
        List<String> expectedIngredients = Arrays.asList("banana", "strawberry", "chocolate");
        List<String> actualIngredients = configs.bananaSplit.ingredient;
        assertEquals(expectedIngredients, actualIngredients);

        List<String> actualStation = configs.bananaSplit.cuttingBoard;
        assertEquals(expectedIngredients, actualStation);

        List<String> expectedCollectStation = Arrays.asList("banana", "strawberry");
        List<String> actualCollectStation = configs.bananaSplit.produceBasket;
        assertEquals(expectedCollectStation, actualCollectStation);

        List<String> expectedCollectStation2 = List.of("chocolate");
        List<String> actualCollectStation2 = configs.bananaSplit.fridge;
        assertEquals(expectedCollectStation2, actualCollectStation2);

        assertEquals(6, configs.bananaSplit.makingTime);
        assertEquals(25, configs.bananaSplit.price);
    }

    @Test
     void getRecipeGivenBanana() {
        List<String> recipes = DishFactory.getPossibleRecipes(List.of("banana"));
        List<String> expected = List.of("bananaSplit", "fruitSalad", "acaiBowl");
        assertEquals(expected, recipes);
    }

    @Test
    void getRecipeGivenAcai() {
        List<String> recipes = DishFactory.getPossibleRecipes(List.of("acai"));
        List<String> expected = List.of("acaiBowl");
        assertEquals(expected, recipes);
    }

    @Test
    void getRecipeGivenTomato() {
        List<String> recipes = DishFactory.getPossibleRecipes(List.of("tomato"));
        List<String> expected = List.of("salad", "steakMeal");
        assertEquals(expected, recipes);
    }
    @Test
    void getRecipeGivenCucumber() {
        List<String> recipes = DishFactory.getPossibleRecipes(List.of("cucumber"));
        List<String> expected = List.of("salad", "steakMeal");
        assertEquals(expected, recipes);
    }

    @Test
    void getRecipeGivenLettuce() {
        List<String> recipes = DishFactory.getPossibleRecipes(List.of("lettuce"));
        List<String> expected = List.of("salad");
        assertEquals(expected, recipes);
    }

    @Test
    void getRecipeGivenStrawberry() {
        List<String> recipes = DishFactory.getPossibleRecipes(List.of("strawberry"));
        List<String> expected = List.of("bananaSplit","fruitSalad");
        assertEquals(expected, recipes);
    }

    @Test
    void getRecipeGivenChocolate() {
        List<String> recipes = DishFactory.getPossibleRecipes(List.of("chocolate"));
        List<String> expected = List.of("bananaSplit");
        assertEquals(expected, recipes);
    }

    @Test
    void getRecipeGivenTwoIngredient() {
        List<String> recipes1 = DishFactory.getPossibleRecipes(List.of("tomato", "cucumber"));
        List<String> expected1 = List.of("salad", "steakMeal");
        assertEquals(expected1, recipes1);

        List<String> recipes2 = DishFactory.getPossibleRecipes(List.of("banana", "strawberry"));
        List<String> expected2 = List.of("bananaSplit", "fruitSalad");
        assertEquals(expected2, recipes2);

        List<String> recipes3 = DishFactory.getPossibleRecipes(List.of("acai", "banana"));
        List<String> expected3 = List.of("acaiBowl");
        assertEquals(expected3, recipes3);
    }

    @Test
    void getRecipeGivenThreeIngredient() {
        List<String> recipes1 = DishFactory.getPossibleRecipes(List.of("tomato", "cucumber", "lettuce"));
        List<String> expected1 = List.of("salad");
        assertEquals(expected1, recipes1);

        List<String> recipes2 = DishFactory.getPossibleRecipes(List.of("banana", "strawberry", "chocolate"));
        List<String> expected2 = List.of("bananaSplit");
        assertEquals(expected2, recipes2);

        List<String> recipes3 = DishFactory.getPossibleRecipes(List.of("beef", "cucumber", "tomato"));
        List<String> expected3 = List.of("steakMeal");
        assertEquals(expected3, recipes3);
    }

    @Test
    void getRecipeGivenOtherIngredient() {
        List<String> recipes1 = DishFactory.getPossibleRecipes(List.of("cheese", "cucumber", "lettuce"));
        List<String> expected1 = List.of();
        assertEquals(expected1, recipes1);

        List<String> recipes2 = DishFactory.getPossibleRecipes(List.of("fish", "chips"));
        List<String> expected2 = List.of();
        assertEquals(expected2, recipes2);

        List<String> recipes3 = DishFactory.getPossibleRecipes(List.of("noodle", "soup", "pudding"));
        List<String> expected3 = List.of();
        assertEquals(expected3, recipes3);
    }

    @Test
    void getDefinitiveRecipe() {
        Optional<String> recipe1 = DishFactory.getDefinitiveRecipe(List.of("tomato", "cucumber", "lettuce"));
        assertEquals(Optional.of("salad"), recipe1);

        Optional<String> recipe2 = DishFactory.getDefinitiveRecipe(List.of("tomato", "cucumber"));
        assertEquals(Optional.empty(), recipe2);
    }
}
