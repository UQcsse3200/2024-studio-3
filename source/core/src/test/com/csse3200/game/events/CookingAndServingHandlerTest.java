package com.csse3200.game.events;

import com.badlogic.gdx.graphics.Texture;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class CookingAndServingHandlerTest {

    private CookingAndServingHandler cookingHandler;
    private GameTime gameTime;
    private Entity entity;

    @BeforeEach
    void setup () {
        gameTime = new GameTime();
        cookingHandler = new CookingAndServingHandler(gameTime);

        entity = new Entity();
        cookingHandler.setEntity(entity);
    }

    @Test
    void createWithMissingComponents() {
        cookingHandler.create();
        assertNull(entity.getComponent(IngredientComponent.class));
        assertNull(entity.getComponent(TextureRenderComponent.class));
    }

    @Test
    void startCookingWithRawIngredientTest() {

        IngredientComponent ingredient = new IngredientComponent("Beef", ItemType.BEEF, 10,5,0, "raw");
        TextureRenderComponent textureRender = new TextureRenderComponent("images/raw_beef.png");
        entity.addComponent(ingredient);
        entity.addComponent(textureRender);

        cookingHandler.create();
        cookingHandler.startCooking();

        assertTrue(cookingHandler.isCooking());
        assertEquals("raw", ingredient.getItemState());

    }

    @Test
    void cookingWithNonRawIngredientTest() {

        IngredientComponent ingredient = new IngredientComponent("Beef", ItemType.BEEF, 10, 5, 0, "cooked");
        TextureRenderComponent textureRender = new TextureRenderComponent("images/cooked_beef.png");

        entity.addComponent(ingredient);
        entity.addComponent(textureRender);

        cookingHandler.create();
        cookingHandler.startCooking();

        assertFalse(cookingHandler.isCooking());
        assertEquals("cooked", ingredient.getItemState());
    }

    @Test
    void stopCookingTest() {

        IngredientComponent ingredient = new IngredientComponent("Beef", ItemType.BEEF, 10,5, 0,"raw" );
        entity.addComponent(ingredient);
        entity.addComponent(new TextureRenderComponent("images/raw_beef.png"));

        cookingHandler.create();
        cookingHandler.startCooking();
        cookingHandler.stopCooking();

        assertFalse(cookingHandler.isCooking());
    }

    @Test
    void updateTextureTest () {
        IngredientComponent ingredient = new IngredientComponent("Beef", ItemType.BEEF, 10, 5, 0, "raw");
        TextureRenderComponent textureRender = new TextureRenderComponent("images/raw_beef.png");

        entity.addComponent(ingredient);
        entity.addComponent(textureRender);

        cookingHandler.create();
        ingredient.cookItem();
        cookingHandler.updateState();

        assertEquals("images/cooked_beef.png", textureRender.getTexturePath());
    }

}
