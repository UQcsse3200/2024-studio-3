package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.csse3200.game.GdxGame;
import com.csse3200.game.input.InputService;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.DebugRenderer;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class OrderActionsTest {

    @Mock InputService inputService;
    @Mock DocketService docketService;
    @Mock EventHandler eventHandler;
    @Spy GdxGame game; // Spy can be used if you need to control interactions with GdxGame

    private OrderActions orderActions;

    @BeforeEach
    void setUp() {
        // Clear ServiceLocator before each test
        ServiceLocator.clear();

        // Mock necessary services and register them in ServiceLocator
        ServiceLocator.registerInputService(inputService);
        ServiceLocator.registerDocketService(docketService);

        // Set up the mocked event handler for docketService
        when(ServiceLocator.getDocketService().getEvents()).thenReturn(eventHandler);

        // Create OrderActions with necessary game instance and call create method
        orderActions = new OrderActions(game);
        orderActions.create();  // Simulate creation phase
    }

    @Test
    void testKeyDown_ShiftLeftKey() {
        // Simulate pressing the left bracket key
        boolean result = orderActions.keyDown(Input.Keys.LEFT_BRACKET);
        assertTrue(result, "The keyDown method should return true for the left bracket key.");

        // Verify that the "shiftDocketsLeft" event was triggered
        assertTrue(ServiceLocator.getDocketService().getEvents().hasTriggered("shiftDocketsLeft"),
                "The shiftDocketsLeft event should have been triggered.");
    }

    @Test
    void testKeyDown_ShiftRightKey() {
        // Simulate pressing the right bracket key
        boolean result = orderActions.keyDown(Input.Keys.RIGHT_BRACKET);
        assertTrue(result, "The keyDown method should return true for the right bracket key.");

        // Verify that the "shiftDocketsRight" event was triggered
        assertTrue(ServiceLocator.getDocketService().getEvents().hasTriggered("shiftDocketsRight"),
                "The shiftDocketsRight event should have been triggered.");
    }

    @Test
    void testKeyDown_UnhandledKey() {
        // Simulate pressing an unrelated key (e.g., A key)
        boolean result = orderActions.keyDown(Input.Keys.A);
        assertFalse(result, "The keyDown method should return false for an unhandled key.");
    }

    @Test
    void testKeyUp_ReturnsFalse() {

        boolean result = orderActions.keyUp(Input.Keys.ANY_KEY);
        assertFalse(result, "The keyUp method should always return false.");
    }

    @Test
    void testKeyTyped_ReturnsFalse() {

        boolean result = orderActions.keyTyped('a');
        assertFalse(result, "The keyTyped method should always return false.");
    }

    @Test
    void testOnRemoveOrder() {
        int index = 1;


        orderActions.onRemoveOrder(index);

        // Verify that the reorderDockets event was triggered with the correct index
        assertTrue(ServiceLocator.getDocketService().getEvents().hasTriggered("reorderDockets", index),
                "The reorderDockets event should be triggered with the correct index.");
    }



}
