package com.csse3200.game.components.station;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.components.items.ChopIngredientComponent;
import com.csse3200.game.components.items.CookIngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.physics.components.PhysicsComponent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;

/**
 * Unit tests for the StationProgressDisplay component.
 */
@ExtendWith(GameExtension.class)
public class StationProgressDisplayTest {

    private static final float TEST_X_OFFSET = 0.0f;
    private static final float TEST_Y_OFFSET = 0.05f;
    private static final float TEST_BAR_MAX_WIDTH = 1.0f;
    private static final float TEST_BAR_HEIGHT = 0.2f;

    private RenderService renderService;
    private SpriteBatch spriteBatch;
    private StationProgressDisplay progressDisplay;
    private Entity entity;
    private EventHandler eventHandler;
    private PhysicsComponent physicsComponent;
    private StationItemHandlerComponent itemHandlerComponent;

    @BeforeEach
    public void setUp() {
        // Initialize HeadlessApplication to mock LibGDX environment
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {}
            @Override
            public void resize(int width, int height) {}
            @Override
            public void render() {}
            @Override
            public void pause() {}
            @Override
            public void resume() {}
            @Override
            public void dispose() {}
        }, config);

        // Mock Gdx environment
        Gdx.app = mock(Application.class);
        Gdx.gl = mock(GL20.class);
        Gdx.input = mock(Input.class);
        Gdx.graphics = mock(Graphics.class);

        // Mock RenderService and register it
        renderService = mock(RenderService.class);
        ServiceLocator.registerRenderService(renderService);

        // Mock SpriteBatch
        spriteBatch = mock(SpriteBatch.class);

        // Mock EventHandler
        eventHandler = mock(EventHandler.class);

        // Create a new Entity and add necessary components
        entity = spy(new Entity());
        doReturn(eventHandler).when(entity).getEvents();
        physicsComponent = mock(PhysicsComponent.class);
        when(physicsComponent.getBody()).thenReturn(mock(Body.class));
        when(physicsComponent.getBody().getPosition()).thenReturn(new Vector2(100, 200));
        entity.addComponent(physicsComponent);

        itemHandlerComponent = mock(StationItemHandlerComponent.class);
        entity.addComponent(itemHandlerComponent);

        // Initialize StationProgressDisplay and set its entity
        progressDisplay = new StationProgressDisplay();
        entity.addComponent(progressDisplay);
        progressDisplay.create();
    }

    @AfterEach
    public void tearDown() {
        ServiceLocator.clear();
    }

    /**
     * Tests that the StationProgressDisplay is correctly initialized.
     */
    @Test
    public void testCreate() throws NoSuchFieldException, IllegalAccessException {
        // Verify that RenderService.register was called
        verify(renderService).register(progressDisplay);

        // Access private fields to verify textures are loaded
        Field barOutlineField = StationProgressDisplay.class.getDeclaredField("barOutline");
        Field barFillField = StationProgressDisplay.class.getDeclaredField("barFill");
        Field positionField = StationProgressDisplay.class.getDeclaredField("position");
        Field scaleField = StationProgressDisplay.class.getDeclaredField("scale");
        Field barPercentageField = StationProgressDisplay.class.getDeclaredField("barPercentage");
        Field displayBarField = StationProgressDisplay.class.getDeclaredField("displayBar");

        barOutlineField.setAccessible(true);
        barFillField.setAccessible(true);
        positionField.setAccessible(true);
        scaleField.setAccessible(true);
        barPercentageField.setAccessible(true);
        displayBarField.setAccessible(true);

        Texture barOutline = (Texture) barOutlineField.get(progressDisplay);
        Texture barFill = (Texture) barFillField.get(progressDisplay);
        Vector2 position = (Vector2) positionField.get(progressDisplay);
        Vector2 scale = (Vector2) scaleField.get(progressDisplay);
        float barPercentage = barPercentageField.getFloat(progressDisplay);
        boolean displayBar = displayBarField.getBoolean(progressDisplay);

        assertNotNull(barOutline, "barOutline texture should be loaded");
        assertNotNull(barFill, "barFill texture should be loaded");
        assertNotNull(position, "position should be initialized");
        assertNotNull(scale, "scale should be initialized");
        assertEquals(0.0f, barPercentage, "Initial barPercentage should be 0.0f");
        assertFalse(displayBar, "Initial displayBar should be false");
    }

    /**
     * Tests the update method when there is no item in the station.
     */
    @Test
    public void testUpdate_NoItem() throws NoSuchFieldException, IllegalAccessException {
        // Arrange: No item in the station
        when(itemHandlerComponent.peek()).thenReturn(null);

        // Access private fields
        Field barPercentageField = StationProgressDisplay.class.getDeclaredField("barPercentage");
        Field displayBarField = StationProgressDisplay.class.getDeclaredField("displayBar");

        barPercentageField.setAccessible(true);
        displayBarField.setAccessible(true);

        // Act
        progressDisplay.update();

        // Assert
        float barPercentage = barPercentageField.getFloat(progressDisplay);
        boolean displayBar = displayBarField.getBoolean(progressDisplay);

        assertEquals(0.0f, barPercentage, "barPercentage should be reset to 0.0f");
        assertFalse(displayBar, "displayBar should be false when there is no item");

        // Verify that updateInventory event is not triggered
        verify(eventHandler, never()).trigger(eq("updateInventory"));
    }

    /**
     * Tests the update method with an item that has a ChopIngredientComponent and is incomplete.
     */
    @Test
    public void testUpdate_WithChopIngredientComponent_Incomplete() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        ItemComponent item = mock(ItemComponent.class);
        ChopIngredientComponent chopComponent = mock(ChopIngredientComponent.class);
        Entity itemEntity = mock(Entity.class);
        when(itemHandlerComponent.peek()).thenReturn(item);
        when(item.getEntity()).thenReturn(itemEntity);
        when(itemEntity.getComponent(ChopIngredientComponent.class)).thenReturn(chopComponent);
        when(chopComponent.getCompletionPercent()).thenReturn(50.0f);

        // Access private fields
        Field barPercentageField = StationProgressDisplay.class.getDeclaredField("barPercentage");
        Field displayBarField = StationProgressDisplay.class.getDeclaredField("displayBar");

        barPercentageField.setAccessible(true);
        displayBarField.setAccessible(true);

        // Act
        progressDisplay.update();

        // Assert
        float barPercentage = barPercentageField.getFloat(progressDisplay);
        boolean displayBar = displayBarField.getBoolean(progressDisplay);

        assertEquals(0.5f, barPercentage, 0.001f, "barPercentage should be 0.5f");
        assertTrue(displayBar, "displayBar should be true when processing is incomplete");

        // Verify that the "updateInventory" event is NOT triggered since the process is not yet complete
        verify(eventHandler, never()).trigger(eq("updateInventory"));
    }

    /**
     * Tests the update method with an item that has a ChopIngredientComponent and is complete.
     */
    @Test
    public void testUpdate_WithChopIngredientComponent_Complete() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        ItemComponent item = mock(ItemComponent.class);
        ChopIngredientComponent chopComponent = mock(ChopIngredientComponent.class);
        Entity itemEntity = mock(Entity.class);
        when(itemHandlerComponent.peek()).thenReturn(item);
        when(item.getEntity()).thenReturn(itemEntity);
        when(itemEntity.getComponent(ChopIngredientComponent.class)).thenReturn(chopComponent);
        when(chopComponent.getCompletionPercent()).thenReturn(100.0f);

        // Access private fields
        Field barPercentageField = StationProgressDisplay.class.getDeclaredField("barPercentage");
        Field displayBarField = StationProgressDisplay.class.getDeclaredField("displayBar");

        barPercentageField.setAccessible(true);
        displayBarField.setAccessible(true);

        // Act
        progressDisplay.update();

        // Assert
        float barPercentage = barPercentageField.getFloat(progressDisplay);
        boolean displayBar = displayBarField.getBoolean(progressDisplay);

        assertEquals(1.0f, barPercentage, 0.001f, "barPercentage should be 1.0f");
        assertFalse(displayBar, "displayBar should be false when processing is complete");

        // Verify that the updateInventory event is triggered once
        verify(eventHandler, times(1)).trigger(eq("updateInventory"));
    }

    /**
     * Tests the update method with an item that has a CookIngredientComponent and is incomplete.
     */
    @Test
    public void testUpdate_WithCookIngredientComponent_Incomplete() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        ItemComponent item = mock(ItemComponent.class);
        CookIngredientComponent cookComponent = mock(CookIngredientComponent.class);
        Entity itemEntity = mock(Entity.class);
        when(itemHandlerComponent.peek()).thenReturn(item);
        when(item.getEntity()).thenReturn(itemEntity);
        when(itemEntity.getComponent(CookIngredientComponent.class)).thenReturn(cookComponent);
        when(cookComponent.getCompletionPercent()).thenReturn(75.0f);

        // Access private fields
        Field barPercentageField = StationProgressDisplay.class.getDeclaredField("barPercentage");
        Field displayBarField = StationProgressDisplay.class.getDeclaredField("displayBar");

        barPercentageField.setAccessible(true);
        displayBarField.setAccessible(true);

        // Act
        progressDisplay.update();

        // Assert
        float barPercentage = barPercentageField.getFloat(progressDisplay);
        boolean displayBar = displayBarField.getBoolean(progressDisplay);

        assertEquals(0.75f, barPercentage, 0.001f, "barPercentage should be 0.75f");
        assertTrue(displayBar, "displayBar should be true when cooking is incomplete");

        // Verify that the "updateInventory" event is NOT triggered since the process is not yet complete
        verify(eventHandler, never()).trigger(eq("updateInventory"));
    }

    /**
     * Tests the update method with an item that has a CookIngredientComponent and is complete but still cooking.
     */
    @Test
    public void testUpdate_WithCookIngredientComponent_CompleteCooking() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        ItemComponent item = mock(ItemComponent.class);
        CookIngredientComponent cookComponent = mock(CookIngredientComponent.class);
        Entity itemEntity = mock(Entity.class);
        when(itemHandlerComponent.peek()).thenReturn(item);
        when(item.getEntity()).thenReturn(itemEntity);
        when(itemEntity.getComponent(CookIngredientComponent.class)).thenReturn(cookComponent);
        when(cookComponent.getCompletionPercent()).thenReturn(100.0f);
        when(cookComponent.getIsCooking()).thenReturn(true);

        // Access private fields
        Field barPercentageField = StationProgressDisplay.class.getDeclaredField("barPercentage");
        Field displayBarField = StationProgressDisplay.class.getDeclaredField("displayBar");

        barPercentageField.setAccessible(true);
        displayBarField.setAccessible(true);

        // Act
        progressDisplay.update();

        // Assert
        float barPercentage = barPercentageField.getFloat(progressDisplay);
        boolean displayBar = displayBarField.getBoolean(progressDisplay);

        assertEquals(1.0f, barPercentage, 0.001f, "barPercentage should be 1.0f");
        assertTrue(displayBar, "displayBar should be true when cooking is complete but still cooking");

        // Verify that updateInventory event is not triggered
        verify(eventHandler, never()).trigger(eq("updateInventory"));
    }

    /**
     * Tests the draw method when the progress bar should be displayed.
     */
    @Test
    public void testDraw_DisplayBar() throws NoSuchFieldException, IllegalAccessException {
        // Arrange: Mock the Textures and inject them into progressDisplay
        Texture mockBarOutline = mock(Texture.class);
        Texture mockBarFill = mock(Texture.class);

        // Use reflection to access and set the private barOutline and barFill fields
        Field barOutlineField = StationProgressDisplay.class.getDeclaredField("barOutline");
        Field barFillField = StationProgressDisplay.class.getDeclaredField("barFill");

        barOutlineField.setAccessible(true);
        barFillField.setAccessible(true);

        barOutlineField.set(progressDisplay, mockBarOutline);
        barFillField.set(progressDisplay, mockBarFill);

        // Arrange: Set displayBar to true and set barPercentage
        Field displayBarField = StationProgressDisplay.class.getDeclaredField("displayBar");
        Field barPercentageField = StationProgressDisplay.class.getDeclaredField("barPercentage");
        Field positionField = StationProgressDisplay.class.getDeclaredField("position");
        Field scaleField = StationProgressDisplay.class.getDeclaredField("scale");

        displayBarField.setAccessible(true);
        barPercentageField.setAccessible(true);
        positionField.setAccessible(true);
        scaleField.setAccessible(true);

        displayBarField.setBoolean(progressDisplay, true);
        barPercentageField.setFloat(progressDisplay, 0.5f);
        positionField.set(progressDisplay, new Vector2(100, 200));
        scaleField.set(progressDisplay, new Vector2(1, 1));

        // Act
        progressDisplay.draw(spriteBatch);

        // Assert: Verify that SpriteBatch.draw was called with the correct parameters for barOutline and barFill
        verify(spriteBatch).draw(eq(mockBarOutline),
                eq(100.0f + TEST_X_OFFSET),
                eq(200.0f + TEST_Y_OFFSET),
                eq(TEST_BAR_MAX_WIDTH),
                eq(TEST_BAR_HEIGHT));

        verify(spriteBatch).draw(eq(mockBarFill),
                eq(100.0f + TEST_X_OFFSET),
                eq(200.0f + TEST_Y_OFFSET),
                eq(TEST_BAR_MAX_WIDTH * 0.5f), // barPercentage = 0.5f
                eq(TEST_BAR_HEIGHT));
    }

    /**
     * Tests the draw method when the progress bar should not be displayed.
     */
    @Test
    public void testDraw_NoDisplayBar() throws NoSuchFieldException, IllegalAccessException {
        // Arrange: Set displayBar to false
        Field displayBarField = StationProgressDisplay.class.getDeclaredField("displayBar");
        displayBarField.setAccessible(true);
        displayBarField.setBoolean(progressDisplay, false);

        // Act
        progressDisplay.draw(spriteBatch);

        // Assert
        // Verify that draw is never called since displayBar is false
        verify(spriteBatch, never()).draw(any(Texture.class), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    /**
     * Tests the dispose method to ensure textures are disposed and the component is unregistered.
     */
    @Test
    public void testDispose() throws NoSuchFieldException, IllegalAccessException {
        // Arrange: Ensure textures are loaded
        Field barOutlineField = StationProgressDisplay.class.getDeclaredField("barOutline");
        Field barFillField = StationProgressDisplay.class.getDeclaredField("barFill");

        barOutlineField.setAccessible(true);
        barFillField.setAccessible(true);

        Texture barOutline = mock(Texture.class);
        Texture barFill = mock(Texture.class);

        barOutlineField.set(progressDisplay, barOutline);
        barFillField.set(progressDisplay, barFill);

        // Act
        progressDisplay.dispose();

        // Assert
        // Verify that textures are disposed
        verify(barOutline).dispose();
        verify(barFill).dispose();

        // Verify that RenderService.unregister was called
        verify(renderService).unregister(progressDisplay);
    }

    /**
     * Tests that getLayer returns the correct layer value.
     */
    @Test
    public void testGetLayer() {
        // Act
        int layer = progressDisplay.getLayer();

        // Assert
        assertEquals(3, layer, "getLayer should return 3");
    }

    /**
     * Tests that setStage does not perform any actions.
     */
    @Test
    public void testSetStage() {
        // Arrange
        Stage mockStage = mock(Stage.class);

        // Act & Assert: No exception should be thrown
        assertDoesNotThrow(() -> progressDisplay.setStage(mockStage));
    }

    /**
     * Tests the update method when the item has neither ChopIngredientComponent nor CookIngredientComponent.
     */
    @Test
    public void testUpdate_ItemWithoutRelevantComponents() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        ItemComponent item = mock(ItemComponent.class);
        when(itemHandlerComponent.peek()).thenReturn(item);
        Entity itemEntity = mock(Entity.class);
        when(item.getEntity()).thenReturn(itemEntity);
        when(itemEntity.getComponent(ChopIngredientComponent.class)).thenReturn(null);
        when(itemEntity.getComponent(CookIngredientComponent.class)).thenReturn(null);

        // Access private fields
        Field barPercentageField = StationProgressDisplay.class.getDeclaredField("barPercentage");
        Field displayBarField = StationProgressDisplay.class.getDeclaredField("displayBar");

        barPercentageField.setAccessible(true);
        displayBarField.setAccessible(true);

        // Act
        progressDisplay.update();

        // Assert
        float barPercentage = barPercentageField.getFloat(progressDisplay);
        boolean displayBar = displayBarField.getBoolean(progressDisplay);

        assertEquals(0.0f, barPercentage, "barPercentage should be reset to 0.0f");
        assertFalse(displayBar, "displayBar should be false when item has no relevant components");

        // Verify that updateInventory event is not triggered
        verify(eventHandler, never()).trigger("updateInventory");
    }
}
