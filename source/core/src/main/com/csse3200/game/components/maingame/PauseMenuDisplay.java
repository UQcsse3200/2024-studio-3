package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PauseMenuDisplay extends UIComponent {
    private boolean isVisible;
    private final MainGameScreen game;
    private Table table;
    private static final Logger logger = LoggerFactory.getLogger(PauseMenuDisplay.class);
    private static final String[] pauseMenuTexture = {"images/pause_menu2.png"};

    private PauseMenuKoalaDisplay koala = new PauseMenuKoalaDisplay();

    public PauseMenuDisplay(MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }

//    private Image createKoalaImage() {
//        table = new Table();
//        table.bottom().left();
////        table.setFillParent(true);
//        Texture koalaTexture = ServiceLocator
//                .getResourceService().getAsset("images/koala5.png", Texture.class);
//        Image koalaImage = new Image(koalaTexture);
//        logger.debug("Not loading");
//        return koalaImage;
//    }

    private Image createPauseMenuBackground() {
        Texture pauseMenuTexture = ServiceLocator
                .getResourceService().getAsset("images/pause_menu2.png", Texture.class);
        Image backgroundImage = new Image(pauseMenuTexture);
        backgroundImage.setSize(800, 800);

        return backgroundImage;
    }

    private Table createButtonsTable() {
        Table buttonTable = new Table();

        TextButton resumeBtn = new TextButton("Resume", skin);
        TextButton restartBtn = new TextButton("Restart", skin);
        TextButton settingsBtn = new TextButton("Settings", skin);
        TextButton exitBtn = new TextButton("Main Menu", skin);
        TextButton quitBtn = new TextButton("Quit", skin);

        resumeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleVisibility();
            }
        });

        restartBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Restart button clicked");
                entity.getEvents().trigger("restart");
            }
        });

        settingsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Settings button clicked");
                entity.getEvents().trigger("setting");
            }
        });

        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Exit button clicked");
                entity.getEvents().trigger("exitGame");
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Quit button clicked");
                entity.getEvents().trigger("quit");
            }
        });

        buttonTable.add(resumeBtn).minWidth(250).minHeight(53).padTop(50);
        buttonTable.row();
        buttonTable.add(restartBtn).minWidth(250).minHeight(53).padTop(20);
        buttonTable.row();
        buttonTable.add(settingsBtn).minWidth(250).minHeight(53).padTop(20);
        buttonTable.row();
        buttonTable.add(exitBtn).minWidth(250).minHeight(53).padTop(20);
        buttonTable.row();
        buttonTable.add(quitBtn).minWidth(250).minHeight(53).padTop(20);

        return buttonTable;
    }

    private void stackPauseMenu() {
        table = new Table();
        table.setFillParent(true);

        Image backgroundImage = createPauseMenuBackground();

//        Image koalaImage = createKoala();

        Table buttonTable = createButtonsTable();

        Stack stack = new Stack();
        stack.add(backgroundImage);

        stack.add(buttonTable);

//        stack.add(createKoalaImage());

//        stack.add(koalaImage);

        table.add(stack).center().expand();


        stage.addActor(table);


        table.setVisible(isVisible);
        displayScreen();
    }


    public void addActors() {
        stackPauseMenu();
    }


    public void create() {
        super.create();
        ServiceLocator.getResourceService().loadTextures(pauseMenuTexture);
        ServiceLocator.getResourceService().loadAll(); // Ensures the texture is loaded
        addActors();
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


    public boolean getIsVisible() {
        return isVisible;
    }


    public void showMenu() {
        isVisible = true;
        table.setVisible(true);
        logger.info("PAUSE GAME");
        game.pause();
    }

    public void hideMenu() {
        isVisible = false;
        table.setVisible(false);
        logger.info("RESUME GAME");
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
//        table.clear();
        ServiceLocator.getResourceService().unloadAssets(pauseMenuTexture);
    }

    @Override
    protected void draw(SpriteBatch batch) {
    }

    @Override
    public void setStage(Stage mock) {
    }

}
