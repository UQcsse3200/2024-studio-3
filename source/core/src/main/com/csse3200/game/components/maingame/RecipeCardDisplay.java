package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecipeCardDisplay extends UIComponent {

    private boolean isVisible;
    private final MainGameScreen game;
    private static final Logger logger = LoggerFactory.getLogger(RecipeCardDisplay.class);

    private static final String[] recipeCardTexture = {"images/pause_menu2.png"};
    private Image backgroundImage;

    //TODO add esc key exit

    public RecipeCardDisplay(MainGameScreen game) {
        super();
        this.game = game;
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

        backgroundImage.setVisible(false);
        return backgroundImage;
    }

    @Override
    public void create() {
        super.create();
        ServiceLocator.getResourceService().loadTextures(recipeCardTexture);

        backgroundImage = createRecipeCardBackground();
        stage.addActor(backgroundImage);

        // I had to comment this out because the image it displays is ugly and causes glitches #474
        ServiceLocator.getResourceService().loadAll(); // Ensures the texture is loaded
    }

    /**
     * Pressing enter will stop the game and display the recipe card
     */
    public void displayScreen() {
        isVisible = !isVisible;
        backgroundImage.setVisible(isVisible);

        if (isVisible) {
            logger.info("Upgrades menu is now visible.");
            game.pause();
        } else {
            logger.info("Upgrades menu is now hidden.");
            game.resume();
        }

    }

    /**
     * Display the pause menu screen and pause the game
     */
    public void showMenu() {
        isVisible = true;
        logger.info("PAUSE GAME");
        game.pause();

        backgroundImage.setVisible(true);
    }

    /**
     * Hide the screen and resume the game
     */
    public void hideMenu() {
        isVisible = false;
        logger.info("RESUME GAME");
        game.resume();
        if (backgroundImage != null) {
            backgroundImage.remove();
        }
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
        ServiceLocator.getResourceService().unloadAssets(new String[]{"images/pause_menu2.png"});

    }

    @Override
    protected void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void setStage(Stage mock) {
        this.stage = mock;
    }
}
