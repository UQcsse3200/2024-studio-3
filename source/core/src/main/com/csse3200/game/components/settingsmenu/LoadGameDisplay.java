package com.csse3200.game.components.settingsmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.GdxGame;
import com.csse3200.game.GdxGame.ScreenType;
import com.csse3200.game.files.UserSettings;
import com.csse3200.game.files.UserSettings.DisplaySettings;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.utils.StringDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.util.Set;

public class LoadGameDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(LoadGameDisplay.class);
    private final GdxGame game;
    private Table rootTable;
    private Table savesTable;

    public LoadGameDisplay(game) {
        super();
        this.game = game;
    }
    @Override
    public void create() {
        super.create();
        addActors();
    }
    private void addActors() {
        rootTable = new Table();
        table.setFillParent(true);

        Label title = new Label("Load Game");
        rootTable.add(title).row();

        savesTable = new Table();
        for (int i = 1; i < 7; i++) {
            Load load = new Table();
            load.add(new Label("Save " + i));
            savesTable.add(load);
            if (i % 2 == 0) {
                savesTable.row();
            }
        }
        rootTable.add(savesTable).row();
        ImageTextButton backBtn = new ImageTextButton("Start", skin);
        backBtn.addListener(
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    logger.debug("Back button clicked");
                    entity.getEvents().trigger("exit");
                }
            });
        )
        rootTable.add(backBtn).row();
    }
}