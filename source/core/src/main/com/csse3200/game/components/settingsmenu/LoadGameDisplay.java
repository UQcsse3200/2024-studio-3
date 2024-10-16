package com.csse3200.game.components.settingsmenu;

import com.badlogic.gdx.graphics.Texture;
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
    private Texture coinTexture;

    @Override
    public void create() {
        super.create();
        coinTexture = new Texture("images/coin.png"); // Load the money icon texture
        addActors();
    }
    private void addActors() {
        // Load the background texture
        Texture backgroundTexture = new Texture("images/background_images/1.0.png");
        Image backgroundImage = new Image(backgroundTexture);

        // Set the background image to fill the entire screen
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);  // Add the background image first

        // Create the root table for other UI elements
        rootTable = new Table();
        rootTable.setFillParent(true);

        // Title label
        Label title = new Label("Load Game", skin);
        title.setFontScale(3);
        rootTable.add(title).padBottom(10).row();

        // Instruction label based on the number of saved games
        Label instructionLabel;
        FileHandle[] saves = FileLoader.getFiles("saves", Location.LOCAL);
        if (saves.length > 0) {
            instructionLabel = new Label("Click on a saved game to load it!", skin);
            instructionLabel.setFontScale(2);
        } else {
            instructionLabel = new Label("There are no saved games to load!", skin);
            instructionLabel.setFontScale(2);
        }
        rootTable.add(instructionLabel).padBottom(20).row();

        // Saves table
        savesTable = new Table();
        for (int i = 1; i < 7; i++) {
            if (i - 1 < saves.length) {
                String save = saves[i - 1].name().split("[.]")[0];
                Table load = new Table();
                load.add(new Label(save, skin)).row();

                GameState state = FileLoader.readClass(GameState.class, "saves" + File.separator + save + ".json", Location.LOCAL);
                try {
                    load.add(new Label("Last Modified: " + state.getModTime(), skin)).row();
                    load.add(new Label("Day " + state.getDay(), skin));
                    load.add(new Label("| Cash: " + state.getMoney(), skin)).padRight(5);
                    load.add(new Image(coinTexture)).size(30, 30).row();
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
                    logger.warn("Couldn't display {0}", save);
                }
            }
        }
        rootTable.add(savesTable).row();

        // Back button
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

        // Add the UI elements after the background
        stage.addActor(rootTable);
    }

    @Override
    public void draw(SpriteBatch batch) {}
    @Override
    public void setStage(Stage mock) {}
}