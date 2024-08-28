package com.csse3200.game.events;

import com.badlogic.gdx.graphics.Texture;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class CookingAndServingHandlerTest {

    private CookingAndServingHandler cookingHandler;
    private GameTime gameTime;
    private Entity entity;

    @BeforeEach
    void setUp() {
        gameTime = new GameTime();
        cookingHandler = new CookingAndServingHandler(gameTime);

        entity = new Entity();
        cookingHandler.setEntity(entity);
    }

    @Test
    void createWithMissingComponentTest() {
        cookingHandler.create();
        assertNull(entity.getComponent(IngredientComponent.class));
        assertNull(entity.getComponent(TextureRenderComponent.class));
    }


}
