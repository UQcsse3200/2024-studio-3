package com.csse3200.game.components.items;

import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class IngredientComponentTest {
    @Test
    void IngredientComponentConstructorTestCookableAndChoppable() {
        IngredientComponent ingredient = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10,
                5, 3, "raw");
        assertEquals("Cucumber", ingredient.getItemName());
        assertEquals(ItemType.CUCUMBER, ingredient.getItemType());
        assertEquals(10, ingredient.weight());
        assertEquals(5, ingredient.getCookTime());
        assertEquals(3, ingredient.getChopTime());
        assertEquals("raw", ingredient.getItemState());
        assertTrue(ingredient.isCookable());
        assertTrue(ingredient.isChoppable());
    }

    @Test
    void IngredientComponentConstructorTestCookableNotChoppable() {
        IngredientComponent ingredient = new IngredientComponent("Beef", ItemType.BEEF, 10,
                8, 0, "cooked");
        assertEquals("Beef", ingredient.getItemName());
        assertEquals(ItemType.BEEF, ingredient.getItemType());
        assertEquals(10, ingredient.weight());
        assertEquals(8, ingredient.getCookTime());
        assertEquals(0, ingredient.getChopTime());
        assertEquals("cooked", ingredient.getItemState());
        assertTrue(ingredient.isCookable());
        assertFalse(ingredient.isChoppable());
    }

    @Test
    void IngredientComponentConstructorTestChoppableNotCookable() {
        IngredientComponent ingredient = new IngredientComponent("Strawberry", ItemType.STRAWBERRY, 5,
                0, 4, "chopped");
        assertEquals("Strawberry", ingredient.getItemName());
        assertEquals(ItemType.STRAWBERRY, ingredient.getItemType());
        assertEquals(5, ingredient.weight());
        assertEquals(0, ingredient.getCookTime());
        assertEquals(4, ingredient.getChopTime());
        assertEquals("chopped", ingredient.getItemState());
        assertFalse(ingredient.isCookable());
        assertTrue(ingredient.isChoppable());
    }

    @Test
    void IngredientComponentConstructorTest() {
        IngredientComponent ingredient = new IngredientComponent("Lettuce", ItemType.LETTUCE, 3,
                0, 0, "raw");
        assertEquals("Lettuce", ingredient.getItemName());
        assertEquals(ItemType.LETTUCE, ingredient.getItemType());
        assertEquals(3, ingredient.weight());
        assertEquals(0, ingredient.getCookTime());
        assertEquals(0, ingredient.getChopTime());
        assertEquals("raw", ingredient.getItemState());
        assertFalse(ingredient.isCookable());
        assertFalse(ingredient.isChoppable());
    }

    @Test
    void cookItemTest() {
        IngredientComponent ingredient = new IngredientComponent("Beef", ItemType.BEEF, 10, 5,
                0, "raw");
        ingredient.cookItem();
        assertEquals("cooked", ingredient.getItemState());
    }

    @Test
    void cookItemUnsupportedOperationExceptionTest() {
        IngredientComponent ingredient = new IngredientComponent("Banana", ItemType.BANANA, 1, 0,
                5, "raw");
        try {
            ingredient.cookItem();
            fail("Expected UnsupportedOperationException to be thrown");
        } catch (UnsupportedOperationException e) {
            assertEquals("This item is not cookable.", e.getMessage());
        }
    }

    @Test
    void burnItemTest() {
        IngredientComponent ingredient = new IngredientComponent("Beef", ItemType.BEEF, 10, 5,
                0, "raw");
        ingredient.burnItem();
        assertEquals("burnt", ingredient.getItemState());
    }

    @Test
    void burnItemUnsupportedOperationExceptionTest() {
        IngredientComponent ingredient = new IngredientComponent("Banana", ItemType.BANANA, 1, 0,
                5, "raw");
        try {
            ingredient.burnItem();
            fail("Expected UnsupportedOperationException to be thrown");
        } catch (UnsupportedOperationException e) {
            assertEquals("This item is not able to be burnt.", e.getMessage());
        }
    }

    @Test
    void chopItemUnsupportedOperationExceptionTest() {
        IngredientComponent ingredient = new IngredientComponent("Beef", ItemType.BEEF, 10, 5,
                0, "raw");
        try {
            ingredient.chopItem();
            fail("Expected UnsupportedOperationException to be thrown");
        } catch (UnsupportedOperationException e) {
            assertEquals("This item is not choppable.", e.getMessage());
        }
    }

    @Test
    void chopItemTest() {
        IngredientComponent ingredient = new IngredientComponent("Banana", ItemType.BANANA, 1, 0,
                5, "raw");
        ingredient.chopItem();
        assertEquals("chopped", ingredient.getItemState());
    }
}
