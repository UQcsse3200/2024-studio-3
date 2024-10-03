package com.csse3200.game.components.cutscenes;

import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages a cutscene area in the game. Handles the creation, updating,
 * and disposal of cutscenes, and ensures that transitions occur once cutscenes are completed.
 */
public class CutsceneArea extends GameArea {
    private static final Logger logger = LoggerFactory.getLogger(CutsceneArea.class);

    // Index representing which cutscene to load (could be part of an enum in the future)
    private final int cutsceneValue;
    private Cutscene currentCutscene;

    /**
     * Constructor for the CutsceneArea. It registers the game area as well as sets the value for the cutscene to
     * load
     *
     * @param cutsceneValue An integer representing the specific cutscene to load.
     */
    public CutsceneArea(int cutsceneValue) {
        super();
        ServiceLocator.registerGameArea(this);  // Register this cutscene area in the service locator
        this.cutsceneValue = cutsceneValue;
    }

    /**
     * Creates the cutscene based on the provided cutsceneValue and starts the cutscene.
     */
    @Override
    public void create() {
        // The current cutscene being played in the area
        switch (cutsceneValue) {
            case 0:
                logger.debug("Loading backstory cutscene");
                currentCutscene = new BackstoryCutscene();  // Initialize the intro cutscene
                ServiceLocator.setCurrentCutscene(currentCutscene);  // Set the current cutscene in the service locator
                break;
            case 1:
                logger.debug("Loading Day 2 cutscene");
                currentCutscene = new Day2Cutscene();  // Initialize the intro cutscene
                ServiceLocator.setCurrentCutscene(currentCutscene);  // Set the current cutscene in the service locator
                break;
            default:
                logger.error("Invalid cutscene value: {}", cutsceneValue);  // Log an error if the cutscene value is invalid
                return;
        }

        // Add the cutscene entity to the service locator to be used later
        Entity cutsceneEntity = new Entity();
        cutsceneEntity.addComponent(currentCutscene);
        ServiceLocator.getEntityService().register(cutsceneEntity);

        // Start the cutscene
        currentCutscene.start();
    }

    /**
     * Returns the cutscene value of the cutscene area
     * @return The cutscene value
     */
    public int getCutsceneValue() {
        return cutsceneValue;
    }

    /**
     * Returns the current cutscene in the cutscene area
     * @return The current cutscene
     */
    public Cutscene getCurrentCutscene() {
        return currentCutscene;
    }
}