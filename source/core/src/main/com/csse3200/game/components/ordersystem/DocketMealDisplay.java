package com.csse3200.game.components.ordersystem;


public class DocketMealDisplay {
    private static final String[] mealDocketTextures = {
            "images/ordersystem/salad_docket.png",
            "images/ordersystem/fruit_salad_docket.png",
            "images/ordersystem/steak_meal_docket.png",
            "images/ordersystem/acai_bowl_docket.png",
            "images/ordersystem/banana_split_docket.png",
            "images/ordersystem/salad_docket_vertical.png",
            "images/ordersystem/fruit_salad_docket_vertical.png",
            "images/ordersystem/steak_meal_docket_vertical.png",
            "images/ordersystem/acai_bowl_docket_vertical.png",
            "images/ordersystem/banana_split_docket_vertical.png"
    };

    public static String[] getMealDocketTextures() {
        return mealDocketTextures;
    }


    public String getMealImage(String mealName, String type) {
        String mealImage;
        if(type.equalsIgnoreCase("vertical")){


            return switch (mealName) {
                case "salad" -> {
                    mealImage = "images/ordersystem/salad_docket.png";
                    yield mealImage;
                }
                case "fruitSalad" -> {
                    mealImage = "images/ordersystem/fruit_salad_docket.png";
                    yield mealImage;
                }
                case "steakMeal" -> {
                    mealImage = "images/ordersystem/steak_meal_docket.png";
                    yield mealImage;
                }
                case "acaiBowl" -> {
                    mealImage = "images/ordersystem/acai_bowl_docket.png";
                    yield mealImage;
                }
                case "bananaSplit" -> {
                    mealImage = "images/ordersystem/banana_split_docket.png";
                    yield mealImage;
                }
                default -> null;
            };
        }
        else{

            return switch (mealName) {
                case "salad" -> {
                    mealImage = "images/ordersystem/salad_docket_vertical.png";
                    yield mealImage;
                }
                case "fruitSalad" -> {
                    mealImage = "images/ordersystem/fruit_salad_docket_vertical.png";
                    yield mealImage;
                }
                case "steakMeal" -> {
                    mealImage = "images/ordersystem/steak_meal_docket_vertical.png";
                    yield mealImage;
                }
                case "acaiBowl" -> {
                    mealImage = "images/ordersystem/acai_bowl_docket_vertical.png";
                    yield mealImage;
                }
                case "bananaSplit" -> {
                    mealImage = "images/ordersystem/banana_split_docket_vertical.png";
                    yield mealImage;
                }
                default -> null;
            };
        }

    }
}
