package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.maingame.MainGameActions;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.configs.BaseCustomerConfig;
import com.csse3200.game.entities.configs.CustomerPersonalityConfig;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.csse3200.game.files.FileLoader;

/**
 * Unit tests for the MainGameOrderTicketDisplay class.
 */
@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class OrderManagerTest {


    private EventHandler eventHandler;
    private Entity customer;
    private CustomerComponent customerComponent;
    private Recipe recipe;

    @BeforeEach
    void setup() {
        // Mock FileLoader to return a valid recipes map when OrderManager tries to load recipes
        Map<String, Recipe> mockRecipes = new HashMap<>();
        Recipe bananaSplitRecipe = mock(Recipe.class);
        when(bananaSplitRecipe.getName()).thenReturn("bananaSplit");
        mockRecipes.put("bananaSplit", bananaSplitRecipe);

        // Use Mockito to mock the FileLoader static method
        mockStatic(FileLoader.class);
        when(FileLoader.readClass(eq(Map.class), eq("configs/recipe.json"))).thenReturn(mockRecipes);

        // Load recipes from the mocked FileLoader
        OrderManager.loadRecipes();
    }

    @Test
    void getRecipeTest() {
        // Now, the "bananaSplit" recipe should be available due to the mocked FileLoader
        Recipe recipe = OrderManager.getRecipe("bananaSplit");

        assertNotNull(recipe, "Recipe should not be null");
        assertEquals("bananaSplit", recipe.getName(), "Recipe name should be bananaSplit");
    }

//    @Test
//    void displayOrderTest_Banana_Split(){
//        recipe = mock(Recipe.class);
//        eventHandler = mock(EventHandler.class);
//        EntityService entityService = mock(EntityService.class);
//        ServiceLocator.registerEntityService(entityService);
//
//        customer = mock(Entity.class);
//        customerComponent = mock(CustomerComponent.class);
//
//        when(customer.getComponent(CustomerComponent.class)).thenReturn(customerComponent);
//        when(entityService.getEvents()).thenReturn(eventHandler);
//        when(customerComponent.getPreference()).thenReturn("bananaSplit");
//
//        OrderManager.displayOrder(customer);
//        verify(eventHandler).trigger("createBananaDocket");
//    }
}
