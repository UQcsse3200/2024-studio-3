package com.csse3200.game.ui.terminal.commands;

import com.csse3200.game.components.moral.MoralDecision;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MoralCommands implements Command{
    private static final Logger logger = LoggerFactory.getLogger(MoralCommands.class);

    /**
     * Toggles debug mode on or off if the corresponding argument is received.
     * @param args command arguments
     */
    public boolean action(ArrayList<String> args) {
        if (!isValid(args)) {
            logger.debug("Invalid arguments received for 'addQuestions' command: {}", args);
            return false;
        }

        String question = args.getFirst();
        boolean isGood = Boolean.parseBoolean(args.get(1));
        int points = Integer.parseInt(args.get(2));
        Entity moralScreen = ServiceLocator.getEntityService().getMoralScreen();
        moralScreen.getComponent(MoralDecision.class).addQuestion(question, isGood, points);

        logger.debug("Added question: {} as {} for {} points", question, isGood?"good":"bad", points);
        return true;
    }
    /**
     * Validates the command arguments.
     * @param args command arguments
     * @return is valid
     */
    boolean isValid(ArrayList<String> args) {
        return args.size() == 3;
    }
}
