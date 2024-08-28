package com.csse3200.game.components.ordersystem;

import com.csse3200.game.entities.configs.MultiStationRecipeConfig;
import com.csse3200.game.entities.configs.SingleStationRecipeConfig;
import com.csse3200.game.entities.factories.DishFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Recipe {
	private String recipeName;
	private SingleStationRecipeConfig singleStationRecipe;
	private MultiStationRecipeConfig multiStationRecipe;

	public Recipe(String recipeName) {
		this.recipeName = recipeName;
		loadRecipeDetails();
	}

	private void loadRecipeDetails() {
		if (DishFactory.recipeExists(recipeName)) {
			singleStationRecipe = DishFactory.getSingleStationRecipe(recipeName);
			multiStationRecipe = DishFactory.getMultiStationRecipe(recipeName);
		}
	}

	public List<String> getIngredients() {
		if (multiStationRecipe != null) {
			return multiStationRecipe.getIngredient();
		} else if (singleStationRecipe != null) {
			return singleStationRecipe.getIngredient();
		}
		return Collections.emptyList();
	}

	public int getMakingTime() {
		if (multiStationRecipe != null) {
			return multiStationRecipe.getMakingTime();
		} else if (singleStationRecipe != null) {
			return singleStationRecipe.getMakingTime();
		}
		return 0;
	}

	public Integer getBurnedTime() {
		if (multiStationRecipe != null) {
			return multiStationRecipe.getBurnedTime();
		}
		return null;
	}

	public String getStationType() {
		if (multiStationRecipe != null) {
			if (multiStationRecipe.getFryingPan() != null) {
				return "FRYING_PAN";
			} else if (multiStationRecipe.getOven() != null) {
				return "OVEN";
			}
		}
		return "CUTTING_BOARD";
	}
}
