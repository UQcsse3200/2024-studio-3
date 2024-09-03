package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.screens.MainMenuScreen;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PauseMenu extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(PauseMenu.class);

    private boolean isVisible;
    private final MainGameScreen game;
    private Table table;
    private static final String[] pauseMenuTexture = {"images/pause_menu.png"};

    public PauseMenu (MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }

    public void create(){
        super.create();
        table = new Table();
        table.setVisible(isVisible);
        displayScreen();


        // Main Menu Actions (after creating the UI and implement the functionalities, we need to
        // trigger each button
//        entity.getEvents().addListener("start", this::onStart);
//        entity.getEvents().addListener("load", this::onLoad);
//        entity.getEvents().addListener("exit", this::onExit);
//        entity.getEvents().addListener("settings", this::onSettings);
    }

    // UI part
    public void menuUI() {

    }

    public void displayScreen() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.O) {
                    toggleVisibility();
                    logger.info("press O");
                    return true;
                }
                return false;
            }
        });
    }

    public void showMenu() {
        isVisible = true;
        table.setVisible(true);
//        ResourceService resourceService = ServiceLocator.getResourceService();
//        resourceService.loadTextures(pauseMenuTexture);
        game.pause();
    }

    public void hideMenu() {
        isVisible = false;
        table.setVisible(false);
//        ResourceService resourceService = ServiceLocator.getResourceService();
//        resourceService.unloadAssets(pauseMenuTexture);
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
