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
     * Adds a question to the moral decision component.
     * @param args command arguments
     */
    public boolean action(ArrayList<String> args) {

        logger.debug("Received command: 'question' with arguments: {}", args);
        if (!isValid(args)) {
            logger.debug("Invalid arguments received for 'addQuestions' command: {}", args);
            return false;
        }

        String question = String.join(" ", args);
        Entity moralScreen = ServiceLocator.getEntityService().getMoralScreen();
        Entity moralSystem = ServiceLocator.getEntityService().getMoralSystem();
        moralSystem.getComponent(MoralDecision.class).addQuestion(question);

        logger.debug("Added question: {}", question);
        return true;
    }
    /**
     * Validates the command arguments.
     * @param args command arguments
     * @return is valid
     */
    boolean isValid(ArrayList<String> args) {
        return !args.isEmpty();
    }
}
