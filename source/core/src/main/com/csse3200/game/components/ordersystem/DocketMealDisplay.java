package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.csse3200.game.services.ServiceLocator;

public class DocketMealDisplay {
    private Image mealImage;
    private static final String[] mealDocketTextures = {
            "images/ordersystem/salad_docket.png",
            "images/ordersystem/fruit_salad_docket.png",
            "images/ordersystem/steak_meal_docket.png",
            "images/ordersystem/acai_bowl_docket.png",
            "images/ordersystem/banana_split_docket.png"
    };

    public static String[] getMealDocketTextures() {
        return mealDocketTextures;
    }

    public Image getMealImage(String mealName) {
        switch (mealName) {
            case "salad":
                mealImage = new Image(ServiceLocator.getResourceService().getAsset("images/ordersystem/salad_docket.png", Texture.class));
                return mealImage;
            case "fruitSalad":
                mealImage = new Image(ServiceLocator.getResourceService().getAsset("images/ordersystem/fruit_salad_docket.png", Texture.class));
                return mealImage;
            case "steakMeal":
                mealImage = new Image(ServiceLocator.getResourceService().getAsset("images/ordersystem/steak_meal_docket.png", Texture.class));
                return mealImage;
            case "acaiBowl":
                mealImage = new Image(ServiceLocator.getResourceService().getAsset("images/ordersystem/acai_bowl_docket.png", Texture.class));
                return mealImage;
            case "bananaSplit":
                mealImage = new Image(ServiceLocator.getResourceService().getAsset("images/ordersystem/banana_split_docket.png", Texture.class));
                return mealImage;
            default:
                return null;
        }

    }
}
