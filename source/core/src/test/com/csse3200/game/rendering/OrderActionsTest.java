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
/*
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

        orderActions.create();
    }

    @Test
    void testOnAddOrder() {
        // Simulate triggering the "addOrder" event
        orderActions.getEntity().getEvents().trigger("addOrder");

        verify(mockLogger).info("Add order");

    }

    @Test
    void testOnRemoveOrder() {

        int index = 1;
        orderActions.getEntity().getEvents().trigger("removeOrder", index);

        verify(mockLogger).info("Remove order");

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockDocketService.getEvents()).trigger("reorderDockets", argumentCaptor.capture());
        assertEquals(index, argumentCaptor.getValue());
    }

    @Test
    void testOnMoveOrder() {

        orderActions.getEntity().getEvents().trigger("moveOrder");


        verify(mockLogger).info("Move order");

    }

    @Test
    void testOnChangeColour() {

        orderActions.getEntity().getEvents().trigger("changeColour");


        verify(mockLogger).info("Change colour");
       
    }
*/
}




