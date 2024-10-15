package com.csse3200.game.components.ordersystem;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.input.InputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
class OrderActionsTest {
    private OrderActions orderActions;
    private DocketService docketService;

    @BeforeEach
    void setUp() {
        docketService = new DocketService();
        ServiceLocator.clear();
        ServiceLocator.registerDocketService(docketService);
        ServiceLocator.registerInputService(new InputService());
        Entity entity = new Entity();
        entity.addComponent(new OrderActions());
        orderActions = entity.getComponent(OrderActions.class);
    }

    @Test
    void testKeyDown_ShiftLeftKey() {
        EventListener0 eventListener = mock(EventListener0.class);
        docketService.getEvents().addListener("shiftDocketsLeft", eventListener);

        boolean result = orderActions.keyDown(com.badlogic.gdx.Input.Keys.LEFT_BRACKET);
        assertTrue(result);
        verify(eventListener).handle();
    }

    @Test
    void testKeyDown_ShiftRightKey() {
        EventListener0 eventListener = mock(EventListener0.class);
        docketService.getEvents().addListener("shiftDocketsRight", eventListener);

        boolean result = orderActions.keyDown(com.badlogic.gdx.Input.Keys.RIGHT_BRACKET);
        assertTrue(result);
        verify(eventListener).handle();
    }

    @Test
    void testKeyDown_UnhandledKey() {
        boolean result = orderActions.keyDown(com.badlogic.gdx.Input.Keys.A);
        assertFalse(result, "The keyDown method should return false for an unhandled key.");
    }

    @Test
    void testKeyUp() {
        boolean result = orderActions.keyUp(com.badlogic.gdx.Input.Keys.A);
        assertFalse(result, "The keyUp method should return false as it does nothing.");
    }

    @Test
    void testKeyTyped() {
        boolean result = orderActions.keyTyped('A');
        assertFalse(result, "The keyTyped method should return false as it does nothing.");
    }

    @Test
    void testLogger() {
        Logger logger = orderActions.getLogger();
        assertNotNull(logger, "Logger should be initialized.");
    }
}
