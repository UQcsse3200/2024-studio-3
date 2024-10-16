package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * A UI component for displaying and interacting with the upgrades menu in the game.
 */
public class UpgradesDisplay extends UIComponent {
    private final MainGameScreen game;
    private static final String[] upgradesMenuTexture = {"images/Upgrade_display.png"};
    private static final Logger logger = LoggerFactory.getLogger(UpgradesDisplay.class);

    private Image upgradesMenuImage;
    public boolean isVisible = false;
    private Table upgradesTable;
    private List<Image> notEnoughGoldImages;

    private static final String[] upgradeTexturePaths = {
            "images/SpeedBoot.png",
            "images/Extortion1.png",
            "images/Loan1.png",
            "images/notEnoughGold.png",
            "images/Dance_party.png"
    };

    /**
     *
     * This initializes the list of upgrade images but does not create the menu UI components.
     */
    public UpgradesDisplay(MainGameScreen game) {
        super();
        notEnoughGoldImages = new ArrayList<>();
        this.game = game;
    }


    /**
     * Create the upgrade menu to allow user to see which upgrade is generated
     * @return The upgrade menu background image
     */
    public Image createUpgradesMenuDisplay() {
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

    /**
     * Initializes the UI components of the upgrade display, loads the necessary textures,
     * and sets up the event listeners for displaying or hiding the upgrade menu.
     */
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
        upgradesTable.setFillParent(false);  // if set to true it will take the whole screen
        upgradesTable.setSize(400, 300);
        upgradesTable.setPosition(
                upgradesMenuImage.getX() + 100,
                upgradesMenuImage.getY() + 30
        );

        Table button = createButtonsTable();
        upgradesTable.addActor(button);
 
        addUpgradeImage();


        upgradesTable.top().left();
        stage.addActor(upgradesTable);
        upgradesTable.setVisible(false);


        ServiceLocator.getRandomComboService().getEvents().addListener("notenoughmoney", this::displayNotEnoughGoldUI);
    }

    /**
     * Add associated upgrade image based on the generated upgrade
     */
    public void addUpgradeImage() {
//        upgradesTable.clearChildren();
        String upgrade = ServiceLocator.getRandomComboService().getSelectedUpgrade();
        logger.info(upgrade);
        String texturePath = switch (upgrade) {
            case "Extortion" -> "images/Extortion1.png";
            case "Speed" -> "images/SpeedBoot.png";
            case "Loan" -> "images/Loan1.png";
            case "Dance party" -> "images/Dance_party.png";
            default -> "";
        };

        Texture upgradeTexture = ServiceLocator.getResourceService().getAsset(texturePath, Texture.class);
        Image upImage = new Image(upgradeTexture);
        upgradesTable.add(upImage).pad(10);
    }


    /**
     * Allowing user to decide whether the upgrade is needed or not by pressing YES or NO button
     * @return The table with buttons
     */
    public Table createButtonsTable() {
        Table buttonTable = new Table();

        TextButton yesButton = new TextButton("YES", skin);
        TextButton noButton = new TextButton("NO", skin);

        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ServiceLocator.getRandomComboService().activateUpgrade();
                logger.info("YES button clicked");
                ServiceLocator.getRandomComboService().getEvents().trigger("response");
                toggleVisibility();
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resume();
                logger.info("NO button clicked");
                ServiceLocator.getRandomComboService().getEvents().trigger("response");
                toggleVisibility();
            }
        });


        buttonTable.add(yesButton).minWidth(200).minHeight(50).padTop(-100).padLeft(-40);
        buttonTable.add(noButton).minWidth(200).minHeight(50).padTop(-100).padRight(-700);

        return buttonTable;
    }

    /**
     * Display a message when the user does not have enough gold to purchase the upgrade
     */
    public void displayNotEnoughGoldUI() {

        Texture notEnoughGoldTexture = ServiceLocator.getResourceService()
                .getAsset("images/notEnoughGold.png", Texture.class);

        Image notEnoughGoldImage = new Image(notEnoughGoldTexture);
        notEnoughGoldImage.setSize(400, 400);

        float xPosition = (stage.getWidth() - notEnoughGoldImage.getWidth()) / 2;
        float yPosition = (stage.getHeight() - notEnoughGoldImage.getHeight()) / 2;
        notEnoughGoldImage.setPosition(xPosition, yPosition);

        notEnoughGoldImages.add(notEnoughGoldImage);
        stage.addActor(notEnoughGoldImage);

        // Optionally, remove the image after some time or when clicked
        // Remove the image after 2 seconds
        notEnoughGoldImage.addAction(Actions.sequence(
                Actions.delay(2f),
                Actions.run(notEnoughGoldImage::remove)
        ));
    }

    /**
     * Toggles the visibility of the upgrades menu
     */
    public void toggleVisibility() {
        isVisible = !isVisible;
        upgradesMenuImage.setVisible(isVisible);

        if (isVisible) {
            logger.info("Upgrades menu is now visible.");
            game.pause();

            // Add a random upgrade image each time if th emenu is shown

            upgradesTable.setVisible(true);
        } else {
            logger.info("Upgrades menu is now hidden.");
            game.resume();
            upgradesTable.setVisible(false);
        }
    }

    public Button getYesButton() {
        Table buttonTable = (Table) this.getUpgradesTable().getChildren().get(0);
        return (TextButton) buttonTable.getChildren().get(0);
    }

    public Button getNoButton() {
        Table buttonTable = (Table) this.getUpgradesTable().getChildren().get(0);
        return (TextButton) buttonTable.getChildren().get(1);
    }

    public void simulateYesButtonClick() {
        TextButton yesButton = (TextButton) this.getYesButton();
        yesButton.getClickListener().clicked(null, 0, 0);

        for (EventListener listener : yesButton.getListeners()) {
            if (listener instanceof ClickListener) {
                ((ClickListener) listener).clicked(null, 0, 0);
            }
        }
    }

    public void simulateNoButtonClick() {
        TextButton noButton = (TextButton) this.getNoButton();
        noButton.getClickListener().clicked(null, 0, 0);

        for (EventListener listener : noButton.getListeners()) {
            if (listener instanceof ClickListener) {
                ((ClickListener) listener).clicked(null, 0, 0);
            }
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

    public void setUpgradesTable(Table upgradesTable) {
        this.upgradesTable = upgradesTable;
    }

    public Table getUpgradesTable() {
        return upgradesTable;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public Image getUpgradesMenuImage() {
        return upgradesMenuImage;
    }

    public String[] getUpgradesMenuTexture() {
        return upgradesMenuTexture;
    }

    public String[] getUpgradeTexturePaths() {
        return upgradeTexturePaths;
    }

    public List<Image> getNotEnoughGoldImages() {
        return notEnoughGoldImages;
    }
}
