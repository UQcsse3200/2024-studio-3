package com.csse3200.game.components.recipe;

import java.util.Arrays;
import java.util.List;

 public enum FoodRecipe {
    ACAI_BOWL(Arrays.asList("Acai", "Banana")),
    SALAD(Arrays.asList("Tomato", "Lettuce", "Cucumber")),
    FRUIT_SALAD(Arrays.asList("Banana", "Strawberry")),
    STEAK_MEAL(Arrays.asList("Beef", "Tomato", "Cucumber")),
    BANANA_SPLIT(Arrays.asList("Banana", "Chocolate", "Strawberry"));

    private final List<String> ingredients;

    FoodRecipe(List<String> ingredients) {
        this.ingredients = ingredients;
    }


    public List<String> getIngredients() {
        return ingredients;
    }
}
