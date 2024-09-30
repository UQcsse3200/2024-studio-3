package com.csse3200.game.components.ordersystem;

public enum RecipeNameEnums {
	ACAI_BOWL("acaiBowl"),
	SALAD("salad"),
	FRUIT_SALAD("fruitSalad"),
	STEAK_MEAL("steakMeal"),
	BANANA_SPLIT("bananaSplit");

	private final String recipeName;

	RecipeNameEnums(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getRecipeName() {
		return recipeName;
	}
}
