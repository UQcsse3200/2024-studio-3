package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Input;
import com.csse3200.game.GdxGame;
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
    private Table table;
    private static final Logger logger = LoggerFactory.getLogger(RecipeCardDisplay.class);
    private static final String[] recipeCardTexture = {"images/recipe_card.png"};
    private Image backgroundImage;

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
        Texture texture = ServiceLocator
                .getResourceService().getAsset("images/recipe_card.png", Texture.class);
        Image backgroundImage = new Image(texture);
        backgroundImage.setSize(2000, 2000);

        return backgroundImage;
    }

    public void create() {
        super.create();
        ServiceLocator.getResourceService().loadTextures(recipeCardTexture);

        table = new Table();
        table.setFillParent(true);
        backgroundImage = createRecipeCardBackground();
        table.add(backgroundImage).center().expand();
        table.setVisible(isVisible);
        stage.addActor(table);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.NUM_0) {
                    toggleVisibility();
                    return true;
                }
                return false;
            }
        });

        ServiceLocator.getResourceService().loadAll(); // Ensures the texture is loaded
    }

    /**
     * Display the pause menu screen and pause the game
     */
    public void showMenu() {
        isVisible = true;
        logger.info("PAUSE GAME");
        game.pause();

        table.setVisible(true);
    }

    /**
     * Hide the screen and resume the game
     */
    public void hideMenu() {
        isVisible = false;
        logger.info("RESUME GAME");
        game.resume();

        if (table != null) {
            table.setVisible(false);
        }
    }


    /**
     * Toggle if the pause menu should be show or hide
     */
    public void toggleVisibility() {
        if (ServiceLocator.getGame().getCurrentScreenType() == GdxGame.ScreenType.MAIN_GAME) {
            if (isVisible) {
                hideMenu();
            } else {
                showMenu();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().unloadAssets(new String[]{"images/recipe_card.png"});
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