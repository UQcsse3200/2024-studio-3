package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.Input;
import com.csse3200.game.GdxGame;
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

}