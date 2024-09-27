package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.GridPoint2;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.areas.ForestGameArea;

import com.csse3200.game.areas.terrain.TerrainFactory;

public class GoodEnd extends ForestGameArea {
    private TerrainFactory terrainFactory;

    /**
     * Initialise this ForestGameArea to use the provided TerrainFactory.
     *
     * @param terrainFactory TerrainFactory used to create the terrain for the GameArea.
     * @requires terrainFactory != null
     */
    public GoodEnd(TerrainFactory terrainFactory) {
        super(terrainFactory);
        this.terrainFactory = terrainFactory;
    }

    private static final String[] forestTextures = {
            "images/ingredients/raw_beef.png",
            "images/ingredients/cooked_beef.png",
            "images/ingredients/burnt_beef.png"
    };

    public void trigger() {
        create();
        // list of other functions that'll ultimately have the cutscene working
    }
    @Override
    public void create() {

        spawnBeef("cooked");
    }

    private void spawnBeef(String cookedLevel) {
        Entity newBeef = ItemFactory.createBeef(cookedLevel);
        spawnEntityAt(newBeef, new GridPoint2(3, 3), true, true);
        newBeef.setScale(0.5f,0.5f);
    }

}
