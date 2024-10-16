package com.csse3200.game.entities.factories;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsEngine;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.physics.components.PhysicsComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class CustomerTests {

    private PhysicsService physicsService;
    private PhysicsComponent physicsComponent;

    private PhysicsEngine physicsEngine;

    @BeforeEach
    void setup(){
        physicsService = mock(PhysicsService.class);
        physicsEngine = mock(PhysicsEngine.class);
        physicsComponent = mock(PhysicsComponent.class);

    }

    @Test
    void hankConfigTest(){
//        CustomerPersonalityConfig customerPersonalityConfig = new CustomerPersonalityConfig();
//        customerPersonalityConfig.name = "Hank";
//
//        NPCConfigs npcConfigs = new NPCConfigs();
//        npcConfigs.Hank.name = "Hank";
//        npcConfigs.Hank.type = "Gorilla";
//        npcConfigs.Hank.Customer_id = 10000;
//
//
//        npcConfigs.Hank.texture = "images/animal_images/gorilla.atlas";
//
//        customer = NPCFactory.createCustomerPersonal("Hank", new Vector2(1,3));
//        assert(Objects.equals(customer.getComponent(CustomerComponent.class).getName(), "Hank"));


    }




}
