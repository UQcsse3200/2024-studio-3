package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.maingame.MainGameActions;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.configs.BaseCustomerConfig;
import com.csse3200.game.entities.configs.CustomerPersonalityConfig;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the MainGameOrderTicketDisplay class.
 */
@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class OrderManagerTest {
    @Mock RenderService renderService;
    @Spy OrthographicCamera camera;
    @Mock Stage stage;
    @Mock Viewport viewport;
    @Mock DocketService docketService;
    @Mock PlayerService playerService;
    @Mock EventHandler eventHandler;
    @Mock EventHandler eventHandler2;
    @Mock EventHandler eventHandler3;
    @Mock ResourceService resourceService;
    @Mock Texture textureMock;
    MainGameOrderTicketDisplay orderTicketDisplay;
    @Mock CombatStatsComponent combatStatsComponent;

    @Mock OrderManager orderManager;
    @Mock EntityService entityService;

    private static final Logger logger = LoggerFactory.getLogger(MainGameOrderTicketDisplayTest.class);

    /**
     * Sets up the environment before each test by initializing services and MainGameOrderTicketDisplay instance
     */

    @BeforeEach
    void setup(){
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerPlayerService(playerService);
        ServiceLocator.registerEntityService(entityService);
        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
        when(ServiceLocator.getRenderService().getStage().getViewport()).thenReturn(viewport);
        lenient().when(ServiceLocator.getRenderService().getStage().getViewport().getCamera()).thenReturn(camera);
        resourceService = mock(ResourceService.class);
        ResourceService mockResourceService = mock(ResourceService.class);
        CustomerPersonalityConfig mockCustomerPersonalityConfig = new CustomerPersonalityConfig();
        BaseCustomerConfig mockBaseCustomerConfig = new BaseCustomerConfig();

    }

    @Test
    void testTriggerCall(){
        CustomerPersonalityConfig mockConfigs = new CustomerPersonalityConfig();
        mockConfigs.name = "Hank";
        mockConfigs.type = "Gorilla";
        mockConfigs.countDown = 20;
        mockConfigs.preference = "bananaSplit";
        mockConfigs.reputation = 100;


        CustomerComponent components = new CustomerComponent(mockConfigs);
        Entity customer = new Entity();
        customer.addComponent(components);

//        OrderManager orderManager1 = new OrderManager();
//        MainGameOrderTicketDisplay mainOrder= new MainGameOrderTicketDisplay();
//
//        OrderManager.displayOrder(customer);

    }
}
