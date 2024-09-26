package com.csse3200.game.components.cutscenes;

import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CutsceneArea extends GameArea {
    private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);

    private Cutscene currentCutscene;

    // The index of the cutscene, should be a part of the enum.
    private int cutsceneValue;

    public CutsceneArea(int cutsceneValue) {
        super();
        ServiceLocator.registerGameArea(this);
        this.cutsceneValue = cutsceneValue;

    }

    @Override
    public void create() {
        switch (cutsceneValue) {
            case 0:
                logger.debug("Loading intro cutscene");
                System.out.println("Loading in intro cutscene");
                currentCutscene = new IntroCutscene();
                break;
            default:
                logger.error("Invalid cutscene value: {}", cutsceneValue);
                return;
        }

        Entity cutsceneEntity = new Entity();
        cutsceneEntity.addComponent(currentCutscene);
        ServiceLocator.getEntityService().register(cutsceneEntity);
        currentCutscene.start();
    }

    public void update(float delta) {
        if (cutsceneCompleted()) {
            // Trigger transition to next gameplay area
            logger.debug("Cutscene is done");

        }
    }

    private boolean cutsceneCompleted() {
        // Use a listener to update this value
        return currentCutscene != null && currentCutscene.isCompleted();
    }

    public void dispose() {
        if (currentCutscene != null) {
            currentCutscene.dispose();
        }
        super.dispose();
    }
}
