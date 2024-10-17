package com.csse3200.game.components.cutscenes;

import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.entities.Entity;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotNull;

public class Day2CutsceneTest {

    private Day2Cutscene day2Cutscene;
    private ResourceService resourceService;
    private Entity entity;

    @Before
    public void setUp() {
        resourceService = mock(ResourceService.class);
        entity = mock(Entity.class);

        ServiceLocator.registerResourceService(resourceService);

        day2Cutscene = new Day2Cutscene();
        day2Cutscene.setEntity(entity);
    }

    @Test
    public void testSetupScenes() throws Exception {
        Method setupScenesMethod = Day2Cutscene.class.getDeclaredMethod("setupScenes");
        setupScenesMethod.setAccessible(true);
        setupScenesMethod.invoke(day2Cutscene);

        assertNotNull(day2Cutscene);
    }

    @Test
    public void testCreateEntities() {
        day2Cutscene.createEntities();
        assertNotNull(day2Cutscene);
    }

    @Test
    public void testStart() {
        day2Cutscene.start();
        assertNotNull(day2Cutscene);
    }

    @Test
    public void testDispose() {
        day2Cutscene.dispose();
        verify(resourceService, times(1)).unloadAssets(any(String[].class));
    }
}
