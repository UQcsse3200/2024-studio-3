package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Cutscene extends Component {
    private static  final Logger logger = LoggerFactory.getLogger(Cutscene.class);
    protected String[] textures;
    protected String[] sounds;
    protected String[] music;
    protected List<Entity> entities = new ArrayList<>();
    protected Array<String> cutsceneText = new Array<String>();

    public Cutscene() {
        loadAssets();
    }

    protected void setScript() {
        ServiceLocator.getCutsceneScreen().getCutsceneScreenDisplay().setCutsceneText(cutsceneText);
    }

    @Override
    public void create() {
        entity.getEvents().addListener("nextCutscene", this::nextCutscene);
    }

    @Override
    public abstract void update();

    protected abstract void loadAssets();
    protected abstract void createEntities();

    protected void nextCutscene(){
        logger.error("Not implemented, should be overridden by child cutscene");
    }

    public void start() {
        createEntities();
    }

    public void dispose() {
        unloadAssets();
    }

    protected void unloadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(textures);
        resourceService.unloadAssets(sounds);
        resourceService.unloadAssets(music);
    }

    public boolean isCompleted() {
        logger.error("Have not implement function: isCompleted()");
        return true;
    }
}

