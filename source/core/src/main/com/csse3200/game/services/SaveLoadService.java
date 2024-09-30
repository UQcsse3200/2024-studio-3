package com.csse3200.game.services;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.files.GameState;
import com.csse3200.game.files.FileLoader.Location;
import com.csse3200.game.files.FileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.CombatStatsComponent;
import java.io.File;
import java.util.HashMap;

public class SaveLoadService {
    private static final Logger logger = LoggerFactory.getLogger(SaveLoadService.class);
    private static final String ROOT_DIR = "saves";
    private static final String SAVE_FILE = "saveFile.json";
    private CombatStatsComponent combatStatsComponent;

    public SaveLoadService() {
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
    }

    /**
     * Saves the current state of the game into a GameState and writes to the path given
     */
    public void save() {
        GameState state = new GameState();
        state.setMoney(75);
        state.setDay(2);
        FileLoader.writeClass(state, (ROOT_DIR + File.separator + SAVE_FILE), Location.LOCAL);
        ServiceLocator.getEntityService().getEvents().trigger("togglePause");
    }

    /**
     * Loads a gamestate given the file for said gamestate
     * @param file - the string which is the name of the file
     */
    public void load(String file) {
        GameState state = FileLoader.readClass(GameState.class, ROOT_DIR + File.separator + file, Location.LOCAL);
        UpdateStats(state);
        ServiceLocator.getEntityService().getEvents().trigger("togglePause");
    }

    public void UpdateStats(GameState state) {
        this.combatStatsComponent.setGold(state.getMoney());
    }
}