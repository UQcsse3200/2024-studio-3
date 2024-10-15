package com.csse3200.game.components.ordersystem;

import com.csse3200.game.entities.configs.MultiStationRecipeConfig;
import com.csse3200.game.entities.configs.SingleStationRecipeConfig;
import com.csse3200.game.entities.factories.DishFactory;

import java.util.Collections;
import java.util.List;

/**
 * Get recipe data from any station
 */
public class Recipe {
	protected String recipeName;
	private SingleStationRecipeConfig singleStationRecipe;
	private MultiStationRecipeConfig multiStationRecipe;

	/**
	 * Constructs recipe data from the recipe's name
	 * @param recipeName name of the recipe
	 */
	public Recipe(String recipeName) {
		this.recipeName = recipeName;
		loadRecipeDetails();
	}

	/**
	 * Loads recipe data
	 */
	protected void loadRecipeDetails() {
		if (DishFactory.recipeExists(recipeName)) {
			singleStationRecipe = DishFactory.getSingleStationRecipe(recipeName);
			multiStationRecipe = DishFactory.getMultiStationRecipe(recipeName);
		}
	}

	public boolean isValid() {
		return singleStationRecipe != null || multiStationRecipe != null;
	}

	/**
	 * Gets recipe ingredients
	 * @return recipe ingredients
	 */
	public List<String> getIngredients() {
		if (multiStationRecipe != null) {
			return multiStationRecipe.getIngredient();
		} else if (singleStationRecipe != null) {
			return singleStationRecipe.getIngredient();
		}
		return Collections.emptyList();
	}

	/**
	 * Gets recipe making time
	 * @return making time
	 */
	public int getMakingTime() {
		if (multiStationRecipe != null) {
			return multiStationRecipe.getMakingTime();
		} else if (singleStationRecipe != null) {
			return singleStationRecipe.getMakingTime();
		}
		return 0;
	}

	/**
	 * Gets recipe name
	 * @return recipe name
	 */
	public String getName() {
		return this.recipeName;
	}

	/**
	 * Gets recipe burn time
	 * @return recipe burn time
	 */
	public Integer getBurnedTime() {
		if (multiStationRecipe != null) {
			return multiStationRecipe.getBurnedTime();
		}
		return null;
	}

	/**
	 * Gets recipe station type
	 * @return recipe station type
	 */
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

	// Getters for test coverage
	public SingleStationRecipeConfig getSingleStationRecipe() {
		return singleStationRecipe;
	}

	public MultiStationRecipeConfig getMultiStationRecipe() {
		return multiStationRecipe;
	}

	public void setSingleStationRecipe(SingleStationRecipeConfig singleStationRecipe) {
		this.singleStationRecipe = singleStationRecipe;
	}

	public void setMultiStationRecipe(MultiStationRecipeConfig multiStationRecipe) {
		this.multiStationRecipe = multiStationRecipe;
	}
}
