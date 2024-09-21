package com.csse3200.game.components.cutscenes;

import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public abstract class Cutscene extends Component {
    protected String[] textures;
    protected String[] sounds;
    protected String[] music;
    protected List<Entity> entities = new ArrayList<>();

    public Cutscene() {
        loadAssets();
    }

    @Override
    public abstract void update();

    protected abstract void loadAssets();
    protected abstract void createEntities();

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
}

