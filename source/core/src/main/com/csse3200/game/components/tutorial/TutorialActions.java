package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.csse3200.game.GdxGame;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens to key presses to complete tutorial-related actions.
 */

public class TutorialActions extends InputComponent {
    private static final Logger logger = LoggerFactory.getLogger(TutorialActions.class);
    private final GdxGame game;

    public TutorialActions(GdxGame game) {
        super(5);  // Priority for input processing order
        this.game = game;
    }

    @Override
    public void create() {
        ServiceLocator.getInputService().register(this);
        ServiceLocator.getTutorialService().getEvents().trigger("startTutorial");

        ServiceLocator.getInputService().getEvents().addListener("playerMoved", this::onPlayerMove);
        ServiceLocator.getDocketService().getEvents().addListener("shiftDocketsLeft", this::onShiftDocketsLeft);
        ServiceLocator.getDocketService().getEvents().addListener("shiftDocketsRight", this::onShiftDocketsRight);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            System.out.println("Space key pressed, triggering item pickup.");
            ServiceLocator.getInputService().getEvents().trigger("itemPickedUp"); //CLARIFY WHICH SERVICE WITH TEAM
            return true;
        } else if (keycode == Input.Keys.ENTER) {
            logger.info("Enter key pressed, completing the tutorial.");
            ServiceLocator.getTutorialService().getEvents().trigger("completeTutorial");
            return true;
        } else if (keycode == Input.Keys.W || keycode == Input.Keys.A || keycode == Input.Keys.S || keycode == Input.Keys.D) {
            logger.info("Movement key (W/A/S/D) pressed.");
            ServiceLocator.getInputService().getEvents().trigger("playerMoved");
            return true;
        }
        if (keycode == Keys.LEFT_BRACKET) {
            logger.info("Shift dockets left pressed.");
            ServiceLocator.getDocketService().getEvents().trigger("shiftDocketsLeft");
            return true;
        } else if (keycode == Keys.RIGHT_BRACKET) {
            logger.info("Shift dockets right pressed.");
            ServiceLocator.getDocketService().getEvents().trigger("shiftDocketsRight");
            return true;
        }
        return false;
    }

    private void onPlayerMove() {
        logger.info("Player moved, advancing tutorial.");
        ServiceLocator.getTutorialService().getEvents().trigger("advanceTutorial");
    }

    private void onShiftDocketsLeft() {
        logger.info("Dockets shifted left.");
        ServiceLocator.getTutorialService().getEvents().trigger("advanceTutorial");
    }

    private void onShiftDocketsRight() {
        logger.info("Dockets shifted right.");
        ServiceLocator.getTutorialService().getEvents().trigger("advanceTutorial");
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }



}

