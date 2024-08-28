package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Graphics;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class MainGameOrderTicketDisplayTest {
    @Mock RenderService renderService;
    @Spy OrthographicCamera camera;
    @Mock Stage stage;
    @Mock Viewport viewport;
    @Mock DocketService docketService;
    @Mock EventHandler eventHandler;
    MainGameOrderTicketDisplay orderTicketDisplay;

    @BeforeEach
    void setUp() {
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerDocketService(docketService);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerDocketService(docketService);

        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
        when(ServiceLocator.getRenderService().getStage().getViewport()).thenReturn(viewport);
        lenient().when(ServiceLocator.getRenderService().getStage().getViewport().getCamera()).thenReturn(camera);
        when(ServiceLocator.getDocketService().getEvents()).thenReturn(eventHandler);

        orderTicketDisplay = new MainGameOrderTicketDisplay();
        Entity entity = new Entity();
        entity.addComponent(orderTicketDisplay);
        orderTicketDisplay.create();
    }

    @AfterEach
    void tearDown() {
        orderTicketDisplay.getTableArrayList().clear();
    }

    @Test
    void shouldAddTablesToArrayAndRemove() {
        orderTicketDisplay.addActors();
        orderTicketDisplay.addActors();

        assertEquals(2, (orderTicketDisplay.getTableArrayList()).size());
        orderTicketDisplay.getTableArrayList().clear();
        assertEquals(0, (orderTicketDisplay.getTableArrayList()).size());
    }

    @Test
    void testEnlargementOfLastDocket() {
        for (int i = 0; i < 5; i++) {
            orderTicketDisplay.addActors();
        }
        orderTicketDisplay.updateDocketSizes();
        Table lastTable = MainGameOrderTicketDisplay.getTableArrayList().get(MainGameOrderTicketDisplay.getTableArrayList().size() - 1);
        assertEquals(170f, lastTable.getWidth(), 0.1f);
        assertEquals(200f, lastTable.getHeight(), 0.1f);

        float expectedX = orderTicketDisplay.getViewportWidth() - 260f;
        float expectedY = orderTicketDisplay.getViewportHeight() * orderTicketDisplay.getViewPortHeightMultiplier() - 50;

        assertEquals(expectedX, lastTable.getX(), 0.1f);
        assertEquals(expectedY, lastTable.getY(), 0.1f);

    }

    @Test
    void testEnlargementOfSingleDocket() {
        orderTicketDisplay.addActors();
        orderTicketDisplay.updateDocketSizes();
        Table singleTable = MainGameOrderTicketDisplay.getTableArrayList().get(0);

        assertEquals(170f, singleTable.getWidth(), 0.1f, "Docket width is incorrect.");
        assertEquals(200f, singleTable.getHeight(), 0.1f, "Docket height is incorrect.");

        float expectedX = orderTicketDisplay.getViewportWidth() - 260f;
        float expectedY = orderTicketDisplay.getViewportHeight() * orderTicketDisplay.getViewPortHeightMultiplier() - 50;

        assertEquals(expectedX, singleTable.getX(), 0.1f, "Docket X position is incorrect.");
        assertEquals(expectedY, singleTable.getY(), 0.1f, "Docket Y position is incorrect.");
    }

    @Test
    void testNotEnlargedDocketSizes() {
        for (int i = 0; i < 5; i++) {
            orderTicketDisplay.addActors();
        }

        orderTicketDisplay.updateDocketSizes();
        for (int i = 0; i < orderTicketDisplay.getTableArrayList().size() - 1; i++) {
            Table table = orderTicketDisplay.getTableArrayList().get(i);
            assertEquals(120f, table.getWidth(), 0.1f);
            assertEquals(150f, table.getHeight(), 0.1f);
        }
    }
}


