package com.csse3200.game.components.maingame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.GdxGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Displays a dialog asking the user to name a save file and confirms the input.
 */
public class TextPopup extends Dialog {
    private static final Logger logger = LoggerFactory.getLogger(TextPopup.class);
    private final GdxGame game;
    private final TextField inputField;

    public TextPopup(String title, Skin skin, Stage stage, GdxGame game) {
        super(title, skin);
        this.game = game;

        inputField = new TextField("", skin);
        text("What would you like to name this save file?");
        getContentTable().add(inputField).width(200).pad(10);

        button("OK", true);
        button("Cancel", false);
        setModal(true);

        show(stage);
    }

    @Override
    protected void result(Object object) {
        if ((boolean) object) {
            String saveFileName = inputField.getText();
            ServiceLocator.getSaveLoadService().setSaveFile(saveFileName + ".json");
            ServiceLocator.getSaveLoadService().save();
        }
    }
}