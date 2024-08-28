package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MainGameOrderTicketDisplayTest {

    private MainGameOrderTicketDisplay orderTicketDisplay;

    @BeforeEach
    void setUp() {
        orderTicketDisplay = new MainGameOrderTicketDisplay();
        orderTicketDisplay.create();
    }

    @Test
    void docketTableSize() {
       orderTicketDisplay.addActors();
       orderTicketDisplay.addActors();

       assertEquals(2, (orderTicketDisplay.getTableArrayList()).size());
    }
}


