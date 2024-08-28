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
import com.csse3200.game.ui.UIComponent;
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

    @BeforeEach
    void setUp() {
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerDocketService(docketService);
    }

    @Test
    void shouldAddTablesToArrayAndRemove() {
        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
        when(ServiceLocator.getRenderService().getStage().getViewport()).thenReturn(viewport);
        when(ServiceLocator.getRenderService().getStage().getViewport().getCamera()).thenReturn(camera);
        when(ServiceLocator.getDocketService().getEvents()).thenReturn(eventHandler);
        MainGameOrderTicketDisplay orderTicketDisplay = new MainGameOrderTicketDisplay();
        Entity entity = new Entity();
        entity.addComponent(orderTicketDisplay);
        orderTicketDisplay.create();
        orderTicketDisplay.addActors();
        orderTicketDisplay.addActors();

        assertEquals(2, (orderTicketDisplay.getTableArrayList()).size());
        orderTicketDisplay.getTableArrayList().clear();
        assertEquals(0, (orderTicketDisplay.getTableArrayList()).size());
    }
}


