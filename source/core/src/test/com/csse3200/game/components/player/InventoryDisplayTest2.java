package com.csse3200.game.components.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Field;
import java.util.ArrayList;

@ExtendWith(GameExtension.class)
public class InventoryDisplayTest2 {

    private ResourceService resourceService;
    private Stage stage;

    @BeforeEach
    public void setUp() {
        // Initialize Gdx application with mocked objects
        Gdx.app = mock(Application.class);
        Gdx.gl = mock(GL20.class);
        Gdx.input = mock(Input.class);
        Gdx.graphics = mock(Graphics.class);

        // Mock the ResourceService
        resourceService = mock(ResourceService.class);
        ServiceLocator.registerResourceService(resourceService);

        // Mock getAsset to return a mock Texture
        when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(mock(Texture.class));

        // Create a real Stage
        stage = mock(Stage.class);
    }

    @AfterEach
    public void tearDown() {
        // Clean up
        ServiceLocator.clear();
        stage.dispose();
    }

    @Test
    public void testDefaultConstructor() {
        InventoryDisplay display = new InventoryDisplay();
        assertEquals(200, display.getSlotSize());
    }

    @Test
    public void testCustomConstructor() {
        InventoryDisplay display = new InventoryDisplay(100);
        assertEquals(100, display.getSlotSize());
    }

    @Test
    public void testInvalidSlotSize() {
        assertThrows(IllegalArgumentException.class, () -> new InventoryDisplay(0));
        assertThrows(IllegalArgumentException.class, () -> new InventoryDisplay(-5));
    }

    // @Test
    // public void testCreate() throws Exception {
    //     // Create an entity with InventoryComponent
    //     Entity entity = new Entity();
    //     InventoryComponent inventory = new InventoryComponent(5);
    //     entity.addComponent(inventory);

    //     // Create an InventoryDisplay and add it to the entity
    //     InventoryDisplay display = new InventoryDisplay();
    //     entity.addComponent(display);

    //     // Set the stage
    //     display.setStage(stage);

    //     // Call create
    //     display.create();

    //     // Verify that the stage has the table actor added
    //     assertEquals(1, stage.getActors().size);

    //     // Use reflection to access private fields
    //     Field tableField = InventoryDisplay.class.getDeclaredField("table");
    //     tableField.setAccessible(true);
    //     assertNotNull(tableField.get(display));

    //     Field slotsField = InventoryDisplay.class.getDeclaredField("slots");
    //     slotsField.setAccessible(true);
    //     ArrayList<Stack> slots = (ArrayList<Stack>) slotsField.get(display);
    //     assertEquals(5, slots.size());
    // }

    // @Test
    // public void testUpdateDisplay_WithItems() throws Exception {
    //     // Create an entity with InventoryComponent
    //     Entity playerEntity = new Entity();
    //     InventoryComponent inventory = new InventoryComponent(5);
    //     playerEntity.addComponent(inventory);

    //     // Add some items to the inventory
    //     IngredientComponent item1 = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10, 5, 5, "Fresh");
    //     IngredientComponent item2 = new IngredientComponent("Banana", ItemType.BANANA, 10, 5, 5, "Fresh");
    //     Entity itemEntity1 = new Entity();
    //     Entity itemEntity2 = new Entity();
    //     inventory.addItem(item1);
    //     inventory.addItem(item2);
    //     itemEntity1.addComponent(item1);
    //     itemEntity2.addComponent(item2);

    //     // Create an InventoryDisplay and add it to the entity
    //     InventoryDisplay display = new InventoryDisplay();
    //     playerEntity.addComponent(display);

    //     // Set the stage
    //     display.setStage(stage);

    //     // Call create
    //     playerEntity.create();
    //     itemEntity1.create();
    //     itemEntity2.create();

    //     // Mock specific texture paths for items
    //     when(resourceService.getAsset("images/ingredients/fresh_cucumber.png", Texture.class)).thenReturn(mock(Texture.class));
    //     when(resourceService.getAsset("images/ingredients/fresh_banana.png", Texture.class)).thenReturn(mock(Texture.class));

    //     // Trigger the update event
    //     display.update();

    //     // Use reflection to access the private slots field
    //     Field slotsField = InventoryDisplay.class.getDeclaredField("slots");
    //     slotsField.setAccessible(true);
    //     @SuppressWarnings("unchecked")
    //     ArrayList<Stack> slots = (ArrayList<Stack>) slotsField.get(display);

    //     // Verify the slots have the correct number of children (background + item image)
    //     assertEquals(2, slots.get(0).getChildren().size); // Slot with item
    //     assertEquals(2, slots.get(1).getChildren().size); // Slot with item
    //     assertEquals(1, slots.get(2).getChildren().size); // Empty slot
    // }

    // @Test
    // public void testDispose() throws Exception {
    //     // Create an entity with InventoryComponent
    //     Entity entity = new Entity();
    //     InventoryComponent inventory = new InventoryComponent(5);
    //     entity.addComponent(inventory);

    //     // Create an InventoryDisplay and add it to the entity
    //     InventoryDisplay display = new InventoryDisplay();
    //     entity.addComponent(display);

    //     // Set the stage
    //     display.setStage(stage);

    //     // Call create
    //     display.create();

    //     // Now, call dispose
    //     display.dispose();

    //     // Verify that the table is removed from the stage
    //     assertEquals(0, stage.getActors().size);

    //     // Use reflection to access the slots and verify they are removed
    //     Field slotsField = InventoryDisplay.class.getDeclaredField("slots");
    //     slotsField.setAccessible(true);
    //     ArrayList<Stack> slots = (ArrayList<Stack>) slotsField.get(display);

    //     for (Stack slot : slots) {
    //         assertNull(slot.getParent());
    //     }
    // }
}
