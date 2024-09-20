package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.GdxGame;
import com.badlogic.gdx.Input;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.input.InputService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(GameExtension.class)
class OrderActionsTest {

    private OrderActions orderActions;
    private DocketService docketService;
    private Entity entity;
    private static final Logger logger = LoggerFactory.getLogger(OrderActionsTest.class);

    @BeforeEach
    void setUp() {
        // Set up necessary services
        docketService = new DocketService();
        ServiceLocator.clear();
        ServiceLocator.registerDocketService(docketService);
        ServiceLocator.registerInputService(new InputService());
        entity = new Entity();
        entity.addComponent(new OrderActions(new GdxGame()));
        orderActions = entity.getComponent(OrderActions.class); // Simulate the creation phase where listeners are registered
    }

    @Test
    void testKeyDown_ShiftLeftKey() {
        // Simulate pressing the left bracket key
        EventListener0 eventListener = mock(EventListener0.class);
        EventListener0 eventListener0 = mock(EventListener0.class);
        docketService.getEvents().addListener("shiftDocketsLeft", eventListener);
        docketService.getEvents().addListener("shiftDocketsRight", eventListener0);
        boolean result = orderActions.keyDown(Input.Keys.LEFT_BRACKET);
        assertTrue(result);
        verify(eventListener).handle();
        verify(eventListener0, times(0)).handle();
        // Assuming there's a way to verify the "shiftDocketsLeft" event was triggered
    }

    @Test
    void testKeyDown_ShiftRightKey() {
        // Simulate pressing the right bracket key
        EventListener0 eventListener = mock(EventListener0.class);
        EventListener0 eventListener0 = mock(EventListener0.class);
        docketService.getEvents().addListener("shiftDocketsLeft", eventListener);
        docketService.getEvents().addListener("shiftDocketsRight", eventListener0);
        boolean result = orderActions.keyDown(Input.Keys.RIGHT_BRACKET);
        assertTrue(result);
        verify(eventListener0).handle();
        verify(eventListener, times(0)).handle();
        // Assuming there's a way to verify the "shiftDocketsRight" event was triggered
    }

    @Test
    void testKeyDown_UnhandledKey() {
        // Simulate pressing an unrelated key (e.g., A key)
        boolean result = orderActions.keyDown(Input.Keys.A);
        assertFalse(result, "The keyDown method should return false for an unhandled key.");
    }
}

//    @Test
//    void testOnAddOrder() {
//        // Manually trigger the "addOrder" event and verify behavior
//        orderActions.onAddOrder();
//        // Assuming you have a way to check that an order was added correctly
//    }
//
//    @Test
//    void testOnRemoveOrder() {
//        // Simulate removing an order by triggering the "removeOrder" event
//        int orderIndex = 1; // Example index
//        orderActions.onRemoveOrder(orderIndex);
//        // Verify that the order was removed, and reorderDockets event was triggered
//    }
//
//    @Test
//    void testOnMoveOrder() {
//        // Simulate moving an order by triggering the "moveOrder" event
//        orderActions.onMoveOrder();
//        // Verify that the appropriate logging or actions were performed
//    }
//
//    @Test
//    void testOnChangeColour() {
//        // Simulate changing color by triggering the "changeColour" event
//        orderActions.onChangeColour();
//        // Verify that the appropriate logging or actions were performed
//    }
//}