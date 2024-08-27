package com.csse3200.game.rendering;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.services.DocketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderActionsTest {
    private OrderActions orderActions;
    private GdxGame mockGame;
    private DocketService mockDocketService;
    private Logger mockLogger;

    @BeforeEach
    void setUp() {
        mockGame = mock(GdxGame.class);
        mockDocketService = mock(DocketService.class);
        mockLogger = mock(Logger.class);



        orderActions = new OrderActions(mockGame) {
            @Override
            protected Logger getLogger() {
                return mockLogger;
            }
        };

        // Initialize the component
        orderActions.create();
    }

    @Test
    void testOnAddOrder() {
        // Simulate triggering the "addOrder" event
        orderActions.getEntity().getEvents().trigger("addOrder");

        // Verify logger info call
        verify(mockLogger).info("Add order");
        // Add additional verification if needed
    }

    @Test
    void testOnRemoveOrder() {
        // Simulate triggering the "removeOrder" event
        int index = 1;
        orderActions.getEntity().getEvents().trigger("removeOrder", index);

        // Verify logger info call
        verify(mockLogger).info("Remove order");

        // Verify that reorderDockets is triggered with the correct index
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockDocketService.getEvents()).trigger("reorderDockets", argumentCaptor.capture());
        assertEquals(index, argumentCaptor.getValue());
    }

    @Test
    void testOnMoveOrder() {
        // Simulate triggering the "moveOrder" event
        orderActions.getEntity().getEvents().trigger("moveOrder");

        // Verify logger info call
        verify(mockLogger).info("Move order");
        // Add additional verification if needed
    }

    @Test
    void testOnChangeColour() {
        // Simulate triggering the "changeColour" event
        orderActions.getEntity().getEvents().trigger("changeColour");

        // Verify logger info call
        verify(mockLogger).info("Change colour");
        // Add additional verification if needed
    }
}




