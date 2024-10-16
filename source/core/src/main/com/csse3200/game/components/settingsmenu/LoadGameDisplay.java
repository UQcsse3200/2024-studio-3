package com.csse3200.game.components.settingsmenu;

import com.csse3200.game.files.GameState;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.files.FileLoader.Location;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

public class LoadGameDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(LoadGameDisplay.class);
    //private final GdxGame game;
    private Table rootTable;
    private Table savesTable;
    @Override
    public void create() {
        super.create();
        addActors();
    }
    private void addActors() {
        FileHandle[] saves = FileLoader.getFiles("saves", Location.LOCAL);
        rootTable = new Table();
        rootTable.setFillParent(true);

        Label title = new Label("Load Game", skin);
        rootTable.add(title).row();

        savesTable = new Table();
        for (int i = 1; i < 7; i++) {
            if (i - 1 < saves.length) {
                String save = saves[i-1].name().split("[.]")[0];
                Table load = new Table();
                load.add(new Label(save, skin)).row();

                GameState state = FileLoader.readClass(GameState.class, "saves" + File.separator + save + ".json", Location.LOCAL);
                try {
                    load.add(new Label("Last Modified: " + state.getModTime(), skin)).row();
                    load.add(new Label("Day " + state.getDay(), skin));
                    load.add(new Label("| Money: " + state.getMoney(), skin)).row();
                    load.addListener(new InputListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            entity.getEvents().trigger("start", save + ".json");
                            return true;
                        }
                    });
                    savesTable.add(load).pad(100, 100, 100, 100);
                    // Make sure every row has two save files
                    if (i % 2 == 0) {
                        savesTable.row();
                    }
                } catch (Exception e) {
                    logger.warn("Coudn't display {0}", save);
                }
            }
        }
        rootTable.add(savesTable).row();
        ImageTextButton backBtn = new ImageTextButton("Back", skin);
        backBtn.addListener(
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    logger.debug("Back button clicked");
                    entity.getEvents().trigger("exit");
                }
            });
        rootTable.add(backBtn).row();
        stage.addActor(rootTable);
    }

    @Override
    public void draw(SpriteBatch batch) {}
    @Override
    public void setStage(Stage mock) {}
}