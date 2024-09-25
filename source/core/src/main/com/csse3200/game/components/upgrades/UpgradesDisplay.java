package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.maingame.PauseMenuDisplay;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UpgradesDisplay extends UIComponent {
    private final MainGameScreen game;
    private static final String[] upgradesMenuTexture = {"images/Upgrade_display.png"};
    //TODO why is this pauseMenuDisplay??
    private static final Logger logger = LoggerFactory.getLogger(PauseMenuDisplay.class);

    private Image upgradesMenuImage;
    private boolean isVisible = false;
    private List<Image> upgradeImages; // this is to store all the upgrades images
    private Table upgradesTable;
    private static final String[] upgradeTexturePaths = {
            "images/Speed_boot.png",
            "images/Rage.png",
            "images/Extortion.png"
    };


    public UpgradesDisplay(MainGameScreen game) {
        super();
        this.game = game;
        this.upgradeImages = new ArrayList<>();
    }


    private Image createUpgradesMenuDisplay() {
        Texture pauseMenuTexture = ServiceLocator
                .getResourceService().getAsset("images/Upgrade_display.png", Texture.class);

        Image backgroundImage = new Image(pauseMenuTexture);
        backgroundImage.setSize(600, 600);


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
        ServiceLocator.getResourceService().loadTextures(upgradeTexturePaths);

        // Create and add the upgrades menu image first (background image)
        upgradesMenuImage = createUpgradesMenuDisplay();
        stage.addActor(upgradesMenuImage);

        // Create a table for the upgrade images
        upgradesTable = new Table();
        upgradesTable.setFillParent(false);  // if set to true it will take the whole screem
        upgradesTable.setSize(400, 300);
        upgradesTable.setPosition(
                upgradesMenuImage.getX() + 100,
                upgradesMenuImage.getY() + 40
        );

        upgradesTable.top().left();
        stage.addActor(upgradesTable);

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

    public void addRandomUpgradeImage() {
        upgradesTable.clearChildren();

        // make it random
        int randomIndex = (int) (Math.random() * upgradeTexturePaths.length);
        String randomTexturePath = upgradeTexturePaths[randomIndex];

        Image upgradeImage = createUpgradeImage(randomTexturePath);

        // Add the upgrade image to the table
        upgradesTable.add(upgradeImage).pad(10);
        upgradeImages.add(upgradeImage);
    }


    private Image createUpgradeImage(String texturePath) {
        Texture upgradeTexture = ServiceLocator.getResourceService().getAsset(texturePath, Texture.class);
        Image upgradeImage = new Image(upgradeTexture);
        upgradeImage.setSize(150, 150);
        return upgradeImage;
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

            // Add a random upgrade image each time if th emenu is shown
            addRandomUpgradeImage();
            upgradesTable.setVisible(true);
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
       this.stage = stage;
    }
}
