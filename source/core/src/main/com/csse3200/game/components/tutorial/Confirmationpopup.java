package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.cutscenes.BackstoryCutscene;
import com.csse3200.game.screens.CutsceneScreen;
import com.csse3200.game.screens.TutorialScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Displays a confirmation dialog asking if the user wants to start the tutorial.
 */
public class Confirmationpopup extends Dialog {
    private static final Logger logger = LoggerFactory.getLogger(Confirmationpopup.class);
    private final GdxGame game;

    public Confirmationpopup(String title, Skin skin, Stage stage, GdxGame game) {
        super(title, skin);
        this.game = game;

        text("Start tuto?");
        button("Yes", 1);
        button("No", 2);
        button("Skip", 3);
        setModal(true);

        show(stage);
    }

    @Override
    protected void result(Object object) {
        int isTrue = (int) object;
        if (isTrue == 1) {
            game.setScreen(new TutorialScreen(game));
        } else if (isTrue == 2) {
//            game.setScreen(GdxGame.ScreenType.MAIN_GAME);
            // User chose "No", transition to BackstoryCutscene
            BackstoryCutscene backstoryCutscene = new BackstoryCutscene();
            game.setCurrentCutscene(backstoryCutscene); // Set the backstory cutscene as current
            game.setScreen(new CutsceneScreen(game, GdxGame.CutsceneType.BACK_STORY)); // Start the cutscene screen the backstory
        } else {
            game.setScreen(GdxGame.ScreenType.MAIN_GAME);
        }

    }

}