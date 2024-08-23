
package com.csse3200.game.entities.factories;


import com.csse3200.game.entities.configs.CookingConfig;
import com.csse3200.game.files.FileLoader;

public class DishFactory {

    private static final CookingConfig configs =
            FileLoader.readClass(CookingConfig.class, "configs/recipe.json");
}
