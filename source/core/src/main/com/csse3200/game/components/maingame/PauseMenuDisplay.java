package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    public PauseMenuDisplay(MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }


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

        buttonTable.add(resumeBtn).minWidth(250).minHeight(50).padTop(70);
        buttonTable.row();
        buttonTable.add(restartBtn).minWidth(250).minHeight(50).padTop(20);
        buttonTable.row();
        buttonTable.add(settingsBtn).minWidth(250).minHeight(50).padTop(20);
        buttonTable.row();
        buttonTable.add(exitBtn).minWidth(250).minHeight(50).padTop(20);
        buttonTable.row();
        buttonTable.add(quitBtn).minWidth(250).minHeight(50).padTop(20);

        return buttonTable;
    }

    private void stackPauseMenu() {
        table = new Table();
        table.setFillParent(true);

        Image backgroundImage = createPauseMenuBackground();
        Table buttonTable = createButtonsTable();

        Stack stack = new Stack();
        stack.add(backgroundImage);

        stack.add(buttonTable);


        table.add(stack).center().expand();


        stage.addActor(table);


        table.setVisible(isVisible);
        displayScreen();
    }


    public void addActors() {
        stackPauseMenu();
    }

        /*
    private void addActors() {
        table = new Table();
        table.setFillParent(true);
        Texture pauseMenuTexture = ServiceLocator
                .getResourceService().getAsset("images/pause_menu.png", Texture.class);

        Image backgroundImage = new Image(pauseMenuTexture);

        table.setBackground(backgroundImage.getDrawable());

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

        restartBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Start button clicked");
                        entity.getEvents().trigger("restart");
                    }
                });

        settingsBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Settings button clicked");
                        entity.getEvents().trigger("setting");
                    }
                });

        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        entity.getEvents().trigger("exitGame");
                    }
                });

        quitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Quit button clicked");
                        entity.getEvents().trigger("quit");
                    }
                });

        //table.add(backgroundImage).expand().center().minWidth(550).minHeight(500);
       // table.row();
        table.add(resumeBtn).minWidth(300).minHeight(50).padTop(20);
        table.row();
        table.add(restartBtn).minWidth(300).minHeight(50).padTop(10);
        table.row();
        table.add(settingsBtn).minWidth(300).minHeight(50).padTop(10);
        table.row();
        table.add(exitBtn).minWidth(300).minHeight(50).padTop(10);
        table.row();
        table.add(quitBtn).minWidth(300).minHeight(50).padTop(10);

        stage.addActor(table);
        table.setVisible(isVisible);
        displayScreen();
    } */


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
        logger.info("MY PAUSE");
        game.pause();
    }

    public void hideMenu() {
        isVisible = false;
        table.setVisible(false);
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
