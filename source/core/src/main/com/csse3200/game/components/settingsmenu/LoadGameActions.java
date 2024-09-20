package com.csse3200.game.components.settingsmenu;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Menu Screen and does something when one of the
 * events is triggered.
 */
public class LoadGameActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(LoadGameActions.class);
    private GdxGame game;

    public LoadGameActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        System.out.println("Hello");
    }
}
