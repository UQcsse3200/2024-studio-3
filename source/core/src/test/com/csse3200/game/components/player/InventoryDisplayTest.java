package com.csse3200.game.components.player;


import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedConstruction;
import static org.junit.Assert.*;
import com.badlogic.gdx.graphics.Texture;
import com.csse3200.game.extensions.GameExtension;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.rendering.Renderable;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.junit.Before;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import java.lang.reflect.Field;


// the tests here are running into some issue with the renderer.
@ExtendWith(GameExtension.class)
class InventoryDisplayTest {
    private AutoCloseable mocks;
    private Entity entity;
    private ItemComponent item1;
    private IngredientComponent item2;
    private ItemComponent item3;
    private InventoryComponent inventory;

    private EventHandler mockEventHandler;
    private RenderService renderService;
    private ResourceService resourceService;
    private InventoryDisplay display;


    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        renderService = mock(RenderService.class);
        resourceService = mock(ResourceService.class);

        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerRenderService(renderService);

        when(resourceService.getAsset("images/inventory_ui/slot.png", Texture.class))
            .thenReturn(mock(Texture.class));
        when(resourceService.getAsset("images/ingredients/raw_lettuce.png", Texture.class))
            .thenReturn(mock(Texture.class));
        when(resourceService.getAsset("images/ingredients/cooked_beef.png", Texture.class))
            .thenReturn(mock(Texture.class));

        display = spy(new InventoryDisplay());
        stage = new Stage();
        display.setStage(stage);

        renderService.register(display);

        entity = spy(new Entity());
        inventory = spy (new InventoryComponent(1));

        entity.addComponent(inventory);

        //generating items
        item1 = new ItemComponent("Cucumber", ItemType.CUCUMBER, 10);
        item2 = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10, 5, 3, "raw");

        inventory.addItem(item2);

        // Initialize InventoryDisplay and set the mock entity
        display.setEntity(entity);
     }
    private Stage stage;
    @AfterEach
    void end() throws Exception {
        mocks.close();
        ServiceLocator.clear();
        display.dispose();
    }
    //@Test
    //public void shouldCreate() {
    //    display.create();
    //    verify(display).create();
    //}

    //@Test
    //public void shouldUpdate() {
    //    display.create();
    //    display.update();
    //    verify(inventory).getItemFirst();
    //    assertEquals(inventory.getItemFirst().getItemName(), "Cucumber");
    //    assertEquals(inventory.getItemFirst().getItemType(), ItemType.CUCUMBER);
    //    assert (((IngredientComponent) item2).getItemState().equals("raw"));
    //    verify(entity).getEvents(); //this isn't getting called somehow

    //}
}
