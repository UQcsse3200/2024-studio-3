package com.csse3200.game.components.ordersystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DocketMealDisplayTest {
    DocketMealDisplay docketMealDisplay;
    @BeforeEach
    void setUp() {
        docketMealDisplay = new DocketMealDisplay();
    }

    @Test
    void testGetMealImageNoExist() {
        assertNull(docketMealDisplay.getMealImage("none", "vertical"));

        assertNull(docketMealDisplay.getMealImage("none", "notVertical"));

    }

    @Test
    void testVerticalMealImage() {
        String type = "vertical";
        checkMealImage(type,
                "salad",
                "images/ordersystem/salad_docket.png");
        checkMealImage(type,
                "fruitSalad",
                "images/ordersystem/fruit_salad_docket.png");
        checkMealImage(type,
                "steakMeal",
                "images/ordersystem/steak_meal_docket.png");
        checkMealImage(type,
                "acaiBowl",
                "images/ordersystem/acai_bowl_docket.png");
        checkMealImage(type,
                "bananaSplit",
                "images/ordersystem/banana_split_docket.png");
    }

    @Test
    void testNotVerticalMealImage() {
        String type = "notVertical";
        checkMealImage(type,
                "salad",
                "images/ordersystem/salad_docket_vertical.png");
        checkMealImage(type,
                "fruitSalad",
                "images/ordersystem/fruit_salad_docket_vertical.png");
        checkMealImage(type,
                "steakMeal",
                "images/ordersystem/steak_meal_docket_vertical.png");
        checkMealImage(type,
                "acaiBowl",
                "images/ordersystem/acai_bowl_docket_vertical.png");
        checkMealImage(type,
                "bananaSplit",
                "images/ordersystem/banana_split_docket_vertical.png");
    }

    private void checkMealImage(String type, String mealName, String returnVal) {
        assertEquals(returnVal, docketMealDisplay.getMealImage(mealName, type));
    }
}
