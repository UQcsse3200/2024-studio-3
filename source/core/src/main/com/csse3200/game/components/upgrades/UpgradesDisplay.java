package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.csse3200.game.components.maingame.PauseMenuDisplay;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpgradesDisplay extends UIComponent {
    private final MainGameScreen game;
    private static final String[] upgradesMenuTexture = {"images/UpgradesDisplay.png"};
    private static final Logger logger = LoggerFactory.getLogger(PauseMenuDisplay.class);

    private Image upgradesMenuImage;
    private boolean isVisible = false;

    public UpgradesDisplay(MainGameScreen game) {
        super();
        this.game = game;
    }


    private Image createUpgradesMenuDisplay() {
        Texture pauseMenuTexture = ServiceLocator
                .getResourceService().getAsset("images/UpgradesDisplay.png", Texture.class);

        Image backgroundImage = new Image(pauseMenuTexture);
        backgroundImage.setSize(500, 500);


        float stageWidth = stage.getWidth();
        float stageHeight = stage.getHeight();


        float xPosition = (stageWidth - backgroundImage.getWidth()) / 2;
        float yPosition = (stageHeight - backgroundImage.getHeight()) / 2;

        backgroundImage.setPosition(xPosition, yPosition);

        backgroundImage.setVisible(false);

        return backgroundImage;
    }


    @Override
    public void create() {
        super.create();
        ServiceLocator.getResourceService().loadTextures(upgradesMenuTexture);


        upgradesMenuImage = createUpgradesMenuDisplay();
        stage.addActor(upgradesMenuImage);


        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.U) {
                    toggleVisibility();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Toggles the visibility of the upgrades menu
     */
    private void toggleVisibility() {
        isVisible = !isVisible;
        upgradesMenuImage.setVisible(isVisible);

        if (isVisible) {
            logger.info("Upgrades menu is now visible.");
            game.pause();
        } else {
            logger.info("Upgrades menu is now hidden.");
            game.resume();
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void setStage(Stage stage) {
       // super.setStage(stage);
    }
}
