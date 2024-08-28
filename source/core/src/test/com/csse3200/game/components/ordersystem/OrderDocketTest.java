package com.csse3200.game.components.ordersystem;

import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.GdxGame;
import com.badlogic.gdx.Input;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.input.InputService;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(GameExtension.class)
class OrderActionsTest {

    private OrderActions orderActions;
    private DocketService docketService;

    @BeforeEach
    void setUp() {
        // Set up necessary services
        docketService = new DocketService();
        ServiceLocator.clear();
        ServiceLocator.registerDocketService(new DocketService());
        ServiceLocator.registerInputService(new InputService());


        orderActions = new OrderActions(null);
        orderActions.create(); // Simulate the creation phase where listeners are registered
    }

    @Test
    void testKeyDown_ShiftLeftKey() {
        // Simulate pressing the left bracket key
        boolean result = orderActions.keyDown(Input.Keys.LEFT_BRACKET);
        assertTrue(result, "The keyDown method should return true for the left bracket key.");
        // Assuming there's a way to verify the "shiftDocketsLeft" event was triggered
    }

    @Test
    void testKeyDown_ShiftRightKey() {
        // Simulate pressing the right bracket key
        boolean result = orderActions.keyDown(Input.Keys.RIGHT_BRACKET);
        assertTrue(result, "The keyDown method should return true for the right bracket key.");
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