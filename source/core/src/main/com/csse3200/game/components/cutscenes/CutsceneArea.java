package com.csse3200.game.components.cutscenes;

import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages a cutscene area in the game. Handles the creation, updating,
 * and disposal of cutscenes, and ensures that transitions occur once cutscenes are completed.
 */
public class CutsceneArea extends GameArea {
    private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);

    // The current cutscene being played in the area
    private Cutscene currentCutscene;

    // Index representing which cutscene to load (could be part of an enum in the future)
    private int cutsceneValue;

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
        switch (cutsceneValue) {
            case 0:
                logger.debug("Loading intro cutscene");
                currentCutscene = new IntroCutscene();  // Initialize the intro cutscene
                ServiceLocator.setCurrentCutscene(currentCutscene);  // Set the current cutscene in the service locator
                break;
            case 1:
                logger.debug("Loading good end cutscene");
                currentCutscene = new GoodEndCutscene();  // Initialize the good end cutscene
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
     * Updates the cutscene area each frame. Transitions the game once the cutscene is completed.
     *
     * @param delta Time since the last frame in seconds
     */
    public void update(float delta) {
        // Check if the cutscene has completed
        if (cutsceneCompleted()) {
            logger.debug("Cutscene is done");  // Log that the cutscene is completed
            // Additional logic to trigger the transition to the next gameplay area could be added here
        }
    }

    /**
     * Checks if the current cutscene has been completed.
     *
     * @return true if the cutscene is completed, false otherwise.
     */
    private boolean cutsceneCompleted() {
        return currentCutscene != null && currentCutscene.isCompleted();
    }
}