package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Input;
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

/**
 * Display the Pause Menu when pausing the game
 */
public class PauseMenuDisplay extends UIComponent {
    private boolean isVisible;
    private final MainGameScreen game;
    private Table table;
    private static final Logger logger = LoggerFactory.getLogger(PauseMenuDisplay.class);
    private static final String[] pauseMenuTexture = {"images/pause_menu.png"};
    private static final String[] koalaTexture = {"images/koala5.png"};
    private static final String[] koalaTexture2 = {"images/koala4.png"};

    public PauseMenuDisplay(MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
        ServiceLocator.getEntityService().getEvents().addListener("togglePause", this::toggleVisibility);
    }

    /**
     * Create the koala image at the bottom left of the pause menu
     * @return koalaImage
     */
    private Image createKoalaImage() {
        table.bottom().left();
        table.setFillParent(true);
        Texture koalaTexture = ServiceLocator
                .getResourceService().getAsset("images/koala5.png", Texture.class);
        Image koalaImage = new Image(koalaTexture);
        koalaImage.setSize(250,250);
        logger.debug("Not loading");
        return koalaImage;
    }

    /**
     * Create the koala image at the top right of the pause menu
     * @return koalaImage2
     */
    private Image createKoalaImage2() {
        table = new Table();
        table.bottom().left();
        table.setFillParent(true);
        Texture koalaTexture = ServiceLocator
                .getResourceService().getAsset("images/koala4.png", Texture.class);
        Image koalaImage = new Image(koalaTexture);
        koalaImage.setSize(230,230);
        logger.debug("Not loading");
        return koalaImage;
    }

    /**
     * Create table for koala1
     * @return koalaTable
     */
    private Table createKoalaTable() {

        Table koalaTable = new Table();
        Image koalaImage = createKoalaImage();
        koalaTable.add(koalaImage).size(200, 150).expand().padTop(500).padRight(150);

        return koalaTable;
    }

    /**
     * Create table for koala2
     * @return koalaTable
     */
    private Table createKoalaTable2() {

        Table koalaTable = new Table();

        Image koalaImage = createKoalaImage2();

        koalaTable.add(koalaImage).size(170, 170).expand().top().padTop(10).right();

        return koalaTable;
    }

    /**
     * Create the pause menu background image
     * @return backgroundImage
     */
    private Image createPauseMenuBackground() {
        Texture pauseMenuTexture = ServiceLocator
                .getResourceService().getAsset("images/pause_menu2.png", Texture.class);
        Image backgroundImage = new Image(pauseMenuTexture);
        backgroundImage.setSize(800, 800);

        return backgroundImage;
    }

    /**
     * Create the table for buttons and add event listener to each button
     * @return buttonTable
     */
    private Table createButtonsTable() {
        Table buttonTable = new Table();

        TextButton resumeBtn = new TextButton("Resume", skin);
        TextButton saveBtn = new TextButton("Save", skin);
        TextButton loadBtn = new TextButton("Load", skin);
        TextButton restartBtn = new TextButton("Restart", skin);
        TextButton exitBtn = new TextButton("Main Menu", skin);
        TextButton quitBtn = new TextButton("Quit", skin);

        resumeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleVisibility();
            }
        });

        saveBtn.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    toggleVisibility();
                                    entity.getEvents().trigger("saveGame");
                                }
                            });

        loadBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ServiceLocator.getSaveLoadService().setSaveFile("saveFile.json");
                ServiceLocator.getSaveLoadService().load();
            }
        });

        restartBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Restart button clicked");
                game.resetScreen();
                entity.getEvents().trigger("restart");
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

        buttonTable.add(resumeBtn).minWidth(250).minHeight(53).padTop(10);
        buttonTable.row();
        buttonTable.add(saveBtn).minWidth(250).minHeight(53).padTop(10);
        buttonTable.row();
        buttonTable.add(loadBtn).minWidth(250).minHeight(53).padTop(10);
        buttonTable.row();
        buttonTable.add(restartBtn).minWidth(250).minHeight(53).padTop(10);
        buttonTable.row();
        buttonTable.row();
        buttonTable.add(exitBtn).minWidth(250).minHeight(53).padTop(10);
        buttonTable.row();
        buttonTable.add(quitBtn).minWidth(250).minHeight(53).padTop(10);

        return buttonTable;
    }

    /**
     * Stack all the tables to the pause menu
     */
    private void stackPauseMenu() {

        table = new Table();
        table.setFillParent(true);

        Image backgroundImage = createPauseMenuBackground();
        Table koalaTable = createKoalaTable();
        Table koalaTable2 = createKoalaTable2();

        Table buttonTable = createButtonsTable();

        Stack stack = new Stack();
        stack.add(backgroundImage);
        stack.add(koalaTable);
        stack.add(koalaTable2);

        stack.add(buttonTable);

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
        ServiceLocator.getResourceService().loadTextures(koalaTexture);
        ServiceLocator.getResourceService().loadTextures(koalaTexture2);
        ServiceLocator.getResourceService().loadAll(); // Ensures the texture is loaded
        addActors();
    }


    /**
     * Pressing O will stop the game and display the pause menu
     */
    public void displayScreen() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
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

    /**
     * Display the pause menu screen and pause the game
     */
    public void showMenu() {
        isVisible = true;
        table.setVisible(true);
        logger.info("PAUSE GAME");
        game.pause();

        table.toFront();
    }

    /**
     * Hide the screen and resume the game
     */
    public void hideMenu() {
        isVisible = false;
        table.setVisible(false);
        logger.info("RESUME GAME");
        game.resume();
    }


    /**
     * Toggle if the pause menu should be show or hide
     */
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

}
