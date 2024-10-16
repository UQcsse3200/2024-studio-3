package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.csse3200.game.entities.configs.CustomerPersonalityConfig;
import com.csse3200.game.entities.configs.NPCConfigs;
import com.csse3200.game.files.FileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.GdxGame;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import java.util.ArrayList;

public class EndDayDisplay extends UIComponent {
    private Table layout; // Layout manager
    private boolean isVisible;
    private static final Logger logger = LoggerFactory.getLogger(com.csse3200.game.components.maingame.EndDayDisplay.class);
    private final MainGameScreen gameScreen;
    private GdxGame game;
    private Image birdImage;
    private Image pointImage1;
    private Image pointImage2;
    private Image pointImage3;
    private float imageX;
    private int currentGold;
    private Label goldLabel;
    public final java.util.List<String> customerNameArray;
    public final java.util.List<String> passedCustomerArray;
    public final java.util.List<String> failedCustomerArray;
    private final Table passedCustomerTable;
    private final Table failedCustomerTable;
    private static final int STARTING_GOLD = ServiceLocator.getLevelService().getCurrGold();
    private static final String POINT_IMAGE_PATH = "images/point.png";
    private static final String TINY_5 = "flat-earth/skin/fonts/Tiny5-Regular.ttf";
    private static final NPCConfigs configs = FileLoader.readClass(NPCConfigs.class, "configs/NPCs.json");

    /**
     * Constructor for the EndDayDisplay class.
     */
    public EndDayDisplay() {
        super();
        this.gameScreen = ServiceLocator.getGameScreen();
        this.setGame(ServiceLocator.getGameScreen().getGame());
        setVisible(false);
        this.currentGold = STARTING_GOLD;
        this.customerNameArray = new ArrayList<>();
        this.passedCustomerArray = new ArrayList<>();
        this.failedCustomerArray = new ArrayList<>();
        passedCustomerTable = new Table(skin);
        failedCustomerTable = new Table(skin);
    }

    /**
     * Initializes and creates the end-of-day display components.
     * This method sets up the entire layout for the display including background,
     * images, and interactive elements like buttons and lists.
     */
    @Override
    public void create() {
        super.create();
        setLayout(new Table());
        getLayout().setFillParent(true);
        getLayout().setVisible(isVisible());
        stage.addActor(getLayout());
        createBackground();
        setupImages();
        setupUI();
        addListeners();
    }

    /**
     * Registers event listeners relevant to the end-of-day display operations, such as gold updates and customer events.
     */
    public void addListeners() {
        ServiceLocator.getDocketService().getEvents().addListener("goldUpdated", this::handleGoldUpdate);
        ServiceLocator.getLevelService().getEvents().addListener("customerSpawned", this::updateCustomerList);
        ServiceLocator.getLevelService().getEvents().addListener("endDayDisplay", this::show);
        ServiceLocator.getLevelService().getEvents().addListener("resetScreen", MainGameScreen::resetScreen);
        ServiceLocator.getEntityService().getEvents().addListener("toggleEndDayScreen", this::toggleVisibility);
        ServiceLocator.getEntityService().getEvents().addListener("customerPassed", this::handlePassedCustomer);

        ServiceLocator.getDayNightService().getEvents().addListener("endOfDay", this::show);
        }

    /**
     * Sets up a white background for the display using a predefined image.
     * This method loads a texture from the resource service and sets it as the background
     * for the layout. The image used is specified in the texture path 'images/endday.png'.
     */
    public void createBackground() {
        // Create a background
        Texture texture = ServiceLocator.getResourceService()
                .getAsset("images/endday.png", Texture.class);
        Drawable background = new TextureRegionDrawable(new TextureRegion(texture));
        getLayout().setBackground(background);
    }

