// package com.csse3200.game.components.player;


// import static org.mockito.Mockito.*;
// import org.mockito.Mockito;
// import org.mockito.MockitoAnnotations;
// import org.mockito.MockedConstruction;
// import static org.junit.Assert.*;
// import com.badlogic.gdx.graphics.Texture;
// import com.csse3200.game.extensions.GameExtension;
// import org.junit.Before;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import com.csse3200.game.components.items.IngredientComponent;
// import com.csse3200.game.components.items.ItemType;
// import com.csse3200.game.components.items.ItemComponent;
// import com.csse3200.game.entities.Entity;
// import com.csse3200.game.events.EventHandler;
// import com.csse3200.game.services.ServiceLocator;
// import com.csse3200.game.services.ResourceService;
// import com.csse3200.game.rendering.RenderService;
// import com.csse3200.game.rendering.Renderable;
// import com.badlogic.gdx.scenes.scene2d.Stage;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import org.junit.Before;
// import org.mockito.Mockito;

// import java.util.ArrayList;
// import java.util.Arrays;
// import com.badlogic.gdx.scenes.scene2d.ui.Stack;
// import java.lang.reflect.Field;


// // the tests here are running into some issue with the renderer.
// @ExtendWith(GameExtension.class)
// class InventoryDisplayTest {
//     private AutoCloseable mocks;
//     private Entity entity;
//     private ItemComponent item1;
//     private IngredientComponent item2;
//     private ItemComponent item3;
//     private InventoryComponent inventory;

//     private EventHandler mockEventHandler;
//     private RenderService renderService;
//     private ResourceService resourceService;
//     private InventoryDisplay display;


//     @BeforeEach
//     void setUp() {
//         mocks = MockitoAnnotations.openMocks(this);
//         renderService = mock(RenderService.class);
//         resourceService = mock(ResourceService.class);

//         ServiceLocator.registerResourceService(resourceService);
//         ServiceLocator.registerRenderService(renderService);

//         when(resourceService.getAsset("images/inventory_ui/slot.png", Texture.class))
//             .thenReturn(mock(Texture.class));
//         when(resourceService.getAsset("images/ingredients/raw_lettuce.png", Texture.class))
//             .thenReturn(mock(Texture.class));
//         when(resourceService.getAsset("images/ingredients/cooked_beef.png", Texture.class))
//             .thenReturn(mock(Texture.class));

//         display = spy(new InventoryDisplay());
//         stage = new Stage();
//         display.setStage(stage);

//         renderService.register(display);

//         entity = spy(new Entity());
//         inventory = spy (new InventoryComponent(1));

//         entity.addComponent(inventory);

//         //generating items
//         item1 = new ItemComponent("Cucumber", ItemType.CUCUMBER, 10);
//         item2 = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10, 5, 3, "raw");

//         inventory.addItem(item2);

//         // Initialize InventoryDisplay and set the mock entity
//         display.setEntity(entity);
//      }
//     private Stage stage;
//     @AfterEach
//     void end() throws Exception {
//         mocks.close();
//         ServiceLocator.clear();
//         display.dispose();
//     }
//     //@Test
//     //public void shouldCreate() {
//     //    display.create();
//     //    verify(display).create();
//     //}

//     //@Test
//     //public void shouldUpdate() {
//     //    display.create();
//     //    display.update();
//     //    verify(inventory).getItemFirst();
//     //    assertEquals(inventory.getItemFirst().getItemName(), "Cucumber");
//     //    assertEquals(inventory.getItemFirst().getItemType(), ItemType.CUCUMBER);
//     //    assert (((IngredientComponent) item2).getItemState().equals("raw"));
//     //    verify(entity).getEvents(); //this isn't getting called somehow

//     //}
// }

package com.csse3200.game.components.player;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import java.lang.reflect.Field;
import java.util.List;
import static org.junit.Assert.*;

public class InventoryDisplayTest {

    private static HeadlessApplication application;

    private Entity entity;
    private InventoryDisplay inventoryDisplay;
    private Stage stage;

