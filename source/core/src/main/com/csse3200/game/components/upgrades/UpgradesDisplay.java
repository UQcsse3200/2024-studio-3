package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.csse3200.game.components.maingame.PauseMenuDisplay;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UpgradesDisplay extends UIComponent {
    private final MainGameScreen game;
    private static final String[] upgradesMenuTexture = {"images/Upgrade_display.png"};
    //TODO why is this pauseMenuDisplay??
    private static final Logger logger = LoggerFactory.getLogger(PauseMenuDisplay.class);

    private Image upgradesMenuImage;
    private boolean isVisible = false;
    private List<Image> upgradeImages; // this is to store all the upgrades images
    private Table upgradesTable;
    private Image upgradeImage;
    private RandomCombination randomCombination = new RandomCombination();

//    private Skin skin;
    private static final String[] upgradeTexturePaths = {
            "images/Speed_boot.png",
            "images/Rage.png",
            "images/Extortion.png",
            "images/Loan.png",
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
        ServiceLocator.getResourceService().loadAll(); // Ensures the texture is loaded

        // Create and add the upgrades menu image first (background image)
        upgradesMenuImage = createUpgradesMenuDisplay();
        stage.addActor(upgradesMenuImage);

        upgradesTable = new Table();
        upgradesTable.setFillParent(false);  // if set to true it will take the whole screem
        upgradesTable.setSize(400, 300);
        upgradesTable.setPosition(
                upgradesMenuImage.getX() + 100,
                upgradesMenuImage.getY() + 30
        );

        Table button = createButtonsTable();
        upgradesTable.addActor(button);

        // Uncomment this sometimes will throw an error
        addUpgradeImage();


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

    public void addUpgradeImage() {
//        upgradesTable.clearChildren();
        String upgrade = randomCombination.getSelectedUpgrade();
        logger.info(upgrade);
        String texturePath = "";

        switch (upgrade) {
            case "Extortion":
                texturePath = "images/Extortion.png";
                break;
            case "Speed":
                texturePath = "images/Speed_boot.png";
                break;
            case "Rage":
                texturePath = "images/Rage.png";
                break;
            case "Loan":
                texturePath = "images/Loan.png";
                break;
        }

        Texture upgradeTexture = ServiceLocator.getResourceService().getAsset(texturePath, Texture.class);
        Image upgradeImage = new Image(upgradeTexture);
        upgradeImages.clear();
        upgradeImages.add(upgradeImage);
        upgradesTable.add(upgradeImage).pad(10);

    }

//    public void addRandomUpgradeImage() {
//        upgradesTable.clearChildren();
//
//        // make it random
//        int randomIndex = (int) (Math.random() * upgradeTexturePaths.length);
//        String randomTexturePath = upgradeTexturePaths[randomIndex];
//
//        Image upgradeImage = createUpgradeImage(randomTexturePath);
//
//        // Add the upgrade image to the table
//        upgradesTable.add(upgradeImage).pad(10);
//        upgradeImages.add(upgradeImage);
//    }
//
//
//    private Image createUpgradeImage(String texturePath) {
//        Texture upgradeTexture = ServiceLocator.getResourceService().getAsset(texturePath, Texture.class);
//        Image upgradeImage = new Image(upgradeTexture);
//        upgradeImage.setSize(150, 150);
//        return upgradeImage;
//    }

    private Table createButtonsTable() {
        Table buttonTable = new Table();

        // Create YES and NO buttons
        TextButton yesButton = new TextButton("YES", skin);
        TextButton noButton = new TextButton("NO", skin);

        // Add click listeners for the buttons
        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                randomCombination.activateUpgrade();
                logger.info("YES button clicked");
                // Handle YES button click
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resume();
                logger.info("NO button clicked");
                // Handle NO button click
            }
        });

        // Add buttons to the table with padding
        buttonTable.add(yesButton).minWidth(200).minHeight(50).pad(10);
        buttonTable.row();  // Move to the next row
        buttonTable.add(noButton).minWidth(200).minHeight(50).pad(10);

        return buttonTable;
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
//            addRandomUpgradeImage();
            upgradesTable.setVisible(true);
        } else {
            logger.info("Upgrades menu is now hidden.");
            game.resume();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().unloadAssets(upgradesMenuTexture);
        ServiceLocator.getResourceService().unloadAssets(upgradeTexturePaths);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void setStage(Stage stage) {
       this.stage = stage;
    }
}