    /**
     * Sets up the images for various UI elements in the display.
     * This method initializes and positions images that are used to enhance the visual
     * representation of the display, such as birds and points icons.
     */
    public void setupImages() {
        setBirdImage(createImage("images/bird.png"));
        setPointImage1(createImage(POINT_IMAGE_PATH));
        setPointImage2(createImage(POINT_IMAGE_PATH));
        setPointImage3(createImage(POINT_IMAGE_PATH));
    }

    /**
     * Creates and returns an image actor for a given texture path.
     * This utility method simplifies the creation of an image actor from a texture,
     * making it easier to manage image setup throughout the class.
     *
     * @param texturePath the path to the texture asset
     * @return an initialized Image actor with the specified texture
     */
    public Image createImage(String texturePath) {
        Texture texture = ServiceLocator.getResourceService().getAsset(texturePath, Texture.class);
        Image image = new Image(new TextureRegionDrawable(new TextureRegion(texture)));
        image.setVisible(false);
        image.setPosition(0, (float) (4 * Gdx.graphics.getHeight()) / 5 - image.getHeight() / 2);
        stage.addActor(image);
        return image;
    }

    /**
     * Sets up the user interface elements for the display.
     * This method arranges all the interactive and non-interactive elements within
     * the display, ensuring proper layout and functionality.
     */
    public void setupUI() {
        addSpacer();
        setupGoldDisplay();
        setupCustomerLists();
        addCloseButton();
    }

    /**
     * Adds a spacer to the layout to manage vertical spacing between components.
     * This method helps in aligning other UI components by adding customizable space
     * in the layout table.
     */
    public void addSpacer() {
        Table spacer = new Table();
        spacer.add().height(4 * getBirdImage().getHeight() / 5);
        getLayout().add(spacer).row();
    }

    /**
     * Sets up the display for showing the current gold amount.
     * This method configures a label to show the amount of gold collected, updating
     * its value in real time during the display.
     */
    public void setupGoldDisplay() {
        Texture coinTexture = ServiceLocator.getResourceService()
                .getAsset("images/coin.png", Texture.class);
        Drawable coinDrawable = new TextureRegionDrawable(new TextureRegion(coinTexture));
        Image coinImage = new Image(coinDrawable);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(TINY_5));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 300;
        parameter.gamma = 1.8f;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle style = new Label.LabelStyle(font, new Color(1.0f, 0.9f, 0.0f, 1.0f));
        goldLabel = new Label("0", style);

        // Sub-table to align the coin image and gold label horizontally
        Table coinAndGoldLayout = new Table();
        coinAndGoldLayout.add(coinImage).padRight(20);  // Add the coin image with some padding to the right
        coinAndGoldLayout.add(goldLabel);  // Add the gold label next to the coin

