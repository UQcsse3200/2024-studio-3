package com.csse3200.game.components.cutscenes;

import com.csse3200.game.GdxGame;
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
    private GdxGame.CutsceneType cutsceneValue;

    /**
     * Constructor for the CutsceneArea. It registers the game area as well as sets the value for the cutscene to
     * load
     *
     * @param cutsceneValue An integer representing the specific cutscene to load.
     */
    public CutsceneArea(GdxGame.CutsceneType cutsceneValue) {
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
            case GdxGame.CutsceneType.BACK_STORY:
                logger.debug("Loading backstory cutscene");
                currentCutscene = new BackstoryCutscene();  // Initialize the intro cutscene
                ServiceLocator.setCurrentCutscene(currentCutscene);  // Set the current cutscene in the service locator
                break;

            case GdxGame.CutsceneType.MORAL_1:
                logger.debug("Loading Day 1 Moral cutscene");
                currentCutscene = new MoralDay1Cutscene();  // Initialize the intro cutscene
                ServiceLocator.setCurrentCutscene(currentCutscene);  // Set the current cutscene in the service locator
                break;


            case GdxGame.CutsceneType.DAY_2:
                logger.debug("Loading Day 2 cutscene");
                currentCutscene = new Day2Cutscene();  // Initialize the intro cutscene
                ServiceLocator.setCurrentCutscene(currentCutscene);  // Set the current cutscene in the service locator
                break;
            case GdxGame.CutsceneType.DAY_3:
                logger.debug("Loading Day 4 cutscene");
                currentCutscene = new Day3Cutscene();
                ServiceLocator.setCurrentCutscene(currentCutscene);
                break;
            case GdxGame.CutsceneType.DAY_4:
                logger.debug("Loading Day 3 cutscene");
                currentCutscene = new Day4Cutscene();
                ServiceLocator.setCurrentCutscene(currentCutscene);
                break;
            case GdxGame.CutsceneType.GOOD_END:
                logger.debug("Loading good end cutscene");
                currentCutscene = new GoodEndCutscene();  // Initialize the good end cutscene
                ServiceLocator.setCurrentCutscene(currentCutscene);  // Set the current cutscene in the service locator
                break;
            case GdxGame.CutsceneType.BAD_END:
                logger.debug("Loading bad end cutscene");
                currentCutscene = new BadEndCutscene();
                ServiceLocator.setCurrentCutscene(currentCutscene);

                break;
            case GdxGame.CutsceneType.LOSE:
                logger.debug("Loading lose end cutscene");
                currentCutscene = new LoseCutscene();
                ServiceLocator.setCurrentCutscene(currentCutscene);
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
}