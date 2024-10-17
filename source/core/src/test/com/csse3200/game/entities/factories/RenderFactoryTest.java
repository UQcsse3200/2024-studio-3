package com.csse3200.game.entities.factories;

import com.csse3200.game.components.CameraComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.rendering.RenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RenderFactoryTest {

    @BeforeEach
    public void setUp() {
        // Clear ServiceLocator and register necessary services before each test
        ServiceLocator.clear();
        ServiceLocator.registerRenderService(new RenderService());
    }

    @Test
    public void testCreateCamera() {
        // Test that createCamera creates an Entity with a CameraComponent
        Entity cameraEntity = RenderFactory.createCamera();
        assertNotNull(cameraEntity, "Camera entity should not be null");
        assertNotNull(cameraEntity.getComponent(CameraComponent.class), 
                     "Camera entity should have a CameraComponent");
    }

    @Test
    public void testPrivateConstructor() {
        // Test that instantiating RenderFactory throws an IllegalStateException
        try {
            var constructor = RenderFactory.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
            fail("Expected IllegalStateException to be thrown");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof IllegalStateException, 
                      "Expected cause to be IllegalStateException");
            assertEquals("Instantiating static util class", e.getCause().getMessage(), 
                         "Expected specific exception message");
        }
    }
}
