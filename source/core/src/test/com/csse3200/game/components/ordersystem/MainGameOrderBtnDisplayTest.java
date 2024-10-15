package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class MainGameOrderBtnDisplayTest {
    
    @Mock RenderService renderService;
    @Mock Stage stage;
    @Mock DocketService docketService;
    MainGameOrderBtnDisplay createOrderBtn;

    @BeforeEach
    void setUp() {
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerDocketService(docketService);
        createOrderBtn = new MainGameOrderBtnDisplay();
        createOrderBtn.setStage(stage);
    }

    @Test
    void testButtonCreation() {
        MainGameOrderBtnDisplay mockBtn = mock(MainGameOrderBtnDisplay.class);
        mockBtn.setStage(stage);
        mockBtn.addActors();
        verify(mockBtn).addActors();
    }

    @Test
    void shouldCreateMainGameOrderBtnDisplayComponent() {
        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);

        Entity entity = new Entity();
        entity.addComponent(createOrderBtn);
        entity.create();

        assertEquals(entity, createOrderBtn.getEntity());
    }

    @Test
    void shouldDisposeMainGameOrderBtnDisplayComponent() {
        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);

        Entity entity = new Entity();
        entity.addComponent(createOrderBtn);
        entity.create();

        assertTrue(createOrderBtn.getTable().hasChildren());
        createOrderBtn.dispose();
        assertFalse(createOrderBtn.getTable().hasChildren());
    }

    @Test
    void dummyTestDraw() {
        SpriteBatch batch = mock(SpriteBatch.class);
        createOrderBtn.draw(batch);
        //Don't do anything as the function doesn't do anything
    }

    @Test
    void testGetZIndex() {
        assertEquals(2f, createOrderBtn.getZIndex());
    }

    @Test
    void testGetAndSetState() {
        createOrderBtn.setState(true);
        assertTrue(createOrderBtn.getState());

        createOrderBtn.setState(false);
        assertFalse(createOrderBtn.getState());
    }
}
