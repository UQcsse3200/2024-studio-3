package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.csse3200.game.services.ServiceLocator;

public class DocketMealDisplay {
    private Texture mealImage;
    private static final String[] mealDocketTextures = {
            "images/ordersystem/salad_docket.png",
            "images/ordersystem/fruit_salad_docket.png",
            "images/ordersystem/steak_meal_docket.png",
            "images/ordersystem/acai_bowl_docket.png",
            "images/ordersystem/banana_split_docket.png",
            "images/fireExtinguisher/Fire_Extinguisher.png"
    };

    public static String[] getMealDocketTextures() {
        return mealDocketTextures;
    }

    public Texture getMealImage(String mealName, String type) {
        if(type.equalsIgnoreCase("vertical")){


            return switch (mealName) {
                case "salad" -> {
                    mealImage = new Texture(String.valueOf(ServiceLocator.getResourceService().getAsset("images/ordersystem/salad_docket.png", Texture.class)));
                    yield mealImage;
                }
                case "fruitSalad" -> {
                    mealImage = new Texture(String.valueOf(ServiceLocator.getResourceService().getAsset("images/ordersystem/fruit_salad_docket.png", Texture.class)));
                    yield mealImage;
                }
                case "steakMeal" -> {
                    mealImage = new Texture(String.valueOf(ServiceLocator.getResourceService().getAsset("images/ordersystem/steak_meal_docket.png", Texture.class)));
                    yield mealImage;
                }
                case "acaiBowl" -> {
                    mealImage = new Texture(String.valueOf(ServiceLocator.getResourceService().getAsset("images/ordersystem/acai_bowl_docket.png", Texture.class)));
                    yield mealImage;
                }
                case "bananaSplit" -> {
                    mealImage = new Texture(String.valueOf(ServiceLocator.getResourceService().getAsset("images/ordersystem/banana_split_docket.png", Texture.class)));
                    yield mealImage;
                }
                default -> null;
            };
        }
        else{

            return switch (mealName) {
                case "salad" -> {
                    mealImage = new Texture(String.valueOf(ServiceLocator.getResourceService().getAsset("images/ordersystem/salad_docket.png", Texture.class)));
                    yield mealImage;
                }
                case "fruitSalad" -> {
                    mealImage = new Texture(String.valueOf(ServiceLocator.getResourceService().getAsset("images/ordersystem/fruit_salad_docket.png", Texture.class)));
                    yield mealImage;
                }
                case "steakMeal" -> {
                    mealImage = new Texture(String.valueOf(ServiceLocator.getResourceService().getAsset("images/ordersystem/steak_meal_docket.png", Texture.class)));
                    yield mealImage;
                }
                case "acaiBowl" -> {
                    mealImage = new Texture(String.valueOf(ServiceLocator.getResourceService().getAsset("images/ordersystem/acai_bowl_docket.png", Texture.class)));
                    yield mealImage;
                }
                case "bananaSplit" -> {
                    mealImage = new Texture(String.valueOf(ServiceLocator.getResourceService().getAsset("images/ordersystem/banana_split_docket.png", Texture.class)));
                    yield mealImage;
                }
                default -> null;
            };

        }

    }
}
