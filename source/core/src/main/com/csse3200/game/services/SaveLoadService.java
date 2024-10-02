package com.csse3200.game.services;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.files.GameState;
import com.csse3200.game.files.FileLoader.Location;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.moral.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.HashMap;
import com.csse3200.game.events.EventHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveLoadService {
    private static final Logger logger = LoggerFactory.getLogger(SaveLoadService.class);
    private final EventHandler eventHandler;
    private static final String ROOT_DIR = "saves";
    private String saveFile = "begin.json";
    public CombatStatsComponent combatStatsComponent;

    public SaveLoadService() {
        this.eventHandler = new EventHandler();
    }

    /**
     * Saves the current state of the game into a GameState and writes to the path given
     */
    public void save() {
        GameState state = new GameState();
        Entity player = ServiceLocator.getPlayerService().getPlayer();
        state.setMoney(player.getComponent(CombatStatsComponent.class).getGold());
        state.setDay(ServiceLocator.getDayNightService().getDay());
        state.setDecisions(ServiceLocator.getEntityService().getMoralSystem().getComponent(MoralDecision.class).getListOfDecisions());
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String formattedDateTime = currentDateTime.format(formatter);
        state.setModTime(formattedDateTime);
        FileLoader.writeClass(state, (ROOT_DIR + File.separator + saveFile), Location.LOCAL);
        ServiceLocator.getEntityService().getEvents().trigger("togglePause");
    }

    /**
     * Loads a gamestate given the file for said gamestate
     * @param file - the string which is the name of the file
     */
    public void load() {
        GameState state = FileLoader.readClass(GameState.class, ROOT_DIR + File.separator + saveFile, Location.LOCAL);

        if (state != null) { // if the file exists
            this.combatStatsComponent.setGold(state.getMoney());
            ServiceLocator.getDayNightService().setDay(state.getDay());
            MoralDecision system = ServiceLocator.getEntityService().getMoralSystem().getComponent(MoralDecision.class);
            for (Decision decision: system.getListOfDecisions()) {
                system.addDecision(decision);
            }
        }

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