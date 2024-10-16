package com.csse3200.game.entities.factories;


import com.badlogic.gdx.files.FileHandle;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.components.CameraComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.configs.CustomerPersonalityConfig;
import com.csse3200.game.entities.configs.NPCConfigs;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.physics.components.PhysicsMovementComponent;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files;

import org.mockito.Spy;


public class NPCFactoryCustomerTests {
    static String[] configPaths = {
            "configs/NPC.json"
    };
    @BeforeEach
    void setUp() {
        Gdx.files = mock(Files.class);
        CameraComponent cc = new CameraComponent();
        TerrainFactory tf = new TerrainFactory(cc);
        ForestGameArea ga = mock(ForestGameArea.class);
        ServiceLocator.registerGameArea(ga);
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(null);
        ServiceLocator.registerResourceService(mockResourceService);

        FileHandle mockFileHandle = mock(FileHandle.class); // Mock the expected return type
        NPCConfigs mockConfigs = new NPCConfigs();
        mockConfigs.Hank = new CustomerPersonalityConfig();
        mockConfigs.Hank.name = "Hank";
        mockConfigs.Hank.countDown = 20;
        mockConfigs.Hank.preference = "bananaSplit";
        mockConfigs.Hank.reputation = 100;
        mockConfigs.Hank.texture = "images/animal_images/gorilla.atlas";

        }
    @Spy
    NPCFactory testNPCFactory;
    @Spy
    NPCConfigs testNpcConfigs;
    void baseComponentsAssertion(Entity e) {
        assertNotNull(e.getComponent(PhysicsComponent.class));
        assertNotNull(e.getComponent(PhysicsMovementComponent.class));
        assertNotNull(e.getComponent(ColliderComponent.class));
        assertNotNull(e.getComponent(HitboxComponent.class));
    }

//    @Test
//    void createHank() {
//        Vector2 target = new Vector2(5,3);
//        Entity personalCustomer = testNPCFactory.createCustomerPersonal("Hank", target);
//        assertEquals("Hank", PersonalCustomerEnums.HANK);
//        baseComponentsAssertion(personalCustomer);
//    }
}
