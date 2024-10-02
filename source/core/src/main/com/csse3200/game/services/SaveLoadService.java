package com.csse3200.game.services;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.files.GameState;
import com.csse3200.game.files.FileLoader.Location;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.components.CombatStatsComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.HashMap;
import com.csse3200.game.events.EventHandler;

public class SaveLoadService {
    private static final Logger logger = LoggerFactory.getLogger(SaveLoadService.class);
    private final EventHandler eventHandler;
    private static final String ROOT_DIR = "saves";
    private String saveFile = "saveFile.json";
    public boolean created = false;
    public CombatStatsComponent combatStatsComponent;

    public SaveLoadService() {
        this.eventHandler = new EventHandler();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            logger.warn("HELLO");
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
            this.created = true;
            this.load();
        });
    }

    /**
     * Saves the current state of the game into a GameState and writes to the path given
     */
    public void save() {
        GameState state = new GameState();
        Entity player = ServiceLocator.getPlayerService().getPlayer();
        state.setMoney(player.getComponent(CombatStatsComponent.class).getGold());
        state.setDay(ServiceLocator.getDayNightService().getDay());
        state.setPosition(player.getPosition());
        FileLoader.writeClass(state, (ROOT_DIR + File.separator + saveFile), Location.LOCAL);
        ServiceLocator.getEntityService().getEvents().trigger("togglePause");
    }

    /**
     * Loads a gamestate given the file for said gamestate
     * @param file - the string which is the name of the file
     */
    public void load() {
        GameState state = FileLoader.readClass(GameState.class, ROOT_DIR + File.separator + saveFile, Location.LOCAL);
        UpdateStats(state);
    }

    public void UpdateStats(GameState state) {
        logger.warn("CRAZY");
        this.combatStatsComponent.setGold(state.getMoney());
        ServiceLocator.getDayNightService().setDay(state.getDay());
        logger.warn(ServiceLocator.getPlayerService().getPlayer().getPosition().toString());
        logger.warn(state.getPosition().toString());
        ServiceLocator.getPlayerService().getPlayer().setPosition(state.getPosition());
        logger.warn(ServiceLocator.getPlayerService().getPlayer().getPosition().toString());
    }

    public String getSaveFile() {
        return saveFile;
    }

    public EventHandler getEvents() {
        return eventHandler;
    }

    public void setSaveFile(String filename) {
        saveFile = filename;
    }
}