        // Add the sub-table to the main layout, centered horizontally
        getLayout().add(coinAndGoldLayout).expandX().fillX().center().row();
    }

    /**
     * Sets up the lists displaying customer feedback.
     * This method configures two lists to show which customers passed and which failed,
     * providing direct feedback to the player.
     */
    public void setupCustomerLists() {
        // Customer lists
        Table listTable = new Table();

        Label passedLabel = new Label("Served Customers", skin);
        passedLabel.setFontScale(1.8f);
        Label failedLabel = new Label("Unserved Customers", skin);
        failedLabel.setFontScale(1.8f);
        listTable.add(passedLabel).pad(10).center();
        listTable.add(failedLabel).pad(10).center().row();

        ScrollPane passedScrollPane = new ScrollPane(passedCustomerTable, skin);
        passedScrollPane.setSmoothScrolling(true);
        ScrollPane failedScrollPane = new ScrollPane(failedCustomerTable, skin);
        failedScrollPane.setSmoothScrolling(true);
        listTable.add(passedScrollPane).pad(10).expand().width(400).fillY();
        listTable.add(failedScrollPane).pad(10).expand().width(400).fillY().row();
        listTable.padLeft(350).padRight(250);

        getLayout().add(listTable).expand().fill().row();
    }

    /**
     * Adds a close button to the display that can hide this component.
     * This method creates a button that when clicked, will toggle the visibility of
     * the end-of-day display, effectively closing it.
     */
    public void addCloseButton() {
        Texture texture = ServiceLocator.getResourceService()
                .getAsset("images/finish.png", Texture.class);

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));

        ImageButton closeBtn = new ImageButton(drawable);

        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleVisibility();
            }
        });
        getLayout().add(closeBtn)
                .expand()
                .padTop(80)
                .padLeft(100)
                .minWidth(200)
                .minHeight(60)
                .row();
    }

    /**
     * Handles updates to the gold display when the total gold changes.
     * This method is typically called by event listeners responding to changes in
     * the game state related to gold accumulation.
     *
     * @param gold the new total amount of gold to display
     */
    public void handleGoldUpdate(int gold) {
        currentGold = gold;
        goldLabel.setText(currentGold);
    }

    /**
     * Handles the event when a customer has successfully completed their interaction, such as finishing an order.
     * This method adds the customer's name in uppercase to the list of passed customers and logs the event.
     *
     * @param customerName The name of the customer who passed.
     */
    public void handlePassedCustomer(String customerName) {
        passedCustomerArray.add(customerName.toUpperCase());
        logger.info("Customer passed: {}", customerName);
    }

    /**
     * Updates the customer feedback lists when new customer feedback is received.
     * This method is called whenever there is new data about customer interactions,
     * allowing the display to refresh its lists accordingly.
     *
     * @param customerName the name of the customer to add to the feedback list
     */
    public void updateCustomerList(String customerName) {
        customerNameArray.add(customerName);
        logger.info("Updated customer list with: {}", customerNameArray);
    }

    /**
     * Recalculates the failed customers list, updating UI elements accordingly.
     */
    private void recalculateFailedCustomers() {
        failedCustomerTable.clearChildren();
        failedCustomerArray.clear();
        failedCustomerArray.addAll(customerNameArray);
        for (String passedName : passedCustomerArray) {
            failedCustomerArray.remove(passedName);
        }
        for (String passedName : passedCustomerArray) {
            assertCustomerTexture(passedName, passedCustomerTable);
        }
        for (String failedName : failedCustomerArray) {
            assertCustomerTexture(failedName, failedCustomerTable);
        }
    }

    /**
     * Retrieves configuration details for a customer based on their name.
     *
     * @param name the name of the customer.
     * @return the configuration details of the customer.
     */
    private CustomerPersonalityConfig getCustomerConfig(String name) {
        return switch (name) {
            case "HANK" -> configs.Hank;
            case "LEWIS" -> configs.Lewis;
            case "SILVER" -> configs.Silver;
            case "JOHN" -> configs.John;
            case "MOONKI" -> configs.Moonki;
            default -> configs.Default;
        };
    }

    /**
     * Attempts to load and display a customer's texture in the provided table. This method retrieves the customer's
     * texture based on their personality configuration and adds it to the specified table along with the customer's name.
     *
     * @param customerName The name of the customer for whom the texture is to be loaded.
     * @param customerTable The table where the customer's image and name label are to be displayed.
     */
    private void assertCustomerTexture(String customerName, Table customerTable) {
        CustomerPersonalityConfig config = getCustomerConfig(customerName);

        TextureAtlas atlas = ServiceLocator.getResourceService().getAsset(config.texture, TextureAtlas.class);
        TextureRegion region = atlas.findRegion("default");
        Image image = new Image(region);
        Label label = new Label(customerName, skin);
        label.setFontScale(1.5f);

        customerTable.row().pad(10);
        customerTable.add(image).size(70, 70);
        customerTable.add(label);
    }

    /**
     * Updates the position of the bird image on the display.
     * This method is used to animate the bird image across the display, creating a
     * dynamic visual effect.
     *
     * @param delta the time elapsed since the last frame update
     */
    public void updateBirdPosition(float delta) {
        imageX -= 200 * delta;
        if (imageX + getBirdImage().getWidth() < 0) {
            imageX = Gdx.graphics.getWidth();
        }
        getBirdImage().setPosition(imageX, getBirdImage().getY());

        getPointImage1().setPosition(imageX + getBirdImage().getWidth(), getPointImage1().getY());
        getPointImage2().setPosition(imageX + getBirdImage().getWidth() + getPointImage1().getWidth(), getPointImage2().getY());
        getPointImage3().setPosition(imageX + getBirdImage().getWidth() + 2 * getPointImage1().getWidth(), getPointImage3().getY());
    }

    /**
     * Animates the change in gold displayed, making the transition smooth.
     * This method gradually updates the displayed gold amount from the previous value
     * to the current value, using a simple animation.
     */
    public void animateGoldChange() {
        float duration = 1.0f;
        goldLabel.addAction(Actions.sequence(
                Actions.run(() -> goldLabel.setText(String.valueOf(STARTING_GOLD))),
                Actions.repeat(30, Actions.run(new Runnable() {
                    private float timePassed = 0;
                    @Override
                    public void run() {
                        timePassed += duration / 30;
                        int displayGold = (int) Interpolation.linear.apply(STARTING_GOLD, currentGold, timePassed / duration);
                        goldLabel.setText(String.valueOf(displayGold));
                    }
                })),
                Actions.run(() -> goldLabel.setText(String.valueOf(currentGold)))
        ));
    }

    /**
     * Shows the end-of-day display.
     * This method makes the display visible and updates all dynamic content to reflect
     * the current game state. It is usually called in response to a game event.
     */
    public void show() {
        ServiceLocator.getRandomComboService().deactivateUpgrade();

        recalculateFailedCustomers();
        setVisible(true);
        getLayout().setVisible(true);
        getBirdImage().setVisible(true);
        getPointImage1().setVisible(true);
        getPointImage2().setVisible(true);
        getPointImage3().setVisible(true);
        gameScreen.pause(); // Pause the game when the display is shown
        imageX = (float) (3 * Gdx.graphics.getWidth()) / 4; // Reset image position
        Task birdMoveTask = new Task() {
            @Override
            public void run() {
                updateBirdPosition(Gdx.graphics.getDeltaTime());
            }
        };
        Timer.schedule(birdMoveTask, 0, 1 / 60f); // Schedule the task
        this.animateGoldChange();
    }

    /**
     * Hides the display and triggers the game to continue.
     * This method is called when the close button is activated, hiding the display
     * and signaling the game to proceed with post-display actions.
     */
    public void hide() {
        ServiceLocator.getLevelService().togglePlayerFinishedLevel();
        getGame().setScreen(GdxGame.ScreenType.MAIN_GAME);
        ServiceLocator.getDayNightService().getEvents().trigger("TOMORAL");
    }

    /**
     * Toggles the visibility of the display between visible and hidden.
     * This method is a convenience for quickly showing or hiding the end-of-day
     * display based on its current visibility state.
     */
    public void toggleVisibility() {
        if (isVisible()) {
            hide();
        } else {
            show();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage

    }

    @Override
    public void setStage(Stage mock) {
        this.stage = mock;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Table getLayout() {
        return layout;
    }

    public void setLayout(Table layout) {
        this.layout = layout;
    }

    public GdxGame getGame() {
        return game;
    }

    public void setGame(GdxGame game) {
        this.game = game;
    }

    public Image getBirdImage() {
        return birdImage;
    }

    public void setBirdImage(Image birdImage) {
        this.birdImage = birdImage;
    }

    public Image getPointImage1() {
        return pointImage1;
    }

    public void setPointImage1(Image pointImage1) {
        this.pointImage1 = pointImage1;
    }

    public Image getPointImage2() {
        return pointImage2;
    }

    public void setPointImage2(Image pointImage2) {
        this.pointImage2 = pointImage2;
    }

    public Image getPointImage3() {
        return pointImage3;
    }

    public void setPointImage3(Image pointImage3) {
        this.pointImage3 = pointImage3;
    }
}
