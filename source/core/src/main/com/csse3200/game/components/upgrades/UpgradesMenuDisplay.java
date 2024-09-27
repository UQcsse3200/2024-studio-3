package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpgradesMenuDisplay extends UIComponent {
    private final MainGameScreen game;
    private Table upgradesMenuTable;
    private Image backgroundImage;
    private Image upgradeImage;
    private Stack imageStack;
    private RandomCombination randomCombination = new RandomCombination();
    private static final Logger logger = LoggerFactory.getLogger(UpgradesMenuDisplay.class);
    //private Skin skin;


    private static final String[] upgradeTexturePaths = {
            "images/Speed_boot.png",
            "images/Rage.png",
            "images/Extortion.png",
            "images/Loan.png",
    };

    public UpgradesMenuDisplay(MainGameScreen game) {
        super();
        this.game = game;
    }

    /**
     * Create the upgrades menu display as a table.
     *
     * @return a Table with the background image and layout configurations.
     */
    private Table createUpgradesMenuDisplay() {

        Texture backgroundTexture = ServiceLocator.getResourceService().getAsset("images/Upgrade_display.png", Texture.class);
        backgroundImage = new Image(backgroundTexture);
        //backgroundImage.setSize(600, 600);


        imageStack = new Stack();
        //imageStack.setSize(600, 700);
        imageStack.add(backgroundImage);


        upgradesMenuTable = new Table();
        upgradesMenuTable.setSize(600, 700);
        upgradesMenuTable.setPosition(
                (stage.getWidth() - upgradesMenuTable.getWidth()) / 2,
                (stage.getHeight() - upgradesMenuTable.getHeight()) / 2
        );


        upgradesMenuTable.add(imageStack).expand().center();
        upgradesMenuTable.row();


        TextButton yesButton = new TextButton("YES", skin);
        TextButton noButton = new TextButton("NO", skin);


        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Activate the selected upgrade
                randomCombination.activateUpgrade();
                upgradesMenuTable.setVisible(false);
                game.resume();
                logger.info("YES button clicked");
            }
        });


        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                upgradesMenuTable.setVisible(false);
                game.resume();
                logger.info("NO button clicked");
            }
        });



        Table buttonTable = new Table();
        buttonTable.add(yesButton).minWidth(200).minHeight(50).pad(10);
        //buttonTable.row();
        buttonTable.add(noButton).minWidth(200).minHeight(50).pad(10);


        upgradesMenuTable.add(buttonTable).padTop(100).center();

        return upgradesMenuTable;
    }

    private void addUpgradeImage() {
        String upgrade = randomCombination.getSelectedUpgrade();
        logger.info("Selected upgrade: " + upgrade);
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
            default:
                logger.warn("Unknown upgrade type: " + upgrade);
                return;
        }

        Texture upgradeTexture = ServiceLocator.getResourceService().getAsset(texturePath, Texture.class);
        if (upgradeTexture == null) {
            logger.error("Failed to load upgrade texture: " + texturePath);
            return;
        }

        if (upgradeImage != null) {
            imageStack.removeActor(upgradeImage.getParent());
        }

        upgradeImage = new Image(upgradeTexture);
        upgradeImage.setSize(40, 50);

        Container<Image> upgradeImageContainer = new Container<>(upgradeImage);

        upgradeImageContainer.padTop(270);
        upgradeImageContainer.padLeft(50);
        upgradeImageContainer.padRight(50);

        upgradeImageContainer.center();
        imageStack.add(upgradeImageContainer);
    }



    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void create() {
        super.create();

        ServiceLocator.getResourceService().loadTextures(new String[]{"images/Upgrade_display.png"});
        ServiceLocator.getResourceService().loadTextures(upgradeTexturePaths);
        ServiceLocator.getResourceService().loadAll();

        Table upgradesMenuTable = createUpgradesMenuDisplay();
        stage.addActor(upgradesMenuTable);

        upgradesMenuTable.setVisible(false);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.U) {
                    boolean visible = !upgradesMenuTable.isVisible();
                    upgradesMenuTable.setVisible(visible);

                    if (visible) {
                        logger.info("Upgrades menu is now visible.");
                        game.pause();
                        addUpgradeImage();
                    } else {
                        logger.info("Upgrades menu is now hidden.");
                        game.resume();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().unloadAssets(new String[]{"images/Upgrade_display.png"});
        ServiceLocator.getResourceService().unloadAssets(upgradeTexturePaths);
    }

    @Override
    public void setStage(Stage stage) {
    }
}
