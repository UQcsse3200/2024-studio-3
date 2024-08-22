/*
package com.csse3200.game.entity.factory;

import com.csse3200.game.entities.config.CookingConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CookingFactory {

    public static List<CookingConfig> initializeRecipes(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse JSON file into a list of CookingConfig objects
            CookingConfig[] recipeArray = objectMapper.readValue(new File(jsonFilePath), CookingConfig[].class);
            for (CookingConfig recipe : recipeArray) {
                recipes.add(recipe);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipes;
    }

}*/
