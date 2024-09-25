package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecipeCardDisplay extends UIComponent {
    private boolean isVisible;
    private final MainGameScreen game;
    private static final Logger logger = LoggerFactory.getLogger(PauseMenuDisplay.class);
    private static final String[] recipeCardTexture = {"images/pause_menu2.png"};

    //TODO add esc key exit

    public RecipeCardDisplay(MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }

    /**
     * Create the recipe card background image
     * @return backgroundImage
     */
    private Image createRecipeCardBackground() {
        Texture pauseMenuTexture = ServiceLocator
                .getResourceService().getAsset("images/pause_menu2.png", Texture.class);
        Image backgroundImage = new Image(pauseMenuTexture);
        backgroundImage.setSize(800, 800);

        return backgroundImage;
    }

    public void create() {
        super.create();
        ServiceLocator.getResourceService().loadTextures(recipeCardTexture);
        ServiceLocator.getResourceService().loadAll(); // Ensures the texture is loaded
    }

    /**
     * Pressing enter will stop the game and display the recipe card
     */
    public void displayScreen() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.M) {
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
        logger.info("PAUSE GAME");
        game.pause();
    }

    /**
     * Hide the screen and resume the game
     */
    public void hideMenu() {
        isVisible = false;
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
        ServiceLocator.getResourceService().unloadAssets(recipeCardTexture);
    }

    @Override
    protected void draw(SpriteBatch batch) {
    }

    @Override
    public void setStage(Stage mock) {
    }
}
