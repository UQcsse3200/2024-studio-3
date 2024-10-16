package com.csse3200.game.components.station.loader;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.csse3200.game.extensions.GameExtension;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

@ExtendWith(GameExtension.class)
public class StationAcceptableItemsGetterTest {

    String[] oven = {"tomato", "cucumber"};
    String[] stove = {"beef"};
    String[] cuttingBoard = {"acai", "banana", "tomato", "cucumber", "lettuce", "strawberry", "chocolate"};
    String[] blender = {"acai", "banana"};
    String[] produceBasket = {"acai", "banana", "tomato", "cucumber", "lettuce", "strawberry"};
    String[] fridge = {"beef", "chocolate"};

    @Test
    public void TestLoadingOven() {
        ArrayList<String> items = StationAcceptableItemsGetter.getAcceptableItems("oven");

        assertNotNull(items);

        for (String item : oven) {
            assertTrue(items.contains(item));
        }

        assertFalse(items.contains("not an accepted item"));
    }

    @Test
    public void TestLoadingStove() {
        ArrayList<String> items = StationAcceptableItemsGetter.getAcceptableItems("stove");

        assertNotNull(items);

        for (String item : stove) {
            assertTrue(items.contains(item));
        }
        
        assertFalse(items.contains("not an accepted item"));
    }

    @Test
    public void TestLoadingCuttingBoard() {
        ArrayList<String> items = StationAcceptableItemsGetter.getAcceptableItems("cutting board");

        assertNotNull(items);

        for (String item : cuttingBoard) {
            assertTrue(items.contains(item));
        }
        
        assertFalse(items.contains("not an accepted item"));
    }

    @Test
    public void TestLoadingBlender() {
        ArrayList<String> items = StationAcceptableItemsGetter.getAcceptableItems("blender");

        assertNotNull(items);

        for (String item : blender) {
            assertTrue(items.contains(item));
        }
        
        assertFalse(items.contains("not an accepted item"));
    }

    @Test
    public void TestLoadingProduceBasket() {
        ArrayList<String> items = StationAcceptableItemsGetter.getAcceptableItems("produce basket");

        assertNotNull(items);

        for (String item : produceBasket) {
            assertTrue(items.contains(item));
        }
        
        assertFalse(items.contains("not an accepted item"));
    }

    @Test
    public void TestLoadingFridge() {
        ArrayList<String> items = StationAcceptableItemsGetter.getAcceptableItems("fridge");

        assertNotNull(items);

        for (String item : fridge) {
            assertTrue(items.contains(item));
        }
        
        assertFalse(items.contains("not an accepted item"));
    }

    @Test
    public void TestLoadingInvalid() {
        ArrayList<String> items = StationAcceptableItemsGetter.getAcceptableItems("doesn't exist");

        assertNull(items);
    }
}

