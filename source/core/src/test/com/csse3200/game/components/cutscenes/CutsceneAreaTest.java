package com.csse3200.game.components.cutscenes;

import com.csse3200.game.entities.EntityService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class CutsceneAreaTest {

    @Before
    public void setUp() {
        ResourceService mockResourceService = mock(ResourceService.class);
        EntityService mockEntityService = mock(EntityService.class);
        ServiceLocator.clear();
        ServiceLocator.registerResourceService(mockResourceService);
        ServiceLocator.registerEntityService(mockEntityService);
    }

    @Test
    public void testInitialisation() {
        CutsceneArea cutsceneArea = new CutsceneArea(0);
        assert cutsceneArea.getCutsceneValue() == 0;
    }

    @Test
    public void testCreateCutsceneArea() {
        // Create a day 2 cutscene
        CutsceneArea cutsceneArea = new CutsceneArea(1);
        cutsceneArea.create();
        // Check it is a day 2 cutscene.
        assert cutsceneArea.getCurrentCutscene().getClass() == Day2Cutscene.class;

        // Create a non existent cutscene
        cutsceneArea = new CutsceneArea(-1);
        cutsceneArea.create();
        // Check it is a day 2 cutscene.
        assert cutsceneArea.getCurrentCutscene() == null;
    }
}
