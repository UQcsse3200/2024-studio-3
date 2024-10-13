package com.csse3200.game.ui.terminal.commands;

import com.csse3200.game.GdxGame;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CutsceneCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(CutsceneCommand.class);

    /**
     * Toggles debug mode on or off if the corresponding argument is received.
     * @param args command arguments
     */
    public boolean action(ArrayList<String> args) {
        if (!isValid(args)) {
            logger.debug("Invalid arguments received for 'debug' command: {}", args);
            return false;
        }

        String arg = args.get(0);
        switch (arg) {
            case "Day2":
                ServiceLocator.getGame().setScreen(GdxGame.ScreenType.CUTSCENE, GdxGame.CutsceneType.DAY_2);
                //ServiceLocator.getGame().setScreen(GdxGame.ScreenType.CUTSCENE, GdxGame.CutsceneType.MORAL_1);
                return true;
            default:
                logger.debug("Unrecognised argument received for 'debug' command: {}", args);
                return false;
        }
    }

    /**
     * Validates the command arguments.
     * @param args command arguments
     * @return is valid
     */
    boolean isValid(ArrayList<String> args) {
        return args.size() == 1;
    }
}