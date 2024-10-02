package com.csse3200.game.components.maingame;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
        public boolean isVisible;
        private static final Logger logger = LoggerFactory.getLogger(com.csse3200.game.components.maingame.EndDayDisplay.class);
        private final MainGameScreen gameScreen;
        private final GdxGame game;
        private Image birdImage;
        private Image pointImage1;
        private Image pointImage2;
        private Image pointImage3;
        private float imageX;
        private int currentGold;
        private Label goldLabel;
        public final ArrayList<String> customerNameArray;
        private List<String> customerList;
        private static final int STARTING_GOLD = ServiceLocator.getLevelService().getCurrGold();

        /**
         * Constructor for the EndDayDisplay class.
         */
        public EndDayDisplay() {
            super();
            this.gameScreen = ServiceLocator.getGameScreen();
            this.game = ServiceLocator.getGameScreen().getGame();
            isVisible = false;
            this.currentGold = STARTING_GOLD;
            this.customerNameArray = new ArrayList<>();
        }

        /**
         * Initializes and creates the end-of-day display components.
         * This method sets up the entire layout for the display including background,
         * images, and interactive elements like buttons and lists.
         */
        public void create() {
            super.create();
            layout = new Table();
            layout.setFillParent(true);
            layout.setVisible(isVisible);
            stage.addActor(layout);

            createBackground();
            setupImages();
            setupUI();

            ServiceLocator.getDocketService().getEvents().addListener("goldUpdated", this::handleGoldUpdate);
            ServiceLocator.getLevelService().getEvents().addListener("customerSpawned", this::updateCustomerList);
            ServiceLocator.getLevelService().getEvents().addListener("endDayDisplay", this::show);
            ServiceLocator.getLevelService().getEvents().addListener("resetScreen", MainGameScreen::resetScreen);
            ServiceLocator.getEntityService().getEvents().addListener("toggleEndDayScreen", this::toggleVisibility);

            ServiceLocator.getDayNightService().getEvents().addListener("endOfDay", () -> {
                logger.info("it is listened in end day");
                show();});
        }

        /**
         * Sets up a white background for the display using a predefined image.
         * This method loads a texture from the resource service and sets it as the background
         * for the layout. The image used is specified in the texture path 'images/endday.png'.
         */
        private void createBackground() {
            // Create a background
            Texture texture = ServiceLocator.getResourceService()
                    .getAsset("images/endday.png", Texture.class);
            Drawable background = new TextureRegionDrawable(new TextureRegion(texture));
            layout.setBackground(background);
        }

        /**
         * Sets up the images for various UI elements in the display.
         * This method initializes and positions images that are used to enhance the visual
         * representation of the display, such as birds and points icons.
         */
        private void setupImages() {
            birdImage = createImage("images/bird.png");
            pointImage1 = createImage("images/point.png");
            pointImage2 = createImage("images/point.png");
            pointImage3 = createImage("images/point.png");
        }

        /**
         * Creates and returns an image actor for a given texture path.
         * This utility method simplifies the creation of an image actor from a texture,
         * making it easier to manage image setup throughout the class.
         *
         * @param texturePath the path to the texture asset
         * @return an initialized Image actor with the specified texture
         */
        private Image createImage(String texturePath) {
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
        private void setupUI() {
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
        private void addSpacer() {
            Table spacer = new Table();
            spacer.add().height(4 * birdImage.getHeight() / 5);
            layout.add(spacer).row();
        }

        /**
         * Sets up the display for showing the current gold amount.
         * This method configures a label to show the amount of gold collected, updating
         * its value in real time during the display.
         */
        private void setupGoldDisplay() {
            Texture coinTexture = ServiceLocator.getResourceService()
                    .getAsset("images/coin.png", Texture.class);
            Drawable coinDrawable = new TextureRegionDrawable(new TextureRegion(coinTexture));
            Image coinImage = new Image(coinDrawable);

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("flat-earth/skin/fonts/Tiny5-Regular.ttf"));
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
            layout.add(coinAndGoldLayout).expandX().fillX().center().row();
        }

        /**
         * Sets up the lists displaying customer feedback.
         * This method configures two lists to show which customers passed and which failed,
         * providing direct feedback to the player.
         */
        private void setupCustomerLists() {
            // Customer lists
            List<String> passedCustomers = new List<>(skin);
            List<String> failedCustomers = new List<>(skin);
            customerList = new List<>(skin);
            Table listTable = new Table();

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("flat-earth/skin/fonts/Tiny5-Regular.ttf"));
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = 30;
            parameter.gamma = 1.8f;
            BitmapFont font = generator.generateFont(parameter);
            generator.dispose();
            Label.LabelStyle newStyle = new Label.LabelStyle();
            newStyle.font = font;
            newStyle.fontColor = Color.WHITE;

            Label passedLabel = new Label("Passed Customers", newStyle);
            passedLabel.setFontScale(1.2f);

            Label failedLabel = new Label("Failed Customers", newStyle);
            failedLabel.setFontScale(1.2f);

            listTable.add(passedLabel).pad(10).center();
            listTable.add(failedLabel).pad(10).center().row();

            ScrollPane passedScrollPane = new ScrollPane(passedCustomers, skin);
            passedScrollPane.setSmoothScrolling(true);

            ScrollPane failedScrollPane = new ScrollPane(customerList, skin);
            failedScrollPane.setSmoothScrolling(true);

            listTable.add(passedScrollPane).pad(10).expand().width(400).fillY();
            listTable.add(failedScrollPane).pad(10).expand().width(400).fillY().row();
            listTable.padLeft(350).padRight(250);

            layout.add(listTable).expand().fill().row();
        }

        /**
         * Adds a close button to the display that can hide this component.
         * This method creates a button that when clicked, will toggle the visibility of
         * the end-of-day display, effectively closing it.
         */
        private void addCloseButton() {
            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
            Texture texture = ServiceLocator.getResourceService()
                    .getAsset("images/finish.png", Texture.class);

            style.up = new TextureRegionDrawable(new TextureRegion(texture));
            // style.down = new TextureRegionDrawable(new TextureRegion(new Texture("button_down.png")));

            BitmapFont font = new BitmapFont(Gdx.files.internal("default.fnt"));
            style.font = font;
            style.fontColor = Color.RED;

            TextButton closeBtn = new TextButton("", style);

            closeBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    toggleVisibility();
                }
            });
            layout.add(closeBtn)
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
         * Updates the customer feedback lists when new customer feedback is received.
         * This method is called whenever there is new data about customer interactions,
         * allowing the display to refresh its lists accordingly.
         *
         * @param customerName the name of the customer to add to the feedback list
         */
        public void updateCustomerList(String customerName) {
            customerNameArray.add(customerName);
            customerList.setItems(customerNameArray.toArray(new String[0]));
        }

        /**
         * Shows the end-of-day display.
         * This method makes the display visible and updates all dynamic content to reflect
         * the current game state. It is usually called in response to a game event.
         */
        public void show() {
            isVisible = true;
            layout.setVisible(true);
            birdImage.setVisible(true);
            pointImage1.setVisible(true);
            pointImage2.setVisible(true);
            pointImage3.setVisible(true);
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
         * Updates the position of the bird image on the display.
         * This method is used to animate the bird image across the display, creating a
         * dynamic visual effect.
         *
         * @param delta the time elapsed since the last frame update
         */
        private void updateBirdPosition(float delta) {
            imageX -= 200 * delta;
            if (imageX + birdImage.getWidth() < 0) {
                imageX = Gdx.graphics.getWidth();
            }
            birdImage.setPosition(imageX, birdImage.getY());

            pointImage1.setPosition(imageX + birdImage.getWidth(), pointImage1.getY());
            pointImage2.setPosition(imageX + birdImage.getWidth() + pointImage1.getWidth(), pointImage2.getY());
            pointImage3.setPosition(imageX + birdImage.getWidth() + 2 * pointImage1.getWidth(), pointImage3.getY());
        }

        /**
         * Animates the change in gold displayed, making the transition smooth.
         * This method gradually updates the displayed gold amount from the previous value
         * to the current value, using a simple animation.
         */
        private void animateGoldChange() {
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
         * Hides the display and triggers the game to continue.
         * This method is called when the close button is activated, hiding the display
         * and signaling the game to proceed with post-display actions.
         */
        public void hide() {
            ServiceLocator.getLevelService().togglePlayerFinishedLevel();
            game.setScreen(GdxGame.ScreenType.MAIN_GAME);
            ServiceLocator.getDayNightService().getEvents().trigger("TOMORAL");
        }

        /**
         * Toggles the visibility of the display between visible and hidden.
         * This method is a convenience for quickly showing or hiding the end-of-day
         * display based on its current visibility state.
         */
        public void toggleVisibility() {
            if (isVisible) {
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

        }
    }
