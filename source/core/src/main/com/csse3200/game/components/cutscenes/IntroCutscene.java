package com.csse3200.game.components.cutscenes;

import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntroCutscene extends Cutscene {
    private static final Logger logger = LoggerFactory.getLogger(IntroCutscene.class);
    float timeStart = 0;
    float duration = 2.0f;
    private GameTime gameTime;

    public IntroCutscene() {
        gameTime = ServiceLocator.getTimeSource();
        timeStart = gameTime.getTime();

        // Create the script
        this.cutsceneText.add("First bit of text");
        this.cutsceneText.add("Second bit of text");
        this.cutsceneText.add("Third bit of text");

        this.setScript();
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void update() {
        // Check if the cutscene has finished based on time
        float currentTime = gameTime.getTime();
        if ((currentTime - timeStart) > duration) {
            logger.debug("Cutscene finished. Triggering next level/cutscene.");
            entity.getEvents().trigger("cutsceneEnded");
        }
    }

    @Override
    protected void nextCutscene() {
        // Should move to the next cutscene, but since it is not available
        entity.getEvents().trigger("cutsceneEnded");
    }

    @Override
    protected void loadAssets() {
        textures = new String[] {"images/Cutscenes/Beastly_Bistro_Background.png"};
//        sounds = new String[] {"sounds/intro_music.mp3"};
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
//        resourceService.loadSounds(sounds);
//        resourceService.loadMusic(music);
        resourceService.loadAll();
    }

    @Override
    protected void createEntities() {
        // Create the background entity
        Entity background = CutsceneFactory.createBackground("images/Cutscenes/Beastly_Bistro_Background.png");

        entities.add(background);
        ServiceLocator.getEntityService().register(background);
    }
}