    @BeforeClass
    public static void init() {
        // Start the headless application
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        application = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {
            }
            @Override
            public void resize(int width, int height) {
            }
            @Override
            public void render() {
            }
            @Override
            public void pause() {
            }
            @Override
            public void resume() {
            }
            @Override
            public void dispose() {
            }
        }, config);
    }

    @AfterClass
    public static void cleanUp() {
        application.exit();
        application = null;
    }

    @Before
    public void setUp() {
        // Mock Gdx.graphics
        Gdx.graphics = Mockito.mock(Graphics.class);
        Mockito.when(Gdx.graphics.getWidth()).thenReturn(800);
        Mockito.when(Gdx.graphics.getHeight()).thenReturn(600);

        // Mock Gdx.gl and Gdx.gl20
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;

        // Set up the ServiceLocator with a mocked ResourceService
        ResourceService resourceService = Mockito.mock(ResourceService.class);
        Texture slotTexture = Mockito.mock(Texture.class);
        Texture nullTexture = Mockito.mock(Texture.class);

        // Mock the getAsset method to return the mocked textures
        Mockito.when(resourceService.getAsset("images/inventory_ui/slot.png", Texture.class)).thenReturn(slotTexture);
        Mockito.when(resourceService.getAsset(Mockito.eq("images/inventory_ui/null_image.png"), Mockito.eq(Texture.class))).thenReturn(nullTexture);
        Mockito.when(resourceService.getAsset(Mockito.anyString(), Mockito.eq(Texture.class))).thenReturn(nullTexture);

        ServiceLocator.registerResourceService(resourceService);

        // Create the entity and add InventoryComponent and InventoryDisplay
        entity = new Entity();
        InventoryComponent inventoryComponent = new InventoryComponent(5); // capacity of 5
        entity.addComponent(inventoryComponent);

        // Create a mocked Batch
        Batch mockBatch = Mockito.mock(Batch.class);
        // Create a Viewport that doesn't rely on Gdx.graphics
        Viewport viewport = new FitViewport(800, 600, new OrthographicCamera());

        // Create the Stage with the mocked Batch and custom Viewport
        stage = new Stage(viewport, mockBatch);

        // Create the InventoryDisplay and attach to the entity
        inventoryDisplay = new InventoryDisplay();
        inventoryDisplay.setStage(stage);
        entity.addComponent(inventoryDisplay);

        // Create the components
        entity.create();
    }

    @Test
    public void testConstructorDefault() {
        InventoryDisplay display = new InventoryDisplay();
        assertEquals(200, display.getSlotSize());
    }

    @Test
    public void testConstructorWithSlotSize() {
        InventoryDisplay display = new InventoryDisplay(100);
        assertEquals(100, display.getSlotSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidSlotSize() {
        new InventoryDisplay(0);
    }

    @Test
    public void testCreateAddsActors() {
        // The actors should be added in the create method
        assertTrue(stage.getActors().size > 0);

        Table table = getTable(inventoryDisplay);
        assertTrue(stage.getActors().contains(table, true));
    }

    @Test
    public void testUpdateDisplayWithItems() {
        // Create an entity for the item
        Entity itemEntity = new Entity();

        // Create the ItemComponent
        ItemComponent itemComponent = new ItemComponent("Beef", ItemType.BEEF, 1);
        itemEntity.addComponent(itemComponent);

        // Create the IngredientComponent
        IngredientComponent ingredientComponent = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10, 5, 5, "Raw");
        itemEntity.addComponent(ingredientComponent);

        // Mock the texture for the item
        Texture itemTexture = Mockito.mock(Texture.class);
        ResourceService resourceService = ServiceLocator.getResourceService();
        Mockito.when(resourceService.getAsset("images/ingredients/raw_cucumber.png", Texture.class)).thenReturn(itemTexture);

        // Add the item to the inventory
        InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
        inventory.addItem(itemComponent);

        // Trigger the update event
        entity.getEvents().trigger("updateInventory");

        // Now check that the slots have been updated
        List<Stack> slots = getSlots(inventoryDisplay);

        // For slot 0, it should have more than one child (the slot image and the item image)
        Stack slot0 = slots.get(0);
        assertEquals(2, slot0.getChildren().size);

        // For the other slots, they should only have the slot image
        for (int i = 1; i < slots.size(); i++) {
            Stack slot = slots.get(i);
            assertEquals(1, slot.getChildren().size);
        }
    }

    @Test
    public void testUpdateDisplayAfterRemovingItem() {
        // Create an entity for the item
        Entity itemEntity = new Entity();

        // Create the ItemComponent
        ItemComponent itemComponent = new ItemComponent("Beef", ItemType.BEEF, 1);
        itemEntity.addComponent(itemComponent);

        // Create the IngredientComponent
        IngredientComponent ingredientComponent = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10, 5, 5, "Raw");
        itemEntity.addComponent(ingredientComponent);

        // Mock the texture for the item
        Texture itemTexture = Mockito.mock(Texture.class);
        ResourceService resourceService = ServiceLocator.getResourceService();
        Mockito.when(resourceService.getAsset("images/ingredients/raw_cucumber.png", Texture.class)).thenReturn(itemTexture);

        // Add the item to the inventory
        InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
        inventory.addItem(itemComponent);
        entity.getEvents().trigger("updateInventory");

        // Now remove the item
        inventory.removeItemName("Beef");
        entity.getEvents().trigger("updateInventory");

        // Now check that the slot is back to having only the slot image
        List<Stack> slots = getSlots(inventoryDisplay);
        Stack slot0 = slots.get(0);
        assertEquals(1, slot0.getChildren().size);
    }

    @Test
    public void testDispose() {
        inventoryDisplay.dispose();
        Table table = getTable(inventoryDisplay);

        // We can test if the table is removed from the stage
        assertFalse(stage.getActors().contains(table, true));

        // Also check that the slots have been removed from the stage
        List<Stack> slots = getSlots(inventoryDisplay);
        for (Stack slot : slots) {
            assertFalse(stage.getActors().contains(slot, true));
        }
    }

    @After
    public void tearDown() {
        ServiceLocator.clear();
        Gdx.graphics = null;
        Gdx.gl20 = null;
        Gdx.gl = null;
    }

    @SuppressWarnings("unchecked")
    private List<Stack> getSlots(InventoryDisplay inventoryDisplay) {
        try {
            Field slotsField = InventoryDisplay.class.getDeclaredField("slots");
            slotsField.setAccessible(true);
            return (List<Stack>) slotsField.get(inventoryDisplay);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Table getTable(InventoryDisplay inventoryDisplay) {
        try {
            Field tableField = InventoryDisplay.class.getDeclaredField("table");
            tableField.setAccessible(true);
            return (Table) tableField.get(inventoryDisplay);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
