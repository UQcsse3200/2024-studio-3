package com.csse3200.game.components.npc;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.components.npc.PersonalCustomerEnums;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.configs.BaseCustomerConfig;
import com.csse3200.game.entities.configs.CustomerPersonalityConfig;
import com.csse3200.game.entities.configs.NPCConfigs;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.entities.factories.NPCFactory;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.physics.components.PhysicsMovementComponent;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

public class CustomerComponentTest {
    @BeforeEach
    void setup(){
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        ResourceService mockResourceService = mock(ResourceService.class);
        CustomerPersonalityConfig mockCustomerPersonalityConfig = new CustomerPersonalityConfig();
        BaseCustomerConfig mockBaseCustomerConfig = new BaseCustomerConfig();
    }

    @Test
    void testPersonalCustomerComponents(){
        CustomerPersonalityConfig mockConfigs = new CustomerPersonalityConfig();
        mockConfigs.name = "Hank";
        mockConfigs.type = "Gorilla";
        mockConfigs.countDown = 20;
        mockConfigs.preference = "bananaSplit";
        mockConfigs.reputation = 100;

        CustomerComponent components = new CustomerComponent(mockConfigs);
        assertEquals("Hank", components.getName());
        assertEquals(20, components.getCountDown());
        assertEquals("bananaSplit", components.getPreference());
        assertEquals(100, components.getReputation());
        assertEquals("Gorilla", components.getType());
    }

    @Test
    void testBasicCustomerComponents(){
        BaseCustomerConfig basicMockConfigs = new BaseCustomerConfig();
        basicMockConfigs.type = "Chicken";
        basicMockConfigs.countDown = 20;
        basicMockConfigs.preference = "salad";

        CustomerComponent components = new CustomerComponent(basicMockConfigs);
        assertEquals(20, components.getCountDown());
        assertEquals("salad", components.getPreference());
        assertEquals("Chicken", components.getType());
    }
}
