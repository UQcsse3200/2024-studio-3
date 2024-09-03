package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.ui.UIComponent;

public class PauseMenu extends UIComponent {
    private boolean isVisible;
    private final MainGameScreen game;

    public PauseMenu (MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }

    public void create(){
        super.create();
    }

    // UI part
    public void displayScreen() {
        if (isVisible) {
            toggleVisibility();
        }
    }

    public void showMenu() {
        isVisible = true;
        game.pause();
    }

    public void hideMenu() {
        isVisible = false;
        game.resume();
    }

    public void toggleVisibility() {
       if (isVisible) {
           showMenu();
       } else {
           hideMenu();
       }
    }


    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void setStage(Stage mock) {

    }
}
