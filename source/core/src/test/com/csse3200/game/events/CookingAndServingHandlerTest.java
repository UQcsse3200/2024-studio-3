package com.csse3200.game.events;

import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class CookingAndServingHandlerTest {

    private CookingAndServingHandler cookingHandler;
    private GameTime gameTime;
    private Entity entity;
    private IngredientComponent ingredient;
    private TextureRenderComponent textureRender;

    @BeforeEach
    void setup () {
        gameTime = mock(GameTime.class);
        cookingHandler = new CookingAndServingHandler(gameTime);

        entity = mock(Entity.class);
        ingredient = mock(IngredientComponent.class);
        textureRender = mock(TextureRenderComponent.class);

        when(entity.getComponent(IngredientComponent.class)).thenReturn(ingredient);
        when(entity.getComponent(TextureRenderComponent.class)).thenReturn(textureRender);

        cookingHandler.setEntity(entity);
    }

    @Test
    void createTest() {
        when(ingredient.getItemState()).thenReturn("raw");
        when(ingredient.getItemName()).thenReturn("fish");

        cookingHandler.create();

        verify(entity).getComponent(IngredientComponent.class);
        verify(entity).getComponent(TextureRenderComponent.class);
        verify(textureRender).setTexture("images/ingredients/raw_fish.png");
    }

    @Test
    void startCookingtartTest() {
        when(ingredient.getItemState()).thenReturn("raw");
        when(gameTime.getTime()).thenReturn(1000L);

        cookingHandler.create();
        cookingHandler.startCooking();

        assertTrue(cookingHandler.isCooking());
        verify(ingredient).getItemState();
    }

    @Test
    void startCookingWithNonRawIngredientTest() {
        when(ingredient.getItemState()).thenReturn("cooked");

        cookingHandler.create();
        cookingHandler.startCooking();

        assertFalse(cookingHandler.isCooking());
    }

    @Test
    void stopCookingTest() {
        when(ingredient.getItemState()).thenReturn("raw");

        cookingHandler.create();
        cookingHandler.startCooking();
        cookingHandler.stopCooking();

        assertFalse(cookingHandler.isCooking());
    }

    @Test
    void serveMealTest() {
        when(ingredient.getItemState()).thenReturn("cooked");
        when(ingredient.getItemName()).thenReturn("beef");

        cookingHandler.create();
        cookingHandler.serveMeal();

        verify(textureRender).setTexture("images/ingredients/cooked_beef.png");
        assertTrue(cookingHandler.isServed());
    }

    @Test
    void serveMealNotCooked() {
        when(ingredient.getItemState()).thenReturn("raw");

        cookingHandler.create();
        cookingHandler.serveMeal();

        verify(ingredient).getItemState();
        assertFalse(cookingHandler.isServed());
    }

    @Test
    void deleteMealWhenServedTest() {
        when(ingredient.getItemState()).thenReturn("cooked");
        when(ingredient.getItemName()).thenReturn("beef");

        cookingHandler.create();
        cookingHandler.serveMeal();
        cookingHandler.deleteMeal();

        verify(entity).dispose();
    }

    @Test
    void deleteMealWhenNotServedTest() {
        when(ingredient.getItemState()).thenReturn("raw");

        cookingHandler.create();
        cookingHandler.deleteMeal();

        verify(entity, never()).dispose();
    }

    @Test
    void updateStateCookingToCooked() {
        when(ingredient.getItemState()).thenReturn("raw");
        when(gameTime.getTime()).thenReturn(0L).thenReturn(5000L);
        when(ingredient.getItemName()).thenReturn("beef");

        cookingHandler.create();
        cookingHandler.startCooking();
        cookingHandler.updateState();

        verify(ingredient).cookItem();
        verify(textureRender).setTexture("images/ingredients/cooked_beef.png");
    }

    @Test
    void updateStateCookingToBurnt() {
        when(ingredient.getItemState()).thenReturn("raw");
        when(gameTime.getTime()).thenReturn(0L).thenReturn(6500L);
        when(ingredient.getCookTime()).thenReturn(5);
        when(ingredient.getItemName()).thenReturn("beef");

        cookingHandler.create();
        cookingHandler.startCooking();
        cookingHandler.updateState();

        verify(ingredient).burnItem();
        verify(textureRender).setTexture("images/ingredients/burnt_beef.png");
    }

    @Test
    void noStateChangeTest() {
        when(ingredient.getItemState()).thenReturn("raw");
        when(gameTime.getTime()).thenReturn(0L).thenReturn(3000L);
        when(ingredient.getCookTime()).thenReturn(5);

        cookingHandler.create();
        cookingHandler.startCooking();
        cookingHandler.updateState();

        verify(ingredient, never()).cookItem();
        verify(ingredient, never()).burnItem();

    }

}
