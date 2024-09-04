package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.table;

public class PauseMenu extends UIComponent {
    private boolean isVisible;
    private final MainGameScreen game;
    private Table table;
    private Image menu;

    private PauseMenu pauseMenu;
    private static final Logger logger = LoggerFactory.getLogger(PauseMenu.class);


    private static final String[] pauseMenuTexture = {"images/pause_menu.png"};

    public PauseMenu (MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }


    private void addImage() {
        table = new Table();
        table.setFillParent(true);
        Texture pauseMenuTexture = ServiceLocator
                .getResourceService().getAsset("images/pause_menu.png", Texture.class);

        Image backgroundImage = new Image(pauseMenuTexture);
        table.add(backgroundImage).expand().center().minWidth(550).minHeight(500);
        stage.addActor(table); //will ensure that elements is rendered correctly
        table.setVisible(isVisible);
        displayScreen();

    }

    public void create() {
        super.create();
        //table = new Table();
        //table.setVisible(isVisible);
        ServiceLocator.getResourceService().loadTextures(pauseMenuTexture);
        ServiceLocator.getResourceService().loadAll(); // Ensures the texture is loaded

        addImage();



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
                    return true;
                }
                return false;
            }
        });
        if (isVisible) {
            toggleVisibility();
        }
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            toggleVisibility(); // i used Q to call the PauseMenu
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void showMenu() {
        isVisible = true;
        table.setVisible(true);

//        ResourceService resourceService = ServiceLocator.getResourceService();
//        resourceService.loadTextures(pauseMenuTexture);
//        ServiceLocator.getResourceService().loadAll();
        logger.info("MY PAUSE");
        game.pause();
    }

    public void hideMenu() {
        isVisible = false;
        table.setVisible(false);
//        ResourceService resourceService = ServiceLocator.getResourceService();
//        resourceService.unloadAssets(pauseMenuTexture);
        logger.info("MY RESUME");
        game.resume();
    }


    public void toggleVisibility() {
       if (isVisible) {
           hideMenu();
       } else {
           showMenu();
       }
    }



    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().unloadAssets(pauseMenuTexture);
    }



    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void setStage(Stage mock) {
    }

//    @Override
//    public void dispose() {
//        table.clear();
//        super.dispose();
//    }



}
