package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.RenderFactory;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.input.InputService;
import com.csse3200.game.physics.PhysicsEngine;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.rendering.Renderer;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class MainGameOrderBtnDisplayTest {
    
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
/*
    @Test
    void shouldCreateMainGameOrderBtnDisplayComponent() {
        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
        when(ServiceLocator.getRenderService().getStage().getViewport()).thenReturn(viewport);
        when(ServiceLocator.getRenderService().getStage().getViewport().getCamera()).thenReturn(camera);
        when(ServiceLocator.getDocketService().getEvents()).thenReturn(eventHandler);
        MainGameOrderBtnDisplay createOrderBtn = new MainGameOrderBtnDisplay();

        Entity entity = new Entity();
        entity.addComponent(createOrderBtn);
        entity.create();

        assertEquals(entity, createOrderBtn.getEntity());
    }*/
}
