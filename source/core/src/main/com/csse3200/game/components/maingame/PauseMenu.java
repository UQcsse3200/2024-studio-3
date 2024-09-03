package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

public class PauseMenu extends UIComponent {
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
        if (isVisible) {
            toggleVisibility();
        }
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
