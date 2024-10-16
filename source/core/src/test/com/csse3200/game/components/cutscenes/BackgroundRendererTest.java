package com.csse3200.game.components.cutscenes;

import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class BackgroundRendererTest {
    @Test
    public void checkLayer() {
        ServiceLocator.clear();
        ResourceService resourceService = mock(ResourceService.class);
        ServiceLocator.registerResourceService(resourceService);
        BackgroundRenderComponent backgroundRenderComponent = new BackgroundRenderComponent("Texture");
        assertEquals(1, backgroundRenderComponent.getLayer());
    }
}
