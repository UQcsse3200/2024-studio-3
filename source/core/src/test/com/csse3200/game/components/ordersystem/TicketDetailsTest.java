package com.csse3200.game.components.ordersystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TicketDetailsTest {

    @Test
    void testGetCurrentBigTicketInfo() {
        TicketDetails ticketDetails = new TicketDetails();
        String[] info = ticketDetails.getCurrentBigTicketInfo();
        for (String s : info) {
            Assertions.assertNull(s);
        }
        ticketDetails.onUpdateBigTicket("ABC", "MEAL", "10S");
        info = ticketDetails.getCurrentBigTicketInfo();
        Assertions.assertEquals("ABC", info[0]);
        Assertions.assertEquals("MEAL", info[1]);
        Assertions.assertEquals("10S", info[2]);
    }
}
