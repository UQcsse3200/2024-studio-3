
package com.csse3200.game.entities.factories;


import com.csse3200.game.entities.configs.CookingConfig;
import com.csse3200.game.files.FileLoader;

public class DishFactory {

    /**
     * Factory to create recipe entities with predefined components.
     *
     * <p>Predefined recipe properties are loaded from a config stored as a json file and should have
     * the properties stores in 'CookingConfig'.
     */
    private static final CookingConfig configs =
            FileLoader.readClass(CookingConfig.class, "configs/recipe.json");
}
