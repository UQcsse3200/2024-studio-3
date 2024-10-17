package com.csse3200.game.components.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.util.ArrayList;

@ExtendWith(GameExtension.class)
class InventoryDisplayTest {

    private ResourceService resourceService;
    private Stage stage;
    private RenderService renderService;

    @BeforeEach
    void setUp() {
        // Headless configuration
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {
                //Used for testing
            }
            @Override
            public void resize(int width, int height) {}
            @Override
            public void render() {
                //Used for testing
                }
            @Override
            public void pause() {}
            @Override
            public void resume() {
                //Used for testing
            }
            @Override
            public void dispose() {
                //Used for testing
            }
        }, config);

        // Initialize Gdx application with mocked objects
        Gdx.app = mock(Application.class);
        Gdx.gl = mock(GL20.class);
        Gdx.input = mock(Input.class);
        Gdx.graphics = mock(Graphics.class);

        // Mock the ResourceService
        resourceService = mock(ResourceService.class);
        ServiceLocator.registerResourceService(resourceService);

        // Mock the RenderService
        renderService = mock(RenderService.class);
        ServiceLocator.registerRenderService(renderService);

        // Mock getAsset to return a mock Texture
        when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(mock(Texture.class));

        // Create a mock Stage
        stage = mock(Stage.class);

        // Mock getStage to return a mock Stage
        when(renderService.getStage()).thenReturn(stage);
    }

    @AfterEach
    void tearDown() {
        // Clean up
        ServiceLocator.clear();
    }

    @Test
    void testDefaultConstructor() {
        InventoryDisplay display = new InventoryDisplay();
        assertEquals(200, display.getSlotSize());
    }

    @Test
    void testCustomConstructor() {
        InventoryDisplay display = new InventoryDisplay(100);
        assertEquals(100, display.getSlotSize());
    }

    @Test
    void testInvalidSlotSize() {
        assertThrows(IllegalArgumentException.class, () -> new InventoryDisplay(0));
        assertThrows(IllegalArgumentException.class, () -> new InventoryDisplay(-5));
    }

    @Test
    void testCreateWithEmptyInventory() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        int inventoryCapacity = 5;
        Entity entity = new Entity();
        InventoryComponent inventory = new InventoryComponent(inventoryCapacity);
        entity.addComponent(inventory);
    
        InventoryDisplay display = new InventoryDisplay();
        // display.setEntity(entity);
        entity.addComponent(display);
        display.setStage(stage);

        // Act
        entity.create();

        // Assert
        // Verify that addActors() was called, which adds slots to the table
        verify(stage).addActor(any(Table.class));

        // Use reflection to access the private 'slots' field
        Field slotsField = InventoryDisplay.class.getDeclaredField("slots");
        slotsField.setAccessible(true);
        ArrayList<Stack> slots = (ArrayList<Stack>) slotsField.get(display);

        assertNotNull(slots);
        assertEquals(inventoryCapacity, slots.size());

        // Verify that getAsset() was called for each slot (5 times in this case)
        verify(resourceService, times(inventoryCapacity)).getAsset("images/inventory_ui/slot.png", Texture.class);

        // Verify that each slot contains only the slot background image
        for (Stack slot : slots) {
            // Assuming that each slot should have exactly one Image (the slot background)
            assertEquals(1, slot.getChildren().size);
        }
    }

    @Test
    void testCreateWithItems() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        int inventoryCapacity = 3;
        Entity entity = new Entity();
        InventoryComponent inventory = new InventoryComponent(inventoryCapacity);
        entity.addComponent(inventory);

        // Create mock items
        ItemComponent item1 = mock(ItemComponent.class);
        when(item1.getTexturePath()).thenReturn("images/items/item1.png");

        ItemComponent item2 = mock(ItemComponent.class);
        when(item2.getTexturePath()).thenReturn("images/items/item2.png");

        // Add items to inventory
        inventory.addItem(item1);
        inventory.addItem(item2);

        InventoryDisplay display = new InventoryDisplay();
        // Add InventoryDisplay to the entity
        entity.addComponent(display);
        display.setStage(stage);

        // Act
        entity.create();

        // Assert
        // Use reflection to access the private 'slots' field
        Field slotsField = InventoryDisplay.class.getDeclaredField("slots");
        slotsField.setAccessible(true);
        ArrayList<Stack> slots = (ArrayList<Stack>) slotsField.get(display);

        assertNotNull(slots);
        assertEquals(inventoryCapacity, slots.size());

        // Verify that getAsset() was called for each slot (3 times)
        verify(resourceService, times(inventoryCapacity)).getAsset("images/inventory_ui/slot.png", Texture.class);

        // Verify that getAsset() was called for each item's texture
        verify(resourceService).getAsset("images/items/item1.png", Texture.class);
        verify(resourceService).getAsset("images/items/item2.png", Texture.class);

        // Verify that each slot contains the correct number of children
        for (int i = 0; i < inventoryCapacity; i++) {
            Stack slot = slots.get(i);
            if (i < 2) { // First two slots have items
                // Each slot should have two children: slot background and item image
                assertEquals(2, slot.getChildren().size);
            } else { // Third slot is empty
                // Only slot background should be present
                assertEquals(1, slot.getChildren().size);
            }
        }

        // Verify that the stage adds the table
        verify(stage).addActor(any(Table.class));
    }

    @Test
    void testUpdateInventory() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        int inventoryCapacity = 2;
        Entity entity = new Entity();
        InventoryComponent inventory = new InventoryComponent(inventoryCapacity);
        entity.addComponent(inventory);

        // Create initial item
        ItemComponent item1 = mock(ItemComponent.class);
        when(item1.getTexturePath()).thenReturn("images/items/item1.png");
        inventory.addItem(item1);

        InventoryDisplay display = new InventoryDisplay();
        // Add InventoryDisplay to the entity
        entity.addComponent(display);
        display.setStage(stage);

        // Act
        entity.create();

        // Access the slots
        Field slotsField = InventoryDisplay.class.getDeclaredField("slots");
        slotsField.setAccessible(true);
        ArrayList<Stack> slots = (ArrayList<Stack>) slotsField.get(display);

        // Verify initial state
        verify(resourceService, times(inventoryCapacity)).getAsset("images/inventory_ui/slot.png", Texture.class);
        verify(resourceService).getAsset("images/items/item1.png", Texture.class);

        // Act: Add a new item to the inventory
        ItemComponent item2 = mock(ItemComponent.class);
        when(item2.getTexturePath()).thenReturn("images/items/item2.png");
        inventory.addItem(item2);

        // Assert: Verify that the second slot is updated with the new item
        verify(resourceService).getAsset("images/items/item2.png", Texture.class);

        // Verify that getAsset for slot.png was called again 'inventoryCapacity' times during update
        verify(resourceService, times(inventoryCapacity * 2)).getAsset("images/inventory_ui/slot.png", Texture.class);

        // Verify that the second slot now has two children
        Stack secondSlot = slots.get(1);
        assertEquals(2, secondSlot.getChildren().size);

        // Act: Remove the first item
        inventory.removeAt(0);

        // Verify that getAsset for slot.png was called again 'inventoryCapacity' times during update
        verify(resourceService, times(inventoryCapacity * 3)).getAsset("images/inventory_ui/slot.png", Texture.class);

        // Assert: Verify that the first slot is now empty
        Stack firstSlot = slots.get(0);
        assertEquals(1, firstSlot.getChildren().size);

        // Act: Change the texture path of the second item to null
        when(item2.getTexturePath()).thenReturn(null);
        entity.getEvents().trigger("updateInventory");

        // Assert: Verify that null_image.png was attempted to be loaded
        verify(resourceService).getAsset("images/inventory_ui/null_image.png", Texture.class);

        // Verify that getAsset for slot.png was called again 'inventoryCapacity' times during update
        verify(resourceService, times(inventoryCapacity * 4)).getAsset("images/inventory_ui/slot.png", Texture.class);
        
        // Verify that the second slot now has two children (slot background + null image)
        Stack updatedSecondSlot = slots.get(1);
        assertEquals(2, updatedSecondSlot.getChildren().size);
    }
    
    @Test
    void testDispose() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        int inventoryCapacity = 2;
        Entity entity = new Entity();
        InventoryComponent inventory = new InventoryComponent(inventoryCapacity);
        entity.addComponent(inventory);

        // Create items
        ItemComponent item1 = mock(ItemComponent.class);
        when(item1.getTexturePath()).thenReturn("images/items/item1.png");
        inventory.addItem(item1);

        ItemComponent item2 = mock(ItemComponent.class);
        when(item2.getTexturePath()).thenReturn("images/items/item2.png");
        inventory.addItem(item2);

        InventoryDisplay display = new InventoryDisplay();
        // Add InventoryDisplay to the entity
        entity.addComponent(display);
        display.setStage(stage);

        // Act
        entity.create();

        // Capture the Table passed to stage.addActor()
        ArgumentCaptor<Table> tableCaptor = ArgumentCaptor.forClass(Table.class);
        verify(stage).addActor(tableCaptor.capture());
        Table table = tableCaptor.getValue();

        // Spy on the Table to verify remove() is called
        Table spyTable = spy(table);

        // Replace the 'table' field in display with spyTable
        Field tableField = InventoryDisplay.class.getDeclaredField("table");
        tableField.setAccessible(true);
        tableField.set(display, spyTable);

        // Access slots
        Field slotsField = InventoryDisplay.class.getDeclaredField("slots");
        slotsField.setAccessible(true);
        ArrayList<Stack> slots = (ArrayList<Stack>) slotsField.get(display);

        // Spy on each Stack to verify remove() is called
        ArrayList<Stack> spiedSlots = new ArrayList<>();
        for (Stack slot : slots) {
            Stack spySlot = spy(slot);
            spiedSlots.add(spySlot);
        }
        // Replace the 'slots' field with the spied slots
        slotsField.set(display, spiedSlots);

        // Act: Dispose the InventoryDisplay
        display.dispose();

        // Assert
        // Verify that table.remove() was called
        verify(spyTable).remove();

        // Verify that each slot's remove() was called
        for (Stack spySlot : spiedSlots) {
            verify(spySlot).remove();
        }
    }
}
