package com.csse3200.game.components.cutscenes;

import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class IntroCutscene extends Cutscene {

    public IntroCutscene() {
        super();
    }

    @Override
    protected void setupScenes() {
        cutsceneText.add("First bit of text");
        cutsceneText.add("Second bit of text");
        cutsceneText.add("Third bit of text");

        scenes.add(new Scene(
                "images/Cutscenes/Beastly_Bistro_Background.png",
                new String[]{"images/player/Cook_Model32.png"},
                cutsceneText, 3.0f));

        scenes.add(new Scene(
                "images/Cutscenes/Graveyard_Scene.png",
                new String[]{"images/player/Cook_Model32.png"},
                cutsceneText, 4.0f));

        scenes.add(new Scene(
                "images/Cutscenes/Beastly_Bistro_Background.png",
                new String[]{"images/player/Cook_Model32.png"},
                cutsceneText, 2.0f));
    }

    @Override
    protected void loadAssets() {
        textures = new String[] {
                "images/Cutscenes/Beastly_Bistro_Background.png",
                "images/Cutscenes/Graveyard_Scene.png"
        };
        animations = new String[] {"images/player/Cook_Model32.png"};
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(animations);
        resourceService.loadAll();
    }

    @Override
    public void createEntities() {
        // Any specific entity creation logic for the cutscene
    }
}


