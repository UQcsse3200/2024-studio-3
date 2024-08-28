package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.csse3200.game.GdxGame;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.ui.UIComponent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class EndDayDisplay extends UIComponent {
    private Table layout; // Layout manager
    private boolean isVisible;
    private final MainGameScreen game;

    public EndDayDisplay(MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }

    public void create() {
        super.create();
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isVisible);
        stage.addActor(layout);

        initializeUI();
        setupInputListener();
    }

    private void initializeUI() {
        Label titleLabel = new Label("Day End Summary", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        layout.add(titleLabel).pad(10).row();

        TextButton closeBtn = new TextButton("Close", skin);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleVisibility();
            }
        });
        layout.add(closeBtn).padTop(20).row();
    }

    private void setupInputListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.P) {
                    toggleVisibility();
                    return true;
                }
                return false;
            }
        });
    }

    public void show() {
        isVisible = true;
        layout.setVisible(isVisible);
        game.pause(); // Pause the game when the display is shown
    }

    public void hide() {
        isVisible = false;
        layout.setVisible(isVisible);
        game.resume(); // Resume the game when the display is hidden
    }

    public void toggleVisibility() {
        if (isVisible) {
            hide();
        } else {
            show();
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        // Drawing logic if needed
    }

    @Override
    public void setStage(Stage mock) {

    }
}
