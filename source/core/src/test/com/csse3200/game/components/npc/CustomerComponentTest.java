package com.csse3200.game.components.npc;

import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.configs.BaseCustomerConfig;
import com.csse3200.game.entities.configs.CustomerPersonalityConfig;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerComponentTest {
    @BeforeEach
    void setup(){
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
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
