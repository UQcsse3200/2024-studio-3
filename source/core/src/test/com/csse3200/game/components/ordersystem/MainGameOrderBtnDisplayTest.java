package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.components.maingame.MainGameExitDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.DocketService;
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

    @Test
    void shouldCreateMainGameOrderBtnDisplayComponent() {
        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
        MainGameOrderBtnDisplay createOrderBtn = new MainGameOrderBtnDisplay();

        Entity entity = new Entity();
        entity.addComponent(createOrderBtn);
        entity.create();

        assertEquals(entity, createOrderBtn.getEntity());
    }

    @Test
    void shouldDisposeMainGameOrderBtnDisplayComponent() {
        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);

        MainGameOrderBtnDisplay createOrderBtn = new MainGameOrderBtnDisplay();

        Entity entity = new Entity();
        entity.addComponent(createOrderBtn);
        entity.create();

        assertTrue(createOrderBtn.table.hasChildren());
        createOrderBtn.dispose();
        assertFalse(createOrderBtn.table.hasChildren());
    }

}
